package Pilas;

import java.util.Stack;
import java.util.Scanner;
import java.io.*;

public class Navegador {

	/***********************************
	 * @name main
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee el fichero e intenta ejecutar la simulación del flujo de navegación.
	 * 
	 * @files "navegacion.txt"
	 * 
	 * @throws FileNotFoundException --> Lanzada cuando no se ha podido leer el fichero correctamente.
	 ***********************************/
	
	public static void main(String args[]) {

		// Bienvenida
		System.out.println("\n*******************************\n* ESTRUCTURA DE DATOS - PILAS *\n*******************************");
		
		try {
				
		// Lectura del fichero navegacion.txt
			File f = new File("navegacion.txt");
			Scanner fichero = new Scanner(f);
				
		// Flujo de navegación y muestra de las estadísticas
			flujoDeNavegación(fichero);
			fichero.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("\nEl fichero no se ha encontrado.\n" + e.toString());
			
		} catch (Exception e) {
			/* Se lanza la excepción cuando en el fichero no hay líneas válidas.
			 * Se entiende por línea válida: "<=" o "url,unEntero" */
			System.out.print("Error inesperado: "+ e.getMessage());
		}
	}

	/***********************************
	 * @name flujoDeNavegación
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Usa una pila para albergar el flujo de las páginas y, en cada paso, visualiza por el navegador la página actual.
	 * 
	 * @param teclado --> Contiene el fichero leído correctamente.
	 * 
	 ***********************************/

	public static void flujoDeNavegación(Scanner teclado) {

		// Declaración de variables
		
		Stack<String> navegador = new Stack(); // Pila donde guardaremos el flujo de las páginas web.
		
		int tiempoTotal = 0, // Tiempo total navegado en segundos.
		páginasVisitadas = 0, // Número de páginas visitadas a lo largo de la navegación realizada.
		páginasDistintas = 0, // Número de páginas nuevas que se han visitado.
		vueltasAtrás = 0; // Número de páginas que se han vuelto a visitar navegando hacia atrás y, por tanto, no son páginas nuevas.

		String línea = null; // Línea leída del fichero
		boolean seguir = true; // Variable auxiliar que nos indicará si hay que seguir ejecutando el bucle while principal o no.
		String[] partes = null; /* Almacena la URL de la página y el tiempo que se permanece en ella. Como ambas
		 						 * partes descritas se encuentran en la misma línea separadas por una coma,
		 						 * usamos split para dividirlas y almacenarlas en el vector. */
		
		
			// Se ignora todo vuelta atrás hasta que se introduzca una línea válida
			while ((línea = teclado.nextLine()).equals("<="));
				
			// Siguen habiendo líneas en el fichero
			while (seguir) { 
			
				if (línea.equals("<=")) {
					if(navegador.size() != 1) {
						navegador.pop(); // Desapilamos la última página, regresando a la anterior.
						partes = navegador.peek().split(",");
						vueltasAtrás += 1;
					} else {
						System.out.println("\tNo se puede volver más atrás."); // Te mantiene en la primera URL visitada
						páginasVisitadas-=1; // Resta una página ya que al final sumará siempre 1.
						tiempoTotal -= Integer.parseInt(partes[1]); // Misma lógica que en la línea anterior pero con el tiempo.
					}
				} else {
					navegador.push(línea); // Apilamos una nueva página.
					partes = línea.split(",");
					páginasDistintas += 1;
				}
				páginasVisitadas += 1;
				tiempoTotal += Integer.parseInt(partes[1]); // Convertimos en entero el tiempo en una página para poder sumarlo.
				System.out.println("\nPaso " + páginasVisitadas + " --> " + partes[0] + "\n");
				
				// Lectura de la siguiente línea
				
				if (teclado.hasNextLine())
					línea= teclado.nextLine();
				else
					seguir= false;
				
				/* Realizamos aquí la lectura de la siguiente línea ya que partimos de un valor previamente asignado
				 * en "línea" (bucle while anterior). Si al principio de este bucle se hubiera hecho otra asignación,
				 * nos habríamos saltado una línea. */
			} 
			mostrarEstadísticas(tiempoTotal, páginasVisitadas, páginasDistintas, vueltasAtrás);
		}
	

	/*********************************
	 * @method mostrarEstadísticas
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Muestra la información relativa a los parámetros que se le envía.
	 * 
	 * @param tiempoTotal      --> Tiempo total navegado en segundos.
	 * @param páginasVisitadas --> Número de páginas visitadas a lo largo de la navegación realizada.
	 * @param páginasDistintas --> Número de páginas nuevas que se han visitado.
	 * @param vueltasAtrás     --> Número de páginas que se han vuelto a visitar navegando hacia atrás y, por tanto, no son páginas nuevas.
	 *********************************/

	public static void mostrarEstadísticas(int tiempoTotal, int páginasVisitadas, int páginasDistintas, int vueltasAtrás) {
		System.out.println(
				"\nEl tiempo total navegando en la página web de la UCLM ha sido de " + tiempoTotal + " segundos.");
		System.out.println("Se visitaron en total " + páginasVisitadas + " páginas: " + páginasDistintas
				+ " distintas y " + vueltasAtrás + " veces se regresó a la página anterior.");
		System.out.printf("El tiempo medio de permanencia en una página fue aproximadamente %.3f de segundos.\n",
				((double) tiempoTotal / páginasVisitadas));
	}

}
