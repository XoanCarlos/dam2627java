
package com.clase.modelo;

import java.time.LocalDate;


public class Cita {

    private int idcita;
    private String citadnipac;
    private int citaiddoc;
    private LocalDate fechacita;
    private String horacita;
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private String estadocita;

    // Datos del doctor para mostrar en la tabla
    private String apeldoc;
    private String espedoc;


    // Constructor vacío
    public Cita() {
    }

    // Constructor con todos los campos
    public Cita(int idcita, String citadnipac, int citaiddoc,
                LocalDate fechacita, String horacita,
                String motivo, String diagnostico,
                String tratamiento, String estadocita) {

        this.idcita = idcita;
        this.citadnipac = citadnipac;
        this.citaiddoc = citaiddoc;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estadocita = estadocita;
    }

    // un constructor para insertar
    // Constructor con todos los campos
    public Cita(String citadnipac, int citaiddoc,
                LocalDate fechacita, String horacita,
                String motivo, String diagnostico,
                String tratamiento, String estadocita) {

      
        this.citadnipac = citadnipac;
        this.citaiddoc = citaiddoc;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estadocita = estadocita;
    }

    // modelo para la tabla de citas
    public Cita(Integer idcita, String apeldoc, String espedoc,
            LocalDate fechacita, String horacita, String estadocita) {
        this.idcita = idcita;
        this.apeldoc = apeldoc;
        this.espedoc = espedoc;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.estadocita = estadocita;
    }


    // Getters y Setters

    public int getIdcita() {
        return idcita;
    }

    public void setIdcita(int idcita) {
        this.idcita = idcita;
    }

    public String getCitadnipac() {
        return citadnipac;
    }

    public void setCitadnipac(String citadnipac) {
        this.citadnipac = citadnipac;
    }

    public int getCitaiddoc() {
        return citaiddoc;
    }

    public void setCitaiddoc(int citaiddoc) {
        this.citaiddoc = citaiddoc;
    }

    public LocalDate getFechacita() {
        return fechacita;
    }

    public void setFechacita(LocalDate fechacita) {
        this.fechacita = fechacita;
    }

    public String getHoracita() {
        return horacita;
    }

    public void setHoracita(String horacita) {
        this.horacita = horacita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getEstadocita() {
        return estadocita;
    }

    public void setEstadocita(String estadocita) {
        this.estadocita = estadocita;
    }

    // del doctor para mostrar en la tabla
    public String getApeldoc() {
        return apeldoc;
    }

    public void setApeldoc(String apeldoc) {
        this.apeldoc = apeldoc;
    }

    public String getEspedoc() {
        return espedoc;
    }

    public void setEspedoc(String espedoc) {
        this.espedoc = espedoc;
    }
}
