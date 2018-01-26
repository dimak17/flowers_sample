package com.impltech.reports.dispo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dima
 */
public class DispoReport {

	private String title;
	private List<DispoColumn> columns;
	private List<String> groups;
	private List<String> subtotals;
	private boolean showPageNumber;

	public DispoReport() {
		columns = new ArrayList<DispoColumn>();
		groups = new ArrayList<String>();
		subtotals = new ArrayList<String>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<DispoColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DispoColumn> columns) {
		this.columns = columns;
	}

	public void addColumn(DispoColumn column) {
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
