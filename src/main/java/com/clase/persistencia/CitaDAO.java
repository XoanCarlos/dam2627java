package com.clase.persistencia;
import com.clase.modelo.Cita;
//import java.util.List;

//Es la interfaz. Solo dice qué operaciones podemos hacer con un cita, no cómo se hacen.

public interface CitaDAO {
    void guardarCita(Cita cita);
    //List<Cita> cargarCitas();
    //void eliminarCita(Integer id);
    //Cita buscaCitaporId (Integer id);
    void modificarCita(Integer id, Cita cita);
    //List<Cita> cargarcitasEspec(String especita); 
}