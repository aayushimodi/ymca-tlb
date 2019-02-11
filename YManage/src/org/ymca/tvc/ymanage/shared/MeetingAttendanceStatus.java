package org.ymca.tvc.ymanage.shared;

import java.util.*;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingAttendanceStatus implements IsSerializable {

	private MeetingId meetingId;
	private HashMap<String, Date> checkedinStudents;
	transient private ArrayList<ArrayList<String>> groups;

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
	
	public void createWorkGroups(int groupNum) {
		groups = new ArrayList<ArrayList<String>>();
		ArrayList<String> vs = new  ArrayList<>(checkedinStudents.keySet());
		
		if (vs.size() < groupNum) {
			new YException("The number of checked in students (" + vs.size() + ") is less than the number of groups requested.");
		} 
		
		Random rand = new Random();
		
		for (int i = vs.size(); i > 0; i--) {
			int x = rand.nextInt(i);
			this.swap(vs, x, i - 1);
		}
		
		ArrayList<String> temp = vs;
		
		int groupSize = vs.size()/groupNum;
		for (int i = 0; i < groupNum; i++) {
			ArrayList<String> group = new ArrayList<String>();
			for (int k = 0; k < groupSize; k++) {
				group.add(temp.get(k));
				temp.remove(k);
			}
			groups.add(group);
		}
		
		groups.get(groups.size() - 1).addAll(temp);
	}
	
	private void swap(ArrayList<String> vs, int i1, int i2) {
		String temp = vs.get(i1);
		vs.set(i1, vs.get(i2));
		vs.set(i2, temp);
	}
}
