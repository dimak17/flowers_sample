package com.impltech.repository;

import com.impltech.domain.PaymentPolicy;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the PaymentPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentPolicyRepository extends JpaRepository<PaymentPolicy,Long> {

    @Query("from PaymentPolicy c where c.id = :id and c.company.id = :idCompany")
    PaymentPolicy findOneByCompanyId(@Param("id") Long id, @Param("idCompany") Long idCompany);

    @Query("from PaymentPolicy p where p.company.id =:idCompany")
    List<PaymentPolicy> findAllByComapnyId(@Param("idCompany") Long idCompany);

}
