package com.impltech.reports.dispo.second_dispo;

import java.util.ArrayList;
import java.util.List;


public class SecondDispoReport {

    private String title;
    private List<SecondDispoColumn> columns;
    private List<String> groups;
    private List<String> subtotals;
    private boolean showPageNumber;

    public SecondDispoReport() {
        columns = new ArrayList<>();
        groups = new ArrayList<>();
        subtotals = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SecondDispoColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SecondDispoColumn> columns) {
        this.columns = columns;
    }

    public void addColumn(SecondDispoColumn column) {
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
