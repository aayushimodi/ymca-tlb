package org.ymca.tvc.ymanage.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YManage implements EntryPoint {

	private static final int REFRESH_INTERVAL = 5000; // 5 seconds

	private final CheckinServiceAsync checkinService = GWT.create(CheckinService.class);

	private HomePanel homePanel;
	private VolunteerCheckinPanel checkinPanel;
	private BoardAdminPanel boardAdminPanel;

	private Timer refreshTimer;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		homePanel = new HomePanel();
		checkinPanel = new VolunteerCheckinPanel(checkinService);
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
					RootLayoutPanel.get().remove(0);
					RootLayoutPanel.get().add(checkinPanel);

				} else if (historyToken.equalsIgnoreCase("board")) {
					RootLayoutPanel.get().remove(0);
					RootLayoutPanel.get().add(boardAdminPanel);					
				} else {
					RootLayoutPanel.get().remove(0);
					RootLayoutPanel.get().add(homePanel);
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
