package org.ymca.tvc.ymanage.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class BoardAdminPanel extends DockLayoutPanel implements RefreshEnabledPanel {

	private final YManageServiceAsync yManageService;
	private static final String titleHTML = "<h2 align='center'>YMCA TVC Board Admin</h2>";

	private TabLayoutPanel tabPanel;
	private int refreshCallCount = 1;
	
	public BoardAdminPanel(YManageServiceAsync yManageService) {
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
		tabPanel = new TabLayoutPanel(2, Unit.EM);
		tabPanel.add(new VolunteerManagementPanel(yManageService), "Volunteers");
		tabPanel.add(new CurrentMeetingPanel(yManageService), "Current Meeting");
		tabPanel.add(new PreviousMeetingsPanel(yManageService), "Previous Meetings");
		tabPanel.selectTab(0);
		tabPanel.setWidth("75%");
		tabPanel.addStyleName("tvc-center-align");
		
		return tabPanel;
	}

	@Override
	public void refreshData() {
		
		if (refreshCallCount == 3) {
			refreshCallCount = 1;
		} else {
			refreshCallCount++;
			return;
		}
		
		int index = tabPanel.getSelectedIndex();
		if (index < 0) {
			return;
		}
		
		Widget w = tabPanel.getWidget(index);
		if (w instanceof RefreshEnabledPanel) {		
			((RefreshEnabledPanel)w).refreshData();
		}
	}
}