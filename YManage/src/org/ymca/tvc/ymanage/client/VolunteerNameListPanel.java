package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

public class VolunteerNameListPanel extends DockLayoutPanel {

	private CellTable<String> table;
	private ListDataProvider<String> dataProvider;
	
	public VolunteerNameListPanel() {
		super(Unit.EM);
		this.createComponents();
	}
	
	public VolunteerNameListPanel(ArrayList<String> names) {
		super(Unit.EM);
		this.createComponents();
		this.addToList(names);
	}
	
	public void clearList() {
		dataProvider.getList().clear();
	}
	
	public void addToList(ArrayList<String> names) {
		this.dataProvider.getList().addAll(names);
	}
	
	public void addToList(String name) {
		this.dataProvider.getList().add(name);
	}
	
	public void removeFromList(String name) {
		this.dataProvider.getList().remove(name);
	}
	
	private void createComponents() {	
		this.add(createListTable());
	}
	
	private Widget createListTable() {
		table = new CellTable<String>();
		TextColumn<String> nameCol = new TextColumn<String>() {
			public String getValue(String row) {
				return row;
			}
		};

		table.addColumn(nameCol, "Name");
		
		this.dataProvider = new ListDataProvider<String>();
		this.dataProvider.addDataDisplay(table);
		
		return table;
	}
}
