package com.impltech.service.mapper;

import com.impltech.domain.Company;
import com.impltech.domain.CompanyUser;
import com.impltech.service.dto.CompanyUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CompanyUser and its DTO CompanyUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface CompanyUserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.farmName", target = "companyFarmName")
    CompanyUserDTO companyUserToCompanyUserDTO(CompanyUser companyUser);

    List<CompanyUserDTO> companyUsersToCompanyUserDTOs(List<CompanyUser> companyUsers);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "companyId", target = "company")
    CompanyUser companyUserDTOToCompanyUser(CompanyUserDTO companyUserDTO);

    List<CompanyUser> companyUserDTOsToCompanyUsers(List<CompanyUserDTO> companyUserDTOs);

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
