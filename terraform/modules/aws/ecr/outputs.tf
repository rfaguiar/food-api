output "ecr_url" {
  value = aws_ecr_repository.ecr.repository_url
}

output "ecr_registry_id" {
  value = aws_ecr_repository.ecr.registry_id
}