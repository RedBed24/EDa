package colas;

public class Paciente implements Comparable<Paciente> {
	
	private int severity;
	private int speciality;
	
	public Paciente(int severity, int speciality) {
		this.severity= severity;
		this.speciality= speciality;
	}

	public int compareTo(Paciente other) {
		if (severity > other.severity) return 1;
		if (severity < other.severity) return -1;
		return 0;
	}
	
	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public int getSpeciality() {
		return speciality;
	}

	public void setSpeciality(int speciality) {
		this.speciality = speciality;
	}
	
}
