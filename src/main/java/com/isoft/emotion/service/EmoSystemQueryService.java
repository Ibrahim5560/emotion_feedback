package com.isoft.emotion.service;

import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.domain.EmoSystem;
import com.isoft.emotion.repository.EmoSystemRepository;
import com.isoft.emotion.service.dto.EmoSystemCriteria;
import com.isoft.emotion.service.dto.EmoSystemDTO;
import com.isoft.emotion.service.mapper.EmoSystemMapper;
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
 * Service for executing complex queries for {@link EmoSystem} entities in the database.
 * The main input is a {@link EmoSystemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmoSystemDTO} or a {@link Page} of {@link EmoSystemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmoSystemQueryService extends QueryService<EmoSystem> {
    private final Logger log = LoggerFactory.getLogger(EmoSystemQueryService.class);

    private final EmoSystemRepository emoSystemRepository;

    private final EmoSystemMapper emoSystemMapper;

    public EmoSystemQueryService(EmoSystemRepository emoSystemRepository, EmoSystemMapper emoSystemMapper) {
        this.emoSystemRepository = emoSystemRepository;
        this.emoSystemMapper = emoSystemMapper;
    }

    /**
     * Return a {@link List} of {@link EmoSystemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmoSystemDTO> findByCriteria(EmoSystemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmoSystem> specification = createSpecification(criteria);
        return emoSystemMapper.toDto(emoSystemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmoSystemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmoSystemDTO> findByCriteria(EmoSystemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmoSystem> specification = createSpecification(criteria);
        return emoSystemRepository.findAll(specification, page).map(emoSystemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmoSystemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmoSystem> specification = createSpecification(criteria);
        return emoSystemRepository.count(specification);
    }

    /**
     * Function to convert {@link EmoSystemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmoSystem> createSpecification(EmoSystemCriteria criteria) {
        Specification<EmoSystem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmoSystem_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), EmoSystem_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), EmoSystem_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), EmoSystem_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmoSystem_.status));
            }
            if (criteria.getEmoSystemMessagesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoSystemMessagesId(),
                            root -> root.join(EmoSystem_.emoSystemMessages, JoinType.LEFT).get(EmoMessages_.id)
                        )
                    );
            }
            if (criteria.getEmoSystemServicesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoSystemServicesId(),
                            root -> root.join(EmoSystem_.emoSystemServices, JoinType.LEFT).get(EmoSystemServices_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
