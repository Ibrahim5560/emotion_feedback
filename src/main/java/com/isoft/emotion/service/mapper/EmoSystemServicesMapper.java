package com.isoft.emotion.service.mapper;

import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.EmoSystemServicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmoSystemServices} and its DTO {@link EmoSystemServicesDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmoSystemMapper.class })
public interface EmoSystemServicesMapper extends EntityMapper<EmoSystemServicesDTO, EmoSystemServices> {
    @Mapping(source = "emoSystem.id", target = "emoSystemId")
    EmoSystemServicesDTO toDto(EmoSystemServices emoSystemServices);

    @Mapping(target = "emoSystemServicesMessages", ignore = true)
    @Mapping(target = "removeEmoSystemServicesMessages", ignore = true)
    @Mapping(source = "emoSystemId", target = "emoSystem")
    EmoSystemServices toEntity(EmoSystemServicesDTO emoSystemServicesDTO);

    default EmoSystemServices fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmoSystemServices emoSystemServices = new EmoSystemServices();
        emoSystemServices.setId(id);
        return emoSystemServices;
    }
}
