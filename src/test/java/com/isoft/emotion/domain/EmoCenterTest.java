package com.isoft.emotion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoCenterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoCenter.class);
        EmoCenter emoCenter1 = new EmoCenter();
        emoCenter1.setId(1L);
        EmoCenter emoCenter2 = new EmoCenter();
        emoCenter2.setId(emoCenter1.getId());
        assertThat(emoCenter1).isEqualTo(emoCenter2);
        emoCenter2.setId(2L);
        assertThat(emoCenter1).isNotEqualTo(emoCenter2);
        emoCenter1.setId(null);
        assertThat(emoCenter1).isNotEqualTo(emoCenter2);
    }
}
