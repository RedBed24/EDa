
/*********************************************************************
* @name Personaje
* 
* @authors DJS - B2 - 03
* 
* @description Contiene la información acerca de los personajes, tanto sus características como los métodos que los manejan.
* 			   Los atributos son tipo, subtipo, nombre, género y referencias al personaje en la saga de establecimiento.
* 			   Los métodos son el constructor, toString y algunos getters.
***********************************************************************/
package B2_03_grafos;

public class Personaje {
	
	public enum Type { PLA, PER, GRO, THIN; }; // Enumerado con los distintos tipos de personajes
	public enum SubType { PLA, MEN, ELVES, DWARFS, HOBBIT, ANIMAL, MIXED, ENTS, AINUR, ORCS, THING; }; // Enumerado con las distintas razas de los personajes
	public enum Gender { MALE, FEMALE, UNKNOWN; }; // Enumerado con los diferentes géneros de los personajes
	
	final private Type type; 		// Tipo de personaje
	final private String subtype;	// Subtipo/raza del personaje
	final private String name;		// Nombre del personaje
	final private Gender gender;	// Género del personaje
	final private int FreqSum;		// Total de referencias al personaje en la saga
	
	/***********************************
	 * @name Constructor de Personaje
	 * 
	 * @authors DJS - B2 - 03
	 * 
	 * @description Inicializa los atributos de una instancia Personaje
	 * 
	 * @param type--> Tipo de personaje
	 * @param subtype --> Subtipo/raza del personaje
	 * @param name --> Nombre del personaje
	 * @param gender --> Género del personaje
	 * @param FreqSum --> Total de referencias al personaje en la saga
	 ***********************************/
	
	public Personaje(Type type, String subtype, String name, Gender gender, int freqSum) {
		super();
		this.type = type;
		this.subtype = subtype;
		this.name = name;
		this.gender = gender;
		FreqSum = freqSum;
	}
	
	public Type getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}

	public int getFreqSum() {
		return FreqSum;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "Personaje [type=" + type + ", subtype=" + subtype + ", name=" + name + ", gender=" + gender + "]";
	}
	
}
