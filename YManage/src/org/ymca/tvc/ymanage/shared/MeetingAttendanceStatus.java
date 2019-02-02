package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingAttendanceStatus implements IsSerializable {

	private String meetingName;
	
	private boolean isCheckinOpen;
	
	private Date meetingDate;
	
	private HashMap<String, Date> checkedinStudents;

	public MeetingAttendanceStatus() {
		
	}
	
	public MeetingAttendanceStatus(String meetingName) {
		this.meetingName = meetingName;
		this.isCheckinOpen = false;
		this.meetingDate = new Date();
		this.checkedinStudents = new HashMap<String, Date>();
	}
	
	
	public String getMeetingName() {
		return this.meetingName;
	}
	
	public Date getMeetingDate() {
		return this.meetingDate;
	}
	
	public boolean getIsCheckinOpen() {
		return this.isCheckinOpen;
	}
	
	public HashMap<String, Date> getCheckedinStudents() {
		return this.checkedinStudents;
	}
}
