
/*********************************************************************
* @name Constantes
* 
* @authors DJS - B2 - 03
* 
* @description Interfaz que contiene las constantes necesarias para la simulación.
***********************************************************************/

package Colas;

public interface Constantes {
	
	// Para poder llevar la temporalización del problema en segundos
	long minuto = 60;
	long hora = 3600;
	
	// Periodo de llegada de un paciente en segundos
	long tick = 10;  
	
	// Restricciones acerca de las colas
	int capacidadTraumatología = 20; // Capacidad máxima de pacientes de la cola de tratamiento de traumatología
	long duraciónAtenciónNeurología = hora; // Especifica durante cuánto tiempo neurología estará funcional durante un día
}

