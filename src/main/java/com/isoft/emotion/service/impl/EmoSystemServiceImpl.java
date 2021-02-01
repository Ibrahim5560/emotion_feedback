package com.isoft.emotion.service.impl;

import com.isoft.emotion.domain.EmoSystem;
import com.isoft.emotion.repository.EmoSystemRepository;
import com.isoft.emotion.service.EmoSystemService;
import com.isoft.emotion.service.dto.EmoSystemDTO;
import com.isoft.emotion.service.mapper.EmoSystemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmoSystem}.
 */
@Service
@Transactional
public class EmoSystemServiceImpl implements EmoSystemService {
    private final Logger log = LoggerFactory.getLogger(EmoSystemServiceImpl.class);

    private final EmoSystemRepository emoSystemRepository;

    private final EmoSystemMapper emoSystemMapper;

    public EmoSystemServiceImpl(EmoSystemRepository emoSystemRepository, EmoSystemMapper emoSystemMapper) {
        this.emoSystemRepository = emoSystemRepository;
        this.emoSystemMapper = emoSystemMapper;
    }

    @Override
    public EmoSystemDTO save(EmoSystemDTO emoSystemDTO) {
        log.debug("Request to save EmoSystem : {}", emoSystemDTO);
        EmoSystem emoSystem = emoSystemMapper.toEntity(emoSystemDTO);
        emoSystem = emoSystemRepository.save(emoSystem);
        return emoSystemMapper.toDto(emoSystem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmoSystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmoSystems");
        return emoSystemRepository.findAll(pageable).map(emoSystemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmoSystemDTO> findOne(Long id) {
        log.debug("Request to get EmoSystem : {}", id);
        return emoSystemRepository.findById(id).map(emoSystemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmoSystem : {}", id);
        emoSystemRepository.deleteById(id);
    }
}
