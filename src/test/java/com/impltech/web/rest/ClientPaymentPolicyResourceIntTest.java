package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.ClientPaymentPolicy;
import com.impltech.repository.ClientPaymentPolicyRepository;
import com.impltech.service.ClientPaymentPolicyService;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientPaymentPolicyResource REST controller.
 *
 * @see ClientPaymentPolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class ClientPaymentPolicyResourceIntTest {

    private static final Long DEFAULT_TERMS_IN_DAYS = 1L;
    private static final Long UPDATED_TERMS_IN_DAYS = 2L;

    private static final BigDecimal DEFAULT_TERMS_IN_SUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_TERMS_IN_SUM = new BigDecimal(2);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private ClientPaymentPolicyRepository clientPaymentPolicyRepository;

    @Autowired
    private ClientPaymentPolicyService clientPaymentPolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientPaymentPolicyMockMvc;

    private ClientPaymentPolicy clientPaymentPolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientPaymentPolicyResource clientPaymentPolicyResource = new ClientPaymentPolicyResource(clientPaymentPolicyService);
        this.restClientPaymentPolicyMockMvc = MockMvcBuilders.standaloneSetup(clientPaymentPolicyResource)
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
    public static ClientPaymentPolicy createEntity(EntityManager em) {
        ClientPaymentPolicy clientPaymentPolicy = new ClientPaymentPolicy()
            .termsInDays(DEFAULT_TERMS_IN_DAYS)
            .termsInSum(DEFAULT_TERMS_IN_SUM)
            .status(DEFAULT_STATUS);
        return clientPaymentPolicy;
    }

    @Before
    public void initTest() {
        clientPaymentPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientPaymentPolicy() throws Exception {
        int databaseSizeBeforeCreate = clientPaymentPolicyRepository.findAll().size();

        // Create the ClientPaymentPolicy
        restClientPaymentPolicyMockMvc.perform(post("/api/client-payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPaymentPolicy)))
            .andExpect(status().isCreated());

        // Validate the ClientPaymentPolicy in the database
        List<ClientPaymentPolicy> clientPaymentPolicyList = clientPaymentPolicyRepository.findAll();
        assertThat(clientPaymentPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        ClientPaymentPolicy testClientPaymentPolicy = clientPaymentPolicyList.get(clientPaymentPolicyList.size() - 1);
        assertThat(testClientPaymentPolicy.getTermsInDays()).isEqualTo(DEFAULT_TERMS_IN_DAYS);
        assertThat(testClientPaymentPolicy.getTermsInSum()).isEqualTo(DEFAULT_TERMS_IN_SUM);
        assertThat(testClientPaymentPolicy.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createClientPaymentPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientPaymentPolicyRepository.findAll().size();

        // Create the ClientPaymentPolicy with an existing ID
        clientPaymentPolicy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientPaymentPolicyMockMvc.perform(post("/api/client-payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPaymentPolicy)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientPaymentPolicy> clientPaymentPolicyList = clientPaymentPolicyRepository.findAll();
        assertThat(clientPaymentPolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientPaymentPolicies() throws Exception {
        // Initialize the database
        clientPaymentPolicyRepository.saveAndFlush(clientPaymentPolicy);

        // Get all the clientPaymentPolicyList
        restClientPaymentPolicyMockMvc.perform(get("/api/client-payment-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientPaymentPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].termsInDays").value(hasItem(DEFAULT_TERMS_IN_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].termsInSum").value(hasItem(DEFAULT_TERMS_IN_SUM.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getClientPaymentPolicy() throws Exception {
        // Initialize the database
        clientPaymentPolicyRepository.saveAndFlush(clientPaymentPolicy);

        // Get the clientPaymentPolicy
        restClientPaymentPolicyMockMvc.perform(get("/api/client-payment-policies/{id}", clientPaymentPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientPaymentPolicy.getId().intValue()))
            .andExpect(jsonPath("$.termsInDays").value(DEFAULT_TERMS_IN_DAYS.intValue()))
            .andExpect(jsonPath("$.termsInSum").value(DEFAULT_TERMS_IN_SUM.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientPaymentPolicy() throws Exception {
        // Get the clientPaymentPolicy
        restClientPaymentPolicyMockMvc.perform(get("/api/client-payment-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientPaymentPolicy() throws Exception {
        // Initialize the database
        clientPaymentPolicyService.save(clientPaymentPolicy);

        int databaseSizeBeforeUpdate = clientPaymentPolicyRepository.findAll().size();

        // Update the clientPaymentPolicy
        ClientPaymentPolicy updatedClientPaymentPolicy = clientPaymentPolicyRepository.findOne(clientPaymentPolicy.getId());
        updatedClientPaymentPolicy
            .termsInDays(UPDATED_TERMS_IN_DAYS)
            .termsInSum(UPDATED_TERMS_IN_SUM)
            .status(UPDATED_STATUS);

        restClientPaymentPolicyMockMvc.perform(put("/api/client-payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientPaymentPolicy)))
            .andExpect(status().isOk());

        // Validate the ClientPaymentPolicy in the database
        List<ClientPaymentPolicy> clientPaymentPolicyList = clientPaymentPolicyRepository.findAll();
        assertThat(clientPaymentPolicyList).hasSize(databaseSizeBeforeUpdate);
        ClientPaymentPolicy testClientPaymentPolicy = clientPaymentPolicyList.get(clientPaymentPolicyList.size() - 1);
        assertThat(testClientPaymentPolicy.getTermsInDays()).isEqualTo(UPDATED_TERMS_IN_DAYS);
        assertThat(testClientPaymentPolicy.getTermsInSum()).isEqualTo(UPDATED_TERMS_IN_SUM);
        assertThat(testClientPaymentPolicy.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingClientPaymentPolicy() throws Exception {
        int databaseSizeBeforeUpdate = clientPaymentPolicyRepository.findAll().size();

        // Create the ClientPaymentPolicy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientPaymentPolicyMockMvc.perform(put("/api/client-payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPaymentPolicy)))
            .andExpect(status().isCreated());

        // Validate the ClientPaymentPolicy in the database
        List<ClientPaymentPolicy> clientPaymentPolicyList = clientPaymentPolicyRepository.findAll();
        assertThat(clientPaymentPolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientPaymentPolicy() throws Exception {
        // Initialize the database
        clientPaymentPolicyService.save(clientPaymentPolicy);

        int databaseSizeBeforeDelete = clientPaymentPolicyRepository.findAll().size();

        // Get the clientPaymentPolicy
        restClientPaymentPolicyMockMvc.perform(delete("/api/client-payment-policies/{id}", clientPaymentPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientPaymentPolicy> clientPaymentPolicyList = clientPaymentPolicyRepository.findAll();
        assertThat(clientPaymentPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPaymentPolicy.class);
        ClientPaymentPolicy clientPaymentPolicy1 = new ClientPaymentPolicy();
        clientPaymentPolicy1.setId(1L);
        ClientPaymentPolicy clientPaymentPolicy2 = new ClientPaymentPolicy();
        clientPaymentPolicy2.setId(clientPaymentPolicy1.getId());
        assertThat(clientPaymentPolicy1).isEqualTo(clientPaymentPolicy2);
        clientPaymentPolicy2.setId(2L);
        assertThat(clientPaymentPolicy1).isNotEqualTo(clientPaymentPolicy2);
        clientPaymentPolicy1.setId(null);
        assertThat(clientPaymentPolicy1).isNotEqualTo(clientPaymentPolicy2);
    }
}
