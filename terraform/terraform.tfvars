prefix = "food"
aws_region = "us-east-1"
bucket_name = "food-rf-api-bucket"
cluster_name = "food-rf"
cluster_instance_type = "t3.micro"
vpc_cidr_block = "10.0.0.0/16"
db_port = 3306
db_name = "food_db"
max_containers = 3
min_containers = 1
container_port = 8080
container_image_version = "latest"
s3_folder = "catalogo"