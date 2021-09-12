terraform {
  required_version = ">=0.13.1"
  required_providers {
    aws = ">=3.54.0"
    local = ">=2.1.0"
  }
}

provider "aws" {
  region = var.aws_region
}

module "new-vpc" {
  source = "./modules/aws/vpc"
  prefix = var.prefix
  vpc_cidr_block = var.vpc_cidr_block
  ingress_port_from = var.db_port
  ingress_port_to = var.db_port
  db_port = var.db_port
  app_port = var.container_port
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
  security_group_id = module.new-vpc.security_group_db_id
  db_port = var.db_port
  db_name = var.db_name
  db_username = var.db_username
  db_password = var.db_password
}

module "ecr" {
  source = "./modules/aws/ecr"
  prefix = var.prefix
}

data "aws_region" "current" {}

module "secret" {
  source = "./modules/aws/secret"
  prefix = var.prefix
  db_password = var.db_password
  db_url = module.rds-instance.rds_hostname
  db_port = module.rds-instance.rds_port
  db_username = module.rds-instance.rds_username
  db_name = var.db_name
  s3_bucket_name = var.bucket_name
  s3_bucket_region = data.aws_region.current.name
  s3_folder = var.s3_folder
  s3_key = var.s3_user_key
  s3_secret = var.s3_user_secret
  jks_alias = var.jks_alias
  jks_base64 = var.jks_base64
  jks_password = var.jks_password
  mail_host = var.mail_host
  mail_password = var.mail_password
  mail_port = var.mail_port
  mail_remetente = var.mail_remetente
  mail_user = var.mail_user
}

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
      cd .. && docker build --force-rm -t ${module.ecr.ecr_url}:${var.container_image_version} .
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
      docker push ${module.ecr.ecr_url}:${var.container_image_version}
    EOF
  }
}

module "certificate" {
  source = "./modules/aws/acm"
  prefix = var.prefix
  domain_name = var.domain_name
}

module "alb" {
  source = "./modules/aws/alb"
  depends_on = [module.new-vpc, module.certificate]
  prefix = var.prefix
  subnets_id = module.new-vpc.subnet_ids
  vpc_id = module.new-vpc.vpc_id
  security_group_id = module.new-vpc.security_group_alb_id
  health_check_path = "/actuator/health"
  alb_tls_cert_arn = module.certificate.acm_id
}

module "ecs" {
  source = "./modules/aws/ecs"
  depends_on = [module.new-vpc, module.secret, module.rds-instance, module.alb, null_resource.push-image]
  prefix = var.prefix
  security_group_id = module.new-vpc.security_group_app_id
  subnet_ids = module.new-vpc.subnet_ids
  container_name = "${var.prefix}-api"
  container_image = "${module.ecr.ecr_url}:${var.container_image_version}"
  container_port = var.container_port
  secret_map = module.secret.parameter_map
  aws_region = data.aws_region.current.name
  aws_alb_target_group_arn = module.alb.target-group-arn
  max_containers = var.max_containers
  min_containers = var.min_containers
}

output "db_hostname" {
  sensitive = true
  value = module.rds-instance.rds_hostname
}

output "alb_hostname" {
  value = module.alb.alb-endpoint
}

