package com.isoft.emotion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoMessagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoMessages.class);
        EmoMessages emoMessages1 = new EmoMessages();
        emoMessages1.setId(1L);
        EmoMessages emoMessages2 = new EmoMessages();
        emoMessages2.setId(emoMessages1.getId());
        assertThat(emoMessages1).isEqualTo(emoMessages2);
        emoMessages2.setId(2L);
        assertThat(emoMessages1).isNotEqualTo(emoMessages2);
        emoMessages1.setId(null);
        assertThat(emoMessages1).isNotEqualTo(emoMessages2);
    }
}
