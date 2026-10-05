package com.clase;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.clase.modelo.Doctor;
//import com.clase.persistencia.DoctorDAOMySQL;

import java.net.URL;
import java.util.ResourceBundle;

import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
//import javafx.scene.control.cell.PropertyValueFactory para usarlo en la tabla de Doctors
import javafx.beans.property.SimpleStringProperty;
//import java.util.List;

public class DoctoresController implements Initializable {

    @FXML
    private TextField iddoc, apeldoc, nomdoc, movildoc, emaildoc;
    @FXML
    private ComboBox<String> espedoc;
    @FXML
    private Button btnguardardoc, btndeldoc;
    @FXML 
    private RadioButton coledocno, coledocsi;   

    // componentes de la tabla
    @FXML
    private TableView<Doctor> tablaDoctores;

    @FXML
    private TableColumn<Doctor, String> coliddoc;

    @FXML
    private TableColumn<Doctor, String> colapeldoc;

    @FXML
    private TableColumn<Doctor, String> colnomdoc;

    @FXML
    private TableColumn<Doctor, String> colmovildoc   ;

    @FXML
    private TableColumn<Doctor, String> colespedoc;

    boolean doctorexiste = false;

    // implementación de los métodos de la interfaz Initializable

    // Este método se llama automáticamente cuando se carga la vista FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configuramos las columnas de la tabla para que muestren los datos de los
        // Doctors

        coliddoc.setCellValueFactory( // para la columna dni coge el valor el dni del Doctor y lo muestra en la
                                       // tabla
                data -> new SimpleStringProperty(data.getValue().getID()));

        colapeldoc.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getApellidos()));

        colnomdoc.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getNombre()));

        colmovildoc.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getMovil()));

        colespedoc.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getEspecialidad()));

        nomdoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String nombre = letrasCapitales(nomdoc.getText());
                nomdoc.setText(nombre);
            }
        });

        apeldoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String nombre = letrasCapitales(apeldoc.getText());
                apeldoc.setText(nombre);
            }
        });

        movildoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                comprobarMovil();
            }
        });

        //carga un Doctor al seleccionarlo en la tabla 
        // en el formulario para poder modificarlo o eliminarlo
/*
        tablaDoctores.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, anterior, nuevo) -> {

            if (nuevo != null) {
                cargarDoctor();
            }
        });
*/
        // manejador de provinvicas, cargamos provincias y luego el evento
        // que carga los municipios de cada provincia
        cargarEspecialidades();

        //cargarDoctores();
    }


    // FUNCIONES AUXILIARES DE DoctorS
   
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
        String movil = movildoc.getText();
        if (movil.isEmpty())
            return;

        if (validarMovil(movil)) {
            movildoc.setStyle("");
            movildoc.setText(movil);
        } else {
            movildoc.setStyle("-fx-border-color: red;");
            movildoc.setText("");
        }
    }

    private boolean validarMovil(String telefono) {
        return telefono.matches("[67][0-9]{8}");
    }

    // cargamos provinias al lanzar le programa

    private void cargarEspecialidades() {

        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/especialidades.json");

        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos el array "provincias" del JSON
        JsonArray especialidades = json.getAsJsonArray("especialidades");

        // Recorremos todas las provincias
        for (var especialidad : especialidades) {

            // Cada elemento del array es un objeto JSON
            JsonObject p = especialidad.getAsJsonObject();

            // Obtenemos el nombre de la provincia
            // y lo añadimos al ComboBox
            espedoc.getItems().add(
                    p.get("nombre").getAsString());
        }

    }

    @FXML
    private void cleanFormdoc() {
        iddoc.setText("");
        apeldoc.setText("");
        nomdoc.setText("");
        movildoc.setText("");
        emaildoc.setText("");
        coledocno.setSelected(false);
        coledocsi.setSelected(false);
        espedoc.getSelectionModel().clearSelection();
       
    }
}
   
   /*
    // FUNCIONES CON BASE DE DATOS
    @FXML
    private void guardarDoctor() {
        String dni = dnipac.getText();
        String apellidos = apelpac.getText();
        String nombre = nompac.getText();
        LocalDate fechaNacimiento = nacpac.getValue();
        String movil = movilpac.getText();
        String email = emailpac.getText();
        String direccion = dirpac.getText();
        String provincia = propac.getValue();
        String municipio = munipac.getValue();
        // Creamos el objeto Doctor
        Doctor Doctor = new Doctor(
                dni,
                apellidos,
                nombre,
                movil,
                email, fechaNacimiento,
                direccion,
                provincia,
                municipio);
        if (Doctorexiste) {
            // Si el Doctor ya existe, lo actualizamos en la base de datos
            DoctorDAOMySQL dao = new DoctorDAOMySQL();
            dao.modificarDoctor(Doctor.getDni(), Doctor);
            Doctorexiste = false; //importante
            buscaPacdni();
        } else {
            // Si el Doctor no existe, lo guardamos en la base de datos
            DoctorDAOMySQL dao = new DoctorDAOMySQL();
            dao.guardarDoctor(Doctor);
        }
        //recargar la tabla con el nuevo Doctor
        cargarDoctors();
    }

    // cargar Doctors en la tabla 
    @FXML
    private void cargarDoctors() { 
        // Creamos el DAO 
        DoctorDAOMySQL dao = new DoctorDAOMySQL(); 
        // Obtenemos los Doctors de la base de datos 
        List<Doctor> Doctors = dao.cargarDoctors(); 
        // Los mostramos en la tabla 
        tablaDoctors.getItems().setAll(Doctors); }

   @FXML 
   private void cargarDoctor() {
        Doctor Doctorselect = tablaDoctors
                .getSelectionModel()
                .getSelectedItem(); //carga los datos de la fila seleccionado

        if (Doctorselect == null) {
            return;
        } else {
            Doctorexiste = true;
        }

        DoctorDAOMySQL dao = new DoctorDAOMySQL();

        Doctor Doctor = dao.buscarDoctor(Doctorselect.getDni());

        if (Doctor == null) {
            return;
        }

        dnipac.setText(Doctor.getDni());
        apelpac.setText(Doctor.getApellidos());
        nompac.setText(Doctor.getNombre());
        movilpac.setText(Doctor.getMovil());
        emailpac.setText(Doctor.getEmail());
        nacpac.setValue(Doctor.getNacimiento());
        dirpac.setText(Doctor.getDireccion());
        propac.setValue(Doctor.getProvincia());
        //cargarMunicipios();  comprobar esto
        munipac.setValue(Doctor.getMunicipio());
            } 
    
    @FXML 
    private void eliminarDoctor() {
        Doctor seleccionado = tablaDoctors
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            return;
        }

        DoctorDAOMySQL dao = new DoctorDAOMySQL();
        dao.eliminarDoctor(seleccionado.getDni());

        // Recargamos la tabla de Doctors
        cargarDoctors();
        }
    @FXML 
    private void buscaPacdni() {

        DoctorDAOMySQL dao = new DoctorDAOMySQL();

        Doctor Doctor = dao.buscaPacdni(dnipac.getText());
        
        if (Doctor == null){
            return;
        } else {
            Doctorexiste = true;
        }

        dnipac.setText(Doctor.getDni());
        apelpac.setText(Doctor.getApellidos());
        nompac.setText(Doctor.getNombre());
        movilpac.setText(Doctor.getMovil());
        emailpac.setText(Doctor.getEmail());
        nacpac.setValue(Doctor.getNacimiento());
        dirpac.setText(Doctor.getDireccion());
        propac.setValue(Doctor.getProvincia());
        //cargarMunicipios();  comprobar esto
        munipac.setValue(Doctor.getMunicipio());
        }  
    }
*/