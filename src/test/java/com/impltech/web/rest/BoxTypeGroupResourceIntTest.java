package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.BoxTypeGroup;
import com.impltech.repository.BoxTypeGroupRepository;
import com.impltech.service.BoxTypeGroupService;
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
 * Test class for the BoxTypeGroupResource REST controller.
 *
 * @see BoxTypeGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class BoxTypeGroupResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_ORDER = 0;
    private static final Integer UPDATED_ORDER = 1;

    @Autowired
    private BoxTypeGroupRepository boxTypeGroupRepository;

    @Autowired
    private BoxTypeGroupService boxTypeGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBoxTypeGroupMockMvc;

    private BoxTypeGroup boxTypeGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoxTypeGroupResource boxTypeGroupResource = new BoxTypeGroupResource(boxTypeGroupService);
        this.restBoxTypeGroupMockMvc = MockMvcBuilders.standaloneSetup(boxTypeGroupResource)
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
    public static BoxTypeGroup createEntity(EntityManager em) {
        BoxTypeGroup boxTypeGroup = new BoxTypeGroup()
            .quantity(DEFAULT_QUANTITY)
            .order(DEFAULT_ORDER);
        return boxTypeGroup;
    }

    @Before
    public void initTest() {
        boxTypeGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoxTypeGroup() throws Exception {
        int databaseSizeBeforeCreate = boxTypeGroupRepository.findAll().size();

        // Create the BoxTypeGroup
        restBoxTypeGroupMockMvc.perform(post("/api/box-type-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxTypeGroup)))
            .andExpect(status().isCreated());

        // Validate the BoxTypeGroup in the database
        List<BoxTypeGroup> boxTypeGroupList = boxTypeGroupRepository.findAll();
        assertThat(boxTypeGroupList).hasSize(databaseSizeBeforeCreate + 1);
        BoxTypeGroup testBoxTypeGroup = boxTypeGroupList.get(boxTypeGroupList.size() - 1);
        assertThat(testBoxTypeGroup.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBoxTypeGroup.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createBoxTypeGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxTypeGroupRepository.findAll().size();

        // Create the BoxTypeGroup with an existing ID
        boxTypeGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxTypeGroupMockMvc.perform(post("/api/box-type-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxTypeGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BoxTypeGroup> boxTypeGroupList = boxTypeGroupRepository.findAll();
        assertThat(boxTypeGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBoxTypeGroups() throws Exception {
        // Initialize the database
        boxTypeGroupRepository.saveAndFlush(boxTypeGroup);

        // Get all the boxTypeGroupList
        restBoxTypeGroupMockMvc.perform(get("/api/box-type-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxTypeGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    @Transactional
    public void getBoxTypeGroup() throws Exception {
        // Initialize the database
        boxTypeGroupRepository.saveAndFlush(boxTypeGroup);

        // Get the boxTypeGroup
        restBoxTypeGroupMockMvc.perform(get("/api/box-type-groups/{id}", boxTypeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boxTypeGroup.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingBoxTypeGroup() throws Exception {
        // Get the boxTypeGroup
        restBoxTypeGroupMockMvc.perform(get("/api/box-type-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoxTypeGroup() throws Exception {
        // Initialize the database
        boxTypeGroupService.save(boxTypeGroup);

        int databaseSizeBeforeUpdate = boxTypeGroupRepository.findAll().size();

        // Update the boxTypeGroup
        BoxTypeGroup updatedBoxTypeGroup = boxTypeGroupRepository.findOne(boxTypeGroup.getId());
        updatedBoxTypeGroup
            .quantity(UPDATED_QUANTITY)
            .order(UPDATED_ORDER);

        restBoxTypeGroupMockMvc.perform(put("/api/box-type-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoxTypeGroup)))
            .andExpect(status().isOk());

        // Validate the BoxTypeGroup in the database
        List<BoxTypeGroup> boxTypeGroupList = boxTypeGroupRepository.findAll();
        assertThat(boxTypeGroupList).hasSize(databaseSizeBeforeUpdate);
        BoxTypeGroup testBoxTypeGroup = boxTypeGroupList.get(boxTypeGroupList.size() - 1);
        assertThat(testBoxTypeGroup.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBoxTypeGroup.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingBoxTypeGroup() throws Exception {
        int databaseSizeBeforeUpdate = boxTypeGroupRepository.findAll().size();

        // Create the BoxTypeGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBoxTypeGroupMockMvc.perform(put("/api/box-type-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxTypeGroup)))
            .andExpect(status().isCreated());

        // Validate the BoxTypeGroup in the database
        List<BoxTypeGroup> boxTypeGroupList = boxTypeGroupRepository.findAll();
        assertThat(boxTypeGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBoxTypeGroup() throws Exception {
        // Initialize the database
        boxTypeGroupService.save(boxTypeGroup);

        int databaseSizeBeforeDelete = boxTypeGroupRepository.findAll().size();

        // Get the boxTypeGroup
        restBoxTypeGroupMockMvc.perform(delete("/api/box-type-groups/{id}", boxTypeGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BoxTypeGroup> boxTypeGroupList = boxTypeGroupRepository.findAll();
        assertThat(boxTypeGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoxTypeGroup.class);
        BoxTypeGroup boxTypeGroup1 = new BoxTypeGroup();
        boxTypeGroup1.setId(1L);
        BoxTypeGroup boxTypeGroup2 = new BoxTypeGroup();
        boxTypeGroup2.setId(boxTypeGroup1.getId());
        assertThat(boxTypeGroup1).isEqualTo(boxTypeGroup2);
        boxTypeGroup2.setId(2L);
        assertThat(boxTypeGroup1).isNotEqualTo(boxTypeGroup2);
        boxTypeGroup1.setId(null);
        assertThat(boxTypeGroup1).isNotEqualTo(boxTypeGroup2);
    }
}
