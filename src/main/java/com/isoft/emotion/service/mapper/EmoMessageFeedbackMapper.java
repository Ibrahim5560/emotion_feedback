package com.isoft.emotion.service.mapper;

import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.EmoMessageFeedbackDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmoMessageFeedback} and its DTO {@link EmoMessageFeedbackDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmoMessageFeedbackMapper extends EntityMapper<EmoMessageFeedbackDTO, EmoMessageFeedback> {
    default EmoMessageFeedback fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmoMessageFeedback emoMessageFeedback = new EmoMessageFeedback();
        emoMessageFeedback.setId(id);
        return emoMessageFeedback;
    }
}
