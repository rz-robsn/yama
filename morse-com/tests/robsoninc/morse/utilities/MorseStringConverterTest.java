package robsoninc.morse.utilities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MorseStringConverterTest {

	private String programmingIsFun = "Programming is fun";
	private String programmingIsFunMorse = ".--. .-. --- --. .-. .- -- -- .. -. --.  .. ...  ..-. ..- -.";

	@Test
	public void getMorse_shouldReturnConvertedMorse() throws Exception {

		assertThat(
				MorseStringConverter.ConvertTextToMorse(this.programmingIsFun),
				equalTo(this.programmingIsFunMorse));
	}

	@Test
	public void getString_shouldReturnConvertedString() throws Exception {

		assertThat(
				MorseStringConverter
						.ConvertMorseToText(this.programmingIsFunMorse),
				equalTo(this.programmingIsFun.toLowerCase()));
	}
}
