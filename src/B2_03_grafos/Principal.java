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
	final static String MENÚ = "\nIntroduce una opción:\n 1: Apartado a\n 2: Apartado b\n 3: Apartado c\n 4: Salir del programa";
	
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
			
			// Lectura de los vértices del grafo - VÉRTICES
			Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo = leerPersonajes(fichero);
			
			// Lectura del fichero de las relaciones
			f = new File("networks-id-3books.csv");
			fichero = new Scanner(f);
			
			// Lectura de las aristas del grafo - ARISTAS
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
		Vertex<DecoratedElement<Personaje>> source = null, target = null;
		Queue<Vertex<DecoratedElement<Personaje>>> colaPersonajes = new ArrayBlockingQueue<Vertex<DecoratedElement<Personaje>>>(2);
		while (true) { // Mientras el usuario no especifique la opción 5, la cual tiene un return */
			try {
				// Muestra del menú
				System.out.println(MENÚ);
				
				// Comprobación de la opción elegida por el usuario
				switch (TECLADO.nextInt()) {
				
					// Apartado a
					case 1: 
						System.out.println("- Cantidad de personajes en grafo: "+ grafo.getN());
						System.out.println("- Cantidad de relaciones: "+ grafo.getM());
						System.out.println("- Personaje con más cantidad de relaciones: \n\t"+ buscarPersonajeMayorInteracción(grafo)+"\n");
						System.out.println("- Pareja con el mayor nivel de interacción: "+ relaciónConMásInteractuación(grafo));
						break;
							
					// Apartado b
					case 2: 
						limpiarEtiquetas(grafo);
						pedirPersonajes(grafo, colaPersonajes);
						source = colaPersonajes.remove();
						target = colaPersonajes.remove();
						DFS(grafo, source, target);
						if (target.getElement().getParent()== null) System.out.println("No existe un camino entre los personajes dados.");
						else System.out.println(deshacerCamino(target.getElement()));
						break;
					
					// Apartado c
					case 3:
						limpiarEtiquetas(grafo);
						pedirPersonajes(grafo, colaPersonajes);
						source = colaPersonajes.remove();
						target = colaPersonajes.remove();
						BFS(grafo, source, target);
						if (target.getElement().getParent()== null) System.out.println("No existe un camino entre los personajes dados.");
						else System.out.println(deshacerCamino(target.getElement()));
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
		// buscar el nodo con mayor grado
		Personaje personajeMásRelaciones = null;
		int mayorRelaciones = 0;
		
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
		// buscar la arista con mayor peso
		Personaje personaje1= null, personaje2= null;
		int mayorRelaciones= 0;

		Iterator<Edge<DecoratedElement<Integer>>> aristas= graph.getEdges(); // Iterador con todas las aristas
		while (aristas.hasNext()) {
			Edge<DecoratedElement<Integer>> aristaActual= aristas.next();

			if (mayorRelaciones < aristaActual.getElement().getElement()) { // Si la arista actual tiene un peso mayor...
				// Actualizamos el mayor número de relaciones
				mayorRelaciones= aristaActual.getElement().getElement();
				
				// Obtenemos los vértices de la arista correspondiente
				Vertex<DecoratedElement<Personaje>> personajes[]= graph.endVertices(aristaActual);
				
				// Conseguimos los personajes de los vértices a partir de la arista
				personaje1 = personajes[0].getElement().getElement();
				personaje2 = personajes[1].getElement().getElement();
			}
		}
		return "\n\t" + personaje1+ " y \n\t"+personaje2+ " mantienen "+ mayorRelaciones+ " relaciones.";
	}
	

	/***********************************
	 * @name pedirPersonajes
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description 
	 * 
	 * @param source --> 
	 * @param target -->
	 * @param graph --> Grafo principal
	 * @param colaPersonajes --> 
	 * 
	 * @throws
	 * 
	 * @return Una cadena que indica la pareja y su cantidad de relaciones.
	 ***********************************/
	
	public static void pedirPersonajes(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo, Queue<Vertex<DecoratedElement<Personaje>>> colaPersonajes) {
		Vertex<DecoratedElement<Personaje>> source= null, target= null;
		//
		TECLADO.nextLine();
		//
		System.out.print("Introduce el primer personaje: ");
		final String p1 = TECLADO.nextLine();
		System.out.print("Introduce el segundo personaje: ");
		final String p2 = TECLADO.nextLine();
		//
		if(p1.equalsIgnoreCase(p2)) throw new IllegalArgumentException("Has indicado el mismo personaje. Debes indicar personajes distintos.");
		//
		Iterator<Vertex<DecoratedElement<Personaje>>> vertices = grafo.getVertices(); 
		// Recorremos todos los vértices mediante el Iterator correspondiente hasta que coincidan con el leído en el fichero
		while (vertices.hasNext()) { 
			Vertex<DecoratedElement<Personaje>> vertice = vertices.next();
			
			if (p1.equalsIgnoreCase(vertice.getElement().getElement().getName()))
				source = vertice;
			else if (p2.equalsIgnoreCase(vertice.getElement().getElement().getName()))
				target = vertice;
			
			if (source != null && target != null) break; // Si los ha encontrado sale del bucle.
		}
		
		//
		if (source == null || target == null) throw new IllegalArgumentException("Uno de los nombres indicados no existe.");
		colaPersonajes.add(source);
		colaPersonajes.add(target);
	}

	/***********************************
	 * @name limpiarEtiquetas
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Resetea las etiquetas (visitado, padre, distancia...) usadas para recorer el grafo. Permite la ejecución de otra búsqueda sin que la anterior afecte a esta
	 * 
	 * @param graph --> Grafo principal
	 ***********************************/
	
	public static void limpiarEtiquetas(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		Iterator<Vertex<DecoratedElement<Personaje>>> vertices= graph.getVertices();
		while (vertices.hasNext()) {
			DecoratedElement<Personaje> decorado= vertices.next().getElement();
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
	 * @description aplica un algoritmo DFS al grafo desde start hasta encontrar end, haciendo que todos los nodos intermedios cumplan ciertas condiciones y en estos, guarda una sucesión de padres
	 * 
	 * @param grafo --> Grafo principal
	 * @param start --> Vértice inicial desde el que se empezará a crear el camino
	 * @param end --> Vértice final cuando este se encuentre, se terminará el recorrido
	 * 
	 ***********************************/
	public static void DFS(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph, final Vertex<DecoratedElement<Personaje>> start, final Vertex<DecoratedElement<Personaje>> end) {
		// marcamos el actual como visitado
		start.getElement().setVisited(true);
		Iterator<Edge<DecoratedElement<Integer>>> edges= graph.incidentEdges(start);
		
		// miraremos sus vértices adyacentes mediante las aristas
		while (edges.hasNext()) {
			Edge<DecoratedElement<Integer>> actualEdge = edges.next();
			Vertex<DecoratedElement<Personaje>> nextVertex = graph.opposite(start, actualEdge);

			// si el nodo adyacente no está visitado
			if (!nextVertex.getElement().getVisited()) {
				// obtenemos el personaje para ver si cumple las condiciones
				Personaje personajeCandidato = nextVertex.getElement().getElement();
				
				// si es el que buscábamos
				if (nextVertex.getElement().equals(end.getElement())) {
					// marcamos su padre
					nextVertex.getElement().setParent(start.getElement());
					// lo marcamos como visitado
					end.getElement().setVisited(true);
					// terminamos
					return;
				}
				// si el personaje cumple las condiciones, pasará a pertenecer al camino
				if ((!personajeCandidato.getSubType().equalsIgnoreCase("MEN") || personajeCandidato.getGender() != Gender.MALE) && personajeCandidato.getFreqSum() >= 80) {
					// marcamos su padre
					nextVertex.getElement().setParent(start.getElement());
					// pasamos a la siguiente iteración del DFS (donde además se marcará como visitado este personaje)
					DFS(graph, nextVertex, end);
					// si hemos obtenido el final, terminaremos
					if (end.getElement().getVisited()) return;
				}
			}
			
		}
	}

	/***********************************
	 * @name BFS
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description aplica un algoritmo BFS al grafo desde start hasta encontrar end, haciendo que todos los nodos intermedios cumplan ciertas condiciones y en estos, guarda una sucesión de padres
	 * 
	 * @param grafo --> Grafo principal
	 * @param start --> Vértice inicial desde el que se empezará a crear el camino
	 * @param end --> Vértice final cuando este se encuentre, se terminará el recorrido
	 * 
	 ***********************************/
	public static void BFS(Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo, Vertex<DecoratedElement<Personaje>> source, Vertex<DecoratedElement<Personaje>> target) {
		// lista que contendrá todos los vértices a visitar
		Queue<Vertex<DecoratedElement<Personaje>>> vertices= new LinkedBlockingQueue<Vertex<DecoratedElement<Personaje>>>();
		vertices.add(source);
		
		// mientras queden vértices por visitar
		while (!vertices.isEmpty()) {
			// obtenermos el vértice a visitar
			Vertex<DecoratedElement<Personaje>> actualVertex= vertices.poll();
			
			// si es el final, terminaremos
			// TODO: Esto realmente no es necesario, ya que se comprueba luego antes de obtener el siguiente vértice
			if (actualVertex.getElement().getElement().getName().equals(target.getElement().getElement().getName())) return;
			
			// lo marcamos como visitado
			actualVertex.getElement().setVisited(true);

			Iterator<Edge<DecoratedElement<Integer>>> adyacentEdges= grafo.incidentEdges(actualVertex);
			
			// miramos los vértices adyacentes al actual mediante las aristas
			while (adyacentEdges.hasNext()) {
				Edge<DecoratedElement<Integer>> actualEdge= adyacentEdges.next();
				Vertex<DecoratedElement<Personaje>> nextVertex= grafo.opposite(actualVertex, actualEdge);

				// si el vértice adyacente no está visitado
				if (!nextVertex.getElement().getVisited()) {
					Personaje candidato= nextVertex.getElement().getElement();

					// si es el final, terminamos
					if (candidato.getName().equals(target.getElement().getElement().getName())) {
						nextVertex.getElement().setParent(actualVertex.getElement());
						return;
					}

					// comprobamos si cumple las condiciones necesarias para poder ser el siguiente vértice
					if (candidato.getType()== Type.PER && actualEdge.getElement().getElement()>= 10) {
						// lo añadimos a la cola
						vertices.add(nextVertex);
						// marcamos que el siguiente es el hijo del actual
						nextVertex.getElement().setParent(actualVertex.getElement());
					}
				}
			}
		}
	}

	/***********************************
	 * @name deshacerEtiquetas
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description recorre todos los padres empezando desde el final hasta llegar al padre que tenga null, el cual es el principio, entonces devuelve el nombre de este e irá añadiendo los nombres de los hijos a la derecha
	 * 
	 * @param target --> Elemento Decorado del final del camino
	 * 
	 * @return String con todos los personajes contenidos en el camino
	 ***********************************/
	public static String deshacerCamino(DecoratedElement<Personaje> target) {
		return (target.getParent()!= null ? deshacerCamino(target.getParent()) : "")+ "\n"+ target;
	}
}
