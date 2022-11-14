package B2_03_pilas;

import java.util.Stack;
import java.util.Scanner;
import java.io.*;

public class Navegador {

	/***********************************
	 * @name main
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee el fichero e intenta ejecutar la simulaci�n del flujo de navegaci�n.
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
				
		// Flujo de navegaci�n y muestra de las estad�sticas
			flujoDeNavegación(fichero);
			fichero.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("\nEl fichero no se ha encontrado.\n" + e.toString());
			
		} catch (Exception e) {
			/* Se lanza la excepci�n cuando en el fichero no hay l�neas v�lidas.
			 * Se entiende por l�nea v�lida: "<=" o "url,unEntero" */
			System.out.print("Error inesperado: "+ e.getMessage());
		}
	}

	/***********************************
	 * @name flujoDeNavegaci�n
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Usa una pila para albergar el flujo de las p�ginas y, en cada paso, visualiza por el navegador la p�gina actual.
	 * 
	 * @param teclado --> Contiene el fichero le�do correctamente.
	 * 
	 ***********************************/

	public static void flujoDeNavegación(Scanner teclado) {

		// Declaraci�n de variables
		
		Stack<String> navegador = new Stack(); // Pila donde guardaremos el flujo de las p�ginas web.
		
		int tiempoTotal = 0, // Tiempo total navegado en segundos.
		páginasVisitadas = 0, // N�mero de p�ginas visitadas a lo largo de la navegaci�n realizada.
		páginasDistintas = 0, // N�mero de p�ginas nuevas que se han visitado.
		vueltasAtrás = 0; // N�mero de p�ginas que se han vuelto a visitar navegando hacia atr�s y, por tanto, no son p�ginas nuevas.

		String línea = null; // L�nea le�da del fichero
		boolean seguir = true; // Variable auxiliar que nos indicar� si hay que seguir ejecutando el bucle while principal o no.
		String[] partes = null; /* Almacena la URL de la p�gina y el tiempo que se permanece en ella. Como ambas
		 						 * partes descritas se encuentran en la misma l�nea separadas por una coma,
		 						 * usamos split para dividirlas y almacenarlas en el vector. */
		
		
			// Se ignora todo vuelta atr�s hasta que se introduzca una l�nea v�lida
			while ((línea = teclado.nextLine()).equals("<="));
				
			// Siguen habiendo l�neas en el fichero
			while (seguir) { 
			
				if (línea.equals("<=")) {
					if(navegador.size() != 1) {
						navegador.pop(); // Desapilamos la �ltima p�gina, regresando a la anterior.
						partes = navegador.peek().split(",");
						vueltasAtrás += 1;
					} else {
						System.out.println("\tNo se puede volver m�s atr�s."); // Te mantiene en la primera URL visitada
						páginasVisitadas-=1; // Resta una p�gina ya que al final sumar� siempre 1.
						tiempoTotal -= Integer.parseInt(partes[1]); // Misma l�gica que en la l�nea anterior pero con el tiempo.
					}
				} else {
					navegador.push(línea); // Apilamos una nueva p�gina.
					partes = línea.split(",");
					páginasDistintas += 1;
				}
				páginasVisitadas += 1;
				tiempoTotal += Integer.parseInt(partes[1]); // Convertimos en entero el tiempo en una p�gina para poder sumarlo.
				System.out.println("\nPaso " + páginasVisitadas + " --> " + partes[0] + "\n");
				
				// Lectura de la siguiente l�nea
				
				if (teclado.hasNextLine())
					línea= teclado.nextLine();
				else
					seguir= false;
				
				/* Realizamos aqu� la lectura de la siguiente l�nea ya que partimos de un valor previamente asignado
				 * en "l�nea" (bucle while anterior). Si al principio de este bucle se hubiera hecho otra asignaci�n,
				 * nos habr�amos saltado una l�nea. */
			} 
			mostrarEstadísticas(tiempoTotal, páginasVisitadas, páginasDistintas, vueltasAtrás);
		}
	

	/*********************************
	 * @name mostrarEstad�sticas
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Muestra la informaci�n relativa a los par�metros que se le env�a.
	 * 
	 * @param tiempoTotal      --> Tiempo total navegado en segundos.
	 * @param p�ginasVisitadas --> N�mero de p�ginas visitadas a lo largo de la navegaci�n realizada.
	 * @param p�ginasDistintas --> N�mero de p�ginas nuevas que se han visitado.
	 * @param vueltasAtr�s     --> N�mero de p�ginas que se han vuelto a visitar navegando hacia atr�s y, por tanto, no son p�ginas nuevas.
	 *********************************/

	public static void mostrarEstadísticas(int tiempoTotal, int páginasVisitadas, int páginasDistintas, int vueltasAtrás) {
		System.out.println(
				"\nEl tiempo total navegando en la p�gina web de la UCLM ha sido de " + tiempoTotal + " segundos.");
		System.out.println("Se visitaron en total " + páginasVisitadas + " p�ginas: " + páginasDistintas
				+ " distintas y " + vueltasAtrás + " veces se regres� a la p�gina anterior.");
		System.out.printf("El tiempo medio de permanencia en una p�gina fue aproximadamente %.3f de segundos.\n",
				((double) tiempoTotal / páginasVisitadas));
	}

}
