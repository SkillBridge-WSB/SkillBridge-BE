FROM maven:3.9.11-eclipse-temurin-25
LABEL authors="igor"

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/skillbridge-0.0.1-SNAPSHOT.jar"]

