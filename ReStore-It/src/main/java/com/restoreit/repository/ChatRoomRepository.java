package com.restoreit.repository;

import com.restoreit.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    List<ChatRoom> findByUserId(UUID userId);

    List<ChatRoom> findByOrderId(UUID orderId);
}
