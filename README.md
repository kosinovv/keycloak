<h1 style="text-align: center;">Курсовая работа - Сервис аутентификации на KeyCloak</h1>

[![versionspringboot](https://img.shields.io/badge/springboot-3.3.2-brightgreen.svg)](https://github.com/spring-projects/spring-boot)

Разработчик: Косинов Виктор, версия: 1.4

Приложение демонстрирует работу IDM KeyCloak

# Используемые в Docker образы 

| Образ                        | Версия |  Контейнер  |
|------------------------------|--------|-------------|
| `quay.io/keycloak/keycloak`  | latest |  keycloak   |
| `postgres`                   | latest |  postgres_k |
 
# Как загрузить quay.io/keycloak/keycloak

- docker pull quay.io/keycloak/keycloak

# Что необходимо сделать для запуска приложения

- запустить docker-compose.yaml и проверить, что в Docker поднялся контейнер kosinov-keycloak и в нем образы keycloak и postgres_k
- авторизоваться под admin/admin на сервисе Keycloak http://localhost:8080
- завести административного пользователя в realm Kosinov Keycloak с ролью ADMIN, либо включить в группу Administrators
- запустить приложение KosinovKeycloakApplication.java
- в браузере открыть адрес http://localhost:8081
- авторизоваться с административной учеткой realm Kosinov Keycloak
- проверить функционал приложения:
  - ограничение доступа к страницам согласно ролям ADMIN и USER
  - создание нового пользователя под пользователем с ролью администратора
  - изменение данных пользователя под пользователем с ролью администратора 
  - удаление пользователя под пользователем с ролью администратора
  - изменение собственного пароля для всех авторизованных пользователей

# История изменений

- `24.07.2024` **Первичная фиксация проекта**
- `29.07.2024` **Добавление модулей для Keycloak, изменение настроек**
- `05.08.2024` **Реализация отдельных Dockerfile для приложения и Keycloak (для включения драйвера postgres)**
- `06.08.2024` **Настройка и фиксация realm. Реализация конфигураций Spring security для Keycloak**
- `07.08.2024` **Рефакторинг функционала. Удаление ненужного кода. Реализация сервиса и web интерфейса для управления пользователями и проверки прав.
                 Изменение настроек realm. Доработка web интерфейса в части кода изменения пароля и создания пользователей.**
- `13.08.2024` **Добавление кода редактирования пользователей**
- `23.08.2024` **Добавлен код журналирования. Удален докер файл приложения.**