package com.impltech.repository;

import com.impltech.domain.Pinch;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pinch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinchRepository extends JpaRepository<Pinch,Long> {

}
