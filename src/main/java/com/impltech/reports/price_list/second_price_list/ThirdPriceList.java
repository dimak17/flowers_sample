package com.impltech.reports.price_list.second_price_list;

import com.impltech.reports.balance.second_balance.BalanceTemplates;
import com.impltech.reports.invoice.second_template.SecondInvoiceData;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by alex
 */
public class ThirdPriceList {

    public static ComponentBuilder<?, ?> titleReportsComponent;
    public static ComponentBuilder<?, ?> titleReportsComponent1;
    private SecondInvoiceData invoiceData = new SecondInvoiceData();

    public static void thirdPriceListReportBuilder(String path) throws FileNotFoundException {

        try {
            new ThirdPriceList().build().toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    public JasperReportBuilder build() throws FileNotFoundException {
        JasperReportBuilder report = DynamicReports.report();

        TextColumnBuilder<String> itemColumn = col.column("Variedad", "variety", type.stringType())
            .setFixedWidth(50);

        TextColumnBuilder<BigDecimal> thirtyColumn = col.column("30", "thirty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> fortyColumn = col.column("40", "fourty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> fiftyColumn = col.column("50", "fifty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> sixtyColumn = col.column("60", "sixty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> seventyColumn = col.column("70", "seventy", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> eightyColumn = col.column("80", "eighty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> ninetyColumn = col.column("90", "ninety", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredColumn = col.column("100", "houndred", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredTenColumn = col.column("110", "huendred_ten", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredTwentyColumn = col.column("120", "houndred_twenty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredThirtyColumn = col.column("130", "houndred_thirty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredFourtyColumn = col.column("140", "houndred_fourty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredFiftyColumn = col.column("150", "houndred_fifty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredSixtyColumn = col.column("160", "houndred_sixty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredSeventyColumn = col.column("170", "houndred_seventy", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredEightyColumn = col.column("180", "houndred_eighty", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> houndredNinetyColumn = col.column("190", "houndred_ninety", type.bigDecimalType());
        TextColumnBuilder<BigDecimal> twoHundredColumn = col.column("200", "two_hundred", type.bigDecimalType());


        titleReportsComponent =
            cmp.horizontalList(
                cmp.image(ReportsUtils.getImagePath().toString()).setFixedHeight(100));

        titleReportsComponent1 =
            cmp.horizontalList(
                cmp.text(invoiceData.getSecondInvoice().getCompanyName() + "\n"
                    + invoiceData.getSecondInvoice().getSeason() + " "
                    + invoiceData.getSecondInvoice().getStartSeason() + " - "
                    + invoiceData.getSecondInvoice().getEndSeason() +
                    "\nPrice List").setStyle(BalanceTemplates.boldHeadStyle14))
                .setBackgroundComponent(BalanceTemplates.rectangleBackground);


        HorizontalListBuilder title = cmp.horizontalList()
            .add(titleReportsComponent, titleReportsComponent1);


        report
            .setTemplate(BalanceTemplates.reportTemplate)

            .title(title,
                cmp.verticalGap(10))

            .columns(itemColumn, thirtyColumn, fortyColumn, fiftyColumn, sixtyColumn, seventyColumn, eightyColumn, ninetyColumn, houndredColumn, houndredTenColumn,
                houndredTwentyColumn, houndredThirtyColumn, houndredFourtyColumn, houndredFiftyColumn, houndredSixtyColumn, houndredSeventyColumn,
                houndredEightyColumn, houndredNinetyColumn, twoHundredColumn).setColumnStyle(BalanceTemplates.columnStyle8)

            .setDataSource(createThirdDataSource());

        return report;
    }


    private JRDataSource createThirdDataSource() {
        DRDataSource thirdDataSource = new DRDataSource("variety", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety",
            "houndred", "huendred_ten", "houndred_twenty", "houndred_thirty", "houndred_fourty", "houndred_fifty", "houndred_sixty", "houndred_seventy",
            "houndred_eighty", "houndred_ninety", "two_hundred");

        List<String> flowersName = new ArrayList<>(Arrays.asList("Freedom", "Explorer", "Mondial", "Vendela", "3D",
            "Iguana", "High & Magic", "Red Intuition"));

        for (String flowerName : flowersName) {
            thirdDataSource.add(flowerName, new BigDecimal(16), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15),
                new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15),
                new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15), new BigDecimal(15));

        }

        return thirdDataSource;
    }

}
