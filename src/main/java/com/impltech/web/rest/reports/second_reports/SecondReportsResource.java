package com.impltech.web.rest.reports.second_reports;

import com.impltech.reports.balance.second_balance.SecondBalanceDesign;
import com.impltech.reports.dispo.second_dispo.SecondDispoDesign;
import com.impltech.reports.invoice.second_template.SecondInvoiceDesign;
import com.impltech.reports.price_list.second_price_list.SecondPriceList;
import com.impltech.reports.price_list.second_price_list.ThirdPriceList;
import com.impltech.reports.variety_list.second_variety_list.SecondVarietlyDesign;
import com.impltech.service.BankDetailsServiceSH;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by alex
 */
@RestController
@RequestMapping("/api")
public class SecondReportsResource {

    private final BankDetailsServiceSH bankDetailsService;

    public SecondReportsResource(BankDetailsServiceSH bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    private String path = SecondReportsUtils.getProjectPath().toString() + SecondReportsUtils.getPathToReportsFolder() + "/";

    @GetMapping(path = "/downloadSecondBalance")
    public void downloadSecondBalance(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondBalancePathName();
        SecondBalanceDesign.balanceReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + SecondReportsUtils.getSecondBalancePathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/showSecondBalance")
    public void showSecondBalance(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondBalancePathName();
        SecondBalanceDesign.balanceReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + SecondReportsUtils.getSecondBalancePathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/showSecondDispo")
    public void showSecondDispo(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondDispoPathName();
        SecondDispoDesign.secondDispoReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + SecondReportsUtils.getSecondDispoPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/downloadSecondDispo")
    public void downloadSecondDispo(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondDispoPathName();
        SecondDispoDesign.secondDispoReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + SecondReportsUtils.getSecondDispoPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/downloadSecondInvoice")
    public void downloadSecondInvoice(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondInvoicePathName();
        SecondInvoiceDesign.invoiceReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + SecondReportsUtils.getSecondInvoicePathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/showSecondInvoice")
    public void showSecondInvoice(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondInvoicePathName();
        SecondInvoiceDesign.invoiceReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + SecondReportsUtils.getSecondInvoicePathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/downloadSecondPriceList")
    public void downloadSecondPriceList(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondPriceListPathName();
        SecondPriceList.secondPriceListReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + SecondReportsUtils.getSecondPriceListPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/showSecondPriceList")
    public void showSecondPriceList(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondPriceListPathName();
        SecondPriceList.secondPriceListReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + SecondReportsUtils.getSecondPriceListPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/downloadThirdPriceList")
    public void downloadThirdPriceList(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getThirdPriceListPathName();
        ThirdPriceList.thirdPriceListReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + SecondReportsUtils.getThirdPriceListPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/showThirdPriceList")
    public void showThirdPriceList(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getThirdPriceListPathName();
        ThirdPriceList.thirdPriceListReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + SecondReportsUtils.getThirdPriceListPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/downloadSecondVarietyList")
    public void downloadSecondVarietyList(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondVarietyListPathName();
        SecondVarietlyDesign.secondVarietyListReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + SecondReportsUtils.getSecondVarietyListPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @GetMapping(path = "/showSecondVarietyList")
    public void showSecondVarietyList(HttpServletResponse response) throws IOException {
        String filePath = path + SecondReportsUtils.getSecondVarietyListPathName();
        SecondVarietlyDesign.secondVarietyListReportBuilder(filePath);
        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + SecondReportsUtils.getSecondVarietyListPathName());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
    }

}
