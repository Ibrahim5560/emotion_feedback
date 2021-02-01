package com.isoft.emotion.service;

import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.domain.EmoMessageFeedback;
import com.isoft.emotion.repository.EmoMessageFeedbackRepository;
import com.isoft.emotion.service.dto.EmoMessageFeedbackCriteria;
import com.isoft.emotion.service.dto.EmoMessageFeedbackDTO;
import com.isoft.emotion.service.mapper.EmoMessageFeedbackMapper;
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
 * Service for executing complex queries for {@link EmoMessageFeedback} entities in the database.
 * The main input is a {@link EmoMessageFeedbackCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmoMessageFeedbackDTO} or a {@link Page} of {@link EmoMessageFeedbackDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmoMessageFeedbackQueryService extends QueryService<EmoMessageFeedback> {
    private final Logger log = LoggerFactory.getLogger(EmoMessageFeedbackQueryService.class);

    private final EmoMessageFeedbackRepository emoMessageFeedbackRepository;

    private final EmoMessageFeedbackMapper emoMessageFeedbackMapper;

    public EmoMessageFeedbackQueryService(
        EmoMessageFeedbackRepository emoMessageFeedbackRepository,
        EmoMessageFeedbackMapper emoMessageFeedbackMapper
    ) {
        this.emoMessageFeedbackRepository = emoMessageFeedbackRepository;
        this.emoMessageFeedbackMapper = emoMessageFeedbackMapper;
    }

    /**
     * Return a {@link List} of {@link EmoMessageFeedbackDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmoMessageFeedbackDTO> findByCriteria(EmoMessageFeedbackCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmoMessageFeedback> specification = createSpecification(criteria);
        return emoMessageFeedbackMapper.toDto(emoMessageFeedbackRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmoMessageFeedbackDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmoMessageFeedbackDTO> findByCriteria(EmoMessageFeedbackCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmoMessageFeedback> specification = createSpecification(criteria);
        return emoMessageFeedbackRepository.findAll(specification, page).map(emoMessageFeedbackMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmoMessageFeedbackCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmoMessageFeedback> specification = createSpecification(criteria);
        return emoMessageFeedbackRepository.count(specification);
    }

    /**
     * Function to convert {@link EmoMessageFeedbackCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmoMessageFeedback> createSpecification(EmoMessageFeedbackCriteria criteria) {
        Specification<EmoMessageFeedback> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmoMessageFeedback_.id));
            }
            if (criteria.getEmoSystemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmoSystemId(), EmoMessageFeedback_.emoSystemId));
            }
            if (criteria.getCenterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCenterId(), EmoMessageFeedback_.centerId));
            }
            if (criteria.getEmoSystemServicesId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getEmoSystemServicesId(), EmoMessageFeedback_.emoSystemServicesId));
            }
            if (criteria.getCounter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCounter(), EmoMessageFeedback_.counter));
            }
            if (criteria.getTrsId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrsId(), EmoMessageFeedback_.trsId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), EmoMessageFeedback_.userId));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), EmoMessageFeedback_.message));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmoMessageFeedback_.status));
            }
            if (criteria.getFeedback() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeedback(), EmoMessageFeedback_.feedback));
            }
            if (criteria.getApplicantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicantName(), EmoMessageFeedback_.applicantName));
            }
        }
        return specification;
    }
}
