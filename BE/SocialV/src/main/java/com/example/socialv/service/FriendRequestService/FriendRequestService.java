package com.example.socialv.service.FriendRequestService;

import com.example.socialv.model.FriendRequest;
import com.example.socialv.model.Users;
import com.example.socialv.repository.IFriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestService implements IFriendRequestService {
    @Autowired
    private IFriendRequestRepository friendRequestRepository;

    @Override
    public Iterable<FriendRequest> findAll() {
        return null;
    }

    @Override
    public Optional<FriendRequest> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<FriendRequest> findFriendRequest(Long id1, Long id2) {
        return friendRequestRepository.findFriendRequest(id1, id2);
    }

    @Override
    public Optional<FriendRequest> findRequest(Long id1, Long id2) {
        return friendRequestRepository.findRequest(id1,id2);
    }

    @Override
    @Transactional
    public void deleteFriendRequest(Long id1, Long id2) {
        friendRequestRepository.deleteFriendRequest(id1, id2);
        friendRequestRepository.deleteFriendRequest(id2, id1);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long id1, Long id2) {
        deleteFriendRequest(id1, id2);
        friendRequestRepository.acceptFriendRequest(id1, id2);
        friendRequestRepository.acceptFriendRequest(id2, id1);
    }

    @Override
    public int countFriend(Long id) {
        return friendRequestRepository.countFriend(id);
    }
}
