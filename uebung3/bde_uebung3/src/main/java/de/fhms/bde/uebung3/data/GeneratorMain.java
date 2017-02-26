package de.fhms.bde.uebung3.data;

import java.io.FileWriter;
import java.io.IOException;

public class GeneratorMain {

	private static enum GENDER{MALE, FEMALE}
	private static final int SIZE = 1000;
	
	public static void main(String[] args) {
		
		String [] value  = new String[3];
		for(int i = 0; i <= SIZE; i++){
			boolean gender = getRandomBoolean();
			//GENDER
			if(gender){
				value[0] = GENDER.MALE.toString();
			} else {
				value[0] = GENDER.FEMALE.toString();
			}
			
			//AGE
			int age = 10 + (int)Math.round(Math.random() * 100);
			value[1] = String.valueOf(age);
			
			//HEIGHT
			int randomHeight = (int)Math.round(Math.random() * 50);
			if(gender){
				value[2] = String.valueOf(160 + randomHeight);
			} else {
				value[2] = String.valueOf(140 + randomHeight);
			}
			
			exportToFile(value, "./src/main/resources/person.txt");
		}

	}

	private static void exportToFile(String [] value, String file){
		try{	
		    FileWriter writer = new FileWriter(file, true);
		    
		    StringBuilder builder = new StringBuilder();
		    for(String s : value) {
		        builder.append(s + "\t");
		    }
		    builder.append(System.getProperty("line.separator"));
		    writer.append(builder.toString());
		    writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	private static boolean getRandomBoolean() {
       return Math.random() < 0.5;
	}
}
