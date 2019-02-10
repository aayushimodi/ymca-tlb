package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingAttendanceStatus implements IsSerializable {

	private MeetingId meetingId;
	private HashMap<String, Date> checkedinStudents;

	public MeetingAttendanceStatus() {
		
	}
	
	public MeetingAttendanceStatus(MeetingId id) {
		this.meetingId = id; 
		this.checkedinStudents = new HashMap<String, Date>();
	}
	
	public MeetingId getMeetingId() {
		return this.meetingId;
	}
	
	public HashMap<String, Date> getCheckedinStudents() {
		return this.checkedinStudents;
	}
}
