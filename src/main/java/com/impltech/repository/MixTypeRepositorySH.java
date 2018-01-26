package com.impltech.repository;

import com.impltech.domain.MixType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MixType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MixTypeRepositorySH extends JpaRepository<MixType,Long> {

    @Query("from MixType c where c.company.id = ?1")
    List<MixType> findByCompanyId(Long id);


    @Query(value = "select id from mix_type where mix_type.name =:name and mix_type.company_id =:idCompany limit 1", nativeQuery = true)
    Long checkMixTypeName(@Param("name") String name, @Param("idCompany") Long idCompany);
}
