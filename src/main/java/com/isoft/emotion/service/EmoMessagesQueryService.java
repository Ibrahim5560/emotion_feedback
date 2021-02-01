package com.isoft.emotion.service;

import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.repository.EmoMessagesRepository;
import com.isoft.emotion.service.dto.EmoMessagesCriteria;
import com.isoft.emotion.service.dto.EmoMessagesDTO;
import com.isoft.emotion.service.mapper.EmoMessagesMapper;
import io.github.jhipster.service.QueryService;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for executing complex queries for {@link EmoMessages} entities in the database.
 * The main input is a {@link EmoMessagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmoMessagesDTO} or a {@link Page} of {@link EmoMessagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmoMessagesQueryService extends QueryService<EmoMessages> {
    private final Logger log = LoggerFactory.getLogger(EmoMessagesQueryService.class);

    private final EmoMessagesRepository emoMessagesRepository;

    private final EmoMessagesMapper emoMessagesMapper;

    public EmoMessagesQueryService(EmoMessagesRepository emoMessagesRepository, EmoMessagesMapper emoMessagesMapper) {
        this.emoMessagesRepository = emoMessagesRepository;
        this.emoMessagesMapper = emoMessagesMapper;
    }

    /**
     * Return a {@link List} of {@link EmoMessagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmoMessagesDTO> findByCriteria(EmoMessagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmoMessages> specification = createSpecification(criteria);
        return emoMessagesMapper.toDto(emoMessagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmoMessagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmoMessagesDTO> findByCriteria(EmoMessagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmoMessages> specification = createSpecification(criteria);
        return emoMessagesRepository.findAll(specification, page).map(emoMessagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmoMessagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmoMessages> specification = createSpecification(criteria);
        return emoMessagesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmoMessagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmoMessages> createSpecification(EmoMessagesCriteria criteria) {
        Specification<EmoMessages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmoMessages_.id));
            }
            if (criteria.getCounter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCounter(), EmoMessages_.counter));
            }
            if (criteria.getTrsId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrsId(), EmoMessages_.trsId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), EmoMessages_.userId));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), EmoMessages_.message));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmoMessages_.status));
            }
            if (criteria.getApplicantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicantName(), EmoMessages_.applicantName));
            }
            if (criteria.getEmoCenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoCenterId(),
                            root -> root.join(EmoMessages_.emoCenter, JoinType.LEFT).get(EmoCenter_.id)
                        )
                    );
            }
            if (criteria.getEmoSystemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoSystemId(),
                            root -> root.join(EmoMessages_.emoSystem, JoinType.LEFT).get(EmoSystem_.id)
                        )
                    );
            }
            if (criteria.getEmoSystemServicesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoSystemServicesId(),
                            root -> root.join(EmoMessages_.emoSystemServices, JoinType.LEFT).get(EmoSystemServices_.id)
                        )
                    );
            }
            if (criteria.getEmoUsersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoUsersId(),
                            root -> root.join(EmoMessages_.emoUsers, JoinType.LEFT).get(EmoUsers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
