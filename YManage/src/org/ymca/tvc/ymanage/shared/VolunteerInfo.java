package org.ymca.tvc.ymanage.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VolunteerInfo implements IsSerializable, Comparable<VolunteerInfo> {
	private String name;
	private String email;
	private String school;
	private String age;
	
	public VolunteerInfo() {
	}
	
	public VolunteerInfo(String name) {
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

	@Override
	public int compareTo(VolunteerInfo other) {
		return this.getName().compareTo(other.getName());
	}
}
