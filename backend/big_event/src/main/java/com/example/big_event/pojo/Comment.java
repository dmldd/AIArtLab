package com.example.big_event.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private String content;
    private String nickname;
    private String picture;
    private LocalDateTime time;
    private Integer picId;
}
