package com.impltech.repository;

import com.impltech.domain.Market;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Market entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketRepository extends JpaRepository<Market,Long> {

    @Query("select distinct market from Market market left join fetch market.boxGroups")
    List<Market> findAllWithEagerRelationships();

    @Query("select market from Market market left join fetch market.boxGroups where market.id =:id")
    Market findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select m from Market m where m.company.id = :companyId")
    List<Market> findAllByCurrentCompanyId(@Param("companyId") Long companyId);

    @Query("select m from Market m where m.company.id = :companyId and m.id =:id")
    Market findOneByCurrentCompanyId(@Param("companyId") Long companyId, @Param("id") Long id);


}
