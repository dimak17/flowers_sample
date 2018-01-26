package com.impltech.repository;

import com.impltech.domain.MarketSeason;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MarketSeason entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSeasonRepository extends JpaRepository<MarketSeason,Long> {

    @Query(value = "SELECT * FROM market_season ms " +
        "LEFT JOIN market m ON ms.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE c.id=?1", nativeQuery = true)
    List<MarketSeason> findAllByCurrentCompany (Long companyId);


    @Query(value = "SELECT * FROM market_season ms " +
        "LEFT JOIN market m ON ms.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE c.id=?1 AND ms.id =?2 limit 1" , nativeQuery = true)
    MarketSeason findOneByCurrentCompany (Long companyId, Long id);



    @Query(value = "SELECT  * FROM market_season ms " +
        "LEFT JOIN market m ON ms.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE ms.season_id =:seasonId AND c.id =:companyId", nativeQuery = true)
    List<MarketSeason> findAllBySeasonIdAndCompanyId(@Param("seasonId") Long seasonId, @Param("companyId") Long companyId);
}
