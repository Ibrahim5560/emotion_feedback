package com.isoft.emotion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoUsersDTO.class);
        EmoUsersDTO emoUsersDTO1 = new EmoUsersDTO();
        emoUsersDTO1.setId(1L);
        EmoUsersDTO emoUsersDTO2 = new EmoUsersDTO();
        assertThat(emoUsersDTO1).isNotEqualTo(emoUsersDTO2);
        emoUsersDTO2.setId(emoUsersDTO1.getId());
        assertThat(emoUsersDTO1).isEqualTo(emoUsersDTO2);
        emoUsersDTO2.setId(2L);
        assertThat(emoUsersDTO1).isNotEqualTo(emoUsersDTO2);
        emoUsersDTO1.setId(null);
        assertThat(emoUsersDTO1).isNotEqualTo(emoUsersDTO2);
    }
}
