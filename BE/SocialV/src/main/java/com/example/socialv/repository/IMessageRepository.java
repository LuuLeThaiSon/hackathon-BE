package com.example.socialv.repository;

import com.example.socialv.model.Conversation;
import com.example.socialv.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMessageRepository extends JpaRepository<Messages, Long> {
    List<Messages> findAllByConversation(Conversation conversation);
    @Query(value = "SELECT * FROM messages WHERE conversation_id = ?1 ORDER BY text_at DESC LIMIT 1", nativeQuery = true)
    Optional<Messages> getLatestMessage(Long id);
}
