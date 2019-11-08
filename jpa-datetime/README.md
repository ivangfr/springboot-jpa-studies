# `jpa-datetime`

The goal of this example is to study how `Date`, `Time` and `DateTime` work with `Java`, `JPA/Hibernate` and `MySQL`. 

## Start applications

> Note. before starting the applications, the services present in `docker-compose.yml` file must be up and running
as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment).

### producer

In a terminal and inside `springboot-jpa-studies` run 
```
./mvnw spring-boot:run --projects jpa-datetime/producer
```

The `producer` Swagger link is http://localhost:8084/swagger-ui.html

### consumer

In a new terminal and inside `springboot-jpa-studies` run
```
./mvnw spring-boot:run --projects jpa-datetime/consumer
```

The `consumer` Swagger link is http://localhost:8085/swagger-ui.html

## Sample of execution

#### Calling `producer` endpoint `/api/opening-hours`

```
curl -i -X POST http://localhost:8084/api/opening-hours \
  -H "Content-Type: application/json" \
  -d '{ "date": "2019-12-31", "begin": "00:00:00", "end": "23:59:59", "zoneId": "Europe/Berlin"}'
```

It will return
```
HTTP/1.1 201
{
  "openingHourJavaSql": {
    "id": 1,
    "date": "2019-12-31",
    "begin": "00:00:00",
    "end": "23:59:59",
    "dateTimeBegin": "2019-12-30T23:00:00.000+0000",
    "dateTimeEnd": "2019-12-31T22:59:59.000+0000"
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
    "dateTimeBegin": "2019-12-31T00:00:00+01:00",
    "dateTimeEnd": "2019-12-31T23:59:59+01:00"
  }
}
```

#### Calling `consumer` endpoint `/api/opening-hours`

```
curl -i http://localhost:8085/api/opening-hours
```

It will return
```
HTTP/1.1 200

[
  {
    "openingHourJavaSql": {
      "id": 1,
      "date": "2019-12-30",
      "begin": "00:00:00",
      "end": "23:59:59",
      "dateTimeBegin": "2019-12-30T23:00:00.000+0000",
      "dateTimeEnd": "2019-12-31T22:59:59.000+0000"
    },
    "openingHourJavaTimeLocal": {
      "id": 1,
      "date": "2019-12-30",
      "begin": "00:00:00",
      "end": "23:59:59",
      "dateTimeBegin": "2019-12-31T00:00:00",
      "dateTimeEnd": "2019-12-31T23:59:59"
    },
    "openingHourJavaTimeZone": {
      "id": 1,
      "date": "2019-12-30",
      "begin": "00:00:00",
      "end": "23:59:59",
      "dateTimeBegin": "2019-12-31T00:00:00+01:00",
      "dateTimeEnd": "2019-12-31T23:59:59+01:00"
    }
  }
]
```

#### Checking `MySQL` database

- Access `MySQL` client inside `studies-mysql` docker container
```
docker exec -it studies-mysql mysql -uroot -psecret --database studiesdb
```

- Select rows in the tables
```
mysql> select * from opening_hours_java_sql;
+----+----------+------------+---------------------+---------------------+----------+
| id | begin    | date       | date_time_begin     | date_time_end       | end      |
+----+----------+------------+---------------------+---------------------+----------+
|  1 | 23:00:00 | 2019-12-30 | 2019-12-30 23:00:00 | 2019-12-31 22:59:59 | 22:59:59 |
+----+----------+------------+---------------------+---------------------+----------+

mysql> select * from opening_hours_java_time_local;
+----+----------+------------+---------------------+---------------------+----------+
| id | begin    | date       | date_time_begin     | date_time_end       | end      |
+----+----------+------------+---------------------+---------------------+----------+
|  1 | 23:00:00 | 2019-12-30 | 2019-12-30 23:00:00 | 2019-12-31 22:59:59 | 22:59:59 |
+----+----------+------------+---------------------+---------------------+----------+

mysql> select * from opening_hours_java_time_zone;
+----+----------+------------+---------------------+---------------------+----------+
| id | begin    | date       | date_time_begin     | date_time_end       | end      |
+----+----------+------------+---------------------+---------------------+----------+
|  1 | 23:00:00 | 2019-12-30 | 2019-12-30 23:00:00 | 2019-12-31 22:59:59 | 22:59:59 |
+----+----------+------------+---------------------+---------------------+----------+
```

## Problem

Setting the date `2019-12-31`, we have the value `2019-12-30` on the `date` field in `MySQL` database and also in the
response for the `consumer`. Is it a bug?
