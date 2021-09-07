resource "aws_ecr_repository" "ecr" {
  name                 = "${var.prefix}-api"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
  tags = {
    Name = "${var.prefix}-ecr"
  }
}

resource "aws_ecr_lifecycle_policy" "main" {
  repository = aws_ecr_repository.ecr.name

  policy = jsonencode({
    rules = [{
      rulePriority = 1
      description  = "keep last 3 images"
      action       = {
        type = "expire"
      }
      selection     = {
        tagStatus   = "any"
        countType   = "imageCountMoreThan"
        countNumber = 3
      }
    }]
  })
}
