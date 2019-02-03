package org.ymca.tvc.ymanage.shared;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Volunteer implements IsSerializable {
	String name;
	String email;
	String school;
	String age;
	HashMap<String, Boolean> attendance;
	
	public Volunteer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public void setAttendance(String meetingId, Boolean status) {
		attendance.put(meetingId, status);
	}
	
	
	
}
