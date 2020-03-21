# Тестирование авторизации

Имеющееся окружение (Windows 8.1, DockerToolbox) наложило определенные ограничения на выполнение задания и потребовало произвести некоторые дополнительные настройки, в частности в файле `docker-compose.yml`.

## Первый запуск приложение
Так, при первом запуске приложения необыходимо выполнить указанные действия:
1. Запустить контейнер.
2. Для подгрузки схемы БД в браузере необходимо ввести следующий адрес: http://192.168.99.100:8080/
3. В открывшемся окне ввести следующие данные:
    - Движок - MySQL
    - Сервер - <имя контейнера> (mysqlContainer)
    - Имя пользователя - app
    - Пароль - pass
    - База данных - app
4. Далее импортировать схему БД, указав путь к файлу `schema.sql`
5. После этого можно запустить приложение командой:

    `java -jar app-deadline.jar -P:jdbc.url="jdbc:mysql://192.168.99.100:3306/app" -P:jdbc.user=app -P:jdbc.password=pass`

## Повторный запуск приложения
Для повтороного запуска приложение с уже запущенны контейнером необходимо 
1. сначала остановив приложение компантов 'ctrl + c' 
2. затем запустить приложение повторно той же командой:

    `java -jar app-deadline.jar -P:jdbc.url="jdbc:mysql://192.168.99.100:3306/app" -P:jdbc.user=app -P:jdbc.password=pass`