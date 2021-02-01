package com.isoft.emotion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmoSystemServicesMapperTest {
    private EmoSystemServicesMapper emoSystemServicesMapper;

    @BeforeEach
    public void setUp() {
        emoSystemServicesMapper = new EmoSystemServicesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emoSystemServicesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emoSystemServicesMapper.fromId(null)).isNull();
    }
}
