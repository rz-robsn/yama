package com.yama;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.yama.R;
import com.yama.ActivityMain;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ActivityMainTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new ActivityMain().getString(R.string.hello);
        assertThat(hello, equalTo("Hello World, MorseActivity!"));
    }
}