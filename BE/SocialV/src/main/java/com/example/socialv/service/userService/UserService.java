package com.example.socialv.service.userService;

import com.example.socialv.model.FriendRequest;
import com.example.socialv.model.Users;
import com.example.socialv.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(Users users) {
        userRepository.save(users);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Users findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<Users> findFriendRequestsByIdAndStatusTrue(Long id) {
        return userRepository.findFriendRequestsByIdAndStatusTrue(id);
    }

    @Override
    public List<Users> findAllLikePost(Long id) {
        return userRepository.findAllLikePost(id);
    }

    @Override
    public List<Users> listFriendRequest(Long id) {
        return userRepository.listFriendRequest(id);
    }

    @Override
    public List<Users> findUsersActiveByName(String name) {
        return userRepository.findUsersByNameContainingAndStatusIsTrue(name);
    }

    @Override
    public List<Users> findInListFriend(Long id, String q) {
        List<Users> users = findFriendRequestsByIdAndStatusTrue(id);
        List<Users> listSearch = new ArrayList<>();
        for (Users users1: users){
            if (users1.getName().contains(q)){
                listSearch.add(users1);
            }
        }
        return listSearch;
    }

    @Override
    public List<Users> findMemberByConversation(Long id) {
        return userRepository.findMemberByConversation(id);
    }
}
