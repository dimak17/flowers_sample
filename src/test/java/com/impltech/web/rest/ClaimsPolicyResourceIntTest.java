package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.ClaimsPolicy;
import com.impltech.repository.ClaimsPolicyRepository;
import com.impltech.service.ClaimsPolicyService;
import com.impltech.service.ShippingPolicyService;
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
 * Test class for the ClaimsPolicyResource REST controller.
 *
 * @see ClaimsPolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class ClaimsPolicyResourceIntTest {

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    @Autowired
    private ClaimsPolicyRepository claimsPolicyRepository;

    @Autowired
    private ClaimsPolicyService claimsPolicyService;

    @Autowired
    private  ShippingPolicyService shippingPolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClaimsPolicyMockMvc;

    private ClaimsPolicy claimsPolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClaimsPolicyResource claimsPolicyResource = new ClaimsPolicyResource(claimsPolicyService, shippingPolicyService);
        this.restClaimsPolicyMockMvc = MockMvcBuilders.standaloneSetup(claimsPolicyResource)
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
    public static ClaimsPolicy createEntity(EntityManager em) {
        ClaimsPolicy claimsPolicy = new ClaimsPolicy()
            .shortName(DEFAULT_SHORT_NAME)
            .fullName(DEFAULT_FULL_NAME);
        return claimsPolicy;
    }

    @Before
    public void initTest() {
        claimsPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createClaimsPolicy() throws Exception {
        int databaseSizeBeforeCreate = claimsPolicyRepository.findAll().size();

        // Create the ClaimsPolicy
        restClaimsPolicyMockMvc.perform(post("/api/claims-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claimsPolicy)))
            .andExpect(status().isCreated());

        // Validate the ClaimsPolicy in the database
        List<ClaimsPolicy> claimsPolicyList = claimsPolicyRepository.findAll();
        assertThat(claimsPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimsPolicy testClaimsPolicy = claimsPolicyList.get(claimsPolicyList.size() - 1);
        assertThat(testClaimsPolicy.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void createClaimsPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = claimsPolicyRepository.findAll().size();

        // Create the ClaimsPolicy with an existing ID
        claimsPolicy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimsPolicyMockMvc.perform(post("/api/claims-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claimsPolicy)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClaimsPolicy> claimsPolicyList = claimsPolicyRepository.findAll();
        assertThat(claimsPolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClaimsPolicies() throws Exception {
        // Initialize the database
        claimsPolicyRepository.saveAndFlush(claimsPolicy);

        // Get all the claimsPolicyList
        restClaimsPolicyMockMvc.perform(get("/api/claims-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimsPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getClaimsPolicy() throws Exception {
        // Initialize the database
        claimsPolicyRepository.saveAndFlush(claimsPolicy);

        // Get the claimsPolicy
        restClaimsPolicyMockMvc.perform(get("/api/claims-policies/{id}", claimsPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(claimsPolicy.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClaimsPolicy() throws Exception {
        // Get the claimsPolicy
        restClaimsPolicyMockMvc.perform(get("/api/claims-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClaimsPolicy() throws Exception {
        // Initialize the database
        claimsPolicyService.save(claimsPolicy);

        int databaseSizeBeforeUpdate = claimsPolicyRepository.findAll().size();

        // Update the claimsPolicy
        ClaimsPolicy updatedClaimsPolicy = claimsPolicyRepository.findOne(claimsPolicy.getId());
        updatedClaimsPolicy
            .shortName(UPDATED_SHORT_NAME)
            .fullName(UPDATED_FULL_NAME);

        restClaimsPolicyMockMvc.perform(put("/api/claims-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClaimsPolicy)))
            .andExpect(status().isOk());

        // Validate the ClaimsPolicy in the database
        List<ClaimsPolicy> claimsPolicyList = claimsPolicyRepository.findAll();
        assertThat(claimsPolicyList).hasSize(databaseSizeBeforeUpdate);
        ClaimsPolicy testClaimsPolicy = claimsPolicyList.get(claimsPolicyList.size() - 1);
        assertThat(testClaimsPolicy.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingClaimsPolicy() throws Exception {
        int databaseSizeBeforeUpdate = claimsPolicyRepository.findAll().size();

        // Create the ClaimsPolicy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClaimsPolicyMockMvc.perform(put("/api/claims-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claimsPolicy)))
            .andExpect(status().isCreated());

        // Validate the ClaimsPolicy in the database
        List<ClaimsPolicy> claimsPolicyList = claimsPolicyRepository.findAll();
        assertThat(claimsPolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClaimsPolicy() throws Exception {
        // Initialize the database
        claimsPolicyService.save(claimsPolicy);

        int databaseSizeBeforeDelete = claimsPolicyRepository.findAll().size();

        // Get the claimsPolicy
        restClaimsPolicyMockMvc.perform(delete("/api/claims-policies/{id}", claimsPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClaimsPolicy> claimsPolicyList = claimsPolicyRepository.findAll();
        assertThat(claimsPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimsPolicy.class);
        ClaimsPolicy claimsPolicy1 = new ClaimsPolicy();
        claimsPolicy1.setId(1L);
        ClaimsPolicy claimsPolicy2 = new ClaimsPolicy();
        claimsPolicy2.setId(claimsPolicy1.getId());
        assertThat(claimsPolicy1).isEqualTo(claimsPolicy2);
        claimsPolicy2.setId(2L);
        assertThat(claimsPolicy1).isNotEqualTo(claimsPolicy2);
        claimsPolicy1.setId(null);
        assertThat(claimsPolicy1).isNotEqualTo(claimsPolicy2);
    }
}
