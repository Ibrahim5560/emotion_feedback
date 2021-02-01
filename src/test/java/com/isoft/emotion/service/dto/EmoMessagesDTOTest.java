package com.isoft.emotion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoMessagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoMessagesDTO.class);
        EmoMessagesDTO emoMessagesDTO1 = new EmoMessagesDTO();
        emoMessagesDTO1.setId(1L);
        EmoMessagesDTO emoMessagesDTO2 = new EmoMessagesDTO();
        assertThat(emoMessagesDTO1).isNotEqualTo(emoMessagesDTO2);
        emoMessagesDTO2.setId(emoMessagesDTO1.getId());
        assertThat(emoMessagesDTO1).isEqualTo(emoMessagesDTO2);
        emoMessagesDTO2.setId(2L);
        assertThat(emoMessagesDTO1).isNotEqualTo(emoMessagesDTO2);
        emoMessagesDTO1.setId(null);
        assertThat(emoMessagesDTO1).isNotEqualTo(emoMessagesDTO2);
    }
}
