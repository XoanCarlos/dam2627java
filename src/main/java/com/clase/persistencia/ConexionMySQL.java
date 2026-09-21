package com.clase.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* * Esta clase se encarga únicamente de establecer la conexión * entre 
*nuestra aplicación Java y la base de datos MySQL. 
* Es mejor tenerla separada del DAO porque así la conexión 
* queda centralizada y los DAO solo se ocupan de trabajar 
* con los datos. */ 

public class ConexionMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/bbdd";
    private static final String USUARIO = "user";
    private static final String PASSWORD = "abc123";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}