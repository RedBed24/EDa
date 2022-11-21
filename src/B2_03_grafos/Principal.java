package B2_03_grafos;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import B2_03_grafos.Personaje.*;

public class Principal {

	public static List<Personaje> leerFichero(final Scanner fichero) throws IllegalArgumentException, NumberFormatException {
		List<Personaje> devolver= new LinkedList<Personaje>();
		
		fichero.nextLine();
		
		while (fichero.hasNext()) {
			String[] data= fichero.nextLine().split(";");
			/* 
			 * data[0] id
			 * data[1] type
			 * data[2] subtype
			 * data[3] name
			 * data[4] gender
			 * data[5] FreqSum
			 */
			
			final String id= data[0];
			
			final Type type;
			switch (data[1]) {
			case "PLA":  type= Type.PLA;  break;
			case "PER":  type= Type.PER;  break;
			case "GRO":  type= Type.GRO;  break;
			case "THIN": type= Type.THIN; break;
			default: throw new IllegalArgumentException("Unexpected Type value: " + data[1]);
			}
			
			final String subtype= data[2];
			
			final String name= data[3];
			
			final Gender gender;
			switch (data[4]) {
			case "male":   gender= Gender.MALE;   break;
			case "female": gender= Gender.FEMALE; break;
			default:       gender= Gender.UKNOWN; break;
			}
			
			final int freqsum= Integer.parseInt(data[5]);
			
			devolver.add(new Personaje(id, type, subtype, name, gender, freqsum));
		}
		
		return devolver;
	}
	
	public static void main(String[] args) {
		

	}

}
