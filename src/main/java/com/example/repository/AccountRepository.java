package com.example.repository;

import org.springframework.data.jpa.repository.*;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    
    public Account findAccountByUsername(String username);
}
