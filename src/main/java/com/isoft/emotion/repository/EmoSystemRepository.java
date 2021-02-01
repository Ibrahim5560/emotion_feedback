package com.isoft.emotion.repository;

import com.isoft.emotion.domain.EmoSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmoSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmoSystemRepository extends JpaRepository<EmoSystem, Long>, JpaSpecificationExecutor<EmoSystem> {}
