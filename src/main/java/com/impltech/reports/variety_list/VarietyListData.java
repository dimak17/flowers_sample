package com.impltech.reports.variety_list;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author dima
 */
public class VarietyListData {

    /**
     *
     * @return
     */
	public JRDataSource createDataSource() {

		DRDataSource dataSource = new DRDataSource("variety");
		dataSource.add("Freedom");
		dataSource.add("Mondial");
		dataSource.add("Explorer");
		dataSource.add("Red Intuition");
		dataSource.add("Blush");
		dataSource.add("Imagination");
		dataSource.add("High & Yellow Magic");
		dataSource.add("Payaso");
		dataSource.add("Vendela");


		return dataSource;
	}


    /**
     *
     * @return
     */
	public VarietyListReport getDynamicReport() {

	    VarietyListReport report = new VarietyListReport();

		report.setTitle("Variety List");

        report.addColumn(new VarietyListColumn("Variety", "variety", "string"));

        report.setShowPageNumber(true);

    return report;
	}

}
