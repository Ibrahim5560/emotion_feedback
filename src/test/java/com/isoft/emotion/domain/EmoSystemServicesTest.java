package com.isoft.emotion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoSystemServicesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoSystemServices.class);
        EmoSystemServices emoSystemServices1 = new EmoSystemServices();
        emoSystemServices1.setId(1L);
        EmoSystemServices emoSystemServices2 = new EmoSystemServices();
        emoSystemServices2.setId(emoSystemServices1.getId());
        assertThat(emoSystemServices1).isEqualTo(emoSystemServices2);
        emoSystemServices2.setId(2L);
        assertThat(emoSystemServices1).isNotEqualTo(emoSystemServices2);
        emoSystemServices1.setId(null);
        assertThat(emoSystemServices1).isNotEqualTo(emoSystemServices2);
    }
}
