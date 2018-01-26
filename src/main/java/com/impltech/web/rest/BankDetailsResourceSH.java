package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.BankDetails;
import com.impltech.security.SecurityUtils;
import com.impltech.service.BankDetailsServiceSH;
import com.impltech.web.rest.reports.second_reports.SecondReportsUtils;
import com.impltech.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static com.impltech.config.Constants.DOC_UPLOAD_EXT_REGEX;
import static com.impltech.reports.reports_core.Extensions.getPdf;
import static com.impltech.service.BankDetailsServiceSH.cleanDirectory;

/**
 * @author dima
 * REST controller for managing BankDetails.
 */
@RestController
@RequestMapping("/api")
public class BankDetailsResourceSH {

    private final Logger log = LoggerFactory.getLogger(BankDetailsResourceSH.class);

    private static final String ENTITY_NAME = "bankDetails";

    private final BankDetailsServiceSH bankDetailsService;

    public BankDetailsResourceSH(BankDetailsServiceSH bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    private final String PATH = SecondReportsUtils.getProjectPath().toString() + SecondReportsUtils.getPathToReportsFolder();
    private final String UPLOAD_FOLDER = SecondReportsUtils.getProjectPath().toString() + Paths.get("/src/main/resources/farms/miraflowers/uploaded-bank-details/");

    /**
     * POST  /bank-details : Create a new bankDetails.
     *
     * @param bankDetails the bankDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bankDetails, or with status 400 (Bad Request) if the bankDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bank-details")
    @Timed
    public ResponseEntity<BankDetails> createBankDetails(@RequestBody BankDetails bankDetails) throws URISyntaxException {
        log.debug("REST request to save BankDetails : {}", bankDetails);
        if (bankDetails.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bankDetails cannot already have an ID")).body(null);
        }

        if (bankDetails.getGeneral().length() > 500 || bankDetails.getAlternative().length() > 500) {
            return ResponseEntity.status(400).build();
        }

        BankDetails result = bankDetailsService.save(bankDetails);
        return ResponseEntity.created(new URI("/api/bank-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bank-details : Updates an existing bankDetails.
     *
     * @param bankDetails the bankDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid,
     * or with status 500 (Internal Server Error) if the bankDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bank-details")
    @Timed
    public ResponseEntity<BankDetails> updateBankDetails(@RequestBody BankDetails bankDetails) throws URISyntaxException {
        log.debug("REST request to update BankDetails : {}", bankDetails);
        if (bankDetails.getId() == null) {
            return createBankDetails(bankDetails);
        }

        if (bankDetails.getGeneral().length() > 500 || bankDetails.getAlternative().length() > 500) {
            return ResponseEntity.status(400).build();
        }
        BankDetails result = bankDetailsService.save(bankDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bankDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bank-details : get all the bankDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bankDetails in body
     */
    @GetMapping("/bank-details")
    @Timed
    public List<BankDetails> getAllBankDetails() {
        log.debug("REST request to get all BankDetails");
        return bankDetailsService.findAll();
    }

    /**
     * GET  /bank-details/:id : get the "id" bankDetails.
     *
     * @param id the id of the bankDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bankDetails, or with status 404 (Not Found)
     */
    @GetMapping("/bank-details/company/{id}")
    @Timed
    public ResponseEntity<BankDetails> getBankDetails(@PathVariable Long id) {
        log.debug("REST request to get BankDetails by id Company: {}", id);
        BankDetails bankDetails = bankDetailsService.findBankDetailsByCompanyId();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bankDetails));
    }

    @GetMapping("/bank-details/currentCompanyId")
    @Timed
    public ResponseEntity<BankDetails> getBankDetails() {
        log.debug("REST request to get BankDetails by id Company: {}");
        BankDetails bankDetails = bankDetailsService.findBankDetailsByCompanyId();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bankDetails));
    }

    /**
     * DELETE  /bank-details/:id : delete the "id" bankDetails.
     *
     * @param id the id of the bankDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bank-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
        log.debug("REST request to delete BankDetails : {}", id);
        bankDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /bank-details/:id to download bank-details file
     * @param id he id that we are getting from bank-details checkbox, to add the corresponding text from input
     * @param response set the file type, and file name
     * @return he ResponseEntity with status 200 (OK) and with body the bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid
     * @throws IOException
     */
    @GetMapping(path = "/bank-details/downloadBankDetails/{id}")
    @Timed
    public ResponseEntity<String> downloadCompanyBankDetails(@PathVariable Long id, HttpServletResponse response)
        throws IOException {
        String date = BankDetailsServiceSH.getCurrentDate();
        String companyName = SecurityUtils.getCurrentCompanyUser().getCompany().getFarmName();
        String filePath = PATH + companyName + "_Bank Details" + getPdf();
        if (!bankDetailsService.setReportDataByCheckbox(id, filePath, companyName, date)) {
            ResponseEntity.badRequest();
        }

        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + companyName + "_Bank Details" + getPdf());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
        return ResponseEntity.ok().build();
    }

    /**
     * GET /bank-details/: download uploaded bank-details
     * @param type: gets "general" or  "alternative" string from front to check which bank details to download
     * @param response set the file type, and file name
     * @return the ResponseEntity with status 200 (OK) and with body the bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid
     * @throws IOException
     */
    @GetMapping(path = "/bank-details/downloadUploadedBankDetails")
    public ResponseEntity<String> downloadUploadedBankDetails(@RequestParam String type, HttpServletResponse response)
        throws IOException {
        if (!type.equals("general") && !type.equals("alternative")) {
            return ResponseEntity.badRequest().build();
        }
        File file = null;
        if (type.equals("general")) {
            file = new File(UPLOAD_FOLDER + "/uploaded-general/").listFiles()[0];
            if (!file.exists()) {
                return ResponseEntity.badRequest().build();
            }

        } else if (type.equals("alternative")) {
            file = new File(UPLOAD_FOLDER + "/uploaded-alternative/").listFiles()[0];
            if (!file.exists()) {
                return ResponseEntity.badRequest().build();
            }
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * GET /bank-details/:id creates preview of bank-details file
     * @param id the id that we are getting from bank-details checkbox, to add the corresponding text from input
     * @param response set the file type, and file name
     * @return the ResponseEntity with status 200 (OK) and with body the bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid,
     * @throws IOException
     */
    @GetMapping(path = "/bank-details/showBankDetails/{id}")
    public ResponseEntity<String> showCompanyBankDetails(@PathVariable Long id, HttpServletResponse response)
        throws IOException {
        String date = BankDetailsServiceSH.getCurrentDate();
        String companyName = SecurityUtils.getCurrentCompanyUser().getCompany().getFarmName();
        String filePath = PATH + companyName + "_Bank Details" + getPdf();
        if (!bankDetailsService.setReportDataByCheckbox(id, filePath, companyName, date)) {
            ResponseEntity.badRequest();
        }

        File file = new File(filePath);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename=" + companyName + "_Bank Details" + getPdf());

        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
            return ResponseEntity.ok().build();
    }

    /**
     * POST /bank-details/ uploads bank-details
     * @param type gets "general" or  "alternative" string from front to check which bank details to upload
     * @param request
     * @return the ResponseEntity with status 200 (OK) and with body the bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid
     * @throws IOException
     * @throws ServletException
     */
    @PostMapping(value = "/bank-details/uploadBankDetails")
    public ResponseEntity<String> uploadCompanyBankDetails(@RequestParam String type, HttpServletRequest request)
        throws IOException, ServletException {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        Long fileSize = request.getPart("uploadFile").getSize();

        String nameCheckExtensions = request.getPart("uploadFile").getSubmittedFileName();

        if (!nameCheckExtensions.matches(DOC_UPLOAD_EXT_REGEX)) {
            return ResponseEntity.badRequest().build();
        }

        if (fileSize / 10 > (1024 * 1024)) {
            return ResponseEntity.badRequest().build();
        }

        if (!type.equals("general") && !type.equals("alternative")) {
            return ResponseEntity.badRequest().build();
        }

        File file = new File(UPLOAD_FOLDER);
        if (type.equals("general")) {
            file = new File(file + "/uploaded-general");
            cleanDirectory(file);
            if (!file.exists()) {
                file.mkdir();
            }

        } else if (type.equals("alternative")) {
            file = new File(file + "/uploaded-alternative");
            cleanDirectory(file);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        InputStream inputStream = request.getPart("uploadFile").getInputStream();
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        File result = new File(file + "/" + request.getPart("uploadFile").getSubmittedFileName());
        OutputStream outputStream = new FileOutputStream(result);
        outputStream.write(buffer);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE /bank-details/:id delete bank-details file
     * @param type gets "general" or "alternative" string from front to check which bank details to delete
     * @param id the id that we are getting to delete the corresponding file
     * @param request
     * @return he ResponseEntity with status 200 (OK) and with body the bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid
     * @throws IOException
     */
    @DeleteMapping(value = "/bank-details/deleteBankDetails/{id}")
    public ResponseEntity<String> deleteBankDetails(@RequestParam String type, @PathVariable Long id,
                                                    HttpServletRequest request) throws IOException {

        File file = new File(UPLOAD_FOLDER);
        if (type.equals("general")) {
            file = new File(file + "/uploaded-general");
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            } else if (!file.exists()) {
                return ResponseEntity.badRequest().build();
            }
        } else if (type.equals("alternative")) {
            file = new File(file + "/uploaded-alternative");
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            } else if (!file.exists()) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    /**
     * GET /bank-details/ display bank-details file name
     * @return he ResponseEntity with status 200 (OK) and with body the bankDetails,
     * or with status 400 (Bad Request) if the bankDetails is not valid
     * @throws IOException
     */
    @GetMapping(value = "/bank-details/showUploadedFileName")
    public ResponseEntity<Map> showUploadedFileName() throws IOException {
        File generalFile = new File(UPLOAD_FOLDER + "/uploaded-general");
        File alternativeFile = new File(UPLOAD_FOLDER + "/uploaded-alternative");

        if(generalFile.exists()) {
            generalFile = generalFile.listFiles()[0];
        }
        if(alternativeFile.exists()) {
            alternativeFile = alternativeFile.listFiles()[0];
        }
        Map fileNames = new HashMap();
        if (generalFile.length() > 0) {
            fileNames.put("general", generalFile.getName());
        }
        if (alternativeFile.length() > 0) {
            fileNames.put("alternative", alternativeFile.getName());
        } else if (!generalFile.exists() && !alternativeFile.exists()) {
            fileNames.put("absent", "");
        }
        return ResponseEntity.ok(fileNames);
    }
}
