package org.ymca.tvc.ymanage.client;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.user.client.rpc.*;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CheckinServiceAsync {
	void checkinStudent(String studentName, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void getCheckinStatus(AsyncCallback<MeetingAttendanceStatus> callback);
}
