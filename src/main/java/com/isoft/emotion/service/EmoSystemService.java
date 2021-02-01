package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.EmoSystemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.EmoSystem}.
 */
public interface EmoSystemService {
    /**
     * Save a emoSystem.
     *
     * @param emoSystemDTO the entity to save.
     * @return the persisted entity.
     */
    EmoSystemDTO save(EmoSystemDTO emoSystemDTO);

    /**
     * Get all the emoSystems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmoSystemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emoSystem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmoSystemDTO> findOne(Long id);

    /**
     * Delete the "id" emoSystem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
