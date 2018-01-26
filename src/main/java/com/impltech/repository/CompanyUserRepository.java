package com.impltech.repository;

import com.impltech.domain.CompanyUser;
import com.impltech.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Spring Data JPA repository for the CompanyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser,Long> {

    @Query("select distinct company_user from CompanyUser company_user left join fetch company_user.positions")
    List<CompanyUser> findAllWithEagerRelationships();

    @Query("select company_user from CompanyUser company_user left join fetch company_user.positions where company_user.id =:id")
    CompanyUser findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select cu from CompanyUser cu left join fetch cu.positions left join fetch cu.markets where cu.company.id = :companyId and cu.id = :id ")
    CompanyUser findOneWithEagerByCurrentCompanyId(@Param("companyId") Long companyId, @Param("id") Long id);


    @Modifying
    @Transactional
    @Query("delete from CompanyUser c where c.user = ?1")
    void deleteByUserId(User userId);

    @Query("from CompanyUser c where c.user.login = ?1")
    CompanyUser findByUserLogin(String login);

    @Query("select cu from CompanyUser cu where cu.company.id = :companyId and cu.user.login = :login")
    CompanyUser findByUserLoginByCompany(@Param("companyId") Long companyId, @Param("login") String login);

    @Query("from CompanyUser c where c.company.id = ?1")
    List<CompanyUser> findByCompanyId(Long id);

    @Query("select cu from CompanyUser cu where cu.company.id = :companyId")
    List<CompanyUser> findAllCompanyUsersByCurrentCompanyId(@Param("companyId") Long companyId);

}
