package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.user.client.rpc.*;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("ymanage")
public interface YManageService extends RemoteService {
	
	// volunteer management
	void addVolunteer(VolunteerInfo info) throws YException;
	
	void removeVolunteer(String name) throws YException;
	
	ArrayList<VolunteerInfo> getAllVolunteerInfo();

	ArrayList<AttendanceRecord> getAttendanceRecord(String name);
	
	// meeting management
	MeetingAttendanceStatus startMeeting() throws YException;
	
	void endMeeting() throws YException;
	
	AttendanceRecord checkInVolunteer(String name) throws YException;
	
	MeetingAttendanceStatus getCheckinStatus() throws YException;
}
