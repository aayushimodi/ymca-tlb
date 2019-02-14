package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;

import org.ymca.tvc.ymanage.shared.MeetingAttendanceStatus;
import org.ymca.tvc.ymanage.shared.MeetingId;
import org.ymca.tvc.ymanage.shared.VolunteerInfo;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class PreviousMeetingsPanel extends DockLayoutPanel implements RefreshEnabledPanel {

	private final YManageServiceAsync yManageService;
	private StatusPanel statusPanel;
	private Label meetingIdLabel;
	private Label meetingDateLabel;
	private MeetingIdListPanel meetingIdListPanel;
	private SingleSelectionModel<MeetingId> meetingIdSelectionModel = new SingleSelectionModel<MeetingId>();
	private ListPanel volunteerNameListPanel;
	
	public PreviousMeetingsPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("90%");
		this.addStyleName("tvc-center-align");
		this.getPastMeetingIds();
	}

	private void createComponents() {
		SplitLayoutPanel s = new SplitLayoutPanel();
		s.addWest(createMeetingIdListPanel(), 150);
		s.add(createMeetingInfoPanel());
		s.getElement().setAttribute("cellpadding", "10");
		
		this.addSouth(createStatusPanel(), 5);
		this.add(s);
	}
	
	private Widget createMeetingIdListPanel() {
	
		meetingIdListPanel = new MeetingIdListPanel(" Meeting Id ");
		meetingIdListPanel.setStyleName("tvc-center-align");
		meetingIdListPanel.setSelectionModel(meetingIdSelectionModel);

		meetingIdSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				MeetingId id = meetingIdSelectionModel.getSelectedObject();
				if (id != null) {
					getPastCheckedInVolunteers(id);
				}
			}
		});
		
		ScrollPanel s = new ScrollPanel(meetingIdListPanel);
		s.setHeight("100%");
		s.setVerticalScrollPosition(0);
	
		return s;
	}
	
	private Widget createMeetingInfoPanel() {
		VerticalPanel v  = new VerticalPanel();
		v.getElement().setAttribute("cellpadding", "5");
		
		meetingIdLabel = new Label(getMeetingIdLabel(null));
		meetingIdLabel.setWidth("100%");
		meetingIdLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		v.add(meetingIdLabel);
		
		
		meetingDateLabel = new Label(getMeetingDateLabel(null));
		meetingDateLabel.setWidth("100%");
		meetingDateLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		v.add(meetingDateLabel);
		
		Label l3 = new Label(" ** Checked In Volunteers ** ");
		l3.setWidth("100%");
		l3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		v.add(l3);
		
		v.setWidth("100%");
		
		volunteerNameListPanel = new ListPanel(" Volunteer Name ");
		volunteerNameListPanel.setStyleName("tvc-center-align");	
		ScrollPanel s = new ScrollPanel(volunteerNameListPanel);
		s.setHeight("100%");
		s.setVerticalScrollPosition(0);
		v.add(s);
		
		v.setStyleName("tvc-center-align");
		
		return v;
	}
	
	private Widget createStatusPanel() {
		this.statusPanel = new StatusPanel();
		this.statusPanel.setWidth("100%");
		return this.statusPanel;
	}

	private String getMeetingIdLabel(MeetingId id) {
		if (id == null) {
			return " Meeting Id: ";
		} else {
			return " Meeting Id: " + id.getNum();
		}
	}
	
	private String getMeetingDateLabel(MeetingId id) {
		if (id == null) {
			return " Meeting Date: ";
		} else {
			return " Meeting Date: " + id.getDate().toString();
		}
	}
	
	private void getPastMeetingIds() {
		statusPanel.displayInfo("Getting past meeting ids");
		
		yManageService.getPastMeetingIds(new AsyncCallback<ArrayList<MeetingId>>() {

			@Override
			public void onFailure(Throwable caught) {

				statusPanel.displayError(caught);
			}

			@Override
			public void onSuccess(ArrayList<MeetingId> result) {
				processPastMeetingIds(result);
				statusPanel.clearDisplay();
			}
		});
	}
	
	private void processPastMeetingIds(ArrayList<MeetingId> result) {
		
		MeetingId currentSelected = meetingIdSelectionModel.getSelectedObject();
		MeetingId nextSelected = null;
		
		meetingIdListPanel.clearList();
		volunteerNameListPanel.clearList();
		
		
		for (MeetingId m : result) {
		
			if ((currentSelected != null) && (currentSelected.equals(m))) {
				nextSelected = currentSelected;
			}
			
			meetingIdListPanel.addToList(m);
		}
		
		if (nextSelected != null) {
			meetingIdSelectionModel.setSelected(nextSelected, true);
			getPastCheckedInVolunteers(nextSelected);
		}
	}
	
	private void getPastCheckedInVolunteers(MeetingId pastMeetingId) {
		
		this.meetingIdLabel.setText(this.getMeetingIdLabel(pastMeetingId));
		this.meetingDateLabel.setText(this.getMeetingDateLabel(pastMeetingId));
		
		yManageService.getCheckedInVolunteers(pastMeetingId, new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {

				statusPanel.displayError(caught);
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				volunteerNameListPanel.clearList();
				for (String s : result) {
					volunteerNameListPanel.addToList(s);
				}
				
				statusPanel.clearDisplay();
			}
		});
	}

	@Override
	public void refreshData() {
		this.getPastMeetingIds();
	}
}
