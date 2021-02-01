package com.isoft.emotion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoMessageFeedbackTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoMessageFeedback.class);
        EmoMessageFeedback emoMessageFeedback1 = new EmoMessageFeedback();
        emoMessageFeedback1.setId(1L);
        EmoMessageFeedback emoMessageFeedback2 = new EmoMessageFeedback();
        emoMessageFeedback2.setId(emoMessageFeedback1.getId());
        assertThat(emoMessageFeedback1).isEqualTo(emoMessageFeedback2);
        emoMessageFeedback2.setId(2L);
        assertThat(emoMessageFeedback1).isNotEqualTo(emoMessageFeedback2);
        emoMessageFeedback1.setId(null);
        assertThat(emoMessageFeedback1).isNotEqualTo(emoMessageFeedback2);
    }
}
