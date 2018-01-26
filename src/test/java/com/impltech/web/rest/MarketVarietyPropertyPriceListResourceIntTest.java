package com.impltech.web.rest;

import com.impltech.FlowersApp;
import com.impltech.domain.MarketVarietyPropertyPriceList;
import com.impltech.domain.enumeration.Length;
import com.impltech.repository.MarketVarietyPropertyPriceListRepository;
import com.impltech.service.MarketService;
import com.impltech.service.MarketVarietyPropertyPriceListService;
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
/**
 * Test class for the MarketVarietyPropertyResource REST controller.
 *
 * @see MarketVarietyPropertyPriceListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketVarietyPropertyPriceListResourceIntTest {

    private static final Length DEFAULT_LENGTH = Length._40;
    private static final Length UPDATED_LENGTH = Length._50;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private MarketVarietyPropertyPriceListRepository marketVarietyPropertyRepository;

    @Autowired
    private MarketService marketService;

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private ShippingPolicyService shippingPolicyService;


    @Autowired
    private MarketVarietyPropertyPriceListService marketVarietyPropertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketVarietyPropertyMockMvc;

    private MarketVarietyPropertyPriceList marketVarietyPropertyPriceList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketVarietyPropertyPriceListResource marketVarietyPropertyResource = new MarketVarietyPropertyPriceListResource(marketVarietyPropertyService, marketService, varietyService, shippingPolicyService);
        this.restMarketVarietyPropertyMockMvc = MockMvcBuilders.standaloneSetup(marketVarietyPropertyResource)
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
    public static MarketVarietyPropertyPriceList createEntity(EntityManager em) {
        MarketVarietyPropertyPriceList marketVarietyPropertyPriceList = new MarketVarietyPropertyPriceList()
            .length(DEFAULT_LENGTH)
            .price(DEFAULT_PRICE);
        return marketVarietyPropertyPriceList;
    }

    @Before
    public void initTest() {
        marketVarietyPropertyPriceList = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketVarietyProperty() throws Exception {
        int databaseSizeBeforeCreate = marketVarietyPropertyRepository.findAll().size();

        // Create the MarketVarietyPropertyPriceList
        restMarketVarietyPropertyMockMvc.perform(post("/api/market-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketVarietyPropertyPriceList)))
            .andExpect(status().isCreated());

        // Validate the MarketVarietyPropertyPriceList in the database
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceListList = marketVarietyPropertyRepository.findAll();
        assertThat(marketVarietyPropertyPriceListList).hasSize(databaseSizeBeforeCreate + 1);
        MarketVarietyPropertyPriceList testMarketVarietyPropertyPriceList = marketVarietyPropertyPriceListList.get(marketVarietyPropertyPriceListList.size() - 1);
        assertThat(testMarketVarietyPropertyPriceList.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testMarketVarietyPropertyPriceList.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createMarketVarietyPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketVarietyPropertyRepository.findAll().size();

        // Create the MarketVarietyPropertyPriceList with an existing ID
        marketVarietyPropertyPriceList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketVarietyPropertyMockMvc.perform(post("/api/market-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketVarietyPropertyPriceList)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceListList = marketVarietyPropertyRepository.findAll();
        assertThat(marketVarietyPropertyPriceListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketVarietyProperties() throws Exception {
        // Initialize the database
        marketVarietyPropertyRepository.saveAndFlush(marketVarietyPropertyPriceList);

        // Get all the marketVarietyPropertyList
        restMarketVarietyPropertyMockMvc.perform(get("/api/market-variety-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketVarietyPropertyPriceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getMarketVarietyProperty() throws Exception {
        // Initialize the database
        marketVarietyPropertyRepository.saveAndFlush(marketVarietyPropertyPriceList);

        // Get the marketVarietyPropertyPriceList
        restMarketVarietyPropertyMockMvc.perform(get("/api/market-variety-properties/{id}", marketVarietyPropertyPriceList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketVarietyPropertyPriceList.getId().intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketVarietyProperty() throws Exception {
        // Get the marketVarietyPropertyPriceList
        restMarketVarietyPropertyMockMvc.perform(get("/api/market-variety-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketVarietyProperty() throws Exception {
        // Initialize the database
        marketVarietyPropertyService.save(marketVarietyPropertyPriceList);

        int databaseSizeBeforeUpdate = marketVarietyPropertyRepository.findAll().size();

        // Update the marketVarietyPropertyPriceList
        MarketVarietyPropertyPriceList updatedMarketVarietyPropertyPriceList = marketVarietyPropertyRepository.findOne(marketVarietyPropertyPriceList.getId());
        updatedMarketVarietyPropertyPriceList
            .length(UPDATED_LENGTH)
            .price(UPDATED_PRICE);

        restMarketVarietyPropertyMockMvc.perform(put("/api/market-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketVarietyPropertyPriceList)))
            .andExpect(status().isOk());

        // Validate the MarketVarietyPropertyPriceList in the database
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceListList = marketVarietyPropertyRepository.findAll();
        assertThat(marketVarietyPropertyPriceListList).hasSize(databaseSizeBeforeUpdate);
        MarketVarietyPropertyPriceList testMarketVarietyPropertyPriceList = marketVarietyPropertyPriceListList.get(marketVarietyPropertyPriceListList.size() - 1);
        assertThat(testMarketVarietyPropertyPriceList.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testMarketVarietyPropertyPriceList.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketVarietyProperty() throws Exception {
        int databaseSizeBeforeUpdate = marketVarietyPropertyRepository.findAll().size();

        // Create the MarketVarietyPropertyPriceList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketVarietyPropertyMockMvc.perform(put("/api/market-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketVarietyPropertyPriceList)))
            .andExpect(status().isCreated());

        // Validate the MarketVarietyPropertyPriceList in the database
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceListList = marketVarietyPropertyRepository.findAll();
        assertThat(marketVarietyPropertyPriceListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketVarietyProperty() throws Exception {
        // Initialize the database
        marketVarietyPropertyService.save(marketVarietyPropertyPriceList);

        int databaseSizeBeforeDelete = marketVarietyPropertyRepository.findAll().size();

        // Get the marketVarietyPropertyPriceList
        restMarketVarietyPropertyMockMvc.perform(delete("/api/market-variety-properties/{id}", marketVarietyPropertyPriceList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceListList = marketVarietyPropertyRepository.findAll();
        assertThat(marketVarietyPropertyPriceListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketVarietyPropertyPriceList.class);
        MarketVarietyPropertyPriceList marketVarietyPropertyPriceList1 = new MarketVarietyPropertyPriceList();
        marketVarietyPropertyPriceList1.setId(1L);
        MarketVarietyPropertyPriceList marketVarietyPropertyPriceList2 = new MarketVarietyPropertyPriceList();
        marketVarietyPropertyPriceList2.setId(marketVarietyPropertyPriceList1.getId());
        assertThat(marketVarietyPropertyPriceList1).isEqualTo(marketVarietyPropertyPriceList2);
        marketVarietyPropertyPriceList2.setId(2L);
        assertThat(marketVarietyPropertyPriceList1).isNotEqualTo(marketVarietyPropertyPriceList2);
        marketVarietyPropertyPriceList1.setId(null);
        assertThat(marketVarietyPropertyPriceList1).isNotEqualTo(marketVarietyPropertyPriceList2);
    }
}
