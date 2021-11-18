# springboot-jpa-studies

The goal of this project is to study `JPA Associations` (one-to-one, one-to-many and many-to-many), `JPA Batch Processing` (i.e, insert/update/delete a set of records in a single command), `JPA Locking` and `Datetime in JPA`.

## Modules

- ### [jpa-associations](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations#jpa-associations)
- ### [jpa-batch](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-batch#jpa-batch)
- ### [jpa-locking](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-locking#jpa-locking)
- ### [jpa-datetime](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-datetime#jpa-datetime)

## Prerequisites

- [`Java 11+`](https://www.oracle.com/java/technologies/downloads/#java11)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)

## Start Environment

- Open one terminal and inside `springboot-jpa-studies` root folder run
  ```
  docker-compose up -d
  ```

- Wait for Docker containers to be up and running. To check it, run
  ```
  docker-compose ps
  ```

## Useful Commands

- **MySQL**

  - Run `MySQL` interactive terminal (`mysql`)
    ```
    docker exec -it mysql mysql -uroot -psecret --database studiesdb
    ```
    > Type `exit` to exit
    
- **PostgreSQL**

  - Run `Postgres` interactive terminal (`psql`)
    ```
    docker exec -it postgres psql -U postgres -d studiesdb
    ```
    > Type `\q` to exit

## Shutdown

- In a terminal, make sure you are in `springboot-jpa-studies` root folder

- To stop and remove docker-compose containers, networks and volumes run
  ```
  docker-compose down -v
  ```

## Running tests

- In a terminal, make sure you are in `springboot-jpa-studies` root folder  

- The commands below will run the test cases of all modules. In order to run just the tests of a specific module check the module README.

  During the tests, [`Testcontainers`](https://www.testcontainers.org/) starts automatically Docker containers of the databases before the tests begin and shuts the containers down when the tests finish.

  > **Note:** jpa-datetime producer and consumer don't have test cases

  - **Using MySQL**
    ```
    ./mvnw clean test -DargLine="-Dspring.profiles.active=mysql-test"
    ```

  - **Using PostgreSQL**
    ```
    ./mvnw clean test -DargLine="-Dspring.profiles.active=postgres-test"
    ```
    > **Warning:** jpa-locking test is failing. The problem is while calling `getAvailableLife` in `redeemStars` of `PlayerServiceImpl` class. It's always returning a `life` with id `1`. It's different when using `mysql-test` profile that returns different ids.

## TODO

- Fix `jpa-locking` tests when using PostgreSQL
