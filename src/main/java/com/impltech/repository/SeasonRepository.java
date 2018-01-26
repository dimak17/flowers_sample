package com.impltech.repository;

import com.impltech.domain.Season;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Season entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonRepository extends JpaRepository<Season,Long> {

    @Query("select distinct season from Season season left join fetch season.positions")
    List<Season> findAllWithEagerRelationships();

    @Query("select season from Season season left join fetch season.positions where season.id =:id")
    Season findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "SELECT * FROM  season s " +
        "LEFT JOIN market_season ms ON ms.season_id = s.id " +
        "LEFT JOIN market m ON ms.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE c.id=?1", nativeQuery = true)
    Set<Season> findAllByCurrentCompanyId(Long companyId);

    @Query(value = "SELECT * FROM  season s " +
        "LEFT JOIN market_season ms ON ms.season_id = s.id " +
        "LEFT JOIN market m ON ms.market_id = m.id " +
        "LEFT JOIN company c ON m.company_id = c.id " +
        "WHERE s.id =:id AND c.id =:companyId", nativeQuery = true)
    Season findOneByCurrentCompanyId(@Param("id") Long id, @Param("companyId") Long companyId);

    @Modifying
    @Query("delete from Season s where s.id =:id and s.company.id =:companyId")
    void deleteOneByCurrentCompanyId(@Param("id") Long id, @Param("companyId") Long companyId);
}
