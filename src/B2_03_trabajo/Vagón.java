package B2_03_trabajo;

/*********************************************************************
* Contiene la información acerca de los vagones, tanto sus características como los métodos que los manejan.
* Los atributos son el número de filas y columnas, el número de vagón, la matriz de asientos, los asientos totales y los asientos ocupados.
* Los métodos son el constructor, los getters (getColumnas, getFilas, getNumVagón, getAsientosTotales, getAsientosOcupados, getAsientosLibres),
* operaciones para conocer el estado del vagón (isLleno, isVacio), operaciones relacionadas con identificadores de asientos 
* (getIdentificadorAsiento, setIdentificadorAsiento, identificadorEnUso, reservarAsiento, liberarAsiento) y métodos que muestran salidas 
* (mostrarOcupantesVagón y toString).
***********************************************************************/

public class Vagón {
	
	final public static int nColumnas = 4; // Número constante de columnas para todo vagón
	final private int nFilas; // Número de filas del vagón
	final private int numVagón; // Número de vagón
	final private Asiento [][]asientos; // Matriz de asientos
	final private int asientosTotales;  // Asientos totales del vagón
	private int asientosOcupados = 0; // Asientos ocupados del vagón

	/**
	 * Crea un vagón especificando su número identificativo y la cantidad de filas.
	 * Los asientos se crean pero sus atributos no se inicializan
	 * @param numVagón -> Número identificativo del vagón dentro del tren
	 * @param numFilas -> Número de filas que tendrá el vagón - Rango: [1,?)
	 */
	public Vagón(final int numVagón, final int numFilas) {
		super();
		this.numVagón = numVagón;
		this.nFilas = numFilas;
		this.asientosTotales = nFilas*nColumnas;
		this.asientos = new Asiento[nFilas][nColumnas];
		for (int i = 0; i < asientos.length; i++)
			for (int j = 0; j < asientos[i].length; j++) 
				asientos[i][j]= new Asiento();
	}
	
	/**
	 * Devuelve el número constante de columnas para los vagones
	 * @return Número de columnas para todo vagón
	 */
	public int getColumnas() {
		return nColumnas;
	}
	
	/**
	 * Devuelve el número de filas para los vagones
	 * @return Número de filas del vagón
	 */
	public int getFilas() {
		return nFilas;
	}

	/**
	 * Devuelve el número identificativo del vagón
	 * @return Número identificativo del vagón
	 */
	public int getNumVagón() {
		return numVagón;
	}
	
	/**
	 * Devuelve el número de asientos totales que tiene el vagón
	 * @return Entero que representa la cantidad de asientos que hay en el vagón
	 */
	public int getAsientosTotales() {
		return asientosTotales;
	}
	
	/**
	 * Devuelve el número de asientos que hay ocupados en el vagón
	 * @return Entero que representa los asientos ocupados del vagón - Rango [0, asientosTotales]
	 */
	public int getAsientosOcupados() {
		return asientosOcupados;
	}

	/**
	 * Devuelve el número de asientos libres en el vagón
	 * @return Entero que representa los asientos libres del vagón - Rango [0, asientosTotales]
	 */
	public int getAsientosLibres() {
		return asientosTotales - asientosOcupados;
	}

	/**
	 * Devuelve si el vagón está completamente lleno. Esto es, si todos los asientos están ocupados
	 * @return true si la cantidad de asientos ocupados es la de los asientos totales, false en otro caso
	 */
	public boolean isLleno() {
		return asientosTotales == asientosOcupados;
	}
	
	/**
	 * Devuelve si el vagón está completamente vacío. Esto es, si ningún asiento está ocupado
	 * @return true si la cantidad de asientos ocupados es cero, false en otro caso
	 */
	public boolean isVacio() {
		return asientosOcupados == 0;
	}
	
	/**
	 * Devuelve el identificador de un asiento en concreto, especificado mediante fila y columna para
	 * encontrarlo con la matriz de asientos
	 * @param filaAsiento -> Fila del vagón en la que se encuentra el identificador buscado
	 * @param columnaAsiento -> Columna del vagón en la que se sitúa el identificador buscado
	 * @return Identificador del asiento en la fila "filaAsiento" y en la columna "columnaAsiento"
	 */
	public String getIdentificadorAsiento(int filaAsiento, int columnaAsiento) {
		return asientos[filaAsiento][columnaAsiento].getIdentificadorOcupante();
	}
	
	/**
	 * Modifica los identificadores de los asientos que estén reservados por la misma persona
	 * @param antiguoOcupante -> Identificador de quien ocupa el asiento, necesario para encontrar el asiento a modificar
	 * @param nuevoOcupante -> Nuevo identificador del asiento encontrado
	 */
	public void setIdentificadorAsiento(String antiguoOcupante, String nuevoOcupante) {
		for(int i = 0; i < asientos.length; i++)
			for(int j = 0; j < asientos[i].length; j++)
				if(asientos[i][j].isOcupado() && asientos[i][j].getIdentificadorOcupante().startsWith(antiguoOcupante))
						asientos[i][j].setIdentificadorOcupante(nuevoOcupante);
	}
	
	/**
	 * Permite saber si el vagón contiene algún asiento con el identificador dado. Esto es, si al menos un asiento
	 * del vagón está ocupado por el mismo identificador indicado por parámetro
	 * @param identificadorOcupante -> Identificador a buscar en el vagón
	 * @return true si el vagón contiene un asiento con este identificador, false en otro caso
	 */
	public boolean identificadorEnUso(final String identificadorOcupante) {
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.isOcupado() && asiento.getIdentificadorOcupante().equals(identificadorOcupante))
					return true;
		return false;
	}

	/**
	 * Permite reservar un asiento del vagón, asignando el identificador indicado por parámetro al asiento correspondiente.
	 * Se destaca que la reserva de los asientos dentro de los mismos vagones se realiza de forma creciente por filas y columnas.
	 * @param identificadorOcupante -> Identificador del ocupante que reserva un asiento en el vagón
	 * @return true si se ha reservado el asiento correctamente, false en otro caso
	 */
	public boolean reservarAsiento(final String identificadorOcupante) {
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.reservar(identificadorOcupante)) {
					asientosOcupados++;
					return true;
				}
		return false;
	}

	/**
	 * Libera todas las reservas que correspondan al identificador indicado por parámetro.
	 * @param identificadorOcupante -> Identificador del ocupante a buscar para liberar el asiento del vagón
	 * @return true si se ha reservado el asiento correctamente
	 */
	public boolean liberarAsiento(final String identificadorOcupante) {
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.liberar(identificadorOcupante))
					asientosOcupados--;
		return true; 
	}
	
	/**
	 * Devuelve una cadena que muestra el vagón con los identificadores de todos sus ocupantes.
	 * En el fondo, es un toString() secundario o mejorado. 
	 * @return Cadena con la información del vagón, su disposición y los identificadores de todos sus ocupantes
	 */
	public String mostrarOcupantesVagón() {
		String devolver = "<VAGÓN "+ numVagón+ ">\tCuenta con "+ getAsientosLibres()+" asientos libres\n\n               ";
		devolver+= "       Izquierda        Derecha\nFila |   Ventana   |           Pasillo         |   Ventana   |\n     ---------------------------------------------------------------";
		for (int i = 0; i < asientos.length; i++) {
			devolver+= String.format("\n%-5d| " , i+1);

			for (int j = 0; j < asientos[i].length; j++)
				if(asientos[i][j].getIdentificadorOcupante() == null)
					devolver += " \033[32mLibre\u001B[0m      | "; // Libre en color verde
				else
					devolver += String.format("\033[31m%-12s\u001B[0m| ",asientos[i][j].getIdentificadorOcupante()); // Identificadores en color rojo

		}
		return devolver+"\n     ---------------------------------------------------------------\n";
	}
	
	/**
	 * Devuelve una cadena que muestra el vagón con la ocupación de todos los asientos.
	 * Consideramos este método el toString() principal pero podría ser el anterior perfectamente. El criterio a seguir para determinar cuál
	 * de los dos métodos es el toString() principal es el punto de vista desde el que se conciba. Si es el cliente el que ejecuta el programa,
	 * no debería poder ver la información de los demás, solo si está ocupado o no. En cambio, si es un administrador debería poder conocer
	 * todos los ocupantes del vagón
	 * @return Cadena con la información del vagón, su disposición y la ocupación de todos sus asientos
	 */
	public String toString() {
		String devolver = "<VAGÓN "+ numVagón+ ">\tCuenta con "+ getAsientosLibres()+" asientos libres\n\n               ";
		devolver+= " Izquierda  Derecha\nFila | Ventana |      Pasillo      | Ventana |\n     -----------------------------------";
		for (int i = 0; i < asientos.length; i++) {
			devolver+= String.format("\n%-5d| " , i+1);

			for (int j = 0; j < asientos[i].length; j++)
				devolver += asientos[i][j]+" | ";

		}
		return devolver+"\n     -----------------------------------\n";
	}
	
}
