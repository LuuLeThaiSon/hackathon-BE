package com.example.hackathon.service.userService;

import com.example.hackathon.model.User;
import com.example.hackathon.repository.IUserRepository;
import com.example.hackathon.service.IGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserService implements IUserService  {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User users) {
        userRepository.save(users);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findFriendRequestsByIdAndStatusTrue(Long id) {
        return userRepository.findFriendRequestsByIdAndStatusTrue(id);
    }

    @Override
    public List<User> findAllLikePost(Long id) {
        return userRepository.findAllLikePost(id);
    }

    @Override
    public List<User> listFriendRequest(Long id) {
        return userRepository.listFriendRequest(id);
    }

    @Override
    public List<User> findUsersActiveByName(String name) {
        return userRepository.findUsersByNameContainingAndStatusIsTrue(name);
    }

    @Override
    public List<User> findInListFriend(Long id, String q) {
        List<User> users = findFriendRequestsByIdAndStatusTrue(id);
        List<User> listSearch = new ArrayList<>();
        for (User users1: users){
            if (users1.getName().contains(q)){
                listSearch.add(users1);
            }
        }
        return listSearch;
    }

    @Override
    public List<User> findMemberByConversation(Long id) {
        return userRepository.findMemberByConversation(id);
    }
}
