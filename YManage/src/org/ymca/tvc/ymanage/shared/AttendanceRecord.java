package org.ymca.tvc.ymanage.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AttendanceRecord implements IsSerializable {
	private String meetingId;
	private Boolean attendance;

	
	public AttendanceRecord() {
	}
	
	public AttendanceRecord(String meetingId, Boolean attendance) {
		this.meetingId = meetingId;
		this.attendance = attendance;
	}

	public String getMeetingId() {
		return this.meetingId;
	}

	public Boolean getAttendance() {
		return this.attendance;
	}
}
