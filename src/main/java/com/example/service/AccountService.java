package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws ClientErrorException, ConflictException {
        if (account.getUsername().isEmpty())
            throw new ClientErrorException("Please enter a valid username.");
        if (account.getPassword().length() < 4)
            throw new ClientErrorException("Please enter a longer password.");
        Optional<Account> accountOptional = Optional
            .ofNullable(this.accountRepository
            .findByUsername(account.getUsername()));
        if (accountOptional.isPresent())
            throw new ConflictException("Please choose a different username."); 
        
        return this.accountRepository.save(account);
    }

    public Account loginAttempt(Account account) throws UnauthorizedException {
        if (account.getUsername().isEmpty())
            throw new UnauthorizedException("Please enter a valid username.");
        Optional<Account> accountOptional = Optional
            .ofNullable(this.accountRepository
            .findByUsername(account.getUsername()));
        if (!accountOptional.isPresent())
            throw new UnauthorizedException("Username does not exist.");
        Account existingAccount = accountOptional.get();    
        if (!account.getPassword().equals(existingAccount.getPassword()))
            throw new UnauthorizedException("Passwords do not match.");
        
        return existingAccount;
    }

}
