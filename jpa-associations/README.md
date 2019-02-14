# jpa-associations

The goal of this module is to study the three associations that JPA and Hibernate provide: `one-to-one`, `one-to-many` and
`many-to-many`.

## Start the Application

- Start the environment (explained in the main README)

- To start the application using `MySQL` (default configuration), run the following command in 
`/sprinboot-jpa-studies/jpa-batch` folder
```
./mvnw spring-boot:run
```

- If you want to use `PostgreSQL` run the same command, however informing the profile `postgres`
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

- The link to Swagger web page is: http://localhost:8080/swagger-ui.html

## One to One

### Composite PK with Auto Increment

#### 1) `persons 1 : 1 person_details`

*persons*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*person_details*
```
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| id          | bigint(20)   | NO   | PRI | NULL    |       |
| person_id   | bigint(20)   | NO   | PRI | NULL    |       |
| description | varchar(255) | YES  |     | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
```

Hibernate is not generating an auto-increment value for `id` field of `person_details`. The exception:
```
{
  "timestamp": "2018-10-12T05:44:56.403+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "No part of a composite identifier may be null; nested exception is org.hibernate.HibernateException: No part of a composite identifier may be null",
  "path": "/api/persons/1/person-details"
}
```

### Simple PK with Auto Increment

#### 2) `teams 1 : 1 team_details`

*teams*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*team_details*
```
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| id          | bigint(20)   | NO   | PRI | NULL    |       |
| description | varchar(255) | YES  |     | NULL    |       |
| team_id     | bigint(20)   | YES  | MUL | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
```

This model works fine.

## One to Many

### Simple relationship

#### `restaurants 1 : N dishes`

*restaurants*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*dishes*
```
+---------------+--------------+------+-----+---------+-------+
| Field         | Type         | Null | Key | Default | Extra |
+---------------+--------------+------+-----+---------+-------+
| id            | bigint(20)   | NO   | PRI | NULL    |       |
| name          | varchar(255) | NO   |     | NULL    |       |
| restaurant_id | bigint(20)   | YES  | MUL | NULL    |       |
+---------------+--------------+------+-----+---------+-------+
```

This model works fine.

### Relationship with Composite PK

#### `players 1 : N weapons`

*players*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*weapons*
```
+-----------+--------------+------+-----+---------+-------+
| Field     | Type         | Null | Key | Default | Extra |
+-----------+--------------+------+-----+---------+-------+
| id        | bigint(20)   | NO   | PRI | NULL    |       |
| player_id | bigint(20)   | NO   | PRI | NULL    |       |
| name      | varchar(255) | NO   |     | NULL    |       |
+-----------+--------------+------+-----+---------+-------+
```

This model works fine.

## Many to Many

### Simple relationship

#### `writers N : N books`

*writers*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*books*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*books_writers*
```
+-----------+------------+------+-----+---------+-------+
| Field     | Type       | Null | Key | Default | Extra |
+-----------+------------+------+-----+---------+-------+
| book_id   | bigint(20) | NO   | PRI | NULL    |       |
| writer_id | bigint(20) | NO   | PRI | NULL    |       |
+-----------+------------+------+-----+---------+-------+
```

This model works fine.

### Relationship with Composite PK and extra column

#### `students N : N courses`

*students*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*courses*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*courses_students*
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

This model works fine.

### Relationship with Simple PK and extra column

#### `reviewer N : N article`

*reviewers*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| name  | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*articles*
```
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| title | varchar(255) | NO   |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

*comments*
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

This model works fine.

## References

**JPA One to One**
- https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate
- https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-one-mapping-example/

**JPA One to Many**
- https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate
- https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/

**JPA Many to Many**
- https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate
- https://www.callicoder.com/hibernate-spring-boot-jpa-many-to-many-mapping-example/