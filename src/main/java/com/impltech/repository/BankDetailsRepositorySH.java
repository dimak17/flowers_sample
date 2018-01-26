package com.impltech.repository;

import com.impltech.domain.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the BankDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankDetailsRepositorySH extends JpaRepository<BankDetails,Long> {

    @Query("select v from BankDetails v where v.company.id=:id")
    BankDetails findBankDetailsByCompanyId(@Param("id")Long id);

}
