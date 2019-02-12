package org.ymca.tvc.ymanage.shared;

import java.util.Date;
import java.util.Objects;

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
	
	@Override
	public int hashCode() {
	    return Objects.hash(this.num, this.date);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		
		if (!(o instanceof MeetingId)) {
			return false;
		}
		
		MeetingId m = (MeetingId) o;
		
		return (Long.compare(this.num, m.num) == 0) && (this.date.equals(m.date));
		
	}
}
