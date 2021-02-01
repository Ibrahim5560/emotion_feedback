package com.isoft.emotion.service.mapper;

import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.EmoMessagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmoMessages} and its DTO {@link EmoMessagesDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { EmoCenterMapper.class, EmoSystemMapper.class, EmoSystemServicesMapper.class, EmoUsersMapper.class }
)
public interface EmoMessagesMapper extends EntityMapper<EmoMessagesDTO, EmoMessages> {
    @Mapping(source = "emoCenter.id", target = "emoCenterId")
    @Mapping(source = "emoSystem.id", target = "emoSystemId")
    @Mapping(source = "emoSystemServices.id", target = "emoSystemServicesId")
    @Mapping(source = "emoUsers.id", target = "emoUsersId")
    EmoMessagesDTO toDto(EmoMessages emoMessages);

    @Mapping(source = "emoCenterId", target = "emoCenter")
    @Mapping(source = "emoSystemId", target = "emoSystem")
    @Mapping(source = "emoSystemServicesId", target = "emoSystemServices")
    @Mapping(source = "emoUsersId", target = "emoUsers")
    EmoMessages toEntity(EmoMessagesDTO emoMessagesDTO);

    default EmoMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmoMessages emoMessages = new EmoMessages();
        emoMessages.setId(id);
        return emoMessages;
    }
}
