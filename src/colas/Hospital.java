
/*********************************************************************
* @name Hospital
* 
* @authors DJS - B2 - 03
* 
* @description Contiene las colas de pacientes de las diferentes especialidades.
* 			   Contiene métodos para tratar y añadir a los pacientes.
***********************************************************************/

package Colas;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Colas.Paciente.*;

public class Hospital implements Constantes {

	private final Queue        <Paciente> traumatología; // Cola que alberga los pacientes a tratar en la sección de traumatología.
	private final PriorityQueue<Paciente> cardiología;   // Cola que alberga los pacientes a tratar en la sección de cardiología.
	private final Queue        <Paciente> neurología;    // Cola que alberga los pacientes a tratar en la sección de neurología.
	
	/***********************************
	 * @name Constructor del Hospital
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Inicializa los atributos de una instancia Hospital. Usa la variable
	 * 				capacidadTraumatología de la interfaz Constantes.
	 ***********************************/
	public Hospital() {
		
		// Implementación de la cola de traumatología usando memoria estática.
		traumatología = new ArrayBlockingQueue<Paciente>(capacidadTraumatología);
		
		// Implementación de la cola de cardiología usando una cola de prioridad.
		cardiología= new PriorityQueue<Paciente>();
		
		// Implementación de la cola de neurología usando memoria dinámica.
		neurología= new LinkedBlockingQueue<Paciente>();
	}
	
	/***********************************
	 * @name añadirPaciente
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Dado un paciente correctamente creado, lo añade a la cola que deba atendiendo a su especialidad.
	 * 
	 * @param paciente --> Paciente que se añadirá a una cola determinada.
	 ***********************************/
	public void añadirPaciente(final Paciente paciente) {
		switch (paciente.getSpeciality()) {
			case TRAUMATOLOGY: traumatología.add(paciente); break;
			case CARDIOLOGY:   cardiología.add(paciente);   break;
			case NEUROLOGY:    neurología.add(paciente);    break;
		}
	}
	
	/***********************************
	 * @name triage
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Asigna los valores de los atributos de un paciente.
	 * 
	 * @param líneaLeída --> Información del paciente contenida en una línea del fichero. 
	 * 						 Debe ser de la forma: DNI;Especialidad;Urgencia
	 * @param horaLlegada --> Tiempo en el que llega el paciente al hospital.
	 * 
	 * @throws InvalidPatientInfoException --> Cuando la información del paciente no coincide con la que se espera
	 * @throws IndexOutOfBoundsException --> Si la líneaLeída no contiene la información necesaria. Unchecked exception
	 * 
	 * @return Paciente creado a partir de los valores de sus atributos
	 ***********************************/
	public Paciente triage(final String líneaLeída, final long horaLlegada) throws InvalidPatientInfoException {
		
		// Guarda las subcadenas de la línea leída del fichero al separarlos mediante un ;
		final String []infoPaciente= líneaLeída.split(";");
		
		// DNI del paciente, se usará para crear el paciente.
		final String DNI= infoPaciente[0];
		
		// Especialidad del problema del paciente. Usada para saber a qué cola pertenece un paciente.
		Speciality speciality;
		switch (infoPaciente[1]) {
			case "Traumatology": speciality = Speciality.TRAUMATOLOGY; break;
			case "Cardiology":   speciality = Speciality.CARDIOLOGY;   break;
			case "Neurology":    speciality = Speciality.NEUROLOGY;    break;
			// Ante cualquier otra especialidad, se lanza la excepción de información inválida.
			default: throw new InvalidPatientInfoException("--> DNI: "+ DNI+ ", Speciality: '"+infoPaciente[1]+"'...?");
		}
		
		// Urgencia del paciente. Usada para conocer las prioridades de los pacientes en cardiología. 
		Severity severity;
		switch (infoPaciente[2]) {
			case "Mild":   severity= Severity.MILD;   break;
			case "Severe": severity= Severity.SEVERE; break;
			case "Vital":  severity= Severity.VITAL;  break;
			// Ante cualquier otro nivel de gravedad, se lanza la excepción de información inválida.
			default: throw new InvalidPatientInfoException("--> DNI: "+ DNI+ ", Severity: '"+infoPaciente[2]+"'...?");
		}
		
		return new Paciente(DNI, severity, speciality, horaLlegada);
	}
	
	/***********************************
	 * @name toString
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Devuelve toda la información del hospital con formato. Esto es, todos los pacientes que han sido 
	 * 				atendidos en cada especialidad, además de los valores de todos sus atributos: DNI, gravedad, hora
	 * 				de llegada en segundos y hora de llegada en horas.
	 * 
	 * @return Toda la información del hospital
	 ***********************************/
	public String toString() {
		String devolver = "--------------------------------\nHISTORIAL DE PACIENTES TRATADOS"
				+ "\n-------------------------------- \nTRAUMATOLOGÍA\n  DNI   Gravedad    Llegada     En horas";
		while(!traumatología.isEmpty())
			devolver += "\n"+traumatología.poll();
		
		devolver += "\n\nCARDIOLOGÍA\n  DNI   Gravedad    Llegada     En horas";
		
		while(!cardiología.isEmpty())
			devolver += "\n"+cardiología.poll();
	
		devolver += "\n\nNEUROLOGÍA\n  DNI   Gravedad    Llegada     En horas";
		
		/* Neurología solo atiende una hora, por lo que si ya ha transcurrido este periodo de tiempo, cerrará sin poder
		 * atender a más pacientes a pesar de que hayan llegado al hospital a una hora determinada. */
		while(!neurología.isEmpty()) 
			if(neurología.peek().getHoraLlegada() < duraciónAtenciónNeurología)
				devolver+= "\n"+neurología.poll();
			else
				devolver+= "\n"+neurología.poll()+" Neurología está cerrado. El paciente no ha llegado a tiempo.";
		
		return devolver;
	}
	
}
