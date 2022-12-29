package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\n>---------------------------------<MENÚ>---------------------------------<\n\t "
			+ "0: Salir del programa\n A) Operaciones básicas\n\t 1: Crear un nuevo tren \n\t 2: Mostrar tren\n\t 3: Comprar billetes\n "
			+ "B) Operaciones del TAD\n\t 4: Número de pasajeros en el tren - numPasajeros\n\t 5: Estado del tren - trenLleno \n\t "
			+ "6: Reservar un asiento - reservarAsiento\n C) Otras operaciones \n\t 7: Consultar ocupante de un asiento\n\t "
			+ "8: Mostrar los identificadores de todos los asientos del tren \n \t 9: Eliminar reservas de una persona"
			+ "\n--------------------------------------------------------------------------\nIntroduce una opción: ";
	final static String BIENVENIDA = "\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TEÓRICO *\n*****************************************";
	
	public static void main(String[] args) {
		// Bienvenida
			System.out.println(BIENVENIDA);
		
		try {
			Tren tren = crearTren();
			menú(tren);
			
		} catch(Exception e) {
			System.err.println("Error inesperado, se finalizará el programa");
			return;
		}
	}

	public static void menú(Tren tren) {
		while (true) { 
			try {
				// Muestra del menú
				System.out.print(MENÚ);
				// Comprobación de la opción elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Opción 1 - Crear un nuevo tren
					case 1:
							tren = crearTren();
							System.out.println("\n<El nuevo tren se ha creado correctamente>");
							break;
							
					// Opción 2 - Mostrar tren
					case 2: 
						System.out.println(tren);
						break;
					
					// Opción 3 - Comprar N billetes
					case 3:
						comprarBilletes(tren);
						break;
					
					// Opción 4 - numPasajeros
					case 4:
						System.out.println("<Pasajeros a bordo: "+ tren.ocupadosTren() + ">");
						break;
					
					// Opción 5 - trenLleno
					case 5:
						if(tren.isLleno())
							System.out.println("<El tren está completamente lleno. Todos los asientos están ocupados y no se pueden añadir más vagones al tren>");
						else
							System.out.println("<El tren no está completamente lleno. Algún asiento está libre y/o se pueden añadir más vagones al tren>");
						break;
						
					// Opción 6 - reservarAsiento
					case 6:
						reservarAsiento(tren);
						break;
					
					// Opción 7 -  Consultar ocupante de un asiento
					case 7:
						consultarOcupanteAsiento(tren);
						break;
					
					// Opción 8 - Mostrar los identificadores de todos los ocupantes
					case 8:
						System.out.println(tren.mostrarOcupantesTren());
						break;
						
					// Opción 9 - liberarAsiento
					case 9:
						liberarAsientos(tren);
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
				
			} catch (ProblemInTrainException e) {
				System.err.println(e.getMessage());
				
			} catch (Exception e) {
				System.err.println("Error inesperado, se finalizará el programa.");
				return ;
			}

		}

	}
	
	public static Tren crearTren() {
		int numMáxVagones, numFilasVagón;
		Tren tren = null;
		while(true) {
			try {
			System.out.print("Introduzca el número máximo de vagones para el tren: ");
			numMáxVagones = TECLADO.nextInt();
		
			System.out.print("Introduzca el número de filas por vagón: ");
			numFilasVagón = TECLADO.nextInt();
			
			tren = new Tren(numMáxVagones, numFilasVagón);
			break;
			
			} catch(ProblemInTrainException e) {
				System.err.println(e.getMessage());
			} catch(InputMismatchException e) {
				System.err.println("\nError al indicar los parámetros numéricos\n");
				TECLADO.next(); // Se quita el valor erróneo del búffer
			}
		}
		return tren;
	}
	
	public static void comprarBilletes(Tren tren) {
		String identificador;
		System.out.print("Introduzca el identificador de quien realiza las reservas: ");
		identificador = TECLADO.next();
		System.out.print("Introduzca la cantidad de asientos que desea reservar: ");
		final int numReservas = TECLADO.nextInt();
		for (int i = 0; i < numReservas; i++)
			tren.reservarAsiento(identificador+(i+1)); 
		System.out.println("\n<Se han reservado correctamente " + numReservas + " asientos>");
	}
	
	public static void consultarOcupanteAsiento(Tren tren) {
		String identificador;
		final int numVagón, fila, columna;
		System.out.print("Introduzca el vagón donde realizará la consulta [1," + tren.getNumVagones() + "] -> ");
		numVagón = TECLADO.nextInt() - 1;
		if(numVagón < 0 || numVagón >= tren.getNumVagones()) throw new IllegalArgumentException("Número de vagón sobrepasado");
		System.out.print("Introduzca la fila del asiento [1," + tren.getFilasVagones()+ "] -> ");
		fila = TECLADO.nextInt() - 1;
		if(fila < 0 || fila >= tren.getFilasVagones()) throw new IllegalArgumentException("Número de fila sobrepasada");
		System.out.print("Introduzca la posición del asiento (VentanaIzq, PasilloIzq, PasilloDer y VentanaDer) -> ");
		switch(TECLADO.next().toLowerCase()) {
			case "ventanaizq": columna = 0; break;
			case "pasilloizq": columna = 1; break;
			case "pasilloder": columna = 2; break;
			case "ventanader": columna = 3; break;
			default: throw new IllegalArgumentException("Posición no reconocida");
		}
		
		if((identificador = tren.getOcupanteAsientoVagón(numVagón, fila, columna)) != null)
			System.out.println("\n<Vagón: " + numVagón + " | Fila: " + fila + " | Columna: " + columna + " | ID: " + identificador + ">");
		else
			System.out.println("\n<Vagón: " + numVagón + " | Fila: " + fila + " | Columna: " + columna + " | Asiento libre>");
		
	}
	
	public static void reservarAsiento(Tren tren) {
		String identificador;
		System.out.print("Introduzca el nombre del ocupante del asiento a reservar: ");
		identificador = TECLADO.next();
		if(Character.isDigit(identificador.charAt(identificador.length()-1))) 
			throw new IllegalArgumentException("Los identificadores con números al final se conservan para la reserva de varios asientos");
		System.out.println(tren.reservarAsiento(identificador));
	}
	
	public static void liberarAsientos(Tren tren) {
		String identificador;
		
		System.out.print("Introduzca el nombre de quien desea eliminar todas sus reservas: ");
		identificador = TECLADO.next();
		// La persona con el identificador indicado no tiene ninguna reserva, por lo que no se encuentra en el tren.
		if(!tren.identificadorEnUso(identificador)) throw new IllegalArgumentException("\""+ identificador + "\" no tiene ninguna reserva en nuestro tren");
		
		//for(int i = 1; tren.identificadorEnUso(identificador+i);i++)
			//System.out.println(tren.liberarAsiento(identificador+i));
		
		if(tren.identificadorEnUso(identificador))
			System.out.println(tren.liberarAsiento(identificador));
			
		
		
	}
}
