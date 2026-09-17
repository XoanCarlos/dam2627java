package com.clase;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PacientesController implements Initializable {

    @FXML
    private TextField dnipac;

    @FXML
    private TextField apelpac;

    @FXML
    private TextField nompat;

    @FXML
    private DatePicker nacpac;

    @FXML
    private TextField tlfopac;

    @FXML
    private TextField emailpac;

    @FXML
    private TextField dirpac;

    @FXML
    private ComboBox<String> cmbpac;

    @FXML
    private ComboBox<String> locpac;

    @FXML
    private Button btnguardarpac;

    @FXML
    private Button btnmodifpac;

    @FXML
    private Button btndelpac;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Datos de prueba para el ComboBox de provincias
        cmbpac.getItems().addAll(
                "A Coruña",
                "Lugo",
                "Ourense",
                "Pontevedra"
        );
    }

    @FXML
    private void guardarPaciente() {

        String dni = dnipac.getText();
        String apellidos = apelpac.getText();
        String nombre = nompat.getText();

        LocalDate fechaNacimiento = nacpac.getValue();

        String telefono = tlfopac.getText();
        String email = emailpac.getText();
        String direccion = dirpac.getText();

        String provincia = cmbpac.getValue();
        String localidad = locpac.getValue();

        System.out.println("========== PACIENTE ==========");
        System.out.println("DNI: " + dni);
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha nacimiento: " + fechaNacimiento);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Email: " + email);
        System.out.println("Dirección: " + direccion);
        System.out.println("Provincia: " + provincia);
        System.out.println("Localidad: " + localidad);
        System.out.println("==============================");
    }
}