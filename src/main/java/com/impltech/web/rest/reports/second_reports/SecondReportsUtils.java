package com.impltech.web.rest.reports.second_reports;

import com.impltech.reports.invoice.InvoiceData;
import com.impltech.reports.invoice.second_template.SecondInvoiceData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static com.impltech.reports.reports_core.Extensions.getPdf;

/**
 * Created by alex
 */
public class SecondReportsUtils {

    private final static Path projectPath = Paths.get(".").toAbsolutePath().getParent();
    private final static Path pathToReportsFolder = Paths.get("/src/main/resources/reports/");
    private final static Path pathToBankDetailsFolder = Paths.get("src/main/resources/farms/miraflowers/uploaded-bank-details/");
    private static SecondInvoiceData secondInvoiceData = new SecondInvoiceData();
    private static InvoiceData dataInvoice = new InvoiceData();
    private static String secondDispoPathName = secondInvoiceData.getSecondInvoice().getCompanyName() + "_Dispo  " + LocalDate.now() + getPdf();
    private static String secondPriceListPathName = dataInvoice.getInvoice().getCompanyName() + "_Price List" + getPdf();
    private static String thirdPriceListPathName =  dataInvoice.getInvoice().getCompanyName() + "_All Price List" + getPdf();
    private static String secondVarietyListPathName = secondInvoiceData.getSecondInvoice().getCompanyName() + "_Variety List" + getPdf();
    private static String secondInvoicePathName = "Invoice_" + secondInvoiceData.getSecondInvoice().getInvoiceNumber() + getPdf();
    private static String secondBalancePathName = secondInvoiceData.getSecondInvoice().getCompanyName() + "_Balance" + getPdf();

    public static Path getProjectPath() {
        return projectPath;
    }

    public static String getSecondDispoPathName() {
        return secondDispoPathName;
    }

    public static String getSecondPriceListPathName() {
        return secondPriceListPathName;
    }

    public static Path getPathToReportsFolder() {
        return pathToReportsFolder;
    }
    public static Path getPathToBankDetailsFolder() {
        return pathToBankDetailsFolder;
    }

    public static String getThirdPriceListPathName() {
        return thirdPriceListPathName;
    }

    public static String getSecondVarietyListPathName() {
        return secondVarietyListPathName;
    }

    public static String getSecondInvoicePathName() {
        return secondInvoicePathName;
    }

    public static String getSecondBalancePathName() {
        return secondBalancePathName;
    }
}
