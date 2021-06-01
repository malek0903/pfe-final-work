CREATE USER developer WITH PASSWORD 'Mbyz0903' CREATEDB;
CREATE DATABASE test
    WITH 
    OWNER = pfe-db
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
