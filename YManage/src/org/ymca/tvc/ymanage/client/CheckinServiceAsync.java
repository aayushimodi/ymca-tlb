package org.ymca.tvc.ymanage.client;

import java.util.Date;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.user.client.rpc.*;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CheckinServiceAsync {
	
	void checkInVolunteer(String name, AsyncCallback<Date> callback) throws YException;
	
	void getCheckinStatus(AsyncCallback<MeetingAttendanceStatus> callback) throws YException;

	void addVolunteer(Volunteer v, AsyncCallback<Void> callback) throws YException;
}
