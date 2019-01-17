# springboot-jpa-studies

The goal of this project is to study `JPA Associations` (one-to-one, one-to-many and many-to-many) and
`JPA Batch Processing` (i.e, insert/update/delete a set of records in a single command) 

## Modules

#### [jpa-associations](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-associations#jpa-associations)
#### [jpa-batch](https://github.com/ivangfr/springboot-jpa-studies/tree/master/jpa-batch#jpa-batch)

## Start Environment

### Docker Compose

- Open one terminal

- In `/springboot-jpa-studies` root folder run
```
docker-compose up -d
```
>
> To stop and remove containers, networks and volumes
>```
>docker-compose down -v
>```

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