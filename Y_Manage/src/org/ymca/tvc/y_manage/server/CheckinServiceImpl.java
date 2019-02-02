package org.ymca.tvc.y_manage.server;

import org.ymca.tvc.y_manage.client.CheckinService;
import org.ymca.tvc.y_manage.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CheckinServiceImpl extends RemoteServiceServlet implements CheckinService {

	
	
	public String checkinStudent(String studentName) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(studentName)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		studentName = escapeHtml(studentName);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + studentName + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	// start a new check-in - called by the board member
	
	// close check-in - called by the board member
	
	// store the current check-in students
	
	// return the check-in status
}
