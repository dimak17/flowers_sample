package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.Market;
import com.impltech.repository.MarketRepositorySH;
import com.impltech.service.MarketServiceSH;
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
 * Test class for the MarketResource REST controller.
 *
 * @see MarketResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MarketRepositorySH marketRepository;

    @Autowired
    private MarketServiceSH marketService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketMockMvc;

    private Market market;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketResourceSH marketResource = new MarketResourceSH(marketService);
        this.restMarketMockMvc = MockMvcBuilders.standaloneSetup(marketResource)
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
    public static Market createEntity(EntityManager em) {
        Market market = new Market()
            .name(DEFAULT_NAME);
        return market;
    }

    @Before
    public void initTest() {
        market = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarket() throws Exception {
        int databaseSizeBeforeCreate = marketRepository.findAll().size();

        // Create the Market
        restMarketMockMvc.perform(post("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(market)))
            .andExpect(status().isCreated());

        // Validate the Market in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeCreate + 1);
        Market testMarket = marketList.get(marketList.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMarketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketRepository.findAll().size();

        // Create the Market with an existing ID
        market.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketMockMvc.perform(post("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(market)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarkets() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);

        // Get all the marketList
        restMarketMockMvc.perform(get("/api/markets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(market.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMarket() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);

        // Get the market
        restMarketMockMvc.perform(get("/api/markets/{id}", market.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(market.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarket() throws Exception {
        // Get the market
        restMarketMockMvc.perform(get("/api/markets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarket() throws Exception {
        // Initialize the database
        marketService.save(market);

        int databaseSizeBeforeUpdate = marketRepository.findAll().size();

        // Update the market
        Market updatedMarket = marketRepository.findOne(market.getId());
        updatedMarket
            .name(UPDATED_NAME);

        restMarketMockMvc.perform(put("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarket)))
            .andExpect(status().isOk());

        // Validate the Market in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeUpdate);
        Market testMarket = marketList.get(marketList.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMarket() throws Exception {
        int databaseSizeBeforeUpdate = marketRepository.findAll().size();

        // Create the Market

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketMockMvc.perform(put("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(market)))
            .andExpect(status().isCreated());

        // Validate the Market in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarket() throws Exception {
        // Initialize the database
        marketService.save(market);

        int databaseSizeBeforeDelete = marketRepository.findAll().size();

        // Get the market
        restMarketMockMvc.perform(delete("/api/markets/{id}", market.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Market.class);
        Market market1 = new Market();
        market1.setId(1L);
        Market market2 = new Market();
        market2.setId(market1.getId());
        assertThat(market1).isEqualTo(market2);
        market2.setId(2L);
        assertThat(market1).isNotEqualTo(market2);
        market1.setId(null);
        assertThat(market1).isNotEqualTo(market2);
    }
}
