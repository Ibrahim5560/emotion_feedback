package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.EmoSystemQueryService;
import com.isoft.emotion.service.EmoSystemService;
import com.isoft.emotion.service.dto.EmoSystemCriteria;
import com.isoft.emotion.service.dto.EmoSystemDTO;
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
 * REST controller for managing {@link com.isoft.emotion.domain.EmoSystem}.
 */
@RestController
@RequestMapping("/api")
public class EmoSystemResource {
    private final Logger log = LoggerFactory.getLogger(EmoSystemResource.class);

    private static final String ENTITY_NAME = "emoSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmoSystemService emoSystemService;

    private final EmoSystemQueryService emoSystemQueryService;

    public EmoSystemResource(EmoSystemService emoSystemService, EmoSystemQueryService emoSystemQueryService) {
        this.emoSystemService = emoSystemService;
        this.emoSystemQueryService = emoSystemQueryService;
    }

    /**
     * {@code POST  /emo-systems} : Create a new emoSystem.
     *
     * @param emoSystemDTO the emoSystemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emoSystemDTO, or with status {@code 400 (Bad Request)} if the emoSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emo-systems")
    public ResponseEntity<EmoSystemDTO> createEmoSystem(@RequestBody EmoSystemDTO emoSystemDTO) throws URISyntaxException {
        log.debug("REST request to save EmoSystem : {}", emoSystemDTO);
        if (emoSystemDTO.getId() != null) {
            throw new BadRequestAlertException("A new emoSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmoSystemDTO result = emoSystemService.save(emoSystemDTO);
        return ResponseEntity
            .created(new URI("/api/emo-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emo-systems} : Updates an existing emoSystem.
     *
     * @param emoSystemDTO the emoSystemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emoSystemDTO,
     * or with status {@code 400 (Bad Request)} if the emoSystemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emoSystemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emo-systems")
    public ResponseEntity<EmoSystemDTO> updateEmoSystem(@RequestBody EmoSystemDTO emoSystemDTO) throws URISyntaxException {
        log.debug("REST request to update EmoSystem : {}", emoSystemDTO);
        if (emoSystemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmoSystemDTO result = emoSystemService.save(emoSystemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emoSystemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emo-systems} : get all the emoSystems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emoSystems in body.
     */
    @GetMapping("/emo-systems")
    public ResponseEntity<List<EmoSystemDTO>> getAllEmoSystems(EmoSystemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmoSystems by criteria: {}", criteria);
        Page<EmoSystemDTO> page = emoSystemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emo-systems/count} : count all the emoSystems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emo-systems/count")
    public ResponseEntity<Long> countEmoSystems(EmoSystemCriteria criteria) {
        log.debug("REST request to count EmoSystems by criteria: {}", criteria);
        return ResponseEntity.ok().body(emoSystemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emo-systems/:id} : get the "id" emoSystem.
     *
     * @param id the id of the emoSystemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emoSystemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emo-systems/{id}")
    public ResponseEntity<EmoSystemDTO> getEmoSystem(@PathVariable Long id) {
        log.debug("REST request to get EmoSystem : {}", id);
        Optional<EmoSystemDTO> emoSystemDTO = emoSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emoSystemDTO);
    }

    /**
     * {@code DELETE  /emo-systems/:id} : delete the "id" emoSystem.
     *
     * @param id the id of the emoSystemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emo-systems/{id}")
    public ResponseEntity<Void> deleteEmoSystem(@PathVariable Long id) {
        log.debug("REST request to delete EmoSystem : {}", id);
        emoSystemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
