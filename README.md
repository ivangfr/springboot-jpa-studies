# springboot-jpa-studies

The goal of this project is to study `JPA Associations` (one-to-one, one-to-many and many-to-many), `JPA Batch Processing` (i.e, insert/update/delete a set of records in a single command), `JPA Locking` and `Datetime in JPA`.

## Modules

- ### [jpa-associations](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations#jpa-associations)
- ### [jpa-batch](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-batch#jpa-batch)
- ### [jpa-locking](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-locking#jpa-locking)
- ### [jpa-datetime](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-datetime#jpa-datetime)

## Prerequisites

- `Java 11+`
- `Docker`
- `Docker-Compose`

## Start Environment

- Open one terminal and inside `springboot-jpa-studies` root folder run
  ```
  docker-compose up -d
  ```

- Wait a little bit until all containers are Up (healthy). You can check their status running
  ```
  docker-compose ps
  ```

  You should see something like
  ```
        Name                    Command                 State                     Ports              
  ---------------------------------------------------------------------------------------------------
  studies-mysql      docker-entrypoint.sh mysqld     Up (healthy)   0.0.0.0:3306->3306/tcp, 33060/tcp
  studies-postgres   docker-entrypoint.sh postgres   Up (healthy)   0.0.0.0:5432->5432/tcp
  ```

## Shutdown

- In a terminal, make sure you are in `springboot-jpa-studies` root folder

- To stop and remove docker-compose containers, networks and volumes run
  ```
  docker-compose down -v
  ```

## Running tests

- In a terminal, make sure you are in `springboot-jpa-studies` root folder

- Make sure you don't have docker-compose `MySQL` and `PostgreSQL` containers running. During the tests, [`Testcontainers`](https://www.testcontainers.org/) starts automatically Docker containers of the databases before the tests begin and shuts the containers down when the tests finish. 

- Running test cases
    
  - For one specific module
    ```
    ./mvnw clean test --projects jpa-associations
    ./mvnw clean test --projects jpa-batch
    ./mvnw clean test --projects jpa-locking
    ```
    > **Note:** jpa-datetime producer and consumer don't have test cases

  - For all modules
    ```
    ./mvnw clean test
    ```

## TODO

- How to run `./mvnw clean test` selecting the database, `MySQL` or `PostgreSQL`?
