package com.example.hackathon.service.conversationMemberService;

import com.example.hackathon.model.ConversationMember;
import com.example.hackathon.repository.IConversationMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationMemberService implements IConversationMemberService {

    @Autowired
    private IConversationMemberRepository conversationMemberRepository;

    @Override
    public Iterable<ConversationMember> findAll() {
        return conversationMemberRepository.findAll();
    }

    @Override
    public Optional<ConversationMember> findById(Long id) {
        return conversationMemberRepository.findById(id);
    }

    @Override
    public void save(ConversationMember conversationMember) {
        conversationMemberRepository.save(conversationMember);
    }

    @Override
    public void remove(Long id) {
        conversationMemberRepository.deleteById(id);
    }
}
