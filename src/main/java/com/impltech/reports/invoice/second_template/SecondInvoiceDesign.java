package com.impltech.reports.invoice.second_template;

import com.impltech.reports.Templates;
import com.impltech.reports.reports_core.Utils;
import com.impltech.web.rest.util.ReportsUtils;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.RectangleBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import static com.impltech.reports.Templates.boldInvoiceStyle;
import static com.impltech.reports.Templates.invoiceStyle;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;


/**
 * Created by alex
 */
public class SecondInvoiceDesign {

    public static ComponentBuilder<?, ?> titleReportsComponent;
    public static ComponentBuilder<?, ?> secondTitleReportsComponent;
    public static ComponentBuilder<?, ?> secondTitleReportsComponent2;
    public static ComponentBuilder<?, ?> secondTitleReportsCustomerBill;
    public static ComponentBuilder<?, ?> secondTitleReportsCustomerBill1;
    public static ComponentBuilder<?, ?> secondTitleReportsShipping;
    public static ComponentBuilder<?, ?> secondTitleReportsCustomerShip1;
    public static ComponentBuilder<?, ?> secondTitleReportsCustomerShip2;
    public AggregationSubtotalBuilder<BigDecimal> totalSum;
    public SecondInvoiceData data2 = new SecondInvoiceData();
    public Utils utils = new Utils();

    public static void invoiceReportBuilder(String path) throws FileNotFoundException {
        SecondInvoiceDesign design = new SecondInvoiceDesign();
        try {
            JasperReportBuilder report = design.build();
            report.toPdf(new FileOutputStream(path));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    public JasperReportBuilder build() throws DRException {
        JasperReportBuilder report = report();

        StyleBuilder rectangleStyle = stl.style().setRadius(5);
        RectangleBuilder rectangleBackground = cmp.rectangle().setStyle(rectangleStyle);

        TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn()
            .setFixedColumns(2)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<Integer> quantityBoxColumn = col.column("Pieces", "boxQuantity", type.integerType())
            .setFixedWidth(49)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<String> boxTypeColumn = col.column("Box Type ", "boxTypes", type.stringType())
            .setFixedWidth(51)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        TextColumnBuilder<String> varietyColumn = col.column("Variety", "varietyTypes", type.stringType())
            .setFixedWidth(149);

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

        titleReportsComponent =
            cmp.horizontalList(
                cmp.image(ReportsUtils.getImagePath().toString())
                    .setHorizontalImageAlignment(HorizontalImageAlignment.LEFT));

        secondTitleReportsComponent =
            cmp.verticalList(

                cmp.text(data2.checkCompanyItems(data2.getSecondInvoice().getInvoiceNumber(), "INVOICE NUMBER:")
                    + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyName(), "\nINVOICE DATE:")
                    + "\nSHIPMENT DATE:\n"
                    + "\nCOMPANY NAME:\n"
                    + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyBrandName(), "\nBRAND NAME:")
                    + data2.checkCompanyItems(data2.getSecondInvoice().getFullName(), "\nCOORDINATION NAME:")
                    + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyEmail(), "\nEMAIL:")
                    + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyOfficePhone(), "\nOFFICE NUMBER:")
                    + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyMobilePhone(), "\nMOBILE NUMBER:"))
                    .setStyle(Templates.invoiceStyle10)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
                .setFixedWidth(110);

        secondTitleReportsComponent2 =
            cmp.verticalList(
                cmp.text(
                    data2.checkCompanyItems(data2.getSecondInvoice().getInvoiceNumber(), data2.getSecondInvoice().getInvoiceNumber())
                        + "\n" + data2.getSecondInvoice().getInvoiceDate() + "\n"
                        + "\n" + data2.getSecondInvoice().getShipmentDate() + "\n"
                        + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyName())
                        + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyBrandName())
                        + data2.checkCompanyItems(data2.getSecondInvoice().getFullName())
                        + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyEmail())
                        + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyOfficePhone())
                        + data2.checkCompanyItems(data2.getSecondInvoice().getCompanyMobilePhone()))
                    .setStyle(Templates.invoiceStyle10)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));

        secondTitleReportsCustomerBill =
            cmp.verticalList(
                cmp.text("BILL CUSTOMER")
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setStyle(boldInvoiceStyle));

        secondTitleReportsCustomerBill1 =
            cmp.horizontalList(
                cmp.text(data2.checkCustomerItems(data2.getSecondInvoice().getCustomerName(), " LABEL NAME:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCompanyName(), "\n COMPANY NAME:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCompanyName(), "\n COUNTRY:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCompanyName(), "\n CITY:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCompanyName(), "\n STREET:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCompanyName(), "\n TEL:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerID(), "\n ID:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerContactPersonName(), "\n CONTACT NAME:"))
                    .setFixedWidth(92)
                    .setStyle(invoiceStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),

                cmp.text(data2.checkCustomerItems(data2.getSecondInvoice().getCustomerName(), " " + data2.getSecondInvoice().getCustomerName())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCompanyName())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCountry())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerCity())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerStreet())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerPhone())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerID())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getCustomerContactPersonName()))
                    .setFixedWidth(185)
                    .setStyle(invoiceStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
                .setMinHeight(80);

        secondTitleReportsCustomerShip1 =
            cmp.verticalList(
                cmp.text("SHIP CUSTOMER")
                    .setStyle(boldInvoiceStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));

        secondTitleReportsCustomerShip2 =
            cmp.horizontalList(

                cmp.text(data2.checkCustomerItems(data2.getSecondInvoice().getCustomerLabel(), " COMPANY NAME:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelCountry(), "\n COUNTRY:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelCity(), "\n CITY:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelStreet(), "\n STREET:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelPhone(), "\n TEL:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelEmail(), "\n EMAIL:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelID(), "\n ID:")
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelContactPersonName(), "\n CONTACT NAME:"))
                    .setFixedWidth(92)
                    .setStyle(invoiceStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                ,

                cmp.text(data2.checkCustomerItems(data2.getSecondInvoice().getCustomerLabel(), " " + data2.getSecondInvoice().getCustomerLabel())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelCountry())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelCity())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelStreet())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelPhone())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelEmail())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelID())
                    + data2.checkCustomerItems(data2.getSecondInvoice().getLabelContactPersonName()))
                    .setStyle(invoiceStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
                .setMinHeight(80);

        secondTitleReportsShipping = cmp.horizontalList(
            cmp.text("CARGO AGENCY: " + data2.getSecondInvoice().getCargoAgency())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(invoiceStyle),
            cmp.text("AIRLINE: " + data2.getSecondInvoice().getAirline())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(invoiceStyle),
            cmp.text("AWB: " + data2.getSecondInvoice().getaWB())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(invoiceStyle),
            cmp.text("HAWB: " + data2.getSecondInvoice().gethAWB())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(invoiceStyle))
            .setBackgroundComponent(rectangleBackground)
            .setStyle(stl.style(5).setFontSize(8));

        VerticalListBuilder titleBC = cmp.verticalList()
            .add(secondTitleReportsCustomerBill, secondTitleReportsCustomerBill1)
            .setBackgroundComponent(rectangleBackground)
            .setFixedWidth(278);
        VerticalListBuilder titleSC = cmp.verticalList()
            .add(secondTitleReportsCustomerShip1, secondTitleReportsCustomerShip2)
            .setBackgroundComponent(rectangleBackground);
        HorizontalListBuilder tittle1 = cmp.horizontalList()
            .add(titleBC, cmp.horizontalGap(10), titleSC)
            .setStyle(stl.style(5));
        HorizontalListBuilder title00 = cmp.horizontalList()
            .add(secondTitleReportsComponent, secondTitleReportsComponent2)
            .setBackgroundComponent(rectangleBackground)
            .setFixedWidth(277);
        HorizontalListBuilder title0 = cmp.horizontalList()
            .add(titleReportsComponent, cmp.horizontalGap(10), title00)
            .setStyle(stl.style(5));
        HorizontalListBuilder summary = cmp.horizontalList()
            .add(utils.selectionCode(true, "12720164000074851"));
        report
            .setTemplate(Templates.reportTemplate)
            .columns(rowNumberColumn, quantityBoxColumn, boxTypeColumn, varietyColumn, varietyLengthColumn,
                stemsPerBunchColumn, bunchesPerBoxColumn, stemsPerBoxColumn, pricePerStemColumn, pricePerBoxColumn)
            .columnGrid(rowNumberColumn, quantityBoxColumn, boxTypeColumn, varietyColumn, varietyLengthColumn,
                stemsPerBunchColumn, bunchesPerBoxColumn, stemsPerBoxColumn, pricePerStemColumn, pricePerBoxColumn)
            .subtotalsAtSummary(
                sbt.text("Total: ", pricePerStemColumn),
                totalSum = sbt.sum(pricePerBoxColumn))
            .title(Templates.createTitleComponent2(title0, tittle1, secondTitleReportsShipping))
            .summary(
                cmp.verticalGap(15),
                summary)
            .pageFooter(Templates.secondFooterComponent)
            .setDataSource(data2.createDataSource());
        return report;
    }

}
