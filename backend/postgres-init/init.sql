-- 1. Création des utilisateurs dédiés
CREATE USER user_access_app WITH PASSWORD 'pwd_access_123';


-- 2. Création des bases de données avec leurs propriétaires respectifs
CREATE DATABASE db_user_access OWNER user_access_app;