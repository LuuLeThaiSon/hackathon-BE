package com.example.socialv.service.signUpService;

import com.example.socialv.model.Users;
import com.example.socialv.repository.IUserRepository;
import com.example.socialv.service.roleService.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleService roleService;
    public boolean signUp(Users users) {

        if (!checkUserExist(users)) {
            if (users.getPassword().equals(users.getConfirmPassword())) {
                users.setAvatar("https://i0.wp.com/sbcf.fr/wp-content/uploads/2018/03/sbcf-default-avatar.png?ssl=1");
                userRepository.save(users);
                return true;
            }
            return false;
        }
        return false;

    }

    public boolean checkUserExist(Users user) {
        List<Users> users = userRepository.findAll();
        for (Users u : users) {
            if (user.getUsername().equals(u.getUsername()) || user.getEmail().equals(u.getEmail())) {
                return true;
            }
        }
        return false;
    }

}
