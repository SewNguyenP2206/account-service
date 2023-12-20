package com.example.AccountService.Controller;

import com.example.AccountService.Config.ModelMapperObject;
import com.example.AccountService.DTO.ResponeAccountDTO;
import com.example.AccountService.Entity.Account;
import com.example.AccountService.Service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ModelMapperObject modelMapperObject;


    private ResponeAccountDTO accountToDTO(Account account) {
        return accountService.fromAccount(account);
    }


    @Override
    @CircuitBreaker(name = "role",fallbackMethod = "fallBackMethod")
    public CompletableFuture<ResponseEntity<ResponeAccountDTO>> login(String gmail, String password) {
        Account account = accountService.findExistAccount(gmail, password);
        if (account != null) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(accountToDTO(account)));
        } else {
            return CompletableFuture.completedFuture(new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
        }
    }
    public CompletableFuture<String> fallBackMethod(String gmail, String password, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }

    @Override
    public ResponseEntity<ResponeAccountDTO> getAccountInformation(int id) {

        Account account = accountService.getAccountInformation(id);
        return account != null ? new ResponseEntity<>(accountToDTO(account),HttpStatus.OK)
                : new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ResponeAccountDTO> registerAccount(Account dto) {
        Account account = accountService.registerAccount(dto);
        return account != null ? new ResponseEntity<>(accountToDTO(account),HttpStatus.OK)
                : new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<ResponeAccountDTO>> getAccounts() {
        List<Account> accounts = accountService.getAllAccounts();

        List<ResponeAccountDTO> responseAccountDTOs = accounts.stream()
                .map(account -> accountService.fromAccount(account))
                .collect(Collectors.toList());

        return !responseAccountDTOs.isEmpty() ? new ResponseEntity<>(responseAccountDTOs, HttpStatus.OK)
                : new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<ResponeAccountDTO>> findAccountByCourseId() {
        List<Account> accounts = accountService.getAllAccounts();

        List<ResponeAccountDTO> responseAccountDTOs = accounts.stream()
                    .map(account -> accountService.fromAccount(account))
                    .collect(Collectors.toList());

        return !responseAccountDTOs.isEmpty() ? new ResponseEntity<>(responseAccountDTOs, HttpStatus.OK)
                : new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

}
