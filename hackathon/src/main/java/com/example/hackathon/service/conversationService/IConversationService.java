package com.example.hackathon.service.conversationService;

import com.example.hackathon.model.Conversation;
import com.example.hackathon.model.User;
import com.example.hackathon.service.IGeneralService;

import java.util.List;

public interface IConversationService extends IGeneralService<Conversation> {
    Conversation findPersonalConversation(Long id1, Long id2);
    void createGroupConversation(List<User> list);
    List<Conversation> getAllPersonalConversation(Long id);
    List<Conversation> getAllGroupConversation(Long id);

    List<Conversation> getALlConversation(Long id);
}
