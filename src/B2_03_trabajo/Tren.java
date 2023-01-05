package B2_03_trabajo;

import java.util.ArrayList;
import java.util.List;

/*********************************************************************
* Contiene la información acerca del tren, tanto sus características como los métodos que los manejan.
* Los atributos son una lista de vagones, el número máximo de vagones que puede tener el tren y el número de filas que contiene cada vagón.
* Los métodos son el constructor, los getters (getNumVagones, getNumMáxVagones, getFilasVagones, getColumnasVagones, getIdentificadorAsientoVagón),
* operaciones relacionadas con parámetros generales del tren (capacidadTren, capacidadVagón, libresActualmente, libresTren, ocupadosTren, isLleno,
* isVacio), operaciones relacionadas con identificadores de asientos (identificadorEnUso,reservarAsiento, liberarAsiento, setIdentificadorAsientoVagón,
* comprobarIdentificador) y métodos que muestran salidas (mostrarOcupantesTren y toString). 
***********************************************************************/

public class Tren {
	final private List<Vagón> vagones; // Lista de vagones 
	final private int numMáxVagones; // Número máximo de vagones
	final private int numFilasVagón; // Número de filas por vagón

	/**
	 * Crea un tren especificando su número máximo de vagones y la cantidad de filas por vagón. Inicialmente,
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
	 * Devuelve el número de vagones que tiene el tren en un momento determinado
	 * @return Número de vagones actuales
	 */
	public int getNumVagones() {
		return vagones.size();
	}
	
	/**
	 * Devuelve el número máximo de vagones que puede tener el tren
	 * @return Número máximo de vagones
	 */
	public int getNumMáxVagones() {
		return numMáxVagones;
	}
	
	/**
	 * Devuelve el número de filas por vagón
	 * @return Número de filas por vagón
	 */
	public int getFilasVagones() {
		return numFilasVagón;
	}
	
	/**
	 * Devuelve el número constante de columnas para los vagones
	 * @return Número de columnas para todo vagón
	 */
	public int getColumnasVagones() {
		return vagones.get(0).getColumnas();
	}
	
	/**
	 * Devuelve el identificador del asiento de un vagón en una fila y columna determinadas
	 * @param numVagón -> Número de vagón donde se encuentra el asiento
	 * @param fila -> Fila dentro del vagón indicado donde se encontrará el asiento
	 * @param posición -> Columna dentro de la fila del vagón indicado donde el asiento se encuentra (VentanaIzq, PasilloIzq, PasilloDer, VentanaDer)
	 * @return Identificador del asiento del vagón "numVagón", situado en la fila "fila" y columna "columna"
	 * @throws IllegalArgumentException -> Lanzada si el número de vagón es menor que 1 o mayor que el número de vagones actuales, 
	 * si la fila es menor que 1 o mayor que las filas de cada vagón y si la posición es distinta a VentanaIzq, PasilloIzq, PasilloDer o VentanaDer
	 */
	public String getIdentificadorAsientoVagón(int numVagón, int fila, String posición) {
		int columna; // Para identificar la posición indicada en la fila
		numVagón--; fila--; // Se resta 1 para manejar correctamente el array
		
		// Comprobación de número de vagón indicado por parámetro
		if(numVagón < 0 || numVagón >= getNumVagones()) 
			throw new IllegalArgumentException("\nNúmero de vagón excedido - Rango de vagones [1,"+getNumVagones()+"]\n");
		
		// Comprobación de fila indicada por parámetro
		if(fila < 0 || fila >= getFilasVagones()) 
			throw new IllegalArgumentException("\nNúmero de fila excedida - Rango de filas [1,"+ getFilasVagones()+"]\n");
		
		// Comprobación de posición indicada por parámetro
		switch(posición.toLowerCase()) { 
			// No importa si el usuario usa mayúsculas o minúsculas para indicar la posición aunque se le mostrará como la indique
			case "ventanaizq": columna = 0; break;
			case "pasilloizq": columna = 1; break;
			case "pasilloder": columna = 2; break;
			case "ventanader": columna = 3; break;
			default: throw new IllegalArgumentException("\nPosición no reconocida\n");
		}
		
		// Obtención y devolución del identificador de asiento especificado mediante número de vagón, fila y posición/columna
		return vagones.get(numVagón).getIdentificadorAsiento(fila, columna);
	}
	
	/**
	 * Modifica los identificadores de los asientos de cada vagón que estén reservados por la misma persona
	 * @param antiguoOcupante -> Identificador de quien ocupa el asiento, necesario para encontrar el asiento a modificar
	 * @param nuevoOcupante -> Nuevo identificador del asiento encontrado
	 * @throws IllegalArgumentException -> Si se pretende modificar un identificador que no existía en el tren
	 */
	public void setIdentificadorAsientoVagón(String antiguoOcupante, String nuevoOcupante) {
		// Comprobación de identificadores correctos
		comprobarIdentificador(antiguoOcupante);
		comprobarIdentificador(nuevoOcupante);
		// Comprobación de que el ocupante a modificar existe
		if(!identificadorEnUso(antiguoOcupante)) 
			throw new IllegalArgumentException("\nNo había ningún ocupante en el tren con el identificador antiguo indicado\n");
		// Modificación de identificadores
		for (int v = 0; v < vagones.size(); v++)
			vagones.get(v).setIdentificadorAsiento(antiguoOcupante, nuevoOcupante);
	}
	
	/**
	 * Devuelve la capacidad de cada vagón. El número de asientos totales del vagón es el número de filas por el de columnas
	 * @return Asientos totales de cada vagón. Se da por hecho que todos los vagones tienen las mismas características dentro del tren.
	 */
	public int capacidadVagón() { 
		return vagones.get(0).getAsientosTotales();
	}
	
	/**
	 * Devuelve la capacidad del tren. El número de asientos totales del tren es la capacidad de un vagón por el número máximo de vagones
	 * @return Capacidad total del tren, independientemente de los vagones actuales
	 */
	public int capacidadTren() { 
		return capacidadVagón() * numMáxVagones;
	}
	
	/**
	 * Devuelve el número de asientos que hay libres en el tren en un momento determinado.
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
	 * Devuelve el número de asientos que hay libres en el tren, teniendo en cuenta el número máximo de vagones.
	 * El número de asientos libres del tren es la diferencia entre la capacidad del tren y los asientos que haya ocupados
	 * @return Número de asientos libres 
	 */
	public int libresTren() {
		return capacidadTren() - ocupadosTren();
	}

	/**
	 * Devuelve el número de asientos que están ocupados en el tren. Este es diferente a los métodos anteriores porque
	 * los vagones se añaden según vayan llenándose estos, es decir, cuando se ocupen en su totalidad y sean necesarios más asientos.
	 * Estrictamente, este método sería el opuesto a libresActualmente() porque indica los asientos ocupados en un momento determinado,
	 * pero para este TAD, es irrelevante. El número de asientos ocupados del tren es la suma de asientos ocupados de cada vagón del tren
	 * @return Número de asientos ocupados en el tren
	 */
	public int ocupadosTren() {
		int ocupados = 0;
		for (Vagón vagón : vagones)
			ocupados += vagón.getAsientosOcupados();
		return ocupados;
	}
	
	/**
	 * Devuelve si el tren está totalmente lleno. Esto es, si ningún asiento está libre y no se pueden añadir más vagones al tren
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
	 * Devuelve si el tren está totalmente vacío. Esto es, si todo asiento está libre
	 * @return true si el tren está completamente vacío, false en otro caso 
	 */
	public boolean isVacio() {
		for (Vagón vagón : vagones)
			if (!vagón.isVacio()) return false;
		return true;
	}

	/**
	 * Permite saber si el tren contiene algún asiento con el identificador dado. Se comprueba tanto el identificador
	 * literal como el identificador con un 1 delante. Esto es debido a que si se desean reservar varios asientos, necesitamos una manera
	 * de distinguir los billetes diferentes y se exige que sea con el nombre del ocupante y el número de billete delante. Si existe el
	 * identificador compuesto por el nombre del ocupante y el primer billete, significa que dicho nombre está contenido en algún asiento.
	 * Por último, nunca ocurrirá que se libere únicamente el asiento con el primer billete y el resto se queden porque cuando se liberan
	 * los asientos, se liberan todos los que corresponden a la misma persona. Por tanto, se entiende que es suficiente con comprobar el primero
	 * @param identificadorOcupante -> Identificador a buscar en el tren
	 * @return true si el tren contiene el asiento con tal identificador, false en otro caso 
	 */
	public boolean identificadorEnUso(final String identificadorOcupante) {
		// Comprobación de identificador correcto
		comprobarIdentificador(identificadorOcupante);
		// Comprobación de identificador literal
		for (Vagón vagón : vagones)
			if (vagón.identificadorEnUso(identificadorOcupante)) return true;
		// Comprobación de identificador compuesto por nombre del ocupante y el primer billete
		for (int v = 0; v < vagones.size(); v++)
			if(vagones.get(v).identificadorEnUso(identificadorOcupante+1)) return true;
		return false;
	}

	/**
	 * Realiza la reserva de uno o varios asientos
	 * @param identificadorReal -> Identificador real del ocupante que realiza la reserva
	 * @param numReservas --> Cantidad de reservas que realizarán a nombre del identificador
	 * @return Mensaje de confirmación de reservas exitosas
	 * @throws IllegalArgumentException -> Si el identificador es igual a null o comienza por null, en caso de que el número de reservas sea
	 * o menor que 1, o mayor que la capacidad del tren, o mayor que el número de asientos disponibles del tren, si el tren está completamente lleno
	 * o si no se ha podido realizar la reserva debido a fallo en otros métodos, 
	 */
	public String reservarAsiento(String identificadorReal, int numReservas) {
		int num = 0; // Para ayudar a la asignación de identificadores a los asientos
		String identificadorOcupante; // Identificador compuesto por el nombre del identificador y el número de la reserva
		String mensaje = ""; // Mensaje de confirmación de reservas
		
		// Comprobación de identificador correcto
		comprobarIdentificador(identificadorReal);
		
		// Comprobación de número de reservas válido
		if(numReservas < 1 || numReservas > capacidadTren() || numReservas > libresTren()) 
			throw new IllegalArgumentException("\nNo se puede reservar la cantidad de asientos indicada. "
			+ "\nNo introduzca un número negativo, ni una cantidad de asientos superior a la capacidad del tren, ni una cantidad que supere el número de asientos que hay disponibles en el tren. "
			+ "\nVaya a la consulta 7 para más información\n");
		
		// Mientras haya reservas...
		while(numReservas > 0) {	
			num++;
			identificadorOcupante = identificadorReal + String.valueOf(num);

			// Se obtiene el vagón menos lleno...
			Vagón menosLleno = vagones.get(0); 
			for (Vagón vagón : vagones)
				if (menosLleno.getAsientosLibres() < vagón.getAsientosLibres())
					menosLleno = vagón;
		
			// Y se realiza la reserva del asiento...
			if (menosLleno.reservarAsiento(identificadorOcupante)) // ...en una situación normal.
				mensaje = "<Operación realizada correctamente>"; 
			else if(vagones.size() < numMáxVagones) { // ...cuando hay que añadir más vagones.
				vagones.add(new Vagón(vagones.size()+1, numFilasVagón));
				vagones.get(vagones.size()-1).reservarAsiento(identificadorOcupante);
				mensaje = "<Operación realizada correctamente. Se añadió un vagón para completar la operación>";
			}
			else throw new IllegalArgumentException("\nError al realizar la reserva\n"); // Esta comprobación realmente se usa al principio
			--numReservas; // Se ha reservado un asiento
		}
		return mensaje;
	}
	
	/**
	 * Se liberan todos los asientos que correspondan a la misma persona
	 * @param identificadorOcupante -> Identificador del ocupante del que se eliminarán todas sus reservas
	 * @return Mensaje de confirmación de eliminación de reservas
	 * @throws IllegalArgumentException -> Si el ocupante correspondiente no tenía ninguna reserva en el tren
	 */
	public String liberarAsiento(final String identificadorOcupante) { 
		// Comprobación de identificador correcto
		comprobarIdentificador(identificadorOcupante);
		// Comprobación de que el identificador indicado existe
		if(!identificadorEnUso(identificadorOcupante)) throw new IllegalArgumentException("\nNo había ningún ocupante en el tren con el identificador antiguo indicado\n");
		// Recorrido de todos los vagones con el identificador literal
		for (Vagón vagón : vagones)
			if (!vagón.liberarAsiento(identificadorOcupante)) throw new IllegalArgumentException("Error al liberar los asientos de \"" + identificadorOcupante+"\"");
		// Recorrido de todos los vagones con el identificador compuesto por el nombre de la persona más el número de billete
		for(int i = 1; identificadorEnUso(identificadorOcupante+i); i++)
			liberarAsiento(identificadorOcupante+i); // Llamada recursiva
		
		return "Se han eliminado las reservas de \"" + identificadorOcupante + "\" correctamente.";
	}
	
	/***********************************
	 * Comprueba un identificador al usuario
	 * @param identificador --> El que será comprobado
	 * @throws IllegalArgumentException -> Lanzada si se indica un identificador que termina en número o null
	 * @return El propio identificador
	 ***********************************/
	public String comprobarIdentificador(final String identificador) {
		// Error si el identificador termina en número
		if(Character.isDigit(identificador.charAt(identificador.length()-1))) 
			throw new IllegalArgumentException("\nNo introduzca un identificador con algún número al final\n");
		// Error si el identificador es null
		if(identificador.equals("null") || identificador.startsWith("null"))
			throw new IllegalArgumentException("\nEl identificador null es inválido\n");
		return identificador;
	}
	
	/**
	 * Devuelve una cadena con la disposición del tren, es decir, cada uno de sus vagones y, además, con
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
	 * Devuelve una cadena con la disposición del tren, es decir, cada uno de sus vagones y, además, con
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
