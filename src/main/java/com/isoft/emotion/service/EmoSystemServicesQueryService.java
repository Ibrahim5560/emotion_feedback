package com.isoft.emotion.service;

import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.domain.EmoSystemServices;
import com.isoft.emotion.repository.EmoSystemServicesRepository;
import com.isoft.emotion.service.dto.EmoSystemServicesCriteria;
import com.isoft.emotion.service.dto.EmoSystemServicesDTO;
import com.isoft.emotion.service.mapper.EmoSystemServicesMapper;
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
 * Service for executing complex queries for {@link EmoSystemServices} entities in the database.
 * The main input is a {@link EmoSystemServicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmoSystemServicesDTO} or a {@link Page} of {@link EmoSystemServicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmoSystemServicesQueryService extends QueryService<EmoSystemServices> {
    private final Logger log = LoggerFactory.getLogger(EmoSystemServicesQueryService.class);

    private final EmoSystemServicesRepository emoSystemServicesRepository;

    private final EmoSystemServicesMapper emoSystemServicesMapper;

    public EmoSystemServicesQueryService(
        EmoSystemServicesRepository emoSystemServicesRepository,
        EmoSystemServicesMapper emoSystemServicesMapper
    ) {
        this.emoSystemServicesRepository = emoSystemServicesRepository;
        this.emoSystemServicesMapper = emoSystemServicesMapper;
    }

    /**
     * Return a {@link List} of {@link EmoSystemServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmoSystemServicesDTO> findByCriteria(EmoSystemServicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmoSystemServices> specification = createSpecification(criteria);
        return emoSystemServicesMapper.toDto(emoSystemServicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmoSystemServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmoSystemServicesDTO> findByCriteria(EmoSystemServicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmoSystemServices> specification = createSpecification(criteria);
        return emoSystemServicesRepository.findAll(specification, page).map(emoSystemServicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmoSystemServicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmoSystemServices> specification = createSpecification(criteria);
        return emoSystemServicesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmoSystemServicesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmoSystemServices> createSpecification(EmoSystemServicesCriteria criteria) {
        Specification<EmoSystemServices> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmoSystemServices_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), EmoSystemServices_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), EmoSystemServices_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), EmoSystemServices_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmoSystemServices_.status));
            }
            if (criteria.getEmoSystemServicesMessagesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoSystemServicesMessagesId(),
                            root -> root.join(EmoSystemServices_.emoSystemServicesMessages, JoinType.LEFT).get(EmoMessages_.id)
                        )
                    );
            }
            if (criteria.getEmoSystemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoSystemId(),
                            root -> root.join(EmoSystemServices_.emoSystem, JoinType.LEFT).get(EmoSystem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
