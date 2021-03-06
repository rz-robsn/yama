package com.yama.utilities;

public class MorseStringConverter {

	public static final char DIT = '.' ;
	public static final char DAH = '-' ;
	public static final char SPACE = ' ' ;
	
	private static char[] letters = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', ' ' };
	private static String[] morseCode = new String[] { ".-", "-...", "-.-.", "-..",
			".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--",
			"-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-",
			".--", "-..-", "-.--", "--..", "-----", ".----", "..---", "...--",
			"....-", ".....", "-....", "--...", "---..", "----.", String.valueOf(SPACE) };

	public static String ConvertTextToMorse(String text) {
		text = text.toLowerCase();
		String result = "";
		int index = -1;
		for (int i = 0; i <= text.length() - 1; i++) {
			index = String.valueOf(letters).indexOf(text.charAt(i));
			if (index != -1)
				result += morseCode[index] + SPACE;
		}

		return result;
	}

	public static String ConvertMorseToText(String text) {
		text = "@" + text.replace(String.valueOf(SPACE), "@@") + "@";
		int index = -1;
		for (char c : letters) {
			index = String.valueOf(letters).indexOf(c);
			text = text.replace("@" + morseCode[index] + "@", "@" + c + "@");
		}

		return text.replace("@@@@", " ").replace("@", "");
	}
}
