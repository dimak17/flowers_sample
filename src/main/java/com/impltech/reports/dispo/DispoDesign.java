package com.impltech.reports.dispo;

import com.impltech.reports.invoice.InvoiceData;
import com.impltech.reports.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.PageXofYBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * @author dima
 */
public class DispoDesign {

    private InvoiceData dataInvoice = new InvoiceData();
	private DispoData data = new DispoData();

    public InvoiceData getDataInvoice() {
        return dataInvoice;
    }

    /**
     *
     * @return
     * @throws DRException
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JasperReportBuilder build() throws DRException {
		JasperReportBuilder report = report();

		report
			.setTemplate(Templates.reportTemplate);

		DispoReport dynamicReport = data.getDynamicReport();
		List<DispoColumn> columns = dynamicReport.getColumns();
		Map<String, TextColumnBuilder> drColumns = new HashMap<String, TextColumnBuilder>();

		for (DispoColumn column : columns) {
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
		report.setDataSource(data.createDataSource());

		return report;
	}

//    /**
//     *
//     * @throws FileNotFoundException
//     */
	public static void dispoReportBuilder(String path) throws FileNotFoundException {
		DispoDesign design = new DispoDesign();

		try {
			JasperReportBuilder report = design.build();
			report.toPdf(new FileOutputStream(path));
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
