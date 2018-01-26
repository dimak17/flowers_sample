package com.impltech.repository;

import com.impltech.domain.PinchVarietyProperty;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PinchVarietyProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinchVarietyPropertyRepository extends JpaRepository<PinchVarietyProperty,Long> {

}
