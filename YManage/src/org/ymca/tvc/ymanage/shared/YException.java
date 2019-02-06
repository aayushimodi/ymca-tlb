package org.ymca.tvc.ymanage.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class YException extends RuntimeException implements IsSerializable {
	

	public YException() {
		
	}
	
	public YException(String message) {
		super(message);
	}
}
