package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.EmoMessageFeedbackDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.EmoMessageFeedback}.
 */
public interface EmoMessageFeedbackService {
    /**
     * Save a emoMessageFeedback.
     *
     * @param emoMessageFeedbackDTO the entity to save.
     * @return the persisted entity.
     */
    EmoMessageFeedbackDTO save(EmoMessageFeedbackDTO emoMessageFeedbackDTO);

    /**
     * Get all the emoMessageFeedbacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmoMessageFeedbackDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emoMessageFeedback.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmoMessageFeedbackDTO> findOne(Long id);

    /**
     * Delete the "id" emoMessageFeedback.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
