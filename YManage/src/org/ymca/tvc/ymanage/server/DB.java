package org.ymca.tvc.ymanage.server;

public class DB {
	
	private static final DB current = new DB();
	
	static DB getCurrent() {
		return current; 
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
