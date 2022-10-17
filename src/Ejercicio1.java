
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.io.*;

public class Ejercicio1 {

	public static void main(String args[]) {

		// Bienvenida
		System.out.println(
				"\n*******************************\n* ESTRUCTURA DE DATOS - PILAS *\n*******************************");

		// Lectura del fichero navegación.txt
		Scanner teclado = lecturaFichero();

		// Flujo de navegación y muestra de las estadísticas
		if (teclado != null)
			try {
			flujoDeNavegación(teclado);
			} catch (Exception e) {
				// Este error se puede dar cuando en el fichero no hay líneas válidas
				// Se entiende como línea válida: "<=" o "url,unEntero"
				System.out.print("Error inesperado "+ e.getMessage());
			}
	}

	/***********************************
	 * @name lecturaFichero
	 * 
	 * @authors DJS - 03
	 * 
	 * @description Lee el fichero y lo devuelve.
	 * 
	 * @return teclado --> Devuelve el fichero si se leyó correctamente. Si no,
	 *         devuelve null.
	 * 
	 * @exception FileNotFoundException se lanza cuando no se ha encontrado el
	 *                                  fichero.
	 ***********************************/

	public static Scanner lecturaFichero() {
		Scanner teclado = null;
		try {
			File fichero = new File("navegacion.txt");
			teclado = new Scanner(fichero);
		} catch (FileNotFoundException e) {
			System.out.println("\nEl fichero no se ha encontrado.\n" + e.toString());
		}
		return teclado;
	}

	/***********************************
	 * @name flujoDeNavegación
	 * 
	 * @authors DJS - 03
	 * 
	 * @description Usa una pila para albergar el flujo de las páginas y, en cada
	 *              paso, visualiza por el navegador la página actual.
	 * 
	 * @param teclado --> Contiene el fichero leído correctamente.
	 * 
	 ***********************************/

	public static void flujoDeNavegación(Scanner teclado) {

		Stack<String> navegador = new Stack(); // Pila donde guardaremos el flujo de las páginas web
		int tiempoTotal = 0, // Es el tiempo total navegado en segundos.
		páginasVisitadas = 0, // Número de páginas visitadas a lo largo de la navegación realizada.
		páginasDistintas = 0, // Número de páginas nuevas que se han visitado.
		vueltasAtrás = 0; // Número de páginas que se han vuelto a visitar navegando hacia atrás y, por tanto, no son páginas nuevas.

		String línea= null;
		boolean seguir;
		String[] partes= null;
		 /*
		 * Almacena la URL de la página y el tiempo que se permanece en ella. Como ambas
		 * partes descritas se encuentran en la misma línea separadas por una coma,
		 * usamos split para dividirlas y almacenarlas en el vector.
		 */
		
		if (teclado.hasNextLine()) {
			
			// Se siguen leyendo líneas hasta que no sea una de vuelta atrás
			while ((seguir= teclado.hasNextLine()) && (línea= teclado.nextLine()).equals("<="))
				System.out.println("No se ha realizado niguna navegación todavía.");
				
			while (seguir) { // siguen habiendo líneas en el fichero
			
				if (línea.equals("<=")) {
					try {
						navegador.pop(); // Desapilamos la última página, regresando a la anterior.
						partes = navegador.peek().split(",");
						vueltasAtrás += 1;
					} catch (EmptyStackException e) {
						// Te mantiene en la primera URL visitada
						System.out.println("No se puede volver más atrás.");
					}
				} else {
					navegador.push(línea); // Apilamos una nueva página.
					partes = línea.split(",");
					páginasDistintas += 1;
				}
				páginasVisitadas += 1;
				tiempoTotal += Integer.parseInt(partes[1]); // Convertimos en entero el tiempo en una página para poder sumarlo.
				System.out.println("\nPaso " + páginasVisitadas + " --> " + partes[0] + "\n");
				
				// Se realiza aquí la lectura ya que partimos de un valor en línea previamente asignado en el while anterior
				// Si al principio de este bucle se hubiera hecho otra asignación, nos habríamos saltado una línea
				if (teclado.hasNextLine())
					línea= teclado.nextLine();
				else
					seguir= false;
			} 
			mostrarEstadísticas(tiempoTotal, páginasVisitadas, páginasDistintas, vueltasAtrás);
		}
	}

	/*********************************
	 * @method mostrarEstadísticas
	 * 
	 * @authors DJS - 03
	 * 
	 * @description Muestra la información relativa a los parámetros que se le
	 *              envía.
	 * 
	 * @param tiempoTotal      --> Es el tiempo total navegado en segundos.
	 * @param páginasVisitadas --> Número de páginas visitadas a lo largo de la
	 *                         navegación realizada.
	 * @param páginasDistintas --> Número de páginas nuevas que se han visitado.
	 * @param vueltasAtrás     --> Número de páginas que se han vuelto a visitar
	 *                         navegando hacia atrás y, por tanto, no son páginas
	 *                         nuevas.
	 *********************************/

	public static void mostrarEstadísticas(int tiempoTotal, int páginasVisitadas, int páginasDistintas,
			int vueltasAtrás) {
		System.out.println(
				"\nEl tiempo total navegando en la página web de la UCLM ha sido de " + tiempoTotal + " segundos.");
		System.out.println("Se visitaron en total " + páginasVisitadas + " páginas: " + páginasDistintas
				+ " distintas y " + vueltasAtrás + " veces se regresó a la página anterior.");
		System.out.printf("El tiempo medio de permanencia en una página fue aproximadamente %.3f de segundos.\n",
				((double) tiempoTotal / páginasVisitadas));
	}

}
