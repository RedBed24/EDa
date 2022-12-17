package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\nIntroduce una opción:\n 1: Ver el tren\n 2: Consultar ocupante de un asiento\n "
			+ "3: numPasajeros\n 4: trenLleno\n 5: reservarAsiento\n 6: liberarAsiento\n 7: Salir del programa";
	
	public static void main(String[] args) {
		// Bienvenida
					System.out.println("\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TEÓRICO *\n*****************************************");
					
		Tren tren = new Tren(1, 6);
		menú(tren);
		System.out.println(tren);
	}
	public static void menú(Tren tren) {
		
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
						System.out.println(tren.trenLleno());
						break;
						
					// reservarAsiento
					case 5:
						System.out.println(tren.reservarAsiento("Hola"));
						break;
					
					// liberarAsiento
					case 6:
						System.out.println();
						break;
					
					// TODO: Añadir vagón¿?
					case 7: 
						System.out.println();
						break;
						
					// Opción de salida	
					case 0:
						TECLADO.close(); return;
					
					// Se ha introducido una opción fuera de las contempladas
					default: System.out.println("Opción seleccionada inválida");
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Error al introducir la selección del menú.");
				TECLADO.next(); // Se quita el valor erróneo del búffer
				
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}
		}

	}
}
