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
	
	public AttendanceRecord(MeetingId meetingId, Date checkinTime) {
		this.meetingId = meetingId;
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
		return Integer.compare(this.meetingId.getNum(), other.getMeetingId().getNum());
	}
}
