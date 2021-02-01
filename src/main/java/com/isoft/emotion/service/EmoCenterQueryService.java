package com.isoft.emotion.service;

import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.domain.EmoCenter;
import com.isoft.emotion.repository.EmoCenterRepository;
import com.isoft.emotion.service.dto.EmoCenterCriteria;
import com.isoft.emotion.service.dto.EmoCenterDTO;
import com.isoft.emotion.service.mapper.EmoCenterMapper;
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
 * Service for executing complex queries for {@link EmoCenter} entities in the database.
 * The main input is a {@link EmoCenterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmoCenterDTO} or a {@link Page} of {@link EmoCenterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmoCenterQueryService extends QueryService<EmoCenter> {
    private final Logger log = LoggerFactory.getLogger(EmoCenterQueryService.class);

    private final EmoCenterRepository emoCenterRepository;

    private final EmoCenterMapper emoCenterMapper;

    public EmoCenterQueryService(EmoCenterRepository emoCenterRepository, EmoCenterMapper emoCenterMapper) {
        this.emoCenterRepository = emoCenterRepository;
        this.emoCenterMapper = emoCenterMapper;
    }

    /**
     * Return a {@link List} of {@link EmoCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmoCenterDTO> findByCriteria(EmoCenterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmoCenter> specification = createSpecification(criteria);
        return emoCenterMapper.toDto(emoCenterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmoCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmoCenterDTO> findByCriteria(EmoCenterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmoCenter> specification = createSpecification(criteria);
        return emoCenterRepository.findAll(specification, page).map(emoCenterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmoCenterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmoCenter> specification = createSpecification(criteria);
        return emoCenterRepository.count(specification);
    }

    /**
     * Function to convert {@link EmoCenterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmoCenter> createSpecification(EmoCenterCriteria criteria) {
        Specification<EmoCenter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmoCenter_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), EmoCenter_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), EmoCenter_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), EmoCenter_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmoCenter_.status));
            }
            if (criteria.getEmoCenterMessagesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoCenterMessagesId(),
                            root -> root.join(EmoCenter_.emoCenterMessages, JoinType.LEFT).get(EmoMessages_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
