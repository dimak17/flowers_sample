package com.impltech.repository;

import com.impltech.domain.BoxType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the BoxType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxTypeRepository extends JpaRepository<BoxType,Long> {

    @Query("select box from BoxType box where box.company.id = :idCompany")
    Page<BoxType> findByCompanyId(@Param("idCompany") Long id, Pageable pageable);

    @Query("from BoxType b where b.company.id = ?1")
    List<BoxType> findByCompanyId(Long id);

    @Query("from BoxType b where b.id = :id and b.company.id = :idCompany")
    BoxType findOneByCompanyId(@Param("id") Long id, @Param("idCompany") Long idCompany);

    BoxType findOneByIdAndCompanyId(Long id, Long companyid);

    @Modifying
    @Query("delete from BoxType b where b.company.id = :idCompany and b.id = :id")
    void deleteBoxTypeByCompanyId(@Param("idCompany") Long companyId, @Param("id") Long id);

}
