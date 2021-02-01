package com.isoft.emotion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.emotion.EmotionFeedbackApp;
import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.domain.EmoSystem;
import com.isoft.emotion.domain.EmoSystemServices;
import com.isoft.emotion.repository.EmoSystemRepository;
import com.isoft.emotion.service.EmoSystemQueryService;
import com.isoft.emotion.service.EmoSystemService;
import com.isoft.emotion.service.dto.EmoSystemCriteria;
import com.isoft.emotion.service.dto.EmoSystemDTO;
import com.isoft.emotion.service.mapper.EmoSystemMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmoSystemResource} REST controller.
 */
@SpringBootTest(classes = EmotionFeedbackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmoSystemResourceIT {
    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    @Autowired
    private EmoSystemRepository emoSystemRepository;

    @Autowired
    private EmoSystemMapper emoSystemMapper;

    @Autowired
    private EmoSystemService emoSystemService;

    @Autowired
    private EmoSystemQueryService emoSystemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmoSystemMockMvc;

    private EmoSystem emoSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoSystem createEntity(EntityManager em) {
        EmoSystem emoSystem = new EmoSystem().nameAr(DEFAULT_NAME_AR).nameEn(DEFAULT_NAME_EN).code(DEFAULT_CODE).status(DEFAULT_STATUS);
        return emoSystem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoSystem createUpdatedEntity(EntityManager em) {
        EmoSystem emoSystem = new EmoSystem().nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        return emoSystem;
    }

    @BeforeEach
    public void initTest() {
        emoSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmoSystem() throws Exception {
        int databaseSizeBeforeCreate = emoSystemRepository.findAll().size();
        // Create the EmoSystem
        EmoSystemDTO emoSystemDTO = emoSystemMapper.toDto(emoSystem);
        restEmoSystemMockMvc
            .perform(
                post("/api/emo-systems").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoSystemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmoSystem in the database
        List<EmoSystem> emoSystemList = emoSystemRepository.findAll();
        assertThat(emoSystemList).hasSize(databaseSizeBeforeCreate + 1);
        EmoSystem testEmoSystem = emoSystemList.get(emoSystemList.size() - 1);
        assertThat(testEmoSystem.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testEmoSystem.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testEmoSystem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmoSystem.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEmoSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emoSystemRepository.findAll().size();

        // Create the EmoSystem with an existing ID
        emoSystem.setId(1L);
        EmoSystemDTO emoSystemDTO = emoSystemMapper.toDto(emoSystem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmoSystemMockMvc
            .perform(
                post("/api/emo-systems").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoSystem in the database
        List<EmoSystem> emoSystemList = emoSystemRepository.findAll();
        assertThat(emoSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmoSystems() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList
        restEmoSystemMockMvc
            .perform(get("/api/emo-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getEmoSystem() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get the emoSystem
        restEmoSystemMockMvc
            .perform(get("/api/emo-systems/{id}", emoSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emoSystem.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getEmoSystemsByIdFiltering() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        Long id = emoSystem.getId();

        defaultEmoSystemShouldBeFound("id.equals=" + id);
        defaultEmoSystemShouldNotBeFound("id.notEquals=" + id);

        defaultEmoSystemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmoSystemShouldNotBeFound("id.greaterThan=" + id);

        defaultEmoSystemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmoSystemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameAr equals to DEFAULT_NAME_AR
        defaultEmoSystemShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the emoSystemList where nameAr equals to UPDATED_NAME_AR
        defaultEmoSystemShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameAr not equals to DEFAULT_NAME_AR
        defaultEmoSystemShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the emoSystemList where nameAr not equals to UPDATED_NAME_AR
        defaultEmoSystemShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultEmoSystemShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the emoSystemList where nameAr equals to UPDATED_NAME_AR
        defaultEmoSystemShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameAr is not null
        defaultEmoSystemShouldBeFound("nameAr.specified=true");

        // Get all the emoSystemList where nameAr is null
        defaultEmoSystemShouldNotBeFound("nameAr.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameArContainsSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameAr contains DEFAULT_NAME_AR
        defaultEmoSystemShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the emoSystemList where nameAr contains UPDATED_NAME_AR
        defaultEmoSystemShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameAr does not contain DEFAULT_NAME_AR
        defaultEmoSystemShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the emoSystemList where nameAr does not contain UPDATED_NAME_AR
        defaultEmoSystemShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameEn equals to DEFAULT_NAME_EN
        defaultEmoSystemShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the emoSystemList where nameEn equals to UPDATED_NAME_EN
        defaultEmoSystemShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameEn not equals to DEFAULT_NAME_EN
        defaultEmoSystemShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the emoSystemList where nameEn not equals to UPDATED_NAME_EN
        defaultEmoSystemShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultEmoSystemShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the emoSystemList where nameEn equals to UPDATED_NAME_EN
        defaultEmoSystemShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameEn is not null
        defaultEmoSystemShouldBeFound("nameEn.specified=true");

        // Get all the emoSystemList where nameEn is null
        defaultEmoSystemShouldNotBeFound("nameEn.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameEnContainsSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameEn contains DEFAULT_NAME_EN
        defaultEmoSystemShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the emoSystemList where nameEn contains UPDATED_NAME_EN
        defaultEmoSystemShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where nameEn does not contain DEFAULT_NAME_EN
        defaultEmoSystemShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the emoSystemList where nameEn does not contain UPDATED_NAME_EN
        defaultEmoSystemShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where code equals to DEFAULT_CODE
        defaultEmoSystemShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the emoSystemList where code equals to UPDATED_CODE
        defaultEmoSystemShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where code not equals to DEFAULT_CODE
        defaultEmoSystemShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the emoSystemList where code not equals to UPDATED_CODE
        defaultEmoSystemShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEmoSystemShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the emoSystemList where code equals to UPDATED_CODE
        defaultEmoSystemShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where code is not null
        defaultEmoSystemShouldBeFound("code.specified=true");

        // Get all the emoSystemList where code is null
        defaultEmoSystemShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByCodeContainsSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where code contains DEFAULT_CODE
        defaultEmoSystemShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the emoSystemList where code contains UPDATED_CODE
        defaultEmoSystemShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where code does not contain DEFAULT_CODE
        defaultEmoSystemShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the emoSystemList where code does not contain UPDATED_CODE
        defaultEmoSystemShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status equals to DEFAULT_STATUS
        defaultEmoSystemShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emoSystemList where status equals to UPDATED_STATUS
        defaultEmoSystemShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status not equals to DEFAULT_STATUS
        defaultEmoSystemShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the emoSystemList where status not equals to UPDATED_STATUS
        defaultEmoSystemShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmoSystemShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emoSystemList where status equals to UPDATED_STATUS
        defaultEmoSystemShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status is not null
        defaultEmoSystemShouldBeFound("status.specified=true");

        // Get all the emoSystemList where status is null
        defaultEmoSystemShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status is greater than or equal to DEFAULT_STATUS
        defaultEmoSystemShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoSystemList where status is greater than or equal to UPDATED_STATUS
        defaultEmoSystemShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status is less than or equal to DEFAULT_STATUS
        defaultEmoSystemShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoSystemList where status is less than or equal to SMALLER_STATUS
        defaultEmoSystemShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status is less than DEFAULT_STATUS
        defaultEmoSystemShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the emoSystemList where status is less than UPDATED_STATUS
        defaultEmoSystemShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        // Get all the emoSystemList where status is greater than DEFAULT_STATUS
        defaultEmoSystemShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the emoSystemList where status is greater than SMALLER_STATUS
        defaultEmoSystemShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByEmoSystemMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);
        EmoMessages emoSystemMessages = EmoMessagesResourceIT.createEntity(em);
        em.persist(emoSystemMessages);
        em.flush();
        emoSystem.addEmoSystemMessages(emoSystemMessages);
        emoSystemRepository.saveAndFlush(emoSystem);
        Long emoSystemMessagesId = emoSystemMessages.getId();

        // Get all the emoSystemList where emoSystemMessages equals to emoSystemMessagesId
        defaultEmoSystemShouldBeFound("emoSystemMessagesId.equals=" + emoSystemMessagesId);

        // Get all the emoSystemList where emoSystemMessages equals to emoSystemMessagesId + 1
        defaultEmoSystemShouldNotBeFound("emoSystemMessagesId.equals=" + (emoSystemMessagesId + 1));
    }

    @Test
    @Transactional
    public void getAllEmoSystemsByEmoSystemServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);
        EmoSystemServices emoSystemServices = EmoSystemServicesResourceIT.createEntity(em);
        em.persist(emoSystemServices);
        em.flush();
        emoSystem.addEmoSystemServices(emoSystemServices);
        emoSystemRepository.saveAndFlush(emoSystem);
        Long emoSystemServicesId = emoSystemServices.getId();

        // Get all the emoSystemList where emoSystemServices equals to emoSystemServicesId
        defaultEmoSystemShouldBeFound("emoSystemServicesId.equals=" + emoSystemServicesId);

        // Get all the emoSystemList where emoSystemServices equals to emoSystemServicesId + 1
        defaultEmoSystemShouldNotBeFound("emoSystemServicesId.equals=" + (emoSystemServicesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmoSystemShouldBeFound(String filter) throws Exception {
        restEmoSystemMockMvc
            .perform(get("/api/emo-systems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restEmoSystemMockMvc
            .perform(get("/api/emo-systems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmoSystemShouldNotBeFound(String filter) throws Exception {
        restEmoSystemMockMvc
            .perform(get("/api/emo-systems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmoSystemMockMvc
            .perform(get("/api/emo-systems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmoSystem() throws Exception {
        // Get the emoSystem
        restEmoSystemMockMvc.perform(get("/api/emo-systems/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmoSystem() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        int databaseSizeBeforeUpdate = emoSystemRepository.findAll().size();

        // Update the emoSystem
        EmoSystem updatedEmoSystem = emoSystemRepository.findById(emoSystem.getId()).get();
        // Disconnect from session so that the updates on updatedEmoSystem are not directly saved in db
        em.detach(updatedEmoSystem);
        updatedEmoSystem.nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        EmoSystemDTO emoSystemDTO = emoSystemMapper.toDto(updatedEmoSystem);

        restEmoSystemMockMvc
            .perform(
                put("/api/emo-systems").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoSystemDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmoSystem in the database
        List<EmoSystem> emoSystemList = emoSystemRepository.findAll();
        assertThat(emoSystemList).hasSize(databaseSizeBeforeUpdate);
        EmoSystem testEmoSystem = emoSystemList.get(emoSystemList.size() - 1);
        assertThat(testEmoSystem.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testEmoSystem.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testEmoSystem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmoSystem.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmoSystem() throws Exception {
        int databaseSizeBeforeUpdate = emoSystemRepository.findAll().size();

        // Create the EmoSystem
        EmoSystemDTO emoSystemDTO = emoSystemMapper.toDto(emoSystem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmoSystemMockMvc
            .perform(
                put("/api/emo-systems").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoSystem in the database
        List<EmoSystem> emoSystemList = emoSystemRepository.findAll();
        assertThat(emoSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmoSystem() throws Exception {
        // Initialize the database
        emoSystemRepository.saveAndFlush(emoSystem);

        int databaseSizeBeforeDelete = emoSystemRepository.findAll().size();

        // Delete the emoSystem
        restEmoSystemMockMvc
            .perform(delete("/api/emo-systems/{id}", emoSystem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmoSystem> emoSystemList = emoSystemRepository.findAll();
        assertThat(emoSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
