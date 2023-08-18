#!/bin/bash

set -e

psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    ALTER SYSTEM SET max_connections TO '100';

    CREATE DATABASE render_farm_db;

    CREATE USER connector WITH ENCRYPTED PASSWORD 'postgres';
    GRANT ALL PRIVILEGES ON DATABASE render_farm_db TO connector;

    GRANT connector TO postgres WITH ADMIN OPTION;
EOSQL
