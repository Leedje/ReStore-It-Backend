package com.restoreit.services;

import com.restoreit.models.ChatMessage;
import com.restoreit.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatMessage sendMessage(ChatMessage message) {
       return chatRepository.save(message);
    }

    public List<ChatMessage> getChatHistoryByChatID(String chatRoomId) {
        return chatRepository.findByChatRoomId(chatRoomId);//I need to order by time sent
        //this stopped working
    }

}
