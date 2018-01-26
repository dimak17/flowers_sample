package com.impltech.repository;

import com.impltech.domain.ClaimsPolicy;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ClaimsPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimsPolicyRepository extends JpaRepository<ClaimsPolicy,Long> {

    @Query("from ClaimsPolicy policy where policy.company.id = :idCompany")
    List<ClaimsPolicy> findAllByCompanyId(@Param("idCompany") Long idCompany);

    @Query("from ClaimsPolicy c where c.id = :id and c.company.id = :idCompany")
    ClaimsPolicy findOneByCompanyId(@Param("id") Long id, @Param("idCompany") Long idCompany);

    @Query(value = "select COUNT(*) as duplicate from claims_policy c  where  c.short_name = :name and c.id is not null and c.company_id = :idCompany", nativeQuery = true)
    Long checkDuplicateIfCreateClaimsPolicy(@Param("name") String name, @Param("idCompany") Long idCompany);

    @Query(value = "select COUNT(*) as duplicate from claims_policy c  where  c.short_name = :name and c.id <> :claims_id and c.company_id = :idCompany", nativeQuery = true)
    Long checkDuplicateIfUpdateClaimsPolicy(@Param("claims_id") Long id,@Param("name") String name, @Param("idCompany") Long idCompany);

    @Modifying
    @Query("delete from ClaimsPolicy с where с.company.id = :idCompany and с.id = :id")
    void deleteClaimsPolicyByCompanyId(@Param("idCompany") Long idCompany, @Param("id") Long id);
}
