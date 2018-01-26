package com.impltech.repository;

import com.impltech.domain.MarketVarietyPropertyPriceList;
import com.impltech.domain.enumeration.Length;
import com.impltech.domain.enumeration.PriceListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MarketVarietyPropertyPriceList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketVarietyPropertyPriceListRepository extends JpaRepository<MarketVarietyPropertyPriceList, Long> {

    @Query("select m from MarketVarietyPropertyPriceList m where m.market.id = :idMarket " +
        "and m.variety.id = :idVariety " +
        "and m.shippingPolicy.id = :idShippingPolicy " +
        "and  m.priceList.type = :priceListType " +
        "and  m.company.id = :currentCompanyId")
    List<MarketVarietyPropertyPriceList> findAllBy(
        @Param("idMarket") Long idMarket,
        @Param("idVariety") Long idVariety,
        @Param("idShippingPolicy") Long idShippingPolicy,
        @Param("priceListType") PriceListType type,
        @Param("currentCompanyId") Long currentCompanyId);

    @Modifying
    @Query("delete from MarketVarietyPropertyPriceList m where m.company.id =:companyId and m.id =:id")
    void deleteByCompanyId(@Param("id") Long id, @Param("companyId") Long companyId);

    @Query("select id from MarketVarietyPropertyPriceList m where m.market.id = :idMarket " +
        "and m.variety.id = :idVariety " +
        "and m.priceList.id = :priceListId " +
        "and m.shippingPolicy.id = :shippingPolicyId " +
        "and m.length = :lengthMVP " +
        "and m.company.id = :currentCompanyId"   )
    Long findMarketVarietyPropertyIdBy(
        @Param("idMarket") Long idMarket,
        @Param("idVariety") Long idVariety,
        @Param("priceListId") Long priceListId,
        @Param("shippingPolicyId") Long shippingPolicyId,
        @Param("lengthMVP") Length length,
        @Param("currentCompanyId") Long currentCompanyId);

    @Query("from MarketVarietyPropertyPriceList m where m.company.id = ?1 and m.priceList.type = ?2")
    List<MarketVarietyPropertyPriceList> findAllBy(Long companyId, PriceListType type);

    @Query(value = "SELECT * FROM market_variety_property_price_list mvp " +
        "LEFT JOIN market m ON mvp.market_id = m.id " +
        "LEFT JOIN shipping_policy shp ON mvp.shipping_policy_id = shp.id " +
        "LEFT JOIN price_list pl ON mvp.price_list_id = pl.id " +
        "LEFT JOIN variety v ON mvp.variety_id = v.id " +
        "LEFT JOIN type_of_flower tof ON v.type_of_flower_id = tof.id " +
        "WHERE pl.price_list_type=?2  AND m.id=?1 AND  tof.id =?3 and shp.id =?4 ", nativeQuery = true)
    List<MarketVarietyPropertyPriceList> findAllBy(
        Long marketId, String type, Long typeOfFlowersId, Long shippingPolicyId);

}
