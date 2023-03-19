package com.example.socialv.controller;

import com.example.socialv.model.Conversation;
import com.example.socialv.model.Messages;
import com.example.socialv.model.Users;
import com.example.socialv.service.conversationService.IConversationService;
import com.example.socialv.service.messageService.IMessageService;
import com.example.socialv.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IConversationService conversationService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;

    @GetMapping("/room/{id1}/{id2}")
    public ResponseEntity<Conversation> getPersonalConversation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2){
        return new ResponseEntity<>(conversationService.findPersonalConversation(id1, id2), HttpStatus.OK);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<List<Messages>> getMessage(@PathVariable Long id){
        Conversation conversation = conversationService.findById(id).get();
        List<Messages> messages = messageService.findAllByConversation(conversation);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String id){
        return "hello";
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody Messages message){
        messageService.save(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<?> createGroupChat(@RequestBody List<Users> users){
        conversationService.createGroupConversation(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Conversation>> getAllPersonalConversation(@PathVariable Long id){
        return new ResponseEntity<>(conversationService.getAllPersonalConversation(id), HttpStatus.OK);
    }
    @GetMapping("/room/group/{id}")
    public ResponseEntity<List<Conversation>> getAllGroupConversation(@PathVariable Long id){
        return new ResponseEntity<>(conversationService.getAllGroupConversation(id), HttpStatus.OK);
    }

    @GetMapping("/room/all/{id}")
    public ResponseEntity<List<Conversation>> getAllConversation(@PathVariable Long id){
        return new ResponseEntity<>(conversationService.getALlConversation(id), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<List<Users>> searchFriend(@PathVariable Long id, @RequestParam("q") String search){
        return new ResponseEntity<>(userService.findInListFriend(id, search), HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<List<Object>> findAllMemberInConversation(@RequestBody List<Conversation> conversations){
        List<Object> users = new ArrayList<>();
        for (Conversation conversation: conversations){
            users.add(userService.findMemberByConversation(conversation.getId()));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping ("/member/{id}")
    public ResponseEntity<List<Users>> findAllMemberInConversation(@PathVariable Long id){
        return new ResponseEntity<>(userService.findMemberByConversation(id), HttpStatus.OK);
    }

    @PutMapping("/changeName")
    public ResponseEntity<?> changeNameGroup(@RequestBody Conversation conversation){
        conversationService.save(conversation);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
