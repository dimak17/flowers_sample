package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.TypeOfFlower;
import com.impltech.repository.TypeOfFlowerRepository;
import com.impltech.service.TypeOfFlowerService;
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
 * Test class for the TypeOfFlowerResource REST controller.
 *
 * @see TypeOfFlowerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class TypeOfFlowerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TypeOfFlowerRepository typeOfFlowerRepository;

    @Autowired
    private TypeOfFlowerService typeOfFlowerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeOfFlowerMockMvc;

    private TypeOfFlower typeOfFlower;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TypeOfFlowerResource typeOfFlowerResource = new TypeOfFlowerResource(typeOfFlowerRepository, typeOfFlowerService);
        this.restTypeOfFlowerMockMvc = MockMvcBuilders.standaloneSetup(typeOfFlowerResource)
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
    public static TypeOfFlower createEntity(EntityManager em) {
        TypeOfFlower typeOfFlower = new TypeOfFlower()
            .name(DEFAULT_NAME);
        return typeOfFlower;
    }

    @Before
    public void initTest() {
        typeOfFlower = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeOfFlower() throws Exception {
        int databaseSizeBeforeCreate = typeOfFlowerRepository.findAll().size();

        // Create the TypeOfFlower
        restTypeOfFlowerMockMvc.perform(post("/api/type-of-flowers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOfFlower)))
            .andExpect(status().isCreated());

        // Validate the TypeOfFlower in the database
        List<TypeOfFlower> typeOfFlowerList = typeOfFlowerRepository.findAll();
        assertThat(typeOfFlowerList).hasSize(databaseSizeBeforeCreate + 1);
        TypeOfFlower testTypeOfFlower = typeOfFlowerList.get(typeOfFlowerList.size() - 1);
        assertThat(testTypeOfFlower.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTypeOfFlowerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeOfFlowerRepository.findAll().size();

        // Create the TypeOfFlower with an existing ID
        typeOfFlower.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfFlowerMockMvc.perform(post("/api/type-of-flowers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOfFlower)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TypeOfFlower> typeOfFlowerList = typeOfFlowerRepository.findAll();
        assertThat(typeOfFlowerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeOfFlowers() throws Exception {
        // Initialize the database
        typeOfFlowerRepository.saveAndFlush(typeOfFlower);

        // Get all the typeOfFlowerList
        restTypeOfFlowerMockMvc.perform(get("/api/type-of-flowers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfFlower.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTypeOfFlower() throws Exception {
        // Initialize the database
        typeOfFlowerRepository.saveAndFlush(typeOfFlower);

        // Get the typeOfFlower
        restTypeOfFlowerMockMvc.perform(get("/api/type-of-flowers/{id}", typeOfFlower.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfFlower.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeOfFlower() throws Exception {
        // Get the typeOfFlower
        restTypeOfFlowerMockMvc.perform(get("/api/type-of-flowers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeOfFlower() throws Exception {
        // Initialize the database
        typeOfFlowerRepository.saveAndFlush(typeOfFlower);
        int databaseSizeBeforeUpdate = typeOfFlowerRepository.findAll().size();

        // Update the typeOfFlower
        TypeOfFlower updatedTypeOfFlower = typeOfFlowerRepository.findOne(typeOfFlower.getId());
        updatedTypeOfFlower
            .name(UPDATED_NAME);

        restTypeOfFlowerMockMvc.perform(put("/api/type-of-flowers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeOfFlower)))
            .andExpect(status().isOk());

        // Validate the TypeOfFlower in the database
        List<TypeOfFlower> typeOfFlowerList = typeOfFlowerRepository.findAll();
        assertThat(typeOfFlowerList).hasSize(databaseSizeBeforeUpdate);
        TypeOfFlower testTypeOfFlower = typeOfFlowerList.get(typeOfFlowerList.size() - 1);
        assertThat(testTypeOfFlower.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeOfFlower() throws Exception {
        int databaseSizeBeforeUpdate = typeOfFlowerRepository.findAll().size();

        // Create the TypeOfFlower

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeOfFlowerMockMvc.perform(put("/api/type-of-flowers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOfFlower)))
            .andExpect(status().isCreated());

        // Validate the TypeOfFlower in the database
        List<TypeOfFlower> typeOfFlowerList = typeOfFlowerRepository.findAll();
        assertThat(typeOfFlowerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeOfFlower() throws Exception {
        // Initialize the database
        typeOfFlowerRepository.saveAndFlush(typeOfFlower);
        int databaseSizeBeforeDelete = typeOfFlowerRepository.findAll().size();

        // Get the typeOfFlower
        restTypeOfFlowerMockMvc.perform(delete("/api/type-of-flowers/{id}", typeOfFlower.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeOfFlower> typeOfFlowerList = typeOfFlowerRepository.findAll();
        assertThat(typeOfFlowerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfFlower.class);
        TypeOfFlower typeOfFlower1 = new TypeOfFlower();
        typeOfFlower1.setId(1L);
        TypeOfFlower typeOfFlower2 = new TypeOfFlower();
        typeOfFlower2.setId(typeOfFlower1.getId());
        assertThat(typeOfFlower1).isEqualTo(typeOfFlower2);
        typeOfFlower2.setId(2L);
        assertThat(typeOfFlower1).isNotEqualTo(typeOfFlower2);
        typeOfFlower1.setId(null);
        assertThat(typeOfFlower1).isNotEqualTo(typeOfFlower2);
    }
}
