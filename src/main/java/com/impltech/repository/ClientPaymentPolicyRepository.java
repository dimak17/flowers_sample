package com.impltech.repository;

import com.impltech.domain.ClientPaymentPolicy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClientPaymentPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientPaymentPolicyRepository extends JpaRepository<ClientPaymentPolicy,Long> {

}
