package colas;

public enum Severity {
	VITAL("Vital",2), SEVERE("Severe",1), MILD("Mild",0);
	
	private String gravedad;
	private int prioridad;
	
	private Severity(String gravedad, int prioridad) {
		this.gravedad = gravedad;
		this.prioridad = prioridad;
	}

	public String getGravedad() {
		return gravedad;
	}

	public void setGravedad(String gravedad) {
		this.gravedad = gravedad;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
}
