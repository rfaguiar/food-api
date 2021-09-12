output "target-group-arn" {
  value = aws_alb_target_group.target-group.arn
}

output "alb-endpoint" {
  value = aws_lb.alb.dns_name
}