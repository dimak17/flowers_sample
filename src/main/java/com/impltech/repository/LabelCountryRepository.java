package com.impltech.repository;

import com.impltech.domain.LabelCountry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LabelCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LabelCountryRepository extends JpaRepository<LabelCountry,Long> {

}
