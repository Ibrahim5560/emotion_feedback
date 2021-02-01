package com.isoft.emotion.repository;

import com.isoft.emotion.domain.EmoUsers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmoUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmoUsersRepository extends JpaRepository<EmoUsers, Long>, JpaSpecificationExecutor<EmoUsers> {}
