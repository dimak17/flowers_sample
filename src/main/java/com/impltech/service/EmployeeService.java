package com.impltech.service;

import com.impltech.domain.Company;
import com.impltech.domain.CompanyUser;
import com.impltech.domain.Position;
import com.impltech.domain.User;
import com.impltech.repository.CompanyUserRepository;
import com.impltech.repository.PositionRepository;
import com.impltech.repository.UserRepository;
import com.impltech.security.SecurityUtils;
import com.impltech.service.dto.EmployeeDTO;
import com.impltech.service.util.ConvertUtil;
import com.impltech.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by platon
 * Service Implementation for managing CompanyUser.
 */
@Service
@Transactional
public class EmployeeService {
    private final Logger log = LoggerFactory.getLogger(CompanyUserService.class);

    private final CompanyUserRepository companyUserRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final PositionRepository positionRepository;

    private final MailService mailService;


    public EmployeeService(
        CompanyUserRepository companyUserRepository,
        UserService userService,
        UserRepository userRepository,
        MailService mailService,
        PositionRepository positionRepository
    ) {
            this.companyUserRepository = companyUserRepository;
            this.userService = userService;
            this.userRepository = userRepository;
            this.positionRepository = positionRepository;
            this.mailService = mailService;
    }

    /**
     * Save an Employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    public EmployeeDTO save(final EmployeeDTO employeeDTO) {
        log.debug("Request to create Employee : {}", employeeDTO);

        String password = RandomUtil.generatePassword();
        //TODO language selection
        User savedUser =  userService.createUser(
            employeeDTO.getEmail(),
            password,
            employeeDTO.getFirstName(),
            employeeDTO.getSecondName(),
            employeeDTO.getEmail(),
            null,
            "es",
            true,
            employeeDTO.getPositions().stream().map(Position::getAuthority).collect(toSet())
        );

        Set<Position> positions = new HashSet<>(positionRepository.findAll(employeeDTO.getPositions().stream().map(Position::getId).collect(toList())));
        Company currentCompnay = companyUserRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin()).getCompany();
        CompanyUser companyUser = new CompanyUser()
            .company(currentCompnay)
            .mobilePhone(employeeDTO.getPhone())
            .whatsUp(employeeDTO.getWhatsUp())
            .skype(employeeDTO.getSkype())
            .user(savedUser)
            //to avoid detached conditions
            .positions(positions);

        CompanyUser savedCompanyUser = companyUserRepository.save(companyUser);

        positions.forEach(p -> p.getCompanyUsers().add(savedCompanyUser));
        positionRepository.save(positions);

        mailService.sendCompanyUserCreation(companyUser, password, "creationEmployeeEmail");
        //TODO send notification for us
        employeeDTO.setId(savedCompanyUser.getId());
        return employeeDTO;
    }

    /**
     * Save an Employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    public EmployeeDTO update(final EmployeeDTO employeeDTO) {
        log.debug("Request to update Employee : {}", employeeDTO);

//        //TODO language selection
        Optional<User> user = userRepository.findOneByEmail(employeeDTO.getEmail());
        final String[] loginBeforeUpdate = {null};
        user.ifPresent(u -> {
            loginBeforeUpdate[0] = u.getLogin();
            u.setLogin(employeeDTO.getEmail());
            u.setEmail(employeeDTO.getEmail());
            u.setFirstName(employeeDTO.getFirstName());
            u.setLastName(employeeDTO.getSecondName());
            //getting from repository to avoid detached condition
            u.setAuthorities(
                positionRepository.findAll(
                    employeeDTO.getPositions()
                        .stream()
                        .map(Position::getId)
                        .collect(toList())
                ).stream()
                .map(Position::getAuthority)
                .collect(toSet()));
            //update user with probably new fields
            userRepository.save(u);
        });

        CompanyUser companyUser = companyUserRepository.findByUserLogin(loginBeforeUpdate[0]);
        Optional.of(companyUser).ifPresent(cu ->
            cu
                .skype(employeeDTO.getSkype())
                .whatsUp(employeeDTO.getWhatsUp())
                .mobilePhone(employeeDTO.getPhone())
//                .positions(positions)
        );
        CompanyUser savedCompanyUser = companyUserRepository.save(companyUser);

        //Remove old positions from employees
        Set<Position> positions = companyUser.getPositions();
        positions
            .stream()
            .filter(pos -> employeeDTO.getPositions().stream().map(Position::getId).noneMatch(newPosId -> newPosId.equals(pos.getId())))
            .map(Position::getCompanyUsers)
            .forEach(users -> users.remove(savedCompanyUser));

        //Add new positions to employees
        employeeDTO.getPositions()
            .stream()
            .filter(newPos -> positions.stream().map(Position::getId).noneMatch(posId -> posId.equals(newPos.getId())))
            .forEach(newPos -> {
                Position persistedPos = positionRepository.findOne(newPos.getId());
                persistedPos.getCompanyUsers().add(savedCompanyUser);
                positions.add(persistedPos);
            });

        //new List to avoid ConcurrentModificationException
        positionRepository.save(new ArrayList<>(positions));
        // TODO send notification for us
        return employeeDTO;
    }

    /**
     *  Get all the employees.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        Page<CompanyUser> result = companyUserRepository.findAll(pageable);
        return result.map(ConvertUtil::companyUserToEmployeeDTO);
    }

    /**
     *  Get all the positions.
     *
     *  @return the list of positions
     */
    @Transactional(readOnly = true)
    public List<Position> findAllPositions() {
        log.debug("Request to get all Positions");
        Company currentCompany = companyUserRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin()).getCompany();
        List<Position> result = positionRepository.findOneByCompany(currentCompany);
        return result;
    }

    /**
     *  Get one Employee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EmployeeDTO findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        CompanyUser companyUser = companyUserRepository.findOne(id);
        return ConvertUtil.companyUserToEmployeeDTO(companyUser);
    }

    /**
     *  Delete the Employee by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee with id : {}", id);
        userService.deleteUser(companyUserRepository.findOne(id).getUser().getLogin());
    }


}
