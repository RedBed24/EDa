
/*********************************************************************
* @name Principal
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la función main, que contiene la lectura del fichero
* 			   y la encargada de simular el funcionamiento del hospital.
***********************************************************************/

package Colas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Colas.InvalidPatientInfoException;

public class Principal implements Constantes {
	
	/***********************************
	 * @name main
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee el fichero e intenta ejecutar la simulación del tratamiento de pacientes en un hospital.
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
				
		// Simulación del hospital
			System.out.println(simular(fichero));
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
	 * @description Simula el paso del tiempo teniendo en cuenta los días, la llegada de los 
	 * 				pacientes cada tick y el tratamiento de los mismos en el hospital.
	 *  
	 * @param fichero --> Scanner abierto con los datos de los pacientes que deberían llegar.
	 * 
	 * @return String con la información resultante de la simulación. Esto es, las 3 colas del registro del hospital.
	 ***********************************/
	
	public static String simular(Scanner fichero) {
		
		// Variable usada para tener en cuenta la hora del día en segundos.
		long horaDelDía = 0;
		
		// Creación del objeto Hospital sobre el que se va a realizar la simulación
		final Hospital hospital= new Hospital();
		
		// Ejecución de la simulación mientras siga habiendo pacientes sin tratar en el hospital.
		do {
			try {
				if (fichero.hasNextLine()) 
					hospital.triage(fichero.nextLine());
			
			} catch (InvalidPatientInfoException e) {
				System.out.println(String.format("La informacion del paciente %s llegado a la hora: %02d:%02d:%02d no es la esperada.", e.getMessage(), (horaDelDía/hora)%24, (horaDelDía/minuto)%60, horaDelDía%60));
				
			} catch (IndexOutOfBoundsException e) {
				System.out.println(String.format("La informacion del paciente llegado a la hora: %02d:%02d:%02d no es suficiente.", (horaDelDía/hora)%24, (horaDelDía/minuto)%60, horaDelDía%60));
			}
			
			/* Tratamiento de un paciente durante el tiempo transcurrido en este tick con la correspondiente hora del día.
			 * Aquí es donde "pasa" el tiempo. */
			hospital.tratar(horaDelDía, tick);
			
			// Manejo de la hora del día
			if (horaDelDía<dia)
				horaDelDía += tick; // No ha pasado un día, por lo que se añade el tiempo transcurrido.
			else
				horaDelDía = tick; // Se resetea el día, inicializando la hora del día a tick ya que ese tiempo ya ha transcurrido.

		} while(!hospital.isEmpty());
		
		// Devuelve la información de los registros del hospital.
		return "\n"+hospital;
	}

}
