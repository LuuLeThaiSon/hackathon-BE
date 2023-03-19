package com.example.socialv.controller;

import com.example.socialv.model.UserUpdate;
import com.example.socialv.model.Users;
import com.example.socialv.service.login.LoginSevice;
import com.example.socialv.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private LoginSevice loginSevice;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users users) {
        if (loginSevice.login(users)) {
            Users usersLogin = userService.findUserByUsername(users.getUsername());
            usersLogin.setCheckOn(true);
            userService.save(usersLogin);
            return new ResponseEntity<>(usersLogin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/changePw")
    public ResponseEntity<?> changePassword(@RequestBody UserUpdate userUpdate) {
        if (loginSevice.changePassword(userUpdate)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<Users> logOut(@RequestBody Users users) {
            Users usersLogOut = userService.findUserByUsername(users.getUsername());
            usersLogOut.setCheckOn(false);
            userService.save(usersLogOut);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/permission")
    public ResponseEntity<Users> setSeeComment( @RequestBody Users users) {
        Users usersSetSeeComment = userService.findUserByUsername(users.getUsername());
        usersSetSeeComment.setCommentPermission(users.isCommentPermission());
        usersSetSeeComment.setSeeFriendPermission(users.isSeeFriendPermission());
        userService.save(usersSetSeeComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<Users>>showAllUsers(){
        List<Users> usersList = (List<Users>) userService.findAll();
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }



}
