FROM openjdk:15-jdk-slim

WORKDIR /app

COPY ./target/*.jar /app/program.jar

ARG PORT=8080
ENV PORT=$PORT

EXPOSE $PORT

ENTRYPOINT java --enable-preview -Dserver.port=$PORT -jar program.jar
