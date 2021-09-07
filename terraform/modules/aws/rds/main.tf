resource "aws_db_subnet_group" "db-subnet-group" {
  name = "${var.prefix}-db-subnet-group"
  subnet_ids = var.subnet_ids
  tags = {
    Name = "${var.prefix}-db-subnet-group"
  }
}

resource "aws_db_instance" "mysql" {
  identifier           = "${var.prefix}-mysql8"
  name                 = var.db_name
  username             = var.db_username
  password             = var.db_password
  port                 = var.db_port
  allocated_storage    = 10
  engine               = "mysql"
  engine_version       = "8.0"
  instance_class       = "db.t2.micro"
  db_subnet_group_name   = aws_db_subnet_group.db-subnet-group.name
  vpc_security_group_ids = [var.security_group_id]
  publicly_accessible    = true
  skip_final_snapshot  = true
}