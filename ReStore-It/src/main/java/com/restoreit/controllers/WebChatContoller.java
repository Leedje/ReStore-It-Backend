package com.restoreit.controllers;

import com.restoreit.dtos.ChatRequestDTO;
import com.restoreit.dtos.ChatRoomDTO;
import com.restoreit.dtos.OrderDTO;
import com.restoreit.models.ChatMessage;
import com.restoreit.services.ChatRoomService;
import com.restoreit.services.ChatService;
import com.restoreit.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class WebChatContoller {

    @Autowired
    private final ChatService chatService;

    @Autowired
    private final ChatRoomService chatRoomService;

    @Autowired
    private final JWTService jwtService;

    private SimpMessagingTemplate messagingTemplate;

    public WebChatContoller(ChatService chatService, JWTService jwtService, ChatRoomService chatRoomService) {
        this.chatService = chatService;
        this.jwtService = jwtService;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        ChatMessage sentMessage = chatService.sendMessage(chatMessage);
        messagingTemplate.convertAndSend("/topic/chats/" + sentMessage.chatRoomId, sentMessage);
    }

    @GetMapping("/history/{chatRoomId}")
    public List<ChatMessage> getChatHistory(@PathVariable String chatRoomId) {
        return chatService.getChatHistoryByChatID(chatRoomId);
    }

    @GetMapping("/business")
    public ResponseEntity<List<ChatRoomDTO>> getChatsByUserId(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            String extractedUserId = (String) jwtService.extractClaims(token).get("userId");

            if(jwtService.validateToken(token, extractedUserId)){
                UUID userId = UUID.fromString(extractedUserId);
                return ResponseEntity.ok(chatRoomService.GetChatRoomsByUserId(userId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/guest")
    public ResponseEntity<List<ChatRoomDTO>> getGuestChatsByEmailAndOrder(@RequestBody ChatRequestDTO chatRequest) {
        if(chatRequest.order.email.equals(chatRequest.email)){
            return ResponseEntity.ok(chatRoomService.GetChatRoomsByOrderId(chatRequest.order.id));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
