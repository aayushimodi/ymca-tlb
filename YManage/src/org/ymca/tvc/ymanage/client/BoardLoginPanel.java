package org.ymca.tvc.ymanage.client;

import org.ymca.tvc.ymanage.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class BoardLoginPanel extends DockLayoutPanel {

	private final CheckinServiceAsync checkinService;
	
	public BoardLoginPanel(CheckinServiceAsync checkinService) {
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
	}
	
	private void createComponents() {

		
	}
}
