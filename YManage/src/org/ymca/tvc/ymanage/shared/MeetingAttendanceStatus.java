package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingAttendanceStatus implements IsSerializable {

	private String meetingId;
	private HashMap<String, Date> checkedinStudents;
	private static int count = 0;

	public MeetingAttendanceStatus() {
		Calendar c = new GregorianCalendar();
		this.meetingId = (count++) + "_" + c.get(Calendar.MONTH) + "_" + c.get(Calendar.DAY_OF_MONTH) + "_" + c.get(Calendar.YEAR);
		this.checkedinStudents = new HashMap<String, Date>();
		this.checkedinStudents.put("Aayushi Modi", new Date());
		this.checkedinStudents.put("Shivani Modi", new Date());
	}
	
	
	public String getMeetingId() {
		return this.meetingId;
	}
	
	public HashMap<String, Date> getCheckedinStudents() {
		return this.checkedinStudents;
	}
}
