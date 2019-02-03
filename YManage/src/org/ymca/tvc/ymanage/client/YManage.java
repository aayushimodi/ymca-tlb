package org.ymca.tvc.ymanage.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YManage implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	private static final int REFRESH_INTERVAL = 5000; // 5 seconds

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final CheckinServiceAsync checkinService = GWT.create(CheckinService.class);

	private HomePanel homePanel;
	private VolunteerCheckinPanel checkinPanel;
	private BoardLoginPanel boardLoginPanel;
	private BoardAdminPanel boardAdminPanel;

	private Timer refreshTimer;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		homePanel = new HomePanel();
		checkinPanel = new VolunteerCheckinPanel(checkinService);
		boardLoginPanel = new BoardLoginPanel(checkinService);
		boardAdminPanel = new BoardAdminPanel(checkinService);
		
	
  	  	RootLayoutPanel.get().add(homePanel);
  	  	
  	  	selectMainPanel();
  	  	createRefreshTimer();
	}

	private void selectMainPanel() {

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();

				Logger logger = Logger.getLogger("");
				logger.log(Level.INFO, "History Token: " + historyToken);

				if (historyToken.equalsIgnoreCase("checkin")) {
					
				} else if (historyToken.equalsIgnoreCase("login")) {
				
					// add logic to go to board if logged in
				} else if (historyToken.equalsIgnoreCase("board")) {
					
					// add logic to go to login if not logged in 
				} else {
					
				}
			}
		});
	}
	
	private void createRefreshTimer() {
		
		refreshTimer = new Timer() {
	        @Override
	        public void run() {
	        	refreshData();
	        }
	      };
	      refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	}
	
	private void refreshData() {
		this.checkinPanel.refreshData();
	}
}
