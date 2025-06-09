package com.restoreit.mappers;

import com.restoreit.dtos.ChatRoomDTO;
import com.restoreit.models.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ChatRoomMapper.class}, componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);
    ChatRoom DTOtoChatRoom(ChatRoomDTO chatRoomDTO);
    ChatRoomDTO ChatRoomToDTO(ChatRoom chatRoom);
}
