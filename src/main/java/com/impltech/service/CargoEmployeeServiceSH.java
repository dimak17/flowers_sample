package com.impltech.service;

import com.impltech.domain.CargoEmployee;
import com.impltech.domain.Company;
import com.impltech.repository.CargoEmployeeRepositorySH;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing CargoEmployee.
 */

@Service
@Transactional
public class CargoEmployeeServiceSH {

    private final Logger log = LoggerFactory.getLogger(CargoEmployeeServiceSH.class);

    private final CargoEmployeeRepositorySH cargoEmployeeRepositorySH;

    public CargoEmployeeServiceSH(CargoEmployeeRepositorySH cargoEmployeeRepositorySH) {
        this.cargoEmployeeRepositorySH = cargoEmployeeRepositorySH;
    }

    /**
     * Save a cargoEmployee.
     *
     * @param cargoEmployee the entity to save
     * @return the persisted entity
     */

    public CargoEmployee save(CargoEmployee cargoEmployee) {
        Company id = SecurityUtils.getCurrentCompanyUser().getCompany();
        cargoEmployee.setCompany(id);
        log.debug("Request to save CargoEmployee : {}", cargoEmployee);
        return cargoEmployeeRepositorySH.save(cargoEmployee);
    }

    /**
     *  Get all the cargoEmployees.
     *
     *  @return the list of entities
     */

    @Transactional(readOnly = true)
    public List<CargoEmployee> findAllByCompanyId() {
        log.debug("Request to get all CargoEmployees");
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoEmployeeRepositorySH.findAllByCompanyId(companyId);
    }

    /**
     *  Get one cargoEmployee by id.
     *
     *  @param employeeId the id of the entity
     *  @return the entity
     */

    @Transactional(readOnly = true)
    public CargoEmployee findOneByCargoEmployeeIdAndCargoAgencyId(Long employeeId, Long cargoAgencyId) {
        log.debug("Request to get CargoEmployee : {}", employeeId);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoEmployeeRepositorySH.findOneByCAgencyIdAndCEmployeeIdAndCompanyId(companyId, cargoAgencyId, employeeId);
    }

    @Transactional(readOnly = true)
    public List<CargoEmployee> findAllByAgencyIdAndCompanyId(Long cargoAgencyId) {
        log.debug("Request to get CargoEmployee : {}", cargoAgencyId);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return cargoEmployeeRepositorySH.findAllByCAgencyIdAndCompanyId(companyId, cargoAgencyId);
    }

    /**
     *  Delete the  cargoEmployee by id.
     *
     *  @param employeeId the id of the entity
     */

    public void deleteCargoEmployeeByCurrentCompanyIdAndCargoAgencyId(Long employeeId, Long cargoAgencyId) {
        log.debug("Request to delete CargoEmployee : {}", employeeId);
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        cargoEmployeeRepositorySH.deleteCargoEmployeeByCurrentCompanyIdAndCargoAgencyId(idCompany, employeeId, cargoAgencyId);
    }
}
