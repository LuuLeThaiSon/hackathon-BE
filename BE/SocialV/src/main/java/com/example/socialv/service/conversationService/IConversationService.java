package com.example.socialv.service.conversationService;

import com.example.socialv.model.Conversation;
import com.example.socialv.model.Users;
import com.example.socialv.service.IGeneralService;
import java.util.List;

public interface IConversationService extends IGeneralService<Conversation> {
    Conversation findPersonalConversation(Long id1, Long id2);
    void createGroupConversation(List<Users> list);
    List<Conversation> getAllPersonalConversation(Long id);
    List<Conversation> getAllGroupConversation(Long id);

    List<Conversation> getALlConversation(Long id);
}
