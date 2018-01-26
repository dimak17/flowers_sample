package com.impltech.service;

import com.impltech.domain.Authority;
import com.impltech.domain.CompanyUser;
import com.impltech.domain.Position;
import com.impltech.repository.CompanyUserRepository;
import com.impltech.security.SecurityUtils;
import com.impltech.service.mapper.UserMapper;
import org.hibernate.Hibernate;
import com.impltech.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Service Implementation for managing CompanyUser.
 */
@Service
@Transactional
public class CompanyUserService {

    private final Logger log = LoggerFactory.getLogger(CompanyUserService.class);

    private final CompanyUserRepository companyUserRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final MailService mailService;

    public CompanyUserService(CompanyUserRepository companyUserRepository, UserService userService, UserMapper userMapper, MailService mailService) {
        this.companyUserRepository = companyUserRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    /**
     * Save a companyUser.
     *
     * @param companyUser the entity to save
     * @return the persisted entity
     */
    public CompanyUser save(CompanyUser companyUser) {
        log.debug("Request to save CompanyUser : {}", companyUser);
        companyUser.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        String password = RandomUtil.generatePassword();
        log.info(password);
        companyUser.setUser(userService.createUser(companyUser.getAccountEmail(), password, companyUser.getFullName(), companyUser.getWorkEmail()));
        CompanyUser result = companyUserRepository.save(companyUser);
        mailService.sendCompanyUserCreation(result, password, "creationEmployeeEmail");
        return result;
    }

    /**
     * update a companyUser.
     *
     * @param companyUser the entity to save
     * @return the persisted entity
     */
    public CompanyUser update(CompanyUser companyUser) {
        Set<Authority> collect = companyUser.getPositions().stream().map(Position::getAuthority).collect(toSet());
        log.debug("Request to save CompanyUser : {}", companyUser);
        userService.updateUser(companyUser.getUser().getId(), companyUser.getAccountEmail(), companyUser.getFullName(), companyUser.getWorkEmail(), companyUser.getUser().getActivated(), collect);
        return companyUserRepository.save(companyUser);
    }

    /**
     *  Get all the companyUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyUser> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyUsers");
        return companyUserRepository.findAll(pageable);
    }

    /**
     *  Get one companyUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyUser findOne(Long id) {
        log.debug("Request to get CompanyUser : {}", id);
        return companyUserRepository.findOneWithEagerRelationships(id);
    }

    @Transactional(readOnly = true)
    public CompanyUser findOneByCurrentCompany(Long id) {
        log.debug("Request to get CompanyUser : {} by current company", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return companyUserRepository.findOneWithEagerByCurrentCompanyId(companyId, id);
    }

    @Transactional(readOnly = true)
    public List<CompanyUser> findAll(Long id) {
        return companyUserRepository.findByCompanyId(id);
    }

    @Transactional(readOnly = true)
    public List<CompanyUser> findAllByCurrentCompanyId() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return companyUserRepository.findAllCompanyUsersByCurrentCompanyId(companyId);
    }

    /**
     *  Delete the  companyUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyUser : {}", id);
        companyUserRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public CompanyUser findCurrentUserByCompany() {
        if(SecurityUtils.getCurrentCompanyUser() != null) {
            Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
            if (companyId != null) {
                CompanyUser companyUser = companyUserRepository.findByUserLoginByCompany(companyId, SecurityUtils.getCurrentUserLogin());
                if (companyUser != null) {
                    Hibernate.initialize(companyUser.getUser().getAuthorities());
                    return companyUser;
                }
            }
        }
        return null;
    }

    public void changeLogin(String login) {
        CompanyUser companyUser = SecurityUtils.getCurrentCompanyUser();
            companyUser.setAccountEmail(login);
            companyUser.getUser().setLogin(login);
            companyUserRepository.save(companyUser);

    }
}
