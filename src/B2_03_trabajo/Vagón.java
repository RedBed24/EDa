package B2_03_trabajo;

public class Vagón {
	
	final public static int nColumnas = 4;
	final private int nFilas;
	final private int numVagón;
	final private Asiento [][]asientos;
	final private int asientosTotales; 
	private int asientosOcupados = 0;

	public static int getNcolumnas() { //TODO: añadidos más getters y poco he hecho en las clases Asiento y Vagón. He tocado más Tren.
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
	
	public Vagón(final int numVagón, final int numFilas) {
		super();
		this.numVagón= numVagón;
		this.nFilas = numFilas;
		if (this.nFilas <= 0) throw new IllegalArgumentException("El vagón debe tener al menos una fila.");
		this.asientosTotales = nColumnas*nFilas;
		
		this.asientos = new Asiento[nColumnas][nFilas]; // TODO: No debería ser new Asiento[nFilas][nColumnas]???
		for (int i= 0; i< asientos.length; i++)
			for (int j= 0; j< asientos[i].length; j++) 
				asientos[i][j]= new Asiento(null);
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
		String devolver = "Vagón "+ numVagón+ "\nCuenta con: "+ getAsientosLibres()+" asientos libres.";
		for (int i = 0; i < asientos.length; i++) {

			devolver += "\nColumna: ";
			switch (i) {
				case 0: devolver += "Ventanilla Izquierda"; break;
				case 1: devolver += "Pasillo Izquierdo   "; break;
				case 2: devolver += "Pasillo Derecho     "; break;
				case 3: devolver += "Ventanilla Derecha  "; break;
			}
			devolver += "\n";

			for (int j = 0; j < asientos[i].length; j++)
				devolver += String.format("Asiento: %"+asientos[i].length+"d, estado: %s", j+1, asientos[i][j]);

		}
		return devolver;
	}
}
