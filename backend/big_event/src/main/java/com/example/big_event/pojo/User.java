package com.example.big_event.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String userName;
    private Integer messageMy;
    private Integer messageSystem;
    private Integer messageStore;
    private String password;
    private String nickname;
    private String email;
    private String address;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String picture;
    private Integer num;
    private Integer admin;
}
