package de.fhms.bde.uebung5.data;

public class GeneratorMain {

	private static enum GENDER {
		MALE, FEMALE
	}


	public static String[] generateEntry() {

		String[] value = new String[3];

		boolean gender = getRandomBoolean();
		// GENDER
		if (gender) {
			value[0] = GENDER.MALE.toString();
		} else {
			value[0] = GENDER.FEMALE.toString();
		}

		// AGE
		int age = 10 + (int) Math.round(Math.random() * 100);
		value[1] = String.valueOf(age);

		// HEIGHT
		int randomHeight = (int) Math.round(Math.random() * 50);
		if (gender) {
			value[2] = String.valueOf(160 + randomHeight);
		} else {
			value[2] = String.valueOf(140 + randomHeight);
		}

		return value;
	}

	private static boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}
}
