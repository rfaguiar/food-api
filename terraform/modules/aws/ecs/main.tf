resource "aws_ecs_cluster" "cluster" {
  name = "${var.prefix}-cluster"
  tags = {
    Name = "${var.prefix}-cluster"
  }
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name = "${var.prefix}-ecsTaskExecutionRole"
  tags = {
    Name = "${var.prefix}-ecsTaskExecutionRole"
  }
  assume_role_policy = <<EOF
{
 "Version": "2012-10-17",
 "Statement": [
   {
     "Action": "sts:AssumeRole",
     "Principal": {
       "Service": "ecs-tasks.amazonaws.com"
     },
     "Effect": "Allow",
     "Sid": ""
   }
 ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "ecs-task-execution-role-policy-attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role" "ecs_task_role" {
  name = "${var.prefix}-ecsTaskRole"
  tags = {
    Name = "${var.prefix}-ecsTaskRole"
  }

  assume_role_policy = <<EOF
{
 "Version": "2012-10-17",
 "Statement": [
   {
     "Action": "sts:AssumeRole",
     "Principal": {
       "Service": "ecs-tasks.amazonaws.com"
     },
     "Effect": "Allow",
     "Sid": ""
   }
 ]
}
EOF
}

resource "aws_iam_policy" "secret" {
  name        = "${var.prefix}-task-policy"
  tags = {
    Name = "${var.prefix}-task-policy"
  }

  policy = <<EOF
{
   "Version": "2012-10-17",
   "Statement": [
       {
           "Effect": "Allow",
           "Action": [
            "ssm:GetParametersByPath",
            "ssm:GetParameters",
            "ssm:GetParameter"
           ],
           "Resource": [
              "arn:aws:ssm:*"
            ]
       }
   ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "ecs-task-role-policy-attachment_task_execution_role" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = aws_iam_policy.secret.arn
}

resource "aws_iam_role_policy_attachment" "ecs-task-role-policy-attachment_task_role" {
  role       = aws_iam_role.ecs_task_role.name
  policy_arn = aws_iam_policy.secret.arn
}

resource "aws_cloudwatch_log_group" "log_group" {
  name = "${var.prefix}/${var.container_name}"

  tags = {
    Environment = "production"
    Application = "serviceA"
  }
}

resource "aws_ecs_task_definition" "task_definition" {
  family = "service"
  network_mode = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu = 512
  memory = 1024
  execution_role_arn = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn = aws_iam_role.ecs_task_role.arn
  container_definitions = jsonencode([
    {
      name = "${var.prefix}-container"
      image = var.container_image
      essential = true
      portMappings = [
        {
          protocol = "tcp"
          containerPort = var.container_port
          hostPort = var.container_port
        }]
      secrets = var.secret_map
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group =  aws_cloudwatch_log_group.log_group.name,
          awslogs-region = var.aws_region,
          awslogs-stream-prefix = var.prefix
        }
      }
    }])
  tags = {
    Name = "${var.prefix}-task_definition"
  }
}

resource "aws_ecs_service" "service" {
  depends_on = [aws_ecs_task_definition.task_definition, aws_ecs_cluster.cluster]
  name                               = "${var.prefix}-service"
  cluster                            = aws_ecs_cluster.cluster.id
  task_definition                    = aws_ecs_task_definition.task_definition.id
  desired_count                      = var.min_containers
  deployment_minimum_healthy_percent = 50
  deployment_maximum_percent         = 200
  launch_type                        = "FARGATE"
  scheduling_strategy                = "REPLICA"
  health_check_grace_period_seconds  = 150
  tags = {
    Name = "${var.prefix}-service"
  }

  network_configuration {
    security_groups = [var.security_group_id]
    subnets          = var.subnet_ids
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = var.aws_alb_target_group_arn
    container_name   = "${var.prefix}-container"
    container_port   = var.container_port
  }
}

resource "aws_appautoscaling_target" "ecs_target" {
  max_capacity       = var.max_containers
  min_capacity       = var.min_containers
  resource_id        = "service/${aws_ecs_cluster.cluster.name}/${aws_ecs_service.service.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

resource "aws_appautoscaling_policy" "ecs_policy_memory" {
  name               = "memory-autoscaling"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.ecs_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_target.service_namespace

  target_tracking_scaling_policy_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ECSServiceAverageMemoryUtilization"
    }
    target_value       = 80
  }
}

resource "aws_appautoscaling_policy" "ecs_policy_cpu" {
  name               = "cpu-autoscaling"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.ecs_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_target.service_namespace

  target_tracking_scaling_policy_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ECSServiceAverageCPUUtilization"
    }
    target_value       = 70
  }
}
