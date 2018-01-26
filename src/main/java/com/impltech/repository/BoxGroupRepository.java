package com.impltech.repository;

import com.impltech.domain.BoxGroup;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the BoxGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxGroupRepository extends JpaRepository<BoxGroup,Long> {

    @Query("from BoxGroup b where b.company.id = :idCompany")
    List<BoxGroup> findByCompanyId(@Param("idCompany") Long idCompany);

    @Query(value = "SELECT * from box_group bg " +
        "  LEFT JOIN market_box_groups mb ON bg.id = mb.box_groups_id " +
        "  LEFT JOIN market m ON mb.markets_id = m.id " +
        "  LEFT JOIN company c ON m.company_id = c.id " +
        " WHERE mb.box_groups_id IS NOT NULL AND c.id IS NOT NULL AND m.id=?1", nativeQuery = true)
    List<BoxGroup> findBoxGroupByMarket(Long id);

}
