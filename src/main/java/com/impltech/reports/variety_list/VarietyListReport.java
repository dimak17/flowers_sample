package com.impltech.reports.variety_list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dima
 */
public class VarietyListReport {

	private String title;
	private List<VarietyListColumn> columns;
	private List<String> groups;
	private List<String> subtotals;
	private boolean showPageNumber;


	public VarietyListReport() {
		columns = new ArrayList<VarietyListColumn>();
		groups = new ArrayList<String>();
		subtotals = new ArrayList<String>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<VarietyListColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<VarietyListColumn> columns) {
		this.columns = columns;
	}

	public void addColumn(VarietyListColumn column) {
		this.columns.add(column);
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public void addGroup(String column) {
		this.groups.add(column);
	}

	public List<String> getSubtotals() {
		return subtotals;
	}

	public void setSubtotals(List<String> subtotals) {
		this.subtotals = subtotals;
	}

	public void addSubtotal(String column) {
		this.subtotals.add(column);
	}

	public boolean isShowPageNumber() {
		return showPageNumber;
	}

	public void setShowPageNumber(boolean showPageNumber) {
		this.showPageNumber = showPageNumber;
	}

}
