package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.PaymentPolicy;
import com.impltech.repository.PaymentPolicyRepository;
import com.impltech.service.PaymentPolicyService;
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
 * Test class for the PaymentPolicyResource REST controller.
 *
 * @see PaymentPolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class PaymentPolicyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PaymentPolicyRepository paymentPolicyRepository;

    @Autowired
    private PaymentPolicyService paymentPolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentPolicyMockMvc;

    private PaymentPolicy paymentPolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentPolicyResource paymentPolicyResource = new PaymentPolicyResource(paymentPolicyService);
        this.restPaymentPolicyMockMvc = MockMvcBuilders.standaloneSetup(paymentPolicyResource)
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
    public static PaymentPolicy createEntity(EntityManager em) {
        PaymentPolicy paymentPolicy = new PaymentPolicy()
            .name(DEFAULT_NAME);
        return paymentPolicy;
    }

    @Before
    public void initTest() {
        paymentPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentPolicy() throws Exception {
        int databaseSizeBeforeCreate = paymentPolicyRepository.findAll().size();

        // Create the PaymentPolicy
        restPaymentPolicyMockMvc.perform(post("/api/payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentPolicy)))
            .andExpect(status().isCreated());

        // Validate the PaymentPolicy in the database
        List<PaymentPolicy> paymentPolicyList = paymentPolicyRepository.findAll();
        assertThat(paymentPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentPolicy testPaymentPolicy = paymentPolicyList.get(paymentPolicyList.size() - 1);
        assertThat(testPaymentPolicy.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPaymentPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentPolicyRepository.findAll().size();

        // Create the PaymentPolicy with an existing ID
        paymentPolicy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentPolicyMockMvc.perform(post("/api/payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentPolicy)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaymentPolicy> paymentPolicyList = paymentPolicyRepository.findAll();
        assertThat(paymentPolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentPolicies() throws Exception {
        // Initialize the database
        paymentPolicyRepository.saveAndFlush(paymentPolicy);

        // Get all the paymentPolicyList
        restPaymentPolicyMockMvc.perform(get("/api/payment-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPaymentPolicy() throws Exception {
        // Initialize the database
        paymentPolicyRepository.saveAndFlush(paymentPolicy);

        // Get the paymentPolicy
        restPaymentPolicyMockMvc.perform(get("/api/payment-policies/{id}", paymentPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentPolicy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentPolicy() throws Exception {
        // Get the paymentPolicy
        restPaymentPolicyMockMvc.perform(get("/api/payment-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentPolicy() throws Exception {
        // Initialize the database
        paymentPolicyService.save(paymentPolicy);

        int databaseSizeBeforeUpdate = paymentPolicyRepository.findAll().size();

        // Update the paymentPolicy
        PaymentPolicy updatedPaymentPolicy = paymentPolicyRepository.findOne(paymentPolicy.getId());
        updatedPaymentPolicy
            .name(UPDATED_NAME);

        restPaymentPolicyMockMvc.perform(put("/api/payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentPolicy)))
            .andExpect(status().isOk());

        // Validate the PaymentPolicy in the database
        List<PaymentPolicy> paymentPolicyList = paymentPolicyRepository.findAll();
        assertThat(paymentPolicyList).hasSize(databaseSizeBeforeUpdate);
        PaymentPolicy testPaymentPolicy = paymentPolicyList.get(paymentPolicyList.size() - 1);
        assertThat(testPaymentPolicy.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentPolicy() throws Exception {
        int databaseSizeBeforeUpdate = paymentPolicyRepository.findAll().size();

        // Create the PaymentPolicy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentPolicyMockMvc.perform(put("/api/payment-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentPolicy)))
            .andExpect(status().isCreated());

        // Validate the PaymentPolicy in the database
        List<PaymentPolicy> paymentPolicyList = paymentPolicyRepository.findAll();
        assertThat(paymentPolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentPolicy() throws Exception {
        // Initialize the database
        paymentPolicyService.save(paymentPolicy);

        int databaseSizeBeforeDelete = paymentPolicyRepository.findAll().size();

        // Get the paymentPolicy
        restPaymentPolicyMockMvc.perform(delete("/api/payment-policies/{id}", paymentPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentPolicy> paymentPolicyList = paymentPolicyRepository.findAll();
        assertThat(paymentPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentPolicy.class);
        PaymentPolicy paymentPolicy1 = new PaymentPolicy();
        paymentPolicy1.setId(1L);
        PaymentPolicy paymentPolicy2 = new PaymentPolicy();
        paymentPolicy2.setId(paymentPolicy1.getId());
        assertThat(paymentPolicy1).isEqualTo(paymentPolicy2);
        paymentPolicy2.setId(2L);
        assertThat(paymentPolicy1).isNotEqualTo(paymentPolicy2);
        paymentPolicy1.setId(null);
        assertThat(paymentPolicy1).isNotEqualTo(paymentPolicy2);
    }
}
