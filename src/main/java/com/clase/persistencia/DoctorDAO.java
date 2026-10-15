package com.clase.persistencia;
import com.clase.modelo.Doctor;
import java.util.List;

//Es la interfaz. Solo dice qué operaciones podemos hacer con un doctor, no cómo se hacen.

public interface DoctorDAO {
void guardarDoctor(Doctor doctor);
List<Doctor> cargarDoctores();
void eliminarDoctor(Integer id);
Doctor buscaDocporId (Integer id);
void modificarDoctor(Integer id, Doctor doctor);
List<Doctor> cargardoctoresEspec(String espedoc); 
}