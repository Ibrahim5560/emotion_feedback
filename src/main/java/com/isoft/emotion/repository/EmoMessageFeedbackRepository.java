package com.isoft.emotion.repository;

import com.isoft.emotion.domain.EmoMessageFeedback;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmoMessageFeedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmoMessageFeedbackRepository
    extends JpaRepository<EmoMessageFeedback, Long>, JpaSpecificationExecutor<EmoMessageFeedback> {}
