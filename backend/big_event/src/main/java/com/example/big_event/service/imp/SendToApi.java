package com.example.big_event.service.imp;

import com.example.big_event.pojo.Result;
import com.example.big_event.pojo.picture.StableDiffusionImg2Img;
import com.example.big_event.pojo.picture.StableDiffusionTextToImg;
import com.example.big_event.pojo.picture.Tagger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class SendToApi {
    //发送请求，并且返回结果的body的Json字符串
    public static String sendToApi(String url, StableDiffusionTextToImg pictureIfo, Tagger tagger, StableDiffusionImg2Img img2img){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //  文生图
        if(url!=null&&tagger==null&&pictureIfo!=null&&img2img==null){
            HttpEntity<StableDiffusionTextToImg> requestEntity = new HttpEntity<>(pictureIfo, headers);
            //发送请求
            ResponseEntity<String> response1 = restTemplate.postForEntity(url, requestEntity, String.class);
            if (Objects.isNull(response1) || !response1.getStatusCode().is2xxSuccessful()) {
                return "此次生成图片失败";
            }
            try {
                String response2=response1.getBody();
                if (Objects.isNull(response2)){
                    return "此次生成图片失败";
                }
                return response2;
            }catch (Exception e) {
                System.out.println("Error processing and uploading image; 所在类：SendApi");
                return  "此次生成图片失败";
            }
        }
        //获取tagger
        else if(url!=null&&pictureIfo==null&tagger!=null){

            HttpEntity<Tagger> requestEntity = new HttpEntity<>(tagger, headers);
            try { ResponseEntity<String> response_Tagger = restTemplate.postForEntity(url, requestEntity, String.class);
            if (Objects.isNull(response_Tagger) || !response_Tagger.getStatusCode().is2xxSuccessful()) {
                return "Tagger获取失败";
            }

                String response_Tagger2=response_Tagger.getBody();
                if (Objects.isNull(response_Tagger2)){
                    return "Tagger获取失败";
                }
                return response_Tagger2;
            }catch (Exception e) {
                System.out.println("Error processing and uploading image; 所在类：SendApi");
                return  "Tagger获取失败";
            }
        }
        //图生图
        else if(pictureIfo==null&&tagger==null&&img2img!=null){
            HttpEntity<StableDiffusionImg2Img> requestEntity = new HttpEntity<>(img2img, headers);
            ResponseEntity<String> response_img2img = restTemplate.postForEntity(url, requestEntity, String.class);
            if (Objects.isNull(response_img2img) || !response_img2img.getStatusCode().is2xxSuccessful()) {
                return "此次生成图片失败";
            }
            try {

                String response2_img2img=response_img2img.getBody();
                if (Objects.isNull(response2_img2img)){
                    return "此次生成图片失败";
                }
                return response2_img2img;
            }catch (Exception e) {
                System.out.println("Error processing and uploading image; 所在类：SendApi");
                return  "此次生成图片失败";
            }
        }
        return "实参不符合要求";
    }
}
