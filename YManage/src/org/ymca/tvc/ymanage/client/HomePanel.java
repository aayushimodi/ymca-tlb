package org.ymca.tvc.ymanage.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.dom.client.Style.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

public class HomePanel extends DockLayoutPanel {

	private static final String titleHTML = "<h2 align='center'>Welcome YMCA Teen Volunteer Corps!</h2>";
	private Button checkInButton = new Button();
	private Button boardButton = new Button();

	public HomePanel() {
		super(Unit.EM);
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
		contentPanel.addNorth(createButtonPanel(), 5);
		return contentPanel;
	}

	private Widget createButtonPanel() {
		FlexTable table = new FlexTable();
		checkInButton.setText("Check In");
		boardButton.setText("Board");
		table.setWidget(0, 0, checkInButton);
		table.setWidget(1, 0, boardButton);
			
		table.setWidth("50%");
		table.addStyleName("tvc-center-align");
		
		
		this.checkInButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
		
		this.boardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}	
		});
		return table;
	}
}
