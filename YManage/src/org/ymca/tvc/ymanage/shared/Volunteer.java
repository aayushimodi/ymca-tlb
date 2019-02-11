package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Volunteer implements IsSerializable {
	VolunteerInfo info;
	HashMap<String, AttendanceRecord> attendanceRecordMap;
	
	public Volunteer() {
		this.attendanceRecordMap = new HashMap<String, AttendanceRecord>();
	}
	
	public Volunteer(VolunteerInfo info) {
		this.info = info;
		this.attendanceRecordMap = new HashMap<String, AttendanceRecord>();
	}
	
	public VolunteerInfo getInfo() {
		return info;
	}
	
	public void setInfo(VolunteerInfo info) {
		this.info = info;
	}
	
	public void markAbsent(MeetingId meetingId) {
		AttendanceRecord record = null;
		String key = meetingId.toString();
		if (attendanceRecordMap.containsKey(key)) {
			record = attendanceRecordMap.get(key);
		} else {
			record = new AttendanceRecord(meetingId);
			attendanceRecordMap.put(key, record);
		}
		
		record.markAbsent();
	}
	
	public AttendanceRecord markPresent(MeetingId meetingId, Date checkinTime) {
		AttendanceRecord record = null;
		String key = meetingId.toString();
		if (attendanceRecordMap.containsKey(key)) {
			record = attendanceRecordMap.get(key);
		} else {
			record = new AttendanceRecord(meetingId);
			attendanceRecordMap.put(key, record);
		}
		
		record.markPresent(checkinTime);
		return record;
	}

	public ArrayList<AttendanceRecord> getAttendanceRecords() {
		return new ArrayList<>(this.attendanceRecordMap.values());
	}
}
