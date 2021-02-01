package com.isoft.emotion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.emotion.EmotionFeedbackApp;
import com.isoft.emotion.domain.EmoCenter;
import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.repository.EmoCenterRepository;
import com.isoft.emotion.service.EmoCenterQueryService;
import com.isoft.emotion.service.EmoCenterService;
import com.isoft.emotion.service.dto.EmoCenterCriteria;
import com.isoft.emotion.service.dto.EmoCenterDTO;
import com.isoft.emotion.service.mapper.EmoCenterMapper;
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
 * Integration tests for the {@link EmoCenterResource} REST controller.
 */
@SpringBootTest(classes = EmotionFeedbackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmoCenterResourceIT {
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
    private EmoCenterRepository emoCenterRepository;

    @Autowired
    private EmoCenterMapper emoCenterMapper;

    @Autowired
    private EmoCenterService emoCenterService;

    @Autowired
    private EmoCenterQueryService emoCenterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmoCenterMockMvc;

    private EmoCenter emoCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoCenter createEntity(EntityManager em) {
        EmoCenter emoCenter = new EmoCenter().nameAr(DEFAULT_NAME_AR).nameEn(DEFAULT_NAME_EN).code(DEFAULT_CODE).status(DEFAULT_STATUS);
        return emoCenter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoCenter createUpdatedEntity(EntityManager em) {
        EmoCenter emoCenter = new EmoCenter().nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        return emoCenter;
    }

    @BeforeEach
    public void initTest() {
        emoCenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmoCenter() throws Exception {
        int databaseSizeBeforeCreate = emoCenterRepository.findAll().size();
        // Create the EmoCenter
        EmoCenterDTO emoCenterDTO = emoCenterMapper.toDto(emoCenter);
        restEmoCenterMockMvc
            .perform(
                post("/api/emo-centers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoCenterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmoCenter in the database
        List<EmoCenter> emoCenterList = emoCenterRepository.findAll();
        assertThat(emoCenterList).hasSize(databaseSizeBeforeCreate + 1);
        EmoCenter testEmoCenter = emoCenterList.get(emoCenterList.size() - 1);
        assertThat(testEmoCenter.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testEmoCenter.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testEmoCenter.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmoCenter.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEmoCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emoCenterRepository.findAll().size();

        // Create the EmoCenter with an existing ID
        emoCenter.setId(1L);
        EmoCenterDTO emoCenterDTO = emoCenterMapper.toDto(emoCenter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmoCenterMockMvc
            .perform(
                post("/api/emo-centers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoCenter in the database
        List<EmoCenter> emoCenterList = emoCenterRepository.findAll();
        assertThat(emoCenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmoCenters() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList
        restEmoCenterMockMvc
            .perform(get("/api/emo-centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getEmoCenter() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get the emoCenter
        restEmoCenterMockMvc
            .perform(get("/api/emo-centers/{id}", emoCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emoCenter.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getEmoCentersByIdFiltering() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        Long id = emoCenter.getId();

        defaultEmoCenterShouldBeFound("id.equals=" + id);
        defaultEmoCenterShouldNotBeFound("id.notEquals=" + id);

        defaultEmoCenterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmoCenterShouldNotBeFound("id.greaterThan=" + id);

        defaultEmoCenterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmoCenterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameAr equals to DEFAULT_NAME_AR
        defaultEmoCenterShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the emoCenterList where nameAr equals to UPDATED_NAME_AR
        defaultEmoCenterShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameAr not equals to DEFAULT_NAME_AR
        defaultEmoCenterShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the emoCenterList where nameAr not equals to UPDATED_NAME_AR
        defaultEmoCenterShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultEmoCenterShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the emoCenterList where nameAr equals to UPDATED_NAME_AR
        defaultEmoCenterShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameAr is not null
        defaultEmoCenterShouldBeFound("nameAr.specified=true");

        // Get all the emoCenterList where nameAr is null
        defaultEmoCenterShouldNotBeFound("nameAr.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameArContainsSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameAr contains DEFAULT_NAME_AR
        defaultEmoCenterShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the emoCenterList where nameAr contains UPDATED_NAME_AR
        defaultEmoCenterShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameAr does not contain DEFAULT_NAME_AR
        defaultEmoCenterShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the emoCenterList where nameAr does not contain UPDATED_NAME_AR
        defaultEmoCenterShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameEn equals to DEFAULT_NAME_EN
        defaultEmoCenterShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the emoCenterList where nameEn equals to UPDATED_NAME_EN
        defaultEmoCenterShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameEn not equals to DEFAULT_NAME_EN
        defaultEmoCenterShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the emoCenterList where nameEn not equals to UPDATED_NAME_EN
        defaultEmoCenterShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultEmoCenterShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the emoCenterList where nameEn equals to UPDATED_NAME_EN
        defaultEmoCenterShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameEn is not null
        defaultEmoCenterShouldBeFound("nameEn.specified=true");

        // Get all the emoCenterList where nameEn is null
        defaultEmoCenterShouldNotBeFound("nameEn.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameEnContainsSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameEn contains DEFAULT_NAME_EN
        defaultEmoCenterShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the emoCenterList where nameEn contains UPDATED_NAME_EN
        defaultEmoCenterShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where nameEn does not contain DEFAULT_NAME_EN
        defaultEmoCenterShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the emoCenterList where nameEn does not contain UPDATED_NAME_EN
        defaultEmoCenterShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where code equals to DEFAULT_CODE
        defaultEmoCenterShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the emoCenterList where code equals to UPDATED_CODE
        defaultEmoCenterShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where code not equals to DEFAULT_CODE
        defaultEmoCenterShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the emoCenterList where code not equals to UPDATED_CODE
        defaultEmoCenterShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEmoCenterShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the emoCenterList where code equals to UPDATED_CODE
        defaultEmoCenterShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where code is not null
        defaultEmoCenterShouldBeFound("code.specified=true");

        // Get all the emoCenterList where code is null
        defaultEmoCenterShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoCentersByCodeContainsSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where code contains DEFAULT_CODE
        defaultEmoCenterShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the emoCenterList where code contains UPDATED_CODE
        defaultEmoCenterShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where code does not contain DEFAULT_CODE
        defaultEmoCenterShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the emoCenterList where code does not contain UPDATED_CODE
        defaultEmoCenterShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status equals to DEFAULT_STATUS
        defaultEmoCenterShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emoCenterList where status equals to UPDATED_STATUS
        defaultEmoCenterShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status not equals to DEFAULT_STATUS
        defaultEmoCenterShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the emoCenterList where status not equals to UPDATED_STATUS
        defaultEmoCenterShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmoCenterShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emoCenterList where status equals to UPDATED_STATUS
        defaultEmoCenterShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status is not null
        defaultEmoCenterShouldBeFound("status.specified=true");

        // Get all the emoCenterList where status is null
        defaultEmoCenterShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status is greater than or equal to DEFAULT_STATUS
        defaultEmoCenterShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoCenterList where status is greater than or equal to UPDATED_STATUS
        defaultEmoCenterShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status is less than or equal to DEFAULT_STATUS
        defaultEmoCenterShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoCenterList where status is less than or equal to SMALLER_STATUS
        defaultEmoCenterShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status is less than DEFAULT_STATUS
        defaultEmoCenterShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the emoCenterList where status is less than UPDATED_STATUS
        defaultEmoCenterShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        // Get all the emoCenterList where status is greater than DEFAULT_STATUS
        defaultEmoCenterShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the emoCenterList where status is greater than SMALLER_STATUS
        defaultEmoCenterShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoCentersByEmoCenterMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);
        EmoMessages emoCenterMessages = EmoMessagesResourceIT.createEntity(em);
        em.persist(emoCenterMessages);
        em.flush();
        emoCenter.addEmoCenterMessages(emoCenterMessages);
        emoCenterRepository.saveAndFlush(emoCenter);
        Long emoCenterMessagesId = emoCenterMessages.getId();

        // Get all the emoCenterList where emoCenterMessages equals to emoCenterMessagesId
        defaultEmoCenterShouldBeFound("emoCenterMessagesId.equals=" + emoCenterMessagesId);

        // Get all the emoCenterList where emoCenterMessages equals to emoCenterMessagesId + 1
        defaultEmoCenterShouldNotBeFound("emoCenterMessagesId.equals=" + (emoCenterMessagesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmoCenterShouldBeFound(String filter) throws Exception {
        restEmoCenterMockMvc
            .perform(get("/api/emo-centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restEmoCenterMockMvc
            .perform(get("/api/emo-centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmoCenterShouldNotBeFound(String filter) throws Exception {
        restEmoCenterMockMvc
            .perform(get("/api/emo-centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmoCenterMockMvc
            .perform(get("/api/emo-centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmoCenter() throws Exception {
        // Get the emoCenter
        restEmoCenterMockMvc.perform(get("/api/emo-centers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmoCenter() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        int databaseSizeBeforeUpdate = emoCenterRepository.findAll().size();

        // Update the emoCenter
        EmoCenter updatedEmoCenter = emoCenterRepository.findById(emoCenter.getId()).get();
        // Disconnect from session so that the updates on updatedEmoCenter are not directly saved in db
        em.detach(updatedEmoCenter);
        updatedEmoCenter.nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        EmoCenterDTO emoCenterDTO = emoCenterMapper.toDto(updatedEmoCenter);

        restEmoCenterMockMvc
            .perform(
                put("/api/emo-centers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoCenterDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmoCenter in the database
        List<EmoCenter> emoCenterList = emoCenterRepository.findAll();
        assertThat(emoCenterList).hasSize(databaseSizeBeforeUpdate);
        EmoCenter testEmoCenter = emoCenterList.get(emoCenterList.size() - 1);
        assertThat(testEmoCenter.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testEmoCenter.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testEmoCenter.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmoCenter.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmoCenter() throws Exception {
        int databaseSizeBeforeUpdate = emoCenterRepository.findAll().size();

        // Create the EmoCenter
        EmoCenterDTO emoCenterDTO = emoCenterMapper.toDto(emoCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmoCenterMockMvc
            .perform(
                put("/api/emo-centers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoCenter in the database
        List<EmoCenter> emoCenterList = emoCenterRepository.findAll();
        assertThat(emoCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmoCenter() throws Exception {
        // Initialize the database
        emoCenterRepository.saveAndFlush(emoCenter);

        int databaseSizeBeforeDelete = emoCenterRepository.findAll().size();

        // Delete the emoCenter
        restEmoCenterMockMvc
            .perform(delete("/api/emo-centers/{id}", emoCenter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmoCenter> emoCenterList = emoCenterRepository.findAll();
        assertThat(emoCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
