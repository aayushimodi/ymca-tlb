package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.MeetingAttendanceStatus;
import org.ymca.tvc.ymanage.shared.YException;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class CurrentMeetingPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;

	private HTML meetingLabel = new HTML();
	private Button meetingButton = new Button();
	
	private TextBox numGroupsBox;
	private Button createGroupsButton;
	private int numGroups;
	
	private TabPanel groupsTabPanel;
	
	private StatusPanel statusPanel;

	public CurrentMeetingPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("90%");
		this.addStyleName("tvc-center-align");
		this.getMeetingStatus();
	}

	private void createComponents() {
		this.addSouth(createStatusPanel(), 5);
		this.addNorth(createStartEndMeetingPanel(), 5);
		
		DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		p.addNorth(createGroupNumPanel(), 7);
		p.add(createGroupsTabPanel());
		this.add(p);
	}

	private Widget createStartEndMeetingPanel() {
		Grid g = new Grid(1, 3);
		
		this.meetingButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(meetingButton.getText().equals("Start")) {
					yManageService.startMeeting(new AsyncCallback<MeetingAttendanceStatus>() {
						@Override
						public void onFailure(Throwable caught) {
							statusPanel.displayError(caught);
						}

						@Override
						public void onSuccess(MeetingAttendanceStatus result) {
							processMeetingStatus(result);
							statusPanel.clearDisplay();
						}
					});
				} else {
					yManageService.endMeeting(new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							statusPanel.displayError(caught);
						}

						@Override
						public void onSuccess(Void result) {
							processMeetingStatus(null);
							statusPanel.clearDisplay();
						}
					});
				}
			}
		});

		g.setWidget(0, 0, meetingLabel);
		g.setWidget(0, 1, meetingButton);
		g.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		g.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		g.setCellPadding(10);
		g.setWidth("100%");
		g.addStyleName("tvc-center-align");

		return g;
	}
	
	private Widget createStatusPanel() {

		this.statusPanel = new StatusPanel();
		this.statusPanel.setWidth("100%");
		return this.statusPanel;
	}
	
	private Widget createGroupNumPanel() {
		
		FlexTable table = new FlexTable();
		
		
		Label l1 = new Label(" Divide Checked in Volunteers in groups.");
		l1.setWidth("100%");
		l1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidget(0, 0, l1);
		table.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		
		this.numGroupsBox = new TextBox();
		this.createGroupsButton = new Button("Create");
		
		table.setWidget(2, 0, new Label("Enter number of groups:"));
		table.getFlexCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		table.setWidget(2, 1, this.numGroupsBox);
		this.numGroupsBox.setWidth("100%");
		
		table.setWidget(2, 2, this.createGroupsButton);
		table.getFlexCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		table.setWidth("75%");
		table.setCellPadding(10);
		table.addStyleName("tvc-center-align");
		
		this.numGroupsBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					numGroups = Integer.parseInt(numGroupsBox.getText());
					createGroups();
				}
			}
		});
		
		this.createGroupsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				numGroups = Integer.parseInt(numGroupsBox.getText());
				createGroups();
			}
		});
		
		
		return table;
	}

	private Widget createGroupsTabPanel() {
		
		groupsTabPanel = new TabPanel();
		groupsTabPanel.setWidth("100%");
		groupsTabPanel.addStyleName("tvc-center-align");
		
		ScrollPanel scrollPanel = new ScrollPanel(groupsTabPanel);
		scrollPanel.setWidth("100%");
		scrollPanel.setHorizontalScrollPosition(0);
		return scrollPanel;
	}
	
	private void createGroups( ) {
		yManageService.createWorkGroups(numGroups, new AsyncCallback<MeetingAttendanceStatus>() {

			@Override
			public void onFailure(Throwable caught) {

				statusPanel.displayError(caught);
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				processMeetingStatus(result);	
				statusPanel.clearDisplay();
			}
		});
	}
	
	private void getMeetingStatus() {
		yManageService.getCurrentMeeting(new AsyncCallback<MeetingAttendanceStatus>() {

			@Override
			public void onFailure(Throwable caught) {

				statusPanel.displayError(caught);
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				processMeetingStatus(result);
				statusPanel.clearDisplay();
			}
		});
	}
	
	private void processMeetingStatus(MeetingAttendanceStatus result) {
		
		groupsTabPanel.clear();
		
		if (result == null) {
			
			this.meetingLabel.setHTML("<b>No meeting is in progress. <br/>Click `Start` button to start the meeting.</b>");
			this.meetingButton.setText("Start");
			
		} else {
			
			this.meetingLabel.setHTML(
					"The meeting <b>" + result.getMeetingId().getNum() + 
					"</b> started at <b>" + result.getMeetingId().getDate() + 
					"</b> is in progress. <br/> Click `End` button to end the meeting.");
			this.meetingButton.setText("End");
			
			groupsTabPanel.clear();
			ArrayList<ArrayList<String>> groups = result.getWorkGroups();
			
			if (groups != null) {
				for(int i = 0; i < groups.size(); i++) {
					ListPanel p = new ListPanel(" Volunteer Name ", groups.get(i));
					ScrollPanel s = new ScrollPanel(p);
					s.setHeight("20em");
					s.setVerticalScrollPosition(0);
					
					groupsTabPanel.add(s, "Group " + (i + 1));
				}
				
				groupsTabPanel.selectTab(0);
			}
		}
	}
}
