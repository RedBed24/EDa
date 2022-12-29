package B2_03_trabajo;

import java.util.ArrayList;
import java.util.List;

/*********************************************************************
* @name Tren
* @authors DJS - B2 - 03
* @description Contiene la información acerca del tren, tanto sus características como los métodos que los manejan.
* Los atributos son una lista de vagones, el número máximo de vagones que puede tener el tren y el número de filas que contiene cada vagón.
* Los métodos son el constructor, los getters (getNumVagones, getNumMáxVagones, getFilasVagones, getColumnasVagones, getIdentificadorAsientoVagón),
* operaciones relacionadas con parámetros generales del tren (capacidadTren, capacidadVagón, libresActualmente, libresTren, ocupadosTren, isLleno,
* isVacio), operaciones relacionadas con identificadores de asientos (identificadorEnUso,reservarAsiento, liberarAsiento, setIdentificadorAsientoVagón)
* y métodos que muestran salidas (mostrarOcupantesTren y toString). 
***********************************************************************/

public class Tren {
	final private List<Vagón> vagones; // Lista de vagones 
	final private int numMáxVagones; // Número máximo de vagones
	final private int numFilasVagón; // Número de filas por vagón

	/**
	 * @name Constructor de Tren
	 * @description Crea un tren especificando su número máximo de vagones y la cantidad de filas por vagón. Inicialmente,
	 * un tren dispondrá de un vagón.
	 * @param numMáxVagones -> Número máximo de vagones que puede tener el tren - Rango: [1, ?)
	 * @param numFilasVagón ->  Número de filas que contiene cada vagón - Rango: [1, ?)
	 * @throws IllegalArgumentException -> Si el número máximo de vagones o la cantidad de filas por vagón es igual o menor que cero
	 */
	public Tren(final int numMáxVagones, final int numFilasVagón) {
		super();
		this.numMáxVagones = numMáxVagones;
		this.numFilasVagón = numFilasVagón;
		if (this.numMáxVagones <= 0 || this.numFilasVagón <= 0) 
			throw new IllegalArgumentException("\nEl tren debe tener como mínimo un vagón y los vagones al menos una fila\n");
		
		this.vagones = new ArrayList<Vagón>(numMáxVagones);
		vagones.add(new Vagón(1, numFilasVagón)); // Un tren tiene como mínimo un vagón en su creación
	
	}
	
	/**
	 * @name getNumVagones
	 * @description Devuelve el número de vagones que tiene el tren en un momento determinado
	 * @return Número de vagones actuales
	 */
	public int getNumVagones() {
		return vagones.size();
	}
	
	/**
	 * @name getNumMáxVagones
	 * @description Devuelve el número máximo de vagones que puede tener el tren
	 * @return Número máximo de vagones
	 */
	public int getNumMáxVagones() {
		return numMáxVagones;
	}
	
	/**
	 * @name getFilasVagones
	 * @description Devuelve el número de filas por vagón
	 * @return Número de filas por vagón
	 */
	public int getFilasVagones() {
		return numFilasVagón;
	}
	
	/**
	 * @name getColumnasVagones
	 * @description Devuelve el número constante de columnas para los vagones
	 * @return Número de columnas para todo vagón
	 */
	public int getColumnasVagones() {
		return vagones.get(0).getColumnas();
	}
	
	/**
	 * @name getIdentificadorAsientoVagón
	 * @description Devuelve el identificador del asiento de un vagón en una fila y columna determinadas
	 * @param numVagón -> Número de vagón donde se encuentra el asiento
	 * @param fila -> Fila dentro del vagón indicado donde se encontrará el asiento
	 * @param columna -> Columna dentro del vagón indicado donde encontraremos el asiento
	 * @return Identificador del asiento del vagón "numVagón", situado en la fila "fila" y columna "columna"
	 */
	public String getIdentificadorAsientoVagón(int numVagón, int fila, int columna) {
		return vagones.get(numVagón).getIdentificadorAsiento(fila, columna);
	}
	
	/**
	 * @name setIdentificadorAsientoVagón
	 * @description Modifica los identificadores de los asientos de cada vagón que estén reservados por la misma persona
	 * @param antiguoOcupante -> Identificador de quien ocupa el asiento, necesario para encontrar el asiento a modificar
	 * @param nuevoOcupante -> Nuevo identificador del asiento encontrado
	 */
	public void setIdentificadorAsientoVagón(String antiguoOcupante, String nuevoOcupante) {
		for (int v = 0; v < vagones.size(); v++)
			vagones.get(v).setIdentificadorAsiento(antiguoOcupante, nuevoOcupante);
	}
	
	/**
	 * @name capacidadVagón
	 * @description Devuelve la capacidad de cada vagón. El número de asientos totales del vagón es el número de filas por el de columnas
	 * @return Asientos totales de cada vagón. Se da por hecho que todos los vagones tienen las mismas características dentro del tren.
	 */
	public int capacidadVagón() { 
		return vagones.get(0).getAsientosTotales();
	}
	
	/**
	 * @name capacidadTren
	 * @description Devuelve la capacidad del tren. El número de asientos totales del tren es la capacidad de un vagón por el número máximo de vagones
	 * @return Capacidad total del tren, independientemente de los vagones actuales
	 */
	public int capacidadTren() { 
		return capacidadVagón() * numMáxVagones;
	}
	
	/**
	 * @name libresActualmente
	 * @description Devuelve el número de asientos que hay libres en el tren en un momento determinado.
	 * El número de asientos libres del tren en un momento determinado es igual a la suma de asientos libres de cada vagón (de los actuales).
	 * @return Asientos libres actualmente, sin tener en cuenta el número máximo de vagones
	 */
	public int libresActualmente() {
		int libres = 0;
		for(Vagón vagón: vagones)
			libres += vagón.getAsientosLibres();
		return libres;
	}
	
	/**
	 * @name libresTren
	 * @description Devuelve el número de asientos que hay libres en el tren, teniendo en cuenta el número máximo de vagones.
	 * El número de asientos libres del tren es la diferencia entre la capacidad del tren y los asientos que haya ocupados en el mismo
	 * @return Número de asientos libres 
	 */
	public int libresTren() {
		return capacidadTren() - ocupadosTren();
	}

	/**
	 * @name ocupadosTren
	 * @description Devuelve el número de asientos que están ocupados en el tren. Este es diferente a los métodos anteriores porque
	 * los vagones se añaden según vayan llenándose estos, es decir, cuando se ocupen en su totalidad y sean necesarios más asientos.
	 * Estrictamente, este método sería el opuesto a libresActualmente() porque indica los asientos ocupados en un momento determinado,
	 * pero para este TAD, es irrelavante. El número de asientos ocupados del tren es la suma de asientos ocupados de cada vagón del tren
	 * @return Número de asientos ocupados en el tren
	 */
	public int ocupadosTren() {
		int ocupados = 0;
		for (Vagón vagón : vagones)
			ocupados += vagón.getAsientosOcupados();
		return ocupados;
	}
	
	/**
	 * @name isLleno
	 * @description Devuelve si el tren está totalmente lleno. Esto es, si ningún asiento está libre y no se pueden añadir más vagones al tren
	 * @return true si el tren está completamente lleno, false en otro caso 
	 */
	public boolean isLleno() { 
		// Ningún asiento está libre
		for (Vagón vagón : vagones)
			if (!vagón.isLleno()) return false;
		
		// No se pueden añadir más vagones al tren
		return vagones.size() >= numMáxVagones;
	}
	
	/**
	 * @name isVacio
	 * @description Devuelve si el tren está totalmente vacío. Esto es, si todo asiento está libre
	 * @return true si el tren está completamente vacío, false en otro caso 
	 */
	public boolean isVacio() {
		for (Vagón vagón : vagones)
			if (!vagón.isVacio()) return false;
		return true;
	}

	/**
	 * @name identificadorEnUso
	 * @description Permite saber si el tren contiene algún asiento con el identificador dado. Se comprueba tanto el identificador
	 * literal como el identificador con un 1 delante. Esto es debido a que si se desean reservar varios asientos, necesitamos una manera
	 * de distinguir los billetes diferentes y se exige que sea con el nombre del ocupante y el número de billete delante. Si existe el
	 * identificador compuesto por el nombre del ocupante y el primer billete, significa que dicho nombre está contenido en algún asiento.
	 * Por último, nunca ocurrirá que se libere únicamente el asiento con el primer billete y el resto se queden porque cuando se liberan
	 * los asientos, se liberan todos los que corresponden a la misma persona. Por tanto, se entiende que es suficiente con comprobar el primero
	 * @param identificadorOcupante -> Identificador a buscar en el tren
	 * @return true si el tren contiene el asiento con tal identificador, false en otro caso 
	 */
	public boolean identificadorEnUso(final String identificadorOcupante) {
		// Comprobación de identificador literal
		for (Vagón vagón : vagones)
			if (vagón.identificadorEnUso(identificadorOcupante)) return true;
		// Comprobación de identificador compuesto por nombre del ocupante y el primer billete
		for (int v = 0; v < vagones.size(); v++)
			if(vagones.get(v).identificadorEnUso(identificadorOcupante+1)) return true;
		return false;
	}

	/**
	 * @name reservarAsiento
	 * @description Realiza la reserva de un asiento
	 * @param identificadorOcupante -> Identificador del ocupante que realiza la reserva
	 * @return Mensaje de confirmación de reserva exitosa
	 * @throws IllegalArgumentException -> Si el identificador es igual a null o comienza por null, si el tren está completamente lleno
	 * o si no se ha podido realizar la reserva debido a fallo en otros métodos
	 */
	public String reservarAsiento(final String identificadorOcupante) {
		// Comprobación de identificador inválido
		if(identificadorOcupante.equals("null") || identificadorOcupante.startsWith("null")) 
			throw new IllegalArgumentException("\nEl identificador null es inválido\n");

		String mensaje = "";
		// Si el tren está lleno...
		if(libresTren() == 0) {
			if(vagones.size() >= numMáxVagones) // ...y no se pueden añadir más vagones, regresa un valor nulo.
				throw new IllegalArgumentException("\nError al realizar la reserva. El tren está completamente lleno\n");
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
			else throw new IllegalArgumentException("\nError al realizar la reserva\n");
		}
		return mensaje;
	}
	
	/**
	 * @name liberarAsiento
	 * @description Se liberan todos los asientos que correspondan a la misma persona
	 * @param identificadorOcupante -> Identificador del ocupante del que se eliminarán todas sus reservas
	 * @return Mensaje de confirmación de eliminación de reservas
	 * @throws IllegalArgumentException -> Si el ocupante correspondiente no tenía ninguna reserva en el tren
	 */
	public String liberarAsiento(final String identificadorOcupante) { 
		//TODO nunca se lanza esta excepción. Mañana meto el else de liberarAsiento de la clase Principal aquí.
		for (Vagón vagón : vagones)
			if (!vagón.liberarAsiento(identificadorOcupante)) throw new IllegalArgumentException("Error al liberar los asientos de \"" + identificadorOcupante+"\"");
		
		for(int i = 1; identificadorEnUso(identificadorOcupante+i); i++)
			liberarAsiento(identificadorOcupante+i); // Llamada recursiva para liberar todo identificador compuesto por nombre de ocupante más número de billete
		
		return "Se han eliminado las reservas de \"" + identificadorOcupante + "\" correctamente.";
	}
	
	/**
	 * @name mostrarOcupantesTren
	 * @description Devuelve una cadena con la disposición del tren, es decir, cada uno de sus vagones y, además, con
	 * los identificadores de todos los ocupantes del mismo. En el fondo, es un toString() secundario o mejorado
	 * @return Cadena con la información del tren, su disposición y los identificadores de todos sus ocupantes
	 */
	public String mostrarOcupantesTren() {
		String devolver="-----------------------------------<TREN>-----------------------------------";
		for (Vagón vagón : vagones)
			devolver+= "\n"+vagón.mostrarOcupantesVagón();
		return devolver;
	}
	
	/**
	 * @name toString
	 * @description Devuelve una cadena con la disposición del tren, es decir, cada uno de sus vagones y, además, con
	 * la ocupación de todos los asientos. Se recomienda ver el toString() de Vagón para entender por qué consideramos este método
	 * como método toString() principal
	 * @return Cadena con la información del tren, su disposición y la ocupación de todos sus asientos
	 */
	public String toString() {
		String devolver="----------------------<TREN>----------------------";
		for (Vagón vagón : vagones)
			devolver+= "\n"+vagón;
		return devolver;
	}
	


	
}
