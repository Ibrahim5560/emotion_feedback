package com.isoft.emotion.repository;

import com.isoft.emotion.domain.EmoCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmoCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmoCenterRepository extends JpaRepository<EmoCenter, Long>, JpaSpecificationExecutor<EmoCenter> {}
