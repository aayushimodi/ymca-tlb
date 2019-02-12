package org.ymca.tvc.ymanage.client;

import java.util.ArrayList;

import org.ymca.tvc.ymanage.shared.MeetingId;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class MeetingIdListPanel extends CellTable<MeetingId> {

	private ListDataProvider<MeetingId> dataProvider;
	
	public MeetingIdListPanel(String colName) {
		this(colName, new ArrayList<MeetingId>());
	}
	
	public MeetingIdListPanel(String colName, ArrayList<MeetingId> names) {
		super();
		this.createComponents(colName);
		this.addToList(names);
	}
	
	public void clearList() {
		dataProvider.getList().clear();
	}
	
	public void addToList(ArrayList<MeetingId> names) {
		this.dataProvider.getList().addAll(names);
	}
	
	public void addToList(MeetingId name) {
		this.dataProvider.getList().add(name);
	}
	
	public void removeFromList(MeetingId name) {
		this.dataProvider.getList().remove(name);
	}
	
	private void createComponents(String colName) {	
		
		TextColumn<MeetingId> nameCol = new TextColumn<MeetingId>() {
			public String getValue(MeetingId row) {
				return row.getNum() + "";
			}
		};

		this.addColumn(nameCol, colName);
		
		this.dataProvider = new ListDataProvider<MeetingId>();
		this.dataProvider.addDataDisplay(this);
	}
}
