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
	
	Date checkInVolunteer(String name) throws YException;
	
	MeetingAttendanceStatus getCheckinStatus() throws YException;
	
	void addVolunteer(VolunteerInfo info) throws YException;
	
	void removeVolunteer(String name) throws YException;
	
	ArrayList<VolunteerInfo> getAllVolunteerInfo();

	HashMap<String, Boolean> getAttendanceRecord(String name);
}
