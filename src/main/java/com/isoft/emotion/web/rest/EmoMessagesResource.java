package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.EmoMessagesQueryService;
import com.isoft.emotion.service.EmoMessagesService;
import com.isoft.emotion.service.dto.EmoMessagesCriteria;
import com.isoft.emotion.service.dto.EmoMessagesDTO;
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
 * REST controller for managing {@link com.isoft.emotion.domain.EmoMessages}.
 */
@RestController
@RequestMapping("/api")
public class EmoMessagesResource {
    private final Logger log = LoggerFactory.getLogger(EmoMessagesResource.class);

    private static final String ENTITY_NAME = "emoMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmoMessagesService emoMessagesService;

    private final EmoMessagesQueryService emoMessagesQueryService;

    public EmoMessagesResource(EmoMessagesService emoMessagesService, EmoMessagesQueryService emoMessagesQueryService) {
        this.emoMessagesService = emoMessagesService;
        this.emoMessagesQueryService = emoMessagesQueryService;
    }

    /**
     * {@code POST  /emo-messages} : Create a new emoMessages.
     *
     * @param emoMessagesDTO the emoMessagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emoMessagesDTO, or with status {@code 400 (Bad Request)} if the emoMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emo-messages")
    public ResponseEntity<EmoMessagesDTO> createEmoMessages(@RequestBody EmoMessagesDTO emoMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save EmoMessages : {}", emoMessagesDTO);
        if (emoMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new emoMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmoMessagesDTO result = emoMessagesService.save(emoMessagesDTO);
        return ResponseEntity
            .created(new URI("/api/emo-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emo-messages} : Updates an existing emoMessages.
     *
     * @param emoMessagesDTO the emoMessagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emoMessagesDTO,
     * or with status {@code 400 (Bad Request)} if the emoMessagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emoMessagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emo-messages")
    public ResponseEntity<EmoMessagesDTO> updateEmoMessages(@RequestBody EmoMessagesDTO emoMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update EmoMessages : {}", emoMessagesDTO);
        if (emoMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmoMessagesDTO result = emoMessagesService.save(emoMessagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emoMessagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emo-messages} : get all the emoMessages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emoMessages in body.
     */
    @GetMapping("/emo-messages")
    public ResponseEntity<List<EmoMessagesDTO>> getAllEmoMessages(EmoMessagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmoMessages by criteria: {}", criteria);
        Page<EmoMessagesDTO> page = emoMessagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emo-messages/count} : count all the emoMessages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emo-messages/count")
    public ResponseEntity<Long> countEmoMessages(EmoMessagesCriteria criteria) {
        log.debug("REST request to count EmoMessages by criteria: {}", criteria);
        return ResponseEntity.ok().body(emoMessagesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emo-messages/:id} : get the "id" emoMessages.
     *
     * @param id the id of the emoMessagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emoMessagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emo-messages/{id}")
    public ResponseEntity<EmoMessagesDTO> getEmoMessages(@PathVariable Long id) {
        log.debug("REST request to get EmoMessages : {}", id);
        Optional<EmoMessagesDTO> emoMessagesDTO = emoMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emoMessagesDTO);
    }

    /**
     * {@code DELETE  /emo-messages/:id} : delete the "id" emoMessages.
     *
     * @param id the id of the emoMessagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emo-messages/{id}")
    public ResponseEntity<Void> deleteEmoMessages(@PathVariable Long id) {
        log.debug("REST request to delete EmoMessages : {}", id);
        emoMessagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
