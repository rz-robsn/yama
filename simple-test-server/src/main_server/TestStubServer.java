package main_server;

import org.simpleframework.http.core.Container;
import org.simpleframework.http.Response;
import org.simpleframework.http.Request;

import java.io.IOException;
import java.io.PrintStream;

public class TestStubServer implements Container {

	@Override
	public void handle(Request req, Response resp) {

		PrintStream body = null;
		try {
			body = resp.getPrintStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time = System.currentTimeMillis();

		resp.set("Content-Type", "text/plain");
		resp.set("Server", "HelloWorld/1.0 (Simple 4.0)");
		resp.setDate("Date", time);
		resp.setDate("Last-Modified", time);

		body.println("Hello World");
		body.close();
	}

}
