package com.impltech.repository;

import com.impltech.domain.TypeOfFlower;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the TypeOfFlower entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOfFlowerRepository extends JpaRepository<TypeOfFlower,Long> {

    @Query("select t from TypeOfFlower t where t.company.id =:id")
    List<TypeOfFlower> findTypeOfFlowersByIdCompany(@Param("id")Long id);
}
