package com.impltech.service;

import com.impltech.domain.CargoEmployeePosition;
import com.impltech.repository.CargoEmployeePositionRepositorySH;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing CargoEmployeePosition.
 */

@Service
@Transactional
public class CargoEmployeePositionServiceSH {

    private final Logger log = LoggerFactory.getLogger(CargoEmployeePositionServiceSH.class);

    private final com.impltech.repository.CargoEmployeePositionRepositorySH cargoEmployeePositionRepositorySH;

    public CargoEmployeePositionServiceSH(CargoEmployeePositionRepositorySH cargoEmployeePositionRepositorySH) {
        this.cargoEmployeePositionRepositorySH = cargoEmployeePositionRepositorySH;
    }

    /**
     * Save a cargoEmployeePosition.
     *
     * @param cargoEmployeePosition the entity to save
     * @return the persisted entity
     */

    public CargoEmployeePosition save(CargoEmployeePosition cargoEmployeePosition, boolean isEdit) {
        log.debug("Request to save CargoEmployeePosition : {}", cargoEmployeePosition);
        if(!isEdit) {
            cargoEmployeePosition.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        }
        return cargoEmployeePositionRepositorySH.save(cargoEmployeePosition);
    }

    /**
     *  Get all the cargoEmployeePositions.
     *
     *  @return the list of entities
     */

    @Transactional(readOnly = true)
    public List<CargoEmployeePosition> findAllByCurrentCompany() {
        log.debug("Request to get all CargoEmployeePositions");
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoEmployeePositionRepositorySH.findAllByCurrentCompany(companyId);
    }

    /**
     *  Get one cargoEmployeePosition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */

    @Transactional(readOnly = true)
    public CargoEmployeePosition findOneByCurrentCompany(Long id) {
        log.debug("Request to get CargoEmployeePosition : {} by current company", id);
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoEmployeePositionRepositorySH.findOneByCompanyId(id, idCompany);
    }

    @Transactional(readOnly = true)
    public Long checkDuplicate(CargoEmployeePosition cargoEmployeePosition) {
        Long result;
        String name = cargoEmployeePosition.getName();
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        if (cargoEmployeePosition.getId() == null) {
            result = cargoEmployeePositionRepositorySH.checkDuplicateIfCreateCargoEmployeePosition(name, companyId);
        } else {
            Long id = cargoEmployeePosition.getId();
            result = cargoEmployeePositionRepositorySH.checkDuplicateIfUpdateCargoEmployeePosition(id, name, companyId);
        }
        return result;
    }

    /**
     *  Delete the  cargoEmployeePosition by id.
     *
     *  @param id the id of the entity
     */

    public void deleteCargoEmployeePositionByCurrentCompany(Long id) {
        log.debug("Request to delete CargoEmployeePosition : {}", id);
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        cargoEmployeePositionRepositorySH.deleteCargoEmployeePositionByCurrentCompany(idCompany, id);
    }
 }

