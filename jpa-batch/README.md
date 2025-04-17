# springboot-jpa-studies
## `> jpa-batch`

The purpose of this module is to study how to insert, update, or delete a set of records in batch (bulk)

## Spring JPA Hibernate - JpaRepository (Batch)

### What to configure

- Add `spring.jpa.properties.hibernate.jdbc.batch_size: 10` to `application.yml`
- Append `&rewriteBatchedStatements=true` to the `spring.datasource.url`
- Use `saveAll` to save entities
  ```text
  <S extends T> List<S> saveAll(Iterable<S> entities);
  ```
- Use `deleteInBatch` to delete entities
  ```text
  void deleteInBatch(Iterable<T> entities);
  ```

## Start application

> **Note**: before starting the application, the services present in the `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

- Open a terminal and navigate to the `springboot-jpa-studies` root folder;

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```bash
    ./mvnw clean spring-boot:run --projects jpa-batch -Dspring-boot.run.profiles=mysql
    ```
  
  - **Using PostgreSQL**
    ```bash
    ./mvnw clean spring-boot:run --projects jpa-batch -Dspring-boot.run.profiles=postgres
    ```

- Once the application is running, you can access its Swagger website at http://localhost:8081/swagger-ui.html

### Enable database logs

- **MySQL**

  - Run the `MySQL` interactive terminal (`mysql`) inside docker container
    ```bash
    docker exec -it -e MYSQL_PWD=secret mysql mysql -uroot --database studiesdb
    ```

  - Enable log for all queries
    ```bash
    SET GLOBAL general_log = 'ON';
    SET global log_output = 'table';
    ```
  
- **PostgreSQL**

  - Set `log_statement = 'all'` in `postgres/postgresql.conf` file

  - Set `postgres/postgresql.conf` in `postgres` service in `docker-compose.yml` using volumes mapping and `config_file` parameter

## Execution examples

### Using MySQL

> **Note**: In order to see the logs in `MySQL` interactive terminal, run the following `SELECT`
> ```sql
> SELECT event_time, command_type, CONVERT(SUBSTRING(argument,1,150) USING UTF8) FROM mysql.general_log;
> ```

In a terminal, run the following commands:

- **Create partner**
  ```bash
  curl -i -X POST http://localhost:8081/api/partners \
    -H "Content-Type: application/json" \
    -d '{"name": "partner1"}'
  ```

  It should return
  ```text
  HTTP/1.1 201
  {"id":1,"name":"partner1"}
  ```

  Log output
  ```text
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | insert into partners (name) values ('partner1')                                                                                                        |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1
  ```

- **Insert 15 voucher codes to partner with id 1 (batch_size = 10)**
  ```bash
  curl -i -X POST http://localhost:8081/api/partners/1/insertVoucherCodes \
    -H "Content-Type: application/json" \
    -d '{ "voucherCodes": [ "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115" ]}'
  ```

  It should return
  ```text
  HTTP/1.1 201
  15
  ```

  Log output
  ```text
  | Query        | SET SESSION TRANSACTION READ ONLY                                                                                                                      |
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=1                                                                                            |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1                                                                                                                                       |
  | Query        | SET SESSION TRANSACTION READ WRITE                                                                                                                     |
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | select next_val as id_val from voucher_codes_seq for update                                                                                            |
  | Query        | update voucher_codes_seq set next_val= 51 where next_val=1                                                                                             |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1                                                                                                                                       |
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | select next_val as id_val from voucher_codes_seq for update                                                                                            |
  | Query        | update voucher_codes_seq set next_val= 101 where next_val=51                                                                                           |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1                                                                                                                                       |
  | Query        | SELECT @@session.transaction_read_only                                                                                                                 |
  | Query        | insert into voucher_codes (code,deleted,partner_id,id) values ('110',0,1,1),('111',0,1,2),('101',0,1,3),('112',0,1,4),('102',0,1,5),('113',0,1,6),('10 |
  | Query        | SELECT @@session.transaction_read_only                                                                                                                 |
  | Query        | insert into voucher_codes (code,deleted,partner_id,id) values ('105',0,1,11),('106',0,1,12),('107',0,1,13),('108',0,1,14),('109',0,1,15)               |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1
  ```

- **Soft delete (update `deleted` field to `true`) voucher codes of the partner with id 1 (batch_size = 10)**
  ```bash
  curl -i -X PUT http://localhost:8081/api/partners/1/softDeleteOldVoucherCodes
  ```

  It should return
  ```text
  HTTP/1.1 200
  15
  ```

  Log output
  ```text
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=1                                                                                            |
  | Query        | select vc1_0.id,vc1_0.code,vc1_0.deleted,vc1_0.partner_id from voucher_codes vc1_0 where vc1_0.partner_id=1                                            |
  | Query        | SELECT @@session.transaction_read_only                                                                                                                 |
  | Query        | SELECT @@session.transaction_read_only                                                                                                                 |
  | Query        | update voucher_codes set code='110',deleted=1,partner_id=1 where id=1;                                                                                 |
  | Query        | update voucher_codes set code='111',deleted=1,partner_id=1 where id=2;                                                                                 |
  | Query        | update voucher_codes set code='101',deleted=1,partner_id=1 where id=3;                                                                                 |
  | Query        | update voucher_codes set code='112',deleted=1,partner_id=1 where id=4;                                                                                 |
  | Query        | update voucher_codes set code='102',deleted=1,partner_id=1 where id=5;                                                                                 |
  | Query        | update voucher_codes set code='113',deleted=1,partner_id=1 where id=6;                                                                                 |
  | Query        | update voucher_codes set code='103',deleted=1,partner_id=1 where id=7;                                                                                 |
  | Query        | update voucher_codes set code='114',deleted=1,partner_id=1 where id=8;                                                                                 |
  | Query        | update voucher_codes set code='104',deleted=1,partner_id=1 where id=9;                                                                                 |
  | Query        | update voucher_codes set code='115',deleted=1,partner_id=1 where id=10                                                                                 |
  | Query        | SELECT @@session.transaction_read_only                                                                                                                 |
  | Query        | SELECT @@session.transaction_read_only                                                                                                                 |
  | Query        | update voucher_codes set code='105',deleted=1,partner_id=1 where id=11;                                                                                |
  | Query        | update voucher_codes set code='106',deleted=1,partner_id=1 where id=12;                                                                                |
  | Query        | update voucher_codes set code='107',deleted=1,partner_id=1 where id=13;                                                                                |
  | Query        | update voucher_codes set code='108',deleted=1,partner_id=1 where id=14;                                                                                |
  | Query        | update voucher_codes set code='109',deleted=1,partner_id=1 where id=15                                                                                 |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1
  ```

- **Hard delete voucher codes of the partner with id 1 (batch_size = 10)**
  ```bash
  curl -i -X DELETE http://localhost:8081/api/partners/1/hardDeleteOldVoucherCodes
  ```

  It should return
  ```text
  HTTP/1.1 200 
  15
  ```

  Log output
  ```text
  | Query        | SET SESSION TRANSACTION READ ONLY                                                                                                                      |
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=1                                                                                            |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1                                                                                                                                       |
  | Query        | SET SESSION TRANSACTION READ WRITE                                                                                                                     |
  | Query        | select vc1_0.id,vc1_0.code,vc1_0.deleted,vc1_0.partner_id from voucher_codes vc1_0 where vc1_0.partner_id=1                                            |
  | Query        | SET autocommit=0                                                                                                                                       |
  | Query        | delete vc1_0 from voucher_codes vc1_0 where vc1_0.id=1 or vc1_0.id=2 or vc1_0.id=3 or vc1_0.id=4 or vc1_0.id=5 or vc1_0.id=6 or vc1_0.id=7 or vc1_0.id |
  | Query        | delete vc1_0 from voucher_codes vc1_0 where vc1_0.id=11 or vc1_0.id=12 or vc1_0.id=13 or vc1_0.id=14 or vc1_0.id=15                                    |
  | Query        | COMMIT                                                                                                                                                 |
  | Query        | SET autocommit=1
  ```

### Using PostgreSQL

In order to see `PostgreSQL` logs, open a new terminal and run
```bash
docker logs -f postgres
```

In another terminal, run the following commands

- **Create partner**
  ```bash
  curl -i -X POST http://localhost:8081/api/partners \
    -H "Content-Type: application/json" \
    -d '{"name": "partner1"}'
  ```

  It should return
  ```text
  HTTP/1.1 201
  {"id":1,"name":"partner1"}
  ```

  Log output
  ```text
  [73] LOG:  execute <unnamed>: BEGIN
  [73] LOG:  execute <unnamed>: insert into partners (name) values ($1)
  RETURNING *
  [73] DETAIL:  Parameters: $1 = 'partner1'
  [73] LOG:  execute S_1: COMMIT
  ```

- **Insert 15 voucher codes to partner with id 1 (batch_size = 10)**
  ```bash
  curl -i -X POST http://localhost:8081/api/partners/1/insertVoucherCodes \
    -H "Content-Type: application/json" \
    -d '{ "voucherCodes": [ "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115" ]}'
  ```

  It should return
  ```text
  HTTP/1.1 201
  15
  ```

  Log output
  ```text
  [73] LOG:  execute <unnamed>: BEGIN READ ONLY
  [73] LOG:  execute <unnamed>: select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute S_1: COMMIT
  [73] LOG:  execute <unnamed>: BEGIN
  [73] LOG:  execute <unnamed>: select nextval('voucher_codes_seq')
  [73] LOG:  execute <unnamed>: select nextval('voucher_codes_seq')
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '110', $2 = 'f', $3 = '1', $4 = '1'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '111', $2 = 'f', $3 = '1', $4 = '2'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '101', $2 = 'f', $3 = '1', $4 = '3'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '112', $2 = 'f', $3 = '1', $4 = '4'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '102', $2 = 'f', $3 = '1', $4 = '5'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '113', $2 = 'f', $3 = '1', $4 = '6'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '103', $2 = 'f', $3 = '1', $4 = '7'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '114', $2 = 'f', $3 = '1', $4 = '8'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '104', $2 = 'f', $3 = '1', $4 = '9'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '115', $2 = 'f', $3 = '1', $4 = '10'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '105', $2 = 'f', $3 = '1', $4 = '11'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '106', $2 = 'f', $3 = '1', $4 = '12'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '107', $2 = 'f', $3 = '1', $4 = '13'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '108', $2 = 'f', $3 = '1', $4 = '14'
  [73] LOG:  execute S_2: insert into voucher_codes (code,deleted,partner_id,id) values ($1,$2,$3,$4)
  [73] DETAIL:  Parameters: $1 = '109', $2 = 'f', $3 = '1', $4 = '15'
  [73] LOG:  execute S_1: COMMIT
  ```

- **Soft delete (update `deleted` field to `true`) voucher codes of the partner with id 1 (batch_size = 10)**
  ```bash
  curl -i -X PUT http://localhost:8081/api/partners/1/softDeleteOldVoucherCodes
  ```

  It should return
  ```text
  HTTP/1.1 200
  15
  ```

  Log output
  ```text
  [73] LOG:  execute <unnamed>: BEGIN READ ONLY
  [73] LOG:  execute <unnamed>: select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute S_1: COMMIT
  [73] LOG:  execute <unnamed>: BEGIN
  [73] LOG:  execute <unnamed>: select vc1_0.id,vc1_0.code,vc1_0.deleted,vc1_0.partner_id from voucher_codes vc1_0 where vc1_0.partner_id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute S_2: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '110', $2 = 't', $3 = '1', $4 = '1'
  [73] LOG:  execute <unnamed>: BEGIN
  [73] LOG:  execute <unnamed>: select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute <unnamed>: select vc1_0.id,vc1_0.code,vc1_0.deleted,vc1_0.partner_id from voucher_codes vc1_0 where vc1_0.partner_id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '110', $2 = 't', $3 = '1', $4 = '1'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '111', $2 = 't', $3 = '1', $4 = '2'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '101', $2 = 't', $3 = '1', $4 = '3'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '112', $2 = 't', $3 = '1', $4 = '4'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '102', $2 = 't', $3 = '1', $4 = '5'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '113', $2 = 't', $3 = '1', $4 = '6'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '103', $2 = 't', $3 = '1', $4 = '7'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '114', $2 = 't', $3 = '1', $4 = '8'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '104', $2 = 't', $3 = '1', $4 = '9'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '115', $2 = 't', $3 = '1', $4 = '10'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '105', $2 = 't', $3 = '1', $4 = '11'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '106', $2 = 't', $3 = '1', $4 = '12'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '107', $2 = 't', $3 = '1', $4 = '13'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '108', $2 = 't', $3 = '1', $4 = '14'
  [73] LOG:  execute S_3: update voucher_codes set code=$1,deleted=$2,partner_id=$3 where id=$4
  [73] DETAIL:  Parameters: $1 = '109', $2 = 't', $3 = '1', $4 = '15'
  [73] LOG:  execute S_1: COMMIT
  ```

- **Hard delete voucher codes of the partner with id 1 (batch_size = 10)**
  ```bash
  curl -i -X DELETE http://localhost:8081/api/partners/1/hardDeleteOldVoucherCodes
  ```

  It should return
  ```text
  HTTP/1.1 200 
  15
  ```

  Log output
  ```text
  [73] LOG:  execute <unnamed>: BEGIN READ ONLY
  [73] LOG:  execute <unnamed>: select p1_0.id,p1_0.name from partners p1_0 where p1_0.id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute S_1: COMMIT
  [73] LOG:  execute <unnamed>: select vc1_0.id,vc1_0.code,vc1_0.deleted,vc1_0.partner_id from voucher_codes vc1_0 where vc1_0.partner_id=$1
  [73] DETAIL:  Parameters: $1 = '1'
  [73] LOG:  execute <unnamed>: BEGIN
  [73] LOG:  execute <unnamed>: delete from voucher_codes vc1_0 where vc1_0.id=$1 or vc1_0.id=$2 or vc1_0.id=$3 or vc1_0.id=$4 or vc1_0.id=$5 or vc1_0.id=$6 or vc1_0.id=$7 or vc1_0.id=$8 or vc1_0.id=$9 or vc1_0.id=$10
  [73] DETAIL:  Parameters: $1 = '1', $2 = '2', $3 = '3', $4 = '4', $5 = '5', $6 = '6', $7 = '7', $8 = '8', $9 = '9', $10 = '10'
  [73] LOG:  execute <unnamed>: delete from voucher_codes vc1_0 where vc1_0.id=$1 or vc1_0.id=$2 or vc1_0.id=$3 or vc1_0.id=$4 or vc1_0.id=$5
  [73] DETAIL:  Parameters: $1 = '11', $2 = '12', $3 = '13', $4 = '14', $5 = '15'
  [73] LOG:  execute S_1: COMMIT
  ```

## Useful Commands

- **MySQL**

  - Dump the database structure for all tables without data
    ```bash
    docker exec -it -e MYSQL_PWD=secret mysql mysqldump --no-data -uroot studiesdb
    ```
  
  - Run `MySQL` interactive terminal (`mysql`), describe `partners` table and select all `partners`
    ```bash
    docker exec -it -e MYSQL_PWD=secret mysql mysql -uroot --database studiesdb
    describe partners;
    select * from partners;
    ```
    > Type `exit` to exit

- **Postgres**

  - Dumping the database structure for all tables with no data
    ```bash
    docker exec -it postgres pg_dump -U postgres -s studiesdb;
    ```

  - Run `Postgres` interactive terminal (`psql`), describe `partners` table and select all `partners`
    ```bash
    docker exec -it postgres psql -U postgres -d studiesdb
    \d partners
    select * from partners;
    ```
    > Type `\q` to exit

## Shutdown

- To stop `jpa-batch` application, go to the terminal where it is running and press `Ctrl+C`.

- To stop and remove docker compose containers, network and volumes, please refer to [Shutdown Environment](https://github.com/ivangfr/springboot-jpa-studies#shutdown-environment) present in the main README.

## Running Tests

- In a terminal, make sure you are in the `springboot-jpa-studies` root folder;

- You can use `MySQL` or `PostgreSQL`

  - **Using MySQL**
    ```bash
    ./mvnw clean test --projects jpa-batch -DargLine="-Dspring.profiles.active=mysql-test"
    ```

  - **Using PostgreSQL**
    ```bash
    ./mvnw clean test --projects jpa-batch -DargLine="-Dspring.profiles.active=postgres-test"
    ```

## Reference

- https://clarkdo.js.org/spring/jpa/java/2017/09/14/79/
- https://thorben-janssen.com/jpa-generate-primary-keys/
- https://thorben-janssen.com/5-things-you-need-to-know-when-using-hibernate-with-mysql/
