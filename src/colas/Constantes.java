
/*********************************************************************
* @name Constantes
* 
* @authors DJS - B2 - 03
* 
* @description Interfaz que contiene las constantes necesarias para la simulación.
***********************************************************************/

package Colas;

public interface Constantes {
	
	// Para poder llevar la temporalización del problema
	long segundo = 1;
	long minuto = 60*segundo;
	long hora = 60*minuto;
	long dia = 24*hora;
	
	// Periodo de llegada de un paciente
	long tick = 10*segundo; 
	
	// Tiempo de atención para un paciente
	long tiempoAtención = 10*minuto; 
	
	// Restricciones acerca de las colas
	int capacidadTraumatología = 20; // Capacidad máxima de pacientes de la cola de tratamiento de traumatología
	long horaAtenciónNeurología= 9*hora; // Hora del día en la que neurología empezará a atender
	long duraciónAtenciónNeurología= 1*hora; // Especifica durante cuánto tiempo neurología estará funcional durante un día
}

