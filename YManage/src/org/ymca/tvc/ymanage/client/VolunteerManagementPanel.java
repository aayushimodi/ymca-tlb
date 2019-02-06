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

public class VolunteerManagementPanel extends DockLayoutPanel {

	private final CheckinServiceAsync checkinService;

	// table that shows the list of the volunteers
	private ListDataProvider<VolunteersTableRow> volunteersTableDataProvider;
	private CellTable<VolunteersTableRow> volunteersTable;
	private SingleSelectionModel<VolunteersTableRow> volunteersTableSelectionModel;

	// add or edit volunteer information
	private TextBox nameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox ageTextBox = new TextBox();
	private TextBox schoolTextBox = new TextBox();
	private Button addVolunteerButton = new Button("Add");

	private StatusPanel statusPanel;

	public VolunteerManagementPanel(CheckinServiceAsync checkinService) {
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
		this.setWidth("75%");
		this.addStyleName("tvc-center-align");
	}

	private void createComponents() {

		this.addSouth(createStatusPanel(), 5);

		// DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		this.addWest(createAddEditPanel(), 18);
		this.add(createVolunteerTable());
		// this.add(p);
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

		addEditPanel.setWidget(5, 0, new Label(" "));
		addEditPanel.setWidget(5, 1, addVolunteerButton);

		nameTextBox.setFocus(true);

		addVolunteerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addVolunteer();
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

		TextColumn<VolunteersTableRow> nameCol = new TextColumn<VolunteersTableRow>() {
			public String getValue(VolunteersTableRow row) {
				return row.name;
			}
		};

		TextColumn<VolunteersTableRow> emailCol = new TextColumn<VolunteersTableRow>() {
			public String getValue(VolunteersTableRow row) {
				return row.email;
			}
		};

		TextColumn<VolunteersTableRow> ageCol = new TextColumn<VolunteersTableRow>() {
			public String getValue(VolunteersTableRow row) {
				return row.age;
			}
		};

		TextColumn<VolunteersTableRow> schoolCol = new TextColumn<VolunteersTableRow>() {
			public String getValue(VolunteersTableRow row) {
				return row.school;
			}
		};

		volunteersTable = new CellTable<>();
		volunteersTable.addColumn(nameCol, "Name");
		volunteersTable.addColumn(emailCol, "Email");
		volunteersTable.addColumn(ageCol, "Age");
		volunteersTable.addColumn(schoolCol, "School");

		volunteersTableDataProvider = new ListDataProvider<VolunteersTableRow>();
		volunteersTableDataProvider.addDataDisplay(volunteersTable);

		volunteersTable.setWidth("100%");
		volunteersTable.addStyleName("tvc-center-align");

		volunteersTableSelectionModel = new SingleSelectionModel<VolunteersTableRow>();
		volunteersTable.setSelectionModel(volunteersTableSelectionModel);

		volunteersTableSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				VolunteersTableRow row = volunteersTableSelectionModel.getSelectedObject();
				if (row != null) {
					List<VolunteersTableRow> list = volunteersTableDataProvider.getList();
					list.remove(row);
				}
			}
		});

		ScrollPanel s = new ScrollPanel(volunteersTable);
		s.setHeight("20em");
		s.setVerticalScrollPosition(0);

		return s;
	}

	private Widget createStatusPanel() {

		this.statusPanel = new StatusPanel();
		this.statusPanel.setWidth("75%");
		return this.statusPanel;
	}

	private void addVolunteer() {
		final String name = nameTextBox.getText().trim();
		final String email = emailTextBox.getText();
		final String age = ageTextBox.getText();
		final String school = schoolTextBox.getText();
		nameTextBox.setFocus(true);

		Volunteer v = new Volunteer(name);
		v.setEmail(email);
		v.setAge(age);
		v.setSchool(school);

		checkinService.addVolunteer(v, new AsyncCallback<Void>() {

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
				logger.log(Level.INFO, name + " added");

				List<VolunteersTableRow> list = volunteersTableDataProvider.getList();
				VolunteersTableRow row = new VolunteersTableRow(name, email, age, school);
				list.add(row);

				nameTextBox.setText("");
				emailTextBox.setText("");
				ageTextBox.setText("");
				schoolTextBox.setText("");
				
				statusPanel.clearDisplay();
			}
		});
	}

	class VolunteersTableRow {
		public String name;
		public String email;
		public String age;
		public String school;

		public VolunteersTableRow(String name, String email, String age, String school) {
			this.name = name;
			this.email = email;
			this.age = age;
			this.school = school;
		}
	}
}
