package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Company;
import com.impltech.domain.Market;
import com.impltech.domain.TypeOfFlower;
import com.impltech.reports.farm_data_report.FarmDataReportDesign;
import com.impltech.security.SecurityUtils;
import com.impltech.service.CompanyService;
import com.impltech.service.MarketService;
import com.impltech.service.TypeOfFlowerService;
import com.impltech.validator.util.CompanyValidator;
import com.impltech.web.rest.request.FileUploadRequest;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.ImageUtil;
import com.impltech.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static com.impltech.reports.reports_core.Extensions.getPdf;

/**
 * @author platon
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "company";

    private final CompanyService companyService;
    private final MarketService marketService;
    private final TypeOfFlowerService typeOfFlowerService;

    public CompanyResource(CompanyService companyService, MarketService marketService, TypeOfFlowerService typeOfFlowerService) {
        this.companyService = companyService;
        this.marketService = marketService;
        this.typeOfFlowerService = typeOfFlowerService;

    }

    /**
     * POST  /companies : Create a new company.
     *
     * @param company the company to create
     * @return the ResponseEntity with status 201 (Created) and with body the new company, or with status 400 (Bad Request) if the company has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/companies")
    @Timed
    public ResponseEntity<Company> createCompany(@RequestBody Company company) throws URISyntaxException {
        log.debug("REST request to save Company : {}", company);
        if (company.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new company cannot already have an ID")).body(null);
        }
        if (CompanyValidator.checkCompanyLength(company)) {
            return ResponseEntity.status(400).build();
        }
        Company result = companyService.save(company);
        return ResponseEntity.created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /companies : Updates an existing company.
     *
     * @param company the company to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated company,
     * or with status 400 (Bad Request) if the company is not valid,
     * or with status 500 (Internal Server Error) if the company couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/companies")
    @Timed
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) throws URISyntaxException {
        log.debug("REST request to update Company : {}", company);
        if (company.getId() == null) {
            return createCompany(company);
        }
        if (CompanyValidator.checkCompanyLength(company)) {
            return ResponseEntity.status(400).build();
        }
        Company result = companyService.save(company);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, company.getId().toString()))
            .body(result);
    }

    /**
     * GET  /companies : get all the companies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companies in body
     */
    @GetMapping("/companies")
    @Timed
    public ResponseEntity<List<Company>> getAllCompanies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Companies");
        Page<Company> page = companyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /companies/:id : get the "id" company.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the company, or with status 404 (Not Found)
     */
    @GetMapping("/companies/current")
    @Timed
    public ResponseEntity<Company> getCurrentCompany() {
        log.debug("REST request to get Current Company : {}");
        Company company = SecurityUtils.getCurrentCompanyUser().getCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(company));
    }

    /**
     * DELETE  /companies/:id : delete the "id" company.
     *
     * @param id the id of the company to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/companies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @PostMapping("/companies/uploadCompanyImage")
    public ResponseEntity<String> handleFileUpload(@RequestBody FileUploadRequest req) {

        if (req != null) {
            ImageUtil.uploadFile(req, ImageUtil.getLogoPath());

            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "fileLength", "file length to big")).body(null);
            }
            return ResponseEntity.ok().build();
        }

    @GetMapping("/companies/default-image")
    public ResponseEntity<List<String>> getDefaultImage() {

        List<String> result = new ArrayList<>();

        File[] defaultList = ImageUtil.listDefaultFiles(ImageUtil.getLogoDir());
        try {
            return ImageUtil.getDefaultImage(result, defaultList);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/companies/image")
    public ResponseEntity<List<String>> getCompanyLogo(){
        List<String> result = new ArrayList<>();

        final Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();

        List<File> list = ImageUtil.listFilteredFiles(companyId, ImageUtil.getLogoDir());

        list = ImageUtil.numberingImageFileSorting(list);

        if (list.size() == 0 || !ImageUtil.checkIfExist(companyId, list)) {
            File[] defaultList = ImageUtil.listDefaultFiles(ImageUtil.getLogoDir());
            try {
                return ImageUtil.getDefaultImage(result, defaultList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return ImageUtil.getImage(result, list.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/companies/image-delete")
    @Timed
    public ResponseEntity<Void> deleteLogo() {

        final Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();

        if( companyId != null) {
            log.debug("REST request to delete Company image: {}", companyId);
            List<File> list = ImageUtil.listFilteredFiles(companyId, ImageUtil.getLogoDir());
            ImageUtil.deleteImage(companyId, list);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/companies/download")
    public ResponseEntity<Void> downloadPDF(HttpServletResponse response) {
        String companyName = SecurityUtils.getCurrentCompanyUser().getCompany().getFarmName();
        List<Market> markets = marketService.findAllByCurrentCompany();
        List<TypeOfFlower> typeOfFlowers = typeOfFlowerService.findTypeOfFlowersByCompany();
        Company company = SecurityUtils.getCurrentCompanyUser().getCompany();
        LocalDate currentDate = LocalDate.now();
        Path projectPath = Paths.get(".").toAbsolutePath().getParent();
        String path = (projectPath + "/src/test/resources/reports/" + companyName + " presentation list " + currentDate.toString() + getPdf());

        try {
            FarmDataReportDesign.farmDataReportBuilder(path, currentDate, markets, company, typeOfFlowers);
        }
        catch(FileNotFoundException e) {
        }

        File file = new File(path);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + companyName + "_Presentation List" + getPdf());
        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
        return ResponseEntity.ok().build();
    }
}
