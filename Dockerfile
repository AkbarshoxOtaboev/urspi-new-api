# ---------- Frontend (Vite/React) ----------
FROM node:22-alpine AS frontend
WORKDIR /frontend

COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci

COPY frontend/ ./
RUN npm run build

# ---------- Backend (Maven/Spring Boot) ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src src
COPY --from=frontend /frontend/dist/ src/main/resources/static/

RUN mvn -q -B -DskipTests package

# ---------- Runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

ENV TZ=Asia/Tashkent \
    JAVA_OPTS="-Xms256m -Xmx512m" \
    FILE_UPLOAD_DIR=/app/uploads

RUN groupadd --system spring \
    && useradd --system --gid spring --home-dir /app --shell /usr/sbin/nologin spring \
    && mkdir -p /app/uploads \
    && chown -R spring:spring /app

COPY --from=build --chown=spring:spring /app/target/newurspi-0.0.1-SNAPSHOT.jar /app/app.jar

USER spring
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
