package colas;

public enum Speciality {
	TRAUMATOLOGÍA("Traumatology", 0), CARDIOLOGÍA("Cardiology", 1), NEUROLOGÍA("Neurology", 2);
	
	private String especialidad;
	private int departamento;
	
	private Speciality(String especialidad, int departamento) {
		this.especialidad = especialidad;
		this.departamento = departamento;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public int getDepartamento() {
		return departamento;
	}

	public void setDepartamento(int departamento) {
		this.departamento = departamento;
	}
}
