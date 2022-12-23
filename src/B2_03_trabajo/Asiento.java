package B2_03_trabajo;

import java.util.Objects;

/*********************************************************************
* @name Asiento
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información acerca de los asientos, tanto sus características como los métodos que los manejan.
* 			   Los atributos son el identificador del ocupante y si está ocupado o no.
* 			   Los métodos son el constructor vacío, los getters (getIdentificadorOcupante(), isOcupado(), isLibre()), los
* 			   setters (setIdentificadorOcupante(String), reservarAsiento(String), liberarAsiento(String)), el equals y el toString.
***********************************************************************/

public class Asiento {
	private Boolean ocupado = false;
	private String identificadorOcupante = null;
	
	public Asiento() {
		super();
	}
	
	public String getIdentificadorOcupante() {
		return identificadorOcupante;
	}
	
	public void setIdentificadorOcupante(String identificadorOcupante) {
		this.identificadorOcupante = identificadorOcupante;
	}
	
	public boolean isOcupado() {
		return ocupado;
	}
	
	public boolean isLibre() {
		return !ocupado;
	}
	
	public boolean reservar(final String identificadorOcupante) {
		if (ocupado) return false; /* throw new IllegalStateException("Error, el asiento ya está ocupado."); */
		this.identificadorOcupante = identificadorOcupante;
		return ocupado = true; // Devuelve que está ocupado = ocupado
	}
	
	public boolean liberar(final String identificadorOcupante) { 
		// TODO: Creo que es conveniente tener una forma de liberar un asiento y no quedar permanenetemente ocupado en la misma ejecución.
		// Yo también lo veo necesario, pero si le pasamos el identificador, supongo que será porque queremos que se libere si el ideintificador pasado es el del atirbuto
		// También entiendo que si llamas a liberar, esperas que te devuelva true si se ha podido liberar, ¿no? Por ello he cambiado los returns
		if (isLibre()) return false; // tenemos una función para comprobar si está libre, no tenemos porqué pensar en contario (aunque sí, es mas eficiente de la otra forma, pero prima más que se pueda leer)
		if (!this.identificadorOcupante.equals(identificadorOcupante)) return false; // si no coinciden los identificadores
		
		this.identificadorOcupante = null;
		return !(ocupado = false); // Devuelve que se ha podido liberar correctamente
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asiento other = (Asiento) obj;
		return Objects.equals(identificadorOcupante, other.getIdentificadorOcupante());
	}
	
	public String toString() {
		return (ocupado ? "\033[31mOcupado\u001B[0m" : "\033[32mLibre\u001B[0m  ");
	}

}
