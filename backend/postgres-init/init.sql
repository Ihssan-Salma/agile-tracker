-- 1. Création des utilisateurs dédiés
CREATE USER user_access_app WITH PASSWORD 'pwd_access_123';
CREATE USER scrum_core_app WITH PASSWORD 'pwd_scrum_123';
CREATE USER time_reporting_app WITH PASSWORD 'pwd_time_123';

-- 2. Création des bases de données avec leurs propriétaires respectifs
CREATE DATABASE db_user_access OWNER user_access_app;
CREATE DATABASE db_scrum_core OWNER scrum_core_app;
CREATE DATABASE db_time_reporting OWNER time_reporting_app;