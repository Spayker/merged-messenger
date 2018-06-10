#!/bin/bash

# Immediately exits if any error occurs during the script
# execution. If not set, an error could occur and the
# script would continue its execution.
set -o errexit

# Creating an array that defines the environment variables
# that must be set. This can be consumed later via arrray
# variable expansion ${REQUIRED_ENV_VARS[@]}.
readonly REQUIRED_ENV_VARS=(
  "MEME_DB_USER"
  "MEME_DB_PASSWORD"
  "MEME_DB_NAME"
  "MEME_DB_SCHEME"
  "POSTGRES_PASSWORD")


# Main execution:
# - verifies if all environment variables are set
# - runs the SQL code to create user and database
main() {
  init_user_and_db
}

# Performs the initialization in the already-started PostgreSQL
init_user_and_db() {
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
     CREATE USER $MEME_DB_USER WITH PASSWORD '$MEME_DB_PASSWORD';
     CREATE DATABASE $MEME_DB_NAME
       WITH OWNER = $MEME_DB_USER
            ENCODING = 'UTF8'
            TABLESPACE = pg_default
            CONNECTION LIMIT = -1;
            \connect $MEME_DB_NAME;
     CREATE SCHEMA $MEME_DB_SCHEME AUTHORIZATION $MEME_DB_USER;
EOSQL
}
main "$@"