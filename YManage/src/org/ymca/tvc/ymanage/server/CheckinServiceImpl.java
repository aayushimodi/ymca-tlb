package org.ymca.tvc.ymanage.server;

import java.util.Date;

import org.ymca.tvc.ymanage.client.CheckinService;
import org.ymca.tvc.ymanage.shared.FieldVerifier;
import org.ymca.tvc.ymanage.shared.MeetingAttendanceStatus;
import org.ymca.tvc.ymanage.shared.Volunteer;
import org.ymca.tvc.ymanage.shared.YException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CheckinServiceImpl extends RemoteServiceServlet implements CheckinService {

	public Date checkInVolunteer(String name) throws YException {
		DB db = DB.getCurrent();
		return db.checkInVolunteer(name);
	}
	
	public MeetingAttendanceStatus getCheckinStatus() throws YException {
		return DB.getCurrent().getCurrentMeeting();
	}

	public void addVolunteer(Volunteer v) throws YException {
		DB db = DB.getCurrent();
		db.addVolunteer(v);	
	}
}
