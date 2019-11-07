package ch.mtrail.test.webserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ch.mtrail.test.webserver.handler.Handler;
import ch.mtrail.test.webserver.handler.HelloWorldHandler;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class App extends NanoHTTPD {

	final Map<String, Handler> handlers = new HashMap<>();

	public App() throws IOException {
		super(8086);
		start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
		System.out.println("\nRunning! Point your browsers to http://localhost:8081/ \n");
	}

	public static void main(final String[] args) {
		try {
			final App app = new App();
			app.registerHandler("/hello-world", new HelloWorldHandler());
		} catch (final IOException ioe) {
			System.err.println("Couldn't start server:\n" + ioe);
		}
	}

	private void registerHandler(final String uri, final Handler handler) {
		handlers.put(uri, handler);
	}

	@Override
	public Response serve(final IHTTPSession session) {
		final Handler handler = handlers.get(session.getUri());
		if (handler != null) {
			return handler.serve(session);
		}

		final String msg = "<html><body><h1>Invalid URL</h1></body></html>\n";
		return newFixedLengthResponse(Status.NOT_FOUND, NanoHTTPD.MIME_HTML, msg);
	}
}