# springboot-jpa-studies
## `> jpa-locking`

The idea of this module is to study JPA locking.

## Start application

> **Note**: before starting the application, the services present in the `compose.yaml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- Open a terminal and navigate to the `springboot-jpa-studies` root folder;

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```bash
    ./mvnw clean spring-boot:run --projects jpa-locking -Dspring-boot.run.profiles=mysql
    ```

  - **Using PostgreSQL**
    ```bash
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
    ```bash
    ./mvnw clean test --projects jpa-locking -DargLine="-Dspring.profiles.active=mysql-test"
    ```
  
  - **Using PostgreSQL**
    ```bash
    ./mvnw clean test --projects jpa-locking -DargLine="-Dspring.profiles.active=postgres-test"
    ```

## Multithreading Simulation

To simulate multiple players collecting stars and redeeming them for lives simultaneously:

- **Shell script**: After starting the application (see [Start application](#start-application)), open **another terminal** in the root folder and run:

  ```bash
  ./jpa-locking/simulation.sh
  ```

  > **Note**: [`jq`](https://jqlang.org/) must be installed.

- **Test class**: Run `PlayerControllerTest` as described in [Running Tests](#running-tests).
