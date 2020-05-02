CREATE DATABASE IF NOT EXISTS clinica_etsii;

ALTER DATABASE clinica_etsii
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

CREATE USER IF NOT EXISTS 'clinica_etsii' IDENTIFIED BY 'clinica_etsii';

GRANT ALL PRIVILEGES ON clinica_etsii.* TO 'clinica_etsii';