package com.isoft.emotion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmoUsersMapperTest {
    private EmoUsersMapper emoUsersMapper;

    @BeforeEach
    public void setUp() {
        emoUsersMapper = new EmoUsersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emoUsersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emoUsersMapper.fromId(null)).isNull();
    }
}
