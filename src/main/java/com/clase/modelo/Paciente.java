package com.clase.modelo;

import java.time.LocalDate;

public class Paciente {
    private String dnipac;
    private String apelpac;
    private String nompac;
    private String movilpac;
    private String mailpac;
    private LocalDate nacpac;
    private String dirpac;
    private String propac;
    private String munipac;

    public Paciente() {
    }

    public Paciente(String dnipac, String apelpac, String nompac,
            String movilpac, String mailpac,LocalDate nacpac, 
            String dirpac, String propac, String munipac) {
        this.dnipac = dnipac;
        this.apelpac = apelpac;
        this.nompac = nompac;
        this.nacpac = nacpac;   
        this.movilpac = movilpac;
        this.mailpac = mailpac;
        this.dirpac = dirpac;   
        this.propac = propac;
        this.munipac = munipac;
    }

    public String getNombre() {
        return nompac;
    }

    public void setNombre(String nompac) {
        this.nompac = nompac;
    }

    public String getApellidos() {
        return apelpac;
    }

    public void setApellidos(String apelpac) {
        this.apelpac = apelpac;
    }

    public String getDni() {
        return dnipac;
    }

    public void setDni(String dnipac) {
        this.dnipac = dnipac;
    }

    public String getMovil() {
        return movilpac;
    }

    public void setMovil(String movilpac) {
        this.movilpac = movilpac;
    }

    public String getDireccion() {
        return dirpac;
    }

    public void setDireccion(String dirpac) {
        this.dirpac = dirpac;
    }

    public LocalDate getFechaAlta() {
        return nacpac;
    }

    public void setFechaAlta(LocalDate nacpac) {
        this.nacpac = nacpac;
    }
      public String getmailpac() {
        return mailpac;
    }

    public void setmailpac(String mailpac) {
        this.mailpac = mailpac;
    }
    
    



}