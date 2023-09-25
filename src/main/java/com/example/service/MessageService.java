package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.exception.ClientErrorException;
import com.example.repository.*;

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
        return this.messageRepository.deleteMessageByMessageId(id); 
    }

    public int updateMessage(Message message, int id) throws ClientErrorException {
        if (message.getMessage_text().isBlank())
            throw new ClientErrorException("Please enter some text.");
        if (message.getMessage_text().length() >= 255)
            throw new ClientErrorException("Please enter a shorter message.");

        Optional<Message> messageOptional = this.messageRepository.findById(id);
        if (!messageOptional.isPresent())
            throw new ClientErrorException("Message does not exist.");
        
        return this.messageRepository.updateMessageByMessageId(message.getMessage_text(), id);
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        //User story did not explicitly say I had to check if the account exists.
        //Therefore, I did not check and throw a client error exception if they don't exist.
        return this.messageRepository.findAllByPostedBy(id);
    }
}
