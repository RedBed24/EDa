package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\n>---------------------------------<MENÚ>---------------------------------<\n\t "
			+ "0: Salir del programa\n A) Operaciones básicas\n\t 1: Crear un nuevo tren \n\t 2: Mostrar tren\n\t 3: Comprar billetes\n "
			+ "B) Operaciones del TAD\n\t 4: Número de pasajeros en el tren - numPasajeros\n\t 5: Estado del tren - trenLleno \n\t "
			+ "6: Reservar un asiento - reservarAsiento\n C) Otras operaciones \n\t 7: Parámetros generales del tren"
			+ "\n\t 8: Consultar ocupante de un asiento\n\t 9: Mostrar los identificadores de todos los asientos del tren \n \t"
			+ "10: Eliminar reservas de una persona \n \t11: Modificar identificadores de las reservas de alguien\n\t"
			+ "12: Existencia de un identificador en el tren\n--------------------------------------------------------------------------"
			+ "\nIntroduce una opción: ";
	final static String BIENVENIDA = "\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TEÓRICO *\n*****************************************";
	
	public static void main(String[] args) {
		// Bienvenida
			System.out.println(BIENVENIDA);
			
			menú();	
	}

	public static void menú() {
		Tren tren = null;
		while (true) { 
			try {
				// Comprobación de inicio de programa
				if(tren == null) tren = crearTren();
				
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
						System.out.println(tren.reservarAsiento(pedirIdentificador("Introduzca el nombre del ocupante del asiento a reservar: ")));
						break;
						
					// Opción 7 - Parámetros generales del tren
					case 7:
						imprimirParámetrosTren(tren);
						break;
						
					// Opción 8 -  Consultar ocupante de un asiento
					case 8:
						consultarOcupanteAsiento(tren);
						break;
					
					// Opción 9 - Mostrar los identificadores de todos los ocupantes 
					case 9:
						System.out.println(tren.mostrarOcupantesTren());
						break;
						
					// Opción 10 - liberarAsiento
					case 10:
						liberarAsientos(tren);
						break;
					
					// Opción 11 - Modificar identificadores de las reservas de alguien
					case 11:
						modificarIdentificadoresDe(tren);
						break;
					
					// Opción 12 - Existencia de un identificador en el tren
					case 12:
						existenciaIdentificador(tren);
						break;
					// Opción de salida	
					case 0:
						TECLADO.close(); return;
					
					// Se ha introducido una opción fuera de las contempladas
					default: System.err.println("Opción seleccionada inválida");
				}
				
			} catch (InputMismatchException e) {
				System.err.println("\nError al introducir la selección del menú o algún parámetro numérico\n");
				TECLADO.next(); // Se quita el valor erróneo del búffer
				
			} catch(IllegalArgumentException e) {
				System.err.println(e.getMessage());

			} catch (Exception e) {
				System.err.println("\nError inesperado, se finalizará el programa.");
				return ;
			}

		}

	}
	
	public static Tren crearTren() {
		
			System.out.print("Introduzca el número máximo de vagones para el tren: ");
			final int numMáxVagones = TECLADO.nextInt();
		
			System.out.print("Introduzca el número de filas por vagón: ");
			final int numFilasVagón = TECLADO.nextInt();

		return new Tren(numMáxVagones, numFilasVagón);
	}
	
	public static void comprarBilletes(Tren tren) {
		
		String identificador = pedirIdentificador("Introduzca el nombre de quien realiza las reservas: ");
		
		System.out.print("Introduzca la cantidad de asientos que desea reservar: ");
		final int numReservas = TECLADO.nextInt();
		if(numReservas < 1 || numReservas > tren.capacidadTren()) throw new IllegalArgumentException("\nNo se puede reservar la cantidad de "
				+ "asientos indicada. No introduzca un número negativo ni una cantidad de asientos superior a la capacidad del tren\n");
		
		for (int i = 0; i < numReservas; i++)
			tren.reservarAsiento(identificador+(i+1)); 
		
		System.out.println("\n<Se han reservado correctamente " + numReservas + " asientos>");
	}
	
	public static void imprimirParámetrosTren(Tren tren) {
		 // Parámetros fijos
		System.out.println("\u001B[34m Parámetros fijos\u001B[0m");
		System.out.println("<Capacidad del tren: " + tren.capacidadTren()+">"); // Capacidad del tren
		System.out.println("<Capacidad de cada vagón: " + tren.capacidadVagón()+">"); // Capacidad de cada vagón
		System.out.println("<Número de filas de cada vagón: " + tren.getFilasVagones()+">"); // Número de filas de cada vagón
		System.out.println("<Número de columnas de cada vagón: " + tren.getColumnasVagones()+">"); // Número de columnas de cada vagón
		System.out.println("<Número máximo de vagones: " + tren.getNumMáxVagones()+">"); // Número máximo de vagones
		// Parámetros variables
		System.out.println("\u001B[33m Parámetros variables\u001B[0m"); 
		System.out.println("<Número de vagones actualmente: " + tren.getNumVagones()+">"); // Número de vagones actualmente
		System.out.println("<Número de asientos ocupados: " + tren.ocupadosTren()+">"); // Número de asientos ocupados
		System.out.println("<Número de asientos libres: " + tren.libresActualmente()+">"); // Número de asientos libres actualmente
		System.out.println("<Número de asientos disponibles: " + tren.libresTren()+">"); // Número de asientos disponibles (libres en el tren)
		System.out.println("<El tren está lleno: " + tren.isLleno()+">"); // ¿El tren está lleno?
		System.out.println("<El tren está vacío: " + tren.isVacio()+">"); // ¿El tren está vacío?
	}
	
	public static void consultarOcupanteAsiento(Tren tren) {
		
		String identificador;
		final int numVagón, fila, columna;
		
		System.out.print("Introduzca el vagón donde realizará la consulta [1," + tren.getNumVagones() + "] -> ");
		numVagón = TECLADO.nextInt() - 1;
		if(numVagón < 0 || numVagón >= tren.getNumVagones()) throw new IllegalArgumentException("\nNúmero de vagón excedido - Rango de vagones [1,"+tren.getNumVagones()+"]\n");
		
		System.out.print("Introduzca la fila del asiento [1," + tren.getFilasVagones()+ "] -> ");
		fila = TECLADO.nextInt() - 1;
		if(fila < 0 || fila >= tren.getFilasVagones()) throw new IllegalArgumentException("\nNúmero de fila excedida - Rango de filas [1,"+tren.getFilasVagones()+"]\n");
		
		System.out.print("Introduzca la posición del asiento (VentanaIzq, PasilloIzq, PasilloDer y VentanaDer) -> ");
		switch(TECLADO.next().toLowerCase()) {
			case "ventanaizq": columna = 0; break;
			case "pasilloizq": columna = 1; break;
			case "pasilloder": columna = 2; break;
			case "ventanader": columna = 3; break;
			default: throw new IllegalArgumentException("\nPosición no reconocida\n");
		}
		
		if((identificador = tren.getOcupanteAsientoVagón(numVagón, fila, columna)) != null)
			System.out.println("\n<Vagón: " + (numVagón+1) + " | Fila: " + (fila+1) + " | Columna: " + (columna+1) + " | ID: " + identificador + ">");
		else
			System.out.println("\n<Vagón: " + (numVagón+1) + " | Fila: " + (fila+1) + " | Columna: " + (columna+1) + " | Asiento libre>");
		
	}
	
	public static void liberarAsientos(Tren tren) {
		String identificador = pedirIdentificador("Introduzca el nombre de quien desea eliminar todas sus reservas: ");
		
		if(tren.identificadorEnUso(identificador))
			System.out.println(tren.liberarAsiento(identificador));
		else // La persona con el identificador indicado no tiene ninguna reserva, por lo que no se encuentra en el tren.
			throw new IllegalArgumentException("\n\""+ identificador + "\" no tiene ninguna reserva en nuestro tren\n");
		
	}
	
	public static void modificarIdentificadoresDe(Tren tren) {
		String antiguoOcupante = pedirIdentificador("Introduzca el identificador del antiguo ocupante: ");
		String nuevoOcupante = pedirIdentificador("Introduzca el identificador del nuevo ocupante: ");
		
		if(tren.identificadorEnUso(antiguoOcupante)) {
			tren.setOcupanteAsientoVagón(antiguoOcupante, nuevoOcupante);
			System.out.println("<Los identificadores correspondientes a \"" +antiguoOcupante + "\" se han modificado a \""+ nuevoOcupante + "\">");
		} else
			throw new IllegalArgumentException("\nNo había ningún ocupante en el tren con el identificador antiguo indicado\n");

	}
	
	public static void existenciaIdentificador(Tren tren) {
		
		String identificador = pedirIdentificador("Introduzca el identificador a consultar: ");
		
		if(tren.identificadorEnUso(identificador))
			System.out.println("<El identificador \"" + identificador + "\" está en uso>");
		else
			System.out.println("<El identificador \"" + identificador + "\" aún no se ha usado por nadie>");
	}
	
	public static String pedirIdentificador(String mensaje) {
		String identificador;
		System.out.print(mensaje);
		identificador = TECLADO.next();

		if(Character.isDigit(identificador.charAt(identificador.length()-1))) 
			throw new IllegalArgumentException("\nNo introduzca un identificador con algún número al final\n");
		if(identificador.equals("null") || identificador.startsWith("null"))
			throw new IllegalArgumentException("\nEl identificador null es inválido\n");

		return identificador;
	}
}
