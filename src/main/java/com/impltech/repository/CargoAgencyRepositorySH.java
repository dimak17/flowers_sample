package com.impltech.repository;

import com.impltech.domain.CargoAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CargoAgency entity.
 */

@SuppressWarnings("unused")
@Repository
public interface CargoAgencyRepositorySH extends JpaRepository<CargoAgency,Long> {

    @Query("select c from CargoAgency c where c.id = :id and c.company.id = :idCompany")
    CargoAgency findOneByCompanyId(@Param("id") Long id, @Param("idCompany") Long idCompany);

    @Query("select c from CargoAgency c where c.company.id = :idCompany")
    List<CargoAgency> findAllByCurrentCompany (@Param("idCompany") Long idCompany);

    @Modifying
    @Query("delete from CargoAgency c where c.company.id =:idCompany and c.id = :id")
    void deleteCargoAgencyByCurrentCompany (@Param("idCompany") Long idCompany, @Param("id") Long id);

    @Query(value = "select COUNT(*) as duplicate from cargo_agency ca  where  ca.name =:name and ca.id is not null and ca.company_id =:idCompany", nativeQuery = true)
    Long checkDuplicateIfCreateCargoAgency(@Param("name") String name, @Param("idCompany") Long idCompany);

    @Query(value = "select COUNT(*) as duplicate from cargo_agency ca  where  ca.name =:name and ca.id <> :cargo_agency_id and ca.company_id = :idCompany", nativeQuery = true)
    Long checkDuplicateIfUpdateCargoAgency(@Param("cargo_agency_id") Long id,@Param("name") String name, @Param("idCompany") Long idCompany);
}
