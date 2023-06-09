package com.example.hackathon.controller;

import com.example.hackathon.model.FriendRequest;
import com.example.hackathon.model.User;
import com.example.hackathon.service.friendRequestService.IFriendRequestService;
import com.example.hackathon.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private IFriendRequestService friendRequestService;
    @Autowired
    private IUserService userService;

    @GetMapping("/mutual/{id1}/{id2}")
    public ResponseEntity<List<User>> mutualFriends(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        //id1 cua friend, id2 cua nguoi dang nhap
        List<User> usersList = new ArrayList<>();
        for (User u : userService.findFriendRequestsByIdAndStatusTrue(id2)) {
            Optional<FriendRequest> friendRequest = friendRequestService.findFriendRequest(id1, u.getId());
            if (friendRequest.isPresent()) {
                usersList.add(u);
            }
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @PostMapping("/mutual/search/{id}")
    public ResponseEntity<List<Integer>> countMutualFriend(@PathVariable Long id, @RequestBody List<User> users) {
        List<Integer> integerList = new ArrayList<>();
        for (User u : users) {
            List<User> usersList = new ArrayList<>();
            for (User us : userService.findFriendRequestsByIdAndStatusTrue(id)) {
                Optional<FriendRequest> friendRequest = friendRequestService.findFriendRequest(us.getId(), u.getId());
                if (friendRequest.isPresent()) {
                    usersList.add(u);
                }
            }
            integerList.add(usersList.size());
        }
        return new ResponseEntity<>(integerList, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> requestFriend(@RequestBody FriendRequest friendRequest) {
        if (friendRequestService.findRequest(friendRequest.getUsersReceive().getId(), friendRequest.getUsersRequest().getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        friendRequestService.save(friendRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/checkRequest/{id1}/{id2}")
    public ResponseEntity<Boolean> checkRequest(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        return new ResponseEntity<>(friendRequestService.findRequest(id1, id2).isPresent(), HttpStatus.OK);
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<?> deleteFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        friendRequestService.deleteFriendRequest(id1, id2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<User>> getListFriend(@PathVariable Long id) {
        List<User> usersList = userService.findFriendRequestsByIdAndStatusTrue(id);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/accept/{id1}/{id2}")
    public ResponseEntity<?> acceptFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        friendRequestService.acceptFriendRequest(id1, id2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/request/{id}")
    public ResponseEntity<List<User>> listFriendRequest(@PathVariable Long id) {
        List<User> list = userService.listFriendRequest(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/sum")
    public ResponseEntity<List<Integer>> countFriend(@RequestBody User[] users) {
        List<Integer> countFriends = new ArrayList<>();
        for (User u : users
        ) {
            int count = friendRequestService.countFriend(u.getId());
            countFriends.add(count);
        }
        return new ResponseEntity<>(countFriends, HttpStatus.OK);
    }
}
