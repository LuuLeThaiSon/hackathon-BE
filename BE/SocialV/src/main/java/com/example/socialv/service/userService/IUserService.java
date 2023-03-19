package com.example.socialv.service.userService;

import com.example.socialv.model.Users;
import com.example.socialv.service.IGeneralService;
import java.util.List;

public interface IUserService extends IGeneralService<Users> {
    Users findUserByUsername(String username);
    List<Users> findFriendRequestsByIdAndStatusTrue(Long id);
    List<Users> findAllLikePost(Long id);
    List<Users> listFriendRequest(Long id);
    List<Users> findUsersActiveByName(String name);
    List<Users> findInListFriend(Long id, String q);
    List<Users> findMemberByConversation(Long id);
}
