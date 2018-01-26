package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.CompanyUser;
import com.impltech.security.SecurityUtils;
import com.impltech.service.CompanyUserService;
import com.impltech.service.UserService;
import com.impltech.validator.util.CompanyUserValidator;
import com.impltech.web.rest.request.FileUploadRequest;
import com.impltech.web.rest.request.PasswordChangeRequest;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.ImageUtil;
import com.impltech.web.rest.util.PaginationUtil;
import com.impltech.web.rest.util.UploaderUtils;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author platon
 * REST controller for managing CompanyUser.
 */
@RestController
@RequestMapping("/api")
public class CompanyUserResource {

    private final Logger log = LoggerFactory.getLogger(CompanyUserResource.class);

    private static final String ENTITY_NAME = "companyUser";

    private final CompanyUserService companyUserService;
    private final UserService userService;

    public CompanyUserResource(CompanyUserService companyUserService, UserService userService) {
        this.companyUserService = companyUserService;
        this.userService = userService;

    }

    /**
     * POST  /company-users : Create a new companyUser.
     *
     * @param companyUser the companyUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyUser, or with status 400 (Bad Request) if the companyUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-users")
    @Timed
    public ResponseEntity<CompanyUser> createCompanyUser(@RequestBody CompanyUser companyUser) throws URISyntaxException {
        log.debug("REST request to save CompanyUser : {}", companyUser);
        if (CompanyUserValidator.checkForExistingLogin(companyUserService, companyUser)) {
            return  ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }
        if (companyUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyUser cannot already have an ID")).body(null);
        }
        if (CompanyUserValidator.checkLengthValidation(companyUser)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        CompanyUser result = companyUserService.save(companyUser);

        return ResponseEntity.created(new URI("/api/company-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-users : Updates an existing companyUser.
     *
     * @param companyUser the companyUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyUser,
     * or with status 400 (Bad Request) if the companyUser is not valid,
     * or with status 500 (Internal Server Error) if the companyUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-users")
    @Timed
    public ResponseEntity<CompanyUser> updateCompanyUser(@RequestBody CompanyUser companyUser) throws URISyntaxException {
        log.debug("REST request to update CompanyUser : {}", companyUser);
        if (companyUser.getId() == null) {
            return createCompanyUser(companyUser);
        }
        if (CompanyUserValidator.checkLengthValidation(companyUser)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        CompanyUser result = companyUserService.update(companyUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-users : get all the companyUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyUsers in body
     */
    @GetMapping("/company-users")
    @Timed
    public ResponseEntity<List<CompanyUser>> getAllCompanyUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompanyUsers");
        Page<CompanyUser> page = companyUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-users/:id : get the "id" companyUser.
     *
     * @param id the id of the companyUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyUser, or with status 404 (Not Found)
     */
    @GetMapping("/company-users/{id}")
    @Timed
    public ResponseEntity<CompanyUser> getCompanyUser(@PathVariable Long id) {
        log.debug("REST request to get CompanyUser : {}", id);
        CompanyUser companyUser = companyUserService.findOneByCurrentCompany(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyUser));
    }

    @GetMapping("/company-users/company")
    @Timed
    public ResponseEntity<List<CompanyUser>> getAllCompanyUsersByCurrentCompany() {
        log.debug("REST request to get all CompanyUser by current company");
        List<CompanyUser> companyUsers = companyUserService.findAllByCurrentCompanyId();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyUsers));
    }

    /**
     * DELETE  /company-users/:id : delete the "id" companyUser.
     *
     * @param id the id of the companyUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyUser(@PathVariable Long id) {
        log.debug("REST request to delete CompanyUser : {}", id);
        companyUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * DELETE  /company-users/:id/image : delete the "image" of  pro.
     *
     * @param id the id of the variety to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-users/{id}/image-delete")
    @Timed
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {

        if( id != null) {
            log.debug("REST request to delete Profile Info Image : {}", id);

            FileFilter fileFilter = pathname -> pathname.getName().startsWith(companyUserService.findOne(id).getId().toString());

            File imageDir = ImageUtil.getCompanyUserImageDir();

            File[] list = imageDir.listFiles(fileFilter);

            if (list != null || list.length != 0) {
                for (int i = 0; i < list.length; i++) {
                    for (String extension : UploaderUtils.EXTENSIONS) {
                        if (list[i].getName().equals(companyUserService.findOne(id).getId().toString().concat(extension))) {
                            list[i].delete();
                            break;
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();

    }


    /**
     * GET  /company-users/current get the companyUser by login.
     *
     * @return the ResponseEntity with status 200 (OK) and current companyUser in body
     */
    @GetMapping("/company-users/current")
    @Timed
    public ResponseEntity<CompanyUser> getCompanyUserByLoginByCompany() {
        log.debug("REST request to current CompanyUser");
        Optional<CompanyUser> currentUser = Optional.ofNullable(companyUserService.findCurrentUserByCompany());
        return ResponseUtil.wrapOrNotFound(currentUser);
    }

    /**
     * POST  /change_password : changes the current user's password
     *
     * @param req the new password and the old password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/company-users/change_account_data")
    @Timed
    public ResponseEntity changePassword(@RequestBody PasswordChangeRequest req) {
        if(!CompanyUserValidator.checkPassword(SecurityUtils.getCurrentUserLogin(), req.getOldPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isNotEmpty(req.getNewPassword()) && !CompanyUserValidator.checkPasswordLength(req.getNewPassword())) {
            return new ResponseEntity<>("Password doesn't strong", HttpStatus.BAD_REQUEST);
        }

        if(StringUtils.isNotEmpty(req.getNewPassword())) {
            userService.changePassword(req.getNewPassword());
        }

        if(CompanyUserValidator.checkLogin(req.getLogin())) {
            companyUserService.changeLogin(req.getLogin());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /company-users/uploadCompanyUserImage: uploads Company User Image
     * @param req
     * @return ResponceEntity OK, status 200
     */
    @PostMapping("/company-users/uploadCompanyUserImage")
    public ResponseEntity<String> handleFileUpload(@RequestBody FileUploadRequest req) {

        if (req != null) {
            ImageUtil.uploadFile(req, ImageUtil.getCompanyUserImagePath());
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "fileLength", "file length to big")).body(null);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * GET /company-users/image/{id}: get image of company user by id
     * @param id
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET, value = "/company-users/image/{id}")
    public ResponseEntity<List<String>> getImage(@PathVariable String id,  HttpServletResponse response) {

        FileFilter fileFilter = pathname -> pathname.getName().startsWith(id);

        FileFilter defaultFileFilter = pathname -> pathname.getName().startsWith("default");

        File imageDir = ImageUtil.getCompanyUserImageDir();
        File[] list = imageDir.listFiles(fileFilter);

        List<String> result = new ArrayList<>();
        if (list == null || list.length == 0) {
            File[] defaultList = imageDir.listFiles(defaultFileFilter);
            try {
                return ImageUtil.getDefaultImage(result, defaultList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return ImageUtil.getImage(result, list[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.notFound().build();
    }

}
