package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.EmoSystemServicesQueryService;
import com.isoft.emotion.service.EmoSystemServicesService;
import com.isoft.emotion.service.dto.EmoSystemServicesCriteria;
import com.isoft.emotion.service.dto.EmoSystemServicesDTO;
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
 * REST controller for managing {@link com.isoft.emotion.domain.EmoSystemServices}.
 */
@RestController
@RequestMapping("/api")
public class EmoSystemServicesResource {
    private final Logger log = LoggerFactory.getLogger(EmoSystemServicesResource.class);

    private static final String ENTITY_NAME = "emoSystemServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmoSystemServicesService emoSystemServicesService;

    private final EmoSystemServicesQueryService emoSystemServicesQueryService;

    public EmoSystemServicesResource(
        EmoSystemServicesService emoSystemServicesService,
        EmoSystemServicesQueryService emoSystemServicesQueryService
    ) {
        this.emoSystemServicesService = emoSystemServicesService;
        this.emoSystemServicesQueryService = emoSystemServicesQueryService;
    }

    /**
     * {@code POST  /emo-system-services} : Create a new emoSystemServices.
     *
     * @param emoSystemServicesDTO the emoSystemServicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emoSystemServicesDTO, or with status {@code 400 (Bad Request)} if the emoSystemServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emo-system-services")
    public ResponseEntity<EmoSystemServicesDTO> createEmoSystemServices(@RequestBody EmoSystemServicesDTO emoSystemServicesDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmoSystemServices : {}", emoSystemServicesDTO);
        if (emoSystemServicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new emoSystemServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmoSystemServicesDTO result = emoSystemServicesService.save(emoSystemServicesDTO);
        return ResponseEntity
            .created(new URI("/api/emo-system-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emo-system-services} : Updates an existing emoSystemServices.
     *
     * @param emoSystemServicesDTO the emoSystemServicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emoSystemServicesDTO,
     * or with status {@code 400 (Bad Request)} if the emoSystemServicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emoSystemServicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emo-system-services")
    public ResponseEntity<EmoSystemServicesDTO> updateEmoSystemServices(@RequestBody EmoSystemServicesDTO emoSystemServicesDTO)
        throws URISyntaxException {
        log.debug("REST request to update EmoSystemServices : {}", emoSystemServicesDTO);
        if (emoSystemServicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmoSystemServicesDTO result = emoSystemServicesService.save(emoSystemServicesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emoSystemServicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emo-system-services} : get all the emoSystemServices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emoSystemServices in body.
     */
    @GetMapping("/emo-system-services")
    public ResponseEntity<List<EmoSystemServicesDTO>> getAllEmoSystemServices(EmoSystemServicesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmoSystemServices by criteria: {}", criteria);
        Page<EmoSystemServicesDTO> page = emoSystemServicesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emo-system-services/count} : count all the emoSystemServices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emo-system-services/count")
    public ResponseEntity<Long> countEmoSystemServices(EmoSystemServicesCriteria criteria) {
        log.debug("REST request to count EmoSystemServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(emoSystemServicesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emo-system-services/:id} : get the "id" emoSystemServices.
     *
     * @param id the id of the emoSystemServicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emoSystemServicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emo-system-services/{id}")
    public ResponseEntity<EmoSystemServicesDTO> getEmoSystemServices(@PathVariable Long id) {
        log.debug("REST request to get EmoSystemServices : {}", id);
        Optional<EmoSystemServicesDTO> emoSystemServicesDTO = emoSystemServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emoSystemServicesDTO);
    }

    /**
     * {@code DELETE  /emo-system-services/:id} : delete the "id" emoSystemServices.
     *
     * @param id the id of the emoSystemServicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emo-system-services/{id}")
    public ResponseEntity<Void> deleteEmoSystemServices(@PathVariable Long id) {
        log.debug("REST request to delete EmoSystemServices : {}", id);
        emoSystemServicesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
