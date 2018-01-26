package com.impltech.reports.dispo;

import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author dima
 */
public class DispoData {

    public JRDataSource createDataSource() {

        DRDataSource dataSource = new DRDataSource("boxType", "variety", "40", "50", "60", "70", "80", "90", "100",
            "110", "120", "150", "200");
        dataSource.add("HB", "Freedom", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Mondial", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Explorer", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Red Intuition", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Blush", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Imagination", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "High & Yellow Magic", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Payaso", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("HB", "Vendela", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);

        dataSource.add("QB", "Freedom", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("QB", "Mondial", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("QB", "Explorer", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);


        dataSource.add("Bunch", "Red Intuition", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("Bunch", "3D", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("Bunch", "Cabaret", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("Bunch", "Vendela", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);

        dataSource.add("Stems", "Freedom", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);
        dataSource.add("Stems", "High & Yellow Magic", 3, 5, 1, 2, 6, 8, 4, 4, 3, null, 1);

        return dataSource;
    }

    //For future!
//        /**
//         * Ingress Date into dispo report.
//         * @param year
//         * @param month
//         * @param day
//         * @return
//         */
//	private Date toDate(int year, int month, int day) {
//
//	    Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, year);
//		c.set(Calendar.MONTH, month - 1);
//		c.set(Calendar.DAY_OF_MONTH, day);
//		return c.getTime();
//	}

    public DispoReport getDynamicReport() {

        DispoReport report = new DispoReport();

        report.setTitle("Sales");

        report.addColumn(new DispoColumn("boxType", "boxType", "string"));
        report.addColumn(new DispoColumn("Variety", "variety", "string"));

        DispoColumn fourty = new DispoColumn("40", "40", "integer");
        fourty.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(fourty);

        DispoColumn fifty = new DispoColumn("50", "50", "integer");
        fifty.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(fifty);

        DispoColumn sixty = new DispoColumn("60", "60", "integer");
        sixty.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(sixty);

        DispoColumn seventy = new DispoColumn("70", "70", "integer");
        seventy.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(seventy);

        DispoColumn eighty = new DispoColumn("80", "80", "integer");
        eighty.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(eighty);

        DispoColumn ninety = new DispoColumn("90", "90", "integer");
        ninety.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(ninety);

        DispoColumn houndred = new DispoColumn("100", "100", "integer");
        houndred.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(houndred);

        DispoColumn houndredTen = new DispoColumn("110", "110", "integer");
        houndredTen.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(houndredTen);

        DispoColumn houndredTwenty = new DispoColumn("120", "120", "integer");
        houndredTwenty.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(houndredTwenty);

        DispoColumn houndredFifty = new DispoColumn("150", "150", "integer");
        houndredFifty.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(houndredFifty);

        DispoColumn twoHundred = new DispoColumn("200", "200", "integer");
        twoHundred.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        report.addColumn(twoHundred);

        report.addGroup("boxType");

        report.setShowPageNumber(true);

        return report;
    }

}
