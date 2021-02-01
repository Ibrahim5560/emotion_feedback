package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.EmoUsersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.EmoUsers}.
 */
public interface EmoUsersService {
    /**
     * Save a emoUsers.
     *
     * @param emoUsersDTO the entity to save.
     * @return the persisted entity.
     */
    EmoUsersDTO save(EmoUsersDTO emoUsersDTO);

    /**
     * Get all the emoUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmoUsersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emoUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmoUsersDTO> findOne(Long id);

    /**
     * Delete the "id" emoUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
