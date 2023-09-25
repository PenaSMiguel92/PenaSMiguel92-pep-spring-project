package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    //Dependency Injections
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //Account Mappings

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Account registerAcount(@RequestBody Account account) throws ClientErrorException, ConflictException {
        return this.accountService.registerAccount(account);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Account loginAttempt(@RequestBody Account account) throws UnauthorizedException {
        return this.accountService.loginAttempt(account);
    }

    //Message Mappings

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public Message createMessage(@RequestBody Message message) throws ClientErrorException {
        return this.messageService.createMessage(message);
    }

    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public Message getMessageById(@PathVariable("message_id") int id) {
        return messageService.getMessageById(id);
    }
    //TODO: Find out how to determine number of rows affected with JPARepository.
    //TODO: Find out how to send a custom responsebody, where it can be empty.
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") int id) {
        int affectedRows = this.messageService.deleteMessageById(id);
        if (affectedRows>0)
            return ResponseEntity
                .status(HttpStatus.OK)
                .header("delete-status", "true")
                .body(affectedRows);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("delete-status", "false")
                .build();
    }

    @PatchMapping("/messages")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message message) {
        int affectedRows = this.messageService.updateMessage(message);
        if (affectedRows > 0)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("update-status", "true")
                    .body(affectedRows);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("update-status", "false")
                .build();
    }

    // @GetMapping("/accounts/{account_id}/messages")
    // @ResponseStatus(HttpStatus.OK)
    // public List<Message> getAllMessagesByAccountId(@PathVariable("account_id") int id) {
    //     return this.messageService.getAllMessagesByAccountId(id);
    // }

    //Exception Handlers

    @ExceptionHandler(ClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String HandleClientErrorException(ClientErrorException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String HandleClientErrorException(ConflictException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String HandleUnauthorizedException(UnauthorizedException ex) {
        return ex.getMessage();
    }


}
