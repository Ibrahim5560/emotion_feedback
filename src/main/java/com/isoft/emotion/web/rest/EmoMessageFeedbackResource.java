package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.EmoMessageFeedbackQueryService;
import com.isoft.emotion.service.EmoMessageFeedbackService;
import com.isoft.emotion.service.dto.EmoMessageFeedbackCriteria;
import com.isoft.emotion.service.dto.EmoMessageFeedbackDTO;
import com.isoft.emotion.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.isoft.emotion.domain.EmoMessageFeedback}.
 */
@RestController
@RequestMapping("/api")
public class EmoMessageFeedbackResource {
    private final Logger log = LoggerFactory.getLogger(EmoMessageFeedbackResource.class);

    private static final String ENTITY_NAME = "emoMessageFeedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmoMessageFeedbackService emoMessageFeedbackService;

    private final EmoMessageFeedbackQueryService emoMessageFeedbackQueryService;

    public EmoMessageFeedbackResource(
        EmoMessageFeedbackService emoMessageFeedbackService,
        EmoMessageFeedbackQueryService emoMessageFeedbackQueryService
    ) {
        this.emoMessageFeedbackService = emoMessageFeedbackService;
        this.emoMessageFeedbackQueryService = emoMessageFeedbackQueryService;
    }

    /**
     * {@code POST  /emo-message-feedbacks} : Create a new emoMessageFeedback.
     *
     * @param emoMessageFeedbackDTO the emoMessageFeedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emoMessageFeedbackDTO, or with status {@code 400 (Bad Request)} if the emoMessageFeedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emo-message-feedbacks")
    public ResponseEntity<EmoMessageFeedbackDTO> createEmoMessageFeedback(@RequestBody EmoMessageFeedbackDTO emoMessageFeedbackDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmoMessageFeedback : {}", emoMessageFeedbackDTO);
        if (emoMessageFeedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new emoMessageFeedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmoMessageFeedbackDTO result = emoMessageFeedbackService.save(emoMessageFeedbackDTO);
        return ResponseEntity
            .created(new URI("/api/emo-message-feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emo-message-feedbacks} : Updates an existing emoMessageFeedback.
     *
     * @param emoMessageFeedbackDTO the emoMessageFeedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emoMessageFeedbackDTO,
     * or with status {@code 400 (Bad Request)} if the emoMessageFeedbackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emoMessageFeedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emo-message-feedbacks")
    public ResponseEntity<EmoMessageFeedbackDTO> updateEmoMessageFeedback(@RequestBody EmoMessageFeedbackDTO emoMessageFeedbackDTO)
        throws URISyntaxException {
        log.debug("REST request to update EmoMessageFeedback : {}", emoMessageFeedbackDTO);
        if (emoMessageFeedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmoMessageFeedbackDTO result = emoMessageFeedbackService.save(emoMessageFeedbackDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emoMessageFeedbackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emo-message-feedbacks} : get all the emoMessageFeedbacks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emoMessageFeedbacks in body.
     */
    @GetMapping("/emo-message-feedbacks")
    public ResponseEntity<List<EmoMessageFeedbackDTO>> getAllEmoMessageFeedbacks(EmoMessageFeedbackCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmoMessageFeedbacks by criteria: {}", criteria);
        Page<EmoMessageFeedbackDTO> page = emoMessageFeedbackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emo-message-feedbacks/count} : count all the emoMessageFeedbacks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emo-message-feedbacks/count")
    public ResponseEntity<Long> countEmoMessageFeedbacks(EmoMessageFeedbackCriteria criteria) {
        log.debug("REST request to count EmoMessageFeedbacks by criteria: {}", criteria);
        return ResponseEntity.ok().body(emoMessageFeedbackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emo-message-feedbacks/:id} : get the "id" emoMessageFeedback.
     *
     * @param id the id of the emoMessageFeedbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emoMessageFeedbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emo-message-feedbacks/{id}")
    public ResponseEntity<EmoMessageFeedbackDTO> getEmoMessageFeedback(@PathVariable Long id) {
        log.debug("REST request to get EmoMessageFeedback : {}", id);
        Optional<EmoMessageFeedbackDTO> emoMessageFeedbackDTO = emoMessageFeedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emoMessageFeedbackDTO);
    }

    /**
     * {@code DELETE  /emo-message-feedbacks/:id} : delete the "id" emoMessageFeedback.
     *
     * @param id the id of the emoMessageFeedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emo-message-feedbacks/{id}")
    public ResponseEntity<Void> deleteEmoMessageFeedback(@PathVariable Long id) {
        log.debug("REST request to delete EmoMessageFeedback : {}", id);
        emoMessageFeedbackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
