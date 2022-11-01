
/*********************************************************************
* @name Paciente
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información para hacer la simulación y tiene métodos para conseguir esta
***********************************************************************/

package Colas;

import Colas.Constantes;
import Colas.Paciente;
import Colas.Paciente.Severity;
import Colas.Paciente.Speciality;

public class Paciente implements Comparable<Paciente>, Constantes {
	
	// Enumeraciones para poder usar valores entendibles en vez de números. 
	public enum Severity {MILD, SEVERE, VITAL};
	public enum Speciality {TRAUMATOLOGY, CARDIOLOGY, NEUROLOGY};
	
	private final String dni;
	private final Severity severity;     // Gravedad del paciente.
	private final Speciality speciality; // Especialidad del problema del paciente.
	
	private long tiempoAtendido;        // Tiempo en segundos que ha sido atendido el paciente.
	private final long tiempoRequerido; // Tiempo en segundos que requiere un paciente para terminar su tratamiento.
	
	private long finTratamiento; // Tiempo en segundos dentro de un día para saber cuándo ha sido tratado el paciente.
	
	/***********************************
	 * @name Constructor de Paciente
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Inicializa los atributos de una instancia Paciente
	 * 
	 * @param dni --> DNI del paciente
	 * @param severity --> Urgencia del paciente
	 * @param speciality --> Tipo de emergencia que tiene el paciente
	 * @param tiempoRequerido --> Tiempo en segundos necesarios para tratar la emergencia del paciente
	 ***********************************/
	public Paciente(final String dni, final Severity severity, final Speciality speciality, final long tiempoRequerido) {
		super();
		this.dni = dni;
		this.severity = severity;
		this.speciality = speciality;
		this.tiempoRequerido= tiempoRequerido;
		
		finTratamiento = -1; // Inicializada a -1. El paciente no ha sido tratado aún. 
		tiempoAtendido = 0; // Inicializada a 0. Se ha tratado al paciente durante 0 segundos.
	}

	public Severity getSeverity() {
		return severity;
	}

	public Speciality getSpeciality() {
		return speciality;
	}
	
	/***********************************
	 * @name tratar
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Trata al paciente durante un tiempo, esto es, incrementar el tiempo de 
	 * 				atención el tiempo especificado. 
	 * @param tiempo --> Tiempo en segundos durante los que se va a tratar al paciente
	 * 
	 * @return Devuelve true si se ha terminado de tratar al paciente y falso en caso contrario.
	 ***********************************/
	public boolean tratar(final long tiempo) {
		tiempoAtendido+= tiempo;
		return tiempoAtendido >= tiempoRequerido;
	}
	
	/***********************************
	 * @name siendoTratado
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Calcula si el paciente está siendo tratado, esto es, que el tiempo que ha sido atendido sea mayor que 0
	 * 
	 * @return Devuelve true si el tiempo atendido es superior a 0 y falso si es igual o menor
	 ***********************************/
	public boolean siendoTratado() {
		return tiempoAtendido > 0;
	}

	/***********************************
	 * @name compareTo
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Indica qué paciente tiene prioridad respecto a otro atendiendo a la urgencia de su enfermedad
	 * 
	 * @param other --> Paciente con el que se compara
	 * 
	 * @return 1 --> Otro paciente tiene prioridad sobre el actual por estar siendo tratado o por tener una urgencia mayor
	 * @return 0 --> Ambos tienen la misma prioridad
	 * @return -1 --> El paciente actual tiene preferencia sobre el otro paciente por tener una urgencia mayor
	 ***********************************/
	public int compareTo(final Paciente other) {
		
		// Otro paciente de la cola está siendo tratado y, por tanto, tiene prioridad sobre mí.
		if (other.siendoTratado()) return 1;
		
		// Si mi urgencia es baja y la del otro no lo es, el otro paciente tiene prioridad.
		if (severity == Severity.MILD && other.getSeverity() != Severity.MILD) return 1;
		// Si mi urgencia es alta y la del otro no lo es, yo tengo prioridad. 
		if (severity == Severity.VITAL && other.getSeverity() != Severity.VITAL) return -1;
		// Si mi urgencia es media...
		if (severity == Severity.SEVERE)
			// ... y la del otro es baja, yo tengo prioridad.
			if (other.getSeverity()== Severity.MILD) return -1;
			// ... y la del otro es alta, el otro tiene prioridad.
			else if(other.getSeverity() == Severity.VITAL) return 1;
		// Si tenemos la misma emergencia, ambos tienen la misma prioridad.
		return 0;
	}

	/***********************************
	 * @name setHoraSalida
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Asigna una hora de fin del tratamieto al paciente, en segundos
	 * 
	 * @param horaSalida --> Hora en segundos de la finalización de su tratamiento
	 * 
	 * @return this --> Referencia al paciente que hizo la llamada
	 ***********************************/
	public Paciente setHoraSalida (Long horaSalida) {
		finTratamiento = horaSalida;
		return this;
	}
	

	/***********************************
	 * @name toString
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Devuelve el DNI y la hora formateada del fin del tratamiento del paciente,
	 * 				para la hora se pasan los segundos a las unidades necesarias.
	 * 
	 * @return String
	 ***********************************/
	public String toString() {
		return String.format("| DNI: %s | Gravedad: %6s | Especialidad: %12s | Hora de salida: %02d:%02d:%02d |", dni, severity, speciality, finTratamiento/hora, (finTratamiento/minuto)%60, finTratamiento%60);
	}
}
