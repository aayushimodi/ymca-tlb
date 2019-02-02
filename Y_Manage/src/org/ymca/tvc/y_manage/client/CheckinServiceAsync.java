package org.ymca.tvc.y_manage.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CheckinServiceAsync {
	void checkinStudent(String studentName, AsyncCallback<String> callback) throws IllegalArgumentException;
}
