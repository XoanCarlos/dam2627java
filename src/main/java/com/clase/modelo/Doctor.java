package com.clase.modelo;

public class Doctor {
    private Integer iddoc;
    private String apeldoc;
    private String nomdoc;
    private String movildoc;
    private String emaildoc;
    private Boolean coledoc;
    private String espedoc;
  

    //modelo para insertar docientes en la bbdd
    public Doctor(String apeldoc, String nomdoc,
            String movildoc, String emaildoc,
            Boolean coledoc, String espedoc) {
    
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.emaildoc = emaildoc;
        this.coledoc = coledoc;
        this.espedoc = espedoc;
    }
    
    // modelo para la tabla de docientes
    public Doctor(Integer iddoc, String apeldoc, String nomdoc,
            String movildoc, String espedoc) {

        this.iddoc = iddoc;
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.espedoc = espedoc;
    }

    public Integer getID() {
        return iddoc;
    }

    public void setID(Integer iddoc) {
        this.iddoc = iddoc;
    }

    public String getNombre() {
        return nomdoc;
    }

    public void setNombre(String nomdoc) {
        this.nomdoc = nomdoc;
    }

    public String getApellidos() {
        return apeldoc;
    }

    public void setApellidos(String apeldoc) {
        this.apeldoc = apeldoc;
    }

    public String getMovil() {
        return movildoc;
    }

    public void setMovil(String movildoc) {
        this.movildoc = movildoc;
    }

    public Boolean getColegiado() {
        return coledoc;
    }

    public void setColegiado(Boolean coledoc) {
        this.coledoc = coledoc;
    }

    public String getEmail() {
        return emaildoc;
    }

    public void setEmail(String emaildoc) {
        this.emaildoc = emaildoc;
    }

    public String getEspecialidad() {
        return espedoc;
    }

    public void setEspecialidad(String espedoc) {
        this.espedoc = espedoc;
    }

}