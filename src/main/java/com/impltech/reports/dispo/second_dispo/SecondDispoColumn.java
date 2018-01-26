package com.impltech.reports.dispo.second_dispo;

/**
 * @author alex
 */
public class SecondDispoColumn {

    private String name;
    private String title;
    private String type;
    private String pattern;

    public SecondDispoColumn(String title, String name, String type) {
        this.name = name;
        this.type = type;
        this.title = title;
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


