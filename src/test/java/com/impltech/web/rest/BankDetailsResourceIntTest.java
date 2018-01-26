package com.impltech.web.rest;

import com.impltech.FlowersApp;
import com.impltech.domain.BankDetails;
import com.impltech.repository.BankDetailsRepositorySH;
import com.impltech.service.BankDetailsServiceSH;
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
 * Test class for the BankDetailsResource REST controller.
 *
 * @see BankDetailsResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class BankDetailsResourceIntTest {

    private static final String DEFAULT_GENERAL = "AAAAAAAAAA";
    private static final String UPDATED_GENERAL = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATIVE = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATIVE = "BBBBBBBBBB";

    @Autowired
    private BankDetailsRepositorySH bankDetailsRepository;

    @Autowired
    private BankDetailsServiceSH bankDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBankDetailsMockMvc;

    private BankDetails bankDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankDetailsResourceSH bankDetailsResource = new BankDetailsResourceSH(bankDetailsService);
        this.restBankDetailsMockMvc = MockMvcBuilders.standaloneSetup(bankDetailsResource)
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
    public static BankDetails createEntity(EntityManager em) {
        BankDetails bankDetails = new BankDetails()
            .general(DEFAULT_GENERAL)
            .alternative(DEFAULT_ALTERNATIVE);
        return bankDetails;
    }

    @Before
    public void initTest() {
        bankDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankDetails() throws Exception {
        int databaseSizeBeforeCreate = bankDetailsRepository.findAll().size();

        // Create the BankDetails
        restBankDetailsMockMvc.perform(post("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankDetails)))
            .andExpect(status().isCreated());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getGeneral()).isEqualTo(DEFAULT_GENERAL);
        assertThat(testBankDetails.getAlternative()).isEqualTo(DEFAULT_ALTERNATIVE);
    }

    @Test
    @Transactional
    public void createBankDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankDetailsRepository.findAll().size();

        // Create the BankDetails with an existing ID
        bankDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankDetailsMockMvc.perform(post("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList
        restBankDetailsMockMvc.perform(get("/api/bank-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].general").value(hasItem(DEFAULT_GENERAL.toString())))
            .andExpect(jsonPath("$.[*].alternative").value(hasItem(DEFAULT_ALTERNATIVE.toString())));
    }

    @Test
    @Transactional
    public void getBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get the bankDetails
        restBankDetailsMockMvc.perform(get("/api/bank-details/{id}", bankDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankDetails.getId().intValue()))
            .andExpect(jsonPath("$.general").value(DEFAULT_GENERAL.toString()))
            .andExpect(jsonPath("$.alternative").value(DEFAULT_ALTERNATIVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankDetails() throws Exception {
        // Get the bankDetails
        restBankDetailsMockMvc.perform(get("/api/bank-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankDetails() throws Exception {
        // Initialize the database
        bankDetailsService.save(bankDetails);

        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Update the bankDetails
        BankDetails updatedBankDetails = bankDetailsRepository.findOne(bankDetails.getId());
        updatedBankDetails
            .general(UPDATED_GENERAL)
            .alternative(UPDATED_ALTERNATIVE);

        restBankDetailsMockMvc.perform(put("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankDetails)))
            .andExpect(status().isOk());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getGeneral()).isEqualTo(UPDATED_GENERAL);
        assertThat(testBankDetails.getAlternative()).isEqualTo(UPDATED_ALTERNATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Create the BankDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBankDetailsMockMvc.perform(put("/api/bank-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankDetails)))
            .andExpect(status().isCreated());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBankDetails() throws Exception {
        // Initialize the database
        bankDetailsService.save(bankDetails);

        int databaseSizeBeforeDelete = bankDetailsRepository.findAll().size();

        // Get the bankDetails
        restBankDetailsMockMvc.perform(delete("/api/bank-details/{id}", bankDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDetails.class);
        BankDetails bankDetails1 = new BankDetails();
        bankDetails1.setId(1L);
        BankDetails bankDetails2 = new BankDetails();
        bankDetails2.setId(bankDetails1.getId());
        assertThat(bankDetails1).isEqualTo(bankDetails2);
        bankDetails2.setId(2L);
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
        bankDetails1.setId(null);
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
    }
}
