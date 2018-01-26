package com.impltech.repository;

import com.impltech.domain.ClientEmployeePosition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClientEmployeePosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientEmployeePositionRepository extends JpaRepository<ClientEmployeePosition,Long> {

}
