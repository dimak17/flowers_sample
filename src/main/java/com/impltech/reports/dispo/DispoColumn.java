package com.impltech.reports.dispo;

import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;

/**
 * @author dima
 */
public class DispoColumn {

	private String name;
	private String title;
	private String type;
	private String pattern;
	private HorizontalTextAlignment horizontalTextAlignment;

    /**
     *
     * @param title
     * @param name
     * @param type
     */
	public DispoColumn(String title, String name, String type) {
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

	public HorizontalTextAlignment getHorizontalTextAlignment() {
		return horizontalTextAlignment;
	}

	public void setHorizontalTextAlignment(HorizontalTextAlignment horizontalTextAlignment) {
		this.horizontalTextAlignment = horizontalTextAlignment;
	}

}
