Задание № 2 "Программа для работы с БД".

Выполнил: Богдан Ноздряков.

Приложение подключается к БД, выполняет один SELECT-запрос и выводит в консоль список департаментов
с количеством сотрудников.


Технологии
==========

- Java 17
- Maven
- JUnit 5
- PostgreSQL 16
- JDBC
- Docker Compose


Запуск приложения
=================

1. Создать в корне проекта файл .env

   Можно использовать значения из файла .env.example без изменений:

   DB_URL=jdbc:postgresql://localhost:5433/roster
   DB_PORT=5433
   DB_NAME=roster
   DB_USER=postgres
   DB_PASSWORD=postgres

   При необходимости можно изменить DB_PORT и соответствующий порт в DB_URL.
   Указанный порт должен быть свободен на хост-машине.

2. Запустить PostgreSQL из корня проекта командой:

   docker compose --env-file .env -f deploy/docker-compose.yml up -d

   При первом запуске автоматически создается база данных, выполняются SQL-скрипты создания схемы и добавляются
   тестовые данные.

3. Открыть проект в IDE и запустить метод:

   com.roster.Main.main()

После запуска приложение подключится к базе данных roster, выполнит SELECT-запрос и выведет статистику
по департаментам в консоль.


Управление базой данных
======================

Для запуска PostgreSQL используется Docker Compose.

Перед запуском необходимо создать файл .env в корне проекта.
Можно использовать значения из файла .env.example без изменений:

DB_URL=jdbc:postgresql://localhost:5433/roster
DB_PORT=5433
DB_NAME=roster
DB_USER=postgres
DB_PASSWORD=postgres

PostgreSQL внутри Docker-контейнера работает на порту 5432, а на хост-машине доступен через порт, указанный в DB_PORT.

Запускать базу данных нужно из корня проекта через команду:

docker compose --env-file .env -f deploy/docker-compose.yml up -d

Проверить состояние контейнера можно с помощью команды:

docker compose --env-file .env -f deploy/docker-compose.yml ps

При первом запуске автоматически выполняются SQL-скрипты:

deploy/postgres/init/01-schema.sql
deploy/postgres/init/02-seed.sql

Первый скрипт создает структуру базы данных, второй добавляет тестовые данные.

Для временной остановки контейнера PostgreSQL без его удаления:

docker compose --env-file .env -f deploy/docker-compose.yml stop

Для повторного запуска остановленного контейнера:

docker compose --env-file .env -f deploy/docker-compose.yml start

Для остановки и удаления контейнера PostgreSQL с сохранением данных:

docker compose --env-file .env -f deploy/docker-compose.yml down

Для остановки контейнера и удаления базы данных вместе с Docker volume:

docker compose --env-file .env -f deploy/docker-compose.yml down -v


Выполняемый SQL-запрос
======================

SQL-запрос находится в файле:

src/main/resources/sql/department/find_statistics.sql

SELECT
    d.name AS department_name,
    COUNT(ed.employee_id) AS employee_count
FROM departments d
LEFT OUTER JOIN employee_departments ed ON d.id = ed.department_id
GROUP BY d.id, d.name
ORDER BY d.name;

Запрос выводит все департаменты и количество сотрудников в каждом из них.
LEFT OUTER JOIN позволяет также вывести департаменты, в которых нет сотрудников.


Проверка результата
=================

Результат соответствует тестовым данным из файла:

deploy/postgres/init/02-seed.sql

В seed-данных сотрудники распределены по департаментам следующим образом:

- Development - 4 сотрудника
- Marketing - 2 сотрудника
- Sales - 1 сотрудник
- Support - 0 сотрудников

Поэтому вывод приложения:

Department                     Employees
------------------------------------------
Development                    4
Marketing                      2
Sales                          1
Support                        0

является ожидаемым.

Департамент Support также присутствует в результате с количеством 0, поскольку запрос использует
LEFT OUTER JOIN и выводит все департаменты, в том числе те, в которых нет сотрудников.


Структура проекта
=================

deploy/
    Docker Compose и SQL-скрипты инициализации PostgreSQL

src/main/java/com/roster/core/
    Прикладная логика приложения.
    Классы:
    - DepartmentService - получение статистики по департаментам
    - DepartmentStatistics - модель результата запроса

src/main/java/com/roster/persistence/
    Работа с базой данных.
    Содержит конфигурацию подключения, JDBC-инфраструктуру и репозитории.
    Классы:
    - DatabaseConfig / DatabaseConfigLoader - загрузка настроек БД
    - ConnectionFactory - создание JDBC-соединения
    - SqlQueryLoader - загрузка SQL-запросов из resources
    - DepartmentRepository - контракт доступа к данным департаментов
    - JdbcDepartmentRepository - JDBC-реализация репозитория

src/main/java/com/roster/presentation/
    Формирование и вывод результата в консоль.
    Классы:
    - DepartmentStatisticsPrinter - вывод статистики в табличном виде

src/main/resources/sql/
    SQL-запросы приложения

src/test/
    Unit-тест логики консольного вывода


Конфигурация
============

Параметры подключения к базе данных задаются в файле .env и загружаются
приложением при запуске.

Пароль и другие параметры подключения не хранятся непосредственно в исходном Java-коде.


Тесты
=====

Для запуска unit-тестов необходимо в корне выполнить команду:

mvn test

В проекте присутствует unit-тест для проверки логики консольного вывода.

Тест передает в DepartmentStatisticsPrinter заранее заданные данные:

- Development - 4 сотрудника
- Marketing - 2 сотрудника

Стандартный вывод System.out временно перенаправляется в буфер в памяти, после чего тест проверяет:

- наличие заголовков Department и Employees
- наличие строки-разделителя
- корректный вывод значения Development - 4
- корректный вывод значения Marketing - 2

Тест не обращается к базе данных и проверяет только работу DepartmentStatisticsPrinter.


Обработка ошибок и логирование
==============================

Приложение содержит обработку ошибок подключения и выполнения SQL-запроса.

Для логирования используется стандартный java.util.logging.
При возникновении ошибки выводится исходная причина исключения вместе со stack trace.