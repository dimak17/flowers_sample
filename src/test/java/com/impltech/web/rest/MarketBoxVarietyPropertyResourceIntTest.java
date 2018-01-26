package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.MarketBoxVarietyProperty;
import com.impltech.repository.MarketBoxVarietyPropertyRepository;
import com.impltech.service.MarketBoxVarietyPropertyService;
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

import com.impltech.domain.enumeration.Length;
/**
 * Test class for the MarketBoxVarietyPropertyResource REST controller.
 *
 * @see MarketBoxVarietyPropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketBoxVarietyPropertyResourceIntTest {

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Length DEFAULT_LENGTH = Length._40;
    private static final Length UPDATED_LENGTH = Length._50;

    @Autowired
    private MarketBoxVarietyPropertyRepository marketBoxVarietyPropertyRepository;

    @Autowired
    private MarketBoxVarietyPropertyService marketBoxVarietyPropertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketBoxVarietyPropertyMockMvc;

    private MarketBoxVarietyProperty marketBoxVarietyProperty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketBoxVarietyPropertyResource marketBoxVarietyPropertyResource = new MarketBoxVarietyPropertyResource(marketBoxVarietyPropertyService);
        this.restMarketBoxVarietyPropertyMockMvc = MockMvcBuilders.standaloneSetup(marketBoxVarietyPropertyResource)
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
    public static MarketBoxVarietyProperty createEntity(EntityManager em) {
        MarketBoxVarietyProperty marketBoxVarietyProperty = new MarketBoxVarietyProperty()
            .capacity(DEFAULT_CAPACITY)
            .length(DEFAULT_LENGTH);
        return marketBoxVarietyProperty;
    }

    @Before
    public void initTest() {
        marketBoxVarietyProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketBoxVarietyProperty() throws Exception {
        int databaseSizeBeforeCreate = marketBoxVarietyPropertyRepository.findAll().size();

        // Create the MarketBoxVarietyProperty
        restMarketBoxVarietyPropertyMockMvc.perform(post("/api/market-box-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketBoxVarietyProperty)))
            .andExpect(status().isCreated());

        // Validate the MarketBoxVarietyProperty in the database
        List<MarketBoxVarietyProperty> marketBoxVarietyPropertyList = marketBoxVarietyPropertyRepository.findAll();
        assertThat(marketBoxVarietyPropertyList).hasSize(databaseSizeBeforeCreate + 1);
        MarketBoxVarietyProperty testMarketBoxVarietyProperty = marketBoxVarietyPropertyList.get(marketBoxVarietyPropertyList.size() - 1);
        assertThat(testMarketBoxVarietyProperty.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testMarketBoxVarietyProperty.getLength()).isEqualTo(DEFAULT_LENGTH);
    }

    @Test
    @Transactional
    public void createMarketBoxVarietyPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketBoxVarietyPropertyRepository.findAll().size();

        // Create the MarketBoxVarietyProperty with an existing ID
        marketBoxVarietyProperty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketBoxVarietyPropertyMockMvc.perform(post("/api/market-box-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketBoxVarietyProperty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketBoxVarietyProperty> marketBoxVarietyPropertyList = marketBoxVarietyPropertyRepository.findAll();
        assertThat(marketBoxVarietyPropertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketBoxVarietyProperties() throws Exception {
        // Initialize the database
        marketBoxVarietyPropertyRepository.saveAndFlush(marketBoxVarietyProperty);

        // Get all the marketBoxVarietyPropertyList
        restMarketBoxVarietyPropertyMockMvc.perform(get("/api/market-box-variety-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketBoxVarietyProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())));
    }

    @Test
    @Transactional
    public void getMarketBoxVarietyProperty() throws Exception {
        // Initialize the database
        marketBoxVarietyPropertyRepository.saveAndFlush(marketBoxVarietyProperty);

        // Get the marketBoxVarietyProperty
        restMarketBoxVarietyPropertyMockMvc.perform(get("/api/market-box-variety-properties/{id}", marketBoxVarietyProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketBoxVarietyProperty.getId().intValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketBoxVarietyProperty() throws Exception {
        // Get the marketBoxVarietyProperty
        restMarketBoxVarietyPropertyMockMvc.perform(get("/api/market-box-variety-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketBoxVarietyProperty() throws Exception {
        // Initialize the database
        marketBoxVarietyPropertyService.save(marketBoxVarietyProperty);

        int databaseSizeBeforeUpdate = marketBoxVarietyPropertyRepository.findAll().size();

        // Update the marketBoxVarietyProperty
        MarketBoxVarietyProperty updatedMarketBoxVarietyProperty = marketBoxVarietyPropertyRepository.findOne(marketBoxVarietyProperty.getId());
        updatedMarketBoxVarietyProperty
            .capacity(UPDATED_CAPACITY)
            .length(UPDATED_LENGTH);

        restMarketBoxVarietyPropertyMockMvc.perform(put("/api/market-box-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketBoxVarietyProperty)))
            .andExpect(status().isOk());

        // Validate the MarketBoxVarietyProperty in the database
        List<MarketBoxVarietyProperty> marketBoxVarietyPropertyList = marketBoxVarietyPropertyRepository.findAll();
        assertThat(marketBoxVarietyPropertyList).hasSize(databaseSizeBeforeUpdate);
        MarketBoxVarietyProperty testMarketBoxVarietyProperty = marketBoxVarietyPropertyList.get(marketBoxVarietyPropertyList.size() - 1);
        assertThat(testMarketBoxVarietyProperty.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testMarketBoxVarietyProperty.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketBoxVarietyProperty() throws Exception {
        int databaseSizeBeforeUpdate = marketBoxVarietyPropertyRepository.findAll().size();

        // Create the MarketBoxVarietyProperty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketBoxVarietyPropertyMockMvc.perform(put("/api/market-box-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketBoxVarietyProperty)))
            .andExpect(status().isCreated());

        // Validate the MarketBoxVarietyProperty in the database
        List<MarketBoxVarietyProperty> marketBoxVarietyPropertyList = marketBoxVarietyPropertyRepository.findAll();
        assertThat(marketBoxVarietyPropertyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketBoxVarietyProperty() throws Exception {
        // Initialize the database
        marketBoxVarietyPropertyService.save(marketBoxVarietyProperty);

        int databaseSizeBeforeDelete = marketBoxVarietyPropertyRepository.findAll().size();

        // Get the marketBoxVarietyProperty
        restMarketBoxVarietyPropertyMockMvc.perform(delete("/api/market-box-variety-properties/{id}", marketBoxVarietyProperty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketBoxVarietyProperty> marketBoxVarietyPropertyList = marketBoxVarietyPropertyRepository.findAll();
        assertThat(marketBoxVarietyPropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketBoxVarietyProperty.class);
        MarketBoxVarietyProperty marketBoxVarietyProperty1 = new MarketBoxVarietyProperty();
        marketBoxVarietyProperty1.setId(1L);
        MarketBoxVarietyProperty marketBoxVarietyProperty2 = new MarketBoxVarietyProperty();
        marketBoxVarietyProperty2.setId(marketBoxVarietyProperty1.getId());
        assertThat(marketBoxVarietyProperty1).isEqualTo(marketBoxVarietyProperty2);
        marketBoxVarietyProperty2.setId(2L);
        assertThat(marketBoxVarietyProperty1).isNotEqualTo(marketBoxVarietyProperty2);
        marketBoxVarietyProperty1.setId(null);
        assertThat(marketBoxVarietyProperty1).isNotEqualTo(marketBoxVarietyProperty2);
    }
}
