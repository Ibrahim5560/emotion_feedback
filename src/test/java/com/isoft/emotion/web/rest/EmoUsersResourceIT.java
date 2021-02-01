package com.isoft.emotion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.emotion.EmotionFeedbackApp;
import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.domain.EmoUsers;
import com.isoft.emotion.repository.EmoUsersRepository;
import com.isoft.emotion.service.EmoUsersQueryService;
import com.isoft.emotion.service.EmoUsersService;
import com.isoft.emotion.service.dto.EmoUsersCriteria;
import com.isoft.emotion.service.dto.EmoUsersDTO;
import com.isoft.emotion.service.mapper.EmoUsersMapper;
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
 * Integration tests for the {@link EmoUsersResource} REST controller.
 */
@SpringBootTest(classes = EmotionFeedbackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmoUsersResourceIT {
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
    private EmoUsersRepository emoUsersRepository;

    @Autowired
    private EmoUsersMapper emoUsersMapper;

    @Autowired
    private EmoUsersService emoUsersService;

    @Autowired
    private EmoUsersQueryService emoUsersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmoUsersMockMvc;

    private EmoUsers emoUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoUsers createEntity(EntityManager em) {
        EmoUsers emoUsers = new EmoUsers().nameAr(DEFAULT_NAME_AR).nameEn(DEFAULT_NAME_EN).code(DEFAULT_CODE).status(DEFAULT_STATUS);
        return emoUsers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmoUsers createUpdatedEntity(EntityManager em) {
        EmoUsers emoUsers = new EmoUsers().nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        return emoUsers;
    }

    @BeforeEach
    public void initTest() {
        emoUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmoUsers() throws Exception {
        int databaseSizeBeforeCreate = emoUsersRepository.findAll().size();
        // Create the EmoUsers
        EmoUsersDTO emoUsersDTO = emoUsersMapper.toDto(emoUsers);
        restEmoUsersMockMvc
            .perform(post("/api/emo-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the EmoUsers in the database
        List<EmoUsers> emoUsersList = emoUsersRepository.findAll();
        assertThat(emoUsersList).hasSize(databaseSizeBeforeCreate + 1);
        EmoUsers testEmoUsers = emoUsersList.get(emoUsersList.size() - 1);
        assertThat(testEmoUsers.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testEmoUsers.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testEmoUsers.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmoUsers.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEmoUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emoUsersRepository.findAll().size();

        // Create the EmoUsers with an existing ID
        emoUsers.setId(1L);
        EmoUsersDTO emoUsersDTO = emoUsersMapper.toDto(emoUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmoUsersMockMvc
            .perform(post("/api/emo-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmoUsers in the database
        List<EmoUsers> emoUsersList = emoUsersRepository.findAll();
        assertThat(emoUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmoUsers() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList
        restEmoUsersMockMvc
            .perform(get("/api/emo-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getEmoUsers() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get the emoUsers
        restEmoUsersMockMvc
            .perform(get("/api/emo-users/{id}", emoUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emoUsers.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getEmoUsersByIdFiltering() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        Long id = emoUsers.getId();

        defaultEmoUsersShouldBeFound("id.equals=" + id);
        defaultEmoUsersShouldNotBeFound("id.notEquals=" + id);

        defaultEmoUsersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmoUsersShouldNotBeFound("id.greaterThan=" + id);

        defaultEmoUsersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmoUsersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameAr equals to DEFAULT_NAME_AR
        defaultEmoUsersShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the emoUsersList where nameAr equals to UPDATED_NAME_AR
        defaultEmoUsersShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameAr not equals to DEFAULT_NAME_AR
        defaultEmoUsersShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the emoUsersList where nameAr not equals to UPDATED_NAME_AR
        defaultEmoUsersShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultEmoUsersShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the emoUsersList where nameAr equals to UPDATED_NAME_AR
        defaultEmoUsersShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameAr is not null
        defaultEmoUsersShouldBeFound("nameAr.specified=true");

        // Get all the emoUsersList where nameAr is null
        defaultEmoUsersShouldNotBeFound("nameAr.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameArContainsSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameAr contains DEFAULT_NAME_AR
        defaultEmoUsersShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the emoUsersList where nameAr contains UPDATED_NAME_AR
        defaultEmoUsersShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameAr does not contain DEFAULT_NAME_AR
        defaultEmoUsersShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the emoUsersList where nameAr does not contain UPDATED_NAME_AR
        defaultEmoUsersShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameEn equals to DEFAULT_NAME_EN
        defaultEmoUsersShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the emoUsersList where nameEn equals to UPDATED_NAME_EN
        defaultEmoUsersShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameEn not equals to DEFAULT_NAME_EN
        defaultEmoUsersShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the emoUsersList where nameEn not equals to UPDATED_NAME_EN
        defaultEmoUsersShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultEmoUsersShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the emoUsersList where nameEn equals to UPDATED_NAME_EN
        defaultEmoUsersShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameEn is not null
        defaultEmoUsersShouldBeFound("nameEn.specified=true");

        // Get all the emoUsersList where nameEn is null
        defaultEmoUsersShouldNotBeFound("nameEn.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameEnContainsSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameEn contains DEFAULT_NAME_EN
        defaultEmoUsersShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the emoUsersList where nameEn contains UPDATED_NAME_EN
        defaultEmoUsersShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where nameEn does not contain DEFAULT_NAME_EN
        defaultEmoUsersShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the emoUsersList where nameEn does not contain UPDATED_NAME_EN
        defaultEmoUsersShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where code equals to DEFAULT_CODE
        defaultEmoUsersShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the emoUsersList where code equals to UPDATED_CODE
        defaultEmoUsersShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where code not equals to DEFAULT_CODE
        defaultEmoUsersShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the emoUsersList where code not equals to UPDATED_CODE
        defaultEmoUsersShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEmoUsersShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the emoUsersList where code equals to UPDATED_CODE
        defaultEmoUsersShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where code is not null
        defaultEmoUsersShouldBeFound("code.specified=true");

        // Get all the emoUsersList where code is null
        defaultEmoUsersShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoUsersByCodeContainsSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where code contains DEFAULT_CODE
        defaultEmoUsersShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the emoUsersList where code contains UPDATED_CODE
        defaultEmoUsersShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where code does not contain DEFAULT_CODE
        defaultEmoUsersShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the emoUsersList where code does not contain UPDATED_CODE
        defaultEmoUsersShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status equals to DEFAULT_STATUS
        defaultEmoUsersShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emoUsersList where status equals to UPDATED_STATUS
        defaultEmoUsersShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status not equals to DEFAULT_STATUS
        defaultEmoUsersShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the emoUsersList where status not equals to UPDATED_STATUS
        defaultEmoUsersShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmoUsersShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emoUsersList where status equals to UPDATED_STATUS
        defaultEmoUsersShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status is not null
        defaultEmoUsersShouldBeFound("status.specified=true");

        // Get all the emoUsersList where status is null
        defaultEmoUsersShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status is greater than or equal to DEFAULT_STATUS
        defaultEmoUsersShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoUsersList where status is greater than or equal to UPDATED_STATUS
        defaultEmoUsersShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status is less than or equal to DEFAULT_STATUS
        defaultEmoUsersShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the emoUsersList where status is less than or equal to SMALLER_STATUS
        defaultEmoUsersShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status is less than DEFAULT_STATUS
        defaultEmoUsersShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the emoUsersList where status is less than UPDATED_STATUS
        defaultEmoUsersShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        // Get all the emoUsersList where status is greater than DEFAULT_STATUS
        defaultEmoUsersShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the emoUsersList where status is greater than SMALLER_STATUS
        defaultEmoUsersShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmoUsersByEmoUsersMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);
        EmoMessages emoUsersMessages = EmoMessagesResourceIT.createEntity(em);
        em.persist(emoUsersMessages);
        em.flush();
        emoUsers.addEmoUsersMessages(emoUsersMessages);
        emoUsersRepository.saveAndFlush(emoUsers);
        Long emoUsersMessagesId = emoUsersMessages.getId();

        // Get all the emoUsersList where emoUsersMessages equals to emoUsersMessagesId
        defaultEmoUsersShouldBeFound("emoUsersMessagesId.equals=" + emoUsersMessagesId);

        // Get all the emoUsersList where emoUsersMessages equals to emoUsersMessagesId + 1
        defaultEmoUsersShouldNotBeFound("emoUsersMessagesId.equals=" + (emoUsersMessagesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmoUsersShouldBeFound(String filter) throws Exception {
        restEmoUsersMockMvc
            .perform(get("/api/emo-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emoUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restEmoUsersMockMvc
            .perform(get("/api/emo-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmoUsersShouldNotBeFound(String filter) throws Exception {
        restEmoUsersMockMvc
            .perform(get("/api/emo-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmoUsersMockMvc
            .perform(get("/api/emo-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmoUsers() throws Exception {
        // Get the emoUsers
        restEmoUsersMockMvc.perform(get("/api/emo-users/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmoUsers() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        int databaseSizeBeforeUpdate = emoUsersRepository.findAll().size();

        // Update the emoUsers
        EmoUsers updatedEmoUsers = emoUsersRepository.findById(emoUsers.getId()).get();
        // Disconnect from session so that the updates on updatedEmoUsers are not directly saved in db
        em.detach(updatedEmoUsers);
        updatedEmoUsers.nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        EmoUsersDTO emoUsersDTO = emoUsersMapper.toDto(updatedEmoUsers);

        restEmoUsersMockMvc
            .perform(put("/api/emo-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoUsersDTO)))
            .andExpect(status().isOk());

        // Validate the EmoUsers in the database
        List<EmoUsers> emoUsersList = emoUsersRepository.findAll();
        assertThat(emoUsersList).hasSize(databaseSizeBeforeUpdate);
        EmoUsers testEmoUsers = emoUsersList.get(emoUsersList.size() - 1);
        assertThat(testEmoUsers.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testEmoUsers.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testEmoUsers.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmoUsers.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmoUsers() throws Exception {
        int databaseSizeBeforeUpdate = emoUsersRepository.findAll().size();

        // Create the EmoUsers
        EmoUsersDTO emoUsersDTO = emoUsersMapper.toDto(emoUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmoUsersMockMvc
            .perform(put("/api/emo-users").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emoUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmoUsers in the database
        List<EmoUsers> emoUsersList = emoUsersRepository.findAll();
        assertThat(emoUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmoUsers() throws Exception {
        // Initialize the database
        emoUsersRepository.saveAndFlush(emoUsers);

        int databaseSizeBeforeDelete = emoUsersRepository.findAll().size();

        // Delete the emoUsers
        restEmoUsersMockMvc
            .perform(delete("/api/emo-users/{id}", emoUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmoUsers> emoUsersList = emoUsersRepository.findAll();
        assertThat(emoUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
