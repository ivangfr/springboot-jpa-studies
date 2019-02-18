#!/usr/bin/env bash
docker exec -i studies-postgres psql -U postgres -d studiesdb < jpa-batch/init-postgres-database.sql