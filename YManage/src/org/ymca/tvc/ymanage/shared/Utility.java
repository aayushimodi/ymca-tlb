package org.ymca.tvc.ymanage.shared;

import java.util.Date;

class Utility {
	
	static String formatDate(Date d, String format) {
		
		DefaultDateTimeFormat dft = new DefaultDateTimeFormat(format);
		return dft.format(d);
	}	
}

