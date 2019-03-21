# jpa-locking

The idea of this module is to study jpa locking

# Start application

> **Note**: before starting the application, the services present in `docker-compose.yml` file must be up and running
as explained in the main README.

## Using MySQL

To start the application using `MySQL` (default configuration), run the following command in `sprinboot-jpa-studies` root folder
```
./mvnw spring-boot:run --projects jpa-locking
```

## Using PostgreSQL

If you want to use `PostgreSQL` run the same command, however informing the profile `postgres`
```
./mvnw spring-boot:run --projects jpa-locking -Dspring-boot.run.profiles=postgres
```
