package B2_03_grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import B2_03_grafos.Personaje.*;

import graphsDSESIUCLM.*;

public class Principal {

	public static Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> leerPersonajes(final Scanner fichero) {
		final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> devolver= new TreeMapGraph<DecoratedElement<Personaje>, DecoratedElement<Integer>>();
		
		fichero.nextLine();
		
		while (fichero.hasNext()) {
			try {
				final String[] data= fichero.nextLine().split(";");
			/* 
			 * data[0] id
			 * data[1] type
			 * data[2] subtype
			 * data[3] name
			 * data[4] gender
			 * data[5] FreqSum
			 * */
			
				final String id= data[0];
			
				final Type type;
				switch (data[1].toUpperCase()) {
				case "PLA":  type= Type.PLA;  break;
				case "PER":  type= Type.PER;  break;
				case "GRO":  type= Type.GRO;  break;
				case "THIN": type= Type.THIN; break;
				default: throw new IllegalArgumentException("Unexpected Type value: " + data[1]);
				}	
			
				final String subtype= data[2];
			
				final String name= data[3];
			
				final Gender gender;
				switch (data[4].toUpperCase()) {
				case "MALE":   gender= Gender.MALE;   break;
				case "FEMALE": gender= Gender.FEMALE; break;
				default:       gender= Gender.UKNOWN; break;
				}
			
				final int freqsum= Integer.parseInt(data[5]);
			
				final Personaje personaje= new Personaje(type, subtype, name, gender, freqsum);
			
				final DecoratedElement<Personaje> decorated= new DecoratedElement<>(id, personaje);

				devolver.insertVertex(decorated);
			
			} catch (IndexOutOfBoundsException e) {
				System.err.println("No se ha recibido la cantidad de información necesaria.");
			} catch (NumberFormatException e) {
				System.err.println("No se ha podido convertir a entero la entrada esperada.");
			} catch (IllegalArgumentException e) {
				System.err.println("Se ha recibido un dato erróneo: "+e);
			}
		}
		/* añadir directamente al grafo */
		return devolver;
	}
	
	public static Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> leerRelaciones(final Scanner fichero, Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		fichero.nextLine();

		while (fichero.hasNext()) {
			try {
				final String[] data= fichero.nextLine().split(";");
				/* 
				 * data[0] idSource
				 * data[1] idTarget
				 * data[2] weight
				 * */
				Vertex<DecoratedElement<Personaje>> source= null, target= null;
				Iterator<Vertex<DecoratedElement<Personaje>>> vertices= graph.getVertices();
				while (vertices.hasNext()) {
					Vertex<DecoratedElement<Personaje>> vertice= vertices.next();
					
					if (data[0].equalsIgnoreCase(vertice.getElement().getID()))
						source= vertice;
					else if (data[1].equalsIgnoreCase(vertice.getElement().getID()))
						target= vertice;
					if (source!= null && target!= null) break;
				}

				graph.insertEdge(source, target, new DecoratedElement<Integer>(source.getElement().getID()+target.getElement().getID(), Integer.parseInt(data[2])));

			} catch (IndexOutOfBoundsException e) {
				System.err.println("No se ha recibido la cantidad de información necesaria.");
			} catch (NumberFormatException e) {
				System.err.println("No se ha podido convertir a entero la entrada esperada.");
			} 
		}
		// realmente no es necesario ya que se pasa por referencia.
		return graph;
	}
	
	public static String buscarPersonajeMayorInteracción(final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		Personaje personajeMasRelaciones= null;
		int mayorRelaciones= 0;
		
		Iterator<Vertex<DecoratedElement<Personaje>>> nodos= graph.getVertices();
		while (nodos.hasNext()) {
			Vertex<DecoratedElement<Personaje>> nodoActual= nodos.next();
			
			int cuenta= 0; // contar las aristas (relaciones) que tiene un nodo (personaje)
			Iterator<Edge<DecoratedElement<Integer>>> aristas= graph.incidentEdges(nodoActual);
			while (aristas.hasNext()) {
				aristas.next();
				cuenta++;
			}
			
			// obtener la cuenta mayor
			if (mayorRelaciones< cuenta) {
				mayorRelaciones= cuenta;
				personajeMasRelaciones= nodoActual.getElement().getElement();
			}
		}
		return personajeMasRelaciones+ " tiene "+ mayorRelaciones+ " relaciones.";
	}
	
	public static String relaciónConMásInteractuación (final Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> graph) {
		Personaje personaje1= null, personaje2= null;
		int mayorRelaciones= 0;

		Iterator<Edge<DecoratedElement<Integer>>> aristas= graph.getEdges();
		while (aristas.hasNext()) {
			Edge<DecoratedElement<Integer>> aristaActual= aristas.next();

			if (mayorRelaciones< aristaActual.getElement().getElement()) {
				// actualizamos el número mayor de relaciones
				mayorRelaciones= aristaActual.getElement().getElement();
				// obtenemos los vértices de la arista
				Vertex<DecoratedElement<Personaje>> personajes[]= graph.endVertices(aristaActual);
				// sacamos los personajes de los vértices
				personaje1= personajes[0].getElement().getElement();
				personaje2= personajes[1].getElement().getElement();
			}
		}
		
		return personaje1+ " y "+personaje2+ " mantienen "+ mayorRelaciones+ " relaciones.";
	}

	public static void main(String[] args) {
		try { 
			File f= new File("lotr-pers.csv");
			Scanner fichero= new Scanner(f);
			
			Graph<DecoratedElement<Personaje>, DecoratedElement<Integer>> grafo= leerPersonajes(fichero);
			
			f= new File("networks-id-3books.csv");
			fichero= new Scanner(f);
			
			grafo= leerRelaciones(fichero, grafo);
			
			// apartado a
			System.out.println("Cantidad de personajes en grafo: "+ grafo.getN());
			System.out.println("Cantidad de relaciones: "+ grafo.getM());
			System.out.println("Personaje con más cantidad de relaciones: "+ buscarPersonajeMayorInteracción(grafo));
			System.out.println("Personajes que mantienen más relaciones: "+ relaciónConMásInteractuación(grafo));
			
			// apartado b
			
			// apartado c
		} catch (FileNotFoundException e) {
			System.err.println("No se ha encontrado alguno de los archivos.");
		} catch (Exception e){
			System.err.println("Error inesperado.");
		}
	}

}
