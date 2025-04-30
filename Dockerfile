#FROM openjdk:21-jdk-slim
#RUN apt-get update && apt-get install -y maven
#
##COPY src/main/java /app
##WORKDIR /app
#
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#
#ARG JAR_FILE=target/couriertracking-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} application.jar
#
##RUN mvn clean package -DskipTests
#ENTRYPOINT ["java", "-jar", "application.jar"]
#EXPOSE 8080


FROM openjdk:21-jdk
ARG JAR_FILE=target/couriertracking-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080