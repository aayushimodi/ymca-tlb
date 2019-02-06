package org.ymca.tvc.ymanage.shared;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Volunteer implements IsSerializable {
	VolunteerInfo info;
	HashMap<String, Boolean> attendance;
	
	public Volunteer() {
		
	}
	
	public Volunteer(VolunteerInfo info) {
		this.info = info;
		this.attendance = new HashMap<String, Boolean>();
	}

	public VolunteerInfo getInfo() {
		return info;
	}
	
	public void setInfo(VolunteerInfo info) {
		this.info = info;
	}

	public HashMap<String, Boolean> getAttendance() {
		return this.attendance;
	}
	
	public void markAttendance(String meetingId, Boolean status) {
		attendance.put(meetingId, status);
	}
}
