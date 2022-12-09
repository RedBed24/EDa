package B2_03_trabajo;

import java.util.ArrayList;
import java.util.List;

public class Tren {
	final private List<Vagón> vagones;
	final private int numMáxVagones;
	final private int numFilasVagón;

	public Tren(final int numMáxVagones, final int numFilasVagón) {
		super();
		if ((this.numMáxVagones = numMáxVagones) <= 0) throw new IllegalArgumentException("El tren debe tener al menos un vagón.");
		this.vagones = new ArrayList<Vagón>(numMáxVagones);
		if ((this.numFilasVagón = numFilasVagón) <= 0) throw new IllegalArgumentException("El vagón necesita tener al menos una fila.");
		vagones.add(new Vagón(0, numFilasVagón));
	}
	
	public int getNumVagones() {
		return vagones.size();
	}
	
	public int getNumMáxVagones() {
		return numMáxVagones;
	}
	
	public int capacidadVagón() {
		// El número de asientos totales del vagón es el número de filas por el de columnas.
		return vagones.get(0).getAsientosTotales();
	}
	
	public int capacidadTren() { // TODO: estos estaban con otros nombres que quizás pueden confundir.
		// El número de asientos totales del tren es igual a la capacidad de un vagón por el número de vagones
		return capacidadVagón() * numMáxVagones;
	}
	
	public int libresTren() {
		// El número de asientos libres del tren es igual a la suma de asientos libres de cada vagón del tren.
		int libres = 0;
		for(Vagón vagón: vagones)
			libres += vagón.getAsientosLibres();
		return libres;
	}
	public int ocupadosTren() { //TODO: literalmente, el método anterior y este son el contrario. De hecho, (por ejemplo) el
								// de libresTren puede ser capacidadTren() - ocupadosTren() antes que eso.
		// El número de asientos ocupados del tren es igual a la suma de asientos ocupados de cada vagón del tren.
		int ocupados = 0;
		for (Vagón vagón : vagones)
			ocupados += vagón.getAsientosOcupados();
		return ocupados;
	}
	
	/*public int getAsientosLibresMáx() {
		return getAsientosTotalesMáx()* getAsientosOcupados();
	}*/ // TODO: @Samu este método no lo entiendo. Puede que quizás se te colara o es que soy retrasado xddddd
	
	public String reservarAsiento(final String identificadorOcupante) {
		String mensaje = "";
		
		// Si el tren está lleno...
		if(libresTren() == 0) {
			if(vagones.size()+1 >= numMáxVagones) // ...y no se pueden añadir más vagones, regresa un valor nulo.
				mensaje = null; // Este caso es equivalente a if(trenlleno()) mensaje = null;
			else {	// ...y se pueden añadir más vagones, se añade un vagón más y se realiza la reserva
				vagones.add(new Vagón(vagones.size(), numFilasVagón));	
				vagones.get(vagones.size()).reservarAsiento(identificadorOcupante);
			}
		} 
		// Si el tren tiene algún asiento libre...
		else { 
			// Se obtiene el vagón menos lleno...
			Vagón menosLleno = vagones.get(0); 
			for (Vagón vagón : vagones)
				if (menosLleno.getAsientosLibres() < vagón.getAsientosLibres())
					menosLleno = vagón;
			// Y se realiza la reserva del asiento.
			if (menosLleno.reservarAsiento(identificadorOcupante)) mensaje = "Éxito";
			else mensaje = null;
		}

		return mensaje; 
		// TODO: Quizás, este método puede devolver otra cosa más que un boolean y mensaje = null es equivalente a una excepción o lo que sea.
	}
	
	public int numPasajeros() { 
		// El número de pasajeros del tren es la suma de todos los asientos ocupados de cada vagón
		int pasajeros = 0;
		for(Vagón vagón: vagones)
			pasajeros += vagón.getAsientosOcupados();
		return pasajeros; /* TODO: el ejercicio pide que añadamos explícitamente 3 operaciones:
		 						numPasajeros, trenLleno y reservarAsiento. Tmb dice que añadamos todo lo que haga un TAD
		 						completo y útil. Pensemos en más métodos y/o pasemos ya al menú en sí y realizar el resto.
		 						Tmb puede ser que conforme hagamos el trabajo nos surjan ideas de nuevos métodos, incluso
		 						nuevos atributos.*/
	}
	
	public boolean trenLleno() { 
		// Ningún asiento está libre
		for (Vagón vagón : vagones)
			if (vagón.getAsientosLibres() != 0) return false;
		
		// No se pueden añadir más vagones al tren
		return vagones.size()+1 < numMáxVagones;

	}
	
}
