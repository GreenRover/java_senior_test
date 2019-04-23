package ch.mtrail.test.webserver.handler;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public class ChatHandler implements Handler {

	private final List<ChatMsg> msgs = new LinkedList<>();

	private class ChatMsg {
		final String msg;
		final Date date;
		final String user;

		public ChatMsg(final String msg, final Date date, final String user) {
			this.msg = msg;
			this.date = date;
			this.user = user;
		}

		public String getMsg() {
			return msg;
		}

		public Date getDate() {
			return date;
		}

		public String getUser() {
			return user;
		}
	}
	
	private void addMsg(final String user, final String msg) {
		if (msgs.size() > 100) {
			msgs.remove(0);
		}
		msgs.add(new ChatMsg(msg, new Date(), user));
	}

	public Response serve(final IHTTPSession session) {
		final Map<String, List<String>> parms = session.getParameters();
		
		final String action = Optional.ofNullable(parms.get("action")).orElse(Collections.singletonList("form")).get(0);
		switch (action) {
		case "list_msgs":
			return serverListMsgs(session);
			
		case "form":
			default:
			return serverListForm(session);
		}
		
	}

	private Response serverListMsgs(final IHTTPSession session) {
		final ListIterator<ChatMsg> li = msgs.listIterator(msgs.size());

		String html = "";
		while(li.hasPrevious()) {
			final ChatMsg msg = li.previous();
			
			html += String.format("<div>%s %s: %s</div>",  msg.getDate().toString(), msg.getUser(), msg.getMsg());
		}
		final Response response = NanoHTTPD.newFixedLengthResponse("<html><body>" + html + "</body></html>");
		response.addHeader("Refresh", "1;?action=list_msgs");
		return response;
	}

	private Response serverListForm(final IHTTPSession session) {
		final Map<String, List<String>> parms = session.getParameters();
		
		String msg = "<html><body><h1>Welcome to chat</h1>\n";

		if (parms.get("username") == null) {
			msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p></form>\n";
		} else {
			if (parms.get("msg") != null) {
				addMsg(parms.get("username").get(0), parms.get("msg").get(0));
			}
			msg += "<form action='?' method='get'>\n  <p>Msg: <input type='text' name='msg'></p>"
					+ "<input type='hidden' name='username' value ='" + parms.get("username").get(0) + "'></form>\n";
			msg += "<iframe src='?action=list_msgs' width='100%' height='400' >";
		}

		return NanoHTTPD.newFixedLengthResponse(msg + "</body></html>\n");
	}

}
