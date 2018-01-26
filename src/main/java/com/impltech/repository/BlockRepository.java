package com.impltech.repository;

import com.impltech.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Block entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockRepository extends JpaRepository<Block,Long> {

    @Query("select distinct block from Block block left join fetch block.varieties")
    List<Block> findAllWithEagerRelationships();

    @Query("select block from Block block left join fetch block.varieties where block.id =:id")
    Block findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select id from block where block.name =:name and block.company_id =:idCompany limit 1", nativeQuery = true)
    Long checkBlockIdentityName(@Param("name") String name, @Param("idCompany") Long idCompany);

    @Query("select block from Block block where block.company.id = :idCompany")
    List<Block> findAllByCompanyId(@Param("idCompany") Long idCompany);

}
