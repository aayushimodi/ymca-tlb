package org.ymca.tvc.y_manage.client;

import org.ymca.tvc.y_manage.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class StudentCheckinPanel extends DockLayoutPanel {

	private final CheckinServiceAsync checkinService;
	
	public StudentCheckinPanel(CheckinServiceAsync checkinService) {
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
	}
	
	private void createComponents() {

		// add button
		
		// add table
				
		// get the current check-in date, checked in students so far, last check-in number
		
		// display data in the table
		
		// add timer to refresh
	}
}
