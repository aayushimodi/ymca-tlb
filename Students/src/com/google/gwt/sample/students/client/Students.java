package com.google.gwt.sample.students.client;


import java.util.*;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class Students implements EntryPoint {
	
	private HorizontalPanel homePanel = new HorizontalPanel();
	private Button boardButton = new Button();
	private Button meetingButton = new Button();
	
	private TabPanel pMeeting = new TabPanel();
	private VerticalPanel groupsPanel = new VerticalPanel();
	private VerticalPanel attendancePanel = new VerticalPanel();
	
	private Label groupNumLabel = new Label();
	private TextBox groupNumBox = new TextBox();
	private FlexTable groupFlexTable = new FlexTable();
	int groupNum;
	
	private TextBox attendanceTextBox = new TextBox();
	private Button checkInButton = new Button();
	
	private VerticalPanel passwordPanel = new VerticalPanel();
	private PopupPanel boardPopUp = new PopupPanel();
	private PasswordTextBox passwordBox = new PasswordTextBox();
	private Button closePassword = new Button();
	private Label passwordLabel = new Label();
	String password;
	
	private TabPanel pBoard = new TabPanel();
	private FlexTable studentsFlexTable = new FlexTable();
	private VerticalPanel addPanel = new VerticalPanel();
	private HorizontalPanel membersPanel = new HorizontalPanel();
	private TextBox nameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox ageTextBox = new TextBox();
	private TextBox schoolTextBox = new TextBox();
	private Button addStudentButton = new Button("Add");
	private Label nameLabel = new Label();
	private Label emailLabel = new Label();
	private Label ageLabel = new Label();
	private Label schoolLabel = new Label();

	private FlexTable tasksFlexTable = new FlexTable();
	private VerticalPanel taskPanel = new VerticalPanel();
	private HorizontalPanel schedulePanel = new HorizontalPanel();
	private Label orderLabel = new Label();
	private Label taskLabel = new Label();
	private Label startLabel = new Label();
	private Label endLabel = new Label();
	private TextBox orderBox = new TextBox();
	private TextBox taskBox = new TextBox();
	private TextBox startBox = new TextBox();
	private TextBox endBox = new TextBox();
	private Button addTaskButton = new Button("Add");
	
	
	private FlexTable notesFlexTable = new FlexTable();
	private HorizontalPanel notesPanel = new HorizontalPanel();
	private VerticalPanel notesEnterPanel = new VerticalPanel();
	private Label dateLabel = new Label();
	private Label notesLabel = new Label();
	private TextBox dateBox = new TextBox();
	private TextBox notesBox = new TextBox();

	private HashMap<String, Student> students = new HashMap<String, Student>();
	private ArrayList<String> studentsNames = new ArrayList<String>();
	private ArrayList<String> tasks = new ArrayList<String>();
	private HashMap<String, ArrayList<Student>> attendance = new HashMap<String, ArrayList<Student>>();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		
		makeHomePanel();
		makeBoardTab();
		makeMeetingTab();
		RootPanel.get("studentList").add(homePanel);
	}
	
	private void makeBoardTab() {
		makePasswordBox();
		makeMembersPanel();
		makeSchedulePanel();
		makeNotesPanel();
		
		pBoard.add(membersPanel, "Members");
		pBoard.add(schedulePanel, "Tasks");
		pBoard.add(notesPanel, "Notes");
		pBoard.selectTab(0);
		pBoard.setPixelSize(800, 800);
	}
	
	private void makeMeetingTab() {
		pMeeting.add(attendancePanel, "Attendance");
		pMeeting.add(groupsPanel, "Groups");
		pMeeting.selectTab(0);
		pMeeting.setPixelSize(800, 800);
		makeGroupPanel();
	}
	
	private void makeAttendancePanel() {
		attendancePanel.add(attendanceTextBox);
		attendancePanel.add(checkInButton);
		
		checkInButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				checkIn(attendanceTextBox.getText());
			}
		});
	}
	
	private void checkIn(String name) {
//		if(students.containsKey(name)) {
//			if()
//		}
	}
	
	private void makeGroupPanel() {
		groupNumLabel.setText("Number of groups: ");
		groupsPanel.add(groupNumLabel);
		groupsPanel.add(groupNumBox);
		groupsPanel.add(groupFlexTable);
		
		groupNumBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					groupNum = Integer.parseInt(groupNumBox.getText());
					
					int size = students.size()/groupNum;
					Random rand = new Random();
					ArrayList<String> temp = new ArrayList<String>();
					temp = studentsNames;
					for (int i = 0; i < groupNum; i++) {
						for (int k = 0; k < size; k++) {
							int x = rand.nextInt(temp.size()-1);
							groupFlexTable.setText(k, i, temp.get(x));
							temp.remove(x);
						}
					}
					
					for (int i = 0; i < temp.size(); i++) {
						groupFlexTable.setText(size, groupNum - 1, temp.get(i));
					}
					
					groupFlexTable.insertRow(0);
					for (int i = 0; i < groupNum; i++) {
						groupFlexTable.setText(0, i, "Group " + (i+1));
					}
				}
			}
		});
	}
	
	private void makePasswordBox() {
		boardPopUp.add(passwordPanel);
		passwordPanel.add(closePassword);
		passwordLabel.setText("Enter Password:");
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordBox);
		
		closePassword.setText("x");
		closePassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boardPopUp.hide();
			}
		});
		
		passwordBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					password = passwordBox.getText();
					
					if(password.equals("hello")) {
						RootPanel.get("studentList").add(pBoard);
						RootPanel.get("studentList").remove(homePanel);
						boardPopUp.hide();
					}
				}
			}
		});
	}
	
	private void makeHomePanel() {
		Grid g = new Grid(1,2);
		boardButton.setText("Board");
		g.setWidget(0, 0, boardButton);
		boardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boardPopUp.showRelativeTo(homePanel);
			}
		});
		
		meetingButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("studentList").add(pMeeting);
				RootPanel.get("studentList").remove(homePanel);
				boardPopUp.hide();
			}
		});
		
		meetingButton.setText("Meeting");
		g.setWidget(0, 1, meetingButton);
		homePanel.add(g);
	}

	private void makeMembersPanel() {
		studentsFlexTable.setText(0, 0, "Name");
		studentsFlexTable.setText(0, 1, "Email");
		studentsFlexTable.setText(0, 2, "Age");
		studentsFlexTable.setText(0, 3, "School");

		nameLabel.setText("Name:");
		addPanel.add(nameLabel);
		addPanel.add(nameTextBox);
		emailLabel.setText("Email:");
		addPanel.add(emailLabel);
		addPanel.add(emailTextBox);
		ageLabel.setText("Age:");
		addPanel.add(ageLabel);
		addPanel.add(ageTextBox);
		schoolLabel.setText("School:");
		addPanel.add(schoolLabel);
		addPanel.add(schoolTextBox);
		addPanel.add(addStudentButton);
		nameTextBox.setFocus(true);

		addStudentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addStudent();
			}
		});

		schoolTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addStudent();
				}
			}
		});

		membersPanel.add(addPanel);
		membersPanel.add(studentsFlexTable);
	}

	private void makeSchedulePanel() {
		tasksFlexTable.setText(0, 0, "Order");
		tasksFlexTable.setText(0, 1, "Task");
		tasksFlexTable.setText(0, 2, "Start Time");
		tasksFlexTable.setText(0, 3, "End Time");
		
		orderLabel.setText("Order:");
		taskPanel.add(orderLabel);
		taskPanel.add(orderBox);
		taskLabel.setText("Task:");
		taskPanel.add(taskLabel);
		taskPanel.add(taskBox);
		startLabel.setText("Start Time:");
		taskPanel.add(startLabel);
		taskPanel.add(startBox);
		endLabel.setText("End Time:");
		taskPanel.add(endLabel);
		taskPanel.add(endBox);
		taskPanel.add(addTaskButton);
		orderBox.setFocus(true);
		
		
		addTaskButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addTask();
			}
		});
		
		endBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addTask();
				}
			}
		});
		
		schedulePanel.add(taskPanel);
		schedulePanel.add(tasksFlexTable);
	}
	
	private void addTask() {
		final String task = taskBox.getText().toUpperCase().trim();
		nameTextBox.setFocus(true);

		if (tasks.contains(task))
			return;

		int row = studentsFlexTable.getRowCount();
		tasks.add(task);
		tasksFlexTable.setText(row, 0, orderBox.getText());
		tasksFlexTable.setText(row, 1, task);
		tasksFlexTable.setText(row, 2, startBox.getText());
		tasksFlexTable.setText(row, 3, endBox.getText());

		orderBox.setText("");
		taskBox.setText("");
		startBox.setText("");
		endBox.setText("");

		Button removeTaskButton = new Button("x");
		removeTaskButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int removedIndex = tasks.indexOf(task);
				tasks.remove(removedIndex);
				tasksFlexTable.removeRow(removedIndex + 1);
			}
		});
		tasksFlexTable.setWidget(row, 4, removeTaskButton);
	}
	
	private void makeNotesPanel() {
		notesFlexTable.setText(0, 0, "Date");
		notesFlexTable.setText(0, 1, "Notes");
		
		dateLabel.setText("Date:");
		notesEnterPanel.add(dateLabel);
		notesEnterPanel.add(dateBox);
		notesLabel.setText("Notes:");
		notesEnterPanel.add(notesLabel);
		notesEnterPanel.add(notesBox);
		
		notesBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addNote();
				}
			}
		});
		
		notesPanel.add(notesEnterPanel);
		notesPanel.add(notesFlexTable);
	}
	
	private void addNote() {
		//same as add student and add task
	}

	private void addStudent() {
		final String name = nameTextBox.getText().trim();
		nameTextBox.setFocus(true);

		if (students.containsKey(name))
			return;

		int row = studentsFlexTable.getRowCount();
		students.put(name, new Student(name));
		studentsNames.add(name);
		studentsFlexTable.setText(row, 0, name);
		studentsFlexTable.setText(row, 1, emailTextBox.getText());
		students.get(name).setEmail(emailTextBox.getText());
		studentsFlexTable.setText(row, 2, ageTextBox.getText());
		students.get(name).setAge(ageTextBox.getText());
		studentsFlexTable.setText(row, 3, schoolTextBox.getText());
		students.get(name).setSchool(schoolTextBox.getText());

		nameTextBox.setText("");
		emailTextBox.setText("");
		ageTextBox.setText("");
		schoolTextBox.setText("");

		Button removeStudentButton = new Button("x");
		removeStudentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int removedIndex = studentsNames.indexOf(name);
				studentsNames.remove(removedIndex);
				students.remove(name);
				studentsFlexTable.removeRow(removedIndex + 1);
			}
		});
		studentsFlexTable.setWidget(row, 4, removeStudentButton);
	}
}