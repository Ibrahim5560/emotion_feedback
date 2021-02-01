package com.isoft.emotion.service.impl;

import com.isoft.emotion.domain.EmoMessageFeedback;
import com.isoft.emotion.repository.EmoMessageFeedbackRepository;
import com.isoft.emotion.service.EmoMessageFeedbackService;
import com.isoft.emotion.service.dto.EmoMessageFeedbackDTO;
import com.isoft.emotion.service.mapper.EmoMessageFeedbackMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmoMessageFeedback}.
 */
@Service
@Transactional
public class EmoMessageFeedbackServiceImpl implements EmoMessageFeedbackService {
    private final Logger log = LoggerFactory.getLogger(EmoMessageFeedbackServiceImpl.class);

    private final EmoMessageFeedbackRepository emoMessageFeedbackRepository;

    private final EmoMessageFeedbackMapper emoMessageFeedbackMapper;

    public EmoMessageFeedbackServiceImpl(
        EmoMessageFeedbackRepository emoMessageFeedbackRepository,
        EmoMessageFeedbackMapper emoMessageFeedbackMapper
    ) {
        this.emoMessageFeedbackRepository = emoMessageFeedbackRepository;
        this.emoMessageFeedbackMapper = emoMessageFeedbackMapper;
    }

    @Override
    public EmoMessageFeedbackDTO save(EmoMessageFeedbackDTO emoMessageFeedbackDTO) {
        log.debug("Request to save EmoMessageFeedback : {}", emoMessageFeedbackDTO);
        EmoMessageFeedback emoMessageFeedback = emoMessageFeedbackMapper.toEntity(emoMessageFeedbackDTO);
        emoMessageFeedback = emoMessageFeedbackRepository.save(emoMessageFeedback);
        return emoMessageFeedbackMapper.toDto(emoMessageFeedback);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmoMessageFeedbackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmoMessageFeedbacks");
        return emoMessageFeedbackRepository.findAll(pageable).map(emoMessageFeedbackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmoMessageFeedbackDTO> findOne(Long id) {
        log.debug("Request to get EmoMessageFeedback : {}", id);
        return emoMessageFeedbackRepository.findById(id).map(emoMessageFeedbackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmoMessageFeedback : {}", id);
        emoMessageFeedbackRepository.deleteById(id);
    }
}
