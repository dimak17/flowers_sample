package com.impltech.repository;

import com.impltech.domain.Destination;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Destination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DestinationRepository extends JpaRepository<Destination,Long> {

}
