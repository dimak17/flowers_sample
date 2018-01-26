package com.impltech.web.rest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.io.Files;
import com.impltech.security.SecurityUtils;
import com.impltech.validator.util.VarietyValidator;
import com.impltech.web.rest.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Variety;
import com.impltech.service.VarietyService;
import com.impltech.web.rest.request.FileUploadRequest;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;



/**
 * @author dima
 * REST controller for managing Variety.
 */
@RestController
@RequestMapping("/api")
public class VarietyResource {

    private final Logger log = LoggerFactory.getLogger(VarietyResource.class);

    private static final String ENTITY_NAME = "variety";

    private final VarietyService varietyService;

    public VarietyResource(VarietyService varietyService) {
        this.varietyService = varietyService;
    }

    /**
     * POST  /varieties : Create a new variety.
     *
     * @param variety the variety to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variety, or with status 400 (Bad Request) if the variety has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/varieties")
    @Timed
    public ResponseEntity<Variety> createVariety(@RequestBody Variety variety) throws URISyntaxException {
        log.debug("REST request to save Variety : {}", variety);
        for (Variety varietyFromList : this.varietyService.findAllByCompany()) {
            if(varietyFromList.getName().equalsIgnoreCase(variety.getName())){
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
            }
        }
        if (variety.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new variety cannot already have an ID")).body(null);
        }
        if (VarietyValidator.checkLengthValidation(variety)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        Variety result = varietyService.save(variety);
        return ResponseEntity.created(new URI("/api/varieties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /varieties : Updates an existing variety.
     *
     * @param variety the variety to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variety,
     * or with status 400 (Bad Request) if the variety is not valid,
     * or with status 500 (Internal Server Error) if the variety couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/varieties")
    @Timed
    public ResponseEntity<Variety> updateVariety(@RequestBody Variety variety) throws URISyntaxException {
        log.debug("REST request to update Variety : {}", variety);
        if (variety.getId() == null) {
            return createVariety(variety);
        } else {
                if (varietyService.findOneByCurrentCompanyId(variety.getId()) != null) {
                    for (String ext : UploaderUtils.EXTENSIONS) {
                        File file = new File(ImageUtil.getVarietyImagePath().toString().concat("/").concat(varietyService.findOneByCurrentCompanyId(variety.getId()).getName()).concat(ext));
                        if (file.exists()) {
                            file.renameTo(new File(ImageUtil.getVarietyImagePath().toString().concat("/").concat(variety.getName()).concat(ext)));
                            break;
                        }
                    }
                }
            }
        if (VarietyValidator.checkLengthValidation(variety)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
            Variety result = varietyService.save(variety);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variety.getId().toString()))
                .body(result);
    }

    /**
     * GET  /varieties : get all the varieties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of varieties in body
     */
    @GetMapping("/varieties")
    @Timed
    public ResponseEntity<List<Variety>> getAllVarieties(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Varieties");
        Page<Variety> page = varietyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/varieties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /varieties/:id : get the "id" variety.
     *
     * @param id the id of the variety to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variety, or with status 404 (Not Found)
     */
    @GetMapping("/varieties/{id}")
    @Timed
    public ResponseEntity<Variety> getVariety(@PathVariable Long id) {
        log.debug("REST request to get Variety : {}", id);
        Variety variety = varietyService.findOneByCurrentCompanyId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(variety));
    }

    @GetMapping("/varieties/company/{id}")
    @Timed
    public ResponseEntity<List<Variety>> getVarietiesByIdCompany(@PathVariable Long id) {
        log.debug("REST request to get list of Variaty by id Comapany: {}", id);
        List<Variety> varieties = null;
        if(SecurityUtils.getCurrentCompanyUser().getCompany().getId() == id) {
            varieties = varietyService.findVarietiesByIdCompany(id);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(varieties));
    }

    @GetMapping("/varieties/type-of-flower/{id}")
    @Timed
    public ResponseEntity<List<Variety>>getVarietiesByTypeOfFlowers(@PathVariable Long id) {
        log.debug("REST request to get list of Variaty by Type Of Flower and Company Id: {}", id);
        List<Variety> varieties = varietyService.findVarietiesByIdCompanyAndTypeOfFlower(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(varieties));
    }

    /**
     * GET  /varieties : get all the varieties by Company id.
     * @return the ResponseEntity with status 200 (OK) and the list of varieties in body
     */
    @GetMapping("/varieties/company")
    @Timed
    public ResponseEntity<List<Variety>> getAllVarieties() {
        log.debug("REST request to get a page of Varieties");
        List<Variety> varieties = varietyService.findAllByCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(varieties));
    }

    /**
     * DELETE  /varieties/:id : delete the "id" variety.
     *
     * @param id the id of the variety to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/varieties/{id}")
    @Timed
    public ResponseEntity<Void> deleteVariety(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        log.debug("REST request to delete Variety : {}", id);
        List<String> varietyUsages = varietyService.delete(id);

        if (varietyUsages.get(varietyUsages.size()-1).equals("Blocks")) {
            varietyUsages.remove(varietyUsages.size()-1);
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Blocks", varietyUsages.toString())).build();
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }



    /**
     * DELETE  /varieties/:id/image : delete the "image" of  variety.
     *
     * @param id the id of the variety to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/varieties/{id}/image")
    @Timed
    public ResponseEntity<Void> deleteVarietyImage(@PathVariable Long id) {

        if( id != null) {
            log.debug("REST request to delete Variety : {}", id);
            List<File> list = ImageUtil.listFilteredFiles(id, ImageUtil.getVarietyImageDir());
            ImageUtil.deleteImage(id, list);
        }
            return ResponseEntity.ok().build();

    }


    @GetMapping("/varieties/default-image")
    public ResponseEntity<List<String>> getDefaultImage() {

        List<String> result = new ArrayList<>();

        File[] defaultList = ImageUtil.listDefaultFiles(ImageUtil.getVarietyImageDir());
        try {
            return ImageUtil.getDefaultImage(result, defaultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/varieties/uploadVarietyImage")
    public ResponseEntity<String> handleFileUpload(@RequestBody FileUploadRequest req) {

        if (req != null) {
            ImageUtil.uploadFile(req, ImageUtil.getVarietyImagePath());
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "fileLength", "file length to big")).body(null);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/varieties/image/{id}")
    public ResponseEntity<List<String>> getVarietyImage(@PathVariable Long id) {

        List<String> result = new ArrayList<>();

        List<File> list = ImageUtil.listFilteredFiles(id, ImageUtil.getVarietyImageDir());

        list = ImageUtil.numberingImageFileSorting(list);

        if (list.size() == 0 || !ImageUtil.checkIfExist(id, list)) {
            File[] defaultList = ImageUtil.listDefaultFiles(ImageUtil.getVarietyImageDir());
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

    @GetMapping("/varieties//type-of-flower-images/{typeOfFlowerId}")
    public ResponseEntity<List<String>> getAllVarietyImagesByCurrentCompanyAndTypeOfFlowersId(@PathVariable String typeOfFlowerId) {

        final List<File> files = ImageUtil.numberingImageFileSorting(Arrays.asList(ImageUtil.listAllImageFile(ImageUtil.getVarietyImageDir())));
        List<String> fileCounting = files.stream().map(file -> Arrays.asList(file.getName().split("\\.")).get(0)).collect(Collectors.toList());

        List<Variety> allCompanyVarieties = varietyService.findAllByCompany()
            .stream()
            .filter(variety -> variety.getTypeOfFlower() != null && variety.getTypeOfFlower().getId()
                .equals(Long.valueOf(typeOfFlowerId))).collect(Collectors.toList());

        List<String> imageBase64Files = new ArrayList<>();

        allCompanyVarieties.forEach(variety -> {
            if (fileCounting.contains(variety.getId().toString())) {
                try {
                    File file = files.stream()
                        .filter(image -> variety.getId().toString().equals(image.getName().split("\\.")[0]))
                        .collect(Collectors.toList()).get(0);

                    byte[] fileBytes = Files.toByteArray(file);
                    imageBase64Files.add(Base64.getEncoder().encodeToString(fileBytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File[] defaultList = ImageUtil.listDefaultFiles(ImageUtil.getVarietyImageDir());
                try {
                    byte[] fileBytes = Files.toByteArray(defaultList[0]);
                    imageBase64Files.add(Base64.getEncoder().encodeToString(fileBytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if (imageBase64Files.size() <= 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imageBase64Files));
    }

}
