package org.ymca.tvc.ymanage.client;

import org.ymca.tvc.ymanage.shared.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.dom.client.Style.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class VolunteerManagementPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;

	// table that shows the list of the volunteers
	private ListDataProvider<VolunteerInfo> volunteersTableDataProvider;
	private CellTable<VolunteerInfo> volunteersTable;
	private SingleSelectionModel<VolunteerInfo> volunteersTableSelectionModel = new SingleSelectionModel<VolunteerInfo>();

	//table that shows attendance record
	private ListDataProvider<AttendanceRecord> attendanceTableDataProvider;
	private CellTable<AttendanceRecord> attendanceTable;
	
	// add or edit volunteer information
	private TextBox nameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox ageTextBox = new TextBox();
	private TextBox schoolTextBox = new TextBox();
	private Button addVolunteerButton = new Button("Add");
	private Button deleteVolunteerButton = new Button("Delete");
	private VolunteerInfo newVInfo;
	private String name;
	private String email;
	private String age;
	private String school;
	
	private DockLayoutPanel studentInfoPanel = new DockLayoutPanel(Unit.EM);
	private StatusPanel statusPanel;

	public VolunteerManagementPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("75%");
		this.addStyleName("tvc-center-align");
		this.getVolunteers();
	}

	private void createComponents() {
		this.addSouth(createStatusPanel(), 5);
		this.addWest(createAddEditPanel(), 18);
		this.add(createStudentInfoPanel());
	}

	private Widget createAddEditPanel() {
		FlexTable addEditPanel = new FlexTable();

		addEditPanel.setWidget(1, 0, new Label("Name: "));
		addEditPanel.setWidget(1, 1, nameTextBox);

		addEditPanel.setWidget(2, 0, new Label("Email: "));
		addEditPanel.setWidget(2, 1, emailTextBox);

		addEditPanel.setWidget(3, 0, new Label("Age: "));
		addEditPanel.setWidget(3, 1, ageTextBox);

		addEditPanel.setWidget(4, 0, new Label("School: "));
		addEditPanel.setWidget(4, 1, schoolTextBox);

		addEditPanel.setWidget(5, 0, addVolunteerButton);
		addEditPanel.setWidget(5, 1, deleteVolunteerButton);

		nameTextBox.setFocus(true);

		addVolunteerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addVolunteer();
			}
		});
		
		deleteVolunteerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteVolunteer();
			}
		});

		schoolTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addVolunteer();
				}
			}
		});

		return addEditPanel;
	}

	private Widget createVolunteerTable() {

		TextColumn<VolunteerInfo> nameCol = new TextColumn<VolunteerInfo>() {
			public String getValue(VolunteerInfo row) {
				return row.getName();
			}
		};

		TextColumn<VolunteerInfo> emailCol = new TextColumn<VolunteerInfo>() {
			public String getValue(VolunteerInfo row) {
				return row.getEmail();
			}
		};

		TextColumn<VolunteerInfo> ageCol = new TextColumn<VolunteerInfo>() {
			public String getValue(VolunteerInfo row) {
				return row.getAge();
			}
		};

		TextColumn<VolunteerInfo> schoolCol = new TextColumn<VolunteerInfo>() {
			public String getValue(VolunteerInfo row) {
				return row.getSchool();
			}
		};

		volunteersTable = new CellTable<>();
		volunteersTable.addColumn(nameCol, "Name");
		volunteersTable.addColumn(emailCol, "Email");
		volunteersTable.addColumn(ageCol, "Age");
		volunteersTable.addColumn(schoolCol, "School");

		volunteersTableDataProvider = new ListDataProvider<VolunteerInfo>();
		volunteersTableDataProvider.addDataDisplay(volunteersTable);

		volunteersTable.setWidth("100%");
		volunteersTable.addStyleName("tvc-center-align");

		volunteersTable.setSelectionModel(volunteersTableSelectionModel);

		volunteersTableSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				VolunteerInfo row = volunteersTableSelectionModel.getSelectedObject();
				if (row != null) {
					onVolunteerSelected(row.getName());
				}
			}
		});

		ScrollPanel s = new ScrollPanel(volunteersTable);
		s.setHeight("20em");
		s.setVerticalScrollPosition(0);

		return s;
	}
	
	private Widget createAttendanceTable() {
		TextColumn<AttendanceRecord> dateCol = new TextColumn<AttendanceRecord>() {
			public String getValue(AttendanceRecord row) {
				return row.getMeetingId().toString();
			}
		};

		TextColumn<AttendanceRecord> attendanceCol = new TextColumn<AttendanceRecord>() {
			public String getValue(AttendanceRecord row) {
				return row.getAttendance() + "";
			}
		};

		attendanceTable = new CellTable<>();
		attendanceTable.addColumn(dateCol, "Date");
		attendanceTable.addColumn(attendanceCol, "Attendance");

		attendanceTableDataProvider = new ListDataProvider<AttendanceRecord>();
		attendanceTableDataProvider.addDataDisplay(attendanceTable);

		attendanceTable.setWidth("100%");
		attendanceTable.addStyleName("tvc-center-align");

		ScrollPanel s = new ScrollPanel(attendanceTable);
		s.setHeight("20em");
		s.setVerticalScrollPosition(0);

		return s;
	}
	
	private void onVolunteerSelected(String name) {
		yManageService.getAttendanceRecord(name, new AsyncCallback<ArrayList<AttendanceRecord>>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getClass().equals(YException.class)) {
					statusPanel.displayError(caught.getMessage());
				} else {
					statusPanel.displayError("Error in getting information from the server, try again later!");
				}

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<AttendanceRecord> result) {
				
				List<AttendanceRecord> list = attendanceTableDataProvider.getList();
				list.clear();
				
				for (AttendanceRecord ar: result) {
					list.add(ar);
				}
				
			}
		});
	}
	
	private Widget createStudentInfoPanel() {
		this.studentInfoPanel.addNorth(createVolunteerTable(), 20);
		this.studentInfoPanel.add(createAttendanceTable());
		
		return this.studentInfoPanel;
	}

	private Widget createStatusPanel() {

		this.statusPanel = new StatusPanel();
		this.statusPanel.setWidth("75%");
		return this.statusPanel;
	}

	private void addVolunteer() {
		name = nameTextBox.getText().trim();
		email = emailTextBox.getText();
		age = ageTextBox.getText();
		school = schoolTextBox.getText();
		nameTextBox.setFocus(true);
		
		if(!FieldVerifier.isValidName(name)) {
			statusPanel.displayError("Please enter a valid name.");
			return;
		}
		newVInfo = new VolunteerInfo(name);
		newVInfo.setEmail(email);
		newVInfo.setAge(age);
		newVInfo.setSchool(school);

		yManageService.addVolunteer(newVInfo, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getClass().equals(YException.class)) {
					statusPanel.displayError(caught.getMessage());
				} else {
					statusPanel.displayError("Error in getting information from the server, try again later!");
				}

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
			}

			public void onSuccess(Void result) {
				Logger logger = Logger.getLogger("");
				logger.log(Level.INFO, newVInfo.getName() + " added");

				List<VolunteerInfo> list = volunteersTableDataProvider.getList();
				list.add(newVInfo);

				nameTextBox.setText("");
				emailTextBox.setText("");
				ageTextBox.setText("");
				schoolTextBox.setText("");
				
				statusPanel.clearDisplay();
			}
		});
	}
	
	private void deleteVolunteer() {
		final VolunteerInfo row = volunteersTableSelectionModel.getSelectedObject();
		
		yManageService.removeVolunteer(row.getName(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getClass().equals(YException.class)) {
					statusPanel.displayError(caught.getMessage());
				} else {
					statusPanel.displayError("Error in getting information from the server, try again later!");
				}

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
			}

			public void onSuccess(Void result) {
				Logger logger = Logger.getLogger("");
				logger.log(Level.INFO, newVInfo.getName() + " deleted");

				List<VolunteerInfo> list = volunteersTableDataProvider.getList();
				list.remove(row);
				
				statusPanel.clearDisplay();
			}
		});
	}

	
	private void getVolunteers() {
		
		// statusPanel.displayInfo("Refreshing checking information ...");
		
		yManageService.getAllVolunteerInfo(new AsyncCallback<ArrayList<VolunteerInfo>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
			}
		

			@Override
			public void onSuccess(ArrayList<VolunteerInfo> result) {
				List<VolunteerInfo> list = volunteersTableDataProvider.getList();
				list.clear();
				for(VolunteerInfo vInfo : result) {
					list.add(vInfo);
				}
			}
		});		
	}
}
