package com.isoft.emotion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoSystemServicesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoSystemServicesDTO.class);
        EmoSystemServicesDTO emoSystemServicesDTO1 = new EmoSystemServicesDTO();
        emoSystemServicesDTO1.setId(1L);
        EmoSystemServicesDTO emoSystemServicesDTO2 = new EmoSystemServicesDTO();
        assertThat(emoSystemServicesDTO1).isNotEqualTo(emoSystemServicesDTO2);
        emoSystemServicesDTO2.setId(emoSystemServicesDTO1.getId());
        assertThat(emoSystemServicesDTO1).isEqualTo(emoSystemServicesDTO2);
        emoSystemServicesDTO2.setId(2L);
        assertThat(emoSystemServicesDTO1).isNotEqualTo(emoSystemServicesDTO2);
        emoSystemServicesDTO1.setId(null);
        assertThat(emoSystemServicesDTO1).isNotEqualTo(emoSystemServicesDTO2);
    }
}
