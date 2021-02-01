package com.isoft.emotion.service.mapper;

import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.EmoUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmoUsers} and its DTO {@link EmoUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmoUsersMapper extends EntityMapper<EmoUsersDTO, EmoUsers> {
    @Mapping(target = "emoUsersMessages", ignore = true)
    @Mapping(target = "removeEmoUsersMessages", ignore = true)
    EmoUsers toEntity(EmoUsersDTO emoUsersDTO);

    default EmoUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmoUsers emoUsers = new EmoUsers();
        emoUsers.setId(id);
        return emoUsers;
    }
}
