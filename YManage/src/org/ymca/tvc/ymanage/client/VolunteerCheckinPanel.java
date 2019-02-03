package org.ymca.tvc.ymanage.client;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.*;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

public class VolunteerCheckinPanel extends DockLayoutPanel {

	private static final String titleHTML = "<h2 align='center'>Teen Volunteer Corps Check In</h2>";
	
	private final CheckinServiceAsync checkinService;
	
	private ListDataProvider<AttendanceTableRow> attendanceTableDataProvider;
	private StatusPanel statusPanel;
	
	private TextBox nameBox;
	private Button checkInButton;
	private Label meetingIdLabel;

	
	public VolunteerCheckinPanel(CheckinServiceAsync checkinService) {
		
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
		
		this.getCheckinStatus();
	}
	
	void refreshData() {
		this.getCheckinStatus();
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
		DockLayoutPanel contentPanel = new DockLayoutPanel(Unit.EM);
		contentPanel.addNorth(createCheckinInputPanel(), 5);
		contentPanel.addSouth(createStatusPanel(), 5);
		contentPanel.add(createAttendanceTable());
		
		return contentPanel;
	}
	
	private Widget createCheckinInputPanel() {
		
		this.nameBox = new TextBox();
		this.nameBox.setWidth("90%");
		
		this.checkInButton = new Button();
		this.checkInButton.setText("Check In");
		this.checkInButton.setWidth("90%");
		this.checkInButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				checkInVolunteer();
			}

			
		});
		
		FlexTable table = new FlexTable();
		
		this.meetingIdLabel = new Label();
		this.meetingIdLabel.setWidth("100%");
		this.meetingIdLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		table.setWidget(0, 0, this.meetingIdLabel);
		table.getFlexCellFormatter().setColSpan(0, 0, 2);
		
		table.setWidget(1, 0, new Label());
		table.getFlexCellFormatter().setColSpan(1, 0, 2);
		
		table.setWidget(2, 0, this.nameBox);
		table.setWidget(2, 1, this.checkInButton);
		table.setWidth("50%");
		table.addStyleName("tvc-center-align");
		
		return table;
	}
	
	private Widget createStatusPanel() {
		
		this.statusPanel = new StatusPanel();
		return this.statusPanel;
	}
	
	private Widget createAttendanceTable() {
	
		CellTable<AttendanceTableRow> table = new CellTable<>();
		TextColumn<AttendanceTableRow> nameCol = new TextColumn<AttendanceTableRow>() {
			public String getValue(AttendanceTableRow row) {
				return row.name;
			}
		};

		TextColumn<AttendanceTableRow> dateCol = new TextColumn<AttendanceTableRow>() {
			public String getValue(AttendanceTableRow row) {
				return row.date;
			}
		};

		table.addColumn(nameCol, "Name");
		table.addColumn(dateCol, "Checkin Time");

		this.attendanceTableDataProvider = new ListDataProvider<AttendanceTableRow>();
		this.attendanceTableDataProvider.addDataDisplay(table);
		
		table.setWidth("50%");
		table.addStyleName("tvc-center-align");
		
		return table;
	}
	
	
	private void getCheckinStatus() {
		
		statusPanel.displayInfo("Refreshing checking information ...");
		
		checkinService.getCheckinStatus(new AsyncCallback<MeetingAttendanceStatus>() {
			
			
			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
				
				statusPanel.displayError("ERROR in getting information from the server, will retry in a bit!");
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.INFO, "Meeting Id: " + result.getMeetingId());

				processMeetingAttendanceStatus(result);
				
				statusPanel.clearDisplay();
			}
		});		
	}
	
	private void processMeetingAttendanceStatus(MeetingAttendanceStatus status) {
		
		if (status == null) {
			
			this.checkInButton.setEnabled(false);
			this.nameBox.setEnabled(false);
			
		} else {
			
			this.checkInButton.setEnabled(true);
			this.nameBox.setEnabled(true);
		}
		
		// update the meeting name and the date
		this.meetingIdLabel.setText("Check in for " + status.getMeetingId());
		
		// update the attendance table
		List<AttendanceTableRow> list = this.attendanceTableDataProvider.getList();
		list.clear();

		HashMap<String, Date> checkedInStudents = status.getCheckedinStudents();
		for (String name : checkedInStudents.keySet()) {
			AttendanceTableRow row = new AttendanceTableRow(name, checkedInStudents.get(name).toString());
			list.add(row);
		}
	}
	
	private void checkInVolunteer() {
		
		String volunteerName = this.nameBox.getText();
		statusPanel.displayInfo("Checking in " + volunteerName + " ...");
		
		checkinService.checkInStudent(new AsyncCallback<MeetingAttendanceStatus>() {
			
			
			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
				
				statusPanel.displayError("ERROR in getting information from the server, will retry in a bit!");
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.INFO, "Meeting Id: " + result.getMeetingId());

				processMeetingAttendanceStatus(result);
				
				statusPanel.clearDisplay();
			}
		});	
	}
	
	private class AttendanceTableRow {
		public String name;
		public String date;

		public AttendanceTableRow(String name, String date) {
			this.name = name;
			this.date = date;
		}
	}
}
