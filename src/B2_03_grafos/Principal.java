package B2_03_grafos;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import B2_03_grafos.Personaje.*;

import graphsDSESIUCLM.*;

public class Principal {

	public static Graph<DecoratedElement<Personaje>, Integer> leerPersonajes(final Scanner fichero) {
		final Graph<DecoratedElement<Personaje>, Integer> devolver= new TreeMapGraph<DecoratedElement<Personaje>, Integer>();
		
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
	
	public static Graph<DecoratedElement<Personaje>, Integer> leerRelaciones(final Scanner fichero, Graph<DecoratedElement<Personaje>, Integer> graph) {
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
				}
				
				System.out.println(graph.getM());
				System.out.println(data[0] + " " +source.getElement().getID()+ " "+ data[1]+ " " +target.getElement().getID());

				graph.insertEdge(source, target, Integer.parseInt(data[2]));

			} catch (IndexOutOfBoundsException e) {
				System.err.println("No se ha recibido la cantidad de información necesaria.");
			} catch (NumberFormatException e) {
				System.err.println("No se ha podido convertir a entero la entrada esperada.");
			} catch (IllegalArgumentException e) {
				System.err.println("Se ha recibido un dato erróneo: "+e);
			}
		}
		return graph;
	}
	
	public static void main(String[] args) {
		try { 
			File f= new File("lotr-pers.csv");
			Scanner fichero= new Scanner(f);
			
			Graph<DecoratedElement<Personaje>, Integer> grafo= leerPersonajes(fichero);
			
			f= new File("networks-id-3books.csv");
			fichero= new Scanner(f);
			
			grafo= leerRelaciones(fichero, grafo);
			
			System.out.println("Cantidad de personajes en grafo: "+ grafo.getN());
			System.out.println("Cantidad de relaciones: "+ grafo.getM());
		} catch (Exception e){
			System.err.println("Error inesperado.");
		}
	}

}
