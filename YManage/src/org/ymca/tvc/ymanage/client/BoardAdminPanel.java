package org.ymca.tvc.ymanage.client;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.DefaultSelectionModel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

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