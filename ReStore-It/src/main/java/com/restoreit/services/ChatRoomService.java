package com.restoreit.services;

import com.restoreit.dtos.ChatRoomDTO;
import com.restoreit.mappers.ChatRoomMapper;
import com.restoreit.models.ChatRoom;
import com.restoreit.models.Order;
import com.restoreit.models.Product;
import com.restoreit.models.User;
import com.restoreit.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private  final ChatRoomMapper chatRoomMapper;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserService userService, ChatRoomMapper chatRoomMapper) {
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
        this.chatRoomMapper = chatRoomMapper;
    }

    public boolean CreateChatRooms(Order order) {
        Set<String> existingChatRoomIds = chatRoomRepository.findAll()
                .stream().map(ChatRoom::getId).collect(Collectors.toSet());

        Set<ChatRoom> chatRoomsToSave = new HashSet<>();

        for (Product product : order.getProducts()) {
            User user = userService.GetUserByProductId(product.getId());
            String chatRoomId = user.getId() + "_" + order.getId();

            if (!existingChatRoomIds.contains(chatRoomId)) {
                chatRoomsToSave.add(new ChatRoom(chatRoomId,order, user, order.getFirstName(), product.getSeller()));
            }
        }

        chatRoomRepository.saveAll(chatRoomsToSave);
        return !chatRoomsToSave.isEmpty();
    }

    public List<ChatRoomDTO> GetChatRoomsByUserId(UUID userId) {
        return chatRoomRepository.findByUserId(userId)
                .stream()
                .map(chatRoomMapper::ChatRoomToDTO)
                .collect(Collectors.toList());
    }

    public List<ChatRoomDTO> GetChatRoomsByOrderId(UUID orderId) {
        return chatRoomRepository.findByOrderId(orderId)
                .stream()
                .map(chatRoomMapper::ChatRoomToDTO)
                .collect(Collectors.toList());
    }

}
