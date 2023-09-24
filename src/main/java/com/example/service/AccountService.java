package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws ClientErrorException {
        if (account.getUsername().isEmpty())
            throw new ClientErrorException("Please enter a valid username.");
        if (account.getPassword().length() < 4)
            throw new ClientErrorException("Please enter a longer password.");
        Optional<Account> accountOptional = Optional.of(this.accountRepository.findAccountByUsername(account.getUsername()));
        if (accountOptional.isPresent())
            throw new ClientErrorException("Please choose a different username.");

        accountOptional = Optional.of(this.accountRepository.save(account));
        if (accountOptional.isPresent())
            return accountOptional.get(); 
        
        return null;
    }

}
