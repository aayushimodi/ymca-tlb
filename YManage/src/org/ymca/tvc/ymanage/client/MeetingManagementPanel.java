package org.ymca.tvc.ymanage.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.MeetingAttendanceStatus;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class MeetingManagementPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;

	private Label meetingLabel = new Label();
	private Button meetingButton = new Button();

	public MeetingManagementPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("75%");
		this.addStyleName("tvc-center-align");
	}

	private void createComponents() {
		this.addNorth(startEndMeetingPanel(), 5);
	}

	private Widget startEndMeetingPanel() {
		Grid g = new Grid(1, 3);

		yManageService.getCheckinStatus(new AsyncCallback<MeetingAttendanceStatus>() {

			@Override
			public void onFailure(Throwable caught) {

				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Error" + caught.toString());
			}

			@Override
			public void onSuccess(MeetingAttendanceStatus result) {

				processMeetingAttendanceStatus(result);				
				//statusPanel.clearDisplay();
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
			this.meetingLabel.setText("No meeting in progress. Click to start meeting.");
			this.meetingButton.setText("Start");
		} else {
			this.meetingLabel.setText("Meeting in progress. Click to end meeting.");
			this.meetingButton.setText("End");
		}
	}

}
