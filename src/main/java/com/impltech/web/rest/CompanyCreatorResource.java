package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.service.CompanyCreatorService;
import com.impltech.service.MailService;
import com.impltech.service.dto.CompanyCreatorDTO;
import com.impltech.web.rest.util.HeaderUtil;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * @author platon
 * REST controller for managing CompanyActivation.
 */
@RestController
@RequestMapping("/api")
public class CompanyCreatorResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "companyCreator";

    private final CompanyCreatorService companyCreatorService;

    public CompanyCreatorResource(CompanyCreatorService companyCreatorService, MailService mailService) {
        this.companyCreatorService = companyCreatorService;

    }

    /**
     * POST  /company-creator : Activate a new company.
     *
     * @param creator the company to create
     * @return the ResponseEntity with status 201 (Created) and with body the new company, or with status 400 (Bad Request) if the company has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-creator")
    @Timed
    public ResponseEntity<String> createCompanyCreator(@Valid @RequestBody CompanyCreatorDTO creator) throws URISyntaxException, JSONException {
        log.debug("REST request to activate Company : {}", creator);
        //TODO validation
        return companyCreatorService.save(creator)
            .map(user -> new ResponseEntity<String>(HttpStatus.CREATED))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /company-creator : Check activation key for requesting company creation page.
     *
     * @param key the key to get company activation page
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/company-creator-page")
    @Timed
    public ResponseEntity<String> getCompanyCreatorPage(@RequestParam(value = "key") String key) {
        log.debug("REST request to get company activation page by key: {}", key);
        if (key == null || !companyCreatorService.isActivationKeyExists(key)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("companyActivation", "keyExists", "Current activation key doesn't")).body(null);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * POST  /company-creator-email : Send activation email
     *
     * @param email the email to activate
     * @return the ResponseEntity with status 201 (Created) and with body the new company, or with status 400 (Bad Request) if the company has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-creator-email")
    @Timed
    public ResponseEntity<String> sendActivationMail(@RequestBody String email) throws URISyntaxException {
        log.debug("REST request to send activation email Company : {}", email);
        if (email == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emptyMail", "Email is empty")).body(null);
        }

        return companyCreatorService.sendActivationCompanyEmail(email)
            .map(activation -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
