package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.EmoUsersQueryService;
import com.isoft.emotion.service.EmoUsersService;
import com.isoft.emotion.service.dto.EmoUsersCriteria;
import com.isoft.emotion.service.dto.EmoUsersDTO;
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
 * REST controller for managing {@link com.isoft.emotion.domain.EmoUsers}.
 */
@RestController
@RequestMapping("/api")
public class EmoUsersResource {
    private final Logger log = LoggerFactory.getLogger(EmoUsersResource.class);

    private static final String ENTITY_NAME = "emoUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmoUsersService emoUsersService;

    private final EmoUsersQueryService emoUsersQueryService;

    public EmoUsersResource(EmoUsersService emoUsersService, EmoUsersQueryService emoUsersQueryService) {
        this.emoUsersService = emoUsersService;
        this.emoUsersQueryService = emoUsersQueryService;
    }

    /**
     * {@code POST  /emo-users} : Create a new emoUsers.
     *
     * @param emoUsersDTO the emoUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emoUsersDTO, or with status {@code 400 (Bad Request)} if the emoUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emo-users")
    public ResponseEntity<EmoUsersDTO> createEmoUsers(@RequestBody EmoUsersDTO emoUsersDTO) throws URISyntaxException {
        log.debug("REST request to save EmoUsers : {}", emoUsersDTO);
        if (emoUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new emoUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmoUsersDTO result = emoUsersService.save(emoUsersDTO);
        return ResponseEntity
            .created(new URI("/api/emo-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emo-users} : Updates an existing emoUsers.
     *
     * @param emoUsersDTO the emoUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emoUsersDTO,
     * or with status {@code 400 (Bad Request)} if the emoUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emoUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emo-users")
    public ResponseEntity<EmoUsersDTO> updateEmoUsers(@RequestBody EmoUsersDTO emoUsersDTO) throws URISyntaxException {
        log.debug("REST request to update EmoUsers : {}", emoUsersDTO);
        if (emoUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmoUsersDTO result = emoUsersService.save(emoUsersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emoUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emo-users} : get all the emoUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emoUsers in body.
     */
    @GetMapping("/emo-users")
    public ResponseEntity<List<EmoUsersDTO>> getAllEmoUsers(EmoUsersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmoUsers by criteria: {}", criteria);
        Page<EmoUsersDTO> page = emoUsersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emo-users/count} : count all the emoUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emo-users/count")
    public ResponseEntity<Long> countEmoUsers(EmoUsersCriteria criteria) {
        log.debug("REST request to count EmoUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(emoUsersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emo-users/:id} : get the "id" emoUsers.
     *
     * @param id the id of the emoUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emoUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emo-users/{id}")
    public ResponseEntity<EmoUsersDTO> getEmoUsers(@PathVariable Long id) {
        log.debug("REST request to get EmoUsers : {}", id);
        Optional<EmoUsersDTO> emoUsersDTO = emoUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emoUsersDTO);
    }

    /**
     * {@code DELETE  /emo-users/:id} : delete the "id" emoUsers.
     *
     * @param id the id of the emoUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emo-users/{id}")
    public ResponseEntity<Void> deleteEmoUsers(@PathVariable Long id) {
        log.debug("REST request to delete EmoUsers : {}", id);
        emoUsersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
