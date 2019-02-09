package org.ymca.tvc.ymanage.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AttendanceRecord implements IsSerializable {
	private String meetingId;
	private Date checkinTime;

	
	public AttendanceRecord() {
	}
	
	public AttendanceRecord(String meetingId) {
		this.meetingId = meetingId;
		this.checkinTime = null;
	}
	
	public AttendanceRecord(String meetingId, Date checkinTime) {
		this.meetingId = meetingId;
		this.checkinTime = checkinTime;
	}
	
	public String getMeetingId() {
		return this.meetingId;
	}

	public Boolean getAttendance() {
		return (this.checkinTime != null);
	}
	
	public Date getCheckinTime() {
		return this.checkinTime;
	}
}
