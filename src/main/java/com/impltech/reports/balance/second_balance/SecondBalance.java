package com.impltech.reports.balance.second_balance;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex
 */
public class SecondBalance {

    private String title;
    private List<SecondBalanceColumn> columns;
    private List<String> groups;
    private List<String> subtotals;
    private boolean showPageNumber;


    public SecondBalance() {
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


    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getSubtotals() {
        return subtotals;
    }

    public void setSubtotals(List<String> subtotals) {
        this.subtotals = subtotals;
    }

    public boolean isShowPageNumber() {
        return showPageNumber;
    }

    public void setShowPageNumber(boolean showPageNumber) {
        this.showPageNumber = showPageNumber;
    }

    public List<SecondBalanceColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SecondBalanceColumn> columns) {
        this.columns = columns;
    }

    public void addSecondColumn(SecondBalanceColumn column) {
        this.columns.add(column);


    }

    public void addSecondSubtotal(String column) {
        this.subtotals.add(column);
    }
}
