package com.isoft.emotion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.emotion.EmotionFeedbackApp;
import com.isoft.emotion.domain.EmoMessageFeedback;
import com.isoft.emotion.repository.EmoMessageFeedbackRepository;
import com.isoft.emotion.service.EmoMessageFeedbackQueryService;
import com.isoft.emotion.service.EmoMessageFeedbackService;
import com.isoft.emotion.service.dto.EmoMessageFeedbackCriteria;
import com.isoft.emotion.service.dto.EmoMessageFeedbackDTO;
import com.isoft.emotion.service.mapper.EmoMessageFeedbackMapper;
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
 * Integration tests for the {@link EmoMessageFeedbackResource} REST controller.
 */
@SpringBootTest(classes = EmotionFeedbackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmoMessageFeedbackResourceIT {
    private static final Long DEFAULT_EMO_SYSTEM_ID = 1L;
    private static final Long UPDATED_EMO_SYSTEM_ID = 2L;
    private static final Long SMALLER_EMO_SYSTEM_ID = 1L - 1L;

    private static final Long DEFAULT_CENTER_ID = 1L;
    private static final Long UPDATED_CENTER_ID = 2L;
    private static final Long SMALLER_CENTER_ID = 1L - 1L;

    private static final Long DEFAULT_EMO_SYSTEM_SERVICES_ID = 1L;
    private static final Long UPDATED_EMO_SYSTEM_SERVICES_ID = 2L;
    private static final Long SMALLER_EMO_SYSTEM_SERVICES_ID = 1L - 1L;

    private static final Long DEFAULT_COUNTER = 1L;
    private static final Long UPDATED_COUNTER = 2L;
    private static final Long SMALLER_COUNTER = 1L - 1L;

    private static final Long DEFAULT_TRS_ID = 1L;
    private static final Long UPDATED_TRS_ID = 2L;
    private static final Long SMALLER_TRS_ID = 1L - 1L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_NAME = "BBBBBBBBBB";

    @Autowired
    private EmoMessageFeedbackRepository emoMessageFeedbackRepository;

    @Autowired
    private EmoMessageFeedbackMapper emoMessageFeedbackMapper;

    @Autowired
    private EmoMessageFeedbackService emoMessageFeedbackService;

    @Autowired
    private EmoMessageFeedbackQueryService emoMessageFeedbackQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmoMessageFeedbackMockMvc;

    private EmoMessageFeedback emoMessageFeedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoMessageFeedback createEntity(EntityManager em) {
        EmoMessageFeedback emoMessageFeedback = new EmoMessageFeedback()
            .emoSystemId(DEFAULT_EMO_SYSTEM_ID)
            .centerId(DEFAULT_CENTER_ID)
            .emoSystemServicesId(DEFAULT_EMO_SYSTEM_SERVICES_ID)
            .counter(DEFAULT_COUNTER)
            .trsId(DEFAULT_TRS_ID)
            .userId(DEFAULT_USER_ID)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .feedback(DEFAULT_FEEDBACK)
            .applicantName(DEFAULT_APPLICANT_NAME);
        return emoMessageFeedback;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoMessageFeedback createUpdatedEntity(EntityManager em) {
        EmoMessageFeedback emoMessageFeedback = new EmoMessageFeedback()
            .emoSystemId(UPDATED_EMO_SYSTEM_ID)
            .centerId(UPDATED_CENTER_ID)
            .emoSystemServicesId(UPDATED_EMO_SYSTEM_SERVICES_ID)
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .feedback(UPDATED_FEEDBACK)
            .applicantName(UPDATED_APPLICANT_NAME);
        return emoMessageFeedback;
    }

    @BeforeEach
    public void initTest() {
        emoMessageFeedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmoMessageFeedback() throws Exception {
        int databaseSizeBeforeCreate = emoMessageFeedbackRepository.findAll().size();
        // Create the EmoMessageFeedback
        EmoMessageFeedbackDTO emoMessageFeedbackDTO = emoMessageFeedbackMapper.toDto(emoMessageFeedback);
        restEmoMessageFeedbackMockMvc
            .perform(
                post("/api/emo-message-feedbacks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoMessageFeedbackDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmoMessageFeedback in the database
        List<EmoMessageFeedback> emoMessageFeedbackList = emoMessageFeedbackRepository.findAll();
        assertThat(emoMessageFeedbackList).hasSize(databaseSizeBeforeCreate + 1);
        EmoMessageFeedback testEmoMessageFeedback = emoMessageFeedbackList.get(emoMessageFeedbackList.size() - 1);
        assertThat(testEmoMessageFeedback.getEmoSystemId()).isEqualTo(DEFAULT_EMO_SYSTEM_ID);
        assertThat(testEmoMessageFeedback.getCenterId()).isEqualTo(DEFAULT_CENTER_ID);
        assertThat(testEmoMessageFeedback.getEmoSystemServicesId()).isEqualTo(DEFAULT_EMO_SYSTEM_SERVICES_ID);
        assertThat(testEmoMessageFeedback.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testEmoMessageFeedback.getTrsId()).isEqualTo(DEFAULT_TRS_ID);
        assertThat(testEmoMessageFeedback.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmoMessageFeedback.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEmoMessageFeedback.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmoMessageFeedback.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testEmoMessageFeedback.getApplicantName()).isEqualTo(DEFAULT_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void createEmoMessageFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emoMessageFeedbackRepository.findAll().size();

        // Create the EmoMessageFeedback with an existing ID
        emoMessageFeedback.setId(1L);
        EmoMessageFeedbackDTO emoMessageFeedbackDTO = emoMessageFeedbackMapper.toDto(emoMessageFeedback);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmoMessageFeedbackMockMvc
            .perform(
                post("/api/emo-message-feedbacks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoMessageFeedbackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoMessageFeedback in the database
        List<EmoMessageFeedback> emoMessageFeedbackList = emoMessageFeedbackRepository.findAll();
        assertThat(emoMessageFeedbackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacks() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList
        restEmoMessageFeedbackMockMvc
            .perform(get("/api/emo-message-feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoMessageFeedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].emoSystemId").value(hasItem(DEFAULT_EMO_SYSTEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].emoSystemServicesId").value(hasItem(DEFAULT_EMO_SYSTEM_SERVICES_ID.intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));
    }

    @Test
    @Transactional
    public void getEmoMessageFeedback() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get the emoMessageFeedback
        restEmoMessageFeedbackMockMvc
            .perform(get("/api/emo-message-feedbacks/{id}", emoMessageFeedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emoMessageFeedback.getId().intValue()))
            .andExpect(jsonPath("$.emoSystemId").value(DEFAULT_EMO_SYSTEM_ID.intValue()))
            .andExpect(jsonPath("$.centerId").value(DEFAULT_CENTER_ID.intValue()))
            .andExpect(jsonPath("$.emoSystemServicesId").value(DEFAULT_EMO_SYSTEM_SERVICES_ID.intValue()))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
            .andExpect(jsonPath("$.trsId").value(DEFAULT_TRS_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK))
            .andExpect(jsonPath("$.applicantName").value(DEFAULT_APPLICANT_NAME));
    }

    @Test
    @Transactional
    public void getEmoMessageFeedbacksByIdFiltering() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        Long id = emoMessageFeedback.getId();

        defaultEmoMessageFeedbackShouldBeFound("id.equals=" + id);
        defaultEmoMessageFeedbackShouldNotBeFound("id.notEquals=" + id);

        defaultEmoMessageFeedbackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmoMessageFeedbackShouldNotBeFound("id.greaterThan=" + id);

        defaultEmoMessageFeedbackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmoMessageFeedbackShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId equals to DEFAULT_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.equals=" + DEFAULT_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId equals to UPDATED_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.equals=" + UPDATED_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId not equals to DEFAULT_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.notEquals=" + DEFAULT_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId not equals to UPDATED_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.notEquals=" + UPDATED_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId in DEFAULT_EMO_SYSTEM_ID or UPDATED_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.in=" + DEFAULT_EMO_SYSTEM_ID + "," + UPDATED_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId equals to UPDATED_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.in=" + UPDATED_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId is not null
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.specified=true");

        // Get all the emoMessageFeedbackList where emoSystemId is null
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId is greater than or equal to DEFAULT_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.greaterThanOrEqual=" + DEFAULT_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId is greater than or equal to UPDATED_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.greaterThanOrEqual=" + UPDATED_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId is less than or equal to DEFAULT_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.lessThanOrEqual=" + DEFAULT_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId is less than or equal to SMALLER_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.lessThanOrEqual=" + SMALLER_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId is less than DEFAULT_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.lessThan=" + DEFAULT_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId is less than UPDATED_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.lessThan=" + UPDATED_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemId is greater than DEFAULT_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemId.greaterThan=" + DEFAULT_EMO_SYSTEM_ID);

        // Get all the emoMessageFeedbackList where emoSystemId is greater than SMALLER_EMO_SYSTEM_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemId.greaterThan=" + SMALLER_EMO_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId equals to DEFAULT_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.equals=" + DEFAULT_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId equals to UPDATED_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.equals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId not equals to DEFAULT_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.notEquals=" + DEFAULT_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId not equals to UPDATED_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.notEquals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId in DEFAULT_CENTER_ID or UPDATED_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.in=" + DEFAULT_CENTER_ID + "," + UPDATED_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId equals to UPDATED_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.in=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId is not null
        defaultEmoMessageFeedbackShouldBeFound("centerId.specified=true");

        // Get all the emoMessageFeedbackList where centerId is null
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId is greater than or equal to DEFAULT_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.greaterThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId is greater than or equal to UPDATED_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.greaterThanOrEqual=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId is less than or equal to DEFAULT_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.lessThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId is less than or equal to SMALLER_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.lessThanOrEqual=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId is less than DEFAULT_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.lessThan=" + DEFAULT_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId is less than UPDATED_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.lessThan=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCenterIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where centerId is greater than DEFAULT_CENTER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("centerId.greaterThan=" + DEFAULT_CENTER_ID);

        // Get all the emoMessageFeedbackList where centerId is greater than SMALLER_CENTER_ID
        defaultEmoMessageFeedbackShouldBeFound("centerId.greaterThan=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId equals to DEFAULT_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.equals=" + DEFAULT_EMO_SYSTEM_SERVICES_ID);

        // Get all the emoMessageFeedbackList where emoSystemServicesId equals to UPDATED_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.equals=" + UPDATED_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId not equals to DEFAULT_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.notEquals=" + DEFAULT_EMO_SYSTEM_SERVICES_ID);

        // Get all the emoMessageFeedbackList where emoSystemServicesId not equals to UPDATED_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.notEquals=" + UPDATED_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId in DEFAULT_EMO_SYSTEM_SERVICES_ID or UPDATED_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound(
            "emoSystemServicesId.in=" + DEFAULT_EMO_SYSTEM_SERVICES_ID + "," + UPDATED_EMO_SYSTEM_SERVICES_ID
        );

        // Get all the emoMessageFeedbackList where emoSystemServicesId equals to UPDATED_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.in=" + UPDATED_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is not null
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.specified=true");

        // Get all the emoMessageFeedbackList where emoSystemServicesId is null
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is greater than or equal to DEFAULT_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.greaterThanOrEqual=" + DEFAULT_EMO_SYSTEM_SERVICES_ID);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is greater than or equal to UPDATED_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.greaterThanOrEqual=" + UPDATED_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is less than or equal to DEFAULT_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.lessThanOrEqual=" + DEFAULT_EMO_SYSTEM_SERVICES_ID);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is less than or equal to SMALLER_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.lessThanOrEqual=" + SMALLER_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is less than DEFAULT_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.lessThan=" + DEFAULT_EMO_SYSTEM_SERVICES_ID);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is less than UPDATED_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.lessThan=" + UPDATED_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByEmoSystemServicesIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is greater than DEFAULT_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldNotBeFound("emoSystemServicesId.greaterThan=" + DEFAULT_EMO_SYSTEM_SERVICES_ID);

        // Get all the emoMessageFeedbackList where emoSystemServicesId is greater than SMALLER_EMO_SYSTEM_SERVICES_ID
        defaultEmoMessageFeedbackShouldBeFound("emoSystemServicesId.greaterThan=" + SMALLER_EMO_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter equals to DEFAULT_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.equals=" + DEFAULT_COUNTER);

        // Get all the emoMessageFeedbackList where counter equals to UPDATED_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.equals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter not equals to DEFAULT_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.notEquals=" + DEFAULT_COUNTER);

        // Get all the emoMessageFeedbackList where counter not equals to UPDATED_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.notEquals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter in DEFAULT_COUNTER or UPDATED_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.in=" + DEFAULT_COUNTER + "," + UPDATED_COUNTER);

        // Get all the emoMessageFeedbackList where counter equals to UPDATED_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.in=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter is not null
        defaultEmoMessageFeedbackShouldBeFound("counter.specified=true");

        // Get all the emoMessageFeedbackList where counter is null
        defaultEmoMessageFeedbackShouldNotBeFound("counter.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter is greater than or equal to DEFAULT_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.greaterThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the emoMessageFeedbackList where counter is greater than or equal to UPDATED_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.greaterThanOrEqual=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter is less than or equal to DEFAULT_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.lessThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the emoMessageFeedbackList where counter is less than or equal to SMALLER_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.lessThanOrEqual=" + SMALLER_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter is less than DEFAULT_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.lessThan=" + DEFAULT_COUNTER);

        // Get all the emoMessageFeedbackList where counter is less than UPDATED_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.lessThan=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByCounterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where counter is greater than DEFAULT_COUNTER
        defaultEmoMessageFeedbackShouldNotBeFound("counter.greaterThan=" + DEFAULT_COUNTER);

        // Get all the emoMessageFeedbackList where counter is greater than SMALLER_COUNTER
        defaultEmoMessageFeedbackShouldBeFound("counter.greaterThan=" + SMALLER_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId equals to DEFAULT_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.equals=" + DEFAULT_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId equals to UPDATED_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.equals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId not equals to DEFAULT_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.notEquals=" + DEFAULT_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId not equals to UPDATED_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.notEquals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId in DEFAULT_TRS_ID or UPDATED_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.in=" + DEFAULT_TRS_ID + "," + UPDATED_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId equals to UPDATED_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.in=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId is not null
        defaultEmoMessageFeedbackShouldBeFound("trsId.specified=true");

        // Get all the emoMessageFeedbackList where trsId is null
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId is greater than or equal to DEFAULT_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.greaterThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId is greater than or equal to UPDATED_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.greaterThanOrEqual=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId is less than or equal to DEFAULT_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.lessThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId is less than or equal to SMALLER_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.lessThanOrEqual=" + SMALLER_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId is less than DEFAULT_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.lessThan=" + DEFAULT_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId is less than UPDATED_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.lessThan=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByTrsIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where trsId is greater than DEFAULT_TRS_ID
        defaultEmoMessageFeedbackShouldNotBeFound("trsId.greaterThan=" + DEFAULT_TRS_ID);

        // Get all the emoMessageFeedbackList where trsId is greater than SMALLER_TRS_ID
        defaultEmoMessageFeedbackShouldBeFound("trsId.greaterThan=" + SMALLER_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId equals to DEFAULT_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the emoMessageFeedbackList where userId equals to UPDATED_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId not equals to DEFAULT_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the emoMessageFeedbackList where userId not equals to UPDATED_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the emoMessageFeedbackList where userId equals to UPDATED_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId is not null
        defaultEmoMessageFeedbackShouldBeFound("userId.specified=true");

        // Get all the emoMessageFeedbackList where userId is null
        defaultEmoMessageFeedbackShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId is greater than or equal to DEFAULT_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the emoMessageFeedbackList where userId is greater than or equal to UPDATED_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId is less than or equal to DEFAULT_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the emoMessageFeedbackList where userId is less than or equal to SMALLER_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId is less than DEFAULT_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the emoMessageFeedbackList where userId is less than UPDATED_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where userId is greater than DEFAULT_USER_ID
        defaultEmoMessageFeedbackShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the emoMessageFeedbackList where userId is greater than SMALLER_USER_ID
        defaultEmoMessageFeedbackShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where message equals to DEFAULT_MESSAGE
        defaultEmoMessageFeedbackShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the emoMessageFeedbackList where message equals to UPDATED_MESSAGE
        defaultEmoMessageFeedbackShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where message not equals to DEFAULT_MESSAGE
        defaultEmoMessageFeedbackShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the emoMessageFeedbackList where message not equals to UPDATED_MESSAGE
        defaultEmoMessageFeedbackShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultEmoMessageFeedbackShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the emoMessageFeedbackList where message equals to UPDATED_MESSAGE
        defaultEmoMessageFeedbackShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where message is not null
        defaultEmoMessageFeedbackShouldBeFound("message.specified=true");

        // Get all the emoMessageFeedbackList where message is null
        defaultEmoMessageFeedbackShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByMessageContainsSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where message contains DEFAULT_MESSAGE
        defaultEmoMessageFeedbackShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the emoMessageFeedbackList where message contains UPDATED_MESSAGE
        defaultEmoMessageFeedbackShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where message does not contain DEFAULT_MESSAGE
        defaultEmoMessageFeedbackShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the emoMessageFeedbackList where message does not contain UPDATED_MESSAGE
        defaultEmoMessageFeedbackShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status equals to DEFAULT_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emoMessageFeedbackList where status equals to UPDATED_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status not equals to DEFAULT_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the emoMessageFeedbackList where status not equals to UPDATED_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emoMessageFeedbackList where status equals to UPDATED_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status is not null
        defaultEmoMessageFeedbackShouldBeFound("status.specified=true");

        // Get all the emoMessageFeedbackList where status is null
        defaultEmoMessageFeedbackShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status is greater than or equal to DEFAULT_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoMessageFeedbackList where status is greater than or equal to UPDATED_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status is less than or equal to DEFAULT_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoMessageFeedbackList where status is less than or equal to SMALLER_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status is less than DEFAULT_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the emoMessageFeedbackList where status is less than UPDATED_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where status is greater than DEFAULT_STATUS
        defaultEmoMessageFeedbackShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the emoMessageFeedbackList where status is greater than SMALLER_STATUS
        defaultEmoMessageFeedbackShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where feedback equals to DEFAULT_FEEDBACK
        defaultEmoMessageFeedbackShouldBeFound("feedback.equals=" + DEFAULT_FEEDBACK);

        // Get all the emoMessageFeedbackList where feedback equals to UPDATED_FEEDBACK
        defaultEmoMessageFeedbackShouldNotBeFound("feedback.equals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByFeedbackIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where feedback not equals to DEFAULT_FEEDBACK
        defaultEmoMessageFeedbackShouldNotBeFound("feedback.notEquals=" + DEFAULT_FEEDBACK);

        // Get all the emoMessageFeedbackList where feedback not equals to UPDATED_FEEDBACK
        defaultEmoMessageFeedbackShouldBeFound("feedback.notEquals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where feedback in DEFAULT_FEEDBACK or UPDATED_FEEDBACK
        defaultEmoMessageFeedbackShouldBeFound("feedback.in=" + DEFAULT_FEEDBACK + "," + UPDATED_FEEDBACK);

        // Get all the emoMessageFeedbackList where feedback equals to UPDATED_FEEDBACK
        defaultEmoMessageFeedbackShouldNotBeFound("feedback.in=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where feedback is not null
        defaultEmoMessageFeedbackShouldBeFound("feedback.specified=true");

        // Get all the emoMessageFeedbackList where feedback is null
        defaultEmoMessageFeedbackShouldNotBeFound("feedback.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByFeedbackContainsSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where feedback contains DEFAULT_FEEDBACK
        defaultEmoMessageFeedbackShouldBeFound("feedback.contains=" + DEFAULT_FEEDBACK);

        // Get all the emoMessageFeedbackList where feedback contains UPDATED_FEEDBACK
        defaultEmoMessageFeedbackShouldNotBeFound("feedback.contains=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByFeedbackNotContainsSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where feedback does not contain DEFAULT_FEEDBACK
        defaultEmoMessageFeedbackShouldNotBeFound("feedback.doesNotContain=" + DEFAULT_FEEDBACK);

        // Get all the emoMessageFeedbackList where feedback does not contain UPDATED_FEEDBACK
        defaultEmoMessageFeedbackShouldBeFound("feedback.doesNotContain=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByApplicantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where applicantName equals to DEFAULT_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldBeFound("applicantName.equals=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessageFeedbackList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldNotBeFound("applicantName.equals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByApplicantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where applicantName not equals to DEFAULT_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldNotBeFound("applicantName.notEquals=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessageFeedbackList where applicantName not equals to UPDATED_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldBeFound("applicantName.notEquals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByApplicantNameIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where applicantName in DEFAULT_APPLICANT_NAME or UPDATED_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldBeFound("applicantName.in=" + DEFAULT_APPLICANT_NAME + "," + UPDATED_APPLICANT_NAME);

        // Get all the emoMessageFeedbackList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldNotBeFound("applicantName.in=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByApplicantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where applicantName is not null
        defaultEmoMessageFeedbackShouldBeFound("applicantName.specified=true");

        // Get all the emoMessageFeedbackList where applicantName is null
        defaultEmoMessageFeedbackShouldNotBeFound("applicantName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByApplicantNameContainsSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where applicantName contains DEFAULT_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldBeFound("applicantName.contains=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessageFeedbackList where applicantName contains UPDATED_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldNotBeFound("applicantName.contains=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessageFeedbacksByApplicantNameNotContainsSomething() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        // Get all the emoMessageFeedbackList where applicantName does not contain DEFAULT_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldNotBeFound("applicantName.doesNotContain=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessageFeedbackList where applicantName does not contain UPDATED_APPLICANT_NAME
        defaultEmoMessageFeedbackShouldBeFound("applicantName.doesNotContain=" + UPDATED_APPLICANT_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmoMessageFeedbackShouldBeFound(String filter) throws Exception {
        restEmoMessageFeedbackMockMvc
            .perform(get("/api/emo-message-feedbacks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoMessageFeedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].emoSystemId").value(hasItem(DEFAULT_EMO_SYSTEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].emoSystemServicesId").value(hasItem(DEFAULT_EMO_SYSTEM_SERVICES_ID.intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));

        // Check, that the count call also returns 1
        restEmoMessageFeedbackMockMvc
            .perform(get("/api/emo-message-feedbacks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmoMessageFeedbackShouldNotBeFound(String filter) throws Exception {
        restEmoMessageFeedbackMockMvc
            .perform(get("/api/emo-message-feedbacks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmoMessageFeedbackMockMvc
            .perform(get("/api/emo-message-feedbacks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmoMessageFeedback() throws Exception {
        // Get the emoMessageFeedback
        restEmoMessageFeedbackMockMvc.perform(get("/api/emo-message-feedbacks/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmoMessageFeedback() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        int databaseSizeBeforeUpdate = emoMessageFeedbackRepository.findAll().size();

        // Update the emoMessageFeedback
        EmoMessageFeedback updatedEmoMessageFeedback = emoMessageFeedbackRepository.findById(emoMessageFeedback.getId()).get();
        // Disconnect from session so that the updates on updatedEmoMessageFeedback are not directly saved in db
        em.detach(updatedEmoMessageFeedback);
        updatedEmoMessageFeedback
            .emoSystemId(UPDATED_EMO_SYSTEM_ID)
            .centerId(UPDATED_CENTER_ID)
            .emoSystemServicesId(UPDATED_EMO_SYSTEM_SERVICES_ID)
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .feedback(UPDATED_FEEDBACK)
            .applicantName(UPDATED_APPLICANT_NAME);
        EmoMessageFeedbackDTO emoMessageFeedbackDTO = emoMessageFeedbackMapper.toDto(updatedEmoMessageFeedback);

        restEmoMessageFeedbackMockMvc
            .perform(
                put("/api/emo-message-feedbacks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoMessageFeedbackDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmoMessageFeedback in the database
        List<EmoMessageFeedback> emoMessageFeedbackList = emoMessageFeedbackRepository.findAll();
        assertThat(emoMessageFeedbackList).hasSize(databaseSizeBeforeUpdate);
        EmoMessageFeedback testEmoMessageFeedback = emoMessageFeedbackList.get(emoMessageFeedbackList.size() - 1);
        assertThat(testEmoMessageFeedback.getEmoSystemId()).isEqualTo(UPDATED_EMO_SYSTEM_ID);
        assertThat(testEmoMessageFeedback.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
        assertThat(testEmoMessageFeedback.getEmoSystemServicesId()).isEqualTo(UPDATED_EMO_SYSTEM_SERVICES_ID);
        assertThat(testEmoMessageFeedback.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testEmoMessageFeedback.getTrsId()).isEqualTo(UPDATED_TRS_ID);
        assertThat(testEmoMessageFeedback.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmoMessageFeedback.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEmoMessageFeedback.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmoMessageFeedback.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testEmoMessageFeedback.getApplicantName()).isEqualTo(UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmoMessageFeedback() throws Exception {
        int databaseSizeBeforeUpdate = emoMessageFeedbackRepository.findAll().size();

        // Create the EmoMessageFeedback
        EmoMessageFeedbackDTO emoMessageFeedbackDTO = emoMessageFeedbackMapper.toDto(emoMessageFeedback);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmoMessageFeedbackMockMvc
            .perform(
                put("/api/emo-message-feedbacks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emoMessageFeedbackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoMessageFeedback in the database
        List<EmoMessageFeedback> emoMessageFeedbackList = emoMessageFeedbackRepository.findAll();
        assertThat(emoMessageFeedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmoMessageFeedback() throws Exception {
        // Initialize the database
        emoMessageFeedbackRepository.saveAndFlush(emoMessageFeedback);

        int databaseSizeBeforeDelete = emoMessageFeedbackRepository.findAll().size();

        // Delete the emoMessageFeedback
        restEmoMessageFeedbackMockMvc
            .perform(delete("/api/emo-message-feedbacks/{id}", emoMessageFeedback.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmoMessageFeedback> emoMessageFeedbackList = emoMessageFeedbackRepository.findAll();
        assertThat(emoMessageFeedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
