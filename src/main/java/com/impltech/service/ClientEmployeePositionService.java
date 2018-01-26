package com.impltech.service;

import com.impltech.domain.ClientEmployeePosition;
import com.impltech.repository.ClientEmployeePositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing ClientEmployeePosition.
 */
@Service
@Transactional
public class ClientEmployeePositionService {

    private final Logger log = LoggerFactory.getLogger(ClientEmployeePositionService.class);

    private final ClientEmployeePositionRepository clientEmployeePositionRepository;

    public ClientEmployeePositionService(ClientEmployeePositionRepository clientEmployeePositionRepository) {
        this.clientEmployeePositionRepository = clientEmployeePositionRepository;
    }

    /**
     * Save a clientEmployeePosition.
     *
     * @param clientEmployeePosition the entity to save
     * @return the persisted entity
     */
    public ClientEmployeePosition save(ClientEmployeePosition clientEmployeePosition) {
        log.debug("Request to save ClientEmployeePosition : {}", clientEmployeePosition);
        return clientEmployeePositionRepository.save(clientEmployeePosition);
    }

    /**
     *  Get all the clientEmployeePositions.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClientEmployeePosition> findAll() {
        log.debug("Request to get all ClientEmployeePositions");
        return clientEmployeePositionRepository.findAll();
    }

    /**
     *  Get one clientEmployeePosition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ClientEmployeePosition findOne(Long id) {
        log.debug("Request to get ClientEmployeePosition : {}", id);
        return clientEmployeePositionRepository.findOne(id);
    }

    /**
     *  Delete the  clientEmployeePosition by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientEmployeePosition : {}", id);
        clientEmployeePositionRepository.delete(id);
    }
}
