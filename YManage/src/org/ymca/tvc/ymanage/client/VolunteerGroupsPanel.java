package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.MeetingAttendanceStatus;

import com.google.gwt.dom.client.Style.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class VolunteerGroupsPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;
	
	private TextBox numBox;
	private Button numButton;
	private int groupNum;
	
	private TabPanel groupsTabPanel;
	
	public VolunteerGroupsPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("75%");
		this.addStyleName("tvc-center-align");
		this.getMeetingStatus();
	}

	private void createComponents() {
		this.addNorth(createGroupNumPanel(), 7);
		this.add(createGroupsTabPanel());
	}

	private Widget createGroupNumPanel() {
		
		FlexTable table = new FlexTable();
		
		
		Label l1 = new Label(" Divide Checked in Volunteers in groups.");
		l1.setWidth("100%");
		l1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidget(0, 0, l1);
		table.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		
		this.numBox = new TextBox();
		this.numButton = new Button("Create");
		
		table.setWidget(2, 0, new Label("Enter number of groups:"));
		table.getFlexCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		table.setWidget(2, 1, this.numBox);
		this.numBox.setWidth("100%");
		
		table.setWidget(2, 2, this.numButton);
		table.getFlexCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		table.setWidth("60%");
		table.setCellPadding(5);
		table.addStyleName("tvc-center-align");
		
		this.numBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					groupNum = Integer.parseInt(numBox.getText());
					createGroups();
				}
			}
		});
		
		this.numButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				groupNum = Integer.parseInt(numBox.getText());
				createGroups();
			}
		});
		
		
		return table;
	}
	
	private Widget createGroupsTabPanel() {
		groupsTabPanel = new TabPanel();
		groupsTabPanel.setWidth("75%");
		groupsTabPanel.addStyleName("tvc-center-align");
		
		return groupsTabPanel;
	}
	
	private void createGroups( ) {
		yManageService.createWorkGroups(groupNum, new AsyncCallback<MeetingAttendanceStatus>() {

			@Override
			public void onFailure(Throwable caught) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
				
				// TODO: add to status panel
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				processMeetingStatus(result);				
			}
		});
	}
	
	private void getMeetingStatus() {
		yManageService.getCurrentMeeting(new AsyncCallback<MeetingAttendanceStatus>() {

			@Override
			public void onFailure(Throwable caught) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
				
				// TODO: add to status panel
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				processMeetingStatus(result);				
			}
		});
	}
	
	private void processMeetingStatus(MeetingAttendanceStatus result) {
		
		Logger logger = Logger.getLogger("");
		
		if (result == null)	{
			logger.log(Level.INFO, "Got no result");
		} else {
			if (result.getWorkGroups() != null)	{
				logger.log(Level.INFO, "Got " + result.getWorkGroups().size() + " groups.");
			} else {
				logger.log(Level.INFO, "Got no groups.");
			}
		}
			
		
		groupsTabPanel.clear();
		
		if (result != null) {
	
			ArrayList<ArrayList<String>> groups = result.getWorkGroups();
			
			if (groups != null) {
				for(int i = 0; i < groups.size(); i++) {
					groupsTabPanel.add(new VolunteerNameListPanel(groups.get(i)), "Group " + (i + 1));
				}
			}
		}
	}
	
	/*
	private void createViewGroupsPanel() {
		groupTabs.clear();
		for (int i = 0; i < groupNum; i++) {
			groupTabs.add(new VerticalPanel(), "Group " + (i + 1));
		}
		
		groupTabs.add(new VerticalPanel(), "Unassigned");
		groupTabs.selectTab(0);
		
		groupTabs.addStyleName("tvc-center-align");
		
		this.add(groupTabs);
	}
	*/
}
