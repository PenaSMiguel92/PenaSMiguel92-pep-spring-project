package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) throws ClientErrorException {
        if (message.getMessage_text().isBlank())
            throw new ClientErrorException("Message is blank, please enter something.");
        
        if (message.getMessage_text().length() >= 255)
            throw new ClientErrorException("Message is too long.");
        
        Optional<Account> accountOptional = this.accountRepository
            .findById(message.getPosted_by());
        if (!accountOptional.isPresent())
            throw new ClientErrorException("User does not exist.");

        return this.messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }
    
    public Message getMessageById(int id) {
        Optional<Message> messageOptional = this.messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            return message;
        }

        return null;
    }

    public int deleteMessageById(int id) {
        Optional<Message> messageOptional = this.messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            this.messageRepository.deleteById(id);
            return 1;
        } 

        return 0;
    }

    public int updateMessage(Message message) {
        Optional<Message> messageOptional = this.messageRepository.findById(message.getMessage_id());
        if (messageOptional.isPresent()) {
            Message existingmessage = messageOptional.get();
            existingmessage.setMessage_text(message.getMessage_text());
            this.messageRepository.save(existingmessage);
            return 1;
        }

        return 0;
    }

    // public List<Message> getAllMessagesByAccountId(int id) {
    //     return this.messageRepository.findAllByAccountId(id);
    // }
}
