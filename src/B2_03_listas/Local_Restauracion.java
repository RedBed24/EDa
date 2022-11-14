
/*********************************************************************
* @name Local_Restauracion
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la informaci�n acerca de los locales, tanto sus caracter�sticas como los m�todos que los manejan.
* 			   Los atributos son tipo de establecimiento, nombre, c�digo postal, poblaci�n, provincia y n�mero de visitas.
* 			   Los m�todos son el constructor, compareTo, toString y algunos getters.
***********************************************************************/
package B2_03_listas;

public class Local_Restauracion implements Comparable<Local_Restauracion>{
	
	// Enumeraciones del tipo de establecimiento y la provincia 
	public enum TipoEstablecimiento { RESTAURANTE, BAR, CAFETERÍA; };
	public enum Provincia { ALBACETE, CIUDAD_REAL, CUENCA, GUADALAJARA, TOLEDO; };
	
	private final TipoEstablecimiento tipo;	// Tipo de establecimiento: bar, restaurante o cafeter�a
	private final String nombre;	// Nombre del establecimiento
	private final int códigoPostal;	// C�digo postal del establecimiento
	private final String población;	// Poblaci�n (ciudad, pueblo, aldea...) donde se encuentra el establecimiento
	private final Provincia provincia;	// Provincia de Castilla La-Mancha donde se encuentra el establecimiento: Ciudad Real, Toledo, Cuenca, Albacete o Guadalajara
	private final int visitas;	// N�mero de visitas al establecimiento

	/***********************************
	 * @name Constructor de Local_Restauracion
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Inicializa los atributos de una instancia Local_Restauracion
	 * 
	 * @param tipo --> Tipo de establecimiento: bar, restaurante o cafeter�a
	 * @param nombre --> Nombre del establecimiento
	 * @param c�digoPostal --> C�digo postal del establecimiento
	 * @param poblaci�n --> Poblaci�n (ciudad, pueblo, aldea...) donde se encuentra el establecimiento
	 * @param provincia --> Provincia de Castilla La-Mancha donde se encuentra el establecimiento: Ciudad Real, Toledo, Cuenca, Albacete o Guadalajara
	 * @param visitas --> N�mero de visitas al establecimiento
	 ***********************************/
	public Local_Restauracion(TipoEstablecimiento tipo, String nombre, int códigoPostal, String población, Provincia provincia, int visitas) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.códigoPostal = códigoPostal;
		this.población = población;
		this.provincia = provincia;
		this.visitas = visitas;
	}
	
	/***********************************
	 * @name compareTo
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Indica qu� establecimiento es m�s visitado respecto a otro.
	 * 
	 * @param o --> Establecimiento con el que se compara.
	 * 
	 * @return 1 --> o tiene prioridad sobre this por tener este �ltimo un n�mero de visitas menor.
	 * @return -1 --> this es m�s visitado que o, debido a que el n�mero de visitas del primero es mayor.
	 ***********************************/
	public int compareTo(Local_Restauracion o) {
		// Si tienen las mismas visitas, tienen el mismo orden.
		return visitas == o.getVisitas() ? 0 :
			// Si this tiene menos visitas, debe estar debajo en orden (1)
			   visitas <  o.getVisitas() ? 1 : -1;
			// Si this tiene m�s visitas, debe estar encima en orden (-1)
	}

	public TipoEstablecimiento getTipo() {
		return tipo;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public String getPoblación() {
		return población;
	}

	public int getVisitas() {
		return visitas;
	}
	
	/***********************************
	 * @name toString
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Devuelve el tipo de establecimiento, el nombre, la poblaci�n y el n�mero de visitas.
	 * 
	 * @return String con la informaci�n especificada del establecimiento
	 ***********************************/
	public String toString() {
		return String.format("%-11s | %-40s | %-25s | %5d",tipo, nombre, población, visitas);
	}

}
