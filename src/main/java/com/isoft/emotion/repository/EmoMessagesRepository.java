package com.isoft.emotion.repository;

import com.isoft.emotion.domain.EmoMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmoMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmoMessagesRepository extends JpaRepository<EmoMessages, Long>, JpaSpecificationExecutor<EmoMessages> {}
