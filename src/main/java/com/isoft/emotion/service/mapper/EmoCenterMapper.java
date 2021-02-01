package com.isoft.emotion.service.mapper;

import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.EmoCenterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmoCenter} and its DTO {@link EmoCenterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmoCenterMapper extends EntityMapper<EmoCenterDTO, EmoCenter> {
    @Mapping(target = "emoCenterMessages", ignore = true)
    @Mapping(target = "removeEmoCenterMessages", ignore = true)
    EmoCenter toEntity(EmoCenterDTO emoCenterDTO);

    default EmoCenter fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmoCenter emoCenter = new EmoCenter();
        emoCenter.setId(id);
        return emoCenter;
    }
}
