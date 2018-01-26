package com.impltech.web.rest.reports;

import com.impltech.reports.price_list.PriceListReport;
import com.impltech.security.SecurityUtils;
import com.impltech.service.dto.PriceListsWrapper;
import com.impltech.web.rest.util.ReportsUtils;
import com.impltech.web.rest.util.UploaderUtils;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by dima
 * REST controller for managing price list generation
 */
@RestController
@RequestMapping("/api")
public class ReportsResource {


    private ReportsUtils reportsUtils = new ReportsUtils();


    @GetMapping(value = "/downloadDispoPdf")
    public void downloadDispoPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + reportsUtils.getDispoFileName());

        reportsUtils.getDispoDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getDispoFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getDispoFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @GetMapping(value = "/showDispoPdf")
    public void showDispoPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + reportsUtils.getDispoFileName());

        reportsUtils.getDispoDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getDispoFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getDispoFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    @GetMapping(value = "/downloadBalancePdf")
    public void downloadBalancePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + reportsUtils.getBalanceFileName());

        reportsUtils.getBalanceDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getBalanceFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getBalanceFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @GetMapping(value = "/showBalancePdf")
    public void showBalancePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + reportsUtils.getBalanceFileName());

        reportsUtils.getBalanceDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getBalanceFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getBalanceFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    @GetMapping(value = "/downloadInvoicePdf")
    public void downloadInvoicePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + reportsUtils.getInvoiceFileName());

        reportsUtils.getInvoiceDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getInvoiceFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getInvoiceFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @GetMapping(value = "/showInvoicePdf")
    public void showInvoicePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + reportsUtils.getInvoiceFileName());

        reportsUtils.getInvoiceDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getInvoiceFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getInvoiceFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @PostMapping(value = "/price-lists/downloadPriceList")
    public ResponseEntity<String> downloadPriceListPdf(
        @RequestBody PriceListsWrapper priceLists,
        HttpServletResponse response) throws IOException, DRException {
        String companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId().toString();
        String userId = SecurityUtils.getCurrentCompanyUser().getId().toString();
        final String priceListType = priceLists.getPriceListType();

        File temporaryFolder = new File(ReportsUtils.getProjectPath() + reportsUtils.getPriceListReportPath().toString() + "/" + companyId + "/" + userId + "/");
        temporaryFolder.mkdirs();
        File priceListFile = new File(temporaryFolder.getPath() + reportsUtils.getPriceListFileName(priceLists.getPriceLists(), priceListType));

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + priceListFile.getName());
        try {
            PriceListReport priceListReport = reportsUtils.getPriceListReport();
            priceListReport.setPriceLists(priceLists.getPriceLists(), priceListType);
            if (priceLists.getExtension().equals(".xls")) {
                priceListReport.priceListReportXlsBuilder(priceListFile.getPath());
            } else {
                priceListReport.build().toPdf(new FileOutputStream(priceListFile));
            }

            IOUtils.copy(new FileInputStream(priceListFile), response.getOutputStream());
        } finally {
            priceListFile.delete();
            if (temporaryFolder.listFiles().length == 0) {
                temporaryFolder.delete();
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/downloadTiquetPdf")
    public void downloadTiquetPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + reportsUtils.getTiquetFileName());

        reportsUtils.getTiquetDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getTiquetFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getTiquetFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @GetMapping(value = "/showTiquetPdf")
    public void showTiquetPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + reportsUtils.getTiquetFileName());

        reportsUtils.getTiquetDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getTiquetFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getTiquetFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    @GetMapping(value = "/downloadVarietyListPdf")
    public void downloadVarietyListPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + reportsUtils.getVarietyListFileName());

        reportsUtils.getVarietyListDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getVarietyListFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getVarietyListFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @GetMapping(value = "/showVarietyListPdf")
    public void showVarietyListPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DRException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDir.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + reportsUtils.getVarietyListFileName());

        reportsUtils.getVarietyListDesign().build().toPdf(new FileOutputStream(temporaryFilePath + "/" + reportsUtils.getVarietyListFileName()));

        try (ByteArrayOutputStream baos = UploaderUtils.convertFileToByteArrayOutputStream(temporaryFilePath + "/" + reportsUtils.getVarietyListFileName());
             OutputStream os = response.getOutputStream()
        ) {
            baos.writeTo(os);
            baos.flush();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
