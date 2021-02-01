package com.isoft.emotion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.emotion.EmotionFeedbackApp;
import com.isoft.emotion.domain.EmoCenter;
import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.domain.EmoSystem;
import com.isoft.emotion.domain.EmoSystemServices;
import com.isoft.emotion.domain.EmoUsers;
import com.isoft.emotion.repository.EmoMessagesRepository;
import com.isoft.emotion.service.EmoMessagesQueryService;
import com.isoft.emotion.service.EmoMessagesService;
import com.isoft.emotion.service.dto.EmoMessagesCriteria;
import com.isoft.emotion.service.dto.EmoMessagesDTO;
import com.isoft.emotion.service.mapper.EmoMessagesMapper;
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
 * Integration tests for the {@link EmoMessagesResource} REST controller.
 */
@SpringBootTest(classes = EmotionFeedbackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmoMessagesResourceIT {
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

    private static final String DEFAULT_APPLICANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_NAME = "BBBBBBBBBB";

    @Autowired
    private EmoMessagesRepository emoMessagesRepository;

    @Autowired
    private EmoMessagesMapper emoMessagesMapper;

    @Autowired
    private EmoMessagesService emoMessagesService;

    @Autowired
    private EmoMessagesQueryService emoMessagesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmoMessagesMockMvc;

    private EmoMessages emoMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoMessages createEntity(EntityManager em) {
        EmoMessages emoMessages = new EmoMessages()
            .counter(DEFAULT_COUNTER)
            .trsId(DEFAULT_TRS_ID)
            .userId(DEFAULT_USER_ID)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .applicantName(DEFAULT_APPLICANT_NAME);
        return emoMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoMessages createUpdatedEntity(EntityManager em) {
        EmoMessages emoMessages = new EmoMessages()
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .applicantName(UPDATED_APPLICANT_NAME);
        return emoMessages;
    }

    @BeforeEach
    public void initTest() {
        emoMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmoMessages() throws Exception {
        int databaseSizeBeforeCreate = emoMessagesRepository.findAll().size();
        // Create the EmoMessages
        EmoMessagesDTO emoMessagesDTO = emoMessagesMapper.toDto(emoMessages);
        restEmoMessagesMockMvc
            .perform(
                post("/api/emo-messages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoMessagesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmoMessages in the database
        List<EmoMessages> emoMessagesList = emoMessagesRepository.findAll();
        assertThat(emoMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        EmoMessages testEmoMessages = emoMessagesList.get(emoMessagesList.size() - 1);
        assertThat(testEmoMessages.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testEmoMessages.getTrsId()).isEqualTo(DEFAULT_TRS_ID);
        assertThat(testEmoMessages.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmoMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEmoMessages.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmoMessages.getApplicantName()).isEqualTo(DEFAULT_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void createEmoMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emoMessagesRepository.findAll().size();

        // Create the EmoMessages with an existing ID
        emoMessages.setId(1L);
        EmoMessagesDTO emoMessagesDTO = emoMessagesMapper.toDto(emoMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmoMessagesMockMvc
            .perform(
                post("/api/emo-messages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoMessagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoMessages in the database
        List<EmoMessages> emoMessagesList = emoMessagesRepository.findAll();
        assertThat(emoMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmoMessages() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList
        restEmoMessagesMockMvc
            .perform(get("/api/emo-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));
    }

    @Test
    @Transactional
    public void getEmoMessages() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get the emoMessages
        restEmoMessagesMockMvc
            .perform(get("/api/emo-messages/{id}", emoMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emoMessages.getId().intValue()))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
            .andExpect(jsonPath("$.trsId").value(DEFAULT_TRS_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.applicantName").value(DEFAULT_APPLICANT_NAME));
    }

    @Test
    @Transactional
    public void getEmoMessagesByIdFiltering() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        Long id = emoMessages.getId();

        defaultEmoMessagesShouldBeFound("id.equals=" + id);
        defaultEmoMessagesShouldNotBeFound("id.notEquals=" + id);

        defaultEmoMessagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmoMessagesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmoMessagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmoMessagesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter equals to DEFAULT_COUNTER
        defaultEmoMessagesShouldBeFound("counter.equals=" + DEFAULT_COUNTER);

        // Get all the emoMessagesList where counter equals to UPDATED_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.equals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter not equals to DEFAULT_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.notEquals=" + DEFAULT_COUNTER);

        // Get all the emoMessagesList where counter not equals to UPDATED_COUNTER
        defaultEmoMessagesShouldBeFound("counter.notEquals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter in DEFAULT_COUNTER or UPDATED_COUNTER
        defaultEmoMessagesShouldBeFound("counter.in=" + DEFAULT_COUNTER + "," + UPDATED_COUNTER);

        // Get all the emoMessagesList where counter equals to UPDATED_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.in=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter is not null
        defaultEmoMessagesShouldBeFound("counter.specified=true");

        // Get all the emoMessagesList where counter is null
        defaultEmoMessagesShouldNotBeFound("counter.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter is greater than or equal to DEFAULT_COUNTER
        defaultEmoMessagesShouldBeFound("counter.greaterThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the emoMessagesList where counter is greater than or equal to UPDATED_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.greaterThanOrEqual=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter is less than or equal to DEFAULT_COUNTER
        defaultEmoMessagesShouldBeFound("counter.lessThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the emoMessagesList where counter is less than or equal to SMALLER_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.lessThanOrEqual=" + SMALLER_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter is less than DEFAULT_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.lessThan=" + DEFAULT_COUNTER);

        // Get all the emoMessagesList where counter is less than UPDATED_COUNTER
        defaultEmoMessagesShouldBeFound("counter.lessThan=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByCounterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where counter is greater than DEFAULT_COUNTER
        defaultEmoMessagesShouldNotBeFound("counter.greaterThan=" + DEFAULT_COUNTER);

        // Get all the emoMessagesList where counter is greater than SMALLER_COUNTER
        defaultEmoMessagesShouldBeFound("counter.greaterThan=" + SMALLER_COUNTER);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId equals to DEFAULT_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.equals=" + DEFAULT_TRS_ID);

        // Get all the emoMessagesList where trsId equals to UPDATED_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.equals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId not equals to DEFAULT_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.notEquals=" + DEFAULT_TRS_ID);

        // Get all the emoMessagesList where trsId not equals to UPDATED_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.notEquals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId in DEFAULT_TRS_ID or UPDATED_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.in=" + DEFAULT_TRS_ID + "," + UPDATED_TRS_ID);

        // Get all the emoMessagesList where trsId equals to UPDATED_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.in=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId is not null
        defaultEmoMessagesShouldBeFound("trsId.specified=true");

        // Get all the emoMessagesList where trsId is null
        defaultEmoMessagesShouldNotBeFound("trsId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId is greater than or equal to DEFAULT_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.greaterThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the emoMessagesList where trsId is greater than or equal to UPDATED_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.greaterThanOrEqual=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId is less than or equal to DEFAULT_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.lessThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the emoMessagesList where trsId is less than or equal to SMALLER_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.lessThanOrEqual=" + SMALLER_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId is less than DEFAULT_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.lessThan=" + DEFAULT_TRS_ID);

        // Get all the emoMessagesList where trsId is less than UPDATED_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.lessThan=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByTrsIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where trsId is greater than DEFAULT_TRS_ID
        defaultEmoMessagesShouldNotBeFound("trsId.greaterThan=" + DEFAULT_TRS_ID);

        // Get all the emoMessagesList where trsId is greater than SMALLER_TRS_ID
        defaultEmoMessagesShouldBeFound("trsId.greaterThan=" + SMALLER_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId equals to DEFAULT_USER_ID
        defaultEmoMessagesShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the emoMessagesList where userId equals to UPDATED_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId not equals to DEFAULT_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the emoMessagesList where userId not equals to UPDATED_USER_ID
        defaultEmoMessagesShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultEmoMessagesShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the emoMessagesList where userId equals to UPDATED_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId is not null
        defaultEmoMessagesShouldBeFound("userId.specified=true");

        // Get all the emoMessagesList where userId is null
        defaultEmoMessagesShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId is greater than or equal to DEFAULT_USER_ID
        defaultEmoMessagesShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the emoMessagesList where userId is greater than or equal to UPDATED_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId is less than or equal to DEFAULT_USER_ID
        defaultEmoMessagesShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the emoMessagesList where userId is less than or equal to SMALLER_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId is less than DEFAULT_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the emoMessagesList where userId is less than UPDATED_USER_ID
        defaultEmoMessagesShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where userId is greater than DEFAULT_USER_ID
        defaultEmoMessagesShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the emoMessagesList where userId is greater than SMALLER_USER_ID
        defaultEmoMessagesShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where message equals to DEFAULT_MESSAGE
        defaultEmoMessagesShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the emoMessagesList where message equals to UPDATED_MESSAGE
        defaultEmoMessagesShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where message not equals to DEFAULT_MESSAGE
        defaultEmoMessagesShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the emoMessagesList where message not equals to UPDATED_MESSAGE
        defaultEmoMessagesShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultEmoMessagesShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the emoMessagesList where message equals to UPDATED_MESSAGE
        defaultEmoMessagesShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where message is not null
        defaultEmoMessagesShouldBeFound("message.specified=true");

        // Get all the emoMessagesList where message is null
        defaultEmoMessagesShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByMessageContainsSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where message contains DEFAULT_MESSAGE
        defaultEmoMessagesShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the emoMessagesList where message contains UPDATED_MESSAGE
        defaultEmoMessagesShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where message does not contain DEFAULT_MESSAGE
        defaultEmoMessagesShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the emoMessagesList where message does not contain UPDATED_MESSAGE
        defaultEmoMessagesShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status equals to DEFAULT_STATUS
        defaultEmoMessagesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emoMessagesList where status equals to UPDATED_STATUS
        defaultEmoMessagesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status not equals to DEFAULT_STATUS
        defaultEmoMessagesShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the emoMessagesList where status not equals to UPDATED_STATUS
        defaultEmoMessagesShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmoMessagesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emoMessagesList where status equals to UPDATED_STATUS
        defaultEmoMessagesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status is not null
        defaultEmoMessagesShouldBeFound("status.specified=true");

        // Get all the emoMessagesList where status is null
        defaultEmoMessagesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status is greater than or equal to DEFAULT_STATUS
        defaultEmoMessagesShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoMessagesList where status is greater than or equal to UPDATED_STATUS
        defaultEmoMessagesShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status is less than or equal to DEFAULT_STATUS
        defaultEmoMessagesShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoMessagesList where status is less than or equal to SMALLER_STATUS
        defaultEmoMessagesShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status is less than DEFAULT_STATUS
        defaultEmoMessagesShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the emoMessagesList where status is less than UPDATED_STATUS
        defaultEmoMessagesShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where status is greater than DEFAULT_STATUS
        defaultEmoMessagesShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the emoMessagesList where status is greater than SMALLER_STATUS
        defaultEmoMessagesShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByApplicantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where applicantName equals to DEFAULT_APPLICANT_NAME
        defaultEmoMessagesShouldBeFound("applicantName.equals=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessagesList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultEmoMessagesShouldNotBeFound("applicantName.equals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByApplicantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where applicantName not equals to DEFAULT_APPLICANT_NAME
        defaultEmoMessagesShouldNotBeFound("applicantName.notEquals=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessagesList where applicantName not equals to UPDATED_APPLICANT_NAME
        defaultEmoMessagesShouldBeFound("applicantName.notEquals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByApplicantNameIsInShouldWork() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where applicantName in DEFAULT_APPLICANT_NAME or UPDATED_APPLICANT_NAME
        defaultEmoMessagesShouldBeFound("applicantName.in=" + DEFAULT_APPLICANT_NAME + "," + UPDATED_APPLICANT_NAME);

        // Get all the emoMessagesList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultEmoMessagesShouldNotBeFound("applicantName.in=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByApplicantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where applicantName is not null
        defaultEmoMessagesShouldBeFound("applicantName.specified=true");

        // Get all the emoMessagesList where applicantName is null
        defaultEmoMessagesShouldNotBeFound("applicantName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByApplicantNameContainsSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where applicantName contains DEFAULT_APPLICANT_NAME
        defaultEmoMessagesShouldBeFound("applicantName.contains=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessagesList where applicantName contains UPDATED_APPLICANT_NAME
        defaultEmoMessagesShouldNotBeFound("applicantName.contains=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByApplicantNameNotContainsSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        // Get all the emoMessagesList where applicantName does not contain DEFAULT_APPLICANT_NAME
        defaultEmoMessagesShouldNotBeFound("applicantName.doesNotContain=" + DEFAULT_APPLICANT_NAME);

        // Get all the emoMessagesList where applicantName does not contain UPDATED_APPLICANT_NAME
        defaultEmoMessagesShouldBeFound("applicantName.doesNotContain=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByEmoCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);
        EmoCenter emoCenter = EmoCenterResourceIT.createEntity(em);
        em.persist(emoCenter);
        em.flush();
        emoMessages.setEmoCenter(emoCenter);
        emoMessagesRepository.saveAndFlush(emoMessages);
        Long emoCenterId = emoCenter.getId();

        // Get all the emoMessagesList where emoCenter equals to emoCenterId
        defaultEmoMessagesShouldBeFound("emoCenterId.equals=" + emoCenterId);

        // Get all the emoMessagesList where emoCenter equals to emoCenterId + 1
        defaultEmoMessagesShouldNotBeFound("emoCenterId.equals=" + (emoCenterId + 1));
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByEmoSystemIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);
        EmoSystem emoSystem = EmoSystemResourceIT.createEntity(em);
        em.persist(emoSystem);
        em.flush();
        emoMessages.setEmoSystem(emoSystem);
        emoMessagesRepository.saveAndFlush(emoMessages);
        Long emoSystemId = emoSystem.getId();

        // Get all the emoMessagesList where emoSystem equals to emoSystemId
        defaultEmoMessagesShouldBeFound("emoSystemId.equals=" + emoSystemId);

        // Get all the emoMessagesList where emoSystem equals to emoSystemId + 1
        defaultEmoMessagesShouldNotBeFound("emoSystemId.equals=" + (emoSystemId + 1));
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByEmoSystemServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);
        EmoSystemServices emoSystemServices = EmoSystemServicesResourceIT.createEntity(em);
        em.persist(emoSystemServices);
        em.flush();
        emoMessages.setEmoSystemServices(emoSystemServices);
        emoMessagesRepository.saveAndFlush(emoMessages);
        Long emoSystemServicesId = emoSystemServices.getId();

        // Get all the emoMessagesList where emoSystemServices equals to emoSystemServicesId
        defaultEmoMessagesShouldBeFound("emoSystemServicesId.equals=" + emoSystemServicesId);

        // Get all the emoMessagesList where emoSystemServices equals to emoSystemServicesId + 1
        defaultEmoMessagesShouldNotBeFound("emoSystemServicesId.equals=" + (emoSystemServicesId + 1));
    }

    @Test
    @Transactional
    public void getAllEmoMessagesByEmoUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);
        EmoUsers emoUsers = EmoUsersResourceIT.createEntity(em);
        em.persist(emoUsers);
        em.flush();
        emoMessages.setEmoUsers(emoUsers);
        emoMessagesRepository.saveAndFlush(emoMessages);
        Long emoUsersId = emoUsers.getId();

        // Get all the emoMessagesList where emoUsers equals to emoUsersId
        defaultEmoMessagesShouldBeFound("emoUsersId.equals=" + emoUsersId);

        // Get all the emoMessagesList where emoUsers equals to emoUsersId + 1
        defaultEmoMessagesShouldNotBeFound("emoUsersId.equals=" + (emoUsersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmoMessagesShouldBeFound(String filter) throws Exception {
        restEmoMessagesMockMvc
            .perform(get("/api/emo-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));

        // Check, that the count call also returns 1
        restEmoMessagesMockMvc
            .perform(get("/api/emo-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmoMessagesShouldNotBeFound(String filter) throws Exception {
        restEmoMessagesMockMvc
            .perform(get("/api/emo-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmoMessagesMockMvc
            .perform(get("/api/emo-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmoMessages() throws Exception {
        // Get the emoMessages
        restEmoMessagesMockMvc.perform(get("/api/emo-messages/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmoMessages() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        int databaseSizeBeforeUpdate = emoMessagesRepository.findAll().size();

        // Update the emoMessages
        EmoMessages updatedEmoMessages = emoMessagesRepository.findById(emoMessages.getId()).get();
        // Disconnect from session so that the updates on updatedEmoMessages are not directly saved in db
        em.detach(updatedEmoMessages);
        updatedEmoMessages
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .applicantName(UPDATED_APPLICANT_NAME);
        EmoMessagesDTO emoMessagesDTO = emoMessagesMapper.toDto(updatedEmoMessages);

        restEmoMessagesMockMvc
            .perform(
                put("/api/emo-messages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoMessagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmoMessages in the database
        List<EmoMessages> emoMessagesList = emoMessagesRepository.findAll();
        assertThat(emoMessagesList).hasSize(databaseSizeBeforeUpdate);
        EmoMessages testEmoMessages = emoMessagesList.get(emoMessagesList.size() - 1);
        assertThat(testEmoMessages.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testEmoMessages.getTrsId()).isEqualTo(UPDATED_TRS_ID);
        assertThat(testEmoMessages.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmoMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEmoMessages.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmoMessages.getApplicantName()).isEqualTo(UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmoMessages() throws Exception {
        int databaseSizeBeforeUpdate = emoMessagesRepository.findAll().size();

        // Create the EmoMessages
        EmoMessagesDTO emoMessagesDTO = emoMessagesMapper.toDto(emoMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmoMessagesMockMvc
            .perform(
                put("/api/emo-messages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoMessagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmoMessages in the database
        List<EmoMessages> emoMessagesList = emoMessagesRepository.findAll();
        assertThat(emoMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmoMessages() throws Exception {
        // Initialize the database
        emoMessagesRepository.saveAndFlush(emoMessages);

        int databaseSizeBeforeDelete = emoMessagesRepository.findAll().size();

        // Delete the emoMessages
        restEmoMessagesMockMvc
            .perform(delete("/api/emo-messages/{id}", emoMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmoMessages> emoMessagesList = emoMessagesRepository.findAll();
        assertThat(emoMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
