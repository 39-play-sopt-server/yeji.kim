FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

COPY gradlew build.gradle settings.gradle ./
COPY gradle/ gradle/
COPY src/ src/
RUN bash ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/build/libs/assignment-1.0-SNAPSHOT.jar app.jar

USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]