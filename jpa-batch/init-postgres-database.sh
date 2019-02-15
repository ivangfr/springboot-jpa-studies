#!/usr/bin/env bash
docker exec -i studies-postgres psql -U postgres -d studiesdb < init-postgres-database.sql