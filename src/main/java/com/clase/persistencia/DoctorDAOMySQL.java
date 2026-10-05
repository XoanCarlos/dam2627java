package com.clase.persistencia;

import com.clase.modelo.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DoctorDAOMySQL implements DoctorDAO {
    // Implementación de la interfaz DoctorDAO para MySQL

    public void guardarDoctor(Doctor doctor) {

        String sql = "INSERT INTO doctores "
                + "(apeldoc, nomdoc, movildoc, "
                + " emaildoc, coledoc, espedoc) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, doctor.getApellidos());
            ps.setString(2, doctor.getNombre());
            ps.setString(3, doctor.getMovil());
            ps.setString(4, doctor.getEmail());
            ps.setBoolean(5, doctor.getColegiado());
            ps.setString(6, doctor.getEspecialidad());
            

            ps.executeUpdate();

            System.out.println("Doctor guardado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el doctor: " + e.getMessage());
        }
    }

   
    public List<Doctor> cargarDoctores() {

        List<Doctor> doctores = new ArrayList<>();

        // Solo obtenemos los campos que necesitamos para la tabla
        String sql = "SELECT * FROM doctores ORDER BY apeldoc, nomdoc";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Recorremos las filas obtenidas
            while (rs.next()) {

                Doctor doctor = new Doctor(
                        rs.getInt("iddoc"),
                        rs.getString("apeldoc"),
                        rs.getString("nomdoc"),
                        rs.getString("movildoc"),
                        rs.getString("espedoc"));
                                                              
                doctores.add(doctor);         
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar los doctores: " + e.getMessage());
        }
        //devuelve los doctores que hay en la bbdd
        return doctores ;
    }
}