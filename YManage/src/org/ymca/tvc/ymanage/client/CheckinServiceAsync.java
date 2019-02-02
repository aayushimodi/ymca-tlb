package org.ymca.tvc.ymanage.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CheckinServiceAsync {
	void checkinStudent(String studentName, AsyncCallback<String> callback) throws IllegalArgumentException;
}
