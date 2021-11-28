FROM openjdk:11-jdk-slim
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# https://spring.io/guides/gs/spring-boot-docker/
# 1
# ./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar
# 2
# docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
# 3
#