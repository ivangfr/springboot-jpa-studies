# springboot-jpa-studies
## `> jpa-locking`

The idea of this module is to study jpa locking.

## Start application

> **Note**: before starting the application, the services present in `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- Open a terminal and navigate to the `springboot-jpa-studies` root folder;

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-locking -Dspring-boot.run.profiles=mysql
    ```

  - **Using PostgreSQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-locking -Dspring-boot.run.profiles=postgres
    ```

- Once the application is running, you can access its Swagger website at http://localhost:8082/swagger-ui.html

## Shutdown

- To stop `jpa-locking` application, go to the terminal where it is running and press `Ctrl+C`.

- To stop and remove docker compose containers, network and volumes, please refer to [Shutdown Environment](https://github.com/ivangfr/springboot-jpa-studies#shutdown-environment) present in the main README.

## Running Tests

- In a terminal, make sure you are in the `springboot-jpa-studies` root folder;

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```
    ./mvnw clean test --projects jpa-locking -DargLine="-Dspring.profiles.active=mysql-test"
    ```
  
  - **Using PostgreSQL**
    ```
    ./mvnw clean test --projects jpa-locking -DargLine="-Dspring.profiles.active=postgres-test"
    ```

## Multithreading Simulation 

In order to simulate multiple players collecting stars and redeeming them for lives at the same time, you can run the script `simulation.sh` or `PlayerControllerTest` test case
