# translator

## Описание

Приложение для перевода текста с использованием Google Translation API. Оно также сохраняет информацию о запросах в реляционную базу данных MySQL.

## Требования

- Java 11+
- MySQL
- Maven

## Настройка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/yourusername/yourrepository.git

2.Настройте базу данных MySQL и создайте таблицу:

sql

CREATE TABLE translation_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ip_address VARCHAR(255),
    source_text TEXT,
    translated_text TEXT,
    request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

3. Настройте подключение к базе данных в src/main/resources/application.properties:

properties

spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=ROOT
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.My

4.Создайте и добавьте свой ключ API Google в файл TranslationService.java:
private static final String API_KEY = "YOUR_GOOGLE_CLOUD_API_KEY

5.Соберите и запустите приложение в IDE (Например Inellij) в файле TranslatorApplication.java


римеры запросов с использованием HTTPie:
Пример 1: Успешный перевод

http POST http://localhost:8080/api/translate \
    text="Hello world, this is my first program" \
    sourceLang="en" \
    targetLang="ru"

Ожидаемый ответ:

"Привет мир, это моя первая программа"

Пример 2: Неверный язык

http POST http://localhost:8080/api/translate \
    text="Hello world" \
    sourceLang="unknown" \
    targetLang="ru"

Ожидаемый ответ:

"Не найден язык исходного сообщения"

Пример 3: Ошибка доступа к ресурсу перевода

http POST http://localhost:8080/api/translate \
    text="Hello world" \
    sourceLang="en" \
    targetLang="ru"

Ожидаемый ответ:

"Ошибка доступа к ресурсу перевода
