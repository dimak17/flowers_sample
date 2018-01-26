package com.impltech.reports.balance;

import com.impltech.reports.invoice.InvoiceDesign;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dima
 */
public class BalanceData {



    InvoiceDesign designData = new InvoiceDesign();


    String invoiceNumber = BalanceDesign.getDataInvoice().getInvoice().getInvoiceNumber();
    String customerLabel = BalanceDesign.getDataInvoice().getInvoice().getCustomerLabel();
    Date invoiceDate = BalanceDesign.getDataInvoice().getInvoice().getInvoiceDate();
    BigDecimal totalInvoicePrice =BalanceDesign.getDataInvoice().getInvoice().getTotalInvoicePrice();
    Integer totalStems = BalanceDesign.getDataInvoice().getInvoice().getTotalStems();
    Float totalFB = BalanceDesign.getDataInvoice().getInvoice().getTotalFB();

    Object space = null;
    BigDecimal claimExample = new BigDecimal(11.75);
    BigDecimal paymentExample = new BigDecimal(1592.30);

    BigDecimal sum = new BigDecimal(0);

    /**
     *
     * @return
     */
	public JRDataSource createDataSource() {


        DRDataSource dataSource = new DRDataSource
            ("inv_date", "inv_number", "label",       "stems",    "fb",  "claims", "payment" ,"inv_cost");

		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, claimExample.toString(), space,totalInvoicePrice.subtract(claimExample));
            sum.add(totalInvoicePrice.subtract(claimExample));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
        dataSource.add(invoiceDate, space, space, space, space, space, "Payment: ", sum.subtract(paymentExample));
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);
            sum.add(totalInvoicePrice);
		dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space,totalInvoicePrice);

		return dataSource;
	}

	//For future!
//    /**
//     *
//     * @param year
//     * @param month
//     * @param day
//     * @return
//     */
//	private Date toDate(int year, int month, int day) {
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, year);
//		c.set(Calendar.MONTH, month - 1);
//		c.set(Calendar.DAY_OF_MONTH, day);
//		return c.getTime();
//	}

    /**
     *
     * @return
     */
	public Balance getDynamicReport() {

        Balance report = new Balance();

	    report.setTitle(BalanceDesign.getDataInvoice().getInvoice().getCustomerName());

        BalanceColumn column = new BalanceColumn("Invoice Date", "inv_date", "date");
        column.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        BalanceColumn claimsColumn = new BalanceColumn("Claims", "claims", "string");
        claimsColumn.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        BalanceColumn paymentColumn = new BalanceColumn("Payment", "payment", "string");
        paymentColumn.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        report.addColumn(column);
        report.addColumn(new BalanceColumn("Invoice Number", "inv_number", "string"));
        report.addColumn(new BalanceColumn("Label", "label", "string"));
        report.addColumn(new BalanceColumn("Stems", "stems", "integer"));
        report.addColumn(new BalanceColumn("Fulls", "fb", "float"));
        report.addColumn(claimsColumn);
        report.addColumn(paymentColumn);
        column = new BalanceColumn("Total", "inv_cost", "bigDecimal");
        column.setPattern("#,###.0");
		report.addColumn(column);
        report.addSubtotal("inv_cost");
        report.setShowPageNumber(true);

    return report;
	}

}
