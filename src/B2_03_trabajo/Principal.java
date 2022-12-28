package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\nIntroduce una opción:\n\t 0: Salir del programa\n A) Operaciones básicas\n\t 1: Crear un nuevo tren \n\t 2: Mostrar tren\n\t 3: Comprar billetes\n "
			+ "B) Operaciones del TAD\n\t 4: Número de pasajeros en el tren - numPasajeros\n\t 5: Estado del tren - trenLleno \n\t 6: Reservar un asiento - reservarAsiento\n "
			+ "C) Operaciones extra\n\t 7: Eliminar reservas de una persona - liberarAsiento\n\t 8: Consultar ocupante de un asiento\n "
			+ "\t 9: Mostrar todos los ocupantes del tren";
	
	public static void main(String[] args) {
		// Bienvenida
					System.out.println("\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TEÓRICO *\n*****************************************");
					
		Tren tren = new Tren(2, 2);
		menú(tren);
	}

	public static void menú(Tren tren) {
		String identificador;
		while (true) { 
			try {
				// Muestra del menú
				System.out.println(MENÚ);
				// Comprobación de la opción elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Crear un nuevo tren
					case 1:
							System.out.print("Introduzca el número máximo de vagones para el tren: ");
							final int numMáxVagones = TECLADO.nextInt();
							System.out.print("Introduzca el número de filas por vagón:");
							final int numFilasVagón = TECLADO.nextInt();
							tren = new Tren(numMáxVagones, numFilasVagón);
							System.out.println("\n<El nuevo tren se ha creado correctamente>");
							break;
							
					// Mostrar tren
					case 2: 
						System.out.println(tren);
						break;
					
					// Comprar N billetes
					case 3:
						System.out.print("Introduzca el identificador de quien realiza las reservas: ");
						identificador = TECLADO.next();
						System.out.print("Introduzca la cantidad de asientos que desea reservar: ");
						final int numReservas = TECLADO.nextInt();
						for (int i = 0; i < numReservas; i++)
							tren.reservarAsiento(identificador+(i+1)); // TODO: verProblema en consulta 7. El enunciado nos exige esto. Ya veré qué hago con la consulta 7
						System.out.println("Se han reservado correctamente " + numReservas + " asientos");
						break;
					
					// numPasajeros
					case 4:
						System.out.println("Pasajeros a bordo -> "+ tren.ocupadosTren());
						break;
					
					// trenLleno
					case 5:
						if(tren.isLleno())
							System.out.println("<El tren está completamente lleno. Todos los asientos están ocupados y no se pueden añadir más vagones al tren>");
						else
							System.out.println("<El tren no está completamente lleno. Algún asiento está libre y/o se pueden añadir más vagones al tren>");
						break;
						
					// reservarAsiento
					case 6:
						System.out.print("Introduzca el nombre del ocupante del asiento a reservar: ");
						System.out.println(tren.reservarAsiento(TECLADO.next()));
						break;
					
					// liberarAsiento
					/* TODO: Surge un problema importante. Si ejecutamos la consulta 3, esta consulta "no funciona" por el identificador asignado en dicha opción.
					Digo entre comillas porque realmente sí funciona, siempre que pongas Pepe1, Pepe2, Pepe3...
					En resumen, si en la consulta 3 introduces como identificador "Willy" y reservas 2 asientos, el identificador de ambos asientos serán
					"Willy1" y "Willy2", por lo que esta consulta si pones Willy debería borrar ambas, pero no lo hace.
					*/case 7:
						System.out.print("Introduzca el nombre del ocupante del asiento a reservar: ");
						identificador = TECLADO.next();
						if(tren.identificadorEnUso(identificador))
							System.out.println(tren.liberarAsiento(identificador));
						else // La persona con el identificador indicado no tiene ninguna reserva, por lo que no se encuentra en el tren.
							throw new IllegalArgumentException("\""+ identificador + "\" no tiene ninguna reserva en nuestro tren");
						break;
					
					// Consultar ocupante de un asiento
					case 8:
						break;
						
					// Mostrar reservas de todos los ocupantes del tren
					case 9:
						System.out.println(tren.mostrarOcupantesTren());
						break;
						
					// Opción de salida	
					case 0:
						TECLADO.close(); return;
					
					// Se ha introducido una opción fuera de las contempladas
					default: System.err.println("Opción seleccionada inválida");
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Error al introducir la selección del menú.");
				TECLADO.next(); // Se quita el valor erróneo del búffer
				
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				
			} catch (Exception e) {
				System.err.println("Error inesperado, se finalizará el programa.");
				return ;
			}

		}

	}
}
