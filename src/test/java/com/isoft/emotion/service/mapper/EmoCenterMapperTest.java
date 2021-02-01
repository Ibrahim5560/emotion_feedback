package com.isoft.emotion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmoCenterMapperTest {
    private EmoCenterMapper emoCenterMapper;

    @BeforeEach
    public void setUp() {
        emoCenterMapper = new EmoCenterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emoCenterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emoCenterMapper.fromId(null)).isNull();
    }
}
