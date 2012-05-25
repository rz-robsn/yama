package robsoninc.morse.utilities;

public class MorseStringConverter {

	private char[] letters = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', ' ' };
	private String[] morseCode = new String[] { ".-", "-...", "-.-.", "-..",
			".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--",
			"-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-",
			".--", "-..-", "-.--", "--..", "-----", ".----", "..---", "...--",
			"....-", ".....", "-....", "--...", "---..", "----.", " " };

	public String ConvertTextToMorse(String text) {
		text = text.toLowerCase();
		String result = "";
		int index = -1;
		for (int i = 0; i <= text.length() - 1; i++) {
			index = java.util.Arrays.asList(letters).indexOf(text.charAt(i));
			if (index != -1)
				result += morseCode[index] + " ";
		}

		return result;
	}

	public String ConvertMorseToText(String text) {
		text = "@" + text.replace(" ", "@@") + "@";
		int index = -1;
		for (char c : letters) {
			index = java.util.Arrays.asList(letters).indexOf(c);
			text = text.replace("@" + morseCode[index] + "@", "@" + c + "@");
		}

		return text.replace("@@@@", " ").replace("@", "");
	}
}
