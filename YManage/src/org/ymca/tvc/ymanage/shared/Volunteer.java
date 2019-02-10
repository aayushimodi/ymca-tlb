package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Volunteer implements IsSerializable {
	VolunteerInfo info;
	ArrayList<AttendanceRecord> attendanceRecordList;
	transient HashMap<MeetingId, AttendanceRecord> attendanceRecordTable;
	
	public Volunteer() {
		this.attendanceRecordTable = new HashMap<MeetingId, AttendanceRecord>();
		this.attendanceRecordList = new ArrayList<AttendanceRecord>();
	}
	
	public Volunteer(VolunteerInfo info) {
		this.info = info;
		this.attendanceRecordTable = new HashMap<MeetingId, AttendanceRecord>();
		this.attendanceRecordList = new ArrayList<AttendanceRecord>();
	}
	
	public void loadAttendanceTable() {
		for(AttendanceRecord r : this.attendanceRecordList) {
			this.attendanceRecordTable.put(r.getMeetingId(), r);
		}
	}

	public VolunteerInfo getInfo() {
		return info;
	}
	
	public void setInfo(VolunteerInfo info) {
		this.info = info;
	}
	
	public void markAbsent(MeetingId meetingId) {
		AttendanceRecord record = null;
		if (attendanceRecordTable.containsKey(meetingId)) {
			record = attendanceRecordTable.get(meetingId);
		} else {
			record = new AttendanceRecord(meetingId);
			attendanceRecordTable.put(meetingId, record);
			attendanceRecordList.add(record);
		}
		
		record.markAbsent();
	}
	
	public AttendanceRecord markPresent(MeetingId meetingId, Date checkinTime) {
		AttendanceRecord record = null;
		if (attendanceRecordTable.containsKey(meetingId)) {
			record = attendanceRecordTable.get(meetingId);
		} else {
			record = new AttendanceRecord(meetingId);
			attendanceRecordTable.put(meetingId, record);
			attendanceRecordList.add(record);
		}
		
		record.markPresent(checkinTime);
		return record;
	}

	public ArrayList<AttendanceRecord> getAttendanceRecords() {
		return this.attendanceRecordList;
	}
}
