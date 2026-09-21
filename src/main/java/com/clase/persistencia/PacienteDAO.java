package com.clase.persistencia;
import com.clase.modelo.Paciente;


//Es la interfaz. Solo dice qué operaciones podemos hacer con un paciente, no cómo se hacen.

public interface PacienteDAO {
 void guardarPaciente(Paciente paciente);
 
}