package com.impltech.repository;

import com.impltech.domain.ClientEmployee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ClientEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientEmployeeRepository extends JpaRepository<ClientEmployee,Long> {

    @Query("select distinct client_employee from ClientEmployee client_employee left join fetch client_employee.clientEmployeePositions")
    List<ClientEmployee> findAllWithEagerRelationships();

    @Query("select client_employee from ClientEmployee client_employee left join fetch client_employee.clientEmployeePositions where client_employee.id =:id")
    ClientEmployee findOneWithEagerRelationships(@Param("id") Long id);

}
