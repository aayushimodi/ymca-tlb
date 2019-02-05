package org.ymca.tvc.ymanage.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.dom.client.Style.*;
import com.google.gwt.event.dom.client.*;

public class HomePanel extends DockLayoutPanel {

	private static final String titleHTML = "<h2 align='center'>Welcome YMCA Teen Volunteer Corps!</h2>";

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
		contentPanel.add(createButtonPanel());
		
		return contentPanel;
	}

	private Widget createButtonPanel() {
		
		Button checkInButton = new Button();
		checkInButton.setText("Volunteer Check In");
		checkInButton.setWidth("100%");
		checkInButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientUtility.goToHash("checkin");
			}
		});
		
		Button boardButton = new Button();
		boardButton.setText("Board Members");
		boardButton.setWidth("100%");
		boardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientUtility.goToHash("board");
			}	
		});
		
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, checkInButton);
		table.setWidget(1, 0, new HTML());
		table.setWidget(2, 0, new HTML());
		table.setWidget(3, 0, boardButton);
		
		table.setWidth("50%");
		table.addStyleName("tvc-center-align");
		
		
		return table;
	}
}
