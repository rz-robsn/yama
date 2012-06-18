package robsoninc.morse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat; 
import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ReceiverC2dmRegistrationTest {
		
	private Intent i ;
	
    @Before
    public void setUp() throws Exception {
    	i = createMock(Intent.class);
    	replay(i);
    }
	
	@Test
	public void testSaveRegistrationIdinPreferencesOnSuccessfullRegistration() throws Exception {
		
    }
}
