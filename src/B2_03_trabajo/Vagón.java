package B2_03_trabajo;

public class Vagón {
	
	final public static int nColumnas = 4;
	final private int nFilas;
	final private int numVagón;
	final private Asiento [][]asientos;
	final private int asientosTotales; 
	private int asientosOcupados = 0;

	public Vagón(final int numVagón, final int numFilas) {
		super();
		this.numVagón= numVagón;
		if ((this.nFilas = numFilas) <= 0) throw new IllegalArgumentException("El vagón debe tener al menos una fila.");
		this.asientosTotales = nFilas*nColumnas;
		
		this.asientos = new Asiento[nFilas][nColumnas];
		for (int i= 0; i< asientos.length; i++)
			for (int j= 0; j< asientos[i].length; j++) 
				// se tiene que inicializar para que el toString sea más fácil, si vamos inicializando a medida que compramos, aunque es más eficiente en cuanto a memeoria, sería más complicado en cuanto a lógica
				asientos[i][j]= new Asiento();
	}
	
	public static int getNcolumnas() {
		return nColumnas;
	}
	
	public int getnFilas() {
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
				if (asiento.reservarAsiento(identificadorOcupante)) {
					asientosOcupados++;
					return true;
				}
		return false;
	}
	
	public String toString() {
		String devolver = "Vagón numero: "+ numVagón+ ".\nCuenta con: "+ getAsientosLibres()+" asientos libres.\n\n               ";
		devolver+= "Izquierda | Derecha\nFila | Ventana |      Pasillo      | Ventana |\n     -----------------------------------------";
		for (int i = 0; i < asientos.length; i++) {
			devolver+= String.format("\n%-5d| " , i+1);

			for (int j = 0; j < asientos[i].length; j++)
				devolver += asientos[i][j]+" | ";

		}
		return devolver+"\n     -----------------------------------------";
	}
}
