# defaul shell
SHELL = /bin/bash

# Rule "help"
.PHONY: help
.SILENT: help
help:
	echo "Use make [rule]"
	echo "Rules:"
	echo ""
	echo "build-mvn-app     - build application with local java and maven wrapper"
	echo "build-docker-app  - build application with docker java and maven wrapper"
	echo "build-app 		- build application and generate docker image"
	echo "run-mysql			- Run Mysql Database"
	echo "run-app			- Run application in a docker image"
	echo "logs-app			- Show application logs running in a docker"
	echo "remove-app		- Stop and remove application docker"
	echo "k-setup			- Configure local cluster minikube"
	echo "k-stop			- Stop local cluster minikube"
	echo "k-delete			- Delete local cluster minikube"
	echo "k-dashboard		- Open in default browser K8S cluster dashboard"
	echo "k-deploy-mysql	- Deploy a mysql database image"
	echo "k-build-app		- Build application docker image inside minikube cluster"
	echo "k-remove-app		- Remove application inside minikube cluster"

build-mvn-app:
	./mvnw clean dependency:list package;

build-docker-app:
	docker run -v $(shell pwd):/app \
	-v ~/.m2/repository:/m2/repository \
	--rm -it openjdk:17-jdk-slim bash \
	-c "cd /app && ./mvnw clean package -Dmaven.repo.local=/m2/repository"

build-app: build-docker-app
	docker build --force-rm -t rfaguiar/food-api:latest .;

create-docker-net:
	docker network create -d bridge minha-rede;

run-mysql:
	docker run --rm --name mysql8 --network minha-rede \
	-v /tmp/mysql-datadir:/var/lib/mysql \
	-e MYSQL_ROOT_PASSWORD=my-secret-pw \
	-p 3306:3306 -d mysql:8;

run-app:
	docker run --rm --name food-app --network minha-rede -p 8080:8080 \
	-e DB_URL='jdbc:mysql://mysql8:3306/food_db?createDatabaseIfNotExist=true&serverTimezone=UTC' \
	-e DB_USER=root -e DB_PASS=my-secret-pw -e SPRING_PROFILES_ACTIVE=dev --link mysql8:mysql8 \
	-d rfaguiar/food-api:latest;

logs-app:
	docker logs -f -t food-app;

remove-app:
	docker rm -f food-app mysql8;

k-setup:
	minikube -p dev-to start --cpus 2 --memory=4098; \
	minikube -p dev-to addons enable ingress; \
	minikube -p dev-to addons enable metrics-server; \
	kubectl create namespace dev-to; \
	minikube -p dev-to ip; \

k-dashboard:
	minikube -p dev-to dashboard;

k-stop:
	minikube -p dev-to stop;

k-delete:
	minikube -p dev-to stop && minikube -p dev-to delete;

k-deploy-mysql:
	kubectl -n dev-to apply -f kubernetes/mysql.yaml ;

#apos subir a aplicação deve configurar o arquivo hosts da maquina com o ip do minikube para o host do ingress
k-build-app: build-docker-app
	eval $$(minikube -p dev-to docker-env) && docker build --force-rm -t rfaguiar/food-api:latest .;

k-deploy-app:
	kubectl apply -f kubernetes/food-api.yaml;

k-remove-app:
	kubectl delete -f kubernetes/food-api.yaml;

k-test-app:
	curl --resolve "dev.food-api.local:80:$( minikube -p dev-to ip )" -i http://dev.food-api.local;