package com.example.socialv.repository;

import com.example.socialv.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, Long> {
    @Modifying
    @Query(value = "insert into conversation(type, status) values(1, false)", nativeQuery = true)
    void createPersonalConversation();
    @Modifying
    @Query(value = "insert into conversation(type, status) values(2, false)", nativeQuery = true)
    void createGroupConversation();
    @Query(value = "SELECT * FROM conversation c JOIN conversation_member cm ON c.id = cm.conversation_id " +
            "WHERE c.type = 1 AND (cm.user_id IN (?1, ?2)) GROUP BY cm.conversation_id HAVING COUNT(DISTINCT cm.user_id) = 2", nativeQuery = true)
    Optional<Conversation> findPersonalConversation(Long id1, Long id2);

    @Query(value = "SELECT * FROM conversation ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Conversation getNewConversation();
    @Query(value = "select * from conversation c join conversation_member cm on c.id = cm.conversation_id where cm.user_id = ?1 and c.type = 1", nativeQuery = true)
    List<Conversation> getAllPersonalConversation(Long id);
    @Query(value = "select * from conversation c join conversation_member cm on c.id = cm.conversation_id where cm.user_id = ?1 and c.type = 2", nativeQuery = true)
    List<Conversation> getAllGroupConversation(Long id);

    List<Conversation> findAll();
}
