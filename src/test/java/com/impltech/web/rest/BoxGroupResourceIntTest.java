package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.BoxGroup;
import com.impltech.repository.BoxGroupRepository;
import com.impltech.service.BoxGroupService;
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
 * Test class for the BoxGroupResource REST controller.
 *
 * @see BoxGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class BoxGroupResourceIntTest {

    @Autowired
    private BoxGroupRepository boxGroupRepository;

    @Autowired
    private BoxGroupService boxGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBoxGroupMockMvc;

    private BoxGroup boxGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoxGroupResource boxGroupResource = new BoxGroupResource(boxGroupService);
        this.restBoxGroupMockMvc = MockMvcBuilders.standaloneSetup(boxGroupResource)
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
    public static BoxGroup createEntity(EntityManager em) {
        BoxGroup boxGroup = new BoxGroup();
        return boxGroup;
    }

    @Before
    public void initTest() {
        boxGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoxGroup() throws Exception {
        int databaseSizeBeforeCreate = boxGroupRepository.findAll().size();

        // Create the BoxGroup
        restBoxGroupMockMvc.perform(post("/api/box-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxGroup)))
            .andExpect(status().isCreated());

        // Validate the BoxGroup in the database
        List<BoxGroup> boxGroupList = boxGroupRepository.findAll();
        assertThat(boxGroupList).hasSize(databaseSizeBeforeCreate + 1);
        BoxGroup testBoxGroup = boxGroupList.get(boxGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createBoxGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxGroupRepository.findAll().size();

        // Create the BoxGroup with an existing ID
        boxGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxGroupMockMvc.perform(post("/api/box-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BoxGroup> boxGroupList = boxGroupRepository.findAll();
        assertThat(boxGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBoxGroups() throws Exception {
        // Initialize the database
        boxGroupRepository.saveAndFlush(boxGroup);

        // Get all the boxGroupList
        restBoxGroupMockMvc.perform(get("/api/box-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxGroup.getId().intValue())));
    }

    @Test
    @Transactional
    public void getBoxGroup() throws Exception {
        // Initialize the database
        boxGroupRepository.saveAndFlush(boxGroup);

        // Get the boxGroup
        restBoxGroupMockMvc.perform(get("/api/box-groups/{id}", boxGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boxGroup.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBoxGroup() throws Exception {
        // Get the boxGroup
        restBoxGroupMockMvc.perform(get("/api/box-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoxGroup() throws Exception {
        // Initialize the database
        boxGroupService.save(boxGroup);

        int databaseSizeBeforeUpdate = boxGroupRepository.findAll().size();

        // Update the boxGroup
        BoxGroup updatedBoxGroup = boxGroupRepository.findOne(boxGroup.getId());

        restBoxGroupMockMvc.perform(put("/api/box-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoxGroup)))
            .andExpect(status().isOk());

        // Validate the BoxGroup in the database
        List<BoxGroup> boxGroupList = boxGroupRepository.findAll();
        assertThat(boxGroupList).hasSize(databaseSizeBeforeUpdate);
        BoxGroup testBoxGroup = boxGroupList.get(boxGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBoxGroup() throws Exception {
        int databaseSizeBeforeUpdate = boxGroupRepository.findAll().size();

        // Create the BoxGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBoxGroupMockMvc.perform(put("/api/box-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxGroup)))
            .andExpect(status().isCreated());

        // Validate the BoxGroup in the database
        List<BoxGroup> boxGroupList = boxGroupRepository.findAll();
        assertThat(boxGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBoxGroup() throws Exception {
        // Initialize the database
        boxGroupService.save(boxGroup);

        int databaseSizeBeforeDelete = boxGroupRepository.findAll().size();

        // Get the boxGroup
        restBoxGroupMockMvc.perform(delete("/api/box-groups/{id}", boxGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BoxGroup> boxGroupList = boxGroupRepository.findAll();
        assertThat(boxGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoxGroup.class);
        BoxGroup boxGroup1 = new BoxGroup();
        boxGroup1.setId(1L);
        BoxGroup boxGroup2 = new BoxGroup();
        boxGroup2.setId(boxGroup1.getId());
        assertThat(boxGroup1).isEqualTo(boxGroup2);
        boxGroup2.setId(2L);
        assertThat(boxGroup1).isNotEqualTo(boxGroup2);
        boxGroup1.setId(null);
        assertThat(boxGroup1).isNotEqualTo(boxGroup2);
    }
}
