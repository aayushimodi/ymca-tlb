package org.ymca.tvc.ymanage.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.YException;

import com.google.gwt.user.client.ui.*;

public class StatusPanel extends Grid {

	private HTML statusLabel;
	
	public StatusPanel() {
		super(1,1);
		this.createComponents();
	}

	private void createComponents() {
	
		this.statusLabel = new HTML();
		this.statusLabel.setWidth("100%");
		this.statusLabel.addStyleName("tvc-status-bar");
		
		this.setWidget(0, 0, this.statusLabel);
		this.setWidth("50%");
		this.addStyleName("tvc-center-align");
	}

	void clearDisplay() {
		this.statusLabel.setHTML("");
	}
	
	void displayInfo(String message) {
		this.statusLabel.setHTML(
				//"<span style=\"color:black\">" + new Date().toString() + ": </span>" +
				"<span style=\"color:black\">" + message + "</span>");
		
	}
	
	void displayError(String message) {
		this.statusLabel.setHTML(
				//"<span style=\"color:black\">" + new Date().toString() + ": </span>" +
				"<span style=\"color:red\">" + message + "</span>");
	}
	
	void displayError(Throwable caught) {

		Logger logger = Logger.getLogger("");
		logger.log(Level.SEVERE, "Error" + caught.toString());
		
		if(caught.getClass().equals(YException.class)) {
			this.displayError(caught.getMessage());
		} else {
			this.displayError("Error in getting information from the server, try again later!");
		}
		
	}
}
