
package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.clase.modelo.Cita;
import com.clase.modelo.Doctor;
import com.clase.modelo.Paciente;
import com.clase.persistencia.CitaDAOMySQL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CitasController implements Initializable {

    @FXML
    private TextField citadnipac, citaiddoc, idcita, motivo;

    @FXML
    private CheckBox chkatendida, chkpendiente, chkanulada;

    @FXML 
    private Label lblCita;

    @FXML
    private ComboBox<String> horacita;

    @FXML 
    private DatePicker fechacita;

    @FXML 
    private TextArea diagnostico, tratamiento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Inicialización de la vista de citas
        cargarHorario();

    }
    
    boolean citaexiste = false;
    
   

    // Recibe el paciente seleccionado
    public void cargarDatosPaciente(Paciente paciente) {

        citadnipac.setText(paciente.getDni());
        lblCita.setText("Citas Paciente" + "  " + paciente.getApellidos());
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
            citaestado = "pendiente";
        } else if (chkanulada.isSelected()) {
            citaestado = "anulada";
        } else if (chkatendida.isSelected()) {
            citaestado = "atendida";
        }
        String citadiagnostico = diagnostico.getText();
        String citatratamiento = tratamiento.getText();
      
        // Creamos el objeto Cita
        Cita cita = new Cita(
                citaiddoctor,
                citadnipaciente,
                citamotivo,
                citafecha, 
                citahora,
                citaestado,
                citadiagnostico,
                citatratamiento
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
                alert2.setContentText("Doctor dado de alta en Base de Datos");
                alert2.showAndWait();
                } else{
                    return;
                }    
          }  
        //recargar la tabla con el nuevo Doctor
        //cargarCitas(); 
    }

 }
