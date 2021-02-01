package com.isoft.emotion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoUsers.class);
        EmoUsers emoUsers1 = new EmoUsers();
        emoUsers1.setId(1L);
        EmoUsers emoUsers2 = new EmoUsers();
        emoUsers2.setId(emoUsers1.getId());
        assertThat(emoUsers1).isEqualTo(emoUsers2);
        emoUsers2.setId(2L);
        assertThat(emoUsers1).isNotEqualTo(emoUsers2);
        emoUsers1.setId(null);
        assertThat(emoUsers1).isNotEqualTo(emoUsers2);
    }
}
