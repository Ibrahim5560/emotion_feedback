package com.isoft.emotion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmoMessagesMapperTest {
    private EmoMessagesMapper emoMessagesMapper;

    @BeforeEach
    public void setUp() {
        emoMessagesMapper = new EmoMessagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emoMessagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emoMessagesMapper.fromId(null)).isNull();
    }
}
