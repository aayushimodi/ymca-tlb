package org.ymca.tvc.ymanage.server;

import java.util.*;

import org.ymca.tvc.ymanage.client.YManageService;
import org.ymca.tvc.ymanage.shared.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class YManageServiceImpl extends RemoteServiceServlet implements YManageService {

	public Date checkInVolunteer(String name) throws YException {
		DB db = DB.getCurrent();
		return db.checkInVolunteer(name);
	}
	
	public MeetingAttendanceStatus getCheckinStatus() throws YException {
		return DB.getCurrent().getCurrentMeeting();
	}

	
	public void addVolunteer(VolunteerInfo info) throws YException {
		DB db = DB.getCurrent();
		db.addVolunteer(info);
	}

	
	public void removeVolunteer(String name) throws YException {
		DB db = DB.getCurrent();
		db.removeVolunteer(name);
	}

	@Override
	public ArrayList<VolunteerInfo> getAllVolunteerInfo() {
		DB db = DB.getCurrent();
		return db.getAllVolunteerInfo();
	}

	@Override
	public HashMap<String, Boolean> getAttendanceRecord(String name) {
		DB db = DB.getCurrent();
		return db.getAttendanceRecord(name);
	}
}
