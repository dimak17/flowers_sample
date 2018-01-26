package com.impltech.reports.balance.second_balance;


/**
 * Created by alex
 */
public class SecondBalanceColumn {

    private String name;
    private String title;
    private String type;
    private String pattern;


    public SecondBalanceColumn(String title, String name, String type) {
        this.title = title;
        this.name = name;
        this.type = type;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
