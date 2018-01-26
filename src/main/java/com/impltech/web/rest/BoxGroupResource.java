package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.BoxGroup;
import com.impltech.service.BoxGroupService;
import com.impltech.service.dto.BoxGroupDTO;
import com.impltech.service.util.ConvertUtil;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author platon
 * REST controller for managing BoxGroup.
 */
@RestController
@RequestMapping("/api")
public class BoxGroupResource {

    private final Logger log = LoggerFactory.getLogger(BoxGroupResource.class);

    private static final String ENTITY_NAME = "boxGroup";

    private final BoxGroupService boxGroupService;

    @Autowired
    public BoxGroupResource(BoxGroupService boxGroupService) {
        this.boxGroupService = boxGroupService;
    }

    /**
     * POST  /box-groups : Create a new boxGroup.
     *
     * @param boxGroup the boxGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxGroup, or with status 400 (Bad Request) if the boxGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/box-groups")
    @Timed
    public ResponseEntity<BoxGroup> createBoxGroup(@RequestBody BoxGroup boxGroup) throws URISyntaxException {
        log.debug("REST request to save BoxGroup : {}", boxGroup);
        if (boxGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new boxGroup cannot already have an ID")).body(null);
        }
        BoxGroup result = boxGroupService.save(boxGroup);
        return ResponseEntity.created(new URI("/api/box-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /box-groups : Updates an existing boxGroup.
     *
     * @param boxGroup the boxGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boxGroup,
     * or with status 400 (Bad Request) if the boxGroup is not valid,
     * or with status 500 (Internal Server Error) if the boxGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/box-groups")
    @Timed
    public ResponseEntity<BoxGroup> updateBoxGroup(@RequestBody BoxGroup boxGroup) throws URISyntaxException {
        log.debug("REST request to update BoxGroup : {}", boxGroup);
        if (boxGroup.getId() == null) {
            return createBoxGroup(boxGroup);
        }
        BoxGroup result = boxGroupService.save(boxGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boxGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /box-groups : get all the boxGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of boxGroups in body
     */
    @GetMapping("/box-groups")
    @Timed
    public ResponseEntity<List<BoxGroup>> getAllBoxGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BoxGroups");
        Page<BoxGroup> page = boxGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/box-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /box-groups/:id : get the "id" boxGroup.
     *
     * @param id the id of the boxGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxGroup, or with status 404 (Not Found)
     */
    @GetMapping("/box-groups/{id}")
    @Timed
    public ResponseEntity<BoxGroup> getBoxGroup(@PathVariable Long id) {
        log.debug("REST request to get BoxGroup : {}", id);
        BoxGroup boxGroup = boxGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(boxGroup));
    }

    /**
     * DELETE  /box-groups/:id : delete the "id" boxGroup.
     *
     * @param id the id of the boxGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/box-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteBoxGroup(@PathVariable Long id) {
        log.debug("REST request to delete BoxGroup : {}", id);
        boxGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /box-types : get all the boxTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of boxTypes in body
     */
    @GetMapping("/box-groups/company/dto")
    @Timed
    public ResponseEntity<List<BoxGroupDTO>> getAllBoxTypes() {
        log.debug("REST request to get a page of BoxTypes");
        List<BoxGroup> boxTypes = boxGroupService.findAllByCurrentCompany();
        return new ResponseEntity<>(boxTypes
            .stream()
            .sorted(Comparator.comparing(BoxGroup::getId))
            .map(ConvertUtil::boxGroupToBoxGroupDTO)
            .collect(toList()),
        HttpStatus.OK);
    }

    /**
     * POST  /box-groups/dto : Create a new boxGroup.
     *
     * @param boxGroupDTO the boxGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxGroup, or with status 400 (Bad Request) if the boxGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/box-groups/dto")
    @Timed
    public ResponseEntity<BoxGroupDTO> editBoxGroupDTO(@RequestBody BoxGroupDTO boxGroupDTO) throws URISyntaxException {
        log.debug("REST request to update BoxGroup : {}", boxGroupDTO);
        if (boxGroupDTO.getId() == null) {
            return createBoxGroupDTO(boxGroupDTO);
        }
        BoxGroupDTO result = ConvertUtil.boxGroupToBoxGroupDTO(boxGroupService.update(boxGroupDTO));
        return ResponseEntity.created(new URI("/api/box-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /box-groups/dto : Create a new boxGroup.
     *
     * @param boxGroupDTO the boxGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxGroup, or with status 400 (Bad Request) if the boxGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/box-groups/dto")
    @Timed
    public ResponseEntity<BoxGroupDTO> createBoxGroupDTO(@RequestBody BoxGroupDTO boxGroupDTO) throws URISyntaxException {
        log.debug("REST request to save BoxGroup : {}", boxGroupDTO);
        if (boxGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new boxGroup cannot already have an ID")).body(null);
        }
        BoxGroupDTO result = ConvertUtil.boxGroupToBoxGroupDTO(boxGroupService.save(boxGroupDTO));
        return ResponseEntity.created(new URI("/api/box-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /box-groups/dto:id : get the "id" boxGroup.
     *
     * @param id the id of the boxGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxGroup, or with status 404 (Not Found)
     */
    @GetMapping("/box-groups/dto/{id}")
    @Timed
    public ResponseEntity<BoxGroupDTO> getBoxGroupDTO(@PathVariable Long id) {
        log.debug("REST request to get BoxGroup : {}", id);
        BoxGroup boxGroup = boxGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ConvertUtil.boxGroupToBoxGroupDTO(boxGroup)));
    }

    /**
     * GET  /box-groups/dto:id : get the "id" of market.
     *
     * @param id the id of the boxGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxGroup, or with status 404 (Not Found)
     */
    @GetMapping("/box-groups/dto/market/{id}")
    @Timed
    public ResponseEntity<List<BoxGroupDTO>> getBoxGroupDTOByMarket(@PathVariable Long id) {
        log.debug("REST request to get List<BoxGroup> by Market : {}", id);
        List<BoxGroup> boxGroup = boxGroupService.findByMarket(id);
        return new ResponseEntity<>(boxGroup
            .stream()
            .sorted(Comparator.comparing(BoxGroup::getId))
            .map(ConvertUtil::boxGroupToBoxGroupDTO)
            .collect(toList()), HttpStatus.OK);
    }


    /**
     * GET  /box-types : get all the boxTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of boxTypes in body
     */
    @GetMapping("/box-groups/company")
    @Timed
    public ResponseEntity<List<BoxGroup>> getAllBoxTypesByCurrentCompany() {
        log.debug("REST request to get a page of BoxTypes");
        List<BoxGroup> boxTypes = boxGroupService.findAllByCurrentCompany();
        return new ResponseEntity<>(boxTypes,HttpStatus.OK);
    }

}
