resource "aws_elasticache_subnet_group" "redis-subnet" {
  name       = "${var.prefix}-cache-subnet"
  subnet_ids = var.subnet_ids
  tags = {
    Name = "${var.prefix}-redis-subnet"
  }
}

resource "aws_elasticache_cluster" "redis" {
  cluster_id           = "${var.prefix}-cluster-redis"
  engine               = "redis"
  node_type            = "cache.t3.micro"
  num_cache_nodes      = 1
  parameter_group_name = "default.redis3.2"
  engine_version       = "3.2.10"
  port                 = var.redis_port
  subnet_group_name    = aws_elasticache_subnet_group.redis-subnet.name
  security_group_ids   = [var.security_group_id]
  tags = {
    Name = "${var.prefix}-redis"
  }
}