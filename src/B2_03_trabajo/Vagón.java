package B2_03_trabajo;

/*********************************************************************
* @name Vagón
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información acerca de los vagones, tanto sus características como los métodos que los manejan.
* 			   Los atributos son el número de filas y columnas, el número de vagón... Sin acabar. Primero, se termina y luego se comenta.
* 			   Un vagón es una colección de asientos posicionados en filas y en 4 columnas
***********************************************************************/

public class Vagón {
	
	final public static int nColumnas = 4;
	final private int nFilas;
	final private int numVagón;
	final private Asiento [][]asientos;
	final private int asientosTotales; 
	private int asientosOcupados = 0;

	/**
	 * Constructor de Vagón. Permite crear un vagón especificando un número para este y un número de filas.
	 * Crea todos los asientos vacíos.
	 * @param numVagón --> Número del vagón que identifica a este.
	 * @param numFilas --> Número de filas que tendrá el vagón. rango: [1, ?)
	 * 
	 * @throws IllegalArgumentException --> Si el número de filas es 0 o menor.
	 */
	public Vagón(final int numVagón, final int numFilas) {
		super();
		this.numVagón = numVagón;
		if ((this.nFilas = numFilas) <= 0) throw new IllegalArgumentException("El vagón debe tener al menos una fila.");
		this.asientosTotales = nFilas*nColumnas;
		
		this.asientos = new Asiento[nFilas][nColumnas];
		for (int i = 0; i < asientos.length; i++)
			for (int j = 0; j < asientos[i].length; j++) 
				asientos[i][j]= new Asiento();
	}
	/**
	 * Permite obtener el número de columnas que componen este vagón
	 * @return entero que representa la cantidad de columnas. rango: (0, ?)
	 */
	public static int getColumnas() {
		return nColumnas;
	}
	/**
	 * Permite obtener el número de filas que componen este vagón
	 * @return entero que representa la cantidad de filas. rango: (0, ?)
	 */
	public int getFilas() {
		return nFilas;
	}
	/**
	 * Permite obtener el número de vagón que es el vagón actual.
	 * @return entero que representa el número de vagón. rango: (0, ?)
	 */
	public int getNumVagón() {
		return numVagón;
	}

	/**
	 * Permite obtener el número de asientos que componen este vagón
	 * @return entero que representa el tamaño de la colección de asientos. rango: (0, ?)
	 */
	public int getAsientosTotales() {
		return asientosTotales;
	}

	/**
	 * Permite obtener el número de Asientos que se encuentran actualmente ocupados en este vagón.
	 * @return entero que representa la cantidad de asientos ocupados. rango: [0, asientosTotales]
	 */
	public int getAsientosOcupados() {
		return asientosOcupados;
	}

	/**
	 * Permite obtener el número de Asietnos que se encuentran actualmente libres en este vagón.
	 * @return entero que representa la cantidad de asietnos libres. rango: [0, asientosTotales]
	 */
	public int getAsientosLibres() {
		return asientosTotales - asientosOcupados;
	}
	
	/**
	 * Permite saber si el vagón está completamente lleno, esto es si todos los asientos están ocupados.
	 * @return true: Si está lleno. false: Si hay al menos 1 asiento libre.
	 */
	public boolean isLleno() {
		return asientosTotales== asientosOcupados;
	}
	
	/**
	 * Permite saber si el vagón está completamente vacío, esto es si todos los asientos están libres.
	 * @return true: Si está vacio. false: Si al menos 1 asiento está ocupado
	 */
	public boolean isVacio() {
		return asientosOcupados== 0;
	}
	
	/**
	 * Permite saber si el vagón contiene algún asiento con el identificador dado.
	 * @param identificadorOcupante --> Identificador a buscar en el vagón.
	 * @return true: Si el vagón contiene un asiento con este identificiador. false: Si el vagón no contiene un asiento con el identificador.
	 */
	public boolean identificadorEnUso(final String identificadorOcupante) {
		// TODO: Sería necesario comprobar si es null
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.isOcupado() && asiento.getIdentificadorOcupante().equals(identificadorOcupante))
					return true;
		return false;
	}

	/**
	 * Permite reservar un asiento con el identificador provisto. Se elige en orden de filas y columnas.
	 * @param identificadorOcupante --> Identificador que ocupará el asiento
	 * @return true: Si se ha podido reservar un asiento. false: Si no quedaba hueco
	 */
	public boolean reservarAsiento(final String identificadorOcupante) {
		// TODO: Comprobar si ya existe un asiento con este identificador?
		// se podría hacer en este mismo bucle, por ahorrar iteraciones
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.reservar(identificadorOcupante)) {
					asientosOcupados++;
					return true; // Devuelve que se reserva el asiento
				}
		return false;
	}
	
	/**
	 * Permite liberar el asiento que tenga el Identificador dado.
	 * @param identificadorOcupante --> Identificador del asiento a liberar
	 * @return true: Si se ha podido liberar un asiento, false: Si niguno de los asientos se ha podido liberar
	 */
	public boolean liberarAsiento(final String identificadorOcupante) {
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.liberar(identificadorOcupante)) { // creo que era lioso antes, tiene más sentido pensar (por lo menos para mí) que liberar te va a devolver si se ha podido liberar o no
					asientosOcupados--;
					return true; // Devuelve que se libera el asiento (al igual que liberar de asiento, por consistencia)
				}
		return false;
	}
	
	public String toString() {
		String devolver = "Vagón "+ numVagón+ "\nCuenta con "+ getAsientosLibres()+" asientos libres\n\n               ";
		devolver+= " Izquierda  Derecha\nFila | Ventana |      Pasillo      | Ventana |\n     -----------------------------------------";
		for (int i = 0; i < asientos.length; i++) {
			devolver+= String.format("\n%-5d| " , i+1);

			for (int j = 0; j < asientos[i].length; j++)
				devolver += asientos[i][j]+" | ";

		}
		return devolver+"\n     -----------------------------------------";
	}
}