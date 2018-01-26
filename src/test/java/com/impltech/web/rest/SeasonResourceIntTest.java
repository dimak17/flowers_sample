package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.Season;
import com.impltech.repository.SeasonRepository;
import com.impltech.service.SeasonService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeasonResource REST controller.
 *
 * @see SeasonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class SeasonResourceIntTest {

    private static final String DEFAULT_SEASON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEASON_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEASON_YEAR = 1;
    private static final Integer UPDATED_SEASON_YEAR = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NOTIFY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOTIFY_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeasonMockMvc;

    private Season season;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeasonResource seasonResource = new SeasonResource(seasonService);
        this.restSeasonMockMvc = MockMvcBuilders.standaloneSetup(seasonResource)
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
    public static Season createEntity(EntityManager em) {
        Season season = new Season()
            .seasonName(DEFAULT_SEASON_NAME)
            .seasonYear(DEFAULT_SEASON_YEAR)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .notifyStartDate(DEFAULT_NOTIFY_START_DATE);
        return season;
    }

    @Before
    public void initTest() {
        season = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeason() throws Exception {
        int databaseSizeBeforeCreate = seasonRepository.findAll().size();

        // Create the Season
        restSeasonMockMvc.perform(post("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isCreated());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeCreate + 1);
        Season testSeason = seasonList.get(seasonList.size() - 1);
        assertThat(testSeason.getSeasonName()).isEqualTo(DEFAULT_SEASON_NAME);
        assertThat(testSeason.getSeasonYear()).isEqualTo(DEFAULT_SEASON_YEAR);
        assertThat(testSeason.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSeason.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSeason.getNotifyStartDate()).isEqualTo(DEFAULT_NOTIFY_START_DATE);
    }

    @Test
    @Transactional
    public void createSeasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seasonRepository.findAll().size();

        // Create the Season with an existing ID
        season.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeasonMockMvc.perform(post("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeasons() throws Exception {
        // Initialize the database
        seasonRepository.saveAndFlush(season);

        // Get all the seasonList
        restSeasonMockMvc.perform(get("/api/seasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(season.getId().intValue())))
            .andExpect(jsonPath("$.[*].seasonName").value(hasItem(DEFAULT_SEASON_NAME.toString())))
            .andExpect(jsonPath("$.[*].seasonYear").value(hasItem(DEFAULT_SEASON_YEAR)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].notifyStartDate").value(hasItem(DEFAULT_NOTIFY_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSeason() throws Exception {
        // Initialize the database
        seasonRepository.saveAndFlush(season);

        // Get the season
        restSeasonMockMvc.perform(get("/api/seasons/{id}", season.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(season.getId().intValue()))
            .andExpect(jsonPath("$.seasonName").value(DEFAULT_SEASON_NAME.toString()))
            .andExpect(jsonPath("$.seasonYear").value(DEFAULT_SEASON_YEAR))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.notifyStartDate").value(DEFAULT_NOTIFY_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeason() throws Exception {
        // Get the season
        restSeasonMockMvc.perform(get("/api/seasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeason() throws Exception {
        // Initialize the database
        seasonService.save(season);

        int databaseSizeBeforeUpdate = seasonRepository.findAll().size();

        // Update the season
        Season updatedSeason = seasonRepository.findOne(season.getId());
        updatedSeason
            .seasonName(UPDATED_SEASON_NAME)
            .seasonYear(UPDATED_SEASON_YEAR)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .notifyStartDate(UPDATED_NOTIFY_START_DATE);

        restSeasonMockMvc.perform(put("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeason)))
            .andExpect(status().isOk());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeUpdate);
        Season testSeason = seasonList.get(seasonList.size() - 1);
        assertThat(testSeason.getSeasonName()).isEqualTo(UPDATED_SEASON_NAME);
        assertThat(testSeason.getSeasonYear()).isEqualTo(UPDATED_SEASON_YEAR);
        assertThat(testSeason.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSeason.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSeason.getNotifyStartDate()).isEqualTo(UPDATED_NOTIFY_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeason() throws Exception {
        int databaseSizeBeforeUpdate = seasonRepository.findAll().size();

        // Create the Season

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeasonMockMvc.perform(put("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isCreated());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeason() throws Exception {
        // Initialize the database
        seasonService.save(season);

        int databaseSizeBeforeDelete = seasonRepository.findAll().size();

        // Get the season
        restSeasonMockMvc.perform(delete("/api/seasons/{id}", season.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Season.class);
        Season season1 = new Season();
        season1.setId(1L);
        Season season2 = new Season();
        season2.setId(season1.getId());
        assertThat(season1).isEqualTo(season2);
        season2.setId(2L);
        assertThat(season1).isNotEqualTo(season2);
        season1.setId(null);
        assertThat(season1).isNotEqualTo(season2);
    }
}
