
/*********************************************************************
* @name Hospital
* 
* @authors DJS - B2 - 03
* 
* @description Contiene las colas de las diferentes especialidades y sus registros de pacientes. 
* 			   Tiene métodos para añadir y tratar pacientes
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
	
	private final Queue<Paciente> historialTraumatología; // Cola que alberga los pacientes ya tratados en la sección de traumatología.
	private final Queue<Paciente> historialCardiología;   // Cola que alberga los pacientes ya tratados en la sección de cardiología.
	private final Queue<Paciente> historialNeurología;    // Cola que alberga los pacientes ya tratados en la sección de neurología.
	
	/***********************************
	 * @name Constructor del Hospital
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Inicializa los atributos de una instancia Hospital
	 * 
	 ***********************************/
	public Hospital() {
		
		// Implementación de la cola de tratamiento de traumatología usando memoria estática.
		traumatología = new ArrayBlockingQueue<Paciente>(capacidadTraumatología);
		historialTraumatología = new LinkedBlockingQueue<Paciente>();
		
		// Implementación de la cola de tratamiento de cardiología usando una cola de prioridad.
		cardiología= new PriorityQueue<Paciente>();
		historialCardiología= new LinkedBlockingQueue<Paciente>();
		
		// Implementación de la cola de tratamiento de neurología usando memoria dinámica.
		neurología= new LinkedBlockingQueue<Paciente>();
		historialNeurología= new LinkedBlockingQueue<Paciente>();
	}
	
	/***********************************
	 * @name isEmpty
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Evalúa si todavía quedan pacientes por tratar en alguna de las colas.
	 * 
	 * @return Devuelve true si todas las colas están vacías y falso si quedan pacientes a tratar en alguna cola.
	 ***********************************/
	public boolean isEmpty() {
		return traumatología.isEmpty() && cardiología.isEmpty() && neurología.isEmpty();
	}
	
	/***********************************
	 * @name tratar
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Trata a los pacientes al principio de la cola durante un tiempo especificado. Si ha terminado 
	 * 				el turno de estos, los saca de la cola y los añade al registro junto a la hora de salida.
	 * 
	 * @param horaActual --> Hora del momento en el que se está tratando a los pacientes. Usado para calcular si neurología está abierto.
	 * @param tiempoTratado --> Tiempo durante el cual se va a tratar a los pacientes
	 * 
	 * @return this --> Referencia al hospital que realiza la llamada
	 ***********************************/
	public Hospital tratar(final long horaActual, final long tiempoTratado) {
		
		/* Si quedan pacientes en traumatología/cardiología y se ha terminado de tratar al paciente de la cabeza de la cola
		 * de tratamiento, se elimina de la misma y se añade a la cola del registro con su hora de salida. */
		if (!traumatología.isEmpty() && traumatología.peek().tratar(tiempoTratado))
			historialTraumatología.add(traumatología.poll().setHoraSalida(horaActual+tiempoTratado));
		
		if (!cardiología.isEmpty() && cardiología.peek().tratar(tiempoTratado))
			historialCardiología.add(cardiología.poll().setHoraSalida(horaActual+tiempoTratado));
		
		// Se comprueba si neurología está abierto, es decir, si la hora actual se encuentra entre la de inicio y la final.
		boolean neurologíaAbierto = horaActual > horaAtenciónNeurología && horaActual < horaAtenciónNeurología+duraciónAtenciónNeurología;
		
		/* Además de las sentencias descritas anteriormente para las colas de traumatología y cardiología, comprobamos si
		 * neurología está abierto o hay alguien siendo tratado, en cuyo caso se tratará y, si ha terminado el tratamiento,
		 * se quitará de la cola. */
		if (!neurología.isEmpty() && (neurologíaAbierto || neurología.peek().siendoTratado()) && neurología.peek().tratar(tiempoTratado))
			historialNeurología.add(neurología.poll().setHoraSalida(horaActual+tiempoTratado));
		
		// Devuelve la referencia del hospital.
		return this;
	}
	
	/***********************************
	 * @name añadirPaciente
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Dado un paciente correctamente creado, lo añade a la cola que deba atendiendo a su especialidad.
	 * 
	 * @param Paciente --> Paciente que se añadirá a una cola determinada.
	 ***********************************/
	private void añadirPaciente(final Paciente paciente) {
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
	 * @description Asigna los valores de los atributos de un paciente que se añadirá a las colas con una llamada a triage
	 * 
	 * @param líneaLeída --> Un string con la información del paciente. Debe ser de la forma: DNI;Especialidad;Urgencia
	 * @param tiempoAtención --> Tiempo necesario para tratar al paciente especificado
	 * 
	 * @throws InvalidPatientInfoException --> Cuando la información del paciente no coincide con la que se espera
	 * @throws IndexOutOfBoundsException --> Si la líneaLeída no contiene la información necesaria
	 * @return this --> Referencia al hospital que realiza la llamada
	 ***********************************/
	public Hospital triage(final String líneaLeída) throws InvalidPatientInfoException {
		
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
			default: throw new InvalidPatientInfoException("DNI: "+ DNI+ ", Speciality: '"+infoPaciente[1]+"'...?");
		}
		
		// Urgencia del paciente. Usada para conocer las prioridades de los pacientes en cardiología. 
		Severity severity;
		switch (infoPaciente[2]) {
			case "Mild":   severity= Severity.MILD;   break;
			case "Severe": severity= Severity.SEVERE; break;
			case "Vital":  severity= Severity.VITAL;  break;
			// Ante cualquier otro nivel de gravedad, se lanzala excepción de información inválida.
			default: throw new InvalidPatientInfoException("DNI: "+ DNI+ ", Severity: '"+infoPaciente[2]+"'...?");
		}
		
		// Adición del paciente a su respectiva cola con los valores de los atributos cargados.
		añadirPaciente(new Paciente(DNI, severity, speciality, tiempoAtención));
		
		// Devuelve la referencia del hospital.
		return this;
	}
	
	/***********************************
	 * @name toString
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lista los registros de las diferentes especialidades, que contienen los pacientes ya tratados con su 
	 * 				hora especificada. Adicionalmente, limpia los registros.
	 * 
	 * @return devolver --> String con toda la información de todos los registros.
	 ***********************************/
	public String toString() {
		String devolver = "--------------------------------\nHISTORIAL DE PACIENTES TRATADOS"
				+ "\n-------------------------------- \nTraumatología:";
		while(!historialTraumatología.isEmpty())
			devolver += "\n"+historialTraumatología.poll();
		
		devolver += "\n\nCardiología:";
		while(!historialCardiología.isEmpty())
			devolver += "\n"+historialCardiología.poll();
		
		devolver += "\n\nNeurología:";
		while(!historialNeurología.isEmpty())
			devolver += "\n"+historialNeurología.poll();
		
		return devolver;
	}
	
}
