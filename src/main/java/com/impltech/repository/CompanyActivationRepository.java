package com.impltech.repository;

import com.impltech.domain.CompanyActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by platon
 */
public interface CompanyActivationRepository extends JpaRepository<CompanyActivation, Long> {

    CompanyActivation findOneByActivationKey(String activationKey);

    @Modifying
    @Transactional
    @Query(value="delete from CompanyActivation c where c.activationKey = ?1")
    void deleteOneByActivationKey(String activationKey);

}
