
/*********************************************************************
* @name Paciente
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información acerca de los pacientes, tanto sus características como los métodos que las manejan.
***********************************************************************/

package Colas;


public class Paciente implements Comparable<Paciente>, Constantes {
	
	// Enumeraciones para poder usar valores entendibles en vez de números. 
	public enum Severity {MILD, SEVERE, VITAL}; 
	public enum Speciality {TRAUMATOLOGY, CARDIOLOGY, NEUROLOGY};
	
	private final String dni;	// DNI del paciente.
	private final Severity severity;     // Gravedad del paciente.
	private final Speciality speciality; // Especialidad del problema del paciente.
	private long horaLlegada;	// Tiempo de llegada del paciente al hospital en segundos.
	
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
	 * @param horaLlegada --> Tiempo de llegada del paciente al hospital en segundos
	 ***********************************/
	public Paciente(final String dni, final Severity severity, final Speciality speciality,  final long horaLlegada) {
		super();
		this.dni = dni;
		this.severity = severity;
		this.speciality = speciality;
		this.horaLlegada = horaLlegada;
	}

	public Severity getSeverity() {
		return severity;
	}

	public Speciality getSpeciality() {
		return speciality;
	}
	
	public long getHoraLlegada() {
		return horaLlegada;
	}

	/***********************************
	 * @name compareTo
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Indica qué paciente tiene prioridad respecto a otro atendiendo a la urgencia de su enfermedad.
	 * 
	 * @param other --> Paciente con el que se compara
	 * 
	 * @return 1 --> Otro paciente tiene prioridad sobre el actual por tener una urgencia menor.
	 * @return -1 --> El paciente actual tiene preferencia sobre el otro paciente por tener una urgencia mayor.
	 ***********************************/
	public int compareTo(final Paciente other) {
		
		// Si tenemos la misma emergencia, el otro paciente de la cola tiene prioridad sobre mí por haber llegado antes.
		if (severity == other.getSeverity())
			if(horaLlegada > other.getHoraLlegada())
				return 1;
			else
				return -1;
		
		// Si mi urgencia es baja, el otro paciente tiene prioridad, ya que su prioridad será o SEVERE o VITAL.
		if (severity == Severity.MILD) return 1;
		// Si mi urgencia es alta, yo tengo prioridad, ya que la prioridad del otro paciente será o SEVERE O MILD. 
		if (severity == Severity.VITAL) return -1;
		// Si mi urgencia es media...
		if (severity == Severity.SEVERE)
			// ... y la del otro es baja, yo tengo prioridad.
			if (other.getSeverity() == Severity.MILD) return -1;
			// ... y la del otro es alta, el otro tiene prioridad.
			else if(other.getSeverity() == Severity.VITAL) return 1;
		return 0;
	}

	/***********************************
	 * @name toString
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Devuelve el DNI, la gravedad, el tiempo de llegada en segundos y
	 * 				el tiempo de llegada en formato de horas.
	 * 
	 * @return String con toda la información del paciente
	 ***********************************/
	public String toString() {
		return String.format("| %s |  %-6s  |   %-5d   |  %02d:%02d:%02d  |", dni, severity, horaLlegada, horaLlegada/hora, (horaLlegada/minuto)%60, horaLlegada%60);
	}
}
