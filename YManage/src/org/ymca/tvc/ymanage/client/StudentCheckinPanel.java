package org.ymca.tvc.ymanage.client;


import java.util.Date;
import java.util.HashMap;
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
import com.google.gwt.view.client.ListDataProvider;

public class StudentCheckinPanel extends DockLayoutPanel {

	private static final String titleHTML = "<h2 align='center'>Teen Volunteer Core Checkin</h2>";
	
	private final CheckinServiceAsync checkinService;
	
	private ListDataProvider<AttendanceTableRow> attendanceTableDataProvider;
	
	private TextBox nameBox;
	private Button checkInButton;
	private HTML statusLabel;
	private Label meetingNameLabel;

	
	public StudentCheckinPanel(CheckinServiceAsync checkinService) {
		
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
	}

	private void createComponents() {
		
		this.addNorth(createTitlePanel(), 5);
		this.add(createContentPanel());
		this.getCheckinStatus();
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
		
		FlexTable table = new FlexTable();
		
		this.meetingNameLabel = new Label();
		this.meetingNameLabel.setWidth("100%");
		this.meetingNameLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		table.setWidget(0, 0, this.meetingNameLabel);
		table.getFlexCellFormatter().setColSpan(0, 0, 2);
		
		table.setWidget(1, 0, new Label());
		table.getFlexCellFormatter().setColSpan(1, 0, 2);
		
		table.setWidget(2, 0, this.nameBox);
		table.setWidget(2, 1, this.checkInButton);
		table.setWidth("50%");
		table.addStyleName("tvc-center-align");
		
		return table;
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
	
	private Widget createStatusPanel() {
		
		this.statusLabel = new HTML();
		this.statusLabel.setWidth("100%");
		this.statusLabel.addStyleName("tvc-status-bar");
		
		Grid grid = new Grid(1,1);
		grid.setWidget(0, 0, this.statusLabel);
		grid.setWidth("50%");
		grid.addStyleName("tvc-center-align");
		
		return grid;
	}

	private void getCheckinStatus() {
		
		setInfoStatus("Refreshing checking information ...");
		
		checkinService.getCheckinStatus(new AsyncCallback<MeetingAttendanceStatus>() {
			
			
			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
				
				setErrorStatus("ERROR in getting information from the server, will retry in a bit!");
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.INFO, "Meeting Name: " + result.getMeetingName());

				processMeetingAttendanceStatus(result);
				
				clearStatus();
			}
		});		
	}
	
	private void clearStatus() {
		this.statusLabel.setHTML("");
	}
	
	private void setInfoStatus(String message) {
		this.statusLabel.setHTML(
				"<span style=\"color:black\">" + new Date().toString() + ": </span>" +
				"<span style=\"color:black\">" + message + "</span>");
		
	}
	
	private void setErrorStatus(String message) {
		this.statusLabel.setHTML(
				"<span style=\"color:black\">" + new Date().toString() + ": </span>" +
				"<span style=\"color:red\">" + message + "</span>");
	}
	
	private void processMeetingAttendanceStatus(MeetingAttendanceStatus status) {
		
		// check if the check-in is open or not
		if(!status.getIsCheckinOpen()) {
			this.checkInButton.setEnabled(false);
			this.nameBox.setEnabled(false);
		} else {
			this.checkInButton.setEnabled(true);
			this.nameBox.setEnabled(true);
		}
		
		// update the meeting name and the date
		this.meetingNameLabel.setText("Check in for " + status.getMeetingName());
		
		// update the attendance table
		List<AttendanceTableRow> list = this.attendanceTableDataProvider.getList();
		list.clear();

		HashMap<String, Date> checkedInStudents = status.getCheckedinStudents();
		for (String name : checkedInStudents.keySet()) {
			AttendanceTableRow row = new AttendanceTableRow(name, checkedInStudents.get(name).toString());
			list.add(row);
		}
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
