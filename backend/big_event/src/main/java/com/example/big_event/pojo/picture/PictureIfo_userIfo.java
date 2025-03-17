package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//用于返回前端展示图片信息，包括作者的头像,nickname
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureIfo_userIfo implements Serializable {
    private Integer picId;
    private Integer id;
    private Integer type;
    private String url;
    private Integer store;
    private Boolean isCollected;
    private String nickname;
    private String picture;
    private String sdModelCheckpoint;
    private String sdVae;
    private String prompt;
    private String negativePrompt;
    private String samplerName;
    private Integer cfgScale;
    private Integer steps;
    private Integer width;
    private Integer seed;
    private Integer height;
    private Integer commentsNum;
}
