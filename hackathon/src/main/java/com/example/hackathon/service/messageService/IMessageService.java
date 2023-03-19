package com.example.hackathon.service.messageService;

import com.example.hackathon.model.Conversation;
import com.example.hackathon.model.Messages;
import com.example.hackathon.service.IGeneralService;


import java.util.List;

public interface IMessageService extends IGeneralService<Messages> {
    List<Messages> findAllByConversation(Conversation conversation);
}
