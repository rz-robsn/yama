package main_server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class DriverMain {

	public static void main(String[] list) throws Exception {
		Container container = new TestStubServer();
		Connection connection = new SocketConnection(container);
		SocketAddress address = new InetSocketAddress("localhost", 8080);

		connection.connect(address);
	}
}
