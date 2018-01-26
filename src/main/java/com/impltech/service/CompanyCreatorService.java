package com.impltech.service;

import com.impltech.domain.*;
import com.impltech.repository.*;
import com.impltech.security.AuthoritiesConstants;
import com.impltech.service.dto.CompanyCreatorDTO;
import com.impltech.service.util.PositionsUtil;
import com.impltech.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author platon
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyCreatorService {

    private final Logger log = LoggerFactory.getLogger(CompanyCreatorService.class);

    private final MailService mailService;

    private final CompanyActivationRepository companyActivationRepository;

    private final UserService userService;

    private final AuthorityRepository authorityRepository;

    private final CompanyRepository companyRepository;

    private final CompanyUserRepository companyUserRepository;

    private final PositionRepository positionRepository;

    public CompanyCreatorService(MailService mailService, CompanyActivationRepository companyActivationRepository, UserService userService, AuthorityRepository authorityRepository, CompanyRepository companyRepository, CompanyUserRepository companyUserRepository, PositionRepository positionRepository) {
        this.mailService = mailService;
        this.companyActivationRepository = companyActivationRepository;
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.companyRepository = companyRepository;
        this.companyUserRepository = companyUserRepository;
        this.positionRepository = positionRepository;
    }

    /**
     * Activate a company.
     *
     * @param creator the entity to save
     */
    public Optional<CompanyUser> save(CompanyCreatorDTO creator) {
        //TODO fix language key
        log.debug("Creating new owner user : {}", creator);

        String password = RandomUtil.generatePassword();

        log.info("New company user login=" + creator.getEmail() + ";password=" + password);
        String login = creator.getEmail();

        User user = userService.createUser(
            login,
            password,
            creator.getFirstName(),
            creator.getSecondName(),
            creator.getEmail(),
            null,
            "es",
            true,
            Stream.of(AuthoritiesConstants.COMPANY_OWNER)
                .map(authorityRepository::findOne)
                .collect(Collectors.toSet())
        );

        Company company = companyRepository.save(new Company().farmName(creator.getCompanyName()));

        CompanyUser companyUser = new CompanyUser()
            .user(user)
            .company(company)
            .skype(creator.getSkype())
            .mobilePhone(creator.getPhone())
            .workEmail(creator.getSecondaryEmail())
            .fullName(creator.getLastName())
            .whatsUp(creator.getWhatsup());

        final CompanyUser savedUser = companyUserRepository.save(companyUser);

        positionRepository.save(PositionsUtil.getDefaultPositions4Company(company));
        Position ownerPosition = positionRepository.findOneByCompanyAndName(company, PositionsUtil.COMPANY_OWNER);
        List<Position> ownerPositions = new ArrayList<>();
        Set<CompanyUser> companyUsers = new HashSet<>();
        companyUsers.add(savedUser);
        ownerPosition.setCompanyUsers(companyUsers);
        ownerPositions.add(ownerPosition);
        positionRepository.save(ownerPosition);

        //Remove activation link
        companyActivationRepository.deleteOneByActivationKey(creator.getActivationKey());

        mailService.sendCompanyUserCreation(companyUser, password, "creationCompanyEmail");

        return Optional.of(companyUser);
    }

    public Optional<CompanyActivation> sendActivationCompanyEmail(String email) {
        CompanyActivation companyActivation = new CompanyActivation();
        companyActivation.setActivationKey(RandomUtil.generateActivationKey());
        companyActivation.setEmail(email);
        //TODO validation if we have activation with the same email
        CompanyActivation result = companyActivationRepository.save(companyActivation);
        mailService.sendCompanyActivationEmail(companyActivation);
        return Optional.of(result);
    }

    public Optional<User> createCompanyAccount(CompanyCreatorDTO companyCreatorDTO) {
        return null;
    }

    public boolean isActivationKeyExists(String key) {
        CompanyActivation companyActivation = companyActivationRepository.findOneByActivationKey(key);
        return companyActivation != null;
    }

}
