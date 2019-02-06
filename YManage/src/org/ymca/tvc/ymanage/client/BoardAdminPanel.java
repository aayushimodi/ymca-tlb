package org.ymca.tvc.ymanage.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class BoardAdminPanel extends DockLayoutPanel {

	private final CheckinServiceAsync checkinService;
	private static final String titleHTML = "<h2 align='center'>YMCA TVC Board Admin</h2>";

	
	public BoardAdminPanel(CheckinServiceAsync checkinService) {
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
	}

	private void createComponents() {
		this.addNorth(createTitlePanel(), 5);
		this.add(createContentPanel());
	}

	private Widget createTitlePanel() {
		LayoutPanel titlePanel = new LayoutPanel();
		titlePanel.add(new HTML(titleHTML));
		return titlePanel;
	}

	private Widget createContentPanel() {
		TabLayoutPanel tabPanel = new TabLayoutPanel(2, Unit.EM);
		tabPanel.add(new VolunteerManagementPanel(checkinService), "Volunteers");
		tabPanel.selectTab(0);
		
		return tabPanel;
	}
}