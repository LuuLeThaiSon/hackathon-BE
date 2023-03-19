package com.example.socialv.repository;

import com.example.socialv.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IFriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query(value = "select * from friend_request where user_request_id = ?1 and user_receive_id = ?2 and status = true" , nativeQuery = true)
    Optional<FriendRequest> findFriendRequest(Long id1, Long id2);

    @Modifying
    @Query(value = "delete from friend_request where user_request_id = ?1 and user_receive_id = ?2", nativeQuery = true)
    void deleteFriendRequest(Long id1, Long id2);

    @Modifying
    @Query(value = "insert into friend_request(user_request_id, user_receive_id, status) values(?1, ?2, true)", nativeQuery = true)
    void acceptFriendRequest(Long id1, Long id2);

    @Query(value = "select * from friend_request where user_request_id = ?1 and user_receive_id = ?2 and status = false" , nativeQuery = true)
    Optional<FriendRequest> findRequest(Long id1, Long id2);

    @Query(value = "select COUNT(friend_request.status=true) from friend_request where friend_request.user_receive_id=?1",nativeQuery = true)
    int countFriend(Long id);
}
