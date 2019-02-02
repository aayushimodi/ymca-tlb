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

	private final CheckinServiceAsync checkinService;
	private ListDataProvider<AttendanceTableRow> dataProvider;
	private TextBox name;
	private Button checkInButton;
	private CellTable<AttendanceTableRow> table = new CellTable<>();

	public StudentCheckinPanel(CheckinServiceAsync checkinService) {
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
	}

	private void createComponents() {
		checkInButton = new Button();
		checkInButton.setText("Check In");
		this.add(checkInButton);

		name = new TextBox();
		this.add(name);
		
		makeTable();
		
		getMeetingStatus();
	}

	private void makeTable() {
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

		table.addColumn(nameCol);
		table.addColumn(dateCol);

		dataProvider = new ListDataProvider<AttendanceTableRow>();
		dataProvider.addDataDisplay(table);
	}
	
	private void getMeetingStatus() {
		checkinService.getCheckinStatus(new AsyncCallback<MeetingAttendanceStatus>() {
			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				
				Logger logger = Logger.getLogger("");
			    logger.log(Level.SEVERE, "Error" + caught.toString());
			}
			
			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				   Logger logger = Logger.getLogger("");
			       logger.log(Level.INFO, "Meeting Name: " + result.getMeetingName());
				
			       List<AttendanceTableRow> list = dataProvider.getList();
			       list.clear();
			       
			       HashMap<String, Date> checkedInStudents = result.getCheckedinStudents();
			       for(String name : checkedInStudents.keySet()) {
			    	   AttendanceTableRow row  = new AttendanceTableRow(name, (checkedInStudents.get(name).toGMTString());
			    	  list.add(row);
			       }
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
