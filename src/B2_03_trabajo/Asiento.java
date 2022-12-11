package B2_03_trabajo;

import java.util.Objects;

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
	
	public String informaciónCompleja() {
		return (ocupado ? "Ocupado por: "+ identificadorOcupante : "Libre");
	}

	public String toString() {
		return (ocupado ? "Ocupado" : "Libre  ");
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
	
	public boolean reservarAsiento(final String identificadorOcupante) {
		if (ocupado) return false; /* throw new IllegalStateException("Error, el asiento ya est� ocupado."); */
		// TODO: En estas situaciones, prob usemos excepciones a no ser que sean fáciles de capturar en la clase Principal o en otras
		this.identificadorOcupante = identificadorOcupante;
		return ocupado = true;
	}

}
