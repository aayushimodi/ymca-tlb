package org.ymca.tvc.ymanage.server;

import org.ymca.tvc.ymanage.shared.*;
import java.util.*;

public class DB {
	
	private static final DB current = new DB();
	private HashMap<String, Volunteer> volunteers;
	private HashMap<String, MeetingAttendanceStatus> meetings;
	private MeetingAttendanceStatus currentMeeting;
	
	static DB getCurrent() {
		return current; 
	}
	
	private DB() {
		createTestData();
	}
	
	synchronized Date checkInVolunteer(String name) {
		if (currentMeeting == null) {
			//error
		} 
		
		
		if(!volunteers.containsKey(name)) {
				//error
		} 
		
		if(currentMeeting.getCheckedinStudents().containsKey(name)) {
					//error
		}
		
		Date date = new Date();
		volunteers.get(name).setAttendance(date.toString(), true);
		currentMeeting.getCheckedinStudents().put(name, date);
		
		return date;
	}
	
	synchronized void startMeeting() {
		if (!(currentMeeting == null)) {
			//error
		}
		
		currentMeeting = new MeetingAttendanceStatus();
		
		for(String v: volunteers.keySet()) {
			volunteers.get(v).setAttendance(currentMeeting.getMeetingId(), false);
		}
	}
	
	public synchronized void endMeeting() {
		if(currentMeeting == null) {
			//error
		}
		
		meetings.put(currentMeeting.getMeetingId(), currentMeeting);
		currentMeeting = null;
	}
	
	public synchronized MeetingAttendanceStatus getCurrentMeeting() {
		return currentMeeting;
	}
	
	public synchronized void addVolunteer(Volunteer v) {
		volunteers.put(v.getName(), v);
	}
	
	public synchronized void removeVolunteer(Volunteer v) {
		volunteers.remove(v.getName());
	}
	
	public synchronized Volunteer getVolunteer(String name) {
		return volunteers.get(name);
	}
	
	public synchronized HashMap<String, Volunteer> getAllVolunteers() {
		return volunteers;
	}
	
	private void createTestData() {
		addVolunteer(new Volunteer("Aayushi"));
		addVolunteer(new Volunteer("Shivani"));
		startMeeting();
		endMeeting();
		startMeeting();
		endMeeting();
		
		
		
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
