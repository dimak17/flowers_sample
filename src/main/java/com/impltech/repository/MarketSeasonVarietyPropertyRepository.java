package com.impltech.repository;

import com.impltech.domain.MarketSeasonVarietyProperty;
import com.impltech.domain.enumeration.Length;
import com.impltech.domain.enumeration.PriceListType;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MarketSeasonVarietyProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSeasonVarietyPropertyRepository extends JpaRepository<MarketSeasonVarietyProperty,Long> {

    @Query("select m from MarketSeasonVarietyProperty m where m.marketSeason.id = :idMarketSeason " +
        "and m.variety.id = :idVariety " +
        "and m.shippingPolicy.id =:idShippingPolicy " +
        "and m.company.id =:currentCompanyId")
    List<MarketSeasonVarietyProperty> findAllBy(
        @Param("idMarketSeason")Long idMarketSeason,
        @Param("idVariety")Long idVariety,
        @Param("idShippingPolicy")Long idShippingPolicy,
        @Param("currentCompanyId")Long currentCompanyId);

    @Query("select id from MarketSeasonVarietyProperty m where m.marketSeason.id = :marketSeasonId " +
        "and m.variety.id = :idVariety " +
        "and m.priceList.id = :priceListId " +
        "and m.shippingPolicy.id = :shippingPolicyId " +
        "and m.length = :lengthMSVP " +
        "and m.company.id =:currentCompanyId")
    Long findMarketSeasonVarietyPropertyIdBy(
        @Param("marketSeasonId")Long marketSeasonId,
        @Param("idVariety")Long idVariety,
        @Param("priceListId")Long priceListId,
        @Param("shippingPolicyId")Long shippingPolicyId,
        @Param("lengthMSVP") Length length,
        @Param("currentCompanyId")Long currentCompanyId);


    @Query("from MarketSeasonVarietyProperty m where m.company.id = ?1 and m.priceList.type = ?2")
    List<MarketSeasonVarietyProperty> findAllBy(Long companyId, PriceListType type);


    @Query(value = "SELECT * FROM market_season_variety_property msvp " +
        "LEFT JOIN market_season ms ON msvp.market_season_id = ms.id " +
        "LEFT JOIN shipping_policy shp ON msvp.shipping_policy_id = shp.id " +
        "LEFT JOIN price_list pl ON msvp.price_list_id = pl.id " +
        "LEFT JOIN variety v ON msvp.variety_id = v.id " +
        "LEFT JOIN type_of_flower tof ON v.type_of_flower_id = tof.id " +
        "WHERE pl.price_list_type=?2  AND ms.id=?1 AND  tof.id =?3 and shp.id =?4 ", nativeQuery = true)
    List<MarketSeasonVarietyProperty> findAllBy(
        Long marketSeasonId, String type, Long typeOfFlowersId, Long shippingPolicyId);

    @Modifying
    @Query("delete from MarketSeasonVarietyProperty m where m.company.id =:companyId and m.id =:id")
    void deleteByCompanyId(@Param("companyId") Long companyId, @Param("id") Long id);

}
