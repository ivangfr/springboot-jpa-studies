# springboot-jpa-studies
## `> jpa-datetime`

The goal of this example is to study how `Date`, `Time` and `DateTime` work with `Java`, `JPA/Hibernate` and `MySQL`. 

## Start applications

> **Note:** before starting the application, the services present in `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- **producer**

  - Open a terminal and navigate to `sprinboot-jpa-studies` root folder

  - To start the application run
    ```
    ./mvnw clean spring-boot:run --projects jpa-datetime/producer
    ```
  
    The `producer` Swagger link is http://localhost:8084/swagger-ui.html

- **consumer**

  - Open another terminal and make sure you are in `sprinboot-jpa-studies` root folder

  - To start the application run
    ```
    ./mvnw clean spring-boot:run --projects jpa-datetime/consumer
    ```

    The `consumer` Swagger link is http://localhost:8085/swagger-ui.html

## Sample of execution

- **Calling `producer` endpoint `/api/opening-hours`**

  ```
  curl -i -X POST http://localhost:8084/api/opening-hours \
    -H "Content-Type: application/json" \
    -d '{ "date": "2019-12-31", "begin": "00:00:00", "end": "23:59:59", "zoneId": "UTC"}'
  ```

  It should return
  ```
  HTTP/1.1 201
  {
    "openingHourJavaSql": {
      "id": 1,
      "date": "2019-12-31",
      "begin": "00:00:00",
      "end": "23:59:59",
      "dateTimeBegin": "2019-12-30T23:00:00.000+00:00",
      "dateTimeEnd": "2019-12-31T22:59:59.000+00:00"
    },
    "openingHourJavaTimeLocal": {
      "id": 1,
      "date": "2019-12-31",
      "begin": "00:00:00",
      "end": "23:59:59",
      "dateTimeBegin": "2019-12-31T00:00:00",
      "dateTimeEnd": "2019-12-31T23:59:59"
    },
    "openingHourJavaTimeZone": {
      "id": 1,
      "date": "2019-12-31",
      "begin": "00:00:00",
      "end": "23:59:59",
      "dateTimeBegin": "2019-12-31T00:00:00Z",
      "dateTimeEnd": "2019-12-31T23:59:59Z"
    }
  }
  ```

- **Calling `consumer` endpoint `/api/opening-hours`**

  ```
  curl -i http://localhost:8085/api/opening-hours
  ```

  It should return
  ```
  HTTP/1.1 200
  [
    {
      "openingHourJavaSql": {
        "id": 1,
        "date": "2019-12-31",
        "begin": "00:00:00",
        "end": "23:59:59",
        "dateTimeBegin": "2019-12-30T23:00:00.000+00:00",
        "dateTimeEnd": "2019-12-31T22:59:59.000+00:00"
      },
      "openingHourJavaTimeLocal": {
        "id": 1,
        "date": "2019-12-31",
        "begin": "00:00:00",
        "end": "23:59:59",
        "dateTimeBegin": "2019-12-31T00:00:00",
        "dateTimeEnd": "2019-12-31T23:59:59"
      },
      "openingHourJavaTimeZone": {
        "id": 1,
        "date": "2019-12-31",
        "begin": "00:00:00",
        "end": "23:59:59",
        "dateTimeBegin": "2019-12-31T01:00:00+01:00",
        "dateTimeEnd": "2020-01-01T00:59:59+01:00"
      }
    }
  ]
  ```

- **Checking `MySQL` database**

  - Access `MySQL` client inside `mysql` docker container
    ```
    docker exec -it -e MYSQL_PWD=secret mysql mysql -uroot --database studiesdb
    ```

  - Select rows in the tables
    ```
    mysql> select * from opening_hours_java_sql;
    +----+----------+------------+----------------------------+----------------------------+----------+
    | id | begin    | date       | date_time_begin            | date_time_end              | end      |
    +----+----------+------------+----------------------------+----------------------------+----------+
    |  1 | 00:00:00 | 2019-12-31 | 2019-12-30 23:00:00.000000 | 2019-12-31 22:59:59.000000 | 23:59:59 |
    +----+----------+------------+----------------------------+----------------------------+----------+
    
    mysql> select * from opening_hours_java_time_local;
    +----+----------+------------+----------------------------+----------------------------+----------+
    | id | begin    | date       | date_time_begin            | date_time_end              | end      |
    +----+----------+------------+----------------------------+----------------------------+----------+
    |  1 | 00:00:00 | 2019-12-31 | 2019-12-30 23:00:00.000000 | 2019-12-31 22:59:59.000000 | 23:59:59 |
    +----+----------+------------+----------------------------+----------------------------+----------+
    
    mysql> select * from opening_hours_java_time_zone;
    +----+----------+------------+----------------------------+----------------------------+----------+
    | id | begin    | date       | date_time_begin            | date_time_end              | end      |
    +----+----------+------------+----------------------------+----------------------------+----------+
    |  1 | 00:00:00 | 2019-12-31 | 2019-12-31 00:00:00.000000 | 2019-12-31 23:59:59.000000 | 23:59:59 |
    +----+----------+------------+----------------------------+----------------------------+----------+
    ```
