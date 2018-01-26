package com.impltech.repository;

import com.impltech.domain.Market;
import com.impltech.domain.MarketBox;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import javax.transaction.Transactional;


/**
 * Spring Data JPA repository for the MarketBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketBoxRepository extends JpaRepository<MarketBox,Long> {

    @Modifying
    @Transactional
    @Query("delete from MarketBox mb where mb.market = ?1")
    void deleteByMarket(Market market);

}
