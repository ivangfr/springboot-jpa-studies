#!/usr/bin/env bash
docker exec -i studies-mysql mysql -uroot -psecret studiesdb < jpa-batch/init-mysql-database.sql