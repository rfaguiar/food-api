terraform {
  required_version = ">=0.13.1"
  required_providers {
    aws = ">=3.54.0"
    local = ">=2.1.0"
  }
}

provider "aws" {
  region = "us-east-1"
}

module "new-vpc" {
  source = "./modules/aws/vpc"
  prefix = var.prefix
  vpc_cidr_block = var.vpc_cidr_block
  ingress_port_from = var.db_port
  ingress_port_to = var.db_port
}

module "s3-bucket" {
  source = "./modules/aws/s3"
  prefix = var.prefix
  bucket_name = var.bucket_name
}

module "rds-instance" {
  source = "./modules/aws/rds"
  prefix = var.prefix
  subnet_ids = module.new-vpc.subnet_ids
  security_group_id = module.new-vpc.security_group_id
  db_port = var.db_port
  db_name = var.db_name
  db_username = var.db_username
  db_password = var.db_password
}

output "db_hostname" {
  sensitive = true
  value = module.rds-instance.rds_hostname
}

module "redis" {
  source = "./modules/aws/redis"
  prefix = var.prefix
  subnet_ids = module.new-vpc.subnet_ids
  security_group_id = module.new-vpc.security_group_id
  redis_port = var.redis_port
}

module "ecr" {
  source = "./modules/aws/ecr"
  prefix = var.prefix
}

output "ecr_url" {
  sensitive = true
  value = module.ecr.ecr_url
}

data "aws_region" "current" {}

resource "null_resource" "docker-login" {
  depends_on = [module.ecr]
  triggers = {
    always_run = timestamp()
  }
  provisioner "local-exec" {
    command = <<EOF
      aws ecr get-login-password --region ${data.aws_region.current.name} | docker login --username AWS --password-stdin ${module.ecr.ecr_registry_id}.dkr.ecr.${data.aws_region.current.name}.amazonaws.com
    EOF
  }
}

resource "null_resource" "build-app" {
  triggers = {
    always_run = timestamp()
  }
  provisioner "local-exec" {
    command = <<EOF
      cd .. && docker run --user "$(id -u):$(id -g)" -v $(pwd):/app -v ~/.m2/repository:/m2/repository --rm openjdk:15-jdk-slim bash -c "cd /app && ./mvnw clean package -Dmaven.repo.local=/m2/repository"
    EOF
  }
}

resource "null_resource" "build-image" {
  depends_on = [null_resource.build-app, module.ecr]
  triggers = {
    always_run = timestamp()
  }
  provisioner "local-exec" {
    command = <<EOF
      cd .. && docker build --force-rm -t ${module.ecr.ecr_url}:latest .
    EOF
  }
}

resource "null_resource" "push-image" {
  depends_on = [null_resource.build-image, null_resource.docker-login]
  triggers = {
    always_run = timestamp()
  }
  provisioner "local-exec" {
    command = <<EOF
      docker push ${module.ecr.ecr_url}:latest
    EOF
  }
}
