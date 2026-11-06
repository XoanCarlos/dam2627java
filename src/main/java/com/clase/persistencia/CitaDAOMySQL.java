package com.clase.persistencia;

import com.clase.modelo.Cita;
import com.clase.modelo.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.List;


//import java.sql.ResultSet;
//import java.util.ArrayList;

public class CitaDAOMySQL implements CitaDAO {

     // Implementación de la interfaz DoctorDAO para MySQL

    public void guardarCita(Cita cita) {

        String sql = "INSERT INTO citas "
                + "(citadnipac, citaidoc, fechacita, "
                + " horacita, motivo, diagnostico, tratamiento, estadocita) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cita.getCitadnipac());
            ps.setInt(2, cita.getCitaiddoc());
            ps.setDate(3, java.sql.Date.valueOf(cita.getFechacita()));
            ps.setString(4, cita.getHoracita());
            ps.setString(5, cita.getMotivo());
            ps.setString(6, cita.getDiagnostico());
            ps.setString(7, cita.getTratamiento());
            ps.setString(8, cita.getEstadocita());
            
            ps.executeUpdate();

            System.out.println("Cita guardado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el doctor: " + e.getMessage());
        }
    }
    public void modificarDoctor(Integer id, Cita cita) {

        String sql = "UPDATE cita SET "
                + " citadnipac= ?, "
                + "citaiddoc  = ?, "
                + "fechacita = ?, "
                + "horacita = ?, "
                + "motivo = ?, "
                + "diagnostico = ? "
                + "tratamiento = ? "
                + "estadocita = ? "
                + "WHERE idcita= ?";
       

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setInt(1, cita.getCitaiddoc());
                ps.setString(2, cita.getCitadnipac());
                ps.setDate(3, java.sql.Date.valueOf(cita.getFechacita()));
                ps.setString(4, cita.getHoracita());
                ps.setString(5, cita.getMotivo());
                ps.setString(6, cita.getDiagnostico());
                ps.setString(7, cita.getTratamiento());
                     
                   
                // ID del doctor para modificar
                ps.setInt(8, id);

                ps.executeUpdate();

                System.out.println("Doctor modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el doctor: " + e.getMessage());
        }

        
        //cargarCitas();
    }
}
