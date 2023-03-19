package com.example.socialv.repository;

import com.example.socialv.model.FriendRequest;
import com.example.socialv.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Users findUserByUsername(String username);
    @Query(value = "select * from users join friend_request fr on users.id = fr.user_receive_id " +
            "where fr.user_request_id =?1 and fr.status = true"
            , nativeQuery = true)
    List<Users> findFriendRequestsByIdAndStatusTrue(Long id);
    @Query(value = "select * from users join post_like pl on users.id = pl.user_id where pl.post_id = ?1", nativeQuery = true)
    List<Users> findAllLikePost(Long id);

    @Query(value = "select * from users join friend_request fr on users.id = fr.user_request_id  where fr.user_receive_id = ?1 and fr.status = false", nativeQuery = true)
    List<Users> listFriendRequest(Long id);
    List<Users>findUsersByNameContainingAndStatusIsTrue(String name);
    @Query(value = "select * from users where users.role_id = 1 ",nativeQuery = true)
    List<Users>showAllUser();
    @Query(value = "select * from users join conversation_member cm on users.id = cm.user_id where cm.conversation_id = ?1", nativeQuery = true)
    List<Users> findMemberByConversation(Long id);
}
