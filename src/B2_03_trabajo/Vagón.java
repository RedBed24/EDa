package B2_03_trabajo;

/*********************************************************************
* @name Vagón
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información acerca de los vagones, tanto sus características como los métodos que los manejan.
* 			   Los atributos son el número de filas y columnas, el número de vagón... Sin acabar. Primero, se termina y luego se comenta.
***********************************************************************/

public class Vagón {
	
	final public static int nColumnas = 4;
	final private int nFilas;
	final private int numVagón;
	final private Asiento [][]asientos;
	final private int asientosTotales; 
	private int asientosOcupados = 0;

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
	
	public static int getColumnas() {
		return nColumnas;
	}
	
	public int getFilas() {
		return nFilas;
	}

	public int getNumVagón() {
		return numVagón;
	}

	public int getAsientosTotales() {
		return asientosTotales;
	}

	public int getAsientosOcupados() {
		return asientosOcupados;
	}

	public int getAsientosLibres() {
		return asientosTotales - asientosOcupados;
	}
	
	public boolean reservarAsiento(final String identificadorOcupante) {
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (asiento.reservar(identificadorOcupante)) { // Si está ocupado TODO: curioso que no sea isOcupado(). Se concluye que reservarAsiento de Asiento es un setter
					asientosOcupados++;
					return true; // Devuelve que se reserva el asiento
				}
		return false;
	}
	/* TODO: qué decidimos? Hacer reservarAsiento y liberarAsiento o reservarAsiento y !reservarAsiento? Mi opinión es la siguiente:
	 * 
	 * Para un programa normal, yo no haría estas cosas de crear dos métodos para funciones booleanas, es decir, reservarAsiento
	 * y liberarAsiento ya que hacen lo contrario. Pondría reservarAsiento y !reservarAsiento. Sin embargo, para un TAD creo que
	 * es imprescindible hacer este tipo de cosas. No sé qué opinaréis pero conviene dejar esto claro */
	public boolean liberarAsiento(final String identificadorOcupante) {
		for (Asiento[] fila : asientos)
			for (Asiento asiento : fila)
				if (!asiento.liberar(identificadorOcupante)) { // Devuelve false porque ocupado = false. Entonces hay que hacer al revés para que se cumpla el if en esos casos.
					asientosOcupados--;
					return true; // Devuelve que se libera el asiento
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
