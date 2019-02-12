package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingAttendanceStatus implements IsSerializable {

	private MeetingId meetingId;
	private HashMap<String, Date> checkedinStudents;
	private ArrayList<ArrayList<String>> groups;

	public MeetingAttendanceStatus() {
		
	}
	
	public MeetingAttendanceStatus(MeetingId id) {
		this.meetingId = id; 
		this.checkedinStudents = new HashMap<String, Date>();
	}
	
	public MeetingId getMeetingId() {
		return this.meetingId;
	}
	
	public HashMap<String, Date> getCheckedinStudents() {
		return this.checkedinStudents;
	}
	
	public void addCheckedInVolunteer(String name, Date checkinTime) {
		this.checkedinStudents.put(name, checkinTime);
	}
	
	public ArrayList<ArrayList<String>> getWorkGroups() {
		return this.groups;
	}
	
	public void createWorkGroups(int groupNum) {
		
		if (checkedinStudents.size() < groupNum) {
			throw new YException("The number of checked in students (" + checkedinStudents.size() + ") is less than the number of groups requested.");
		} 
		
		groups = new ArrayList<ArrayList<String>>();
		ArrayList<String> shuffledNames = new  ArrayList<>(checkedinStudents.keySet());
		
		Random rand = new Random();
		
		for (int i = shuffledNames.size(); i > 0; i--) {
			int x = rand.nextInt(i);
			this.swap(shuffledNames, x, i - 1);
		}
		
		int groupSize = shuffledNames.size() / groupNum;
		
		for (int i = 0; i < groupNum; i++) {
			
			ArrayList<String> group = new ArrayList<String>();
			int startIndex = i * groupSize;
			
			for (int k = startIndex; k < (startIndex + groupSize); k++) {
				group.add(shuffledNames.get(k));
			}
			
			groups.add(group);
		}
		
		for (int i = (groupSize * groupNum); i < shuffledNames.size(); i++) {		
			(groups.get(groups.size() - 1)).add(shuffledNames.get(i));
		}
	}
	
	private void swap(ArrayList<String> vs, int i1, int i2) {
		String temp = vs.get(i1);
		vs.set(i1, vs.get(i2));
		vs.set(i2, temp);
	}
}
