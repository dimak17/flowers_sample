package com.impltech.reports.balance;

import com.impltech.reports.invoice.InvoiceData;
import com.impltech.reports.Templates;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.PageXofYBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.impltech.reports.Templates.bold18CenteredStyle;
import static com.impltech.reports.Templates.bold22CenteredStyle;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * @author dima
 */
public class BalanceDesign {

    static InvoiceData dataInvoice = new InvoiceData();


	private BalanceData dataReport = new BalanceData();
    public static ComponentBuilder<?, ?> titleReportsComponent;

    public static InvoiceData getDataInvoice() {
        return dataInvoice;
    }


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JasperReportBuilder build() throws DRException {
		JasperReportBuilder report = report();

        titleReportsComponent =
            cmp.horizontalList(
                cmp.image(ReportsUtils.getImagePath().toString())
                    .setFixedDimension(150, 100)
                    .setHorizontalImageAlignment(HorizontalImageAlignment.CENTER)
                    .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_BOTTOM),
                cmp.text(dataInvoice.getInvoice().getCompanyName())
                    .setStyle(bold22CenteredStyle.setFontSize(15))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                    .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_TOP),

                cmp.text("\nEstado de Cuenta: "
                    +"\nDate: " + Date.valueOf(LocalDate.of(2017, 02, 15))
                    +"\nGenerado desde: " + getDataInvoice().getInvoice().getInvoiceDate())
                    .setStyle(bold18CenteredStyle.setFontSize(10))
                    .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                    .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_TOP));

		report
			.setTemplate(Templates.reportTemplate)
			.title(titleReportsComponent);
//                            Templates.createTitleComponent("DynamicReport"));

		Balance dynamicReport = dataReport.getDynamicReport();
		List<BalanceColumn> columns = dynamicReport.getColumns();
		Map<String, TextColumnBuilder> drColumns = new HashMap<String, TextColumnBuilder>();

		for (BalanceColumn column : columns) {
			TextColumnBuilder drColumn = col.column(column.getTitle(), column.getName(), (DRIDataType) type.detectType(column.getType()));
			if (column.getPattern() != null) {
				drColumn.setPattern(column.getPattern());
			}
			if (column.getHorizontalTextAlignment() != null) {
				drColumn.setHorizontalTextAlignment(column.getHorizontalTextAlignment());
			}
			drColumns.put(column.getName(), drColumn);
			report.columns(drColumn);
		}

		for (String group : dynamicReport.getGroups()) {
			ColumnGroupBuilder group2 = grp.group(drColumns.get(group));
			report.groupBy(group2);

			for (String subtotal : dynamicReport.getSubtotals()) {
				report.subtotalsAtGroupFooter(group2, sbt.sum(drColumns.get(subtotal)));
			}
		}

		for (String subtotal : dynamicReport.getSubtotals()) {
			report.subtotalsAtSummary(sbt.sum(drColumns.get(subtotal)));
		}

		if (dynamicReport.getTitle() != null) {
			TextFieldBuilder<String> title = cmp.text(dynamicReport.getTitle())
				.setStyle(Templates.bold12CenteredStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
			report.addTitle(title);
		}
		if (dynamicReport.isShowPageNumber()) {
			PageXofYBuilder pageXofY = cmp.pageXofY()
				.setStyle(Templates.boldCenteredStyle);
			report.addPageFooter(pageXofY);
		}
		report.setDataSource(dataReport.createDataSource());

		return report;
	}

	public static void balanceReportBuilder( String path) throws FileNotFoundException {
		BalanceDesign design = new BalanceDesign();
		try {
			JasperReportBuilder report = design.build();
			report.toPdf(new FileOutputStream(path));
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
