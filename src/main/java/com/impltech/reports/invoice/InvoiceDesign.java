package com.impltech.reports.invoice;

import com.impltech.reports.Templates;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.RectangleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import static com.impltech.reports.Templates.bold18CenteredStyle;
import static com.impltech.reports.Templates.bold22CenteredStyle;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * @author dima
 */
public class InvoiceDesign {

	public InvoiceData data = new InvoiceData();

	public AggregationSubtotalBuilder<BigDecimal> totalSum;
    public static ComponentBuilder<?, ?> titleReportsComponent;
    public static ComponentBuilder<?, ?> companyAddressComponents;
    public static ComponentBuilder<?,?> companyContactsComponents;

    /**
     *
     * @return
     * @throws DRException
     */
	public JasperReportBuilder build() throws DRException {

		JasperReportBuilder report = report();

		//init styles
		StyleBuilder columnStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());

		StyleBuilder subtotalStyle = stl.style(columnStyle).bold();

		StyleBuilder shippingStyle = stl.style(Templates.boldStyle)
			.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        StyleBuilder rectangleStyle = stl.style().setRadius(5);

		//init columns
		TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn()
            .setFixedColumns(2)
			.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<Integer> quantityBoxColumn = col.column("Pieces", "boxQuantity", type.integerType())
            .setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

		TextColumnBuilder<String> boxTypeColumn = col.column("Box Type", "boxTypes", type.stringType())
            .setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<String> varietyColumn = col.column("Variety", "varietyTypes", type.stringType())
			.setFixedWidth(150);

        TextColumnBuilder<Integer> varietyLengthColumn = col.column("Length", "stemsLength", type.integerType())
			.setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

		TextColumnBuilder<Integer> stemsPerBunchColumn = col.column("Stems / Bunch", "stemsPerBunch", type.integerType())
			.setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<Integer> bunchesPerBoxColumn = col.column("Bunches / Box", "bunchesPerBox", type.integerType())
			.setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<Integer> stemsPerBoxColumn = col.column("Stems / Box", "stemsPerBox", type.integerType())
 			.setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<BigDecimal> pricePerStemColumn = col.column("Price / Stem", "pricePerStem", type.bigDecimalType())
			.setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<BigDecimal> pricePerBoxColumn = pricePerStemColumn.multiply(stemsPerBoxColumn)
			.setTitle("Price / Box")
            .setFixedWidth(50)
			.setDataType(Templates.currencyType);

        TextColumnBuilder<BigDecimal> boxCharge = col.column("Box Charge", "boxCharge", type.bigDecimalType())
			.setFixedWidth(50)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
            .setDataType(Templates.currencyType);

		TextColumnBuilder<BigDecimal> totalColumn = pricePerBoxColumn.add(boxCharge)
			.setTitle("Total Price")
			.setDataType(Templates.currencyType)
			.setRows(2)
			.setStyle(subtotalStyle);

    titleReportsComponent =
        cmp.horizontalList(
        cmp.image(ReportsUtils.getImagePath().toString())
            .setFixedDimension(200, 150)
            .setHorizontalImageAlignment(HorizontalImageAlignment.CENTER)
            .setPositionType(ComponentPositionType.FIX_RELATIVE_TO_BOTTOM),

        cmp.text(data.getInvoice().getCompanyName())
                .setFixedDimension(150, 150)
                .setStyle(bold22CenteredStyle)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),

        cmp.text("Invoice Number: " + data.getInvoice().getInvoiceNumber()
            + "\nInvoice date: " + data.getInvoice().getInvoiceDate()
            + "\nShipment date: " + data.getInvoice().getShipmentDate())
            .setStyle(bold18CenteredStyle.setFontSize(10))
            .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT));

    companyAddressComponents =
        cmp.horizontalList(
            cmp.text(data.getInvoice().getCompanyBrandName())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),

            cmp.text(data.getInvoice().getCompanyCountry())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),

            cmp.text(data.getInvoice().getCompanyCity())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),

            cmp.text(data.getInvoice().getCompanyStreet())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(Templates.bold18CenteredStyle.setFontSize(10)));

    companyContactsComponents =
            cmp.horizontalList(
                cmp.text(data.getInvoice().getCoordinationName())
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),

                cmp.text(data.getInvoice().getCompanyEmail())
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),

                cmp.text(data.getInvoice().getCompanyPhoneNumber())
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),

                cmp.text(data.getInvoice().getCompanyCelNumber())
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10)));


        RectangleBuilder rectangleBackground = cmp.rectangle()
			.setStyle(rectangleStyle);

        HorizontalListBuilder title1 = cmp.horizontalList()
            .add(
                cmp.text("Bill Customer: ")
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),
                cmp.text(
                    data.getInvoice().getCustomerName()
                            + "\n" +
                        data.getInvoice().getCustomerCountry()
                            + "\n" +
                        data.getInvoice().getCustomerCity()
                            + "\n" +
                        data.getInvoice().getCustomerStreet()
                            + "\n" +
                        data.getInvoice().getCustomerPhone()))
                    .setBackgroundComponent(rectangleBackground);


		HorizontalListBuilder title2 = cmp.horizontalList()
			.add(
                cmp.text("Ship Customer: ")
                    .setStyle(Templates.bold18CenteredStyle.setFontSize(10)),
                cmp.text(
                    data.getInvoice().getCustomerLabel()
                            + "\n" +
                        data.getInvoice().getLabelCountry()
                            + "\n" +
                        data.getInvoice().getLabelCity()
                            + "\n" +
                        data.getInvoice().getLabelPhone()
                            + "\n"))
			.setBackgroundComponent(rectangleBackground);

        HorizontalListBuilder title = cmp.horizontalList()
			.add(title1, cmp.horizontalGap(20), title2)
			.setStyle(stl.style(20));

        HorizontalListBuilder shipInfo = cmp.horizontalList()
            .add(
                cmp.text("Cargo Agency: " + data.getInvoice().getAgency()
                    + "\tAirline: " + data.getInvoice().getAirLine()
                    + "\tAWB: " + data.getInvoice().getAWB()
                    + "\tHAWB: " + data.getInvoice().getDaughterAWB())
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
            .setBackgroundComponent(rectangleBackground);

		report
			.setTemplate(Templates.reportTemplate)

			.setColumnStyle(columnStyle)

			.setSubtotalStyle(subtotalStyle)

			//columns
			.columns(rowNumberColumn, quantityBoxColumn, boxTypeColumn, varietyColumn, varietyLengthColumn,
                stemsPerBunchColumn, bunchesPerBoxColumn, stemsPerBoxColumn, pricePerStemColumn, pricePerBoxColumn)

			.columnGrid(rowNumberColumn, quantityBoxColumn, boxTypeColumn, varietyColumn, varietyLengthColumn,
                stemsPerBunchColumn, bunchesPerBoxColumn, stemsPerBoxColumn, pricePerStemColumn, pricePerBoxColumn)

			//subtotals
			.subtotalsAtSummary(
                                sbt.text("Total: ", pricePerStemColumn),
                                totalSum = sbt.sum(pricePerBoxColumn))

			//band components
			.title(
				Templates.createTitleComponent(titleReportsComponent, companyAddressComponents, companyContactsComponents),
				cmp.horizontalList().setStyle(stl.style(10)).setGap(50).add(cmp.verticalGap(10)))

            .pageHeader(shipInfo, title)

			.summary(cmp.horizontalList(
			    cmp.text("Payment terms: 30 days").setStyle(Templates.bold12CenteredStyle)),
				cmp.verticalGap(30),
				cmp.text("Thank you for your business").setStyle(Templates.bold12CenteredStyle))
			.setDataSource(data.createDataSource());

		return report;
	}

    /**
     *
     * @throws FileNotFoundException
     */
	public static void invoiceReportBuilder(String path) throws FileNotFoundException {

            InvoiceDesign design = new InvoiceDesign();

		try {
			JasperReportBuilder report = design.build();
			report.toPdf(new FileOutputStream(path));
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
