
resource "aws_lb" "alb" {
  name               = "${var.prefix}-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.security_group_id]
  subnets            = var.subnets_id
  tags = {
    Name = "${var.prefix}-alb"
  }

  enable_deletion_protection = false
}

resource "aws_alb_target_group" "target-group" {
  name        = "${var.prefix}-alb-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"
  tags = {
    Name = "${var.prefix}-alb-tg"
  }

  health_check {
    healthy_threshold   = "3"
    interval            = "30"
    protocol            = "HTTP"
    matcher             = "200"
    timeout             = "3"
    path                = var.health_check_path
    unhealthy_threshold = "2"
  }
}

resource "aws_alb_listener" "listener-http" {
  load_balancer_arn = aws_lb.alb.id
  port              = 80
  protocol          = "HTTP"
  tags = {
    Name = "${var.prefix}-listener-http"
  }
  default_action {
    type = "redirect"

    redirect {
      port        = 443
      protocol    = "HTTPS"
      status_code = "HTTP_301"
    }
  }
}

resource "aws_alb_listener" "listener-https" {
  load_balancer_arn = aws_lb.alb.id
  port              = 443
  protocol          = "HTTPS"

  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = var.alb_tls_cert_arn
  tags = {
    Name = "${var.prefix}-listener-https"
  }

  default_action {
    target_group_arn = aws_alb_target_group.target-group.id
    type             = "forward"
  }
}