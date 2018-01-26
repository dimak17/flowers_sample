package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Company;
import com.impltech.domain.TypeOfFlower;

import com.impltech.repository.TypeOfFlowerRepository;
import com.impltech.security.SecurityUtils;
import com.impltech.service.TypeOfFlowerService;
import com.impltech.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * @author alex
 * REST controller for managing TypeOfFlower.
 */
@RestController
@RequestMapping("/api")
public class TypeOfFlowerResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfFlowerResource.class);

    private static final String ENTITY_NAME = "typeOfFlower";

    private final TypeOfFlowerRepository typeOfFlowerRepository;
    private final TypeOfFlowerService typeOfFlowerService;

    public TypeOfFlowerResource(TypeOfFlowerRepository typeOfFlowerRepository, TypeOfFlowerService typeOfFlowerService) {
        this.typeOfFlowerRepository = typeOfFlowerRepository;
        this.typeOfFlowerService = typeOfFlowerService;
    }

    /**
     * POST  /type-of-flowers : Create a new typeOfFlower.
     *
     * @param typeOfFlower the typeOfFlower to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeOfFlower, or with status 400 (Bad Request) if the typeOfFlower has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-of-flowers")
    @Timed
    public ResponseEntity<TypeOfFlower> createTypeOfFlower(@RequestBody TypeOfFlower typeOfFlower) throws URISyntaxException {
        log.debug("REST request to save TypeOfFlower : {}", typeOfFlower);
        if (typeOfFlower.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new typeOfFlower cannot already have an ID")).body(null);
        }
        Long currentCompanyID = SecurityUtils.getCurrentCompanyUser().getCompany().getId();

        for(TypeOfFlower tp: typeOfFlowerService.findTypeOfFlowersByIdCompany(currentCompanyID)) {
            if(tp.getName().equals(typeOfFlower.getName())) {
                return ResponseEntity.ok(typeOfFlower);
            }
        }
        Company currentCompany =SecurityUtils.getCurrentCompanyUser().getCompany();
        typeOfFlower.setCompany(currentCompany);
        TypeOfFlower result = typeOfFlowerRepository.save(typeOfFlower);
        return ResponseEntity.created(new URI("/api/type-of-flowers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-of-flowers : Updates an existing typeOfFlower.
     *
     * @param typeOfFlower the typeOfFlower to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeOfFlower,
     * or with status 400 (Bad Request) if the typeOfFlower is not valid,
     * or with status 500 (Internal Server Error) if the typeOfFlower couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-of-flowers")
    @Timed
    public ResponseEntity<TypeOfFlower> updateTypeOfFlower(@RequestBody TypeOfFlower typeOfFlower) throws URISyntaxException {
        log.debug("REST request to update TypeOfFlower : {}", typeOfFlower);
        if (typeOfFlower.getId() == null) {
            return createTypeOfFlower(typeOfFlower);
        }
        TypeOfFlower result = typeOfFlowerRepository.save(typeOfFlower);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeOfFlower.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-of-flowers : get all the typeOfFlowers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeOfFlowers in body
     */
    @GetMapping("/type-of-flowers")
    @Timed
    public List<TypeOfFlower> getAllTypeOfFlowers() {
        log.debug("REST request to get all TypeOfFlowers");
        return typeOfFlowerRepository.findAll();
    }

    /**
     * GET  /type-of-flowers/:id : get the "id" typeOfFlower.
     *
     * @param id the id of the typeOfFlower to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeOfFlower, or with status 404 (Not Found)
     */
    @GetMapping("/type-of-flowers/{id}")
    @Timed
    public ResponseEntity<TypeOfFlower> getTypeOfFlower(@PathVariable Long id) {
        log.debug("REST request to get TypeOfFlower : {}", id);
        TypeOfFlower typeOfFlower = typeOfFlowerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeOfFlower));
    }

    /**
     * DELETE  /type-of-flowers/:id : delete the "id" typeOfFlower.
     *
     * @param id the id of the typeOfFlower to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-of-flowers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeOfFlower(@PathVariable Long id) {
        log.debug("REST request to delete TypeOfFlower : {}", id);
        List<String> varietiesName = typeOfFlowerService.delete(id);

        if(varietiesName.size() == 0) {
            typeOfFlowerRepository.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        }
        return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Variety", varietiesName.toString())).build();
    }

    @GetMapping("type-of-flowers/company")
    @Timed
    public ResponseEntity<List<TypeOfFlower>> getTypeOfFlowerByCompany(){
        List<TypeOfFlower> typeOfFlowers = typeOfFlowerService.findTypeOfFlowersByCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeOfFlowers));
    }

    //TODO  company id ?
    @GetMapping("type-of-flowers/company/{id}")
    @Timed
    public ResponseEntity<List<TypeOfFlower>> getTypeOfFlowerByIdCompany(){
        List<TypeOfFlower> typeOfFlowers = null;
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("REST request to get TypeOfFlower by Company id : {}", idCompany);
        if(idCompany != null) {
            typeOfFlowers = typeOfFlowerService.findTypeOfFlowersByIdCompany(idCompany);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeOfFlowers));
    }

}
