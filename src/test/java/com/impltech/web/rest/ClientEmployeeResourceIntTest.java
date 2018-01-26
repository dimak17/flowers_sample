package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.ClientEmployee;
import com.impltech.repository.ClientEmployeeRepository;
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
 * Test class for the ClientEmployeeResource REST controller.
 *
 * @see ClientEmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class ClientEmployeeResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SKYPE = "AAAAAAAAAA";
    private static final String UPDATED_SKYPE = "BBBBBBBBBB";

    @Autowired
    private ClientEmployeeRepository clientEmployeeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientEmployeeMockMvc;

    private ClientEmployee clientEmployee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientEmployeeResource clientEmployeeResource = new ClientEmployeeResource(clientEmployeeRepository);
        this.restClientEmployeeMockMvc = MockMvcBuilders.standaloneSetup(clientEmployeeResource)
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
    public static ClientEmployee createEntity(EntityManager em) {
        ClientEmployee clientEmployee = new ClientEmployee()
            .fullName(DEFAULT_FULL_NAME)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .officePhone(DEFAULT_OFFICE_PHONE)
            .email(DEFAULT_EMAIL)
            .skype(DEFAULT_SKYPE);
        return clientEmployee;
    }

    @Before
    public void initTest() {
        clientEmployee = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientEmployee() throws Exception {
        int databaseSizeBeforeCreate = clientEmployeeRepository.findAll().size();

        // Create the ClientEmployee
        restClientEmployeeMockMvc.perform(post("/api/client-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmployee)))
            .andExpect(status().isCreated());

        // Validate the ClientEmployee in the database
        List<ClientEmployee> clientEmployeeList = clientEmployeeRepository.findAll();
        assertThat(clientEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        ClientEmployee testClientEmployee = clientEmployeeList.get(clientEmployeeList.size() - 1);
        assertThat(testClientEmployee.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testClientEmployee.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testClientEmployee.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
        assertThat(testClientEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientEmployee.getSkype()).isEqualTo(DEFAULT_SKYPE);
    }

    @Test
    @Transactional
    public void createClientEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientEmployeeRepository.findAll().size();

        // Create the ClientEmployee with an existing ID
        clientEmployee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientEmployeeMockMvc.perform(post("/api/client-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientEmployee> clientEmployeeList = clientEmployeeRepository.findAll();
        assertThat(clientEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientEmployees() throws Exception {
        // Initialize the database
        clientEmployeeRepository.saveAndFlush(clientEmployee);

        // Get all the clientEmployeeList
        restClientEmployeeMockMvc.perform(get("/api/client-employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].skype").value(hasItem(DEFAULT_SKYPE.toString())));
    }

    @Test
    @Transactional
    public void getClientEmployee() throws Exception {
        // Initialize the database
        clientEmployeeRepository.saveAndFlush(clientEmployee);

        // Get the clientEmployee
        restClientEmployeeMockMvc.perform(get("/api/client-employees/{id}", clientEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientEmployee.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.skype").value(DEFAULT_SKYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientEmployee() throws Exception {
        // Get the clientEmployee
        restClientEmployeeMockMvc.perform(get("/api/client-employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientEmployee() throws Exception {
        // Initialize the database
        clientEmployeeRepository.saveAndFlush(clientEmployee);
        int databaseSizeBeforeUpdate = clientEmployeeRepository.findAll().size();

        // Update the clientEmployee
        ClientEmployee updatedClientEmployee = clientEmployeeRepository.findOne(clientEmployee.getId());
        updatedClientEmployee
            .fullName(UPDATED_FULL_NAME)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .officePhone(UPDATED_OFFICE_PHONE)
            .email(UPDATED_EMAIL)
            .skype(UPDATED_SKYPE);

        restClientEmployeeMockMvc.perform(put("/api/client-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientEmployee)))
            .andExpect(status().isOk());

        // Validate the ClientEmployee in the database
        List<ClientEmployee> clientEmployeeList = clientEmployeeRepository.findAll();
        assertThat(clientEmployeeList).hasSize(databaseSizeBeforeUpdate);
        ClientEmployee testClientEmployee = clientEmployeeList.get(clientEmployeeList.size() - 1);
        assertThat(testClientEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testClientEmployee.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testClientEmployee.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
        assertThat(testClientEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientEmployee.getSkype()).isEqualTo(UPDATED_SKYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingClientEmployee() throws Exception {
        int databaseSizeBeforeUpdate = clientEmployeeRepository.findAll().size();

        // Create the ClientEmployee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientEmployeeMockMvc.perform(put("/api/client-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmployee)))
            .andExpect(status().isCreated());

        // Validate the ClientEmployee in the database
        List<ClientEmployee> clientEmployeeList = clientEmployeeRepository.findAll();
        assertThat(clientEmployeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientEmployee() throws Exception {
        // Initialize the database
        clientEmployeeRepository.saveAndFlush(clientEmployee);
        int databaseSizeBeforeDelete = clientEmployeeRepository.findAll().size();

        // Get the clientEmployee
        restClientEmployeeMockMvc.perform(delete("/api/client-employees/{id}", clientEmployee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientEmployee> clientEmployeeList = clientEmployeeRepository.findAll();
        assertThat(clientEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientEmployee.class);
        ClientEmployee clientEmployee1 = new ClientEmployee();
        clientEmployee1.setId(1L);
        ClientEmployee clientEmployee2 = new ClientEmployee();
        clientEmployee2.setId(clientEmployee1.getId());
        assertThat(clientEmployee1).isEqualTo(clientEmployee2);
        clientEmployee2.setId(2L);
        assertThat(clientEmployee1).isNotEqualTo(clientEmployee2);
        clientEmployee1.setId(null);
        assertThat(clientEmployee1).isNotEqualTo(clientEmployee2);
    }
}
