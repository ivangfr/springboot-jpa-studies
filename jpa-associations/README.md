# springboot-jpa-studies
## `> jpa-associations`

The goal of this module is to study the three associations that JPA and Hibernate provide: `one-to-one`, `one-to-many` and `many-to-many`.

## Start application

> **Note:** before starting the application, the services present in `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- Open a terminal and navigate to `sprinboot-jpa-studies` root folder

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-associations
    ```

  - **Using PostgreSQL**
    ```
    ./mvnw clean spring-boot:run --projects jpa-associations -Dspring-boot.run.profiles=postgres
    ```

- Once the application is running, you can access its Swagger website at http://localhost:8080/swagger-ui.html

## One to One

### Composite PK with Auto Increment

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
  | id          | bigint(20)   | NO   | PRI | NULL    |       |
  | person_id   | bigint(20)   | NO   | PRI | NULL    |       |
  | description | varchar(255) | YES  |     | NULL    |       |
  +-------------+--------------+------+-----+---------+-------+
  ```

> **EXCEPTION:** Hibernate is not generating an auto-increment value for `id` field of `person_details`
> ```
> {
>   "timestamp": "2018-10-12T05:44:56.403+0000",
>   "status": 500,
>   "error": "Internal Server Error",
>   "message": "No part of a composite identifier may be null; nested exception is org.hibernate.HibernateException: No part of a composite identifier may be null",
>   "path": "/api/persons/1/person-details"
> }
> ```

### Simple PK with Auto Increment

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

### Simple relationship

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

### Relationship with Composite PK

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

### Simple relationship

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

### Relationship with Composite PK and extra column

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

### Relationship with Simple PK and extra column

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