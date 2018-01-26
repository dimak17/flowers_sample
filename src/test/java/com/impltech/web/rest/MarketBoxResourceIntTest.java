package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.MarketBox;
import com.impltech.repository.MarketBoxRepository;
import com.impltech.service.MarketBoxService;
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

import com.impltech.domain.enumeration.BoxSizeUnit;
/**
 * Test class for the MarketBoxResource REST controller.
 *
 * @see MarketBoxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketBoxResourceIntTest {

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final BoxSizeUnit DEFAULT_UNIT = BoxSizeUnit.MM;
    private static final BoxSizeUnit UPDATED_UNIT = BoxSizeUnit.M;

    @Autowired
    private MarketBoxRepository marketBoxRepository;

    @Autowired
    private MarketBoxService marketBoxService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketBoxMockMvc;

    private MarketBox marketBox;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketBoxResource marketBoxResource = new MarketBoxResource(marketBoxService);
        this.restMarketBoxMockMvc = MockMvcBuilders.standaloneSetup(marketBoxResource)
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
    public static MarketBox createEntity(EntityManager em) {
        MarketBox marketBox = new MarketBox()
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .unit(DEFAULT_UNIT);
        return marketBox;
    }

    @Before
    public void initTest() {
        marketBox = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketBox() throws Exception {
        int databaseSizeBeforeCreate = marketBoxRepository.findAll().size();

        // Create the MarketBox
        restMarketBoxMockMvc.perform(post("/api/market-boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketBox)))
            .andExpect(status().isCreated());

        // Validate the MarketBox in the database
        List<MarketBox> marketBoxList = marketBoxRepository.findAll();
        assertThat(marketBoxList).hasSize(databaseSizeBeforeCreate + 1);
        MarketBox testMarketBox = marketBoxList.get(marketBoxList.size() - 1);
        assertThat(testMarketBox.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testMarketBox.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testMarketBox.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testMarketBox.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    public void createMarketBoxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketBoxRepository.findAll().size();

        // Create the MarketBox with an existing ID
        marketBox.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketBoxMockMvc.perform(post("/api/market-boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketBox)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketBox> marketBoxList = marketBoxRepository.findAll();
        assertThat(marketBoxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketBoxes() throws Exception {
        // Initialize the database
        marketBoxRepository.saveAndFlush(marketBox);

        // Get all the marketBoxList
        restMarketBoxMockMvc.perform(get("/api/market-boxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketBox.getId().intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }

    @Test
    @Transactional
    public void getMarketBox() throws Exception {
        // Initialize the database
        marketBoxRepository.saveAndFlush(marketBox);

        // Get the marketBox
        restMarketBoxMockMvc.perform(get("/api/market-boxes/{id}", marketBox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketBox.getId().intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketBox() throws Exception {
        // Get the marketBox
        restMarketBoxMockMvc.perform(get("/api/market-boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketBox() throws Exception {
        // Initialize the database
        marketBoxService.save(marketBox);

        int databaseSizeBeforeUpdate = marketBoxRepository.findAll().size();

        // Update the marketBox
        MarketBox updatedMarketBox = marketBoxRepository.findOne(marketBox.getId());
        updatedMarketBox
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .unit(UPDATED_UNIT);

        restMarketBoxMockMvc.perform(put("/api/market-boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketBox)))
            .andExpect(status().isOk());

        // Validate the MarketBox in the database
        List<MarketBox> marketBoxList = marketBoxRepository.findAll();
        assertThat(marketBoxList).hasSize(databaseSizeBeforeUpdate);
        MarketBox testMarketBox = marketBoxList.get(marketBoxList.size() - 1);
        assertThat(testMarketBox.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testMarketBox.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMarketBox.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testMarketBox.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketBox() throws Exception {
        int databaseSizeBeforeUpdate = marketBoxRepository.findAll().size();

        // Create the MarketBox

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketBoxMockMvc.perform(put("/api/market-boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketBox)))
            .andExpect(status().isCreated());

        // Validate the MarketBox in the database
        List<MarketBox> marketBoxList = marketBoxRepository.findAll();
        assertThat(marketBoxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketBox() throws Exception {
        // Initialize the database
        marketBoxService.save(marketBox);

        int databaseSizeBeforeDelete = marketBoxRepository.findAll().size();

        // Get the marketBox
        restMarketBoxMockMvc.perform(delete("/api/market-boxes/{id}", marketBox.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketBox> marketBoxList = marketBoxRepository.findAll();
        assertThat(marketBoxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketBox.class);
        MarketBox marketBox1 = new MarketBox();
        marketBox1.setId(1L);
        MarketBox marketBox2 = new MarketBox();
        marketBox2.setId(marketBox1.getId());
        assertThat(marketBox1).isEqualTo(marketBox2);
        marketBox2.setId(2L);
        assertThat(marketBox1).isNotEqualTo(marketBox2);
        marketBox1.setId(null);
        assertThat(marketBox1).isNotEqualTo(marketBox2);
    }
}
