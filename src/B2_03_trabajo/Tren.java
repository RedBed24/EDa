package B2_03_trabajo;

import java.util.ArrayList;
import java.util.List;

/*********************************************************************
* @name Tren
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información acerca del tren, tanto sus características como los métodos que los manejan.
* 			   Sin acabar
***********************************************************************/

public class Tren {
	final private List<Vagón> vagones;
	final private int numMáxVagones;
	final private int numFilasVagón;

	/**
	 * Permite crear un nuevo tren con un número máximo de vagones especificado y un número de filas por vagón también especificado.
	 * @param numMáxVagones --> Número máximo de vagones que puede tener el tren. rango: [1, ?)
	 * @param numFilasVagón --> Número de filas que contiene cada vagón. rango: [1, ?)
	 * 
	 * @throws IllegalArgumentException --> Si el número de vagones especificado es igual o menor que 0
	 * @throws IllegalArgumentException --> Si el número de filas especificado es igual o menor que 0
	 */
	public Tren(final int numMáxVagones, final int numFilasVagón) {
		super();
		this.numMáxVagones = numMáxVagones;
		this.numFilasVagón = numFilasVagón;
		if (this.numMáxVagones <= 0 || this.numFilasVagón <= 0) 
			throw new IllegalArgumentException("\nEl tren debe tener como mínimo un vagón y los vagones al menos una fila\n");
		
		this.vagones = new ArrayList<Vagón>(numMáxVagones);
		vagones.add(new Vagón(1, numFilasVagón));
	
	}
	
	public int getNumVagones() {
		return vagones.size();
	}
	
	public int getNumMáxVagones() {
		return numMáxVagones;
	}
	
	public int getFilasVagones() {
		return vagones.get(0).getFilas();
	}
	
	public int getColumnasVagones() {
		return vagones.get(0).getColumnas();
	}
	
	public String getOcupanteAsientoVagón(int numVagón, int fila, int columna) {
		return vagones.get(numVagón).getIDAsiento(fila, columna);
	}
	
	public void setOcupanteAsientoVagón(String antiguoOcupante, String nuevoOcupante) {
		for (int v = 0; v < vagones.size(); v++)
			vagones.get(v).setIDAsiento(antiguoOcupante, nuevoOcupante);
	}
	
	public int capacidadVagón() { 
		// El número de asientos totales del vagón es el número de filas por el de columnas.
		return vagones.get(0).getAsientosTotales();
	}
	
	public int capacidadTren() { 
		// El número de asientos totales del tren es igual a la capacidad de un vagón por el número de vagones
		return capacidadVagón() * numMáxVagones;
	}
	
	public int libresActualmente() {
		// El número de asientos libres del tren es igual a la suma de asientos libres de cada vagón del tren.
		int libres = 0;
		for(Vagón vagón: vagones)
			libres += vagón.getAsientosLibres();
		return libres;
	}
	
	public int libresTren() {
		return capacidadTren() - ocupadosTren();
		
	}

	public int ocupadosTren() { 
		// El número de asientos ocupados del tren es igual a la suma de asientos ocupados de cada vagón del tren.
		int ocupados = 0;
		for (Vagón vagón : vagones)
			ocupados += vagón.getAsientosOcupados();
		return ocupados;
	}
	
	public boolean isLleno() { 
		// Ningún asiento está libre
		for (Vagón vagón : vagones)
			if (!vagón.isLleno()) return false;
		
		// No se pueden añadir más vagones al tren
		return vagones.size() >= numMáxVagones;
	}
	
	public boolean isVacio() {
		for (Vagón vagón : vagones)
			if (!vagón.isVacio()) return false;
		
		return true;
	}

	/**
	 * Permite saber si el tren contiene algún asiento con el identificador dado.
	 * @param identificadorOcupante --> Identificador a buscar en el tren.
	 * @return true: Si el tren contiene un asiento con este identificiador. false: Si el tren no contiene un asiento con el identificador.
	 */
	public boolean identificadorEnUso(final String identificadorOcupante) { //TODO: SE ESTÁ USANDO EL IDENTIFICADOR X?
		for (Vagón vagón : vagones)
			if (vagón.identificadorEnUso(identificadorOcupante)) return true;
		for (int v = 0; v < vagones.size(); v++)
			if(vagones.get(v).identificadorEnUso(identificadorOcupante+1)) return true;
		return false;
	}

	public String reservarAsiento(final String identificadorOcupante) {
		if(identificadorOcupante.equals("null") || identificadorOcupante.startsWith("null")) 
			throw new IllegalArgumentException("El identificador null es inválido");

		String mensaje = "";
		// Si el tren está lleno...
		if(libresTren() == 0) {
			if(vagones.size() >= numMáxVagones) // ...y no se pueden añadir más vagones, regresa un valor nulo.
				throw new IllegalArgumentException("Error al realizar la reserva. El tren está completamente lleno");
			else {	// ...y se pueden añadir más vagones, se añade un vagón más y se realiza la reserva
				vagones.add(new Vagón(vagones.size()+1, numFilasVagón));	
				vagones.get(vagones.size()-1).reservarAsiento(identificadorOcupante);
				mensaje = "<Reserva realizada correctamente>";
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
			if (menosLleno.reservarAsiento(identificadorOcupante)) mensaje = "<Reserva realizada correctamente>";
			else throw new IllegalArgumentException("Error al realizar la reserva");
		}
		return mensaje;
	}
	
	public String liberarAsiento(final String identificadorOcupante) {
		for (Vagón vagón : vagones)
			if (!vagón.liberarAsiento(identificadorOcupante)) throw new IllegalArgumentException("Error al liberar los asientos de \"" + identificadorOcupante+"\"");
		
		for(int i = 1; identificadorEnUso(identificadorOcupante+i);i++)
			liberarAsiento(identificadorOcupante+i);
		
		return "Se han eliminado las reservas de \"" + identificadorOcupante + "\" correctamente.";
	}
	
	public String mostrarOcupantesTren() {
		String devolver="-----------------------------------<TREN>-----------------------------------";
		for (Vagón vagón : vagones)
			devolver+= "\n"+vagón.mostrarOcupantesVagón();
		return devolver;
	}
	
	public String toString() {
		String devolver="----------------------<TREN>----------------------";
		for (Vagón vagón : vagones)
			devolver+= "\n"+vagón;
		return devolver;
	}
	


	
}
