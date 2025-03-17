package com.example.big_event.pojo.picture;

import lombok.Data;

@Data
public class ShareBody {
    private String picBase64;
    private StableDiffusionTextToImg picIfo;
    private Integer type;
}
