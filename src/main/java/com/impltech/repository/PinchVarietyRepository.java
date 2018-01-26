package com.impltech.repository;

import com.impltech.domain.PinchVariety;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PinchVariety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinchVarietyRepository extends JpaRepository<PinchVariety,Long> {

}
