package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Volunteer implements IsSerializable {
	VolunteerInfo info;
	HashMap<String, AttendanceRecord> attendance;
	
	public Volunteer() {
		
	}
	
	public Volunteer(VolunteerInfo info) {
		this.info = info;
		this.attendance = new HashMap<String, AttendanceRecord>();
	}

	public VolunteerInfo getInfo() {
		return info;
	}
	
	public void setInfo(VolunteerInfo info) {
		this.info = info;
	}
	
	public void markAbsent(String meetingId) {
		attendance.put(meetingId, new AttendanceRecord(meetingId));
	}
	
	public AttendanceRecord markPresent(String meetingId, Date checkinTime) {
		AttendanceRecord r =  new AttendanceRecord(meetingId, checkinTime);
		attendance.put(meetingId, r);
		return r;
	}

	public ArrayList<AttendanceRecord> getAttendanceRecords() {
		return new ArrayList<>(this.attendance.values());
	}
}
