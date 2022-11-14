
/*********************************************************************
* @name Constantes
* 
* @authors DJS - B2 - 03
* 
* @description Interfaz que contiene las constantes necesarias para la simulaci�n.
***********************************************************************/

package B2_03_colas;

public interface Constantes {
	
	// Para poder llevar la temporalizaci�n del problema en segundos
	long minuto = 60;
	long hora = 3600;
	
	// Periodo de llegada de un paciente en segundos
	long tick = 10;  
	
	// Restricciones acerca de las colas
	int capacidadTraumatología = 20; // Capacidad m�xima de pacientes de la cola de tratamiento de traumatolog�a
	long duraciónAtenciónNeurología = hora; // Especifica durante cu�nto tiempo neurolog�a estar� funcional durante un d�a
}

