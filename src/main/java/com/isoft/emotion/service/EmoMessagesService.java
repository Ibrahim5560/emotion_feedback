package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.EmoMessagesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.EmoMessages}.
 */
public interface EmoMessagesService {
    /**
     * Save a emoMessages.
     *
     * @param emoMessagesDTO the entity to save.
     * @return the persisted entity.
     */
    EmoMessagesDTO save(EmoMessagesDTO emoMessagesDTO);

    /**
     * Get all the emoMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmoMessagesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emoMessages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmoMessagesDTO> findOne(Long id);

    /**
     * Delete the "id" emoMessages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
