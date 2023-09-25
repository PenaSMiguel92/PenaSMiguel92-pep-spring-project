package com.example.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("FROM Account WHERE username = :usernameVar")
    public Account findByUsername(@Param("usernameVar") String username);
}
