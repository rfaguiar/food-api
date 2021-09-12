resource "aws_acm_certificate" "cert" {
  domain_name       = var.domain_name
  validation_method = "EMAIL"

  tags = {
    Environment = "${var.prefix}-acm"
  }

  lifecycle {
    create_before_destroy = true
  }
}