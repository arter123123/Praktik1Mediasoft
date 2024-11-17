FROM openjdk:21-oracle

COPY target/Praktik1Mediasoft-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска приложения с ограничением памяти и активным профилем "dev"
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=local", "/app.jar"]

# Открываем порт 8080 для доступа к приложению
EXPOSE 8080