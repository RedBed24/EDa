
/*********************************************************************
* @name Principal
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la funci�n main, que contiene la lectura del fichero
* 			   y la encargada de simular el funcionamiento del hospital.
***********************************************************************/

package B2_03_colas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Principal implements Constantes {
	
	/***********************************
	 * @name main
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee el fichero e intenta ejecutar la simulaci�n del tratamiento de pacientes en un hospital.
	 * 
	 * @files "pacientes.txt"
	 * 
	 * @throws FileNotFoundException --> Lanzada cuando no se ha podido leer el fichero correctamente.
	 ***********************************/
	
	public static void main(String[] args) {
		
		// Bienvenida
		System.out.println("\n*******************************\n* ESTRUCTURA DE DATOS - COLAS *\n*******************************");
		
		try {
				
		// Lectura del fichero pacientes.txt
			File f = new File("pacientes.txt");
			Scanner fichero = new Scanner(f);
		// Simulaci�n del hospital
			System.out.println(simular(fichero));
		// Cierre del fichero
			fichero.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("\nEl fichero no se ha encontrado.\n" + e.toString());
				
		} catch (Exception e) {
			System.out.println("Error inesperado: "+ e);
		}	
	}
	
	/***********************************
	 * @name simular
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Simula la llegada de los pacientes cada tick y el tratamiento de los mismos en el hospital.
	 *  
	 * @param fichero --> Scanner abierto con los datos de los pacientes que deber�an llegar.
	 * 
	 * @return String con la informaci�n resultante de la simulaci�n.
	 ***********************************/
	
	public static String simular(Scanner fichero) {
		
		// Variable usada para tener en cuenta la hora del d�a en segundos.
		long tiempo = 0;
		// Creaci�n del objeto Hospital sobre el que se va a realizar la simulaci�n
		final Hospital hospital= new Hospital();
		
		// Ejecuci�n de la simulaci�n mientras siga habiendo pacientes sin tratar en el hospital.
		while(fichero.hasNextLine())
			try {
				// Creaci�n del paciente
				Paciente paciente = hospital.triage(fichero.nextLine(), tiempo);
				// Adici�n del paciente creado
				hospital.añadirPaciente(paciente);
				// Manejo del tiempo
				tiempo += tick;

			} catch (InvalidPatientInfoException e) {
				System.out.println(String.format("La informaci�n del paciente %s llegada a la hora: %02d:%02d:%02d no es la esperada. (t = %2d)", e.getMessage(), (tiempo/hora)%24, (tiempo/minuto)%60, tiempo%60, tiempo));
				
			} catch (IndexOutOfBoundsException e) {
				System.out.println(String.format("La informaci�n del paciente llegada a la hora: %02d:%02d:%02d no es suficiente. (t = %2d)", (tiempo/hora)%24, (tiempo/minuto)%60, tiempo%60, tiempo));
			}

		return "\n"+hospital;
	}

}
