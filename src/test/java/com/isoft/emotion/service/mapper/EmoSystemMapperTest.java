package com.isoft.emotion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmoSystemMapperTest {
    private EmoSystemMapper emoSystemMapper;

    @BeforeEach
    public void setUp() {
        emoSystemMapper = new EmoSystemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emoSystemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emoSystemMapper.fromId(null)).isNull();
    }
}
