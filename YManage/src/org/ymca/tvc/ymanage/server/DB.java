package org.ymca.tvc.ymanage.server;

import org.ymca.tvc.ymanage.shared.*;

import com.google.gson.*;

import java.io.*;
import java.util.*;

public class DB {
	
	private static final DB current = new DB();
	private HashMap<String, Volunteer> volunteers;
	private HashMap<MeetingId, MeetingAttendanceStatus> meetings;
	private MeetingAttendanceStatus currentMeeting;
	private File meetingsDataFolder;
	private File volunteersDataFolder;
	Gson gson;
	
	static DB getCurrent() {
		return current; 
	}
	
	private DB() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		this.gson = builder.create();
		
		this.volunteers = new HashMap<String, Volunteer>();
		this.meetings = new HashMap<MeetingId, MeetingAttendanceStatus>();
		this.currentMeeting = null;
		
		try {
			this.ensureDataFolders();
			this.loadAllData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (this.volunteers.size() == 0) {
			this.createTestData();
		}
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
		Volunteer v = volunteers.get(name);
		AttendanceRecord result = v.markPresent(currentMeeting.getMeetingId(), checkinTime);
		currentMeeting.addCheckedInVolunteer(name, checkinTime);
		
		try {
			this.saveVolunteer(v);
			this.saveCurrentMeeting();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	synchronized MeetingAttendanceStatus startMeeting() {
		if (currentMeeting == null) {
			
			Date date = new Date();
			long num = System.currentTimeMillis();
			currentMeeting = new MeetingAttendanceStatus(new MeetingId(num, date));
		
			for(String v: volunteers.keySet()) {
				volunteers.get(v).markAbsent(currentMeeting.getMeetingId());
			}
			
			try {
				this.saveAllVolunteers();
				this.saveCurrentMeeting();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		return currentMeeting;
	}
	
	synchronized void endMeeting() {
		if(currentMeeting == null) {
			throw new YException("There is no meeting in progress.");
		}
		
		MeetingAttendanceStatus m = this.currentMeeting;
		meetings.put(currentMeeting.getMeetingId(), currentMeeting);
		currentMeeting = null;
		
		try {
			this.saveMeeting(m);
			this.saveCurrentMeeting();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	synchronized MeetingAttendanceStatus getCurrentMeeting() {
		return currentMeeting;
	}
	
	synchronized void addVolunteer(VolunteerInfo info) {
		
		
		if (volunteers.containsKey(info.getName())) {
			throw new YException("The volunteer " + info.getName() + " already exists.");
		} 
		
		Volunteer v = new Volunteer(info);
		volunteers.put(info.getName(), v);
		
		try {
			this.saveVolunteer(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
 	synchronized void removeVolunteer(String name) {
		if (volunteers.containsKey(name)) {
			volunteers.remove(name);
		} else {
			throw new YException("The volunteer " + name + " does not exists.");
		}
		
		
		try {
			this.removeVolunteerFile(name);
		} catch (IOException e) {
			e.printStackTrace();
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
	
	synchronized MeetingAttendanceStatus createWorkGroups(int numGroups) throws YException {
		if (currentMeeting != null) {
			currentMeeting.createWorkGroups(numGroups);
		}
		
		return currentMeeting;
	}
	
	private void createTestData() {
		
		addVolunteer(new VolunteerInfo("Aayushi"));
		addVolunteer(new VolunteerInfo("Shivani"));
		
		startMeeting();
		
		endMeeting();
		startMeeting();
		
		this.saveAllData();
	}
	
	private void saveAllData()  {
		try {
			this.saveAllVolunteers();
			this.saveAllMeetings();
			this.saveCurrentMeeting();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void saveAllVolunteers() throws IOException {
		for(Volunteer v : this.volunteers.values()) {
			this.saveVolunteer(v);
		}
	}
	
	
	private void saveVolunteer(Volunteer v) throws IOException {
		
		try (FileWriter writer = new FileWriter(this.getVolunteerFilePath(v))) {
			gson.toJson(v, writer);
		}
		
	}
	

	private void removeVolunteerFile(String name) throws IOException {
		File f = new File(this.getVolunteerFilePath(name));
		f.delete();
	}

	
	private void saveAllMeetings() throws IOException {
		
		for(MeetingAttendanceStatus m : this.meetings.values()) {
			this.saveMeeting(m);
		}
	}
	
	private void saveMeeting(MeetingAttendanceStatus m) throws IOException {
		
		try (FileWriter writer = new FileWriter(this.getMeetingFilePath(m))) {
			gson.toJson(m, writer);
		}
		
	}
	
	private void saveCurrentMeeting() throws IOException {
		if (this.currentMeeting != null) {
			try (FileWriter writer = new FileWriter(this.getCurrentMeetingFilePath())) {
				gson.toJson(this.currentMeeting, writer);
			}
		} else {
			File currentMeetingFile = new File(this.getCurrentMeetingFilePath());
			if (currentMeetingFile.exists()) {
				currentMeetingFile.delete();
			}
		}
	}
	
	private void loadAllData()  {
		this.loadCurrentMeeting();
		this.loadAllVolunteers();
		this.loadAllMeetings();
	}
	
	private void loadCurrentMeeting()  {
		
		File currentMeetingFile = new File(this.getCurrentMeetingFilePath());
		if (currentMeetingFile.exists()) {
		
			try {
				this.currentMeeting = gson.fromJson(new FileReader(currentMeetingFile.getAbsolutePath()), MeetingAttendanceStatus.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadAllVolunteers()  {
		
		File[] volunteersFiles = volunteersDataFolder.listFiles(new JsonFileFilter());
		for(File f : volunteersFiles) {
			
			try {
				System.out.println(f.getAbsolutePath());
				Volunteer v = gson.fromJson(new FileReader(f.getAbsolutePath()), Volunteer.class);
				this.volunteers.put(v.getInfo().getName(), v);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	private void loadAllMeetings()  {
		
		File[] meetingFiles = meetingsDataFolder.listFiles(new JsonFileFilter());
		for(File f : meetingFiles) {
			if (!f.getName().startsWith("current")) {
			
				try {
					MeetingAttendanceStatus m = gson.fromJson(new FileReader(f.getAbsolutePath()), MeetingAttendanceStatus.class);
					this.meetings.put(m.getMeetingId(), m);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void ensureDataFolders() throws IOException {
		String sRootPath = new File("").getAbsolutePath();
		volunteersDataFolder = new File(sRootPath, "volunteers");
		if (!volunteersDataFolder.exists()) {
			volunteersDataFolder.mkdir();
		}
		
		
		this.meetingsDataFolder = new File(sRootPath, "meetings");
		if (!meetingsDataFolder.exists()) {
			meetingsDataFolder.mkdir();
		}
	}
	
	private String getVolunteerFilePath(Volunteer v) {
		return getVolunteerFilePath(v.getInfo().getName());
	}
	
	private String getVolunteerFilePath(String vName) {
		File f = new File(volunteersDataFolder, vName.replace(' ', '_') + ".json");
		return f.getAbsolutePath();
	}
	
	private String getMeetingFilePath(MeetingAttendanceStatus m) {
		File f = new File(meetingsDataFolder, m.getMeetingId().getNum() + ".json");
		return f.getAbsolutePath();
	}
	
	private String getCurrentMeetingFilePath() {
		File f = new File(meetingsDataFolder, "current.json");
		return f.getAbsolutePath();
	}
	
	private class JsonFileFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".json");
		}
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
