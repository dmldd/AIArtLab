package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
//照片信息，对应与数据库表：showpicturelist
public class PictureIfo implements Serializable {
    private Integer type;
    private Integer PicId;
    private String url;
    private Integer hostId;
    private Integer store;
    private String sd_model_checkpoint;
    private String sd_vae;
    private String prompt;
    private String negative_prompt;
    private String sampler_name;
    private Integer cfg_scale;
    private Integer steps;
    private Integer width;
    private Integer seed;
    private Integer height;
}
