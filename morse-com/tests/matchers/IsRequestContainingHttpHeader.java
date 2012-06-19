package matchers;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

public class IsRequestContainingHttpHeader extends TypeSafeMatcher<HttpRequest> {

	private String name;
	private String value;
	
	public IsRequestContainingHttpHeader(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public void describeTo(Description description) {		
		description.appendText(String.format("HttpRequest with header \"%s: %s\"", name, value));		
	}

	@Override
	public boolean matchesSafely(HttpRequest request) {
		
		Header[] requestHeaders = request.getHeaders(this.name);
		if (requestHeaders.length == 0) 
		{
			return false;
		}
		else 
		{
			for(Header h : requestHeaders)
			{
				if (h.getValue().equals(this.value))
				{
					return true;
				}
			}
		}		
		return false;
	}

	@Factory
	public static <T> Matcher<HttpRequest> httpRequestContainingHeader(String name, String value) {				
	    return new IsRequestContainingHttpHeader(name, value);
	}
}
