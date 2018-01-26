package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.MarketSeasonVarietyProperty;
import com.impltech.repository.MarketSeasonVarietyPropertyRepository;
import com.impltech.service.MarketSeasonService;
import com.impltech.service.MarketSeasonVarietyPropertyService;
import com.impltech.service.ShippingPolicyService;
import com.impltech.service.VarietyService;
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

import com.impltech.domain.enumeration.Length;
/**
 * Test class for the MarketSeasonVarietyPropertyResource REST controller.
 *
 * @see MarketSeasonVarietyPropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketSeasonVarietyPropertyResourceIntTest {

    private static final Length DEFAULT_LENGTH = Length._40;
    private static final Length UPDATED_LENGTH = Length._50;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private MarketSeasonVarietyPropertyRepository marketSeasonVarietyPropertyRepository;

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private MarketSeasonService marketSeasonService;

    @Autowired
    private ShippingPolicyService shippingPolicyService;

    @Autowired
    private MarketSeasonVarietyPropertyService marketSeasonVarietyPropertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketSeasonVarietyPropertyMockMvc;

    private MarketSeasonVarietyProperty marketSeasonVarietyProperty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketSeasonVarietyPropertyResource marketSeasonVarietyPropertyResource = new MarketSeasonVarietyPropertyResource(marketSeasonVarietyPropertyService,varietyService, marketSeasonService,shippingPolicyService);
        this.restMarketSeasonVarietyPropertyMockMvc = MockMvcBuilders.standaloneSetup(marketSeasonVarietyPropertyResource)
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
    public static MarketSeasonVarietyProperty createEntity(EntityManager em) {
        MarketSeasonVarietyProperty marketSeasonVarietyProperty = new MarketSeasonVarietyProperty()
            .length(DEFAULT_LENGTH)
            .price(DEFAULT_PRICE);
        return marketSeasonVarietyProperty;
    }

    @Before
    public void initTest() {
        marketSeasonVarietyProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketSeasonVarietyProperty() throws Exception {
        int databaseSizeBeforeCreate = marketSeasonVarietyPropertyRepository.findAll().size();

        // Create the MarketSeasonVarietyProperty
        restMarketSeasonVarietyPropertyMockMvc.perform(post("/api/market-season-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketSeasonVarietyProperty)))
            .andExpect(status().isCreated());

        // Validate the MarketSeasonVarietyProperty in the database
        List<MarketSeasonVarietyProperty> marketSeasonVarietyPropertyList = marketSeasonVarietyPropertyRepository.findAll();
        assertThat(marketSeasonVarietyPropertyList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSeasonVarietyProperty testMarketSeasonVarietyProperty = marketSeasonVarietyPropertyList.get(marketSeasonVarietyPropertyList.size() - 1);
        assertThat(testMarketSeasonVarietyProperty.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testMarketSeasonVarietyProperty.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createMarketSeasonVarietyPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketSeasonVarietyPropertyRepository.findAll().size();

        // Create the MarketSeasonVarietyProperty with an existing ID
        marketSeasonVarietyProperty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSeasonVarietyPropertyMockMvc.perform(post("/api/market-season-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketSeasonVarietyProperty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketSeasonVarietyProperty> marketSeasonVarietyPropertyList = marketSeasonVarietyPropertyRepository.findAll();
        assertThat(marketSeasonVarietyPropertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketSeasonVarietyProperties() throws Exception {
        // Initialize the database
        marketSeasonVarietyPropertyRepository.saveAndFlush(marketSeasonVarietyProperty);

        // Get all the marketSeasonVarietyPropertyList
        restMarketSeasonVarietyPropertyMockMvc.perform(get("/api/market-season-variety-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSeasonVarietyProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getMarketSeasonVarietyProperty() throws Exception {
        // Initialize the database
        marketSeasonVarietyPropertyRepository.saveAndFlush(marketSeasonVarietyProperty);

        // Get the marketSeasonVarietyProperty
        restMarketSeasonVarietyPropertyMockMvc.perform(get("/api/market-season-variety-properties/{id}", marketSeasonVarietyProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketSeasonVarietyProperty.getId().intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketSeasonVarietyProperty() throws Exception {
        // Get the marketSeasonVarietyProperty
        restMarketSeasonVarietyPropertyMockMvc.perform(get("/api/market-season-variety-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketSeasonVarietyProperty() throws Exception {
        // Initialize the database
        marketSeasonVarietyPropertyService.save(marketSeasonVarietyProperty);

        int databaseSizeBeforeUpdate = marketSeasonVarietyPropertyRepository.findAll().size();

        // Update the marketSeasonVarietyProperty
        MarketSeasonVarietyProperty updatedMarketSeasonVarietyProperty = marketSeasonVarietyPropertyRepository.findOne(marketSeasonVarietyProperty.getId());
        updatedMarketSeasonVarietyProperty
            .length(UPDATED_LENGTH)
            .price(UPDATED_PRICE);

        restMarketSeasonVarietyPropertyMockMvc.perform(put("/api/market-season-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketSeasonVarietyProperty)))
            .andExpect(status().isOk());

        // Validate the MarketSeasonVarietyProperty in the database
        List<MarketSeasonVarietyProperty> marketSeasonVarietyPropertyList = marketSeasonVarietyPropertyRepository.findAll();
        assertThat(marketSeasonVarietyPropertyList).hasSize(databaseSizeBeforeUpdate);
        MarketSeasonVarietyProperty testMarketSeasonVarietyProperty = marketSeasonVarietyPropertyList.get(marketSeasonVarietyPropertyList.size() - 1);
        assertThat(testMarketSeasonVarietyProperty.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testMarketSeasonVarietyProperty.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketSeasonVarietyProperty() throws Exception {
        int databaseSizeBeforeUpdate = marketSeasonVarietyPropertyRepository.findAll().size();

        // Create the MarketSeasonVarietyProperty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketSeasonVarietyPropertyMockMvc.perform(put("/api/market-season-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketSeasonVarietyProperty)))
            .andExpect(status().isCreated());

        // Validate the MarketSeasonVarietyProperty in the database
        List<MarketSeasonVarietyProperty> marketSeasonVarietyPropertyList = marketSeasonVarietyPropertyRepository.findAll();
        assertThat(marketSeasonVarietyPropertyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketSeasonVarietyProperty() throws Exception {
        // Initialize the database
        marketSeasonVarietyPropertyService.save(marketSeasonVarietyProperty);

        int databaseSizeBeforeDelete = marketSeasonVarietyPropertyRepository.findAll().size();

        // Get the marketSeasonVarietyProperty
        restMarketSeasonVarietyPropertyMockMvc.perform(delete("/api/market-season-variety-properties/{id}", marketSeasonVarietyProperty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketSeasonVarietyProperty> marketSeasonVarietyPropertyList = marketSeasonVarietyPropertyRepository.findAll();
        assertThat(marketSeasonVarietyPropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSeasonVarietyProperty.class);
        MarketSeasonVarietyProperty marketSeasonVarietyProperty1 = new MarketSeasonVarietyProperty();
        marketSeasonVarietyProperty1.setId(1L);
        MarketSeasonVarietyProperty marketSeasonVarietyProperty2 = new MarketSeasonVarietyProperty();
        marketSeasonVarietyProperty2.setId(marketSeasonVarietyProperty1.getId());
        assertThat(marketSeasonVarietyProperty1).isEqualTo(marketSeasonVarietyProperty2);
        marketSeasonVarietyProperty2.setId(2L);
        assertThat(marketSeasonVarietyProperty1).isNotEqualTo(marketSeasonVarietyProperty2);
        marketSeasonVarietyProperty1.setId(null);
        assertThat(marketSeasonVarietyProperty1).isNotEqualTo(marketSeasonVarietyProperty2);
    }
}
