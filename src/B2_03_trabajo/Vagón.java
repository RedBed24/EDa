package B2_03_trabajo;

/*********************************************************************
* @name Vag�n
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la informaci�n acerca de los vagones, tanto sus caracter�sticas como los m�todos que los manejan.
* 			   Los atributos son el n�mero de filas y columnas, el n�mero de vag�n... Sin acabar. Primero, se termina y luego se comenta.
* 			   Un vag�n es una colecci�n de asientos posicionados en filas y en 4 columnas
***********************************************************************/

public class Vag�n {
	
	final public static int nColumnas = 4;
	final private int nFilas;
	final private int numVag�n;
	final private Asiento [][]asientos;
	final private int asientosTotales; 
	private int asientosOcupados = 0;

	/**
	 * Constructor de Vag�n. Permite crear un vag�n especificando un n�mero para este y un n�mero de filas.
	 * Crea todos los asientos vac�os.
	 * @param numVag�n --> N�mero del vag�n que identifica a este.
	 * @param numFilas --> N�mero de filas que tendr� el vag�n. rango: [1, ?)
	 * 
	 * @throws IllegalArgumentException --> Si el n�mero de filas es 0 o menor.
	 */
	public Vag�n(final int numVag�n, final int numFilas) {
		super();
		this.numVag�n = numVag�n;
		if ((this.nFilas = numFilas) <= 0) throw new IllegalArgumentException("El vag�n debe tener al menos una fila.");
		this.asientosTotales = nFilas*nColumnas;
		
		this.asientos = new Asiento[nFilas][nColumnas];
		for (int i = 0; i < asientos.length; i++)
			for (int j = 0; j < asientos[i].length; j++) 
				asientos[i][j]= new Asiento();
	}
	/**
	 * Permite obtener el n�mero de columnas que componen este vag�n
	 * @return entero que representa la cantidad de columnas. rango: (0, ?)
	 */
	public static int getColumnas() {
		return nColumnas;
	}
	/**
	 * Permite obtener el n�mero de filas que componen este vag�n
	 * @return entero que representa la cantidad de filas. rango: (0, ?)
	 */
	public int getFilas() {
		return nFilas;
	}
	/**
	 * Permite obtener el n�mero de vag�n que es el vag�n actual.
	 * @return entero que representa el n�mero de vag�n. rango: (0, ?)
	 */
	public int getNumVag�n() {
		return numVag�n;
	}

	/**
	 * Permite obtener el n�mero de asientos que componen este vag�n
	 * @return entero que representa el tama�o de la colecci�n de asientos. rango: (0, ?)
	 */
	public int getAsientosTotales() {
		return asientosTotales;
	}

	/**
	 * Permite obtener el n�mero de Asientos que se encuentran actualmente ocupados en este vag�n.
	 * @return entero que representa la cantidad de asientos ocupados. rango: [0, asientosTotales]
	 */
	public int getAsientosOcupados() {
		return asientosOcupados;
	}

	/**
	 * Permite obtener el n�mero de Asietnos que se encuentran actualmente libres en este vag�n.
	 * @return entero que representa la cantidad de asietnos libres. rango: [0, asientosTotales]
	 */
	public int getAsientosLibres() {
		return asientosTotales - asientosOcupados;
	}
	
	/**
	 * Permite saber si el vag�n est� completamente lleno, esto es si todos los asientos est�n ocupados.
	 * @return true: Si est� lleno. false: Si hay al menos 1 asiento libre.
	 */
	public boolean isLleno() {
		return asientosTotales== asientosOcupados;
	}
	
	/**
	 * Permite saber si el vag�n est� completamente vac�o, esto es si todos los asientos est�n libres.
	 * @return true: Si est� vacio. false: Si al menos 1 asiento est� ocupado
	 */
	public boolean isVacio() {
		return asientosOcupados== 0;
	}
	
	/**
	 * Permite saber si el vag�n contiene alg�n asiento con el identificador dado.
	 * @param identificadorOcupante --> Identificador a buscar en el vag�n.
	 * @return true: Si el vag�n contiene un asiento con este identificiador. false: Si el vag�n no contiene un asiento con el identificador.
	 */
	public boolean identificadorEnUso(final String identificadorOcupante) {
		// TODO: Ser�a necesario comprobar si es null
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.isOcupado() && asiento.getIdentificadorOcupante().equals(identificadorOcupante))
					return true;
		return false;
	}

	/**
	 * Permite reservar un asiento con el identificador provisto. Se elige en orden de filas y columnas.
	 * @param identificadorOcupante --> Identificador que ocupar� el asiento
	 * @return true: Si se ha podido reservar un asiento. false: Si no quedaba hueco
	 */
	public boolean reservarAsiento(final String identificadorOcupante) {
		// TODO: Comprobar si ya existe un asiento con este identificador?
		// se podr�a hacer en este mismo bucle, por ahorrar iteraciones
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
				if (asiento.liberar(identificadorOcupante)) { // creo que era lioso antes, tiene m�s sentido pensar (por lo menos para m�) que liberar te va a devolver si se ha podido liberar o no
					asientosOcupados--;
					return true; // Devuelve que se libera el asiento (al igual que liberar de asiento, por consistencia)
				}
		return false;
	}
	
	public String toString() {
		String devolver = "Vag�n "+ numVag�n+ "\nCuenta con "+ getAsientosLibres()+" asientos libres\n\n               ";
		devolver+= " Izquierda  Derecha\nFila | Ventana |      Pasillo      | Ventana |\n     -----------------------------------------";
		for (int i = 0; i < asientos.length; i++) {
			devolver+= String.format("\n%-5d| " , i+1);

			for (int j = 0; j < asientos[i].length; j++)
				devolver += asientos[i][j]+" | ";

		}
		return devolver+"\n     -----------------------------------------";
	}
}