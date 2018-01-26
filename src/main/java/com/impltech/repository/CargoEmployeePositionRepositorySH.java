package com.impltech.repository;

import com.impltech.domain.CargoEmployeePosition;
import com.impltech.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CargoEmployeePosition entity.
 */

@SuppressWarnings("unused")
@Repository
public interface CargoEmployeePositionRepositorySH extends JpaRepository<CargoEmployeePosition,Long> {
    List<CargoEmployeePosition> findAllByCompany (Company company);

    @Query("select p from CargoEmployeePosition p where p.company.id = :idCompany")
    List<CargoEmployeePosition> findAllByCurrentCompany (@Param("idCompany") Long idCompany);

    @Query("select p from CargoEmployeePosition p where p.id = :id and p.company.id = :idCompany")
    CargoEmployeePosition findOneByCompanyId(@Param("id") Long id, @Param("idCompany") Long idCompany);

    @Modifying
    @Query("delete from CargoEmployeePosition c where c.company.id =:idCompany and c.id = :id")
    void deleteCargoEmployeePositionByCurrentCompany (@Param("idCompany") Long idCompany, @Param("id") Long id);

    @Query(value = "select COUNT(*) as duplicate from cargo_employee_position cep  where  cep.name =:name and cep.id is not null and cep.company_id =:idCompany", nativeQuery = true)
    Long checkDuplicateIfCreateCargoEmployeePosition(@Param("name") String name, @Param("idCompany") Long idCompany);

    @Query(value = "select COUNT(*) as duplicate from cargo_employee_position cep  where  cep.name =:name and cep.id <> :cargo_employee_position_id and cep.company_id = :idCompany", nativeQuery = true)
    Long checkDuplicateIfUpdateCargoEmployeePosition(@Param("cargo_employee_position_id") Long id,@Param("name") String name, @Param("idCompany") Long idCompany);
}
