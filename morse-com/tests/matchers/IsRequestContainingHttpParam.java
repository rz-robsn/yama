package matchers;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

public class IsRequestContainingHttpParam extends TypeSafeMatcher<HttpRequest> {

	private String key;
	private Object value;
	
	public IsRequestContainingHttpParam(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(String.format("HttpRequest containing parameter %s with value %s", this.key, this.value));
	}

	@Override
	public boolean matchesSafely(HttpRequest request) {
		Object requestValue = request.getParams().getParameter(key);		
		return requestValue != null && this.value.equals(requestValue);
	}

	@Factory
	public static <T> Matcher<HttpRequest> requestContainingHttpParam(String key, Object value) {						
	    return new IsRequestContainingHttpParam(key, value);
	}
}
