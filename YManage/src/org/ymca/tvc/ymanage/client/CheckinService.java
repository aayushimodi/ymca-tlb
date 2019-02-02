package org.ymca.tvc.ymanage.client;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.user.client.rpc.*;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("checkin")
public interface CheckinService extends RemoteService {
	String checkinStudent(String studentName) throws IllegalArgumentException;
	
	MeetingAttendanceStatus getCheckinStatus();
}
