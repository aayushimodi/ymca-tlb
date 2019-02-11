package org.ymca.tvc.ymanage.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class YException extends RuntimeException implements IsSerializable {
		
	private static final long serialVersionUID = 1L;

	public YException() {
		
	}
	
	public YException(String message) {
		super(message);
	}
}
