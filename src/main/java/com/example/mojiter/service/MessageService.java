package com.example.mojiter.service;

import com.example.mojiter.domain.User;
import com.example.mojiter.domain.dto.MessageDto;
import com.example.mojiter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Page<MessageDto> messagesList(Pageable pageable, String filter, User user){
        if (filter != null && !filter.isEmpty()){
            return messageRepository.findByTag(filter, pageable, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
    }

    public Page<MessageDto> messagesListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepository.findByUser(pageable, author, currentUser);
    }
}
