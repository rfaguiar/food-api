# defaul shell
SHELL = /bin/bash

# Rule "help"
.PHONY: help
.SILENT: help
help:
	echo "Use make [rule]"
	echo "Rules:"
	echo ""
	echo "build-app 		- build application and generate docker image"
	echo "run-mysql			- Run Mysql Database"
	echo "run-app			- Run application in a docker image"
	echo "logs-app			- Show application logs running in a docker"
	echo "remove-app		- Stop and remove application docker"

build-app:
	./mvnw clean dependency:list package && docker build --force-rm -t rfaguiar/food-api:latest .;

run-mysql:
	docker run --name mysql8 --network minha-rede -v $(pwd)/mysql-datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 -d mysql:8;

run-app: build-app remove-app logs-app
	docker run --name food-app --network minha-rede -p 8080:8080 -e DB_URL='jdbc:mysql://mysql8:3306/food_db?createDatabaseIfNotExist=true&serverTimezone=UTC' -e DB_USER=root -e DB_PASS=my-secret-pw --link mysql8:mysql8 -d rfaguiar/food-api:latest;

logs-app:
	docker logs -f -t food-app;

remove-app:
	docker rm -f food-app;
