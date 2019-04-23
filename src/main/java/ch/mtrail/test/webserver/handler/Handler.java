package ch.mtrail.test.webserver.handler;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public interface Handler {

	Response serve(IHTTPSession session);
}
