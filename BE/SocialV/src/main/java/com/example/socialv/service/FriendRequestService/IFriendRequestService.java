package com.example.socialv.service.FriendRequestService;

import com.example.socialv.model.FriendRequest;
import com.example.socialv.model.Users;
import com.example.socialv.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IFriendRequestService extends IGeneralService<FriendRequest> {
    Optional<FriendRequest> findFriendRequest(Long id1, Long id2);
    Optional<FriendRequest> findRequest(Long id1, Long id2);
    void deleteFriendRequest(Long id1, Long id2);
    void acceptFriendRequest(Long id1, Long id2);
    int countFriend(Long id);
}
