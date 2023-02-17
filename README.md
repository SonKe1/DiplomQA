# Дипломный проект по курсу "Тестировщик ПО"
## Задача:
Необходимо автоматизировать тестирование комплексного сервиса, взаимодействующего с СУБД и API Банка.
На локальном компьютере заранее должна быть установлена IntelliJ IDEA. Необходимо получить допуск к удаленной виртуальной машине или установить Docker на локальном компьютере.
* Планирование
* Автоматизация
* Подготовке отчётных документов по итогам автоматизированного тестирования
* Подготовка отчётных документов по итогам автоматизации

## Процедура запуска авто-тестов:

1. Склонировать на локальный репозиторий Дипломный проект и открыть его в приложении IntelliJ IDEA.

2. Запустить Docker Desktop.

3. Открыть проект в IntelliJ IDEA.

4. Запустить контейнеры в терминале: 
   docker-compose up -d

5. Запустить целевое приложение:

* для mySQL: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

* для postgresgl: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

6. Открыть второй терминал и запустить тесты:

* для mySQL: ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

* для postgresgl: ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

7. Создать отчёт Allure, открыть в браузере:

./gradlew allureServe

8. Для завершения работы выполнить команду: Ctrl+C

9. Для остановки работы контейнеров выполнить команду: docker-compose down
