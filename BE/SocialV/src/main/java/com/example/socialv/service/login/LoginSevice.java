package com.example.socialv.service.login;

import com.example.socialv.model.UserUpdate;
import com.example.socialv.model.Users;
import com.example.socialv.repository.IRoleRepository;
import com.example.socialv.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginSevice {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository iRoleRepository;

    public boolean login(Users users) {
        List<Users> usersList = userRepository.findAll();
        for (Users us : usersList) {
            if (us.getUsername().equals(users.getUsername()) && us.getPassword().equals(users.getPassword()) ) {
                us.setCheckOn(true);
                return true;
            }
        }
        return false;
    }



    public boolean changePassword(UserUpdate userUpdate) {
        Users users_update = userRepository.findById(userUpdate.getId()).get();
        if (users_update.getPassword().equals(userUpdate.getOldPassword())) {
            if (userUpdate.getNewPassword().equals(userUpdate.getConfirmNewPassword())) {
                users_update.setPassword(userUpdate.getNewPassword());
                userRepository.save(users_update);
                return true;
            }return false;
        }return false;
    }
}
