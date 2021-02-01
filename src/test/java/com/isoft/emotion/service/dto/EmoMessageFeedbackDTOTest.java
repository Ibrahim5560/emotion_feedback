package com.isoft.emotion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoMessageFeedbackDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoMessageFeedbackDTO.class);
        EmoMessageFeedbackDTO emoMessageFeedbackDTO1 = new EmoMessageFeedbackDTO();
        emoMessageFeedbackDTO1.setId(1L);
        EmoMessageFeedbackDTO emoMessageFeedbackDTO2 = new EmoMessageFeedbackDTO();
        assertThat(emoMessageFeedbackDTO1).isNotEqualTo(emoMessageFeedbackDTO2);
        emoMessageFeedbackDTO2.setId(emoMessageFeedbackDTO1.getId());
        assertThat(emoMessageFeedbackDTO1).isEqualTo(emoMessageFeedbackDTO2);
        emoMessageFeedbackDTO2.setId(2L);
        assertThat(emoMessageFeedbackDTO1).isNotEqualTo(emoMessageFeedbackDTO2);
        emoMessageFeedbackDTO1.setId(null);
        assertThat(emoMessageFeedbackDTO1).isNotEqualTo(emoMessageFeedbackDTO2);
    }
}
