package com.impltech.repository;

import com.impltech.domain.Variety;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Variety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VarietyRepository extends JpaRepository<Variety,Long> {

	@Query("select v from Variety v where v.company.id =:id")
	List<Variety>findVarietiesByIdCompany(@Param("id") Long id);

    @Query("select v from Variety v where v.company.id = ?1")
    List<Variety> findByCompanyId(Long id);

    @Query("select v from Variety v where v.company.id = :companyId and v.typeOfFlower.id = :id")
    List<Variety>findVarietiesByIdCompanyAndTypeOfFlower(@Param("companyId") Long companyId, @Param("id") Long id);

    @Query("select v from Variety v where v.company.id = :companyId and v.id = :id")
    Variety findOneByCompanyId(@Param("companyId") Long companyId, @Param("id") Long id);

    @Modifying
    @Query("delete from Variety v where v.company.id = :companyId and v.id = :id")
    void deleteVarietyByCompanyId(@Param("companyId") Long companyId, @Param("id") Long id);
}
