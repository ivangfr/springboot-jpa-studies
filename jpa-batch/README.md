# springboot-jpa-studies
## `> jpa-batch`

The idea of this module is to study how to insert/update/delete a set of records in batch (bulk)

## Start application

> **Note:** before starting the application, the services present in `docker-compose.yml` file must be up and running as explained in the main README, see [Start Environment](https://github.com/ivangfr/springboot-jpa-studies#start-environment)

### Using MySQL

- Open a terminal and navigate to `sprinboot-jpa-studies` root folder

- To start the application run
  ```
  ./mvnw clean spring-boot:run --projects jpa-batch
  ```

> **Note:** if you want to initialize the database manually, start the application as following
>
> - Run the script below to create the tables
>   ```
>   ./jpa-batch/init-mysql-database.sh
>   ```
> - Start application, overwriting hibernate `ddl-auto` property
>   ```
>   ./mvnw clean spring-boot:run --projects jpa-batch -Dspring-boot.run.jvmArguments="-Dspring.jpa.hibernate.ddl-auto=none"
>   ```

### Using PostgreSQL

- Open a terminal and make sure you are in `sprinboot-jpa-studies` root folder

- To start the application run
  ```
  ./mvnw clean spring-boot:run --projects jpa-batch -Dspring-boot.run.profiles=postgres
  ```
  
> **Note:** if you want to initialize the database manually, start the application as following
> - Run the script below to create the tables
>   ```
>   ./jpa-batch/init-postgres-database.sh
>   ```
> - Start application, overwriting hibernate `ddl-auto` property
>   ```
>   ./mvnw clean spring-boot:run --projects jpa-batch -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=postgres -Dspring.jpa.hibernate.ddl-auto=none"
>   ```

### Swagger

Once the application is running, you can access its Swagger website at http://localhost:8081/swagger-ui.html

## Spring JPA Hibernate - JpaRepository (Batch)

### What to configure

- add `spring.jpa.properties.hibernate.jdbc.batch_size: 10` to `application.yml`
- append `&rewriteBatchedStatements=true` to `spring.datasource.url`
- use `saveAll` to save entities
  ```
  <S extends T> List<S> saveAll(Iterable<S> entities);
  ```
- use `deleteInBatch` to delete entities
  ```
  void deleteInBatch(Iterable<T> entities);
  ```

### Enable database logs

#### MySQL

- Run `MySQL` interactive terminal (`mysql`) inside docker container
  ```
  docker exec -it studies-mysql mysql -uroot -psecret --database=studiesdb
  ```

- Enable log for all queries
  ```
  SET GLOBAL general_log = 'ON';
  SET global log_output = 'table';
  
  SELECT event_time, command_type, SUBSTRING(argument,1,150) FROM mysql.general_log;
  ```
  
#### PostgreSQL

- Set `log_statement = 'all'` in `postgres/postgresql.conf` file

- Set `postgres/postgresql.conf` in `studies-postgres` service in `docker-compose.yml` using volumes mapping and `config_file` parameter

## Execution examples

### Using MySQL

- **Create partner**
  ```
  curl -i -X POST http://localhost:8081/api/partners \
    -H "Content-Type: application/json" \
    -d '{"name": "partner1"}'
  ```

  It should return
  ```
  HTTP/1.1 201
  {"id":1,"name":"partner1"}
  ```

  Logs
  ```
  | Query | SET autocommit=0
  | Query | insert into partners (name) values ('partner1')
  | Query | commit
  | Query | SET autocommit=1
  ```

- **Insert 15 voucher codes to partner with id 1 (batch_size = 10)**
  ```
  curl -i -X POST http://localhost:8081/api/partners/1/insertVoucherCodes \
    -H "Content-Type: application/json" \
    -d '{ "voucherCodes": [ "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115" ]}'
  ```

  It should return
  ```
  HTTP/1.1 201
  15
  ```

  Logs
  ```
  | Query | SET autocommit=0
  | Query | select partner0_.id as id1_0_0_, partner0_.name as name2_0_0_ from partners partner0_ where partner0_.id=1
  | Query | commit
  | Query | SET autocommit=1
  | Query | SELECT @@session.transaction_read_only
  | Query | set session transaction read write
  | Query | SET autocommit=0
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 1 where next_val=0
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 2 where next_val=1
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 3 where next_val=2
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 4 where next_val=3
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 5 where next_val=4
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 6 where next_val=5
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 7 where next_val=6
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 8 where next_val=7
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 9 where next_val=8
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 10 where next_val=9
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 11 where next_val=10
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 12 where next_val=11
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 13 where next_val=12
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 14 where next_val=13
  | Query | commit
  | Query | SET autocommit=1
  | Query | SET autocommit=0
  | Query | select next_val as id_val from hibernate_sequence for update
  | Query | update hibernate_sequence set next_val= 15 where next_val=14
  | Query | commit
  | Query | SET autocommit=1
  | Query | SELECT @@session.transaction_read_only
  | Query | insert into voucher_codes (code, deleted, partner_id, id) values ('110', 0, 1, 0),('111', 0, 1, 1),('101', 0, 1, 2),('112', 0, 1, 3),('102', 0, 1, 4),('113', 0, 1, 5),('103', 0, 1, 6),('114', 0, 1, 7),('104', 0, 1, 8),('115', 0, 1, 9)
  | Query | SELECT @@session.transaction_read_only
  | Query | insert into voucher_codes (code, deleted, partner_id, id) values ('105', 0, 1, 10),('106', 0, 1, 11),('107', 0, 1, 12),('108', 0, 1, 13),('109', 0, 1, 14)
  | Query | commit
  | Query | SET autocommit=1  
  ```

- **Soft delete (update `deleted` field to `true`) voucher codes of the partner with id 1 (batch_size = 10)**
  ```
  curl -i -X PUT http://localhost:8081/api/partners/1/softDeleteOldVoucherCodes
  ```

  It should return
  ```
  HTTP/1.1 200
  15
  ```

  Logs
  ```
  | Query | SET autocommit=0
  | Query | select partner0_.id as id1_0_0_, partner0_.name as name2_0_0_ from partners partner0_ where partner0_.id=1
  | Query | select vouchercod0_.id as id1_1_, vouchercod0_.code as code2_1_, vouchercod0_.deleted as deleted3_1_, vouchercod0_.partner_id as partner_4_1_ from voucher_codes vouchercod0_ where vouchercod0_.partner_id=1
  | Query | SELECT @@session.transaction_read_only
  | Query | SELECT @@session.transaction_read_only
  | Query | update voucher_codes set code='110', deleted=1, partner_id=1 where id=0;
  | Query | update voucher_codes set code='111', deleted=1, partner_id=1 where id=1;
  | Query | update voucher_codes set code='101', deleted=1, partner_id=1 where id=2;
  | Query | update voucher_codes set code='112', deleted=1, partner_id=1 where id=3;
  | Query | update voucher_codes set code='102', deleted=1, partner_id=1 where id=4;
  | Query | update voucher_codes set code='113', deleted=1, partner_id=1 where id=5;
  | Query | update voucher_codes set code='103', deleted=1, partner_id=1 where id=6;
  | Query | update voucher_codes set code='114', deleted=1, partner_id=1 where id=7;
  | Query | update voucher_codes set code='104', deleted=1, partner_id=1 where id=8;
  | Query | update voucher_codes set code='115', deleted=1, partner_id=1 where id=9 
  | Query | SELECT @@session.transaction_read_only
  | Query | SELECT @@session.transaction_read_only
  | Query | update voucher_codes set code='105', deleted=1, partner_id=1 where id=10;
  | Query | update voucher_codes set code='106', deleted=1, partner_id=1 where id=11;
  | Query | update voucher_codes set code='107', deleted=1, partner_id=1 where id=12;
  | Query | update voucher_codes set code='108', deleted=1, partner_id=1 where id=13;
  | Query | update voucher_codes set code='109', deleted=1, partner_id=1 where id=14
  | Query | commit                                                                  
  | Query | SET autocommit=1
  ```

- **Hard delete voucher codes of the partner with id 1 (batch_size = 10)**
  ```
  curl -i -X DELETE http://localhost:8081/api/partners/1/hardDeleteOldVoucherCodes
  ```

  It should return
  ```
  HTTP/1.1 200 
  15
  ```

  Logs
  ```
  | Query | set session transaction read only
  | Query | SET autocommit=0
  | Query | select partner0_.id as id1_0_0_, partner0_.name as name2_0_0_ from partners partner0_ where partner0_.id=1
  | Query | commit
  | Query | SET autocommit=1
  | Query | SELECT @@session.transaction_read_only
  | Query | set session transaction read write
  | Query | select vouchercod0_.id as id1_1_, vouchercod0_.code as code2_1_, vouchercod0_.deleted as deleted3_1_, vouchercod0_.partner_id as partner_4_1_ from voucher_codes vouchercod0_ where vouchercod0_.partner_id=1
  | Query | SET autocommit=0
  | Query | delete from voucher_codes where id=0 or id=1 or id=2 or id=3 or id=4 or id=5 or id=6 or id=7 or id=8 or id=9
  | Query | delete from voucher_codes where id=10 or id=11 or id=12 or id=13 or id=14
  | Query | commit
  | Query | SET autocommit=1
  ```

### Using PostgreSQL

- Open a new terminal and run
  ```
  docker logs studies-postgres -f
  ```

- **Create partner**
  ```
  curl -i -X POST http://localhost:8081/api/partners \
    -H "Content-Type: application/json" \
    -d '{"name": "partner1"}'
  ```

  It should return
  ```
  HTTP/1.1 201
  {"id":1,"name":"partner1"}
  ```

  Logs
  ```
  [125] LOG:  execute <unnamed>: BEGIN
  [125] LOG:  execute <unnamed>: insert into partners (name) values ($1)
  [125] DETAIL:  parameters: $1 = 'partner1'
  [125] LOG:  execute <unnamed>: select currval('partners_id_seq')
  [125] LOG:  execute S_1: COMMIT
  ```

- **Insert 15 voucher codes to partner with id 1 (batch_size = 10)**
  ```
  curl -i -X POST http://localhost:8081/api/partners/1/insertVoucherCodes \
    -H "Content-Type: application/json" \
    -d '{ "voucherCodes": [ "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115" ]}'
  ```

  It should return
  ```
  HTTP/1.1 201
  15
  ```

  Logs
  ```
  [125] LOG:  execute <unnamed>: SET SESSION CHARACTERISTICS AS TRANSACTION READ ONLY
  [125] LOG:  execute <unnamed>: BEGIN
  [125] LOG:  execute <unnamed>: select partner0_.id as id1_0_0_, partner0_.name as name2_0_0_ from partners partner0_ where partner0_.id=$1
  [125] DETAIL:  parameters: $1 = '1'
  [125] LOG:  execute S_1: COMMIT
  [125] LOG:  execute <unnamed>: SET SESSION CHARACTERISTICS AS TRANSACTION READ WRITE
  [125] LOG:  execute <unnamed>: BEGIN
  [125] LOG:  execute <unnamed>: select nextval ('hibernate_sequence')
  [125] LOG:  execute <unnamed>: select nextval ('hibernate_sequence')
  [125] LOG:  execute <unnamed>: select nextval ('hibernate_sequence')
  [125] LOG:  execute <unnamed>: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_2: select nextval ('hibernate_sequence')
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '110', $2 = 'f', $3 = '1', $4 = '1'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '111', $2 = 'f', $3 = '1', $4 = '2'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '101', $2 = 'f', $3 = '1', $4 = '3'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '112', $2 = 'f', $3 = '1', $4 = '4'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '102', $2 = 'f', $3 = '1', $4 = '5'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '113', $2 = 'f', $3 = '1', $4 = '6'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '103', $2 = 'f', $3 = '1', $4 = '7'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '114', $2 = 'f', $3 = '1', $4 = '8'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '104', $2 = 'f', $3 = '1', $4 = '9'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '115', $2 = 'f', $3 = '1', $4 = '10'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '105', $2 = 'f', $3 = '1', $4 = '11'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '106', $2 = 'f', $3 = '1', $4 = '12'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '107', $2 = 'f', $3 = '1', $4 = '13'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '108', $2 = 'f', $3 = '1', $4 = '14'
  [125] LOG:  execute S_3: insert into voucher_codes (code, deleted, partner_id, id) values ($1, $2, $3, $4)
  [125] DETAIL:  parameters: $1 = '109', $2 = 'f', $3 = '1', $4 = '15'
  [125] LOG:  execute S_1: COMMIT
  ```

- **Soft delete (update `deleted` field to `true`) voucher codes of the partner with id 1 (batch_size = 10)**
  ```
  curl -i -X PUT http://localhost:8081/api/partners/1/softDeleteOldVoucherCodes
  ```

  It should return
  ```
  HTTP/1.1 200
  15
  ```

  Logs
  ```
  [113] LOG:  execute <unnamed>: BEGIN
  [113] LOG:  execute <unnamed>: select partner0_.id as id1_0_0_, partner0_.name as name2_0_0_ from partners partner0_ where partner0_.id=$1
  [113] DETAIL:  parameters: $1 = '1'
  [113] LOG:  execute <unnamed>: select vouchercod0_.id as id1_1_, vouchercod0_.code as code2_1_, vouchercod0_.deleted as deleted3_1_, vouchercod0_.partner_id as partner_4_1_ from voucher_codes vouchercod0_ where vouchercod0_.partner_id=$1
  [113] DETAIL:  parameters: $1 = '1'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '110', $2 = 't', $3 = '1', $4 = '1'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '111', $2 = 't', $3 = '1', $4 = '2'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '101', $2 = 't', $3 = '1', $4 = '3'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '112', $2 = 't', $3 = '1', $4 = '4'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '102', $2 = 't', $3 = '1', $4 = '5'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '113', $2 = 't', $3 = '1', $4 = '6'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '103', $2 = 't', $3 = '1', $4 = '7'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '114', $2 = 't', $3 = '1', $4 = '8'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '104', $2 = 't', $3 = '1', $4 = '9'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '115', $2 = 't', $3 = '1', $4 = '10'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '105', $2 = 't', $3 = '1', $4 = '11'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '106', $2 = 't', $3 = '1', $4 = '12'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '107', $2 = 't', $3 = '1', $4 = '13'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '108', $2 = 't', $3 = '1', $4 = '14'
  [113] LOG:  execute S_4: update voucher_codes set code=$1, deleted=$2, partner_id=$3 where id=$4
  [113] DETAIL:  parameters: $1 = '109', $2 = 't', $3 = '1', $4 = '15'
  [113] LOG:  execute S_1: COMMIT
  ```

- **Hard delete voucher codes of the partner with id 1 (batch_size = 10)**
  ```
  curl -i -X DELETE http://localhost:8081/api/partners/1/hardDeleteOldVoucherCodes
  ```

  It should return
  ```
  HTTP/1.1 200 
  15
  ```

  Logs
  ```
  [113] LOG:  execute <unnamed>: SET SESSION CHARACTERISTICS AS TRANSACTION READ ONLY
  [113] LOG:  execute <unnamed>: BEGIN
  [113] LOG:  execute <unnamed>: select partner0_.id as id1_0_0_, partner0_.name as name2_0_0_ from partners partner0_ where partner0_.id=$1
  [113] DETAIL:  parameters: $1 = '1'
  [113] LOG:  execute S_1: COMMIT
  [113] LOG:  execute <unnamed>: SET SESSION CHARACTERISTICS AS TRANSACTION READ WRITE
  [113] LOG:  execute <unnamed>: select vouchercod0_.id as id1_1_, vouchercod0_.code as code2_1_, vouchercod0_.deleted as deleted3_1_, vouchercod0_.partner_id as partner_4_1_ from voucher_codes vouchercod0_ where vouchercod0_.partner_id=$1
  [113] DETAIL:  parameters: $1 = '1'
  [113] LOG:  execute <unnamed>: BEGIN
  [113] LOG:  execute <unnamed>: delete from voucher_codes where id=$1 or id=$2 or id=$3 or id=$4 or id=$5 or id=$6 or id=$7 or id=$8 or id=$9 or id=$10
  [113] DETAIL:  parameters: $1 = '1', $2 = '2', $3 = '3', $4 = '4', $5 = '5', $6 = '6', $7 = '7', $8 = '8', $9 = '9', $10 = '10'
  [113] LOG:  execute <unnamed>: delete from voucher_codes where id=$1 or id=$2 or id=$3 or id=$4 or id=$5
  [113] DETAIL:  parameters: $1 = '11', $2 = '12', $3 = '13', $4 = '14', $5 = '15'
  [113] LOG:  execute S_1: COMMIT
  ```

## Useful commands

- **MySQL**

  Dumping the database structure for all tables with no data
  ```
  docker exec -it studies-mysql mysqldump --no-data -uroot -psecret studiesdb
  ```
  
  Run `MySQL` interactive terminal (`mysql`), describe `partners` table and select all `partners`
  ```
  docker exec -it studies-mysql mysql -uroot -psecret --database=studiesdb
  DESCRIBE partners;
  SELECT * FROM partners;
  ```

  Type `exit` to exit

- **Postgres**

  Dumping the database structure for all tables with no data
  ```
  docker exec -it studies-postgres pg_dump -U postgres -s studiesdb;
  ```

  Run `Postgres` interactive terminal (`psql`), describe `partners` table and select all `partners`
  ```
  docker exec -it studies-postgres psql -U postgres -d studiesdb
  \dt partners
  SELECT * FROM PARTNERS;
  ```

  Type `\q` to exit

## Reference

- https://clarkdo.js.org/spring/jpa/java/2017/09/14/79/
- https://thoughts-on-java.org/jpa-generate-primary-keys/
- https://thoughts-on-java.org/5-things-you-need-to-know-when-using-hibernate-with-mysql/
