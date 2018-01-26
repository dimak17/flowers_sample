package com.impltech.repository;

import com.impltech.domain.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the PriceList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceListRepositorySH extends JpaRepository<PriceList,Long> {



    @Query(value = "SELECT * FROM  price_list pl " +
        "LEFT JOIN market_variety_property_price_list mvp ON mvp.price_list_id = pl.id " +
        "LEFT JOIN market m ON mvp.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE pl.price_list_type=?2 AND c.id=?1 limit 1", nativeQuery = true)
    PriceList findPriceListByPriceListTyeAndCurrentCompanyId(Long companyId, String type);


    @Query(value = "SELECT * FROM  price_list pl " +
        "LEFT JOIN market_season_variety_property msvp ON msvp.price_list_id = pl.id " +
        "LEFT JOIN market_season ms ON msvp.market_season_id = ms.id " +
        "LEFT JOIN market m ON ms.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE pl.price_list_type=?2 AND c.id=?1 limit 1", nativeQuery = true)
    PriceList findByPriceListTypeAndCurrentCompanyId(Long companyId, String type);

}
