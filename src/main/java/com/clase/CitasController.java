
package com.clase;

import java.net.URL;
import java.util.ResourceBundle;

import com.clase.modelo.Paciente;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class CitasController implements Initializable {

    @FXML
    private TextField citadnipac;

    // Recibe el paciente seleccionado
    public void cargarDatosPaciente(Paciente paciente) {

        citadnipac.setText(paciente.getDni());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Inicialización de la vista de citas

    }
}
