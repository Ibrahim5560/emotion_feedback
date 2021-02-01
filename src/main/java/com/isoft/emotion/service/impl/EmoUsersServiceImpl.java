package com.isoft.emotion.service.impl;

import com.isoft.emotion.domain.EmoUsers;
import com.isoft.emotion.repository.EmoUsersRepository;
import com.isoft.emotion.service.EmoUsersService;
import com.isoft.emotion.service.dto.EmoUsersDTO;
import com.isoft.emotion.service.mapper.EmoUsersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmoUsers}.
 */
@Service
@Transactional
public class EmoUsersServiceImpl implements EmoUsersService {
    private final Logger log = LoggerFactory.getLogger(EmoUsersServiceImpl.class);

    private final EmoUsersRepository emoUsersRepository;

    private final EmoUsersMapper emoUsersMapper;

    public EmoUsersServiceImpl(EmoUsersRepository emoUsersRepository, EmoUsersMapper emoUsersMapper) {
        this.emoUsersRepository = emoUsersRepository;
        this.emoUsersMapper = emoUsersMapper;
    }

    @Override
    public EmoUsersDTO save(EmoUsersDTO emoUsersDTO) {
        log.debug("Request to save EmoUsers : {}", emoUsersDTO);
        EmoUsers emoUsers = emoUsersMapper.toEntity(emoUsersDTO);
        emoUsers = emoUsersRepository.save(emoUsers);
        return emoUsersMapper.toDto(emoUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmoUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmoUsers");
        return emoUsersRepository.findAll(pageable).map(emoUsersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmoUsersDTO> findOne(Long id) {
        log.debug("Request to get EmoUsers : {}", id);
        return emoUsersRepository.findById(id).map(emoUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmoUsers : {}", id);
        emoUsersRepository.deleteById(id);
    }
}
