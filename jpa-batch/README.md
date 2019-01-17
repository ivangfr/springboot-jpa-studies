# jpa-batch

The idea of this module is to study how to insert/update/delete a set of records in batch (bulk)

## Start the Application

- Start the environment (explained in the main README)

- To start the application using `MySQL` (default configuration), run the following command in 
`/sprinboot-jpa-studies/jpa-batch` folder
```
mvn spring-boot:run
```

- If you want to use `PostgreSQL` run the same command, however informing the profile `postgres`
```
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

- The link to Swagger web page is: http://localhost:8081/swagger-ui.html

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

### How to check whether it is working or not

#### Enable MySQL logs

- Connect to `MySQL` inside docker container
```
docker exec -it studies-mysql bash -c 'mysql -uroot -psecret'
```

- Log all queries
```
SET GLOBAL general_log = 'ON';
SET global log_output = 'table';

SELECT event_time, command_type, SUBSTRING(argument,1,150) FROM mysql.general_log;
```

#### PostgreSQL

TODO

### Execution example using MySQL

1. Create partner

- Request:
```
curl -i -X POST "http://localhost:8081/api/partners" \
  -H "Content-Type: application/json" \
  -d '{"name": "partner1"}'
```

- Response:
```
HTTP/1.1 201
{"id":1,"name":"partner1","voucherCodes":null}
```

- MySQL logs
```
| Query | SET autocommit=0
| Query | select next_val as id_val from hibernate_sequence for update
| Query | update hibernate_sequence set next_val= 2 where next_val=1
| Query | commit
| Query | SET autocommit=1
| Query | select @@session.transaction_read_only
| Query | insert into partners (name, id) values ('partner1', 1)
| Query | commit
| Query | SET autocommit=1
```

2. Insert 15 voucher codes to partner with id 1 (batch_size = 10)

- Request:
```
curl -i -X POST "http://localhost:8081/api/partners/1/insertVoucherCodes" \
  -H "Content-Type: application/json" \
  -d '{ "voucherCodes": [ "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115" ]}'
```

- Response:
```
HTTP/1.1 201
15
```

- MySQL logs
```
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
...
| Query | SET autocommit=0
| Query | select next_val as id_val from hibernate_sequence for update
| Query | update hibernate_sequence set next_val= 17 where next_val=16
| Query | commit
| Query | SET autocommit=1
| Query | select @@session.transaction_read_only
| Query | insert into voucher_codes (code, deleted, partner_id, id) values ('110', 0, 1, 2),('111', 0, 1, 3),('101', 0, 1, 4),('112', 0, 1, 5),('102', 0, 1, 6),('113', 0, 1, 7),('103', 0, 1, 8),('114', 0, 1, 9)
| Query | select @@session.transaction_read_only
| Query | insert into voucher_codes (code, deleted, partner_id, id) values ('105', 0, 1, 12),('106', 0, 1, 13),('107', 0, 1, 14),('108', 0, 1, 15),('109', 0, 1, 16)
| Query | commit
| Query | SET autocommit=1
```

3. Soft delete (update `deleted` field to `true`) voucher codes of the partner with id 1 (batch_size = 10)

- Request:
```
curl -i -X PUT "http://localhost:8081/api/partners/1/softDeleteOldVoucherCodes"
```

- Response:
```
HTTP/1.1 200
15
```

- MySQL logs
```
| Query | SET autocommit=0
| Query | select @@session.transaction_read_only
| Query | select @@session.transaction_read_only
| Query | update voucher_codes set code='110', deleted=1, partner_id=1 where id=2;
| Query | update voucher_codes set code='111', deleted=1, partner_id=1 where id=3;
| Query | update voucher_codes set code='101', deleted=1, partner_id=1 where id=4;
| Query | update voucher_codes set code='112', deleted=1, partner_id=1 where id=5;
| Query | update voucher_codes set code='102', deleted=1, partner_id=1 where id=6;
| Query | update voucher_codes set code='113', deleted=1, partner_id=1 where id=7;
| Query | update voucher_codes set code='103', deleted=1, partner_id=1 where id=8;
| Query | update voucher_codes set code='114', deleted=1, partner_id=1 where id=9;
| Query | update voucher_codes set code='104', deleted=1, partner_id=1 where id=10;
| Query | update voucher_codes set code='115', deleted=1, partner_id=1 where id=11
| Query | select @@session.transaction_read_only
| Query | select @@session.transaction_read_only
| Query | update voucher_codes set code='105', deleted=1, partner_id=1 where id=12;
| Query | update voucher_codes set code='106', deleted=1, partner_id=1 where id=13;
| Query | update voucher_codes set code='107', deleted=1, partner_id=1 where id=14;
| Query | update voucher_codes set code='108', deleted=1, partner_id=1 where id=15;
| Query | update voucher_codes set code='109', deleted=1, partner_id=1 where id=16 
| Query | commit
| Query | SET autocommit=1
```

4. Hard delete voucher codes of the partner with id 1 (batch_size = 10)

- Request:
```
curl -i -X DELETE "http://localhost:8081/api/partners/1/hardDeleteOldVoucherCodes"
```

- Response:
```
HTTP/1.1 200 
15
```

- MySQL logs
```
| Query | SET autocommit=0
| Query | delete from voucher_codes where id=2 or id=3 or id=4 or id=5 or id=6 or id=7 or id=8 or id=9 or id=10 or id=11
| Query | delete from voucher_codes where id=12 or id=13 or id=14 or id=15 or id=16
| Query | commit
| Query | SET autocommit=1
```

### Execution example using PostgreSQL

TODO

## TODO

- Write a simulation using `Postgres`

## Reference

- https://clarkdo.js.org/spring/jpa/java/2017/09/14/79/