/* 
Este fichero contiene la creación de la bbdd y la creación de las tablas y sus relaciones 
pero no contiene la inserción de datos, ya que esto se hace en otro fichero llamado "insert.sql".

Como usamos phpmyadmin no es necesario crear la bbdd desde el código, ya que phpmyadmin 
nos permite crearla de manera visual.

CREATE DATABASE bbdd; 
USE bbdd; 
CREATE TABLE pacientes ( 
dni VARCHAR(9) PRIMARY KEY, 
apellidos VARCHAR(100), 
nombre VARCHAR(50), movil VARCHAR(9), 
email VARCHAR(100), 
nacimiento DATE, 
direccion VARCHAR(150), 
provincia VARCHAR(50), 
municipio VARCHAR(50) );


*/