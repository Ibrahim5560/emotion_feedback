package com.isoft.emotion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmoMessageFeedbackMapperTest {
    private EmoMessageFeedbackMapper emoMessageFeedbackMapper;

    @BeforeEach
    public void setUp() {
        emoMessageFeedbackMapper = new EmoMessageFeedbackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emoMessageFeedbackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emoMessageFeedbackMapper.fromId(null)).isNull();
    }
}
