
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {

		Stack<String> s = new Stack<String>();

		Scanner leerfichero = null;
		boolean seguir = true;
		try {
			File fichero = new File("navegacion.txt"); // este fichero debe estar al mismo nivel que la carpeta src del proyecto, no dentro de esta
			leerfichero = new Scanner(fichero);
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
			seguir = false;
		}

		int tiempototal = 0, cuentapaginas= 0;
		if (seguir) {
			while (leerfichero.hasNextLine()) {
				String linea = leerfichero.nextLine();
				String[] partes; // guardará la URL en [0] y el tiempo en [1]
				if (linea.equals("<=")) { // si es una vuelta atrás
					s.pop(); // Se quita la página en la que estábamos
					partes = s.peek().split(","); // se separan la página y el tiempo de la anterior
				} else { // es una URL
					s.push(linea); // se introduce a la pila
					partes = linea.split(","); // esa URL se separa del tiempo
				}

				cuentapaginas++; // se ha visitado una página más
				tiempototal += Integer.parseInt(partes[1]); // se suma el tiempo de esa página visitada
				System.out.println(partes[0]); // se muestra la página visitada
			}

		}
		System.out.println("El tiempo medio en las paginas es de: "+ tiempototal/cuentapaginas);
	}

}
