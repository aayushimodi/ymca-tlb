package org.ymca.tvc.ymanage.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

public class BoardLoginPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;
	private static final String titleHTML = "<h2 align='center'>YMCA TVC Board Login</h2>";
	
	private PasswordTextBox passwordBox = new PasswordTextBox();
	private Label passwordLabel = new Label();
	String password;
	private Boolean isLoggedIn = false;
	
	public BoardLoginPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
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
		contentPanel.addNorth(createPasswordPanel(), 5);
		return contentPanel;
	}
	
	private Widget createPasswordPanel() {
		FlexTable table = new FlexTable();
		passwordLabel.setText("Enter Password:");
		
		table.setWidget(0, 0, this.passwordLabel);
		table.setWidget(1, 0, this.passwordBox);
		
		table.setWidth("50%");
		table.addStyleName("tvc-center-align");

		
		passwordBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					password = passwordBox.getText();
					
					if(password.equals("ymca")) {
						
					} else {
						//ERROR
					}
				}
			}
		});
		
		return table;
	}
	
	public Boolean getisLoggedIn() {
		return isLoggedIn;
	}
}
