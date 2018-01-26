package com.impltech.reports.variety_list.second_variety_list;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex
 */
public class SecondVarietlyData {

    public JRDataSource createDataSource() {
        List<String> variety = new ArrayList<>(Arrays.asList("Freedom", "Mondial", "Explorer", "Red Intuition",
            "Blush", "Imagination", "High & Yellow Magic", "Payaso", "Vendela"));

        DRDataSource dataSource = new DRDataSource("variety");

        for (String sort : variety) {
            dataSource.add(sort);
        }
        return dataSource;
    }
}
