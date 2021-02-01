package com.isoft.emotion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoSystemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoSystem.class);
        EmoSystem emoSystem1 = new EmoSystem();
        emoSystem1.setId(1L);
        EmoSystem emoSystem2 = new EmoSystem();
        emoSystem2.setId(emoSystem1.getId());
        assertThat(emoSystem1).isEqualTo(emoSystem2);
        emoSystem2.setId(2L);
        assertThat(emoSystem1).isNotEqualTo(emoSystem2);
        emoSystem1.setId(null);
        assertThat(emoSystem1).isNotEqualTo(emoSystem2);
    }
}
