package B2_03_trabajo;

public class Vagón {
	final public static int nColumnas= 4;

	final private int numVagón;
	final private Asiento [][]asientos;
	
	final private int asientosTotales; // para mejorar la complejidad de algunas operaciones a costa de espacio
	private int asientosOcupados= 0;

	public Vagón(final int numVaǵon, final int nFilas) {
		super();
		this.numVagón= numVaǵon;
		if (nFilas <= 0) throw new IllegalArgumentException("El vagón debe tener al menos 1 fila");
		asientos= new Asiento[nColumnas][nFilas];

		asientosTotales= nColumnas*nFilas;

		for (int i= 0; i< asientos.length; i++)
			for (int j= 0; j< asientos[i].length; j++) 
				asientos[i][j]= new Asiento();
	}
	
	public boolean reservarAsiento(final String identificadorOcupante) {
		for (Asiento[] asientos2 : asientos)
			for (Asiento asiento : asientos2)
				if (asiento.reservarAsiento(identificadorOcupante)) {
					asientosOcupados++;
					return true;
				}
		return false;
	}
	
	public int getAsientosTotales() {
		return asientosTotales;
	}

	public int getAsientosOcupados() {
		return asientosOcupados;
	}

	public int getAsientosLibres() {
		return asientosTotales- asientosOcupados;
	}
	
	@Override
	public String toString() {
		String devolver= "Vagón: "+ numVagón+ "\nCuenta con: "+ getAsientosLibres()+" asientos libres.";
		for (int i= 0; i< asientos.length; i++) {

			devolver+= "\nColumna: ";
			switch (i) {
			case 0: devolver+= "Ventanilla Izquierda"; break;
			case 1: devolver+= "Pasillo Izquierdo   "; break;
			case 2: devolver+= "Pasillo Derecho     "; break;
			case 3: devolver+= "Ventanilla Derecha  "; break;
			}
			devolver+= "\n";

			for (int j= 0; j< asientos[i].length; j++) {
				devolver+= String.format("Asiento: %"+asientos[i].length+"d, estado: %s", j+1, asientos[i][j]);
			}
		}
		return devolver;
	}
}
