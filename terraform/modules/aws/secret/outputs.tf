output "parameter_map" {
  value = [
    {name = "DB_URL", valueFrom = aws_ssm_parameter.db_url.arn},
    {name = "DB_PORT", valueFrom = aws_ssm_parameter.db_port.arn},
    {name = "DB_USER", valueFrom = aws_ssm_parameter.db_username.arn},
    {name = "DB_PASS", valueFrom = aws_ssm_parameter.db_password.arn},
    {name = "DB_NAME", valueFrom = aws_ssm_parameter.db_name.arn},
    {name = "S3_CHAVE_ACESSO", valueFrom = aws_ssm_parameter.s3_key.arn},
    {name = "S3_CHAVE_SECRETA", valueFrom = aws_ssm_parameter.s3_secret.arn},
    {name = "S3_BUCKET_NAME", valueFrom = aws_ssm_parameter.s3_bucket_name.arn},
    {name = "S3_BUCKET_REGIAO", valueFrom = aws_ssm_parameter.s3_bucket_region.arn},
    {name = "S3_BUCKET_DIRETORIO", valueFrom = aws_ssm_parameter.s3_folder.arn},
    {name = "MAIL_HOST", valueFrom = aws_ssm_parameter.mail_host.arn},
    {name = "MAIL_PORT", valueFrom = aws_ssm_parameter.mail_port.arn},
    {name = "MAIL_USER", valueFrom = aws_ssm_parameter.mail_user.arn},
    {name = "MAIL_PASSWORD", valueFrom = aws_ssm_parameter.mail_password.arn},
    {name = "MAIL_REMETENTE", valueFrom = aws_ssm_parameter.mail_remetente.arn},
    {name = "JKS_BASE64", valueFrom = aws_ssm_parameter.jks_base64.arn},
    {name = "KEYSTORE_PASSWORD", valueFrom = aws_ssm_parameter.jks_password.arn},
    {name = "KEYSTORE_ALIAS", valueFrom = aws_ssm_parameter.jks_alias.arn},
  ]
}
