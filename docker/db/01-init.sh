#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
 
  CREATE DATABASE $WORKFLOW_DB_NAME;
  CREATE DATABASE $OBJECTIVE_DB_NAME;
  
EOSQL
