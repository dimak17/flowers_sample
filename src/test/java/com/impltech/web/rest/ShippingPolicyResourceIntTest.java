package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.ShippingPolicy;
import com.impltech.repository.ShippingPolicyRepository;
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
 * Test class for the ShippingPolicyResource REST controller.
 *
 * @see ShippingPolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class ShippingPolicyResourceIntTest {

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    @Autowired
    private ShippingPolicyRepository shippingPolicyRepository;

    @Autowired
    private ShippingPolicyService shippingPolicyService;


    @Autowired
    private ClaimsPolicyService claimsPolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShippingPolicyMockMvc;

    private ShippingPolicy shippingPolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShippingPolicyResource shippingPolicyResource = new ShippingPolicyResource(shippingPolicyService, claimsPolicyService);
        this.restShippingPolicyMockMvc = MockMvcBuilders.standaloneSetup(shippingPolicyResource)
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
    public static ShippingPolicy createEntity(EntityManager em) {
        ShippingPolicy shippingPolicy = new ShippingPolicy()
            .shortName(DEFAULT_SHORT_NAME)
            .fullName(DEFAULT_FULL_NAME);
        return shippingPolicy;
    }

    @Before
    public void initTest() {
        shippingPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingPolicy() throws Exception {
        int databaseSizeBeforeCreate = shippingPolicyRepository.findAll().size();

        // Create the ShippingPolicy
        restShippingPolicyMockMvc.perform(post("/api/shipping-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingPolicy)))
            .andExpect(status().isCreated());

        // Validate the ShippingPolicy in the database
        List<ShippingPolicy> shippingPolicyList = shippingPolicyRepository.findAll();
        assertThat(shippingPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingPolicy testShippingPolicy = shippingPolicyList.get(shippingPolicyList.size() - 1);
        assertThat(testShippingPolicy.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testShippingPolicy.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
    }

    @Test
    @Transactional
    public void createShippingPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingPolicyRepository.findAll().size();

        // Create the ShippingPolicy with an existing ID
        shippingPolicy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingPolicyMockMvc.perform(post("/api/shipping-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingPolicy)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShippingPolicy> shippingPolicyList = shippingPolicyRepository.findAll();
        assertThat(shippingPolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShippingPolicies() throws Exception {
        // Initialize the database
        shippingPolicyRepository.saveAndFlush(shippingPolicy);

        // Get all the shippingPolicyList
        restShippingPolicyMockMvc.perform(get("/api/shipping-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())));
    }

    @Test
    @Transactional
    public void getShippingPolicy() throws Exception {
        // Initialize the database
        shippingPolicyRepository.saveAndFlush(shippingPolicy);

        // Get the shippingPolicy
        restShippingPolicyMockMvc.perform(get("/api/shipping-policies/{id}", shippingPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingPolicy.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingPolicy() throws Exception {
        // Get the shippingPolicy
        restShippingPolicyMockMvc.perform(get("/api/shipping-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingPolicy() throws Exception {
        // Initialize the database
        shippingPolicyService.save(shippingPolicy);

        int databaseSizeBeforeUpdate = shippingPolicyRepository.findAll().size();

        // Update the shippingPolicy
        ShippingPolicy updatedShippingPolicy = shippingPolicyRepository.findOne(shippingPolicy.getId());
        updatedShippingPolicy
            .shortName(UPDATED_SHORT_NAME)
            .fullName(UPDATED_FULL_NAME);

        restShippingPolicyMockMvc.perform(put("/api/shipping-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShippingPolicy)))
            .andExpect(status().isOk());

        // Validate the ShippingPolicy in the database
        List<ShippingPolicy> shippingPolicyList = shippingPolicyRepository.findAll();
        assertThat(shippingPolicyList).hasSize(databaseSizeBeforeUpdate);
        ShippingPolicy testShippingPolicy = shippingPolicyList.get(shippingPolicyList.size() - 1);
        assertThat(testShippingPolicy.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testShippingPolicy.getFullName()).isEqualTo(UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = shippingPolicyRepository.findAll().size();

        // Create the ShippingPolicy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShippingPolicyMockMvc.perform(put("/api/shipping-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingPolicy)))
            .andExpect(status().isCreated());

        // Validate the ShippingPolicy in the database
        List<ShippingPolicy> shippingPolicyList = shippingPolicyRepository.findAll();
        assertThat(shippingPolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShippingPolicy() throws Exception {
        // Initialize the database
        shippingPolicyService.save(shippingPolicy);

        int databaseSizeBeforeDelete = shippingPolicyRepository.findAll().size();

        // Get the shippingPolicy
        restShippingPolicyMockMvc.perform(delete("/api/shipping-policies/{id}", shippingPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShippingPolicy> shippingPolicyList = shippingPolicyRepository.findAll();
        assertThat(shippingPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingPolicy.class);
        ShippingPolicy shippingPolicy1 = new ShippingPolicy();
        shippingPolicy1.setId(1L);
        ShippingPolicy shippingPolicy2 = new ShippingPolicy();
        shippingPolicy2.setId(shippingPolicy1.getId());
        assertThat(shippingPolicy1).isEqualTo(shippingPolicy2);
        shippingPolicy2.setId(2L);
        assertThat(shippingPolicy1).isNotEqualTo(shippingPolicy2);
        shippingPolicy1.setId(null);
        assertThat(shippingPolicy1).isNotEqualTo(shippingPolicy2);
    }
}
