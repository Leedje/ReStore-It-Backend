package com.restoreit.models;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="chatMessages")
public class ChatMessage {

    @Id
    private String id;
    public String chatRoomId;
    public String sender;
    public String receiver;
    public String content;
    public LocalDateTime timeSent = LocalDateTime.now();

}
