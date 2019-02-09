package org.ymca.tvc.ymanage.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingId implements IsSerializable {
	private static int count = 0;
	private int num;
	private Date date;
	
	public MeetingId() {
		this.num = count++;
		this.date = new Date();
	}
	
	public int getNum() {
		return this.num;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String toString() {
		return 	num + "_" + date.toString();	
	}
}
