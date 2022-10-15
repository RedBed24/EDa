
import java.util.Stack;
import java.util.Scanner;
import java.io.*;

public class sinMétodos_Dani {
	public static void main(String args[])  {
		
		System.out.println("\n*******************************\n* ESTRUCTURA DE DATOS - PILAS *\n*******************************");
		
		Stack<String> navegador=new Stack(); // Declaración de la pila donde guardaremos el flujo de las páginas web 

		////////////////// Lectura fichero navegación.txt
		Scanner teclado = null;
		boolean seguir = true;
		
		try {
			// Este fichero debe estar al mismo nivel que la carpeta src del proyecto, no dentro de esta
			File fichero=new File("navegación.txt");
			teclado = new Scanner(fichero);
		} catch (FileNotFoundException e) {
			System.out.println("\nEl fichero no se ha encontrado.\n"+e.toString());
			seguir=false;
		}
		
		//////////////////
		int tiempoTotal=0, páginasVisitadas=0, páginasDistintas=0, vueltasAtrás=0;
		if(teclado!=null) {
			while (teclado.hasNextLine()) {
				String línea= teclado.nextLine();
				String[] partes;
				if (línea.equals("<=")) {
					navegador.pop();
					partes=navegador.peek().split(",");
					vueltasAtrás+=1;
					//dividir por , y sumar el tiempo [1]
					// mostar split [0]
				} else {
					navegador.push(línea);
					partes=línea.split(",");
					páginasDistintas+=1;
				}
				páginasVisitadas+=1;
				tiempoTotal+=Integer.parseInt(partes[1]);
				System.out.println("\nPaso "+páginasVisitadas +" --> "+partes[0]+"\n");
			}
			
		//////////////
			System.out.println("\nEl tiempo total navegando en la página web de la UCLM ha sido de "+tiempoTotal+" segundos.");
			System.out.println("Se visitaron en total "+páginasVisitadas+" páginas: "+páginasDistintas+" distintas y "+vueltasAtrás+" veces se regresó a la página anterior.");
			System.out.println("El tiempo medio de permanencia en una página fue aproximadamente de "+ Math.round((double)tiempoTotal/páginasVisitadas)+" segundos.");
		}
	}
	

}
