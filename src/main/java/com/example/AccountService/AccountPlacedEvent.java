package com.example.AccountService;

import java.time.LocalDateTime;
import java.util.Date;

public class AccountPlacedEvent {

    private String username;

    private String gmail;

    private LocalDateTime time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public AccountPlacedEvent(String username, String gmail, LocalDateTime time) {
        this.username = username;
        this.gmail = gmail;
        this.time = time;
    }

    public AccountPlacedEvent() {
    }
}
