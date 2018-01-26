package com.impltech.web.rest;

import com.impltech.FlowersApp;
import com.impltech.domain.MarketVariety;
import com.impltech.repository.MarketVarietyRepositorySH;
import com.impltech.service.MarketVarietyServiceSH;
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

import com.impltech.domain.enumeration.MarketVarietyType;
/**
 * Test class for the MarketVarietyResource REST controller.
 *
 * @see MarketVarietyResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketVarietyResourceIntTest {

    private static final MarketVarietyType DEFAULT_TYPE = MarketVarietyType.PROHIBITED;
    private static final MarketVarietyType UPDATED_TYPE = MarketVarietyType.SPECIAL;

    @Autowired
    private MarketVarietyRepositorySH marketVarietyRepository;

    @Autowired
    private MarketVarietyServiceSH marketVarietyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketVarietyMockMvc;

    private MarketVariety marketVariety;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketVarietyResourceSH marketVarietyResource = new MarketVarietyResourceSH(marketVarietyService);
        this.restMarketVarietyMockMvc = MockMvcBuilders.standaloneSetup(marketVarietyResource)
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
    public static MarketVariety createEntity(EntityManager em) {
        MarketVariety marketVariety = new MarketVariety()
            .type(DEFAULT_TYPE);
        return marketVariety;
    }

    @Before
    public void initTest() {
        marketVariety = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketVariety() throws Exception {
        int databaseSizeBeforeCreate = marketVarietyRepository.findAll().size();

        // Create the MarketVariety
        restMarketVarietyMockMvc.perform(post("/api/market-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketVariety)))
            .andExpect(status().isCreated());

        // Validate the MarketVariety in the database
        List<MarketVariety> marketVarietyList = marketVarietyRepository.findAll();
        assertThat(marketVarietyList).hasSize(databaseSizeBeforeCreate + 1);
        MarketVariety testMarketVariety = marketVarietyList.get(marketVarietyList.size() - 1);
        assertThat(testMarketVariety.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createMarketVarietyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketVarietyRepository.findAll().size();

        // Create the MarketVariety with an existing ID
        marketVariety.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketVarietyMockMvc.perform(post("/api/market-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketVariety)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketVariety> marketVarietyList = marketVarietyRepository.findAll();
        assertThat(marketVarietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketVarieties() throws Exception {
        // Initialize the database
        marketVarietyRepository.saveAndFlush(marketVariety);

        // Get all the marketVarietyList
        restMarketVarietyMockMvc.perform(get("/api/market-varieties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketVariety.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMarketVariety() throws Exception {
        // Initialize the database
        marketVarietyRepository.saveAndFlush(marketVariety);

        // Get the marketVariety
        restMarketVarietyMockMvc.perform(get("/api/market-varieties/{id}", marketVariety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketVariety.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketVariety() throws Exception {
        // Get the marketVariety
        restMarketVarietyMockMvc.perform(get("/api/market-varieties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketVariety() throws Exception {
        // Initialize the database
        marketVarietyService.save(marketVariety);

        int databaseSizeBeforeUpdate = marketVarietyRepository.findAll().size();

        // Update the marketVariety
        MarketVariety updatedMarketVariety = marketVarietyRepository.findOne(marketVariety.getId());
        updatedMarketVariety
            .type(UPDATED_TYPE);

        restMarketVarietyMockMvc.perform(put("/api/market-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketVariety)))
            .andExpect(status().isOk());

        // Validate the MarketVariety in the database
        List<MarketVariety> marketVarietyList = marketVarietyRepository.findAll();
        assertThat(marketVarietyList).hasSize(databaseSizeBeforeUpdate);
        MarketVariety testMarketVariety = marketVarietyList.get(marketVarietyList.size() - 1);
        assertThat(testMarketVariety.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketVariety() throws Exception {
        int databaseSizeBeforeUpdate = marketVarietyRepository.findAll().size();

        // Create the MarketVariety

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketVarietyMockMvc.perform(put("/api/market-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketVariety)))
            .andExpect(status().isCreated());

        // Validate the MarketVariety in the database
        List<MarketVariety> marketVarietyList = marketVarietyRepository.findAll();
        assertThat(marketVarietyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketVariety() throws Exception {
        // Initialize the database
        marketVarietyService.save(marketVariety);

        int databaseSizeBeforeDelete = marketVarietyRepository.findAll().size();

        // Get the marketVariety
        restMarketVarietyMockMvc.perform(delete("/api/market-varieties/{id}", marketVariety.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketVariety> marketVarietyList = marketVarietyRepository.findAll();
        assertThat(marketVarietyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketVariety.class);
        MarketVariety marketVariety1 = new MarketVariety();
        marketVariety1.setId(1L);
        MarketVariety marketVariety2 = new MarketVariety();
        marketVariety2.setId(marketVariety1.getId());
        assertThat(marketVariety1).isEqualTo(marketVariety2);
        marketVariety2.setId(2L);
        assertThat(marketVariety1).isNotEqualTo(marketVariety2);
        marketVariety1.setId(null);
        assertThat(marketVariety1).isNotEqualTo(marketVariety2);
    }
}
