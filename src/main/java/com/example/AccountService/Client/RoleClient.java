package com.example.AccountService.Client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ROLE-APP",path = "/api/role")
public interface RoleClient {
    @GetMapping("/name/{id}")
    public String getRoleNameById(@PathVariable int id);
}
