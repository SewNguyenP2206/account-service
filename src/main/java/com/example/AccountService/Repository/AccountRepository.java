package com.example.AccountService.Repository;

import com.example.AccountService.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query("Select a from Account a where a.email = ?1 and a.password = ?2")
    public Account checkAccountLogin(String gmail,String password);

}
