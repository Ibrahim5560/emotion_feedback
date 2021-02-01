package com.isoft.emotion.service.impl;

import com.isoft.emotion.domain.EmoMessages;
import com.isoft.emotion.repository.EmoMessagesRepository;
import com.isoft.emotion.service.EmoMessagesService;
import com.isoft.emotion.service.dto.EmoMessagesDTO;
import com.isoft.emotion.service.mapper.EmoMessagesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmoMessages}.
 */
@Service
@Transactional
public class EmoMessagesServiceImpl implements EmoMessagesService {
    private final Logger log = LoggerFactory.getLogger(EmoMessagesServiceImpl.class);

    private final EmoMessagesRepository emoMessagesRepository;

    private final EmoMessagesMapper emoMessagesMapper;

    public EmoMessagesServiceImpl(EmoMessagesRepository emoMessagesRepository, EmoMessagesMapper emoMessagesMapper) {
        this.emoMessagesRepository = emoMessagesRepository;
        this.emoMessagesMapper = emoMessagesMapper;
    }

    @Override
    public EmoMessagesDTO save(EmoMessagesDTO emoMessagesDTO) {
        log.debug("Request to save EmoMessages : {}", emoMessagesDTO);
        EmoMessages emoMessages = emoMessagesMapper.toEntity(emoMessagesDTO);
        emoMessages = emoMessagesRepository.save(emoMessages);
        return emoMessagesMapper.toDto(emoMessages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmoMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmoMessages");
        return emoMessagesRepository.findAll(pageable).map(emoMessagesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmoMessagesDTO> findOne(Long id) {
        log.debug("Request to get EmoMessages : {}", id);
        return emoMessagesRepository.findById(id).map(emoMessagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmoMessages : {}", id);
        emoMessagesRepository.deleteById(id);
    }
}
