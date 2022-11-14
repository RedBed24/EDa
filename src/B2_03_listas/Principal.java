
/*********************************************************************
* @name Principal
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la funci�n main, que contiene la lectura del fichero, la adici�n de
* 			   los establecimientos en una lista y el manejo del programa principal a trav�s de un men�.
***********************************************************************/
package B2_03_listas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import B2_03_listas.Local_Restauracion.Provincia;
import B2_03_listas.Local_Restauracion.TipoEstablecimiento;

public class Principal {
	
	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\nIntroduce una opci�n:\n 1: Filtrar por provincia y tipo\n 2: "
			+ "Listar Top CR Awards\n 3: Mostrar Best Caf� Awards\n 4: Listar todos los establecimientos\n 5: Salir del programa";
	
	/***********************************
	 * @name main
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee el fichero, a�ade los establecimientos le�dos a una lista y los env�a al men� principal del programa.
	 * 
	 * @files "2022_23_LabTask_Lists_Entrada.csv"
	 * 
	 * @throws FileNotFoundException --> Lanzada cuando no se ha podido leer el fichero correctamente.
	 ***********************************/
	
	public static void main(String[] args) {
		
		// Bienvenida
		System.out.println("\n*******************************\n* ESTRUCTURA DE DATOS - LISTAS *\n*******************************");
		
		try {
				
		// Lectura del fichero 2022_23_LabTask_Lists_Entrada.csv
			File f = new File("2022_23_LabTask_Lists_Entrada.csv"); // Se han corregido las tildes para que salgan correctamente.
			Scanner fichero = new Scanner(f);
		
		// Adici�n de los establecimientos a una lista
			final List<Local_Restauracion> establecimientos = añadirEstablecimientos(fichero);
			
		// Cierre del fichero
			fichero.close();
			
		// Ejecuci�n del programa principal a trav�s de un men�
			menú(establecimientos);

		} catch (FileNotFoundException e) {
			System.out.println("\nEl fichero no se ha encontrado.\n" + e);
			
		} catch (Exception e) {
			System.out.println("Error inesperado: "+ e);
			
		}
	}

	/***********************************
	 * @name a�adirEstablecimientos
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Crea y a�ade establecimientos a la lista seg�n los atributos asignados.
	 * 
	 * @param fichero --> Scanner abierto con los datos de los establecimientos que deber�an llegar.
	 * 
	 * @throws InputMismatchException --> Lanzada cuando el dato del tipo de establecimiento o de la provincia no coincide
	 * 									  con alguno de los enums declarados en la clase Local_Restauracion.
	 * @throws NumberFormatException --> Lanzada cuando se presenta un error a la hora de realizar una conversi�n de tipo de datos.
	 * @throws IndexOutOfBoundsException --> Lanzada cuando la l�nea le�da no contiene la informaci�n necesaria.
	 * 
	 * @return Lista en la que se han a�adido todos los locales le�dos del fichero.
	 ***********************************/
	
	public static List<Local_Restauracion> añadirEstablecimientos(final Scanner fichero) {
		
		final List<Local_Restauracion> locales = new LinkedList<Local_Restauracion>();
		
		// Guarda las subcadenas con cada dato del establecimiento de la l�nea le�da del fichero.
		String datos[];
		
		fichero.nextLine(); /* La primera l�nea contiene los tipos de datos (tipo;nombre;c�digopostal;...)
		Puede dar fallo si el archivo tiene una l�nea inicial con datos. En ese caso, no se leer�a. */
		
		while (fichero.hasNextLine()) {
			try {
				datos = fichero.nextLine().split(";"); // Las subcadenas se consiguen dividi�ndolas en torno al ;
				/* 
				 * datos[0] = tipo de establecimiento 
				 * datos[1] = nombre del establecimiento
				 * datos[2] = c�digo postal
				 * datos[3] = poblaci�n espec�fica donde se encuentra
				 * datos[4] = provincia donde se encuentra
				 * datos[5] = n�mero de visitas 
				 */
				
				final TipoEstablecimiento tipo;
				switch (datos[0]) {
					case "Restaurantes": tipo = TipoEstablecimiento.RESTAURANTE; break;
					case "Bares":        tipo = TipoEstablecimiento.BAR;         break;
					case "Cafeterías":   tipo = TipoEstablecimiento.CAFETERÍA;   break;
					default: throw new InputMismatchException("No se reconoce el tipo " + datos[0] + ". Compruebe el fichero.");
				}
				
				final String nombre = datos[1];
				final int códigoPostal = Integer.parseInt(datos[2]);
				final String población = datos[3];
				
				final Provincia provincia;
				switch (datos[4]) {
					case "ALBACETE":    provincia = Provincia.ALBACETE;    break;
					case "CIUDAD REAL": provincia = Provincia.CIUDAD_REAL; break;
					case "CUENCA":      provincia = Provincia.CUENCA;      break;
					case "GUADALAJARA": provincia = Provincia.GUADALAJARA; break;
					case "TOLEDO":      provincia = Provincia.TOLEDO;      break;
					default: throw new InputMismatchException("No se reconoce la provincia " + datos[4] +". Compruebe el fichero.");
				}
				
				final int visitas = Integer.parseInt(datos[5]);
				
				// Adici�n del local a la lista que se devolver� usando los datos concretos.
				locales.add(new Local_Restauracion(tipo, nombre, códigoPostal, población, provincia, visitas));
				
			} catch (InputMismatchException e) {
				System.out.println("No se pudo leer uno de los establecimientos. " + e.getMessage());
				
			} catch (NumberFormatException e) {
				System.out.println("No se pudo leer uno de los n�meros ya que no se puede convertir al formato de entero. Compruebe el fichero.");
				
			} catch (IndexOutOfBoundsException e) {
				System.out.println("No se pudo leer uno de los establecimientos debido a que no conten�a la informaci�n suficiente. Compruebe el fichero");
			
			}
		}
		return locales;
	}

	/***********************************
	 * @name men�
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Imprime el men� y procesa las consultas seg�n la opci�n seleccionada por el usuario.
	 * 
	 * @param establecimientos --> Lista con los establecimientos a�adidos
	 * 
	 * @throws InputMismatchException --> Lanzada cuando el dato del tipo de establecimiento o de la provincia no coincide
	 * 									  con alguno de los enums declarados en la clase Local_Restauracion.
	 * @throws InvalidEnumException --> Lanzada cuando el usuario introduce una provincia o tipo de establecimiento inv�lidos.
	 ***********************************/
	
	private static void menú(final List<Local_Restauracion> establecimientos) {
		
		// Listas correspondientes a cada uno de los rankings a mostrar
		List<Local_Restauracion> topGustosPersonales = new LinkedList<Local_Restauracion>();
		final List<Local_Restauracion> topCRAwards = crearTop(establecimientos, true);
		final List<Local_Restauracion> topBestCafeAwards = crearTop(establecimientos, false);
		
		while (true) { // Mientras el usuario no especifique la opci�n 5, la cual tiene un return */
			try {
				// Muestra del men�
				System.out.println(MENÚ);
				
				// Comprobaci�n de la opci�n elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Top Gustos Personales
					case 1: seleccionarGustosPersonales(establecimientos, topGustosPersonales); 
							System.out.println(mostrarBonito(topGustosPersonales)); break;
							
					// Top 10 CR Awards
					case 2: System.out.println(mostrarBonito(topCRAwards)); break;
					
					// Best Cafe Awards
					case 3: System.out.println(mostrarBonito(topBestCafeAwards)); break;
					
					// Todos los establecimientos
					case 4: System.out.println(mostrarBonito(establecimientos)); break;
					
					// Opci�n de salida
					case 5: TECLADO.close(); return;
					
					// Se ha introducido una opci�n fuera de las contempladas
					default: System.out.println("Opci�n seleccionada inv�lida");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("Error al introducir la selecci�n del men�.");
				TECLADO.next(); // Se quita el valor err�neo del b�ffer
				
			} catch (InvalidEnumException e) {
				System.out.println(e.getMessage());
				
			}
		}
	}

	/***********************************
	 * @name crearTop
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Genera la lista correspondiente al Top CR Awards o Best Cafe Awards.
	 * 
	 * @param establecimientos --> Lista con los establecimientos a�adidos
	 * @param topAcrear --> Si recibe un true, se crear� el Top CR Awards. Si recibe false, el Best Cafe Awards.
	 * 
	 * @return Lista que contiene el "Top 10 CR Awards" o el "Best Cafe Awards"
	 ***********************************/
	
	public static List<Local_Restauracion> crearTop(final List<Local_Restauracion> establecimientos, final boolean topAcrear) {
		// Creaci�n del top
		final List<Local_Restauracion> top = new ArrayList<Local_Restauracion>(10);
		
		// Ordenamos la lista de establecimientos para poder coger los 10 primeros
		Collections.sort(establecimientos);
		
		/* 
		 * Si es el top CR Awards, se comprueba que el establecimiento sea de la ciudad de Ciudad Real
		 * Si es el top Best Caf� Awards, se comprueba que el establecimiento sea una cafeter�a 
		 */
		int insertados = 0;
		for (Local_Restauracion local : establecimientos) {
			
			if ((topAcrear && local.getPoblación().equalsIgnoreCase("CIUDAD REAL")) // top CR Awards
					|| (!topAcrear && local.getTipo() == TipoEstablecimiento.CAFETERÍA)) { // Best Cafe Awards
				top.add(local);
				// Si ya se han introducido 10 establecimientos, sale del bucle.
				if (++insertados >= 10) 
					break;
			}	
		}
		return top;
	}

	/***********************************
	 * @name mostrarBonito
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Devuelve una cadena con la informaci�n de la lista que reciba como par�metro para que pueda imprimirse en la salida.
	 * 
	 * @param lista --> Lista a imprimir
	 * 
	 * @return String con toda la informaci�n de la lista que reciba
	 ***********************************/
	
	public static String mostrarBonito(final List<Local_Restauracion> lista) {
		String devolver = "\t\t\t\tESTABLECIMIENTOS DISPONIBLES\n ID : <Tipo>\t  | <Nombre>\t\t\t\t     | <Ciudad>\t\t\t | <Visitas>\n\n";
		
		Iterator<Local_Restauracion> iteraEstablecimientos = lista.iterator();
		
		for (int i= 1; iteraEstablecimientos.hasNext(); i++)
			devolver += String.format("%3d : %s\n", i, iteraEstablecimientos.next());
		
		return devolver;
	}
	
	/***********************************
	 * @name seleccionarGustosPersonales
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Permite que el usuario filtre por provincia y tipo de establecimiento. El resultado ser�
	 * 				un ranking desde el establecimiento m�s visitado al menos visitado, de dichos tipo y provincia.
	 * 
	 * @param establecimientos --> Lista con los establecimientos a�adidos
	 * @param topGustosPersonales --> Lista que representa el ranking personalizado "TopGustosPersonales" 
	 * 
	 * @throws InvalidEnumException --> Lanzada cuando el usuario introduce una provincia o tipo de establecimiento inv�lidos.
	 ***********************************/
	
	public static void seleccionarGustosPersonales(final List<Local_Restauracion> establecimientos, List<Local_Restauracion> topGustosPersonales) throws InvalidEnumException {
		
		// Limpia la lista para que no salga el filtrado anterior
		topGustosPersonales.clear();
		
		String usr; // Guardar� las elecciones del usuario: provincia y tipo de establecimiento
		TECLADO.nextLine(); // L�nea necesaria para que funcione. En la selecci�n del men�, se lee un n�mero, pero no toda la l�nea.
		
		// Pedir provincia
		System.out.println("Introduce una provincia: Ciudad Real, Toledo, Cuenca, Albacete o Guadalajara");
		usr = TECLADO.nextLine();
		Provincia prov;
		switch (usr.toUpperCase()) { // Comprobaci�n de la provincia indicada
			case "ALBACETE":    prov = Provincia.ALBACETE;    break;
			case "CIUDAD REAL": prov = Provincia.CIUDAD_REAL; break;
			case "CUENCA":      prov = Provincia.CUENCA;      break;
			case "GUADALAJARA": prov = Provincia.GUADALAJARA; break;
			case "TOLEDO":      prov = Provincia.TOLEDO;      break;
			default: throw new InvalidEnumException("No se reconoce la provincia: " + usr);
		}
		
		// Pedir tipo de establecimiento
		System.out.println("Introduce un tipo: Bar, Restaurante o Cafeter�a ");
		usr = TECLADO.nextLine();
		final TipoEstablecimiento tipo;
		switch (usr.toLowerCase()) { // Comprobaci�n del tipo de establecimiento especificado
			case "restaurante": tipo = TipoEstablecimiento.RESTAURANTE; break;
			case "bar":         tipo = TipoEstablecimiento.BAR;         break;
			case "cafeter�a":   tipo = TipoEstablecimiento.CAFETERÍA;   break;
			default: throw new InvalidEnumException("No se reconoce el tipo: " + usr);
		}
		
		// Adici�n de los locales, de la misma provincia y tipo que los indicados, a la lista de gustos.
		for (Local_Restauracion local : establecimientos)
			if (local.getProvincia() == prov && local.getTipo() == tipo)
				topGustosPersonales.add(local);

	}

}
