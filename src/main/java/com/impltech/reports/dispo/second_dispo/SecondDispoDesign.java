package com.impltech.reports.dispo.second_dispo;

import com.impltech.reports.balance.second_balance.BalanceTemplates;
import com.impltech.reports.invoice.second_template.SecondInvoiceData;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;


public class SecondDispoDesign {
    public static ComponentBuilder<?, ?> titleReportsComponent;
    public static ComponentBuilder<?, ?> titleReportsComponent1;

    private SecondInvoiceData dataInvoice = new SecondInvoiceData();
    private SecondDispoData data = new SecondDispoData();

    public static void secondDispoReportBuilder(String path) throws FileNotFoundException {
        SecondDispoDesign design = new SecondDispoDesign();

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
                cmp.image(ReportsUtils.getImagePath().toString()).setFixedHeight(80));
        titleReportsComponent1 =
            cmp.horizontalList(
                cmp.text(dataInvoice.getSecondInvoice().getCompanyName() + "\n"
                    + LocalDate.now().toString() + "\n"
                    + dataInvoice.getSecondInvoice().getTypeOrder())
                    .setStyle(BalanceTemplates.boldheadStyle14))
                .setBackgroundComponent(BalanceTemplates.rectangleBackground);

        HorizontalListBuilder title = cmp.horizontalList()
            .add(titleReportsComponent, titleReportsComponent1);

        report.setTemplate(BalanceTemplates.reportTemplate);
        report.title(title, cmp.verticalGap(15),
            cmp.text("Availability").setStyle(BalanceTemplates.boldheadStyle14));

        SecondDispoReport dynamicReport = data.getDynamicReport();
        List<SecondDispoColumn> columns = dynamicReport.getColumns();
        Map<String, TextColumnBuilder> drColumns = new HashMap<String, TextColumnBuilder>();

        for (SecondDispoColumn column : columns) {
            TextColumnBuilder drColumn = col.column(column.getTitle(), column.getName(),
                (DRIDataType) type.detectType(column.getType()));
            if (column.getName().equals("Variety")) {
                drColumn.setFixedWidth(60);
            }
            drColumns.put(column.getName(), drColumn);
            report.columns(drColumn);
        }
        for (String group : dynamicReport.getGroups()) {
            ColumnGroupBuilder group2 = grp.group(drColumns.get(group));
            report.groupBy(group2);
        }
        report.pageFooter(cmp.pageXofY().setStyle(BalanceTemplates.footerStyle));
        report.setDataSource(data.createDataSource());

        return report;
    }
}
