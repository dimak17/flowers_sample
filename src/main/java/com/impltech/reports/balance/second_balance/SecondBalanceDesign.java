package com.impltech.reports.balance.second_balance;

import com.impltech.reports.invoice.second_template.SecondInvoiceData;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by alex
 */
public class SecondBalanceDesign {
    static SecondInvoiceData dataInvoice = new SecondInvoiceData();
    private static ComponentBuilder<?, ?> titleReportsComponent1;
    private static ComponentBuilder<?, ?> titleReportsComponent2;
    JRDataSource jrDataSource;
    private SecondBalanceData dataReport = new SecondBalanceData();

    public static SecondInvoiceData getSecondDataInvoice() {
        return dataInvoice;
    }

    public static void balanceReportBuilder(String path) throws FileNotFoundException {
        SecondBalanceDesign design = new SecondBalanceDesign();
        try {
            JasperReportBuilder report = design.build();
            report.toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        titleReportsComponent1 =
            cmp.horizontalList(
                cmp.image(ReportsUtils.getImagePath().toString()).setFixedDimension(140, 80).setStyle(BalanceTemplates.imageStyle),

                cmp.text(dataInvoice.getSecondInvoice().getCompanyName())
                    .setStyle(BalanceTemplates.boldHeadStyle14));

        titleReportsComponent2 = cmp.horizontalList(
            cmp.text("Estado de Cuenta: "
                + "\n" + dataInvoice.getSecondInvoice().getCustomerName()
                + "\nDate: " + Date.valueOf(LocalDate.of(2017, 05, 25))
                + "\nGenerado desde: " + dataInvoice.getSecondInvoice().getInvoiceDate())
                .setStyle(BalanceTemplates.leftHeadStyle))
            .setBackgroundComponent(cmp.rectangle().setRadius(5));

        HorizontalListBuilder header = cmp.horizontalList()
            .add(titleReportsComponent1, titleReportsComponent2)
            .setStyle(stl.style(5));
        report
            .setTemplate(BalanceTemplates.reportTemplate)
            .title(header, cmp.verticalGap(25));

        SecondBalance dynamicReport = dataReport.getDynamicReport();
        List<SecondBalanceColumn> columns = dynamicReport.getColumns();
        Map<String, TextColumnBuilder> drColumns = new HashMap<>();

        for (SecondBalanceColumn column : columns) {
            TextColumnBuilder drColumn = col.column(column.getTitle(), column.getName(), (DRIDataType) type.detectType(column.getType()));
            if (column.getPattern() != null)
                drColumn.setPattern(column.getPattern());
            if (column.getTitle().equals("Claims") || column.getTitle().equals("Fulls") || column.getTitle().equals("Stems")) {
                drColumn.setFixedWidth(40);
            }
            if (column.getTitle().equals("Payment")) {
                drColumn.setFixedWidth(55);
            }
            drColumns.put(column.getName(), drColumn);
            report.columns(drColumn);
        }

        jrDataSource = dataReport.createDataSource();
        report.subtotalsAtSummary(sbt.text(dataReport.totalInvoicePrice.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString(),
            drColumns.get(dynamicReport.getSubtotals().get(0))));

        report.addPageFooter(cmp.pageXofY().setStyle(BalanceTemplates.footerStyle));
        report.setDataSource(jrDataSource);
        return report;
    }
}
