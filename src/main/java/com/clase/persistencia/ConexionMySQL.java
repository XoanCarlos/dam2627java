
package com.clase.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String URL =
        "jdbc:mysql://localhost:3306/bbdd?useUnicode=true&characterEncoding=UTF-8";

    private static final String USUARIO = "user";
    private static final String PASSWORD = "abc123";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
