resource "aws_ssm_parameter" "db_url" {
  name = "/${var.prefix}-secret/application/db_url"
  type        = "SecureString"
  value       = var.db_url

  tags = {
    environment = "${var.prefix}-secret-dburl"
  }
}

resource "aws_ssm_parameter" "db_port" {
  name = "/${var.prefix}-secret/application/db_port"
  type        = "SecureString"
  value       = var.db_port

  tags = {
    environment = "${var.prefix}-secret-db_port"
  }
}

resource "aws_ssm_parameter" "db_username" {
  name = "/${var.prefix}-secret/application/db_username"
  type        = "SecureString"
  value       = var.db_username

  tags = {
    environment = "${var.prefix}-secret-db_username"
  }
}

resource "aws_ssm_parameter" "db_password" {
  name = "/${var.prefix}-secret/application/db_password"
  type        = "SecureString"
  value       = var.db_username

  tags = {
    environment = "${var.prefix}-secret-db_password"
  }
}

resource "aws_ssm_parameter" "db_name" {
  name = "/${var.prefix}-secret/application/db_name"
  type        = "SecureString"
  value       = var.db_name

  tags = {
    environment = "${var.prefix}-secret-db_name"
  }
}

resource "aws_ssm_parameter" "s3_key" {
  name = "/${var.prefix}-secret/application/s3_key"
  type        = "SecureString"
  value       = var.db_name

  tags = {
    environment = "${var.prefix}-secret-s3_key"
  }
}

resource "aws_ssm_parameter" "s3_secret" {
  name = "/${var.prefix}-secret/application/s3_secret"
  type        = "SecureString"
  value       = var.s3_secret

  tags = {
    environment = "${var.prefix}-secret-s3_secret"
  }
}

resource "aws_ssm_parameter" "s3_bucket_name" {
  name = "/${var.prefix}-secret/application/s3_bucket_name"
  type        = "SecureString"
  value       = var.s3_bucket_name

  tags = {
    environment = "${var.prefix}-secret-s3_bucket_name"
  }
}

resource "aws_ssm_parameter" "s3_bucket_region" {
  name = "/${var.prefix}-secret/application/s3_bucket_region"
  type        = "SecureString"
  value       = var.s3_bucket_region

  tags = {
    environment = "${var.prefix}-secret-s3_bucket_region"
  }
}

resource "aws_ssm_parameter" "s3_folder" {
  name = "/${var.prefix}-secret/application/s3_folder"
  type        = "SecureString"
  value       = var.s3_folder

  tags = {
    environment = "${var.prefix}-secret-s3_folder"
  }
}

resource "aws_ssm_parameter" "mail_host" {
  name = "/${var.prefix}-secret/application/mail_host"
  type        = "SecureString"
  value       = var.mail_host

  tags = {
    environment = "${var.prefix}-secret-mail_host"
  }
}

resource "aws_ssm_parameter" "mail_port" {
  name = "/${var.prefix}-secret/application/mail_port"
  type        = "SecureString"
  value       = var.mail_port

  tags = {
    environment = "${var.prefix}-secret-mail_port"
  }
}

resource "aws_ssm_parameter" "mail_user" {
  name = "/${var.prefix}-secret/application/mail_user"
  type        = "SecureString"
  value       = var.mail_user

  tags = {
    environment = "${var.prefix}-secret-mail_user"
  }
}

resource "aws_ssm_parameter" "mail_password" {
  name = "/${var.prefix}-secret/application/mail_password"
  type        = "SecureString"
  value       = var.mail_password

  tags = {
    environment = "${var.prefix}-secret-mail_password"
  }
}

resource "aws_ssm_parameter" "mail_remetente" {
  name = "/${var.prefix}-secret/application/mail_remetente"
  type        = "SecureString"
  value       = var.mail_remetente

  tags = {
    environment = "${var.prefix}-secret-mail_remetente"
  }
}

resource "aws_ssm_parameter" "jks_base64" {
  name = "/${var.prefix}-secret/application/jks_base64"
  type        = "SecureString"
  value       = var.jks_base64

  tags = {
    environment = "${var.prefix}-secret-jks_base64"
  }
}

resource "aws_ssm_parameter" "jks_password" {
  name = "/${var.prefix}-secret/application/jks_password"
  type        = "SecureString"
  value       = var.jks_password

  tags = {
    environment = "${var.prefix}-secret-jks_password"
  }
}

resource "aws_ssm_parameter" "jks_alias" {
  name = "/${var.prefix}-secret/application/jks_alias"
  type        = "SecureString"
  value       = var.jks_alias

  tags = {
    environment = "${var.prefix}-secret-jks_alias"
  }
}
