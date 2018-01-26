package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.CompanyUser;
import com.impltech.repository.CompanyUserRepository;
import com.impltech.service.CompanyUserService;
import com.impltech.service.UserService;
import com.impltech.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyUserResource REST controller.
 *
 * @see CompanyUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class CompanyUserResourceIntTest {

    private static final String DEFAULT_SKYPE = "AAAAAAAAAA";
    private static final String UPDATED_SKYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PATRONYMIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATRONYMIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WHATS_UP = "AAAAAAAAAA";
    private static final String UPDATED_WHATS_UP = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_PHONE = "BBBBBBBBBB";

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyUserMockMvc;

    private CompanyUser companyUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyUserResource companyUserResource = new CompanyUserResource(companyUserService, userService);
        this.restCompanyUserMockMvc = MockMvcBuilders.standaloneSetup(companyUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUser createEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser()
            .skype(DEFAULT_SKYPE)
            .fullName(DEFAULT_PATRONYMIC_NAME)
            .workEmail(DEFAULT_SECONDARY_EMAIL)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .whatsUp(DEFAULT_WHATS_UP)
            .officePhone(DEFAULT_OFFICE_PHONE);
        return companyUser;
    }

    @Before
    public void initTest() {
        companyUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyUser() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();

        // Create the CompanyUser
        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyUser)))
            .andExpect(status().isCreated());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getSkype()).isEqualTo(DEFAULT_SKYPE);
        assertThat(testCompanyUser.getFullName()).isEqualTo(DEFAULT_PATRONYMIC_NAME);
        assertThat(testCompanyUser.getWorkEmail()).isEqualTo(DEFAULT_SECONDARY_EMAIL);
        assertThat(testCompanyUser.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testCompanyUser.getWhatsUp()).isEqualTo(DEFAULT_WHATS_UP);
        assertThat(testCompanyUser.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
    }

    @Test
    @Transactional
    public void createCompanyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();

        // Create the CompanyUser with an existing ID
        companyUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyUsers() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].skype").value(hasItem(DEFAULT_SKYPE.toString())))
            .andExpect(jsonPath("$.[*].patronymicName").value(hasItem(DEFAULT_PATRONYMIC_NAME.toString())))
            .andExpect(jsonPath("$.[*].secondaryEmail").value(hasItem(DEFAULT_SECONDARY_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].secondPhone").value(hasItem(DEFAULT_SECOND_PHONE.toString())))
            .andExpect(jsonPath("$.[*].whatsUp").value(hasItem(DEFAULT_WHATS_UP.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get the companyUser
        restCompanyUserMockMvc.perform(get("/api/company-users/{id}", companyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyUser.getId().intValue()))
            .andExpect(jsonPath("$.skype").value(DEFAULT_SKYPE.toString()))
            .andExpect(jsonPath("$.patronymicName").value(DEFAULT_PATRONYMIC_NAME.toString()))
            .andExpect(jsonPath("$.secondaryEmail").value(DEFAULT_SECONDARY_EMAIL.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.secondPhone").value(DEFAULT_SECOND_PHONE.toString()))
            .andExpect(jsonPath("$.whatsUp").value(DEFAULT_WHATS_UP.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyUser() throws Exception {
        // Get the companyUser
        restCompanyUserMockMvc.perform(get("/api/company-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyUser() throws Exception {
        // Initialize the database
        companyUserService.save(companyUser);

        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Update the companyUser
        CompanyUser updatedCompanyUser = companyUserRepository.findOne(companyUser.getId());
        updatedCompanyUser
            .skype(UPDATED_SKYPE)
            .fullName(UPDATED_PATRONYMIC_NAME)
            .workEmail(UPDATED_SECONDARY_EMAIL)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .whatsUp(UPDATED_WHATS_UP)
            .officePhone(UPDATED_OFFICE_PHONE);

        restCompanyUserMockMvc.perform(put("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyUser)))
            .andExpect(status().isOk());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getSkype()).isEqualTo(UPDATED_SKYPE);
        assertThat(testCompanyUser.getFullName()).isEqualTo(UPDATED_PATRONYMIC_NAME);
        assertThat(testCompanyUser.getWorkEmail()).isEqualTo(UPDATED_SECONDARY_EMAIL);
        assertThat(testCompanyUser.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testCompanyUser.getWhatsUp()).isEqualTo(UPDATED_WHATS_UP);
        assertThat(testCompanyUser.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Create the CompanyUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyUserMockMvc.perform(put("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyUser)))
            .andExpect(status().isCreated());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyUser() throws Exception {
        // Initialize the database
        companyUserService.save(companyUser);

        int databaseSizeBeforeDelete = companyUserRepository.findAll().size();

        // Get the companyUser
        restCompanyUserMockMvc.perform(delete("/api/company-users/{id}", companyUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyUser.class);
        CompanyUser companyUser1 = new CompanyUser();
        companyUser1.setId(1L);
        CompanyUser companyUser2 = new CompanyUser();
        companyUser2.setId(companyUser1.getId());
        assertThat(companyUser1).isEqualTo(companyUser2);
        companyUser2.setId(2L);
        assertThat(companyUser1).isNotEqualTo(companyUser2);
        companyUser1.setId(null);
        assertThat(companyUser1).isNotEqualTo(companyUser2);
    }
}
