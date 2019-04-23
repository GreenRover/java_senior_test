package ch.mtrail.test.webserver.handler;

import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public class HelloWorldHandler implements Handler {

	public Response serve(final IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        final Map<String, List<String>> parms = session.getParameters();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username").get(0) + "!</p>";
        }
        return NanoHTTPD.newFixedLengthResponse(msg + "</body></html>\n");
	}

}
