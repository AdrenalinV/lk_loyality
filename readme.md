## Запуск postgreSQL в docker

* необходимо установить DockerDesktop
* открыть консоль _win+r_ **cmd** _enter_
    * в консоле для проверки: **docker-compose version**
      вывод **Docker Compose version v2.2.3** (_ну или другая_)
      
* переходим в папку проекта: cd IdeaProjects\lk_loyality
    * проверяем наличие файла **docker-compose.yaml** (**dir** _выводит содержимое текущего каталога_)
    
* запускаем **docker-compose up -d** (-d  _для того что-бы не держать консоль_ )
    * **docker ps** ( просмотр запущенных контейнеров)
      
| CONTAINER ID | IMAGE         | COMMAND                | CREATED        | STATUS                  | PORTS                  | NAMES                   |
|--------------|---------------|------------------------|----------------|-------------------------|------------------------|-------------------------|
| 8654302fdca3 | postgres:13.3 | "docker-entrypoint.s…" | 15 minutes ago | Up 13 minutes (healthy) | 0.0.0.0:5432->5432/tcp | lk_loyality-postgres-1_ |
      
* для остановки **docker-compose down** 


## Параметры подключения к бд:

* **host**        -   localhost:5432
* **db_name**     -   lk_loyality
* **user**        -   gb_user
* **password**    -   gb_user

+ для админки можно использовать pgAdmin4 
  - пример настроек в файле pgAdmin_setup.png

Доска проекта: [Trello](https://trello.com/b/vz4LtS5Z/программа-лояльности)
Видео призентации проекта: [youTobe](https://youtu.be/3tE1_iesrTs)