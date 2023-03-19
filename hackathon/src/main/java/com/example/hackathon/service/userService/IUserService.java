package com.example.hackathon.service.userService;

import com.example.hackathon.model.User;
import com.example.hackathon.service.IGeneralService;

import java.util.List;

public interface IUserService extends IGeneralService<User> {
    User findUserByUsername(String username);
    List<User> findFriendRequestsByIdAndStatusTrue(Long id);
    List<User> findAllLikePost(Long id);
    List<User> listFriendRequest(Long id);
    List<User> findUsersActiveByName(String name);
    List<User> findInListFriend(Long id, String q);
    List<User> findMemberByConversation(Long id);
}
