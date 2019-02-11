package org.ymca.tvc.ymanage.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.MeetingAttendanceStatus;
import org.ymca.tvc.ymanage.shared.YException;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class MeetingManagementPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;

	private Label meetingLabel = new Label();
	private Button meetingButton = new Button();
	private StatusPanel statusPanel;

	public MeetingManagementPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("75%");
		this.addStyleName("tvc-center-align");
	}

	private void createComponents() {
		this.addSouth(createStatusPanel(), 5);
		this.addNorth(createStartEndMeetingPanel(), 5);
	}

	private Widget createStartEndMeetingPanel() {
		Grid g = new Grid(1, 3);

		yManageService.getCurrentMeeting(new AsyncCallback<MeetingAttendanceStatus>() {

			@Override
			public void onFailure(Throwable caught) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				processMeetingAttendanceStatus(result);				
			}
		});
		
		this.meetingButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(meetingButton.getText().equals("Start")) {
					yManageService.startMeeting(new AsyncCallback<MeetingAttendanceStatus>() {

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
						public void onSuccess(MeetingAttendanceStatus result) {
							processMeetingAttendanceStatus(result);
							
						}
					});
				} else {
					yManageService.endMeeting(new AsyncCallback<Void>() {

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
						public void onSuccess(Void result) {
							processMeetingAttendanceStatus(null);
						}
					});
				}
			}
		});

		g.setWidget(0, 0, meetingLabel);
		g.setWidget(0, 1, meetingButton);

		g.setWidth("50%");
		g.addStyleName("tvc-center-align");

		return g;
	}
	
	private void processMeetingAttendanceStatus(MeetingAttendanceStatus status) {
		if (status == null) {
			this.meetingLabel.setText("No meeting is in progress. Click `Start` button to start the meeting.");
			this.meetingButton.setText("Start");
		} else {
			this.meetingLabel.setText("The meeting " + status.getMeetingId().getNum() + " started at " + status.getMeetingId().getDate() + " is in progress. Click `End` button to end the meeting.");
			this.meetingButton.setText("End");
		}
	}
	
	private Widget createStatusPanel() {

		this.statusPanel = new StatusPanel();
		this.statusPanel.setWidth("75%");
		return this.statusPanel;
	}

}
