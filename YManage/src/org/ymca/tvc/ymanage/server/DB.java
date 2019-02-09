package org.ymca.tvc.ymanage.server;

import org.ymca.tvc.ymanage.shared.*;
import java.util.*;

public class DB {
	
	private static final DB current = new DB();
	private HashMap<String, Volunteer> volunteers;
	private HashMap<MeetingId, MeetingAttendanceStatus> meetings;
	private MeetingAttendanceStatus currentMeeting;
	
	static DB getCurrent() {
		return current; 
	}
	
	private DB() {
		this.volunteers = new HashMap<String, Volunteer>();
		this.meetings = new HashMap<MeetingId, MeetingAttendanceStatus>();
		this.currentMeeting = null;
		createTestData();
	}
	
	synchronized AttendanceRecord checkInVolunteer(String name) {
		if (currentMeeting == null) {
			throw new YException("There is no meeting in progress. The board needs to start a meeting first.");
		} 
		
		
		if(!volunteers.containsKey(name)) {
			throw new YException("The volunteer " + name + " does not exist. The board must add them to the volunteer list.");
		} 
		
		if(currentMeeting.getCheckedinStudents().containsKey(name)) {
			throw new YException("The volunteer " + name + " is already checked in.");
		}
		
		Date checkinTime = new Date();
		AttendanceRecord result = volunteers.get(name).markPresent(currentMeeting.getMeetingId(), checkinTime);
		currentMeeting.getCheckedinStudents().put(name, checkinTime);
		
		return result;
	}
	
	synchronized MeetingAttendanceStatus startMeeting() {
		if (currentMeeting == null) {
			
			currentMeeting = new MeetingAttendanceStatus();
		
			for(String v: volunteers.keySet()) {
				volunteers.get(v).markAbsent(currentMeeting.getMeetingId());
			}
		} 
		
		return currentMeeting;
	}
	
	synchronized void endMeeting() {
		if(currentMeeting == null) {
			throw new YException("There is no meeting in progress.");
		}
		
		meetings.put(currentMeeting.getMeetingId(), currentMeeting);
		currentMeeting = null;
	}
	
	synchronized MeetingAttendanceStatus getCurrentMeeting() {
		return currentMeeting;
	}
	
	synchronized void addVolunteer(VolunteerInfo info) {
		if (volunteers.containsKey(info.getName())) {
			throw new YException("The volunteer " + info.getName() + " already exists.");
		} else {
			volunteers.put(info.getName(), new Volunteer(info));
		}
	}
	
	synchronized void removeVolunteer(String name) {
		if (volunteers.containsKey(name)) {
			volunteers.remove(name);
		} else {
			throw new YException("The volunteer " + name + " does not exists.");
		}
	}
	

	ArrayList<AttendanceRecord> getAttendanceRecords(String name) {
		if (volunteers.containsKey(name)) {
			return volunteers.get(name).getAttendanceRecords();
		} else {
			throw new YException("The volunteer " + name + " does not exists.");
		}
	}
	
	synchronized ArrayList<VolunteerInfo> getAllVolunteerInfo() {
		
		ArrayList<VolunteerInfo> result = new ArrayList<VolunteerInfo>();
		for(String k : volunteers.keySet()) {
			result.add(volunteers.get(k).getInfo());
		}
		
		return result;
	}
	
	private void createTestData() {
		
		addVolunteer(new VolunteerInfo("Aayushi"));
		addVolunteer(new VolunteerInfo("Shivani"));
		
		//endMeeting();
		startMeeting();
		
		endMeeting();
		startMeeting();
		
		
	}

	/*
	 *  Checkin Student
	 *  
	 *  Is there a meeting going on? 
	 *  	No - return error
	 *  
	 *  Is there a student with the given name
	 *  	No - return error
	 *  
	 *  Is the student already checked in in the current meeting?
	 * 
	 *  	Yes - return error already checked in
	 * 
	 *  Add to current meeting the attendance record for this student
	 *  Add to the student the attendance record
	 *  
	 *  return the time at which the student is checked in.
	 *  
	 */
	
	/*
	 * Start a meeting
	 * 	Is there already meeting in progress 
	 * 		Yes return error
	 * 
	 *  Create new current meeting
	 *  Add record false to all students for this meeting
	 *  
	 * 		
	 */
	
	/*
	 * Stop a meeting 
	 * 	Is there a meeting in progress
	 * 		No return error
	 * 
	 * Take the current meeting and add it to the history
	 * Reset the current meeting to null
	 */
	
	/*
	 * Get the current meeting 
	 */
	
	/*
	 * Add volunteer
	 */ 
	
	/*
	 * Remove volunteer
	 */
	
	/*
	 * Get volunteer
	 */
	
	/*
	 * Get all volunteers
	 */
	 
}
