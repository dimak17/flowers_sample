package com.impltech.service;

import com.impltech.domain.CargoAgency;
import com.impltech.domain.CargoEmployee;
import com.impltech.domain.Company;
import com.impltech.repository.CargoAgencyRepositorySH;
import com.impltech.repository.CargoEmployeeRepositorySH;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * @author dima
 * Service Implementation for managing CargoAgency.
 */

@Service
@Transactional
public class CargoAgencyServiceSH {

    private final Logger log = LoggerFactory.getLogger(CargoAgencyServiceSH.class);

    private final CargoAgencyRepositorySH cargoAgencyRepositorySH;

    private final CargoEmployeeServiceSH cargoEmployeeServiceSH;

    private final CargoEmployeeRepositorySH cargoEmployeeRepositorySH;

    public CargoAgencyServiceSH(CargoAgencyRepositorySH cargoAgencyRepositorySH, CargoEmployeeServiceSH cargoEmployeeServiceSH, CargoEmployeeRepositorySH cargoEmployeeRepositorySH) {
        this.cargoAgencyRepositorySH = cargoAgencyRepositorySH;
        this.cargoEmployeeServiceSH = cargoEmployeeServiceSH;
        this.cargoEmployeeRepositorySH = cargoEmployeeRepositorySH;
    }

    /**
     * Save a cargoAgency.
     *
     * @param cargoAgency the entity to save
     * @return the persisted entity
     */

    public CargoAgency save(CargoAgency cargoAgency) {
        Company id = SecurityUtils.getCurrentCompanyUser().getCompany();
        cargoAgency.setCompany(id);
        log.debug("Request to save CargoAgency : {}", cargoAgency);
        return cargoAgencyRepositorySH.save(cargoAgency);
    }

    /**
     * Get all the cargoAgencies.
     *
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public List<CargoAgency> findAllByCurrentCompany() {
        log.debug("Request to get all CargoEmployeePositions");
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoAgencyRepositorySH.findAllByCurrentCompany(companyId);
    }

    /**
     * Get one cargoAgency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */

    @Transactional(readOnly = true)
    public CargoAgency findOneByCompanyId(Long id) {
        log.debug("Request to get CargoAgency : {} by current company", id);
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoAgencyRepositorySH.findOneByCompanyId(id, idCompany);
    }

    @Transactional(readOnly = true)
    public Long checkDuplicate(CargoAgency cargoAgency) {
        Long result;
        String name = cargoAgency.getName();
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        if (cargoAgency.getId() == null) {
            result = cargoAgencyRepositorySH.checkDuplicateIfCreateCargoAgency(name, companyId);
        } else {
            Long id = cargoAgency.getId();
            result = cargoAgencyRepositorySH.checkDuplicateIfUpdateCargoAgency(id, name, companyId);
        }
        return result;
    }

    /**
     * Delete the  cargoAgency by id.
     *
     * @param id the id of the entity
     */

    public void deleteByCurrentCompanyId (Long id) {
        log.debug("Request to delete CargoAgency : {}", id);

        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        List<CargoEmployee> cargoEmployees = cargoEmployeeServiceSH.findAllByAgencyIdAndCompanyId(id);
        cargoEmployees.forEach(c -> {
            c.setCargoEmployeePositions(new HashSet<>());
            c.setMarkets(new HashSet<>());
        });
        cargoEmployeeRepositorySH.delete(cargoEmployees);
        cargoAgencyRepositorySH.deleteCargoAgencyByCurrentCompany(idCompany, id);
    }
}
