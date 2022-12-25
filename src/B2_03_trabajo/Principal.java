package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MEN� = "\nIntroduce una opci�n:\n 1: Ver el tren\n 2: Consultar ocupante de un asiento\n "
			+ "3: numPasajeros\n 4: trenLleno\n 5: reservarAsiento\n 6: liberarAsiento\n 7: Reservar varios asientos\n 0: Salir del programa";
	
	public static void main(String[] args) {
		// Bienvenida
					System.out.println("\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TE�RICO *\n*****************************************");
					
		Tren tren = new Tren(1, 6);
		men�(tren);
		System.out.println(tren);
	}

	public static void men�(Tren tren) {
		String identificador;
		while (true) { 
			try {
				// Muestra del men�
				System.out.println(MEN�);
				// Comprobaci�n de la opci�n elegida por el usuario
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
					
					// TODO: A�adir vag�n�? Se a�aden autom�ticamente al llenar uno
					// Nos piden reservar x asientos
					case 7:
						System.out.println("Introduzca un identificador para la compra de varios asientos, se le a�adir� autom�ticamente un n�mero para diferenciarlos.");
						identificador = TECLADO.next();
						System.out.println("Introduzca la cantidad de reservas que quiera realizar.");
						final int reservas = TECLADO.nextInt();
						for (int i = 0; i < reservas; i++)
							// igual es muy redundante el sysout y deber�amos controlar el caso de null
							System.out.println(tren.reservarAsiento(identificador+i));
						System.out.println("Se han realizado " + reservas + " reservas.");
						break;
						
					// Opci�n de salida	
					case 0:
						TECLADO.close(); return;
					
					// Se ha introducido una opci�n fuera de las contempladas
					default: System.err.println("Opci�n seleccionada inv�lida");
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Error al introducir la selecci�n del men�.");
				TECLADO.next(); // Se quita el valor err�neo del b�ffer
				
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println("Error inesperado, se finalizar� el programa.");
				return ;
			}

		}

	}
}