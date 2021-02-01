package com.isoft.emotion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoSystemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoSystemDTO.class);
        EmoSystemDTO emoSystemDTO1 = new EmoSystemDTO();
        emoSystemDTO1.setId(1L);
        EmoSystemDTO emoSystemDTO2 = new EmoSystemDTO();
        assertThat(emoSystemDTO1).isNotEqualTo(emoSystemDTO2);
        emoSystemDTO2.setId(emoSystemDTO1.getId());
        assertThat(emoSystemDTO1).isEqualTo(emoSystemDTO2);
        emoSystemDTO2.setId(2L);
        assertThat(emoSystemDTO1).isNotEqualTo(emoSystemDTO2);
        emoSystemDTO1.setId(null);
        assertThat(emoSystemDTO1).isNotEqualTo(emoSystemDTO2);
    }
}
