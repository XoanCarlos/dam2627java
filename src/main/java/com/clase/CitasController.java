
package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.clase.modelo.Cita;
import com.clase.modelo.Doctor;
import com.clase.modelo.Paciente;
import com.clase.persistencia.CitaDAOMySQL;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

public class CitasController implements Initializable {

    @FXML
    private TextField citadnipac, citaiddoc, idcita, motivo;

    @FXML
    private CheckBox chkatendida, chkpendiente, chkanulada;

    @FXML 
    private Label lblCita;

    @FXML 
    private TableView<Cita> tablaCitas;

     @FXML
    private TableColumn<Cita, Integer> idcitacol;

    @FXML
    private TableColumn<Cita, String> apeldoccol;

    @FXML
    private TableColumn<Cita, String> espedoccol;

    @FXML
    private TableColumn<Cita, String> fechacitacol;

    @FXML 
    private TableColumn<Cita, String> horacitacol;

    @FXML 
    private TableColumn<Cita, String> estadocitacol;


    @FXML
    private ComboBox<String> horacita;

    @FXML 
    private DatePicker fechacita;

    @FXML 
    private TextArea diagnostico, tratamiento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuramos las columnas de la tabla para que muestren los datos de las citas

        idcitacol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getIdcita()).asObject());

        apeldoccol.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getApeldoc()));

        espedoccol.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getEspedoc()));

        fechacitacol.setCellValueFactory(
                data -> new SimpleObjectProperty<>(data.getValue().getFechacita().toString()));

        horacitacol.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getHoracita()));

        estadocitacol.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getEstadocita()));


// Carga una cita al seleccionarla en la tabla
// en el formulario para poder modificarla o eliminarla

tablaCitas.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, anterior, nuevo) -> {

            if (nuevo != null) {
                cargarCita();
            }
        });

        // Inicialización de la vista de citas
        cargarHorario();
    if (citadnipac.getText() != null && !citadnipac.getText().isEmpty()) {
        cargarCitas();
    }

    }
    
    boolean citaexiste = false;
    String nuevodni = "";   

    // Recibe el paciente seleccionado
    public void cargarDatosPaciente(Paciente paciente) {

        citadnipac.setText(paciente.getDni());
        lblCita.setText("Citas Paciente" + "  " + paciente.getApellidos());
        cargarCitas();
    }

    public void cargarDatosDoctor(Doctor doctor) {
        // Aquí puedes implementar la lógica para cargar los datos del doctor
        // seleccionado
        // Por ejemplo, podrías mostrar el nombre del doctor en un campo de texto
        // o realizar cualquier otra acción necesaria.
        citaiddoc.setText(String.valueOf(doctor.getID()));
    }

    private void cargarHorario() {
        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/horario.json");

        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)).getAsJsonObject();

        // Obtenemos el array "provincias" del JSON
        JsonArray horas = json.getAsJsonArray("horario");

        // Recorremos todas las provincias
        for (var hora : horas) {


            // Obtenemos el nombre de la provincia y lo añadimos al ComboBox
            horacita.getItems().add(hora.getAsString());
        }
    }

    // hace autoexcluyetes el checkbox
    @FXML
    private void gestionarExclusion(ActionEvent event) {
        CheckBox seleccionado = (CheckBox) event.getSource();

        if (seleccionado == chkpendiente && chkpendiente.isSelected()) {
            chkatendida.setSelected(false);
            chkanulada.setSelected(false);
        } else if (seleccionado == chkatendida && chkatendida.isSelected()) {
            chkpendiente.setSelected(false);
            chkanulada.setSelected(false);
        } else if (seleccionado == chkanulada && chkanulada.isSelected()) {
            chkpendiente.setSelected(false);
            chkatendida.setSelected(false);
        }
    }

    @FXML 
    private void limpiaForm() {
        idcita.setText("");
        citadnipac.setText("");
        citaiddoc.setText("");
        chkanulada.setSelected(false);
        chkatendida.setSelected(false);
        chkpendiente.setSelected(false);
        motivo.setText("");
        lblCita.setText("CITAS PACIENTE");
        horacita.getSelectionModel().clearSelection();
        fechacita.setValue(null); 
        diagnostico.setText(null);
        tratamiento.setText(null);
        tablaCitas.getItems().clear();       
    }

//FUNCION CRUD DE CITAS

    @FXML
    private void guardarCita() {  
        String citaiddoctor = citaiddoc.getText();
        String citadnipaciente = citadnipac.getText();
        String citamotivo = motivo.getText();
        LocalDate citafecha = fechacita.getValue();
        String citahora = horacita.getValue();
        String citaestado = "";
        if (chkpendiente.isSelected()) {
            citaestado = "Pendiente";
        } else if (chkanulada.isSelected()) {
            citaestado = "Anulada";
        } else if (chkatendida.isSelected()) {
            citaestado = "Atendida";
        }
        String citadiagnostico = diagnostico.getText();
        String citatratamiento = tratamiento.getText();
        
        // Creamos el objeto Cita
        Cita cita = new Cita(
                citadnipaciente,
                Integer.parseInt(citaiddoctor),
                citafecha,
                citahora,
                citamotivo,
                citadiagnostico,
                citatratamiento,
                citaestado
                    );  
            
        if (citaexiste) {
            // Si el Doctor ya existe, lo actualizamos en la base de datos
                // Si el Doctor no existe, lo guardamos en la base de datos
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            alert3.setTitle("Aviso");
            alert3.setHeaderText(null);
            alert3.setContentText("¿Desea Modificar datos del Cita?");
            Optional<ButtonType> result2 = alert3.showAndWait();
            if (result2.isPresent() && result2.get() == ButtonType.OK){
                CitaDAOMySQL dao = new CitaDAOMySQL();
                dao.modificarCita(Integer.parseInt(idcita.getText()), cita);
                citaexiste = false; //importante sino siempre graba
                Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                alert4.setTitle("Aviso");
                alert4.setHeaderText(null);
                alert4.setContentText("Cita modificada en Base de Datos");
                alert4.showAndWait();

            } else{
                return;
            }

            //buscadocid();
        } else { 
            System.out.println("Entrando en guardarCita()");
            // Si la Cita no existe, lo guardamos en la base de datos
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("¿Desea Grabar el Cita?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                {
                CitaDAOMySQL dao = new CitaDAOMySQL();
                dao.guardarCita(cita);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Aviso");
                alert2.setHeaderText(null);
                alert2.setContentText("Cita Guardada en Base de Datos");
                alert2.showAndWait();
                } else{
                    return;
                }    
            }  
        //recargar la tabla con el nuevas Citas
        cargarCitas(); 
    }
    // cargar Doctors en la tabla 
    @FXML
    private void cargarCitas() { 
        nuevodni = citadnipac.getText();
        limpiaForm();
        citadnipac.setText(nuevodni);
        tablaCitas.getItems().clear();
        CitaDAOMySQL dao = new CitaDAOMySQL(); 
        // Obtenemos los Doctors de la base de datos 
        List<Cita> citas = dao.cargarCitaspaciente(String.valueOf(nuevodni)); 
        System.out.println(citas);
        // Los mostramos en la tabla 
        System.out.println(citas.size() + " citas cargadas para el paciente " + citadnipac.getText());
        tablaCitas.getItems().setAll(citas); 
        tablaCitas.refresh();
        }

    @FXML
    private void cargarCita() {
        Cita citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
        citaSeleccionada = new CitaDAOMySQL().buscaCitaporId(citaSeleccionada.getIdcita());
        if (citaSeleccionada != null) {
            idcita.setText(String.valueOf(citaSeleccionada.getIdcita()));
            citaiddoc.setText(String.valueOf(citaSeleccionada.getCitaiddoc()));
            citadnipac.setText(citaSeleccionada.getCitadnipac());
            motivo.setText(citaSeleccionada.getMotivo());
            fechacita.setValue(citaSeleccionada.getFechacita());
            horacita.setValue(citaSeleccionada.getHoracita());
            diagnostico.setText(citaSeleccionada.getDiagnostico());
            tratamiento.setText(citaSeleccionada.getTratamiento());

            // Establecer el estado de la cita en los CheckBoxes
            String estado = citaSeleccionada.getEstadocita();
            chkatendida.setSelected("Atendida".equals(estado));
            chkpendiente.setSelected("Pendiente".equals(estado));
            chkanulada.setSelected("Anulada".equals(estado));

            citaexiste = true; // Marcamos que la cita existe para futuras modificaciones

        }
    }
    public void eliminaCita() {
        Cita citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
        if (citaSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de que desea eliminar la cita seleccionada?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CitaDAOMySQL dao = new CitaDAOMySQL();
                dao.eliminaCita(citaSeleccionada.getIdcita());
                cargarCitas(); // Recargamos la tabla después de eliminar
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Información");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Cita eliminada correctamente.");
                infoAlert.showAndWait();
            }
        } else {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Información");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Por favor, seleccione una cita para eliminar.");
            infoAlert.showAndWait();
        }
    }
 }
