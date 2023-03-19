package com.example.hackathon.repository;

import com.example.hackathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    @Query(value = "select * from user join friend_request fr on user.id = fr.user_receive_id " +
            "where fr.user_request_id =?1 and fr.status = true"
            , nativeQuery = true)
    List<User> findFriendRequestsByIdAndStatusTrue(Long id);
    @Query(value = "select * from user join post_like pl on user.id = pl.user_id where pl.post_id = ?1", nativeQuery = true)
    List<User> findAllLikePost(Long id);

    @Query(value = "select * from user join friend_request fr on user.id = fr.user_request_id  where fr.user_receive_id = ?1 and fr.status = false", nativeQuery = true)
    List<User> listFriendRequest(Long id);
    List<User>findUsersByNameContainingAndStatusIsTrue(String name);
    @Query(value = "select * from user where user.role_id = 1 ",nativeQuery = true)
    List<User>showAllUser();
    @Query(value = "select * from user join conversation_member cm on user.id = cm.user_id where cm.conversation_id = ?1", nativeQuery = true)
    List<User> findMemberByConversation(Long id);
}
