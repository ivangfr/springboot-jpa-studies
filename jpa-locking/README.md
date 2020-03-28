# springboot-jpa-studies
## `> jpa-locking`

The idea of this module is to study jpa locking.

## Start application

> **Note:** before starting the application, the services present in `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- Open a terminal and navigate to `sprinboot-jpa-studies` root folder

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-locking
    ```

  - **Using PostgreSQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-locking -Dspring-boot.run.profiles=postgres
    ```

- Once the application is running, you can access its Swagger website at http://localhost:8082/swagger-ui.html

## Multithreading Simulation 

In order to simulate multiple players collecting stars and redeeming them for lives at the same time, you can run the script `simulation.sh` or `PlayerControllerTest` test case
