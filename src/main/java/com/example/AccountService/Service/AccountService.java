package com.example.AccountService.Service;


import brave.Span;
import brave.Tracer;
import com.example.AccountService.Client.RoleClient;
import com.example.AccountService.Config.ModelMapperObject;
import com.example.AccountService.AccountPlacedEvent;
import com.example.AccountService.DTO.ResponeAccountDTO;
import com.example.AccountService.Entity.Account;
import com.example.AccountService.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    Tracer tracer;

    @Autowired
    RoleClient roleClient;

    @Autowired
    KafkaTemplate<String,AccountPlacedEvent> kafkaTemplate;

    @Autowired
    ModelMapperObject modelMapperObject;
    @Override
    public Account findExistAccount(String gmail, String password) {

        return accountRepository.checkAccountLogin(gmail,password);
    }

    @Override
    public Account getAccountInformation(int id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public Account registerAccount(Account dto) {
        kafkaTemplate.send("notificationTopic",new AccountPlacedEvent(dto.getUsername(),dto.getEmail(), LocalDateTime.now()));
        return accountRepository.save(dto);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public ResponeAccountDTO fromAccount(Account account) {
        Span roleServiceLookUp = tracer.nextSpan().name("roleServiceLookUp");
        try(Tracer.SpanInScope spanInScope = tracer.withSpanInScope(roleServiceLookUp.start()))
        {
            ResponeAccountDTO dto = modelMapperObject.modelMapper().map(account, ResponeAccountDTO.class);
            dto.setRoleName(roleClient.getRoleNameById(account.getRoleId()));
            return dto;
        }
        finally {
            roleServiceLookUp.finish();
        }

    }

}
