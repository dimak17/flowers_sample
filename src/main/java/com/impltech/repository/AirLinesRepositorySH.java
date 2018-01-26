package com.impltech.repository;

import com.impltech.domain.AirLines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the AirLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirLinesRepositorySH extends JpaRepository<AirLines,Long> {

    @Query("select a from AirLines a where a.company.id=:companyId")
    List<AirLines> findAirLinesByCompanyId(@Param("companyId") Long companyId);
}
