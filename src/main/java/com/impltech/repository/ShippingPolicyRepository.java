package com.impltech.repository;

import com.impltech.domain.ShippingPolicy;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ShippingPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingPolicyRepository extends JpaRepository<ShippingPolicy, Long> {

    @Query("from ShippingPolicy policy where policy.company.id = :idCompany ")
    List<ShippingPolicy> findAllByCompanyId(@Param("idCompany") Long idCompany);


    @Query("from ShippingPolicy s where s.id = :id and s.company.id = :idCompany")
    ShippingPolicy findOneByCompanyId(@Param("id") Long id, @Param("idCompany") Long idCompany);

    @Query(value = "select COUNT(*) as duplicate from shipping_policy s  where  s.short_name = :name and s.id is not null and s.company_id = :idCompany", nativeQuery = true)
    Long checkDuplicateIfCreateShippingPolicy(@Param("name") String name, @Param("idCompany") Long idCompany);


   @Query(value = "select COUNT(*) as duplicate from shipping_policy s  where  s.short_name = :name and s.id <> :shipping_id and s.company_id = :idCompany", nativeQuery = true)
    Long checkDuplicateIfUpdateShippingPolicy(@Param("shipping_id") Long id, @Param("name") String name, @Param("idCompany") Long idCompany);

    @Modifying
    @Query("delete from ShippingPolicy s where s.company.id = :idCompany and s.id = :id")
    void deleteShippingPolicyByCompanyId(@Param("idCompany") Long idCompany, @Param("id") Long id);
   }
