package org.ymca.tvc.ymanage.client;

import org.ymca.tvc.ymanage.shared.MeetingId;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PreviousMeetingsPanel extends DockLayoutPanel {

	private final YManageServiceAsync yManageService;
	private StatusPanel statusPanel;
	private Label meetingIdLabel;
	private Label meetingDateLabel;
	private ListPanel meetingIdListPanel;
	private ListPanel volunteerNameListPanel;
	
	public PreviousMeetingsPanel(YManageServiceAsync yManageService) {
		super(Unit.EM);
		this.yManageService = yManageService;
		this.createComponents();
		this.setWidth("90%");
		this.addStyleName("tvc-center-align");
		//getGroupNames();
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
	
		meetingIdListPanel = new ListPanel(" Meeting Id ");
		meetingIdListPanel.setStyleName("tvc-center-align");	
		ScrollPanel s = new ScrollPanel(meetingIdListPanel);
		s.setHeight("20em");
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
		
		Label l3 = new Label(" ** Checkedin Volunteers ** ");
		l3.setWidth("100%");
		l3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		v.add(l3);
		
		v.setWidth("100%");
		
		volunteerNameListPanel = new ListPanel(" Volunteer Name ");
		volunteerNameListPanel.setStyleName("tvc-center-align");	
		ScrollPanel s = new ScrollPanel(volunteerNameListPanel);
		s.setHeight("20em");
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
}
