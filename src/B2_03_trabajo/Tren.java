package B2_03_trabajo;

import java.util.ArrayList;
import java.util.List;

public class Tren {
	final private List<Vagón> vagones;
	final private int numMáxVagones;
	final private int numFilasVagón;

	public Tren(final int numMáxVagones, final int numFilasVagón) {
		super();
		if ((this.numMáxVagones= numMáxVagones) <= 0) throw new IllegalArgumentException("El tren debe tener al menos un vagón.");
		this.vagones= new ArrayList<Vagón>(numMáxVagones);
		if ((this.numFilasVagón= numFilasVagón) <= 0) throw new IllegalArgumentException("El tren necesita tener al menos 1 fila.");
		
		vagones.add(new Vagón(0, numFilasVagón));
	}
	
	public int getNumVagones() {
		return vagones.size();
	}
	
	public int getNumMáxVagones() {
		return numMáxVagones;
	}
	
	public int getAsientosTotalesVagón() {
		return vagones.get(0).getAsientosTotales();
	}
	
	public int getAsientosTotalesMáx() {
		return numMáxVagones* getAsientosTotalesVagón();
	}

	public int getAsientosOcupados() {
		int cantidad= 0;
		for (Vagón vagón : vagones)
			cantidad+= vagón.getAsientosOcupados();
		return cantidad;
	}
	
	public int getAsientosLibresMáx() {
		return getAsientosTotalesMáx()* getAsientosOcupados();
	}
	
	public boolean isLleno() {
		for (Vagón vagón : vagones)
			/* si alguno tiene un asiento libre, no está lleno */
			if (vagón.getAsientosLibres()!= 0) return false;

		/* si están todos ocupados */ 
		
		/* si se puede añadir un vagón, no está lleno */
		return vagones.size()+1< numMáxVagones;
	}
	
	public boolean reservarAsiento(final String identificadorOcupante) {
		if (isLleno()) return false;

		Vagón menosLleno= vagones.get(0);

		for (Vagón vagón : vagones)
			if (vagón.getAsientosLibres() < menosLleno.getAsientosLibres())
				menosLleno= vagón;

		if (menosLleno.reservarAsiento(identificadorOcupante)) return true;

		if (vagones.size()+1>= numMáxVagones) return false;

		vagones.add(new Vagón(vagones.size(), numFilasVagón));
		return vagones.get(vagones.size()).reservarAsiento(identificadorOcupante);
	}
	
}
