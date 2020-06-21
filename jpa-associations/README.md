# springboot-jpa-studies
## `> jpa-associations`

The goal of this module is to study the three associations that JPA and Hibernate provide: `one-to-one`, `one-to-many` and `many-to-many`.

## Start application

> **Note:** before starting the application, the services present in `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- Open a terminal and navigate to `sprinboot-jpa-studies` root folder

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-associations -Dspring-boot.run.profiles=mysql
    ```

  - **Using PostgreSQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-associations -Dspring-boot.run.profiles=postgres
    ```

- Once the application is running, you can access its Swagger website at http://localhost:8080/swagger-ui.html

## Running Tests

- In a terminal, make sure you are in `sprinboot-jpa-studies` root folder

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```
    ./mvnw clean test --projects jpa-associations -DargLine="-Dspring.profiles.active=mysql-test"
    ```
  
  - **Using PostgreSQL**
    ```
    ./mvnw clean test --projects jpa-associations -DargLine="-Dspring.profiles.active=postgres-test"
    ```

## One to One

### [Shared PK](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/onetoone/sharedpk)

#### `persons 1 : 1 person_details`

- **persons**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **person_details**
  ```
  +-------------+--------------+------+-----+---------+-------+
  | Field       | Type         | Null | Key | Default | Extra |
  +-------------+--------------+------+-----+---------+-------+
  | description | varchar(255) | YES  |     | NULL    |       |
  | person_id   | bigint(20)   | NO   | PRI | NULL    |       |
  +-------------+--------------+------+-----+---------+-------+
  ```

### [Simple PK with Auto Increment](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/onetoone/simplepk)

#### `teams 1 : 1 team_details`

- **teams**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **team_details**
  ```
  +-------------+--------------+------+-----+---------+-------+
  | Field       | Type         | Null | Key | Default | Extra |
  +-------------+--------------+------+-----+---------+-------+
  | id          | bigint(20)   | NO   | PRI | NULL    |       |
  | description | varchar(255) | YES  |     | NULL    |       |
  | team_id     | bigint(20)   | YES  | MUL | NULL    |       |
  +-------------+--------------+------+-----+---------+-------+
  ```

## One to Many

### [Simple relationship](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/onetomany/simplepk)

#### `restaurants 1 : N dishes`

- **restaurants**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **dishes**
  ```
  +---------------+--------------+------+-----+---------+-------+
  | Field         | Type         | Null | Key | Default | Extra |
  +---------------+--------------+------+-----+---------+-------+
  | id            | bigint(20)   | NO   | PRI | NULL    |       |
  | name          | varchar(255) | NO   |     | NULL    |       |
  | restaurant_id | bigint(20)   | YES  | MUL | NULL    |       |
  +---------------+--------------+------+-----+---------+-------+
  ```

### [Relationship with Composite PK](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/onetomany/compositepk)

#### `players 1 : N weapons`

- **players**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **weapons**
  ```
  +-----------+--------------+------+-----+---------+-------+
  | Field     | Type         | Null | Key | Default | Extra |
  +-----------+--------------+------+-----+---------+-------+
  | id        | bigint(20)   | NO   | PRI | NULL    |       |
  | player_id | bigint(20)   | NO   | PRI | NULL    |       |
  | name      | varchar(255) | NO   |     | NULL    |       |
  +-----------+--------------+------+-----+---------+-------+
  ```

## Many to Many

### [Simple relationship](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/manytomany/simplerelationship)

#### `writers N : N books`

- **writers**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **books**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **books_writers**
  ```
  +-----------+------------+------+-----+---------+-------+
  | Field     | Type       | Null | Key | Default | Extra |
  +-----------+------------+------+-----+---------+-------+
  | book_id   | bigint(20) | NO   | PRI | NULL    |       |
  | writer_id | bigint(20) | NO   | PRI | NULL    |       |
  +-----------+------------+------+-----+---------+-------+
  ```

### [Relationship with Composite PK and extra column](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/manytomany/compositepkextracolumn)

#### `students N : N courses`

- **students**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **courses**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **courses_students**
  ```
  +-------------------+-------------+------+-----+---------+-------+
  | Field             | Type        | Null | Key | Default | Extra |
  +-------------------+-------------+------+-----+---------+-------+
  | course_id         | bigint(20)  | NO   | PRI | NULL    |       |
  | student_id        | bigint(20)  | NO   | PRI | NULL    |       |
  | grade             | smallint(6) | YES  |     | NULL    |       |
  | registration_date | datetime    | NO   |     | NULL    |       |
  +-------------------+-------------+------+-----+---------+-------+
  ```

### [Relationship with Simple PK and extra column](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations/src/main/java/com/mycompany/jpaassociations/manytomany/simplepkextracolumn)

#### `reviewer N : N article`

- **reviewers**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | name  | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **articles**
  ```
  +-------+--------------+------+-----+---------+-------+
  | Field | Type         | Null | Key | Default | Extra |
  +-------+--------------+------+-----+---------+-------+
  | id    | bigint(20)   | NO   | PRI | NULL    |       |
  | title | varchar(255) | NO   |     | NULL    |       |
  +-------+--------------+------+-----+---------+-------+
  ```

- **comments**
  ```
  +-------------+--------------+------+-----+---------+-------+
  | Field       | Type         | Null | Key | Default | Extra |
  +-------------+--------------+------+-----+---------+-------+
  | id          | bigint(20)   | NO   | PRI | NULL    |       |
  | text        | varchar(255) | NO   |     | NULL    |       |
  | article_id  | bigint(20)   | YES  | MUL | NULL    |       |
  | reviewer_id | bigint(20)   | YES  | MUL | NULL    |       |
  +-------------+--------------+------+-----+---------+-------+
  ```

## Useful Commands

- **MySQL**

  - Run `MySQL` interactive terminal (`mysql`), describe `persons` table and select all `persons`
    ```
    docker exec -it mysql mysql -uroot -psecret --database studiesdb
    describe persons;
    select * from persons;
    ```
    > Type `exit` to exit
    
- **PostgreSQL**

  - Run `Postgres` interactive terminal (`psql`), describe `persons` table and select all `persons`
    ```
    docker exec -it postgres psql -U postgres -d studiesdb
    \d persons
    select * from persons;
    ```
    > Type `\q` to exit

## References

- **JPA One to One**
  - https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate
  - https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-one-mapping-example/

- **JPA One to Many**
  - https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate
  - https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/

- **JPA Many to Many**
  - https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate
  - https://www.callicoder.com/hibernate-spring-boot-jpa-many-to-many-mapping-example/