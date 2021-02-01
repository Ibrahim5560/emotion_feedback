package com.isoft.emotion.repository;

import com.isoft.emotion.domain.EmoSystemServices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmoSystemServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmoSystemServicesRepository extends JpaRepository<EmoSystemServices, Long>, JpaSpecificationExecutor<EmoSystemServices> {}
