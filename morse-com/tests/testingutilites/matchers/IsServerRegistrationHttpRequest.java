package testingutilites.matchers;

import org.apache.http.HttpRequest;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

public class IsServerRegistrationHttpRequest extends
		TypeSafeMatcher<HttpRequest> {

	private String registrationId;

	//public final static String REGISTRATION_URL = SERVER_URL + "/c2dmregister";
	
	@Override
	public void describeTo(Description description) {
		description.appendText("HttpRequest for Sending cd2m id to webserver.");
	}

	@Override
	public boolean matchesSafely(HttpRequest request) {
		
		IsRequestContainingHttpHeader matcher = new  IsRequestContainingHttpHeader("Host", "");
		
		return false;
	}

	@Factory
	public static <T> Matcher<HttpRequest> ServerRegistrationHttpRequest(
			String registrationId) {
		IsServerRegistrationHttpRequest matcher = new IsServerRegistrationHttpRequest();
		matcher.registrationId = registrationId;

		return matcher;
	}
}
