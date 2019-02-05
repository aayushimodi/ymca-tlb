package org.ymca.tvc.ymanage.client;

import java.util.Date;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.user.client.rpc.*;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("checkin")
public interface CheckinService extends RemoteService {
	
	Date checkInVolunteer(String name);
	
	MeetingAttendanceStatus getCheckinStatus();
	
	void addVolunteer(Volunteer v);
}
