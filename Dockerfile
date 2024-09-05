FROM openjdk:20-jdk-slim

WORKDIR /app

COPY ./target/GymKeeper-0.0.1-SNAPSHOT.jar /app/GymKeeper-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "GymKeeper-0.0.1-SNAPSHOT.jar"]