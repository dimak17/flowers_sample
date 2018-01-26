package com.impltech.reports.dispo.second_dispo;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author alex
 */
public class SecondDispoData {

    public JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("boxType", "Variety", "40", "50", "60", "70", "80", "90", "100",
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

    public SecondDispoReport getDynamicReport() {
        SecondDispoReport report = new SecondDispoReport();
        List<String> dispoList = new ArrayList<>(Arrays.asList("boxType", "Variety", "40", "50", "60", "70", "80", "90", "100",
            "110", "120", "150", "200"));
        for (String list : dispoList) {
            if (list.equals("boxType") || list.equals("Variety")) {
                report.addColumn(new SecondDispoColumn(list, list, "string"));
            } else {
                report.addColumn(new SecondDispoColumn(list, list, "integer"));
            }
        }
        report.addGroup("boxType");
        report.setShowPageNumber(true);
        return report;
    }

}
