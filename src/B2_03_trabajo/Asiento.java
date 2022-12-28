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
		if (isOcupado()) return false;
		this.identificadorOcupante = identificadorOcupante;
		return ocupado = true; // Devuelve que está ocupado tras reservar -> ocupado
	}

	public boolean liberar(final String identificadorOcupante) {
		if (isLibre()) return false;
		if (!this.identificadorOcupante.equals(identificadorOcupante)) return false; // No es el asiento a liberar
		this.identificadorOcupante = null;
		return !(ocupado = false); // Devuelve que no está ocupado tras liberar -> !ocupado
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return Objects.equals(identificadorOcupante, ((Asiento)obj).getIdentificadorOcupante());
	}
	
	public String toString() { // En caso de que no se muestren los colores correctamente, pruebe a instalar desde el menú Help > Eclipse Marketplace la extensión ANSI Escape in Console
		return (ocupado ? "\033[31mOcupado\u001B[0m" : "\033[32mLibre\u001B[0m  ");
	}
}
