package com.impltech.reports.variety_list.second_variety_list;

import com.impltech.reports.balance.second_balance.BalanceTemplates;
import com.impltech.reports.invoice.second_template.SecondInvoiceData;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by alex
 */
public class SecondVarietlyDesign {

    public static ComponentBuilder<?, ?> titleReportsComponent;
    public static ComponentBuilder<?, ?> titleReportsComponent1;
    SecondVarietlyData data = new SecondVarietlyData();
    SecondInvoiceData invoiceData = new SecondInvoiceData();
    Calendar calendar = Calendar.getInstance();

    public static void secondVarietyListReportBuilder(String path) throws FileNotFoundException {

        SecondVarietlyDesign design = new SecondVarietlyDesign();
        try {
            JasperReportBuilder report = design.build();
            report.toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        titleReportsComponent =
            cmp.horizontalList(
                cmp.image(ReportsUtils.getImagePath().toString())
                    .setFixedHeight(80)
                    .setStyle(BalanceTemplates.imageStyle));

        titleReportsComponent1 = cmp.horizontalList(
            cmp.text(invoiceData.getSecondInvoice().getCompanyName() + "\n"
                + "Actual variety list "  + calendar.get(Calendar.YEAR) + "\n"
                + invoiceData.getSecondInvoice().getMarket())
                .setStyle(BalanceTemplates.boldHeadStyle14))
            .setBackgroundComponent(BalanceTemplates.rectangleBackground);

        HorizontalListBuilder title =  cmp.horizontalList()
            .add(titleReportsComponent, titleReportsComponent1);

        report
            .title(title, cmp.verticalGap(15))
            .setTemplate(BalanceTemplates.reportTemplate)
            .columns(col.column("Variety", "variety", type.stringType()))
            .setColumnStyle(BalanceTemplates.columnStyle9Left)
            .setDataSource(data.createDataSource());
        return report;
    }

}
