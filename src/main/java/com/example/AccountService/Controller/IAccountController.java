package com.example.AccountService.Controller;

import com.example.AccountService.DTO.ResponeAccountDTO;
import com.example.AccountService.Entity.Account;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/accounts")
public interface IAccountController {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CompletableFuture<ResponseEntity<ResponeAccountDTO>> login(@RequestParam String gmail, @RequestParam String password);

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<ResponeAccountDTO> getAccountInformation(@PathVariable int id);

    @PostMapping("/register")
    public ResponseEntity<ResponeAccountDTO> registerAccount(@RequestBody Account dto);

    @GetMapping("/getAccounts")
    public ResponseEntity<List<ResponeAccountDTO>> getAccounts();

    @GetMapping("/getAccountByCourseId")
    public ResponseEntity<List<ResponeAccountDTO>> findAccountByCourseId();



}
