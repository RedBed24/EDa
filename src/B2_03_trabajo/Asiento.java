package B2_03_trabajo;

import java.util.Objects;

/*********************************************************************
* @name Asiento
* @authors DJS - B2 - 03
* @description Contiene la información acerca de los asientos, tanto sus características como los métodos que los manejan.
* Los atributos son el identificador del ocupante y si está ocupado o no.
* Los métodos son dos constructores, los getters (getIdentificadorOcupante(), isOcupado(), isLibre()), los
* setters (setIdentificadorOcupante, reservar, liberar), el equals y el toString.
***********************************************************************/
public class Asiento {
	
	private boolean ocupado = false; // Ocupación de un asiento: ocupado o libre
	private String identificadorOcupante = null; // Identificador del ocupante del asiento
	
	/**
	 * @name Constructor de Asiento
	 * @description Se crea un asiento vacío 
	 */
	public Asiento() {
		super();
	}
	
	/**
	 * @name Constructor de Asiento
	 * @description Se crea un asiento con ocupante
	 */
	public Asiento(String identificador) {
		identificadorOcupante = identificador;
		ocupado = true;
	}
	
	/** 
	 * @name getIdentificadorOcupante
	 * @description Devuelve el identificador del ocupante del asiento
	 * @return Identificador del ocupante
	 */
	public String getIdentificadorOcupante() {
		return identificadorOcupante;
	}
	
	/** 
	 * @name setIdentificadorOcupante
	 * @description Modifica el identificador del ocupante del asiento
	 * @param identificadorOcupante -> Nuevo identificador del ocupante del asiento
	 */
	public void setIdentificadorOcupante(String identificadorOcupante) {
		this.identificadorOcupante = identificadorOcupante;
	}
	
	/** 
	 * @name isOcupado
	 * @description Devuelve que el asiento está ocupado
	 * @return El asiento está ocupado
	 */
	public boolean isOcupado() {
		return ocupado;
	}
	
	/** 
	 * @name isLibre
	 * @description Devuelve que el asiento está libre
	 * @return El asiento está libre
	 */
	public boolean isLibre() {
		return !ocupado;
	}
	
	/** 
	 * @name reservar
	 * @description Reserva el asiento si está libre. Guarda el identificador del ocupante y 
	 * registra que, ahora, el asiento está ocupado
	 * @param identificadorOcupante -> Identificador del ocupante del asiento
	 * @return El asiento está ocupado, después de haberlo reservado
	 */
	public boolean reservar(final String identificadorOcupante) {
		if (isOcupado()) return false;
		setIdentificadorOcupante(identificadorOcupante);
		return ocupado = true; 
	}

	/** 
	 * @name liberar
	 * @description Libera el asiento si está ocupado y es el recibido como parámetro. Deja a null el identificador 
	 * del ocupante y registra que, ahora, el asiento está sin ocupar
	 * @param identificadorOcupante -> Ocupante del asiento a liberar
	 * @return El asiento está libre, después de haberlo liberado
	 */
	public boolean liberar(final String identificadorOcupante) {
		if (isLibre()) return false;
		if (!this.identificadorOcupante.equals(identificadorOcupante)) return false;
		this.identificadorOcupante = null;
		return !(ocupado = false); 
	}
	
	/** 
	 * @name equals
	 * @description Compara dos identificadores de ocupantes
	 * @param obj -> Un objeto
	 * @return true si son el mismo identificador, false en otro caso
	 */
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return Objects.equals(identificadorOcupante, ((Asiento)obj).getIdentificadorOcupante());
	}
	
	/** 
	 * @name toString
	 * @description Devuelve una cadena con la ocupación del asiento
	 * @return Cadena con la ocupación del asiento
	 */
	public String toString() { // En caso de que no se muestren los colores correctamente, pruebe a instalar desde el menú Help > Eclipse Marketplace la extensión ANSI Escape in Console
		return (ocupado ? "\033[31mOcupado\u001B[0m" : "\033[32mLibre\u001B[0m  "); // Ocupado en color rojo y Libre en color verde
	}
}
