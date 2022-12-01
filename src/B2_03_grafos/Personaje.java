package B2_03_grafos;

public class Personaje {
	public enum Type { PLA, PER, GRO, THIN; };
	public enum SubType { PLA, MEN, ELVES, DWARFS, HOBBIT, ANIMAL, MIXED, ENTS, AINUR, ORCS, THING; };
	public enum Gender { MALE, FEMALE, UKNOWN; };
	
	final private Type type;
	final private String subtype;
	final private String name;
	final private Gender gender;
	final private int FreqSum;
	
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

	@Override
	public boolean equals(Object obj) {
		return name.equals(((Personaje)obj).getName()) && type== ((Personaje)obj).getType();
	}

	@Override
	public String toString() {
		return "Personaje [type=" + type + ", subtype=" + subtype + ", name=" + name + ", gender=" + gender + "]";
	}
	
}