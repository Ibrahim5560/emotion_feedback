package com.isoft.emotion.service.impl;

import com.isoft.emotion.domain.EmoSystemServices;
import com.isoft.emotion.repository.EmoSystemServicesRepository;
import com.isoft.emotion.service.EmoSystemServicesService;
import com.isoft.emotion.service.dto.EmoSystemServicesDTO;
import com.isoft.emotion.service.mapper.EmoSystemServicesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmoSystemServices}.
 */
@Service
@Transactional
public class EmoSystemServicesServiceImpl implements EmoSystemServicesService {
    private final Logger log = LoggerFactory.getLogger(EmoSystemServicesServiceImpl.class);

    private final EmoSystemServicesRepository emoSystemServicesRepository;

    private final EmoSystemServicesMapper emoSystemServicesMapper;

    public EmoSystemServicesServiceImpl(
        EmoSystemServicesRepository emoSystemServicesRepository,
        EmoSystemServicesMapper emoSystemServicesMapper
    ) {
        this.emoSystemServicesRepository = emoSystemServicesRepository;
        this.emoSystemServicesMapper = emoSystemServicesMapper;
    }

    @Override
    public EmoSystemServicesDTO save(EmoSystemServicesDTO emoSystemServicesDTO) {
        log.debug("Request to save EmoSystemServices : {}", emoSystemServicesDTO);
        EmoSystemServices emoSystemServices = emoSystemServicesMapper.toEntity(emoSystemServicesDTO);
        emoSystemServices = emoSystemServicesRepository.save(emoSystemServices);
        return emoSystemServicesMapper.toDto(emoSystemServices);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmoSystemServicesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmoSystemServices");
        return emoSystemServicesRepository.findAll(pageable).map(emoSystemServicesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmoSystemServicesDTO> findOne(Long id) {
        log.debug("Request to get EmoSystemServices : {}", id);
        return emoSystemServicesRepository.findById(id).map(emoSystemServicesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmoSystemServices : {}", id);
        emoSystemServicesRepository.deleteById(id);
    }
}
