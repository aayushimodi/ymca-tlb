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

	public AttendanceRecord checkInVolunteer(String name) throws YException {
		DB db = DB.getCurrent();
		return db.checkInVolunteer(name);
	}
	
	public MeetingAttendanceStatus getCurrentMeeting() throws YException {
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
	public ArrayList<VolunteerInfo> getAllVolunteerInfo()  throws YException {
		DB db = DB.getCurrent();
		return db.getAllVolunteerInfo();
	}

	@Override
	public ArrayList<AttendanceRecord> getAttendanceRecord(String name)  throws YException  {
		DB db = DB.getCurrent();
		ArrayList<AttendanceRecord> list = db.getAttendanceRecords(name);
		Collections.sort(list);
		return list;
		
	}

	@Override
	public MeetingAttendanceStatus startMeeting() throws YException {
		
		DB db = DB.getCurrent();
		return db.startMeeting();
	}

	@Override
	public void endMeeting() throws YException {
		
		DB db = DB.getCurrent();
		db.endMeeting();
	}

	@Override
	public MeetingAttendanceStatus createWorkGroups(int numGroups) throws YException {
		DB db = DB.getCurrent();
		return db.createWorkGroups(numGroups);
	}
	
	@Override
	public ArrayList<MeetingId> getPastMeetingIds() throws YException {
		DB db = DB.getCurrent();
		return db.getPastMeetingIds();
	}

	@Override
	public ArrayList<String> getCheckedInVolunteers(MeetingId pastMeetingId) throws YException {
		DB db = DB.getCurrent();
		return db.getCheckedInVolunteers(pastMeetingId);
	}

}
