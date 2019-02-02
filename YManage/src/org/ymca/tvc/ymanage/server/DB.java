package org.ymca.tvc.ymanage.server;

public class DB {
	
	private static final DB current = new DB();
	
	static DB getCurrent() {
		return current; 
	}
}
