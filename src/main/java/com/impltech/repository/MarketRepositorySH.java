package com.impltech.repository;

import com.impltech.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Spring Data JPA repository for the Market entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketRepositorySH extends JpaRepository<Market,Long> {

    @Query("select distinct market from Market market left join fetch market.boxGroups")
    List<Market> findAllWithEagerRelationships();

    @Query("select market from Market market left join fetch market.boxGroups where market.id =:id and market.company.id =:companyId")
    Market findOneWithEagerRelationships(@Param("id") Long id, @Param("companyId") Long companyId);

    List<Market> findAllByCompanyId(Long id);

    @Query("select m from Market m where m.company.id = :companyId")
    List<Market> findAllByCurrentCompanyId(@Param("companyId") Long companyId);

    @Query(value = "select count(m.id) from market m " +
        "left join company c ON c.id = m.company_id " +
        "WHERE m.id=?1 and c.id=?2", nativeQuery = true)
    BigInteger checkMarketByCompany(Long id, Long companyId);

    @Query(value = "select count(id) from market where id <> ?1 and name = ?2 and company_id = ?3", nativeQuery = true)
    BigInteger isNameAlreadyExists(Long id, String name, Long companyId);
}
