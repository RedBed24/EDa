package B2_03_grafos;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

import B2_03_grafos.Personaje.*;
import graphsDSESIUCLM.*;

/*********************************************************************
* @name Principal
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la función main, que contiene la lectura de los ficheros de personajes y de relaciones,
* 			   la creación del grafo y el manejo del programa principal a través de un menú.
***********************************************************************/
public class Principal {

	final static Scanner TECLADO = new Scanner(System.in);
	final static String MENÚ = "\nIntroduce una opción:\n 1: Apartado a - Información relativa al grafo del Señor de los Anillos"
			+ "\n 2: Apartado b - Formación del comando especial para acabar con el Rey Brujo\n 3: Apartado c - Red de comunicación segura entre dos personajes\n 4: Salir del programa";
	
	/***********************************
	 * @name main
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee los ficheros de los personajes y de las relaciones para crear el grafo y lo envía 
	 * 				al menú principal del programa.
	 * 
	 * @files "lotr-pers.csv" --> Fichero de los personajes
	 * 		  "networks-id-3books.csv" --> Fichero de las relaciones
	 * 
	 * @throws FileNotFoundException --> Lanzada cuando no se ha podido leer el fichero correctamente.
	 ***********************************/
	
	public static void main(String[] args) {
		try { 
			// Bienvenida
			System.out.println("\n*******************************\n* ESTRUCTURA DE DATOS - GRAFOS *\n*******************************");
			
			// Lectura del fichero de los personajes
			File f = new File("lotr-pers.csv");
			Scanner fichero = new Scanner(f);
			
			// Obtención de los vértices del grafo - VÉRTICES
			Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo = leerPersonajes(fichero);
			
			// Lectura del fichero de las relaciones
			f = new File("networks-id-3books.csv");
			fichero = new Scanner(f);
			
			// Obtención de las aristas del grafo - ARISTAS
			grafo = leerRelaciones(fichero, grafo);
			
			// Cierre de los ficheros
			fichero.close();
			
			// Ejecución del programa principal a través de un menú
			menú(grafo);
			
		} catch (FileNotFoundException e) {
			System.err.println("\nUno de los ficheros no se ha encontrado.\n");
			
		} catch (Exception e){
			System.err.println("Error inesperado."+e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	/***********************************
	 * @name menú
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Imprime el menú y procesa las consultas según la opción seleccionada por el usuario.
	 * 
	 * @param grafo --> Grafo con los vértices (personajes) y aristas (relaciones) añadidos
	 * 
	 * @throws InputMismatchException --> Lanzada cuando se indica una opción del menú en un formato incorrecto.
	 ***********************************/
	
	public static void menú(Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo) {
		
		// Cola donde se guardan los personajes que el usuario introduce por teclado
		Queue<Vertex<DecoratedElement<Personaje>>> colaPersonajes = new ArrayBlockingQueue<Vertex<DecoratedElement<Personaje>>>(2);
		
		while (true) { 
			try {
				// Muestra del menú
				System.out.println(MENÚ);
				// Comprobación de la opción elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Apartado a
					case 1: 
						System.out.println("A continuación, se muestra información diversa del grafo del Señor de los Anillos.");
						System.out.println("- Cantidad de personajes en grafo: "+ grafo.getN());
						System.out.println("- Cantidad de relaciones: "+ grafo.getM());
						System.out.println("- Personaje con más cantidad de relaciones: \n\t"+ buscarPersonajeMayorInteracción(grafo)+"\n");
						System.out.println("- Pareja con el mayor nivel de interacción: "+ relaciónConMásInteractuación(grafo));
						break;
							
					// Apartado b
					case 2:
						System.out.println("<Se aplicará un DFS entre dos personajes cualesquiera para generar la formación del comando especial más fuerte>");
						algoritmoDeBúsqueda(grafo, colaPersonajes, true);
						break;
					
					// Apartado c
					case 3:
						System.out.println("<Se aplicará un BFS entre dos personajes cualesquiera para establecer la red de comunicación más corta y segura posible>");
						algoritmoDeBúsqueda(grafo, colaPersonajes, false);
						break;
					
					// Opción de salida
					case 4: TECLADO.close(); return;
					
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
	
	/***********************************
	 * @name leerPersonajes
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee cada línea del fichero de personajes con el fin de añadir al grafo los vértices (personajes)
	 * 				que se generen a partir de la información de sus atributos.
	 * 
	 * @param fichero --> Scanner abierto con los datos de los personajes que deberían llegar del fichero "lotr-pers.csv".
	 * 
	 * @throws IndexOutOfBoundsException --> Lanzada cuando la línea leída no contiene la información necesaria.
	 * @throws NumberFormatException --> Lanzada cuando se presenta un error a la hora de realizar una conversión de tipo de datos.
	 * @throws IllegalArgumentException --> Lanzada ante un dato erróneo o inapropiado.
	 * 
	 * @return grafo --> Grafo con los vértices añadidos
	 ***********************************/
	
	public static Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> leerPersonajes(final Scanner fichero) {
		
		// Grafo principal
		final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo = new TreeMapGraph<DecoratedElement<Personaje>, DecoratedElement<Integer>>();
		
		fichero.nextLine(); /* La primera línea contiene los tipos de datos (id;type;subtype;name;...)
		Puede dar fallo si el archivo tiene una línea inicial con datos. En ese caso, no se leería. */
		
		while (fichero.hasNext()) {
			try {
				final String[] data= fichero.nextLine().split(";"); // Las subcadenas se consiguen dividiéndolas en torno al ;
			/* 
			 * data[0] id
			 * data[1] type
			 * data[2] subtype
			 * data[3] name
			 * data[4] gender
			 * data[5] FreqSum
			 */
			
				final String id= data[0];
			
				final Type type;
				switch (data[1].toUpperCase()) {
					case "PLA":  type = Type.PLA;  break;
					case "PER":  type = Type.PER;  break;
					case "GRO":  type = Type.GRO;  break;
					case "THIN": type = Type.THIN; break;
					default: throw new IllegalArgumentException("Unexpected Type value: " + data[1]);
				}	
			
				final String subtype= data[2];
			
				final String name= data[3];
			
				final Gender gender;
				switch (data[4].toUpperCase()) {
					case "MALE":   gender = Gender.MALE;   break;
					case "FEMALE": gender = Gender.FEMALE; break;
					default:       gender = Gender.UNKNOWN; break;
				}
			
				final int freqsum= Integer.parseInt(data[5]);
				
				// Creación del personaje sin el id leído
				final Personaje personaje = new Personaje(type, subtype, name, gender, freqsum);
				
				/* Creación del elemento decorado del personaje creado anteriormente usando el id como key
				 * y el personaje como element (parámetros del constructor de la clase DecoratedElement) */
				final DecoratedElement<Personaje> decorated = new DecoratedElement<>(id, personaje);
				
				// Inserción del vértice en el grafo 
				grafo.insertVertex(decorated);
			
			} catch (IndexOutOfBoundsException e) {
				System.err.println("No se ha recibido la cantidad de información necesaria.");
				
			} catch (NumberFormatException e) {
				System.err.println("No se ha podido convertir a entero la entrada esperada.");
				
			} catch (IllegalArgumentException e) {
				System.err.println("Se ha recibido un dato erróneo: "+e);
				
			}
		}
		return grafo;
	}
	
	/***********************************
	 * @name leerRelaciones
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Lee cada línea del fichero de relaciones con el fin de añadir al grafo las aristas (relaciones)
	 * 				que se generen a partir de la información proporcionada: vértice origen, vértice destino y peso.
	 * 
	 * @param fichero --> Scanner abierto con los datos de los relaciones del fichero "networks-id-3books.csv".
	 * 
	 * @throws IndexOutOfBoundsException --> Lanzada cuando la línea leída no contiene la información necesaria.
	 * @throws NumberFormatException --> Lanzada cuando se presenta un error a la hora de realizar una conversión de tipo de datos.
	 * 
	 * @return grafo --> Grafo con las aristas añadidas
	 ***********************************/
	
	public static Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> leerRelaciones(final Scanner fichero, Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		
		fichero.nextLine();  /* La primera línea contiene los tipos de datos (id;type;subtype;name;...)
		Puede dar fallo si el archivo tiene una línea inicial con datos. En ese caso, no se leería. */

		while (fichero.hasNext()) {
			try {
				
				final String[] data= fichero.nextLine().split(";"); // Las subcadenas se consiguen dividiéndolas en torno al ;
				
				/* 
				 * data[0] idSource
				 * data[1] idTarget
				 * data[2] weight
				 */
				
				// Obtención de los vértices origen y destino
				Vertex<DecoratedElement<Personaje>> source = null, target = null;
				Iterator<Vertex<DecoratedElement<Personaje>>> vertices = graph.getVertices(); 
				
				// Recorremos todos los vértices mediante el Iterator correspondiente hasta que coincidan con el leído en el fichero
				while (vertices.hasNext()) { 
					Vertex<DecoratedElement<Personaje>> vertice = vertices.next();
					
					if (data[0].equalsIgnoreCase(vertice.getElement().getID()))
						source = vertice;
					else if (data[1].equalsIgnoreCase(vertice.getElement().getID()))
						target = vertice;
					
					if (source != null && target != null) break; // Si los ha encontrado sale del bucle. Tenemos la arista acotada por sus vértices.
				}
				
				// Obtención del peso de la arista
				DecoratedElement<Integer> peso = new DecoratedElement<Integer>(source.getElement().getID()+target.getElement().getID(), Integer.parseInt(data[2]));
				
				// Inserción de la arista en el grafo
				graph.insertEdge(source, target, peso);

			} catch (IndexOutOfBoundsException e) {
				System.err.println("No se ha recibido la cantidad de información necesaria.");
				
			} catch (NumberFormatException e) {
				System.err.println("No se ha podido convertir a entero la entrada esperada.");
				
			} 
		}
		// No es necesario ya que se pasa por referencia.
		return graph;
	}
	
	/***********************************
	 * @name buscarPersonajeMayorInteracción
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Encuentra el personaje que más interacciones tiene en la saga, es decir, el vértice al que inciden 
	 * 				la mayor cantidad de aristas del grafo.
	 * 
	 * @param graph --> Grafo principal
	 * 
	 * @return Una cadena que indica el personaje y su cantidad de relaciones
	 ***********************************/
	
	public static String buscarPersonajeMayorInteracción(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {

		Personaje personajeMásRelaciones = null; // Personaje
		int mayorRelaciones = 0;				 // Cantidad de relaciones
		
		Iterator<Vertex<DecoratedElement<Personaje>>> nodos= graph.getVertices(); // Iterador con todos los vértices
		
		while (nodos.hasNext()) {
			Vertex<DecoratedElement<Personaje>> nodoActual= nodos.next();
			int cuenta = 0; // Lleva la cuenta de las aristas (relaciones) que tiene un nodo (personaje)
			Iterator<Edge<DecoratedElement<Integer>>> aristasIncidentes = graph.incidentEdges(nodoActual); // Iterador con las aristas incidentes al vértice actual
			while (aristasIncidentes.hasNext()) {
				aristasIncidentes.next();
				cuenta++;
			}
			
			// Obtención de la mayor cantidad de interacciones 
			if (mayorRelaciones < cuenta) {
				mayorRelaciones = cuenta;
				personajeMásRelaciones = nodoActual.getElement().getElement();
			}
		}
		return personajeMásRelaciones+ " tiene "+ mayorRelaciones+ " relaciones.";
	}
	
	/***********************************
	 * @name relaciónConMásInteractuación
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Encuentra la pareja que más interactúa de la saga, es decir, los vértices conectados por el mayor número de aristas del grafo.
	 * 
	 * @param graph --> Grafo principal
	 * 
	 * @return Una cadena que indica la pareja y su cantidad de relaciones.
	 ***********************************/
	
	public static String relaciónConMásInteractuación (final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		
		Personaje personaje1 = null, personaje2 = null; // Pareja
		int mayorRelaciones = 0;						// Cantidad de relaciones de la pareja

		Iterator<Edge<DecoratedElement<Integer>>> aristas = graph.getEdges(); // Iterador con todas las aristas
		while (aristas.hasNext()) {
			Edge<DecoratedElement<Integer>> aristaActual = aristas.next();

			if (mayorRelaciones < aristaActual.getElement().getElement()) { // Si la arista actual tiene un peso mayor...
				// Actualizamos el mayor número de relaciones
				mayorRelaciones = aristaActual.getElement().getElement();
				
				// Obtenemos los vértices de la arista correspondiente
				Vertex<DecoratedElement<Personaje>> personajes[] = graph.endVertices(aristaActual);
				
				// Conseguimos los personajes de los vértices a partir de la arista
				personaje1 = personajes[0].getElement().getElement();
				personaje2 = personajes[1].getElement().getElement();
			}
		}
		return "\n\t" + personaje1+ " y \n\t"+personaje2+ " mantienen "+ mayorRelaciones+ " relaciones.";
	}
	
	/***********************************
	 * @name algoritmoDeBúsqueda
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Se encarga de seleccionar el algoritmo de búsqueda que se debe aplicar y llevar a cabo determinadas acciones
	 * 				para que sea posible. Esto es, limpiar las etiquetas de los vértices y aristas del grafo, pedir los personajes
	 * 				al usuario, elegir en sí el algoritmo de búsqueda según el apartado y deshacer el camino conseguido (si existe).
	 * 
	 * @param grafo --> Grafo principal
	 * @param colaPersonajes --> Cola a la que se añaden los personajes que el usuario introduce
	 * @param quéAlgoritmo --> Booleano que permite saber si hay que ejecutar el apartado B - DFS (true) o el apartado C - BFS (false).
	 * 
	 ***********************************/
	
	public static void algoritmoDeBúsqueda(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo, Queue<Vertex<DecoratedElement<Personaje>>> colaPersonajes, boolean quéAlgoritmo) {
		
		Vertex<DecoratedElement<Personaje>> source = null,  target = null;
		
		// Limpiar las etiquetas de los vértices y aristas del grafo para que cada ejecución sea completamente distinta a la anterior
		limpiarEtiquetas(grafo); 
		
		// Pedir los personajes entre los que se ejecutará el algoritmo DFS o BFS
		pedirPersonajes(grafo, colaPersonajes);
		source = colaPersonajes.remove();
		target = colaPersonajes.remove();
		
		// Ejecutar un algoritmo de búsqueda según el valor de la variable booleana
		if (quéAlgoritmo) DFS(grafo, source, target);
		else BFS(grafo, source, target);

		// Deshacer el camino desde el vértice destino
		if (target.getElement().getParent() != null) System.out.println(deshacerCamino(target.getElement()));
		else System.out.println("No existe un camino entre los personajes dados.");
	}
	
	/***********************************
	 * @name pedirPersonajes
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Guarda en una cola los personajes que introduce el usuario.
	 * 
	 * @param graph --> Grafo principal
	 * @param colaPersonajes --> Cola donde se añaden los personajes que el usuario introduce
	 * 
	 * @throws IllegalArgumentException --> Lanzada cuando se introduce el mismo personaje en origen y destino o un personaje que no existe.
	 ***********************************/
	
	public static void pedirPersonajes(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo, Queue<Vertex<DecoratedElement<Personaje>>> colaPersonajes) {
		Vertex<DecoratedElement<Personaje>> source = null, target = null;
		TECLADO.nextLine();
		// Pedir personajes al usuario
		System.out.print("Introduce el personaje origen: ");
		final String p1 = TECLADO.nextLine();
		System.out.print("Introduce el personaje destino: ");
		final String p2 = TECLADO.nextLine();
		
		// --- Comprobación de error 1: Mismo personaje ---
		if(p1.equalsIgnoreCase(p2)) throw new IllegalArgumentException("Has indicado el mismo personaje. Debes indicar personajes distintos.");
		
		// Recorremos todos los vértices mediante el Iterator correspondiente hasta que coincidan con los leídos por teclado
		Iterator<Vertex<DecoratedElement<Personaje>>> vertices = grafo.getVertices(); 
		while (vertices.hasNext()) { 
			Vertex<DecoratedElement<Personaje>> vertice = vertices.next();
			
			if (p1.equalsIgnoreCase(vertice.getElement().getElement().getName()))
				source = vertice;
			else if (p2.equalsIgnoreCase(vertice.getElement().getElement().getName()))
				target = vertice;
			
			if (source != null && target != null) break; // Si los ha encontrado sale del bucle.
		}
		
		// --- Comprobación de error 2: Personaje inexistente ---
		if (source == null || target == null) throw new IllegalArgumentException("Uno de los nombres indicados no existe.");
		
		// Adición de los personajes a la cola en su formato correcto
		colaPersonajes.add(source);
		colaPersonajes.add(target);
	}

	/***********************************
	 * @name limpiarEtiquetas
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Resetea las etiquetas (visitado, padre, distancia...) usadas para recorer el grafo. 
	 * 				Permite la ejecución de otra búsqueda sin que la anterior afecte a esta.
	 * 
	 * @param graph --> Grafo principal
	 ***********************************/
	
	public static void limpiarEtiquetas(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		Iterator<Vertex<DecoratedElement<Personaje>>> vertices = graph.getVertices();
		while (vertices.hasNext()) {
			DecoratedElement<Personaje> decorado = vertices.next().getElement();
			decorado.setVisited(false);
			decorado.setParent(null);
			decorado.setDistance(0);
		}
	}

	/***********************************
	 * @name DFS
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Aplica un algoritmo DFS al grafo desde start hasta encontrar end.
	 * 
	 * @param graph --> Grafo principal
	 * @param start --> Vértice origen del camino
	 * @param end --> Vértice destino del camino
	 * 
	 ***********************************/
	
	public static void DFS(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph, final Vertex<DecoratedElement<Personaje>> start, final Vertex<DecoratedElement<Personaje>> end) {
		
		// Marcamos el actual como visitado
		start.getElement().setVisited(true);
		
		// Miramos sus vértices adyacentes mediante las aristas incidentes
		Iterator<Edge<DecoratedElement<Integer>>> edges = graph.incidentEdges(start);
		while (edges.hasNext()) {
			Edge<DecoratedElement<Integer>> actualEdge = edges.next();							// Arista actual
			Vertex<DecoratedElement<Personaje>> nextVertex = graph.opposite(start, actualEdge); // Vértice siguiente

			// Si el nodo adyacente no está visitado
			if (!nextVertex.getElement().getVisited()) {
				Personaje personajeCandidato = nextVertex.getElement().getElement();
				
				// Si el personaje es el mismo que el que contiene el elemento decorado del vértice destino, terminamos con la arista final.
				if (nextVertex.getElement().equals(end.getElement())) {
					nextVertex.getElement().setParent(start.getElement()); // Asignamos su padre
					end.getElement().setVisited(true); // Y marcamos el nodo final como visitado
					return;
				}
				
				// Si el personaje cumple determinadas condiciones, se incluye en el camino.
				if ((!personajeCandidato.getSubType().equalsIgnoreCase("MEN") || personajeCandidato.getGender() != Gender.MALE) && personajeCandidato.getFreqSum() >= 80) {
					nextVertex.getElement().setParent(start.getElement()); // Marcamos su padre
					DFS(graph, nextVertex, end); // Pasamos a la siguiente iteración del DFS
					if (end.getElement().getVisited()) return; // Si obtenemos el final, terminamos.
				}
			}
			
		}
	}

	/***********************************
	 * @name BFS
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Aplica un algoritmo BFS al grafo desde start hasta encontrar end.
	 * 
	 * @param graph --> Grafo principal
	 * @param start --> Vértice origen del camino
	 * @param end --> Vértice destino del camino
	 * 
	 ***********************************/
	
	public static void BFS(Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph, Vertex<DecoratedElement<Personaje>> source, Vertex<DecoratedElement<Personaje>> target) {
		
		// Cola con los vértices a visitar
		Queue<Vertex<DecoratedElement<Personaje>>> vertices = new LinkedBlockingQueue<Vertex<DecoratedElement<Personaje>>>();
		vertices.add(source);
		
		// Mientras queden vértices por visitar
		while (!vertices.isEmpty()) {
			Vertex<DecoratedElement<Personaje>> actualVertex = vertices.poll(); // Vértice actual
			
			// si es el final, terminaremos
			// TODO: Esto realmente no es necesario, ya que se comprueba luego antes de obtener el siguiente vértice
			//if (actualVertex.getElement().getElement().getName().equals(target.getElement().getElement().getName())) return;
			
			// Marcamos el actual como visitado
			actualVertex.getElement().setVisited(true);

			Iterator<Edge<DecoratedElement<Integer>>> adyacentEdges = graph.incidentEdges(actualVertex);
			
			// Miramos sus vértices adyacentes mediante las aristas incidentes
			while (adyacentEdges.hasNext()) {
				Edge<DecoratedElement<Integer>> actualEdge = adyacentEdges.next(); // Arista actual
				Vertex<DecoratedElement<Personaje>> nextVertex = graph.opposite(actualVertex, actualEdge); // Vértice siguiente

				// Si el nodo adyacente no está visitado
				if (!nextVertex.getElement().getVisited()) {
					Personaje personajeCandidato = nextVertex.getElement().getElement();

					// Si el personaje es el último, terminamos.
					if (personajeCandidato.getName().equals(target.getElement().getElement().getName())) {
						nextVertex.getElement().setParent(actualVertex.getElement());
						return;
					}

					// Si el personaje cumple determinadas condiciones, se incluye en el camino.
					if (personajeCandidato.getType() == Type.PER && actualEdge.getElement().getElement() >= 10) {
						vertices.add(nextVertex); // Lo añadimos a la cola
						nextVertex.getElement().setParent(actualVertex.getElement()); // El siguiente nodo es el hijo del actual
					}
				}
			}
		}
	}

	/***********************************
	 * @name deshacerCamino
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Recorre todos los padres empezando desde el final hasta llegar al vértice origen para imprimir el camino.
	 * 
	 * @param target --> Elemento decorado del vértice destino donde se guarda el camino.
	 * 
	 * @return String con el camino encontrado.
	 ***********************************/
	public static String deshacerCamino(DecoratedElement<Personaje> target) {
		return (target.getParent() != null ? deshacerCamino(target.getParent()) : "")+ "\n"+ target;
	}
}
