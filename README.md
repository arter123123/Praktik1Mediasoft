# Praktik1Mediasoft
1. Требования
Для запуска проекта потребуется:

Docker и Docker Compose для создания контейнеров.
Java (версия 21 или выше) для сборки и запуска Spring Boot приложения.
Maven для сборки проекта (если не используется Docker для этого).
PostgreSQL для работы с базой данных (будет запускаться в контейнере Docker).
2. Структура проекта
Проект состоит из нескольких ключевых файлов и папок:

docker-compose.yaml — файл для настройки контейнеров с Docker.
Dockerfile — инструкция для сборки Docker-образа приложения.
application.yaml и application-local.yaml — конфигурации для Spring Boot, включая настройки базы данных.
db_changelog-master.yml — конфигурация Liquibase для миграции базы данных.
pom.xml — конфигурация Maven для сборки проекта.
src/ — исходный код Java-приложения (контроллеры, сервисы, модели).
3. Подготовка проекта
Шаг 1: Склонировать репозиторий (если проект еще не скачан на вашей системе)
Если проект находится в репозитории, выполните команду:

bash
Копировать код
git clone <URL_репозитория>
cd <папка_проекта>
Шаг 2: Сборка проекта с помощью Maven
Перед тем как запустить проект с помощью Docker, можно собрать его вручную через Maven:

bash
Копировать код
mvn clean install
Этот шаг создаст файл Praktik1Mediasoft-0.0.1-SNAPSHOT.jar, который будет использоваться в Docker-контейнере.

Шаг 3: Построение Docker-образа приложения
Если вы используете Docker для сборки и запуска приложения, переходите к следующему шагу. Для этого нужно собрать Docker-образ:

bash
Копировать код
docker build -t praktik1mediasoft .
Это создаст Docker-образ с вашим приложением.

4. Запуск с использованием Docker
Проект настроен для использования Docker Compose, что позволяет запускать все контейнеры (для приложения и базы данных) одновременно.

Шаг 1: Запуск Docker Compose
Для запуска всех сервисов (контейнеров) используйте следующую команду в корне проекта, где находится файл docker-compose.yaml:

bash
Копировать код
docker-compose up
Эта команда:

Запустит контейнер с PostgreSQL, настроенный с помощью образа postgres:15.
Соберет приложение, если оно еще не собрано, и запустит его в контейнере.
Привяжет порты 8080 (для приложения) и 5432 (для базы данных).
Шаг 2: Проверка работы сервисов
После запуска командой docker-compose up, вы сможете проверить работу сервиса через браузер или через инструменты для тестирования API, такие как Postman или curl.

Приложение будет доступно по адресу: http://localhost:8080
PostgreSQL будет доступен по адресу: localhost:5432.
5. Описание файлов конфигурации
docker-compose.yaml
yaml
Копировать код
version: '3.8'
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: warehouse
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/warehouse
Контейнер db запускает PostgreSQL с базой данных warehouse, пользователем user и паролем password.
Контейнер app будет собирать приложение и запускать его на порту 8080. Он зависит от контейнера базы данных и использует его адрес для подключения.
application.yaml
Этот файл конфигурирует подключение Spring Boot приложения к базе данных.

yaml
Копировать код
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/warehouse
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  liquibase:
    change-log: classpath:db/db_changelog-master.yml
Здесь указывается URL для подключения к базе данных, а также настройки JPA и Liquibase.
application-local.yaml
Конфигурация для локальной разработки с указанием профиля local:

yaml
Копировать код
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/warehouse
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  liquibase:
    change-log: classpath:db/db_changelog-master.yml
db_changelog-master.yml
Файл для миграций с помощью Liquibase.

yaml
Копировать код
databaseChangeLog:
  - includeAll:
      path: classpath*:db/changelogs
      errorIfMissingOrEmpty: false
Этот файл указывает на то, что все изменения для базы данных будут храниться в папке db/changelogs.

6. Тестирование API
API, предоставляемое приложением, доступно по следующему пути:

GET /api/products — получить все товары
GET /api/products/{id} — получить товар по ID
POST /api/products — создать новый товар
PUT /api/products/{id} — обновить товар
DELETE /api/products/{id} — удалить товар
Пример с использованием Postman:

GET /api/products — Получить список всех продуктов.
POST /api/products — Создать новый продукт с телом запроса:
json
Копировать код
{
  "sku": "P12345",
  "name": "Product Name",
  "description": "Product Description",
  "category": "Category",
  "price": 100.50,
  "quantity": 10
}
7. Остановка контейнеров
Для остановки контейнеров можно использовать команду:

bash
Копировать код
docker-compose down
Эта команда остановит все контейнеры, связанные с вашим проектом.

8. Решение возможных проблем
Ошибка при запуске контейнера:

Проверьте логи с помощью команды:
bash
Копировать код
docker-compose logs
Убедитесь, что все зависимости, такие как база данных, настроены правильно.
Проблемы с подключением к базе данных:

Убедитесь, что контейнер с базой данных запущен и работает.
Проверьте настройки SPRING_DATASOURCE_URL в файле docker-compose.yaml и application.yaml.