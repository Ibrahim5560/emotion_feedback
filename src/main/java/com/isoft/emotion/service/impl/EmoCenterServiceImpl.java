package com.isoft.emotion.service.impl;

import com.isoft.emotion.domain.EmoCenter;
import com.isoft.emotion.repository.EmoCenterRepository;
import com.isoft.emotion.service.EmoCenterService;
import com.isoft.emotion.service.dto.EmoCenterDTO;
import com.isoft.emotion.service.mapper.EmoCenterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmoCenter}.
 */
@Service
@Transactional
public class EmoCenterServiceImpl implements EmoCenterService {
    private final Logger log = LoggerFactory.getLogger(EmoCenterServiceImpl.class);

    private final EmoCenterRepository emoCenterRepository;

    private final EmoCenterMapper emoCenterMapper;

    public EmoCenterServiceImpl(EmoCenterRepository emoCenterRepository, EmoCenterMapper emoCenterMapper) {
        this.emoCenterRepository = emoCenterRepository;
        this.emoCenterMapper = emoCenterMapper;
    }

    @Override
    public EmoCenterDTO save(EmoCenterDTO emoCenterDTO) {
        log.debug("Request to save EmoCenter : {}", emoCenterDTO);
        EmoCenter emoCenter = emoCenterMapper.toEntity(emoCenterDTO);
        emoCenter = emoCenterRepository.save(emoCenter);
        return emoCenterMapper.toDto(emoCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmoCenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmoCenters");
        return emoCenterRepository.findAll(pageable).map(emoCenterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmoCenterDTO> findOne(Long id) {
        log.debug("Request to get EmoCenter : {}", id);
        return emoCenterRepository.findById(id).map(emoCenterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmoCenter : {}", id);
        emoCenterRepository.deleteById(id);
    }
}
