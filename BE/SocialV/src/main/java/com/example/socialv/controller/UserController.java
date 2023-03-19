package com.example.socialv.controller;

import com.example.socialv.model.Users;
import com.example.socialv.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Users>> getAllUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Users> findById(@PathVariable Long id) {
        return userService.findById(id);
    }


    @GetMapping("/friend/{id}")
    public ResponseEntity<List<Users>> findAllFriend(@PathVariable Long id) {
        List<Users> users = userService.findFriendRequestsByIdAndStatusTrue(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> save(@RequestBody Users users, @PathVariable("id") Long id) {
        Optional<Users> users1 = userService.findById(id);
        if (!users1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        users.setId(users1.get().getId());
        if (users.getAvatar() == null) {
            users.setAvatar(users1.get().getAvatar());
        }
        users.setPassword(users1.get().getPassword());
        users.setStatus(users1.get().isStatus());
        users.setConfirmPassword(users1.get().getConfirmPassword());
        users.setRole(users1.get().getRole());
        userService.save(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("search")
    ResponseEntity<List<Users>>findUsersByNameContain(@RequestParam ("search") String name){
        List<Users> usersList=userService.findUsersActiveByName(name);
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }
}
