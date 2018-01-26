package com.impltech.repository;

import com.impltech.domain.MarketBoxVarietyProperty;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MarketBoxVarietyProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketBoxVarietyPropertyRepository extends JpaRepository<MarketBoxVarietyProperty,Long> {

    @Query(value = "SELECT * FROM market_box_variety_property mbvp " +
        "LEFT JOIN variety v ON v.id = mbvp.variety_id " +
        "LEFT JOIN company c ON c.id = v.company_id " +
        "LEFT JOIN market_box mb ON mb.id = mbvp.market_box_id " +
        "LEFT JOIN market m ON m.id = mb.market_id " +
        "WHERE v.id IS NOT NULL AND c.id IS NOT NULL AND mb.id IS NOT NULL AND m.id IS NOT NULL" +
        "      AND c.id=:companyId AND mb.id=:marketBoxId",
        nativeQuery = true)
    List<MarketBoxVarietyProperty> findAllByCompanyAndMarketBox(
        @Param("companyId") Long companyId,
        @Param("marketBoxId") Long marketBoxId
    );

    @Query(value = "SELECT * FROM market_box_variety_property mbvp " +
        "LEFT JOIN variety v ON v.id = mbvp.variety_id " +
        "LEFT JOIN company c ON c.id = v.company_id " +
        "LEFT JOIN market_box mb ON mb.id = mbvp.market_box_id " +
        "LEFT JOIN market m ON m.id = mb.market_id " +
        "WHERE v.id IS NOT NULL AND c.id IS NOT NULL AND mb.id IS NOT NULL " +
        "      AND c.id=:companyId AND m.id=:marketId",
        nativeQuery = true)
    List<MarketBoxVarietyProperty> findAllByCompanyAndMarket(
        @Param("companyId") Long companyId,
        @Param("marketId") Long marketId
    );
}
