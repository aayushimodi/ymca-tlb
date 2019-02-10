package org.ymca.tvc.ymanage.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AttendanceRecord implements IsSerializable, Comparable<AttendanceRecord> {
	private MeetingId meetingId;
	private Date checkinTime;

	
	public AttendanceRecord() {
	}
	
	public AttendanceRecord(MeetingId meetingId) {
		this.meetingId = meetingId;
		this.checkinTime = null;
	}
	
	public void markAbsent() {
		this.checkinTime = null;
	}
	
	public void markPresent( Date checkinTime) {
		this.checkinTime = checkinTime;
	}
	
	public MeetingId getMeetingId() {
		return this.meetingId;
	}

	public Boolean getAttendance() {
		return (this.checkinTime != null);
	}
	
	public Date getCheckinTime() {
		return this.checkinTime;
	}

	@Override
	public int compareTo(AttendanceRecord other) {
		return Long.compare(this.meetingId.getNum(), other.getMeetingId().getNum());
	}
}
