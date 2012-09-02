package testingutilites.matchers;

import org.apache.http.HttpRequest;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import testingutilites.HttpConstants;

public class IsServerRegistrationHttpRequest extends TypeSafeMatcher<HttpRequest>
{

	private String registrationId;
	private HttpRequest request;
	private IsRequestContainingHttpParam matcher;

	@Override
	public void describeTo(Description description)
	{
		description.appendText("HttpRequest for Sending cd2m id to webserver.");
		description.appendValueList("WithParameters", ",", ".", "URI contains "
		        + HttpConstants.SERVER_URL + "/c2dmregister", "Method: POST",
		        "user_id: " + registrationId);
		description.appendValueList("got", ",", ".", "URI: "
		        + request.getRequestLine().getUri(), "Method: "
		        + request.getRequestLine().getMethod(), "user_id: "
		        + request.getParams().getParameter("user_id"));
		description.appendDescriptionOf(matcher);
	}

	@Override
	public boolean matchesSafely(HttpRequest request)
	{

		this.request = request;

		matcher = new IsRequestContainingHttpParam("registration_id",
		        registrationId);

		return request.getRequestLine().getUri().contains(String.format("%s/c2dmregister", HttpConstants.SERVER_URL))
		        && request.getRequestLine().getMethod().equalsIgnoreCase("POST")
		        && matcher.matchesSafely(request)
		        && request.getParams().getParameter("user_id") != null
		        && !request.getParams().getParameter("user_id").toString().isEmpty();

	}

	@Factory
	public static <T> Matcher<HttpRequest> serverRegistrationHttpRequest(
	        String registrationId)
	{
		IsServerRegistrationHttpRequest matcher = new IsServerRegistrationHttpRequest();
		matcher.registrationId = registrationId;

		return matcher;
	}
}
