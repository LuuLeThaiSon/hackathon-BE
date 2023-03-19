package com.example.hackathon.service.friendRequestService;

import com.example.hackathon.model.FriendRequest;
import com.example.hackathon.service.IGeneralService;

import java.util.Optional;

public interface IFriendRequestService extends IGeneralService<FriendRequest> {
    Optional<FriendRequest> findFriendRequest(Long id1, Long id2);
    Optional<FriendRequest> findRequest(Long id1, Long id2);
    void deleteFriendRequest(Long id1, Long id2);
    void acceptFriendRequest(Long id1, Long id2);
    int countFriend(Long id);
}
