package com.impltech.reports;

import com.impltech.reports.invoice.InvoiceData;
import com.impltech.reports.invoice.second_template.SecondInvoiceData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static com.impltech.reports.reports_core.Extensions.getPdf;

/**
 * Created by dima
 */
public class ReportsTestUtils {

    static InvoiceData dataInvoice = new InvoiceData();
    static SecondInvoiceData secondInvoiceData = new SecondInvoiceData();

    private static final Path pathToTestReportsFolder = Paths.get("/src/test/resources/reports");
    private static String balancePathName = "/" + dataInvoice.getInvoice().getCustomerName() + "-Balance" + getPdf();
    private static String dispoPathName = "/" + dataInvoice.getInvoice().getCompanyName() + " Actual Dispo in HB " + LocalDate.now() + getPdf();
    private static String secondDispoPathName = "/" + secondInvoiceData.getSecondInvoice().getCompanyName() + "  Dispo  " + LocalDate.now() + getPdf();
    private static String invoicePathName = "/" + dataInvoice.getInvoice().getInvoiceNumber() + getPdf();
    private static String priceListPathName = "/" + "Actual " + dataInvoice.getInvoice().getCompanyName() + " Summer 2017 Price List" + getPdf();
    private static String secondPriceListPathName = "/" + dataInvoice.getInvoice().getCompanyName() + "  Price List" + getPdf();
    private static String thirdPriceListPathName = "/" + dataInvoice.getInvoice().getCompanyName() + "Third Price List" + getPdf();
    private static String fourthPriceListPathName = "/" + dataInvoice.getInvoice().getCompanyName() + "Fourth Price List" + getPdf();

    private static String tiquetPathName = "/" + dataInvoice.getInvoice().getCompanyName() + " " + dataInvoice.getInvoice().getCustomerLabel() + " tiquet" + getPdf();
    private static String varietyListPathName = "/" + dataInvoice.getInvoice().getCompanyName() + " Actual Variety List" + getPdf();
    private static String secondVarietyListPathName = "/" + secondInvoiceData.getSecondInvoice().getCompanyName() + "Variety List" + getPdf();
    private static String secondInvoicePathName = "/" + secondInvoiceData.getSecondInvoice().getInvoiceNumber() + getPdf();
    private static String secondBalancePathName = "/" + secondInvoiceData.getSecondInvoice().getCompanyName() + getPdf();

    private static String farmDataReportPathName = "/" + dataInvoice.getInvoice().getCompanyName() + getPdf();
    public static String getFarmDataReportPathName () { return farmDataReportPathName; }

    public static String getSecondVarietyListPathName() {
        return secondVarietyListPathName;
    }

    public static String getSecondDispoPathName() {
        return secondDispoPathName;
    }

    public static InvoiceData getDataInvoice() {
        return dataInvoice;
    }

    public static SecondInvoiceData getSecondInvoiceData() {
        return secondInvoiceData;
    }

    public static String getSecondPriceListPathName() {
        return secondPriceListPathName;
    }

    public static String getVarietyListPathName() {
        return varietyListPathName;
    }


    public static String getTiquetPathName() {
        return tiquetPathName;
    }

    public static Path getPathToTestReportsFolder() {
        return pathToTestReportsFolder;
    }


    public static String getBalancePathName() {
        return balancePathName;
    }

    public static String getDispoPathName() {
        return dispoPathName;
    }

    public static String getInvoicePathName() {
        return invoicePathName;
    }

    public static String getPriceListPathName() {
        return priceListPathName;
    }

    public static String getSecondInvoicePathName() {
        return secondInvoicePathName;
    }

    public static String getSecondBalancePathName() {
        return secondBalancePathName;
    }

    public static String getThirdPriceListPathName() {
        return thirdPriceListPathName;
    }
    public static String getFourthPriceListPathName() {
        return fourthPriceListPathName;
    }
}
