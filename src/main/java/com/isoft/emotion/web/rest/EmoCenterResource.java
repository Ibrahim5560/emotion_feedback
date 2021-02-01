package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.EmoCenterQueryService;
import com.isoft.emotion.service.EmoCenterService;
import com.isoft.emotion.service.dto.EmoCenterCriteria;
import com.isoft.emotion.service.dto.EmoCenterDTO;
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
 * REST controller for managing {@link com.isoft.emotion.domain.EmoCenter}.
 */
@RestController
@RequestMapping("/api")
public class EmoCenterResource {
    private final Logger log = LoggerFactory.getLogger(EmoCenterResource.class);

    private static final String ENTITY_NAME = "emoCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmoCenterService emoCenterService;

    private final EmoCenterQueryService emoCenterQueryService;

    public EmoCenterResource(EmoCenterService emoCenterService, EmoCenterQueryService emoCenterQueryService) {
        this.emoCenterService = emoCenterService;
        this.emoCenterQueryService = emoCenterQueryService;
    }

    /**
     * {@code POST  /emo-centers} : Create a new emoCenter.
     *
     * @param emoCenterDTO the emoCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emoCenterDTO, or with status {@code 400 (Bad Request)} if the emoCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emo-centers")
    public ResponseEntity<EmoCenterDTO> createEmoCenter(@RequestBody EmoCenterDTO emoCenterDTO) throws URISyntaxException {
        log.debug("REST request to save EmoCenter : {}", emoCenterDTO);
        if (emoCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new emoCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmoCenterDTO result = emoCenterService.save(emoCenterDTO);
        return ResponseEntity
            .created(new URI("/api/emo-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emo-centers} : Updates an existing emoCenter.
     *
     * @param emoCenterDTO the emoCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emoCenterDTO,
     * or with status {@code 400 (Bad Request)} if the emoCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emoCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emo-centers")
    public ResponseEntity<EmoCenterDTO> updateEmoCenter(@RequestBody EmoCenterDTO emoCenterDTO) throws URISyntaxException {
        log.debug("REST request to update EmoCenter : {}", emoCenterDTO);
        if (emoCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmoCenterDTO result = emoCenterService.save(emoCenterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emoCenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emo-centers} : get all the emoCenters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emoCenters in body.
     */
    @GetMapping("/emo-centers")
    public ResponseEntity<List<EmoCenterDTO>> getAllEmoCenters(EmoCenterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmoCenters by criteria: {}", criteria);
        Page<EmoCenterDTO> page = emoCenterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emo-centers/count} : count all the emoCenters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emo-centers/count")
    public ResponseEntity<Long> countEmoCenters(EmoCenterCriteria criteria) {
        log.debug("REST request to count EmoCenters by criteria: {}", criteria);
        return ResponseEntity.ok().body(emoCenterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emo-centers/:id} : get the "id" emoCenter.
     *
     * @param id the id of the emoCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emoCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emo-centers/{id}")
    public ResponseEntity<EmoCenterDTO> getEmoCenter(@PathVariable Long id) {
        log.debug("REST request to get EmoCenter : {}", id);
        Optional<EmoCenterDTO> emoCenterDTO = emoCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emoCenterDTO);
    }

    /**
     * {@code DELETE  /emo-centers/:id} : delete the "id" emoCenter.
     *
     * @param id the id of the emoCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emo-centers/{id}")
    public ResponseEntity<Void> deleteEmoCenter(@PathVariable Long id) {
        log.debug("REST request to delete EmoCenter : {}", id);
        emoCenterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
