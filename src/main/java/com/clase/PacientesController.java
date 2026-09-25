package com.clase;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.clase.modelo.Paciente;
import com.clase.persistencia.PacienteDAOMySQL;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
//import javafx.scene.control.cell.PropertyValueFactory para usarlo en la tabla de pacientes
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class PacientesController implements Initializable {

    @FXML
    private TextField dnipac, apelpac, nompac, movilpac, emailpac, dirpac;
    @FXML
    private DatePicker nacpac;
    @FXML
    private ComboBox<String> propac, munipac;
    @FXML
    private Button btnguardarpac, btnmodifpac, btndelpac;

    // componentes de la tabla
    @FXML
    private TableView<Paciente> tablaPacientes;

    @FXML
    private TableColumn<Paciente, String> coldnipac;

    @FXML
    private TableColumn<Paciente, String> colapelpac;

    @FXML
    private TableColumn<Paciente, String> colnompac;

    @FXML
    private TableColumn<Paciente, String> colmovilpac;

    @FXML
    private TableColumn<Paciente, String> colpropac;

    @FXML
    private TableColumn<Paciente, String> colmunipac;

    // implementación de los métodos de la interfaz Initializable

    // Este método se llama automáticamente cuando se carga la vista FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configuramos las columnas de la tabla para que muestren los datos de los
        // pacientes

        coldnipac.setCellValueFactory( // para la columna dni coge el valor el dni del paciente y lo muestra en la
                                       // tabla
                data -> new SimpleStringProperty(data.getValue().getDni()));

        colapelpac.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getApellidos()));

        colnompac.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getNombre()));

        colmovilpac.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getMovil()));

        colpropac.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getProvincia()));

        colmunipac.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getMunicipio()));

        dnipac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                comprobarDni();
            }
        });

        nompac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String nombre = letrasCapitales(nompac.getText());
                nompac.setText(nombre);
            }
        });

        apelpac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String nombre = letrasCapitales(apelpac.getText());
                apelpac.setText(nombre);
            }
        });

        movilpac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                comprobarMovil();
            }
        });

        // manejador de provinvicas, cargamos provincias y luego el evento
        // que carga los municipios de cada provincia
        cargarProvincias();

        propac.setOnAction(e -> cargarMunicipios());

        cargarPacientes();
    }

    /// validar dni
    @FXML
    private void comprobarDni() {
        String dni = dnipac.getText().trim().toUpperCase();
        if (dni.isEmpty())
            return;

        if (validarDni(dni)) {
            dnipac.setStyle("");
            dnipac.setText(dni);
        } else {
            dnipac.setStyle("-fx-border-color: red;");
            dnipac.setText("");
        }
    }

    private boolean validarDni(String documento) {

        if (documento.matches("\\d{8}[A-Z]")) {
            int numero = Integer.parseInt(documento.substring(0, 8));
            char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

            return letra == documento.charAt(8);
        }

        if (documento.matches("[XYZ]\\d{7}[A-Z]")) {

            String nie = documento
                    .replace("X", "0")
                    .replace("Y", "1")
                    .replace("Z", "2");

            int numero = Integer.parseInt(nie.substring(0, 8));
            char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

            return letra == documento.charAt(8);
        }
        return false;
    }

    /// letras capitales

    private String letrasCapitales(String texto) {
        String[] palabras = texto.toLowerCase().trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        return resultado.toString().trim();
    }

    /// validar movil
    @FXML
    private void comprobarMovil() {
        String movil = movilpac.getText();
        if (movil.isEmpty())
            return;

        if (validarMovil(movil)) {
            movilpac.setStyle("");
            movilpac.setText(movil);
        } else {
            movilpac.setStyle("-fx-border-color: red;");
            movilpac.setText("");
        }
    }

    private boolean validarMovil(String telefono) {
        return telefono.matches("[67][0-9]{8}");
    }

    // cargamos provinias al lanzar le programa

    private void cargarProvincias() {

        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/municipios.json");

        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos el array "provincias" del JSON
        JsonArray provincias = json.getAsJsonArray("provincias");

        // Recorremos todas las provincias
        for (var provincia : provincias) {

            // Cada elemento del array es un objeto JSON
            JsonObject p = provincia.getAsJsonObject();

            // Obtenemos el nombre de la provincia
            // y lo añadimos al ComboBox
            propac.getItems().add(
                    p.get("nm").getAsString());
        }

    }

    private void cargarMunicipios() {

        // Abrimos de nuevo el fichero JSON
        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/municipios.json");

        // Convertimos el contenido del fichero en un JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos los dos arrays que necesitamos
        JsonArray provincias = json.getAsJsonArray("provincias");
        JsonArray municipios = json.getAsJsonArray("municipios");

        // Obtenemos el nombre de la provincia seleccionada
        String nombreProvincia = propac.getValue();

        // Variable donde guardaremos el código de la provincia
        String idProvincia = "";

        // Recorremos las provincias
        for (var provincia : provincias) {

            JsonObject p = provincia.getAsJsonObject();

            // Comprobamos si es la provincia seleccionada
            if (p.get("nm").getAsString().equals(nombreProvincia)) {

                // Obtenemos su código
                idProvincia = p.get("id").getAsString();

                // Ya hemos encontrado la provincia
                break;
            }
        }

        // Eliminamos los municipios que pudiera haber
        // de una selección anterior muy importante sino agrega municipios
        munipac.getItems().clear();

        // Recorremos todos los municipios
        for (var municipio : municipios) {

            JsonObject m = municipio.getAsJsonObject();

            // Comprobamos los dos primeros caracteres del código
            if (m.get("id").getAsString().startsWith(idProvincia)) {

                // Si pertenecen a la provincia,
                // añadimos su nombre al ComboBox
                munipac.getItems().add(
                        m.get("nm").getAsString());
            }
        }
    }

    @FXML
    private void cleanFormpac() {
        dnipac.setText("");
        apelpac.setText("");
        nompac.setText("");
        movilpac.setText("");
        emailpac.setText("");
        nacpac.setValue(null);
        dirpac.setText("");
        propac.getSelectionModel().clearSelection();
        munipac.getSelectionModel().clearSelection();
    }
    // guardar pacientes en la bbdd
    @FXML
    private void guardarPaciente() {
        // Comprobamos que se haya introducido la fecha
        if (nacpac.getValue() == null) {
            System.out.println("Debes introducir la fecha de nacimiento");
            return;
        }

        String dni = dnipac.getText();
        String apellidos = apelpac.getText();
        String nombre = nompac.getText();
        LocalDate fechaNacimiento = nacpac.getValue();
        String movil = movilpac.getText();
        String email = emailpac.getText();
        String direccion = dirpac.getText();
        String provincia = propac.getValue();
        String municipio = munipac.getValue();
        // Creamos el objeto Paciente
        Paciente paciente = new Paciente(
                dni,
                apellidos,
                nombre,
                movil,
                email, fechaNacimiento,
                direccion,
                provincia,
                municipio);

        // Creamos el DAO y guardamos el paciente en MySQL
        PacienteDAOMySQL dao = new PacienteDAOMySQL();
        dao.guardarPaciente(paciente);

        cargarPacientes();
    }

    // cargar pacientes en la tabla 
    @FXML
    private void cargarPacientes() { 
        // Creamos el DAO 
        PacienteDAOMySQL dao = new PacienteDAOMySQL(); 
        // Obtenemos los pacientes de la base de datos 
        List<Paciente> pacientes = dao.cargarPacientes(); 
        // Los mostramos en la tabla 
        tablaPacientes.getItems().setAll(pacientes); }
}
