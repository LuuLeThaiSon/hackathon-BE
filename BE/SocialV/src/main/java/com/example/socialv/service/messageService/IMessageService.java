package com.example.socialv.service.messageService;

import com.example.socialv.model.Conversation;
import com.example.socialv.model.Messages;
import com.example.socialv.service.IGeneralService;

import java.util.List;

public interface IMessageService extends IGeneralService<Messages> {
    List<Messages> findAllByConversation(Conversation conversation);
}
