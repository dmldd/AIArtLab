package com.example.big_event.pojo;

import com.example.big_event.pojo.picture.StableDiffusionImg2Img;
import com.example.big_event.pojo.picture.StableDiffusionTextToImg;
import com.example.big_event.pojo.picture.Tagger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueWrapper {
    private StableDiffusionTextToImg textToImgIfo;
    private String requestId;
    private Tagger taggerIfo;
    private StableDiffusionImg2Img img2ImgIfo;


    public  QueueWrapper(Tagger tagger,String requestId){
        this.taggerIfo=tagger;
        this.requestId=requestId;
    }
   public QueueWrapper(StableDiffusionTextToImg textToImgIfo,String requestId){
        this.textToImgIfo=textToImgIfo;
        this.requestId=requestId;
    }
   public QueueWrapper(StableDiffusionImg2Img img2ImgIfo,String requestId){
        this.img2ImgIfo=img2ImgIfo;
        this.requestId=requestId;
    }

}
