package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.EmoCenterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.EmoCenter}.
 */
public interface EmoCenterService {
    /**
     * Save a emoCenter.
     *
     * @param emoCenterDTO the entity to save.
     * @return the persisted entity.
     */
    EmoCenterDTO save(EmoCenterDTO emoCenterDTO);

    /**
     * Get all the emoCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmoCenterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emoCenter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmoCenterDTO> findOne(Long id);

    /**
     * Delete the "id" emoCenter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
