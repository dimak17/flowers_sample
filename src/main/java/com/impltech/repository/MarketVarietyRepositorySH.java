package com.impltech.repository;

import com.impltech.domain.MarketVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MarketVariety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketVarietyRepositorySH extends JpaRepository<MarketVariety,Long> {

    List<MarketVariety> findByMarketId(Long id);

    @Modifying
    @Query(value = "DELETE FROM market_variety WHERE market_variety.id in " +
    "     (SELECT mv.id FROM (SELECT id, market_id FROM market_variety) as mv " +
    "     LEFT JOIN market m ON m.id = mv.market_id " +
    "     LEFT JOIN company c ON c.id = m.company_id " +
    "             WHERE c.id = ?2 AND m.id = ?1)", nativeQuery = true)
    void safeDeleteByMarketId(Long marketId, Long companyId);

    @Modifying
    @Query(value = "DELETE FROM MarketVariety mv WHERE mv.market.id = ?1")
    void deleteByMarketId(Long marketId);
}
