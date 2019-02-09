package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Volunteer implements IsSerializable {
	VolunteerInfo info;
	HashMap<MeetingId, AttendanceRecord> attendance;
	
	public Volunteer() {
		
	}
	
	public Volunteer(VolunteerInfo info) {
		this.info = info;
		this.attendance = new HashMap<MeetingId, AttendanceRecord>();
	}

	public VolunteerInfo getInfo() {
		return info;
	}
	
	public void setInfo(VolunteerInfo info) {
		this.info = info;
	}
	
	public void markAbsent(MeetingId meetingId) {
		attendance.put(meetingId, new AttendanceRecord(meetingId));
	}
	
	public AttendanceRecord markPresent(MeetingId meetingId, Date checkinTime) {
		AttendanceRecord r =  new AttendanceRecord(meetingId, checkinTime);
		attendance.put(meetingId, r);
		return r;
	}

	public ArrayList<AttendanceRecord> getAttendanceRecords() {
		return new ArrayList<>(this.attendance.values());
	}
}
