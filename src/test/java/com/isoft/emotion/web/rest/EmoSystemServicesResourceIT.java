package com.isoft.emotion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.emotion.EmotionFeedbackApp;
import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.domain.EmoSystem;
import com.isoft.emotion.domain.EmoSystemServices;
import com.isoft.emotion.repository.EmoSystemServicesRepository;
import com.isoft.emotion.service.EmoSystemServicesQueryService;
import com.isoft.emotion.service.EmoSystemServicesService;
import com.isoft.emotion.service.dto.EmoSystemServicesCriteria;
import com.isoft.emotion.service.dto.EmoSystemServicesDTO;
import com.isoft.emotion.service.mapper.EmoSystemServicesMapper;
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
 * Integration tests for the {@link EmoSystemServicesResource} REST controller.
 */
@SpringBootTest(classes = EmotionFeedbackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmoSystemServicesResourceIT {
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
    private EmoSystemServicesRepository emoSystemServicesRepository;

    @Autowired
    private EmoSystemServicesMapper emoSystemServicesMapper;

    @Autowired
    private EmoSystemServicesService emoSystemServicesService;

    @Autowired
    private EmoSystemServicesQueryService emoSystemServicesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmoSystemServicesMockMvc;

    private EmoSystemServices emoSystemServices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoSystemServices createEntity(EntityManager em) {
        EmoSystemServices emoSystemServices = new EmoSystemServices()
            .nameAr(DEFAULT_NAME_AR)
            .nameEn(DEFAULT_NAME_EN)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return emoSystemServices;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoSystemServices createUpdatedEntity(EntityManager em) {
        EmoSystemServices emoSystemServices = new EmoSystemServices()
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        return emoSystemServices;
    }

    @BeforeEach
    public void initTest() {
        emoSystemServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmoSystemServices() throws Exception {
        int databaseSizeBeforeCreate = emoSystemServicesRepository.findAll().size();
        // Create the EmoSystemServices
        EmoSystemServicesDTO emoSystemServicesDTO = emoSystemServicesMapper.toDto(emoSystemServices);
        restEmoSystemServicesMockMvc
            .perform(
                post("/api/emo-system-services")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoSystemServicesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmoSystemServices in the database
        List<EmoSystemServices> emoSystemServicesList = emoSystemServicesRepository.findAll();
        assertThat(emoSystemServicesList).hasSize(databaseSizeBeforeCreate + 1);
        EmoSystemServices testEmoSystemServices = emoSystemServicesList.get(emoSystemServicesList.size() - 1);
        assertThat(testEmoSystemServices.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testEmoSystemServices.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testEmoSystemServices.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmoSystemServices.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEmoSystemServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emoSystemServicesRepository.findAll().size();

        // Create the EmoSystemServices with an existing ID
        emoSystemServices.setId(1L);
        EmoSystemServicesDTO emoSystemServicesDTO = emoSystemServicesMapper.toDto(emoSystemServices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmoSystemServicesMockMvc
            .perform(
                post("/api/emo-system-services")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoSystemServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoSystemServices in the database
        List<EmoSystemServices> emoSystemServicesList = emoSystemServicesRepository.findAll();
        assertThat(emoSystemServicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServices() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList
        restEmoSystemServicesMockMvc
            .perform(get("/api/emo-system-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoSystemServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getEmoSystemServices() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get the emoSystemServices
        restEmoSystemServicesMockMvc
            .perform(get("/api/emo-system-services/{id}", emoSystemServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emoSystemServices.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getEmoSystemServicesByIdFiltering() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        Long id = emoSystemServices.getId();

        defaultEmoSystemServicesShouldBeFound("id.equals=" + id);
        defaultEmoSystemServicesShouldNotBeFound("id.notEquals=" + id);

        defaultEmoSystemServicesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmoSystemServicesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmoSystemServicesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmoSystemServicesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameAr equals to DEFAULT_NAME_AR
        defaultEmoSystemServicesShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the emoSystemServicesList where nameAr equals to UPDATED_NAME_AR
        defaultEmoSystemServicesShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameAr not equals to DEFAULT_NAME_AR
        defaultEmoSystemServicesShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the emoSystemServicesList where nameAr not equals to UPDATED_NAME_AR
        defaultEmoSystemServicesShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultEmoSystemServicesShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the emoSystemServicesList where nameAr equals to UPDATED_NAME_AR
        defaultEmoSystemServicesShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameAr is not null
        defaultEmoSystemServicesShouldBeFound("nameAr.specified=true");

        // Get all the emoSystemServicesList where nameAr is null
        defaultEmoSystemServicesShouldNotBeFound("nameAr.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameArContainsSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameAr contains DEFAULT_NAME_AR
        defaultEmoSystemServicesShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the emoSystemServicesList where nameAr contains UPDATED_NAME_AR
        defaultEmoSystemServicesShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameAr does not contain DEFAULT_NAME_AR
        defaultEmoSystemServicesShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the emoSystemServicesList where nameAr does not contain UPDATED_NAME_AR
        defaultEmoSystemServicesShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameEn equals to DEFAULT_NAME_EN
        defaultEmoSystemServicesShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the emoSystemServicesList where nameEn equals to UPDATED_NAME_EN
        defaultEmoSystemServicesShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameEn not equals to DEFAULT_NAME_EN
        defaultEmoSystemServicesShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the emoSystemServicesList where nameEn not equals to UPDATED_NAME_EN
        defaultEmoSystemServicesShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultEmoSystemServicesShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the emoSystemServicesList where nameEn equals to UPDATED_NAME_EN
        defaultEmoSystemServicesShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameEn is not null
        defaultEmoSystemServicesShouldBeFound("nameEn.specified=true");

        // Get all the emoSystemServicesList where nameEn is null
        defaultEmoSystemServicesShouldNotBeFound("nameEn.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameEnContainsSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameEn contains DEFAULT_NAME_EN
        defaultEmoSystemServicesShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the emoSystemServicesList where nameEn contains UPDATED_NAME_EN
        defaultEmoSystemServicesShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where nameEn does not contain DEFAULT_NAME_EN
        defaultEmoSystemServicesShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the emoSystemServicesList where nameEn does not contain UPDATED_NAME_EN
        defaultEmoSystemServicesShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where code equals to DEFAULT_CODE
        defaultEmoSystemServicesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the emoSystemServicesList where code equals to UPDATED_CODE
        defaultEmoSystemServicesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where code not equals to DEFAULT_CODE
        defaultEmoSystemServicesShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the emoSystemServicesList where code not equals to UPDATED_CODE
        defaultEmoSystemServicesShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEmoSystemServicesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the emoSystemServicesList where code equals to UPDATED_CODE
        defaultEmoSystemServicesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where code is not null
        defaultEmoSystemServicesShouldBeFound("code.specified=true");

        // Get all the emoSystemServicesList where code is null
        defaultEmoSystemServicesShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByCodeContainsSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where code contains DEFAULT_CODE
        defaultEmoSystemServicesShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the emoSystemServicesList where code contains UPDATED_CODE
        defaultEmoSystemServicesShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where code does not contain DEFAULT_CODE
        defaultEmoSystemServicesShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the emoSystemServicesList where code does not contain UPDATED_CODE
        defaultEmoSystemServicesShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status equals to DEFAULT_STATUS
        defaultEmoSystemServicesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emoSystemServicesList where status equals to UPDATED_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status not equals to DEFAULT_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the emoSystemServicesList where status not equals to UPDATED_STATUS
        defaultEmoSystemServicesShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmoSystemServicesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emoSystemServicesList where status equals to UPDATED_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status is not null
        defaultEmoSystemServicesShouldBeFound("status.specified=true");

        // Get all the emoSystemServicesList where status is null
        defaultEmoSystemServicesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status is greater than or equal to DEFAULT_STATUS
        defaultEmoSystemServicesShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoSystemServicesList where status is greater than or equal to UPDATED_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status is less than or equal to DEFAULT_STATUS
        defaultEmoSystemServicesShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoSystemServicesList where status is less than or equal to SMALLER_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status is less than DEFAULT_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the emoSystemServicesList where status is less than UPDATED_STATUS
        defaultEmoSystemServicesShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        // Get all the emoSystemServicesList where status is greater than DEFAULT_STATUS
        defaultEmoSystemServicesShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the emoSystemServicesList where status is greater than SMALLER_STATUS
        defaultEmoSystemServicesShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByEmoSystemServicesMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);
        EmoMessages emoSystemServicesMessages = EmoMessagesResourceIT.createEntity(em);
        em.persist(emoSystemServicesMessages);
        em.flush();
        emoSystemServices.addEmoSystemServicesMessages(emoSystemServicesMessages);
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);
        Long emoSystemServicesMessagesId = emoSystemServicesMessages.getId();

        // Get all the emoSystemServicesList where emoSystemServicesMessages equals to emoSystemServicesMessagesId
        defaultEmoSystemServicesShouldBeFound("emoSystemServicesMessagesId.equals=" + emoSystemServicesMessagesId);

        // Get all the emoSystemServicesList where emoSystemServicesMessages equals to emoSystemServicesMessagesId + 1
        defaultEmoSystemServicesShouldNotBeFound("emoSystemServicesMessagesId.equals=" + (emoSystemServicesMessagesId + 1));
    }

    @Test
    @Transactional
    public void getAllEmoSystemServicesByEmoSystemIsEqualToSomething() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);
        EmoSystem emoSystem = EmoSystemResourceIT.createEntity(em);
        em.persist(emoSystem);
        em.flush();
        emoSystemServices.setEmoSystem(emoSystem);
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);
        Long emoSystemId = emoSystem.getId();

        // Get all the emoSystemServicesList where emoSystem equals to emoSystemId
        defaultEmoSystemServicesShouldBeFound("emoSystemId.equals=" + emoSystemId);

        // Get all the emoSystemServicesList where emoSystem equals to emoSystemId + 1
        defaultEmoSystemServicesShouldNotBeFound("emoSystemId.equals=" + (emoSystemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmoSystemServicesShouldBeFound(String filter) throws Exception {
        restEmoSystemServicesMockMvc
            .perform(get("/api/emo-system-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoSystemServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restEmoSystemServicesMockMvc
            .perform(get("/api/emo-system-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmoSystemServicesShouldNotBeFound(String filter) throws Exception {
        restEmoSystemServicesMockMvc
            .perform(get("/api/emo-system-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmoSystemServicesMockMvc
            .perform(get("/api/emo-system-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmoSystemServices() throws Exception {
        // Get the emoSystemServices
        restEmoSystemServicesMockMvc.perform(get("/api/emo-system-services/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmoSystemServices() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        int databaseSizeBeforeUpdate = emoSystemServicesRepository.findAll().size();

        // Update the emoSystemServices
        EmoSystemServices updatedEmoSystemServices = emoSystemServicesRepository.findById(emoSystemServices.getId()).get();
        // Disconnect from session so that the updates on updatedEmoSystemServices are not directly saved in db
        em.detach(updatedEmoSystemServices);
        updatedEmoSystemServices.nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        EmoSystemServicesDTO emoSystemServicesDTO = emoSystemServicesMapper.toDto(updatedEmoSystemServices);

        restEmoSystemServicesMockMvc
            .perform(
                put("/api/emo-system-services")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoSystemServicesDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmoSystemServices in the database
        List<EmoSystemServices> emoSystemServicesList = emoSystemServicesRepository.findAll();
        assertThat(emoSystemServicesList).hasSize(databaseSizeBeforeUpdate);
        EmoSystemServices testEmoSystemServices = emoSystemServicesList.get(emoSystemServicesList.size() - 1);
        assertThat(testEmoSystemServices.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testEmoSystemServices.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testEmoSystemServices.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmoSystemServices.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmoSystemServices() throws Exception {
        int databaseSizeBeforeUpdate = emoSystemServicesRepository.findAll().size();

        // Create the EmoSystemServices
        EmoSystemServicesDTO emoSystemServicesDTO = emoSystemServicesMapper.toDto(emoSystemServices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmoSystemServicesMockMvc
            .perform(
                put("/api/emo-system-services")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoSystemServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoSystemServices in the database
        List<EmoSystemServices> emoSystemServicesList = emoSystemServicesRepository.findAll();
        assertThat(emoSystemServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmoSystemServices() throws Exception {
        // Initialize the database
        emoSystemServicesRepository.saveAndFlush(emoSystemServices);

        int databaseSizeBeforeDelete = emoSystemServicesRepository.findAll().size();

        // Delete the emoSystemServices
        restEmoSystemServicesMockMvc
            .perform(delete("/api/emo-system-services/{id}", emoSystemServices.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmoSystemServices> emoSystemServicesList = emoSystemServicesRepository.findAll();
        assertThat(emoSystemServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
