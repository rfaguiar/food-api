output "vpc_id" {
  value = aws_vpc.new-vpc.id
}

output "subnet_ids" {
  value = aws_subnet.subnets[*].id
}

output "security_group_app_id" {
  value = aws_security_group.sg-app.id
}

output "security_group_db_id" {
  value = aws_security_group.sg-db.id
}

output "security_group_alb_id" {
  value = aws_security_group.sg-alb.id
}

