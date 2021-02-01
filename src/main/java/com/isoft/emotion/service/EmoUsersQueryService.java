package com.isoft.emotion.service;

import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.domain.EmoUsers;
import com.isoft.emotion.repository.EmoUsersRepository;
import com.isoft.emotion.service.dto.EmoUsersCriteria;
import com.isoft.emotion.service.dto.EmoUsersDTO;
import com.isoft.emotion.service.mapper.EmoUsersMapper;
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
 * Service for executing complex queries for {@link EmoUsers} entities in the database.
 * The main input is a {@link EmoUsersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmoUsersDTO} or a {@link Page} of {@link EmoUsersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmoUsersQueryService extends QueryService<EmoUsers> {
    private final Logger log = LoggerFactory.getLogger(EmoUsersQueryService.class);

    private final EmoUsersRepository emoUsersRepository;

    private final EmoUsersMapper emoUsersMapper;

    public EmoUsersQueryService(EmoUsersRepository emoUsersRepository, EmoUsersMapper emoUsersMapper) {
        this.emoUsersRepository = emoUsersRepository;
        this.emoUsersMapper = emoUsersMapper;
    }

    /**
     * Return a {@link List} of {@link EmoUsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmoUsersDTO> findByCriteria(EmoUsersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmoUsers> specification = createSpecification(criteria);
        return emoUsersMapper.toDto(emoUsersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmoUsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmoUsersDTO> findByCriteria(EmoUsersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmoUsers> specification = createSpecification(criteria);
        return emoUsersRepository.findAll(specification, page).map(emoUsersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmoUsersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmoUsers> specification = createSpecification(criteria);
        return emoUsersRepository.count(specification);
    }

    /**
     * Function to convert {@link EmoUsersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmoUsers> createSpecification(EmoUsersCriteria criteria) {
        Specification<EmoUsers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmoUsers_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), EmoUsers_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), EmoUsers_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), EmoUsers_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmoUsers_.status));
            }
            if (criteria.getEmoUsersMessagesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmoUsersMessagesId(),
                            root -> root.join(EmoUsers_.emoUsersMessages, JoinType.LEFT).get(EmoMessages_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
