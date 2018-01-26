package com.impltech.reports.balance.second_balance;


import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by alex
 */
public class SecondBalanceData {


    String invoiceNumber = SecondBalanceDesign.getSecondDataInvoice().getSecondInvoice().getInvoiceNumber();
    String customerLabel = SecondBalanceDesign.getSecondDataInvoice().getSecondInvoice().getCustomerLabel();
    Date invoiceDate = SecondBalanceDesign.getSecondDataInvoice().getSecondInvoice().getInvoiceDate();
    BigDecimal InvoicePrice = SecondBalanceDesign.getSecondDataInvoice().getSecondInvoice().getTotalInvoicePrice();
    Integer totalStems = SecondBalanceDesign.getSecondDataInvoice().getSecondInvoice().getTotalStems();
    Float totalFB = SecondBalanceDesign.getSecondDataInvoice().getSecondInvoice().getTotalFB();

    Object space = null;
    BigDecimal claimExample = new BigDecimal(11.75);
    BigDecimal paymentExample = new BigDecimal(1592.30);
    BigDecimal sum = new BigDecimal(0);
    BigDecimal totalInvoicePrice = new BigDecimal(0);


    public JRDataSource createDataSource() {

        DRDataSource dataSource = new DRDataSource("inv_date", "inv_number", "label", "stems", "fb", "claims", "payment", "inv_sum", "inv_cost");
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, claimExample.toString(), space, InvoicePrice.subtract(claimExample), totalInvoicePrice.add(InvoicePrice.subtract(claimExample)));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, space, space, space, space, space, "Payment: ", sum.subtract(paymentExample), totalInvoicePrice = totalInvoicePrice.add(sum.subtract(paymentExample)));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        dataSource.add(invoiceDate, invoiceNumber, customerLabel, totalStems, totalFB, space, space, InvoicePrice, totalInvoicePrice = totalInvoicePrice.add(InvoicePrice));
        return dataSource;
    }

    public SecondBalance getDynamicReport() {

        SecondBalance balance = new SecondBalance();
        balance.addSecondColumn(new SecondBalanceColumn("Invoice\nDate", "inv_date", "date"));
        balance.addSecondColumn(new SecondBalanceColumn("Invoice\nNumber", "inv_number", "string"));
        balance.addSecondColumn(new SecondBalanceColumn("Label", "label", "string"));
        SecondBalanceColumn column3 = new SecondBalanceColumn("Stems", "stems", "integer");
        balance.addSecondColumn(column3);
        balance.addSecondColumn(new SecondBalanceColumn("Fulls", "fb", "float"));
        balance.addSecondColumn(new SecondBalanceColumn("Claims", "claims", "string"));
        balance.addSecondColumn(new SecondBalanceColumn("Payment", "payment", "string"));
        SecondBalanceColumn column2 = new SecondBalanceColumn("Invoice\nSum", "inv_sum", "bigDecimal");
        SecondBalanceColumn column = new SecondBalanceColumn("Total", "inv_cost", "bigDecimal");
        balance.addSecondColumn(column2);
        balance.addSecondColumn(column);
        balance.addSecondSubtotal("inv_cost");
        balance.setShowPageNumber(true);
        return balance;
    }

}
