package com.clase.persistencia;

import com.clase.modelo.Cita;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


//import java.sql.ResultSet;
//import java.util.ArrayList;

public class CitaDAOMySQL implements CitaDAO {

     // Implementación de la interfaz DoctorDAO para MySQL

    public void guardarCita(Cita cita) {

        String sql = "INSERT INTO citas "
                + "(citadnipac, citaiddoc, fechacita, "
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

            System.out.println("Cita Guardada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el doctor: " + e.getMessage());
        }
    }
    
    public void modificarCita(Integer id, Cita cita) {

        String sql = "UPDATE citas SET "
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

                ps.setString(1, cita.getCitadnipac());
                ps.setInt(2, cita.getCitaiddoc());
                ps.setDate(3, java.sql.Date.valueOf(cita.getFechacita()));
                ps.setString(4, cita.getHoracita());
                ps.setString(5, cita.getMotivo());
                ps.setString(6, cita.getDiagnostico());
                ps.setString(7, cita.getTratamiento());
                ps.setString(8, cita.getEstadocita());
                ps.setInt(9, id);

                ps.executeUpdate();

                ps.executeUpdate();

                System.out.println("Cita modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el cita: " + e.getMessage());
        }
     
        //cargarCitas(dnipac);
    }

    public List<Cita> cargarCitaspaciente(String dnipac) {

        List<Cita> citas = new ArrayList<>();
        // necesitamos obtener el nombre del doctor y la especialidad, 
        // por lo que hacemos un JOIN con la tabla doctores
        String sql = "SELECT c.idcita, c.citadnipac, c.citaiddoc, "
        + "c.fechacita, c.horacita, c.motivo, "
        + "c.diagnostico, c.tratamiento, c.estadocita, "
        + "d.apeldoc, d.espedoc "
        + "FROM citas c "
        + "INNER JOIN doctores d ON c.citaiddoc = d.iddoc "
        + "WHERE c.citadnipac = ? "
        + "ORDER BY c.fechacita";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, dnipac);
                try (ResultSet rs = ps.executeQuery()) {
            // Recorremos las filas obtenidas

            while (rs.next()) {

                Cita cita = new Cita(
                        rs.getInt("idcita"),
                        rs.getString("apeldoc"),
                        rs.getString("espedoc"),
                        rs.getDate("fechacita").toLocalDate(),
                        rs.getString("horacita"),
                        rs.getString("estadocita")
                );
                citas.add(cita);
            }
        }
        } catch (SQLException e) {
            System.out.println("Error al cargar citas paciente: " + e.getMessage());
        }

        return citas;
    }
}
