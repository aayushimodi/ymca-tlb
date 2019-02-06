package org.ymca.tvc.ymanage.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ymca.tvc.ymanage.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

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
}
