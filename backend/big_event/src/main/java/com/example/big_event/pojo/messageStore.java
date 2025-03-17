package com.example.big_event.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class messageStore {
    private String nickname;
    private Integer PicId;
    private LocalDateTime storeTime;
    private String picture;
}
