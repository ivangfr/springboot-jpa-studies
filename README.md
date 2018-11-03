# springboot-jpa-studies

The idea of this project is to study and jpa associations and jpa batch processing

## Modules

### jpa-associations

The three associations that JPA and Hibernate provide: `one-to-one`, `one-to-many` and `many-to-many`. We will be
testing two relational databases: [MySQL](https://www.mysql.com) and [PostgreSQL](https://www.postgresql.org).

### jpa-batch

Batch (bulk) insert/update/delete of records

## Start Environment

### Docker Compose

- Open one terminal

- In `/springboot-jpa-studies` root folder run
```
docker-compose up -d
```
>
> To stop and remove containers, networks, images, and volumes
>```
>docker-compose down -v
>```

- Wait a little bit until all containers are Up (healthy). You can check their status running
```
docker-compose ps
```