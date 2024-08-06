<h1 align="center">Курсовая работа - Сервис аутентификации на KeyCloak</h1>
Разработчик: Косинов Виктор, версия: 1.1

Приложение демонстрирует работу IDM KeyCloak

# Используемые в Docker образы 

| Образ                        | Версия |  Контейнер  |
|------------------------------|--------|-------------|
| `quay.io/keycloak/keycloak`  | latest |  keycloak   |
| `postgres`                   | latest |  postgres_k |
 
# Как загрузить quay.io/keycloak/keycloak

- docker pull quay.io/keycloak/keycloak

# История изменений

- `24.07.2024` **Первичная фиксация проекта**
- `29.07.2024` **Добавление модулей для Keycloak, изменение настроек**
- `05.08.2024` **Реализация отдельных Dockerfile для приложения и Keycloak (для включения драйвера postgres)**
- `06.08.2024` **Настройка и фиксация realm. Реализация конфигураций Spring security для Keycloak**