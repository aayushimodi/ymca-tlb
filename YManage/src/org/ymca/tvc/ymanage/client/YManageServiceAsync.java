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
	
	void checkInVolunteer(String name, AsyncCallback<AttendanceRecord> callback) throws YException;
	
	void getCurrentMeeting(AsyncCallback<MeetingAttendanceStatus> callback) throws YException;

	void addVolunteer(VolunteerInfo info, AsyncCallback<Void> callback) throws YException;

	void getAllVolunteerInfo(AsyncCallback<ArrayList<VolunteerInfo>> callback) throws YException;

	void removeVolunteer(String name, AsyncCallback<Void> callback) throws YException;
	
	void getAttendanceRecord(String name, AsyncCallback<ArrayList<AttendanceRecord>> callback) throws YException;

	void startMeeting(AsyncCallback<MeetingAttendanceStatus> callback) throws YException;

	void endMeeting(AsyncCallback<Void> callback) throws YException;

	void createWorkGroups(int numGroups, AsyncCallback<MeetingAttendanceStatus> callback) throws YException;

	void getPastMeetingIds(AsyncCallback<ArrayList<MeetingId>> callback) throws YException;

	void getCheckedInVolunteers(MeetingId pastMeetingId, AsyncCallback<ArrayList<String>> callback) throws YException;
}
