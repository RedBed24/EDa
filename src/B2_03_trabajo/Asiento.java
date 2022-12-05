package B2_03_trabajo;

import java.util.Objects;

public class Asiento {
	private Boolean ocupado= false;
	private String identificadorOcupante= null;
	
	public Asiento() {
		super();
	}
	
	public Asiento(final String identificadorOcupante) {
		this.identificadorOcupante= identificadorOcupante;
	}

	public boolean reservarAsiento(final String identificadorOcupante) {
		if (ocupado) return false; /* throw new IllegalStateException("Error, el asiento ya está ocupado."); */

		this.identificadorOcupante= identificadorOcupante;
		return ocupado= true;
	}
	
	public boolean isLibre() {
		return !ocupado;
	}
	
	public boolean isOcupado() {
		return ocupado;
	}

	@Override
	public String toString() {
		return (ocupado ? "Ocupado" : "Libre  ");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass()!= obj.getClass())
			return false;
		Asiento other = (Asiento) obj;
		return Objects.equals(identificadorOcupante, other.identificadorOcupante);
	}

}
