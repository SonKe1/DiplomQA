# План автоматизации тестирования приложения 
**Цель:** Автоматизировать сценарии (как позитивные, так и негативные) покупки тура.
## 1. Перечень автоматизируемых сценариев
### 1.1 Позитивные сценарии
* Позитивный сценарий №1
 1.Открываем страницу http://localhost:8080/;
 1.Нажимаем кнопку "Купить";
 1.Ввести номер карты со статусом APPROVED: 4444 4444 4444 4441;
 1.Остальные поля заполнить корректными значениями;
 1.Нажимаем кнопку "Продолжить";
 1.Появляется сообщение "Успешно. Операция одобрена Банком."
* Позитивный сценарий №2
 1.Открываем страницу http://localhost:8080/;
 1.Нажимаем кнопку "Купить в кредит";
 1.Ввести номер карты со статусом APPROVED: 4444 4444 4444 4441;
 1.Остальные поля заполнить корректными значениями;
 1.Нажимаем кнопку "Продолжить";
 1.Появляется сообщение "Успешно. Операция одобрена Банком."
* Позитивный сценарий №3
 1.Открываем страницу http://localhost:8080/;
 1.Нажимаем кнопку "Купить";
 1.Ввести номер карты со статусом DECLINED: 4444 4444 4444 4442;
 1.Остальные поля заполнить корректными значениями;
 1.Нажимаем кнопку "Продолжить";
 1.Появляется сообщение "Ошибка!  Отказ в проведении операции."
* Позитивный сценарий №4
 1.Открываем страницу http://localhost:8080/;
 1.Нажимаем кнопку "Купить в кредит";
 1.Ввести номер карты со статусом DECLINED: 4444 4444 4444 4442;
 1.Остальные поля заполнить корректными значениями;
 1.Нажимаем кнопку "Продолжить";
 1.Появляется сообщение "Ошибка! Отказ в проведении операции."
### 1.2 Негативные сценарии
* Негативный сценарии №1
 1.Открываем страницу http://localhost:8080/;
 1.Нажимаем кнопку "Купить";
 1.Ввести номер карты : 2567 0004 4568 4442;
 1.Остальные поля заполнить не корректными значениями;
 1.Нажимаем кнопку "Продолжить";
 1.Появляется сообщение "Ошибка!  Отказ в проведении операции."
* Негативный сценарии №2
 1.Открываем страницу http://localhost:8080/;
 1.Нажимаем кнопку "Купить";
 1.Поле с  номером карты оставить незаполненным;
 1.Остальные поля оставить не заполненными;
 1.Нажимаем кнопку "Продолжить";
 1.Появляется сообщение "Ошибка!  Отказ в проведении операции."
##Тестирование полей формы
### 1.1 Позитивные сценарии
Корректные тестовые данные:
1. Поле "Номер карты":
     Цифры;
     Длина значения 16 цифр.
2. Поле "Месяц":
    Цифры;
    Диапазон значений от 01 до 12.
3. Поле "Год":
    Цифры;
    Диапазон значений от 20 до 25.
4. Поле "Владелец":
    Латиница (Sergey Petrovski);
    Длина значения от 2 до 64 символов;
    Допустимые спецсимволы: пробел, дефис.  
5. Поле "CVV":
    Цифры;
    Диапазон значений от 001 до 999.
    
### 1.2 Негативные тестовые сценарии
В негативных тестовых сценариях будут проведены следующие проверки:
Некорректные тестовые данные для негативных тестов:
1. Поле "Номер карты":
    Поле пустое;
    Длина значения менее 16 цифр;
    Номера карты нет в БД банка.
2. Поле "Месяц":
    Поле пустое;
    Состоит из одной цифры;
    Значение больше 12;
    Значение равно 00.
3. Поле "Год":
    Поле пустое;
    Состоит из одной цифры;
    Значение меньше текущего года (менее 20);
    Значение на 5+ лет больше текущего года (более 25);
    Значение равно 00.
4. Поле "Владелец":
    Поле пустое;
    Значение состоит из одного слова;
    Поле содержит кириллицу;
    Поле содержит цифры;
    Поле содержит спецсимволы кроме допустимых (пробел, дефис).
5. Поле "CVV":
    Поле пустое;
    Состоит из одной цифры;
    Состоит из двух цифр.
    
## 2. Перечень используемых инструментов с обоснованием выбора
1. Java 11  
Универсальный язык, позволяющий работать на большинстве ОС и различном оборудовании.
2. IntelliJ IDE 
Многофункциональная среда разработки, бесплатная. Хорошая интеграция в GitHub, широкая поддержка расширений и плагинов для тестирования.
3. Git  
Система контроля версий. Бесплатна, возможность параллельной разработки, хорошая интеграция с IntelliJ IDEA.
4. JUnit5 
Тестовый фреймворк, совместимый с JVM и IntelliJ IDEA, содержит все необходимые аннотации.
5. Gradle
Система сборки проекта. Имеет простой и понятный код, небольшого объема, в сравнении с Maven. Проще подключать внешние зависимости.
6. Lombok  
Плагин для создания аннотаций, заменяющих значительное количество однообразных конструкторов JAVA таких как getters, setters и пр.
7. Selenide  
Фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver. Подключение веб-драйвера происходит автоматически, простое написание кода тестов.
8. JavaFaker
Плагин для генерации случайных данных для тестов. Большое количество настроек, бесплатный, достаточная локализация для России.
9. Docker
Система контейризации. Будет использована для имитации работы IT-системы банка посредством развёртывания БД MySQL, PostgreSQL, запуск самого приложения через Node.js. 
10. Appveyor
Система CI. Непрерывный контроль интеграции кода. Бесплатный, простое подключение и настройка, удобная интеграция с GitHub.
11. Allure Report 
Система подготовки отчётов. Бесплатное решение. Хорошая информативная визуализация отчётов. Позволяет отслеживать данные на протяжении времени

## 3. Перечень и описание возможных рисков при автоматизации
1. Риск неработающего заявленного функционала приложения.
2. Сложности с запуском и настройкой SUT.
3. Сложности с настройкой авто-тестов при отсутствии уникальных css-селекторов в приложении.
4. Изменения в будущем приведут  к неработоспособности текущих авто-тестов.

## 4. Интервальная оценка с учётом рисков (в часах)
1. Планирование автоматизации тестирования - 5-9 часов
2. Написание кода тестов - 30-60 часов 
3. Подготовка отчётных документов по итогам автоматизированного тестирования - 15-20 часов
4. Подготовка отчётных документов по итогам автоматизации - 7-10 часов
5. План сдачи Дипломной работы
1. Готовность авто-тестов — через 12 рабочих дней после утверждения плана тестирования;
2. Подготовка отчетов по результатам прогона тестов — через 3 рабочих дня после прогона тестов;
3. Подготовка отчета по итогам автоматизации — через 3 рабочих дня после отчетов по прогону тестов.

