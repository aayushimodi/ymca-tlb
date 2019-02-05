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

	private TabPanel tabs = new TabPanel();

	private HorizontalPanel addPanel = new HorizontalPanel();
	CellTable<VolunteersTableRow> table = new CellTable<>();
	SingleSelectionModel<VolunteersTableRow> sm = new SingleSelectionModel<VolunteersTableRow>();
	private ListDataProvider<VolunteersTableRow> volunteersTableDataProvider;

	private VerticalPanel fieldPanel = new VerticalPanel();
	private TextBox nameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox ageTextBox = new TextBox();
	private TextBox schoolTextBox = new TextBox();
	private Label nameLabel = new Label();
	private Label emailLabel = new Label();
	private Label ageLabel = new Label();
	private Label schoolLabel = new Label();
	private Button addVolunteerButton = new Button("Add");

	private DockLayoutPanel groupsPanel = new DockLayoutPanel(Unit.EM);

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
		DockLayoutPanel contentPanel = new DockLayoutPanel(Unit.EM);
		tabs.add(createAddPanel(), "Volunteers");
		tabs.selectTab(0);
		contentPanel.add(tabs);
		return contentPanel;
	}

	private Widget createAddPanel() {
		addPanel.add(createFieldPanel());
		addPanel.add(createMembersTable());

		return addPanel;
	}

	private Widget createMembersTable() {
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

		table.addColumn(nameCol, "Name");
		table.addColumn(emailCol, "Email");
		table.addColumn(ageCol, "Age");
		table.addColumn(schoolCol, "School");

		this.volunteersTableDataProvider = new ListDataProvider<VolunteersTableRow>();
		this.volunteersTableDataProvider.addDataDisplay(table);

		table.setWidth("50%");
		table.addStyleName("tvc-center-align");
		
		table.setSelectionModel(sm);

		sm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				VolunteersTableRow row = sm.getSelectedObject();
				if (row != null) {
					List<VolunteersTableRow> list = volunteersTableDataProvider.getList();
					list.remove(row);
				}
			}
		});

		return table;
	}

	private Widget createFieldPanel() {
		nameLabel.setText("Name:");
		fieldPanel.add(nameLabel);
		fieldPanel.add(nameTextBox);
		emailLabel.setText("Email:");
		fieldPanel.add(emailLabel);
		fieldPanel.add(emailTextBox);
		ageLabel.setText("Age:");
		fieldPanel.add(ageLabel);
		fieldPanel.add(ageTextBox);
		schoolLabel.setText("School:");
		fieldPanel.add(schoolLabel);
		fieldPanel.add(schoolTextBox);
		fieldPanel.add(addVolunteerButton);
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

		return fieldPanel;
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
				// Show the RPC error message to the user

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
			}
		});
	}
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
