package org.ymca.tvc.ymanage.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.*;

public class VolunteerGroupsPanel extends DockLayoutPanel {

	private final CheckinServiceAsync checkinService;
	private Label numLabel = new Label();
	private TextBox numBox = new TextBox();
	private Button numButton = new Button();
	private TabPanel groupTabs = new TabPanel();
	private int groupNum;

	public VolunteerGroupsPanel(CheckinServiceAsync checkinService) {
		super(Unit.EM);
		this.checkinService = checkinService;
		this.createComponents();
	}

	private void createComponents() {
		this.addNorth(createGroupNumPanel(), 5);
	}

	private Widget createGroupNumPanel() {
		this.numLabel.setText("Enter number of groups:");
		this.numButton.setText("Enter");
		
		this.numBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					groupNum = Integer.parseInt(numBox.getText());
					createViewGroupsPanel();
				}
			}
		});
		
		this.numButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				groupNum = Integer.parseInt(numBox.getText());
				createViewGroupsPanel();
			}
		});
		
		Grid g = new Grid(1,3);
		
		g.setWidget(0, 0, numLabel);
		g.setWidget(0, 1, numBox);
		g.setWidget(0, 2, numButton);
		
		g.setWidth("25%");
		g.addStyleName("tvc-center-align");
		
		return g;
	}
	
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
}
