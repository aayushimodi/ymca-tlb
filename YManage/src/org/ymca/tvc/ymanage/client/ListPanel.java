package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class ListPanel extends CellTable<String> {

	//private CellTable<String> table;
	private ListDataProvider<String> dataProvider;
	
	public ListPanel(String colName) {
		this(colName, new ArrayList<String>());
	}
	
	public ListPanel(String colName, ArrayList<String> names) {
		super();
		this.createComponents(colName);
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
	
	private void createComponents(String colName) {	
		
		TextColumn<String> nameCol = new TextColumn<String>() {
			public String getValue(String row) {
				return row;
			}
		};

		this.addColumn(nameCol, colName);
		
		this.dataProvider = new ListDataProvider<String>();
		this.dataProvider.addDataDisplay(this);
	}
}
