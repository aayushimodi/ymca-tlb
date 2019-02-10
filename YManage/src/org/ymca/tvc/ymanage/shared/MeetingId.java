package org.ymca.tvc.ymanage.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MeetingId implements IsSerializable {
	
	private long num;
	private Date date;
	
	public MeetingId() {
	}
	
	public MeetingId(long num, Date date) {
		this.date = date;
		this.num = num;
	}
	
	public long getNum() {
		return this.num;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String toString() {
		return 	num + "_" + date.toString();	
	}
}
