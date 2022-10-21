package colas;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Hospital implements Constantes {

	private Queue<Paciente> traumatología;
	private Queue<Paciente> cardiología;
	private Queue<Paciente> neurología;
	
	public Hospital() {
		traumatología= new LinkedBlockingQueue<Paciente>();
		cardiología= new LinkedBlockingQueue<Paciente>();
		neurología= new LinkedBlockingQueue<Paciente>();
	}
	
	public void triage(Paciente paciente) {
		switch (paciente.getSpeciality()) {
			case TRAUMATOLOGÍA: traumatología.add(paciente); break;
			case CARDIOLOGÍA: cardiología.add(paciente); break;
			case NEUROLOGÍA: neurología.add(paciente); break;
			default: System.out.println("Error al añadir al paciente ya que su problema no puede ser tratado.");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
