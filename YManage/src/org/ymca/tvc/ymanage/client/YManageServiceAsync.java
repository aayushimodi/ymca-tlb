package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.user.client.rpc.*;

/**
 * The async counterpart of <code>YManageService</code>.
 */
public interface YManageServiceAsync {
	
	void checkInVolunteer(String name, AsyncCallback<Date> callback) throws YException;
	
	void getCheckinStatus(AsyncCallback<MeetingAttendanceStatus> callback) throws YException;

	void addVolunteer(VolunteerInfo info, AsyncCallback<Void> callback);

	void getAllVolunteerInfo(AsyncCallback<ArrayList<VolunteerInfo>> callback);

	void removeVolunteer(String name, AsyncCallback<Void> callback);
	
	void getAttendanceRecord(String name, AsyncCallback<ArrayList<AttendanceRecord>> callback);
}
