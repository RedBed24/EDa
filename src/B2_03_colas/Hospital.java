
/*********************************************************************
* @name Hospital
* 
* @authors DJS - B2 - 03
* 
* @description Contiene las colas de pacientes de las diferentes especialidades.
* 			   Contiene m�todos para tratar y a�adir a los pacientes.
***********************************************************************/

package B2_03_colas;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import B2_03_colas.Paciente.*;

public class Hospital implements Constantes {

	private final Queue        <Paciente> traumatología; // Cola que alberga los pacientes a tratar en la secci�n de traumatolog�a.
	private final PriorityQueue<Paciente> cardiología;   // Cola que alberga los pacientes a tratar en la secci�n de cardiolog�a.
	private final Queue        <Paciente> neurología;    // Cola que alberga los pacientes a tratar en la secci�n de neurolog�a.
	
	/***********************************
	 * @name Constructor del Hospital
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Inicializa los atributos de una instancia Hospital. Usa la variable
	 * 				capacidadTraumatolog�a de la interfaz Constantes.
	 ***********************************/
	public Hospital() {
		
		// Implementaci�n de la cola de traumatolog�a usando memoria est�tica.
		traumatología = new ArrayBlockingQueue<Paciente>(capacidadTraumatología);
		
		// Implementaci�n de la cola de cardiolog�a usando una cola de prioridad.
		cardiología= new PriorityQueue<Paciente>();
		
		// Implementaci�n de la cola de neurolog�a usando memoria din�mica.
		neurología= new LinkedBlockingQueue<Paciente>();
	}
	
	/***********************************
	 * @name a�adirPaciente
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Dado un paciente correctamente creado, lo a�ade a la cola que deba atendiendo a su especialidad.
	 * 
	 * @param paciente --> Paciente que se a�adir� a una cola determinada.
	 * 
	 * @throws IllegalStateException --> Como la cola de traumatolog�a se implementa con memoria est�tica, puede ocurrir que
	 * 									 ya est� llena y, por tanto, el paciente no pueda ser a�adido a la cola.
	 ***********************************/
	public void añadirPaciente(final Paciente paciente) {
		try {
			switch (paciente.getSpeciality()) {
				case TRAUMATOLOGY: traumatología.add(paciente); break;
				case CARDIOLOGY:   cardiología.add(paciente);   break;
				case NEUROLOGY:    neurología.add(paciente);    break;
			}
		} catch(IllegalStateException e) {
			System.out.println("La cola de traumatolog�a est� llena. El paciente " + paciente + " no puede pasar.");
		}
	}
	
	/***********************************
	 * @name triage
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Asigna los valores de los atributos de un paciente.
	 * 
	 * @param l�neaLe�da --> Informaci�n del paciente contenida en una l�nea del fichero. 
	 * 						 Debe ser de la forma: DNI;Especialidad;Urgencia
	 * @param horaLlegada --> Tiempo en el que llega el paciente al hospital.
	 * 
	 * @throws InvalidPatientInfoException --> Cuando la informaci�n del paciente no coincide con la que se espera
	 * @throws IndexOutOfBoundsException --> Si la l�neaLe�da no contiene la informaci�n necesaria. Unchecked exception
	 * 
	 * @return Paciente creado a partir de los valores de sus atributos
	 ***********************************/
	public Paciente triage(final String líneaLeída, final long horaLlegada) throws InvalidPatientInfoException {
		
		// Guarda las subcadenas de la l�nea le�da del fichero al separarlos mediante un ;
		final String []infoPaciente= líneaLeída.split(";");
		
		// DNI del paciente, se usar� para crear el paciente.
		final String DNI= infoPaciente[0];
		
		// Especialidad del problema del paciente. Usada para saber a qu� cola pertenece un paciente.
		Speciality speciality;
		switch (infoPaciente[1]) {
			case "Traumatology": speciality = Speciality.TRAUMATOLOGY; break;
			case "Cardiology":   speciality = Speciality.CARDIOLOGY;   break;
			case "Neurology":    speciality = Speciality.NEUROLOGY;    break;
			// Ante cualquier otra especialidad, se lanza la excepci�n de informaci�n inv�lida.
			default: throw new InvalidPatientInfoException("--> DNI: "+ DNI+ ", Speciality: '"+infoPaciente[1]+"'...?");
		}
		
		// Urgencia del paciente. Usada para conocer las prioridades de los pacientes en cardiolog�a. 
		Severity severity;
		switch (infoPaciente[2]) {
			case "Mild":   severity= Severity.MILD;   break;
			case "Severe": severity= Severity.SEVERE; break;
			case "Vital":  severity= Severity.VITAL;  break;
			// Ante cualquier otro nivel de gravedad, se lanza la excepci�n de informaci�n inv�lida.
			default: throw new InvalidPatientInfoException("--> DNI: "+ DNI+ ", Severity: '"+infoPaciente[2]+"'...?");
		}
		
		return new Paciente(DNI, severity, speciality, horaLlegada);
	}
	
	/***********************************
	 * @name toString
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Devuelve toda la informaci�n del hospital con formato. Esto es, todos los pacientes que han sido 
	 * 				atendidos en cada especialidad, adem�s de los valores de todos sus atributos: DNI, gravedad, hora
	 * 				de llegada en segundos y hora de llegada en horas.
	 * 
	 * @return Toda la informaci�n del hospital
	 ***********************************/
	public String toString() {
		String devolver = "--------------------------------\nHISTORIAL DE PACIENTES TRATADOS"
				+ "\n-------------------------------- \nTRAUMATOLOG�A\n  DNI   Gravedad    Llegada     En horas";
		while(!traumatología.isEmpty())
			devolver += "\n"+traumatología.poll();
		
		devolver += "\n\nCARDIOLOG�A\n  DNI   Gravedad    Llegada     En horas";
		
		while(!cardiología.isEmpty())
			devolver += "\n"+cardiología.poll();
	
		devolver += "\n\nNEUROLOG�A\n  DNI   Gravedad    Llegada     En horas";
		
		/* Neurolog�a solo atiende una hora, por lo que si ya ha transcurrido este periodo de tiempo, cerrar� sin poder
		 * atender a m�s pacientes a pesar de que hayan llegado al hospital a una hora determinada. */
		while(!neurología.isEmpty()) 
			if(neurología.peek().getHoraLlegada() < duraciónAtenciónNeurología)
				devolver+= "\n"+neurología.poll();
			else
				devolver+= "\n"+neurología.poll()+" Neurolog�a est� cerrado. El paciente no ha llegado a tiempo.";
		
		return devolver;
	}
	
}
