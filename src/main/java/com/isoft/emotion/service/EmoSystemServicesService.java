package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.EmoSystemServicesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.EmoSystemServices}.
 */
public interface EmoSystemServicesService {
    /**
     * Save a emoSystemServices.
     *
     * @param emoSystemServicesDTO the entity to save.
     * @return the persisted entity.
     */
    EmoSystemServicesDTO save(EmoSystemServicesDTO emoSystemServicesDTO);

    /**
     * Get all the emoSystemServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmoSystemServicesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emoSystemServices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmoSystemServicesDTO> findOne(Long id);

    /**
     * Delete the "id" emoSystemServices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
