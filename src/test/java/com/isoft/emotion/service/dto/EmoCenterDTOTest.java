package com.isoft.emotion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.emotion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmoCenterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmoCenterDTO.class);
        EmoCenterDTO emoCenterDTO1 = new EmoCenterDTO();
        emoCenterDTO1.setId(1L);
        EmoCenterDTO emoCenterDTO2 = new EmoCenterDTO();
        assertThat(emoCenterDTO1).isNotEqualTo(emoCenterDTO2);
        emoCenterDTO2.setId(emoCenterDTO1.getId());
        assertThat(emoCenterDTO1).isEqualTo(emoCenterDTO2);
        emoCenterDTO2.setId(2L);
        assertThat(emoCenterDTO1).isNotEqualTo(emoCenterDTO2);
        emoCenterDTO1.setId(null);
        assertThat(emoCenterDTO1).isNotEqualTo(emoCenterDTO2);
    }
}
