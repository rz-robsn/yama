package com.yama.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.yama.utilities.MorseStringConverter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MorseStringConverterTest {

	private String programmingIsFun = "Programming is fun";
	private String programmingIsFunMorse = ".--.+.-.+---+--.+.-.+.-+--+--+..+-.+--.+++..+...+++..-.+..-+-.+";

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
