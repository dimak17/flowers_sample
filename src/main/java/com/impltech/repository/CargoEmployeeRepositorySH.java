package com.impltech.repository;

import com.impltech.domain.CargoEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CargoEmployee entity.
 */

@SuppressWarnings("unused")
@Repository
public interface CargoEmployeeRepositorySH extends JpaRepository<CargoEmployee,Long> {

    @Query(value = "SELECT * FROM cargo_employee ce " +
        "LEFT JOIN cargo_agency ca ON ca.id = ce.cargo_agency_id " +
        "LEFT JOIN company c ON c.id = ca.company_id " +
        "WHERE ca.id IS NOT NULL AND c.id IS NOT NULL " +
        "AND c.id =:companyId AND ca.id =:cargoAgencyId AND ce.id =:cargoEmployeeId",
    nativeQuery = true)
    CargoEmployee findOneByCAgencyIdAndCEmployeeIdAndCompanyId(
        @Param("companyId") Long companyId,
        @Param("cargoAgencyId") Long cargoAgencyId,
        @Param("cargoEmployeeId") Long cargoEmployeeId
    );

    @Query(value = "SELECT * FROM cargo_employee ce " +
        "LEFT JOIN cargo_agency ca ON ca.id = ce.cargo_agency_id " +
        "LEFT JOIN company c ON c.id = ca.company_id " +
        "WHERE ca.id IS NOT NULL AND c.id IS NOT NULL " +
        "AND c.id =:companyId AND ca.id =:cargoAgencyId",
    nativeQuery = true)
    List<CargoEmployee> findAllByCAgencyIdAndCompanyId(
        @Param("companyId") Long companyId,
        @Param("cargoAgencyId") Long cargoAgencyId
    );

    @Query(value = "SELECT * FROM cargo_employee ce " +
        "LEFT JOIN cargo_agency ca ON ca.id = ce.cargo_agency_id " +
        "LEFT JOIN company c ON c.id = ca.company_id " +
        "WHERE ca.id IS NOT NULL AND c.id IS NOT NULL " +
        "AND c.id =:companyId",
        nativeQuery = true)
    List<CargoEmployee> findAllByCompanyId(
        @Param("companyId") Long companyId
    );

    @Modifying
    @Query("delete from CargoEmployee c where c.company.id =:idCompany and c.id = :employeeId and c.cargoAgency.id=:cargoAgencyId")
    void deleteCargoEmployeeByCurrentCompanyIdAndCargoAgencyId (@Param("idCompany") Long idCompany, @Param("employeeId") Long employeeId, @Param("cargoAgencyId") Long cargoAgencyId);
}
