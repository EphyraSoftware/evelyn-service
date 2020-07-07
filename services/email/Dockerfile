FROM registry.evelyn.internal/evelyn/gradle-builder as builder

WORKDIR /build

COPY ["build.gradle", "settings.gradle", "./"]
COPY evelyn-service/ ./evelyn-service
COPY web-app/ ./web-app

RUN gradle build test --no-daemon

FROM openjdk:14-alpine

EXPOSE 8080

WORKDIR /app

COPY --from=builder /build/web-app/build/libs/web-app.jar ./application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
