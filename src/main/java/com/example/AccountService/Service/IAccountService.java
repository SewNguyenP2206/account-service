package com.example.AccountService.Service;

import com.example.AccountService.DTO.ResponeAccountDTO;
import com.example.AccountService.Entity.Account;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IAccountService {

    public Account findExistAccount(String gmail, String password);

    public Account getAccountInformation(int id);

    public Account registerAccount(Account dto);

    public List<Account> getAllAccounts();

    public ResponeAccountDTO fromAccount(Account account);


}
