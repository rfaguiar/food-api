# API de delivery de comida
# Spring 2.3.4 
# Spring OAuth server
# Java 15
# RestFull HATEOAS
# OpenApi Doc 
### https://food-api-rfaguiar.herokuapp.com/swagger-ui/
# HATEOAS Root path
### https://food-api-rfaguiar.herokuapp.com

## v1 Api auth

perfil Admin:  
username: rfaguiar1@gmail.com  
password: 123

perfil Cliente:  
username: maria.vnd@food.com    
password: 123

perfil Cadastrador:  
username: manoel.loja@gmail.com  
password: 123

### Password Flow
clientId: food-web  
clientSecret: web123  
POST:  
https://food-api-rfaguiar.herokuapp.com/oauth/token  
Headers:  
Content-Type: application/x-www-form-urlencoded  
Authorization: Basic Zm9vZC13ZWI6d2ViMTIz  
Body:  
username=manoel.loja@gmail.com&password=123&grant_type=password  

### Implicit Flow
https://food-api-rfaguiar.herokuapp.com/oauth/authorize?response_type=token&client_id=webadmin&state=abc&redirect_uri=http://aplicacao-cliente

### Client Credentials Flow  
clientId: faturamento  
clientSecret: faturamento123  
POST:  
https://food-api-rfaguiar.herokuapp.com/oauth/token  
Headers:  
Content-Type: application/x-www-form-urlencoded  
Authorization: Basic ZmF0dXJhbWVudG86ZmF0dXJhbWVudG8xMjM=  
Body:  
grant_type=client_credentials

### Authorization Code Flow
clientId: food-analytics  
clientSecret: food123    
#### Login in your browser:
https://food-api-rfaguiar.herokuapp.com/oauth/authorize?response_type=code&client_id=food-analytics&state=abc&redirect_uri=http://localhost:8082

#### Request to generate token:  
POST:  
https://food-api-rfaguiar.herokuapp.com/oauth/token  
Headers:  
Content-Type: application/x-www-form-urlencoded  
Authorization: Basic Zm9vZC1hbmFseXRpY3M6Zm9vZDEyMw==  
Body:  
grant_type=authorization_code&redirect_uri=http://localhost:8082&code={substituir por codigo}

### Authorization Code Flow com PKCE
clientId: food-analytics  
clientSecret: food123    
#### Login in your browser:  
Code Verifier: teste123  
Code Challenge: teste123  
Obs mudar o method para s256 (SHA-256)  
https://food-api-rfaguiar.herokuapp.com/oauth/authorize?response_type=code&client_id=food-analytics&state=abc&redirect_uri=http://localhost:8082&code_challeng_method=plain&code_challeng=teste123


sha-256
https://tonyxu-io.github.io/pkce-generator/  
#### Another request in your browser:
Code Verifier: teste123  
Code Challenge com base64 e sha256 base64url(sha256(teste123)): KJFg2w2fOfmuF1TE7JwW-QtQ4y4JxftUga5kKz09GjY  
https://food-api-rfaguiar.herokuapp.com/oauth/authorize?response_type=code&client_id=food-analytics&state=abc&redirect_uri=http://localhost:8082&code_challeng_method=s256&code_challeng=KJFg2w2fOfmuF1TE7JwW-QtQ4y4JxftUga5kKz09GjY


#### Request to generate token:
POST:
https://food-api-rfaguiar.herokuapp.com/oauth/token  
Headers:  
Content-Type: application/x-www-form-urlencoded  
Authorization: Basic Zm9vZC1hbmFseXRpY3M6Zm9vZDEyMw==  
Body:  
grant_type=authorization_code&redirect_uri=http://localhost:8082&code=kUSmN8er&code_verifier=teste123  

# Getting Started

### MySQL 8 docker
* [MySQL Docker Official image](https://hub.docker.com/_/mysql)
```sh
docker run --rm --name mysql8 --network minha-rede -v $(pwd)/mysql-datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 -d mysql:8
```
#### JDBC envs:  
DB_URL
DB_USER
DB_PASS

#### AWS S3 envs:  
TIPO_STORAGE=s3
S3_CHAVE_ACESSO
S3_CHAVE_SECRETA
S3_BUCKET_NAME
S3_BUCKET_REGIAO
S3_BUCKET_DIRETORIO

#### SMTP envs:
EMAIL_IMPL=SMTP
MAIL_HOST
MAIL_PORT
MAIL_USER
MAIL_PASSWORD

#### Security envs:  
JKS_BASE64

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-validation)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-email)
* [Apache Freemarker](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-spring-hateoas)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-security)
* [Jaspersoft JasperReports](https://community.jaspersoft.com/documentation?version=61056)
* [AWS SDK for Java](https://docs.aws.amazon.com/sdk-for-java/index.html)
* [K8S Minikube](https://minikube.sigs.k8s.io/docs/start)

#### Tests Documentation  

* [JUint 5](https://junit.org/junit5/docs/current/user-guide/)
* [REST-assured](https://github.com/rest-assured/rest-assured/wiki/Usage)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
