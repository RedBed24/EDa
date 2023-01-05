package B2_03_trabajo;

import java.util.InputMismatchException;
import java.util.Scanner;
/*********************************************************************
* Contiene la información la función main, que dirige el flujo del programa directamente al menú principal.
* Es un programa de prueba para comprobar que el TAD del tren funciona correctamente. 
***********************************************************************/
public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\n>---------------------------------<MENÚ>---------------------------------<\n\t "
			+ "0: Salir del programa\n A) Operaciones básicas\n\t 1: Crear un nuevo tren \n\t 2: Mostrar tren\n\t 3: Comprar billetes\n "
			+ "B) Operaciones del TAD exigidas\n\t 4: Número de pasajeros en el tren - numPasajeros\n\t 5: Estado del tren - trenLleno \n\t "
			+ "6: Reservar un asiento - reservarAsiento\n C) Otras operaciones \n\t 7: Parámetros generales del tren"
			+ "\n\t 8: Consultar ocupante de un asiento\n\t 9: Mostrar los identificadores de todos los asientos del tren \n \t"
			+ "10: Eliminar reservas de una persona \n \t11: Modificar identificadores de las reservas de alguien\n\t"
			+ "12: Existencia de un identificador en el tren\n--------------------------------------------------------------------------"
			+ "\nIntroduce una opción: ";
	final static String BIENVENIDA = "\n*****************************************\n* ESTRUCTURA DE DATOS - TRABAJO TEÓRICO *\n*****************************************";
	
	/***********************************
	 * Dirige el flujo del programa a un menú principal
	 ***********************************/
	public static void main(String[] args) {
		// Bienvenida
			System.out.println(BIENVENIDA);
		// Menú principal
			menú();	
	}

	/***********************************
	 * Imprime el menú y procesa las consultas según la opción seleccionada por el usuario. Si es la primera vez
	 * que se invoca, pedirá al usuario que introduzca los parámetros para crear un tren inicial.
	 * @throws IllegalArgumentException -> Captura cualquier error relacionado con el TAD del tren
	 * @throws InputMismatchException -> Lanzada cuando se indica una opción del menú en un formato incorrecto
	 * @throws Exception -> Ante un error inesperado, finaliza el programa
	 ***********************************/
	public static void menú() {
		Tren tren = null;
		while (true) { 
			try {
				// Comprobación de inicio de programa
				if(tren == null) tren = crearTren(); 
				// Impresión del menú
				System.out.print(MENÚ);
				// Comprobación de la opción elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Opción 1 - Crear un nuevo tren
					case 1:
							tren = crearTren();
							System.out.println("<El nuevo tren se ha creado correctamente>");
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
						System.out.print("Introduzca el nombre del ocupante del asiento a reservar: ");
						System.out.println(tren.reservarAsiento(TECLADO.next(),1));
						break;
						
					// Opción 7 - Parámetros generales del tren
					case 7:
						imprimirParámetrosTren(tren);
						break;
						
					// Opción 8 -  Consultar ocupante de un asiento
					case 8:
						consultarOcupanteAsiento(tren);
						break;
					
					// Opción 9 - Mostrar los identificadores de todos los ocupantes del tren
					case 9:
						System.out.println(tren.mostrarOcupantesTren());
						break;
						
					// Opción 10 - Eliminar reservas de una persona
					case 10:
						System.out.print("Introduzca el nombre de quien desea eliminar todas sus reservas: ");
						System.out.println(tren.liberarAsiento(TECLADO.next()));
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
					default: System.err.println("\nOpción seleccionada inválida\n");
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
	
	/***********************************
	 * Permite crear un tren, tanto si es la 1ª vez que se invoca el menú como si se desea generar otro con diferentes parámetros
	 * @return Devuelve el tren creado
	 * @throws IllegalArgumentException -> Si al crear el tren, surgió un problema: númMáxVagones <= 0 || numFilasVagón <= 0
	 * @throws InputMismatchException -> Lanzada cuando se indica en un formato incorrecto el número máximo de vagones o el número de filas por vagón
	 ***********************************/
	public static Tren crearTren() {
		// Número máximo de vagones del nuevo tren
		System.out.print("Introduzca el número máximo de vagones para el tren: ");
		final int numMáxVagones = TECLADO.nextInt();
		// Número de filas por vagón 
		System.out.print("Introduzca el número de filas por vagón: ");
		final int numFilasVagón = TECLADO.nextInt();
		// Devuelve la instancia del tren generado
		return new Tren(numMáxVagones, numFilasVagón);
	}
	
	/***********************************
	 * Permite realizar una compra de billetes de forma secuencial, indicando identificador de la persona que los compra
	 * y el número de asientos a reservar
	 * @param tren -> Tren
	 * @throws IllegalArgumentException -> Lanzada en caso de que tren.comprobarIdentificador y/o tren.reservarAsiento fallen
	 * @throws InputMismatchException -> Lanzada cuando se indica en un formato incorrecto el número de asientos a reservar
	 ***********************************/
	public static void comprarBilletes(Tren tren) {
		// Identificador de la persona que compra los billetes
		System.out.print("Introduzca el nombre de quien realiza las reservas: ");
		String identificador = TECLADO.next();
		
		// Número de reservas a realizar
		System.out.print("Introduzca la cantidad de asientos que desea reservar: ");
		int numReservas = TECLADO.nextInt();
		
		// Reserva de los billetes
		tren.reservarAsiento(identificador, numReservas); 
		
		// Mensaje de confirmación
		System.out.println("<Se han reservado correctamente " + numReservas + " asientos>");
	}
	
	/***********************************
	 * Imprime una lista de los parámetros generales del tren. Útil para comprobar si los métodos funcionan correctamente
	 * @param tren -> Tren
	 ***********************************/
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
	
	/***********************************
	 * Permite que el usuario consulte el identificador de un asiento en concreto
	 * @param tren -> Tren
	 * @throws IllegalArgumentException -> Si tren.getIdentificadorAsientoVagón falla
	 * @throws InputMismatchException -> Lanzada cuando se indica en un formato incorrecto el número de vagón o la fila
	 ***********************************/
	public static void consultarOcupanteAsiento(Tren tren) {
		// Declaración de variables
		String identificador, posicion;
		final int numVagón, fila;
		
		// Número de vagón y comprobación de parámetro correcto
		System.out.print("Introduzca el vagón donde realizará la consulta [1," + tren.getNumVagones() + "] -> ");
		numVagón = TECLADO.nextInt(); 
			
		// Fila
		System.out.print("Introduzca la fila del asiento [1," + tren.getFilasVagones()+ "] -> ");
		fila = TECLADO.nextInt(); 
		
		// Columna y comprobación de columna correcta
		System.out.print("Introduzca la posición del asiento (VentanaIzq, PasilloIzq, PasilloDer y VentanaDer) -> ");
		posicion = TECLADO.next();
		
		// Consulta del asiento
		if((identificador = tren.getIdentificadorAsientoVagón(numVagón, fila, posicion)) != null)
			System.out.println("\n<Vagón: " + (numVagón+1) + " | Fila: " + (fila+1) + " | Columna: " + posicion + " | ID: " + identificador + ">");
		else
			System.out.println("\n<Vagón: " + (numVagón+1) + " | Fila: " + (fila+1) + " | Columna: " + posicion + " | Asiento libre>");
		
	}
	
	/***********************************
	 * Permite que el usuario consulte el identificador de un asiento en concreto
	 * @param tren -> Tren
	 * @throws IllegalArgumentException -> Si tren.comprobarIdentificador o tren.setIdentificadorAsientoVagón fallan
	 ***********************************/
	public static void modificarIdentificadoresDe(Tren tren) {
		System.out.print("Introduzca el identificador del antiguo ocupante: ");
		String antiguoOcupante = TECLADO.next(); // Identificador a modificar
		System.out.print("Introduzca el identificador del nuevo ocupante: ");
		String nuevoOcupante = TECLADO.next(); // Nuevo identificador
		tren.setIdentificadorAsientoVagón(antiguoOcupante, nuevoOcupante); // Modificación
		System.out.println("<Los identificadores correspondientes a \"" +antiguoOcupante + "\" se han modificado a \""+ nuevoOcupante + "\">"); // Mensaje
	}
	
	/***********************************
	 * Permite saber si el identificador indicado se encuentra en el tren
	 * @param tren -> Tren
	 * @throws IllegalArgumentException -> Si pedirIdentificador falla
	 ***********************************/
	public static void existenciaIdentificador(Tren tren) {
		// Identificador a consultar
		System.out.print("Introduzca el identificador a consultar: ");
		String identificador = TECLADO.next();
		// Existencia del identificador en el tren
		if(tren.identificadorEnUso(identificador))
			System.out.println("<El identificador \"" + identificador + "\" está en uso>");
		else
			System.out.println("<El identificador \"" + identificador + "\" aún no se ha usado por nadie>");
	}
}
