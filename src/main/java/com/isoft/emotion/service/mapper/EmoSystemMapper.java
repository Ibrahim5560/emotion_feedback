package com.isoft.emotion.service.mapper;

import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.EmoSystemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmoSystem} and its DTO {@link EmoSystemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmoSystemMapper extends EntityMapper<EmoSystemDTO, EmoSystem> {
    @Mapping(target = "emoSystemMessages", ignore = true)
    @Mapping(target = "removeEmoSystemMessages", ignore = true)
    @Mapping(target = "emoSystemServices", ignore = true)
    @Mapping(target = "removeEmoSystemServices", ignore = true)
    EmoSystem toEntity(EmoSystemDTO emoSystemDTO);

    default EmoSystem fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmoSystem emoSystem = new EmoSystem();
        emoSystem.setId(id);
        return emoSystem;
    }
}
