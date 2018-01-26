package com.impltech.repository;

import com.impltech.domain.Disease;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Disease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiseaseRepository extends JpaRepository<Disease,Long> {

}
