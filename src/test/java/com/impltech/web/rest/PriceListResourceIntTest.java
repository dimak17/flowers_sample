package com.impltech.web.rest;

import com.impltech.FlowersApp;
import com.impltech.domain.PriceList;
import com.impltech.domain.enumeration.PriceListType;
import com.impltech.repository.PriceListRepositorySH;
import com.impltech.service.PriceListServiceSH;
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
 * Test class for the PriceListResource REST controller.
 *
 * @see PriceListResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class PriceListResourceIntTest {

    private static final PriceListType DEFAULT_TYPE = PriceListType.DEFAULT;
    private static final PriceListType UPDATED_TYPE = PriceListType.HIGH;

    @Autowired
    private PriceListRepositorySH priceListRepository;

    @Autowired
    private PriceListServiceSH priceListService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceListMockMvc;

    private PriceList priceList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceListResourceSH priceListResource = new PriceListResourceSH(priceListService);
        this.restPriceListMockMvc = MockMvcBuilders.standaloneSetup(priceListResource)
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
    public static PriceList createEntity(EntityManager em) {
        PriceList priceList = new PriceList()
            .type(DEFAULT_TYPE);
        return priceList;
    }

    @Before
    public void initTest() {
        priceList = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceList() throws Exception {
        int databaseSizeBeforeCreate = priceListRepository.findAll().size();

        // Create the PriceList
        restPriceListMockMvc.perform(post("/api/price-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceList)))
            .andExpect(status().isCreated());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeCreate + 1);
        PriceList testPriceList = priceListList.get(priceListList.size() - 1);
        assertThat(testPriceList.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPriceListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceListRepository.findAll().size();

        // Create the PriceList with an existing ID
        priceList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceListMockMvc.perform(post("/api/price-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceList)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPriceLists() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        // Get all the priceListList
        restPriceListMockMvc.perform(get("/api/price-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        // Get the priceList
        restPriceListMockMvc.perform(get("/api/price-lists/{id}", priceList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceList.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceList() throws Exception {
        // Get the priceList
        restPriceListMockMvc.perform(get("/api/price-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceList() throws Exception {
        // Initialize the database
        priceListService.save(priceList);

        int databaseSizeBeforeUpdate = priceListRepository.findAll().size();

        // Update the priceList
        PriceList updatedPriceList = priceListRepository.findOne(priceList.getId());
        updatedPriceList
            .type(UPDATED_TYPE);

        restPriceListMockMvc.perform(put("/api/price-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPriceList)))
            .andExpect(status().isOk());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeUpdate);
        PriceList testPriceList = priceListList.get(priceListList.size() - 1);
        assertThat(testPriceList.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceList() throws Exception {
        int databaseSizeBeforeUpdate = priceListRepository.findAll().size();

        // Create the PriceList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceListMockMvc.perform(put("/api/price-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceList)))
            .andExpect(status().isCreated());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePriceList() throws Exception {
        // Initialize the database
        priceListService.save(priceList);

        int databaseSizeBeforeDelete = priceListRepository.findAll().size();

        // Get the priceList
        restPriceListMockMvc.perform(delete("/api/price-lists/{id}", priceList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceList.class);
        PriceList priceList1 = new PriceList();
        priceList1.setId(1L);
        PriceList priceList2 = new PriceList();
        priceList2.setId(priceList1.getId());
        assertThat(priceList1).isEqualTo(priceList2);
        priceList2.setId(2L);
        assertThat(priceList1).isNotEqualTo(priceList2);
        priceList1.setId(null);
        assertThat(priceList1).isNotEqualTo(priceList2);
    }
}
