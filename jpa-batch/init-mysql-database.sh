#!/usr/bin/env bash
docker exec -i studies-mysql mysql -uroot -psecret studiesdb < init-database.sql