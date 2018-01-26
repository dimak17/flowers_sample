package com.impltech.repository;

import com.impltech.domain.Company;
import com.impltech.domain.Position;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Position entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {

    Position findOneByCompanyAndName(Company company, String name);

    List<Position> findOneByCompany(Company company);

    @Query("select p from Position p where p.company.id = :companyId")
    List<Position> findAllByCurrentCompany(@Param("companyId") Long companyId);

}
