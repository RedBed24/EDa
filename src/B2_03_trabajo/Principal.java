package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\nIntroduce una opción:\n 1: Ver el tren\n 2: Consultar ocupante de un asiento\n "
			+ "3: numPasajeros\n 4: trenLleno\n 5: reservarAsiento\n 6: liberarAsiento\n 7: Reservar varios asientos\n 0: Salir del programa";
	
	public static void main(String[] args) {
		// Bienvenida
					System.out.println("\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TEÓRICO *\n*****************************************");
					
		Tren tren = new Tren(1, 6);
		menú(tren);
		System.out.println(tren);
	}

	public static void menú(Tren tren) {
		String identificador;
		while (true) { 
			try {
				// Muestra del menú
				System.out.println(MENÚ);
				// Comprobación de la opción elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Ver el tren
					case 1: 
						System.out.println(tren);
						break;
							
					// Consultar ocupante de un asiento
					case 2:
						break;
					
					// numPasajeros
					case 3:
						System.out.println(tren.numPasajeros());
						break;
					
					// trenLleno
					case 4:
						System.out.println(tren.isLleno());
						break;
						
					// reservarAsiento
					case 5:
						System.out.println("Introduzca un identificador para la compra de un asientos.");
						System.out.println(tren.reservarAsiento(TECLADO.next()));
						break;
					
					// liberarAsiento
					case 6:
						System.out.println("Introduzca el identificador del asiento a liberar.");
						System.out.println(tren.liberarAsiento(TECLADO.next()));
						break;
					
					// TODO: Añadir vagón¿? Se añaden automáticamente al llenar uno
					// Nos piden reservar x asientos
					case 7:
						System.out.println("Introduzca un identificador para la compra de varios asientos, se le añadirá automáticamente un número para diferenciarlos.");
						identificador = TECLADO.next();
						System.out.println("Introduzca la cantidad de reservas que quiera realizar.");
						final int reservas = TECLADO.nextInt();
						for (int i = 0; i < reservas; i++)
							// igual es muy redundante el sysout y deberíamos controlar el caso de null
							System.out.println(tren.reservarAsiento(identificador+i));
						System.out.println("Se han realizado " + reservas + " reservas.");
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