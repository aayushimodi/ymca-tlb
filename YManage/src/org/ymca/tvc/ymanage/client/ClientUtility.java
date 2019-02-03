package org.ymca.tvc.ymanage.client;

import com.google.gwt.user.client.Window;

class ClientUtility {

	static void goToHash(String newHash) {
		
		String href = Window.Location.getHref();
		String newURL;
		
		if (Window.Location.getHash() == null) {
		
			newURL = href + "#" + newHash;
		} else {
			
			int idx = href.lastIndexOf("#");
			newURL = href.substring(0, idx) + "#" + newHash;
		}
		
		Window.Location.assign(newURL);
	}
	
}

