package com.impltech.reports.tiquet;

import com.impltech.reports.invoice.InvoiceData;
import com.impltech.reports.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.barcode.Code128BarcodeBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * @author dima
 */
public class TiquetDesign {

	private TiquetData data = new TiquetData();
	private StyleBuilder bold14Style;

    InvoiceData invData = new InvoiceData();

    /**
     *
     * @return
     * @throws DRException
     */
	public JasperReportBuilder build() throws DRException {

		JasperReportBuilder report = report();

		StyleBuilder textStyle = stl.style()
			.setFontSize(5);

		bold14Style = stl.style(Templates.boldStyle).setFontSize(5);

		Code128BarcodeBuilder shipToPostalCode = bcode.code128("12720164000074851")
			.setModuleWidth(3d)
			.setStyle(bold14Style);

		report
			.setTemplate(Templates.reportTemplate)
			.setPageFormat(125, 250, PageOrientation.PORTRAIT)
			.setTextStyle(textStyle)
			.title(
                cmp.text(data.customer.getFarmName()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                cmp.horizontalList().newRow(),
				cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("Product Type: " + data.customer.getFlowerType()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("AGROCALIDAD: " + data.customer.getAgroQuialityNumber()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("AWB: " + data.customer.getAWB()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("HAWB: " + data.customer.getHAWB()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("Label: " + data.customer.getLabel()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("Agency: " + data.customer.getAgency()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
                cmp.horizontalList().newRow(),
                cmp.text("Country: " + data.customer.getCountry()),
                cmp.horizontalList().newRow(),
                cmp.horizontalList().newRow().add(cmp.line()),
				cmp.horizontalList(createCustomerComponent("Box Number: ", data.customer)),
                cmp.horizontalList().newRow().add(cmp.line()),

				createCellComponent(shipToPostalCode));

		return report;
	}

    /**
     *
     * @param label
     * @param customer
     * @return
     */
	private ComponentBuilder<?, ?> createCustomerComponent(String label, Tiquet customer) {
		VerticalListBuilder content = cmp.verticalList(
            cmp.text(label).setStyle(stl.style().setFontSize(5)),
			cmp.text(customer.getLabelBoxId()).setStyle(stl.style().setFontSize(10)));

		return content;
	}

    /**
     *
     * @param content
     * @return
     */
	private ComponentBuilder<?, ?> createCellComponent( ComponentBuilder<?, ?> content) {
		VerticalListBuilder cell = cmp.verticalList(
			cmp.horizontalList(content.setStyle(bold14Style.setFontSize(15))));
//		cell.setStyle(stl.style(stl.pen(Float.MIN_VALUE, LineStyle.DOUBLE)));
		return cell;
	}

    /**
     *
     * @throws FileNotFoundException
     */
	public static void tiquetReportBuilder(String path) throws FileNotFoundException {
		TiquetDesign design = new TiquetDesign();
		try {
			JasperReportBuilder report = design.build();

			report.toPdf(new FileOutputStream(path));
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
