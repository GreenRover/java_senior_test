package ch.mtrail.test.webserver.handler;

import java.time.LocalTime;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public class CurrentTimeHandler implements Handler {

	public Response serve(final IHTTPSession session) {
        String msg = "<html><body><h1>The time is</h1>\n";
        msg += "<p>Server time: " + LocalTime.now() + "</p>";
        
        final Response response = NanoHTTPD.newFixedLengthResponse(msg + "</body></html>\n");
        response.addHeader("Refresh", "1;?");
        return response;
	}

}
