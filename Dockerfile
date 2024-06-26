FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/*.jar /app/program.jar
COPY ./cmd/wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

ARG PORT=8080
ENV PORT=$PORT

EXPOSE $PORT

CMD java --enable-preview -Duser.timezone=America/Sao_Paulo -Dserver.port=$PORT -jar program.jar

