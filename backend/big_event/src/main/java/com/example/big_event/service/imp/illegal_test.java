package com.example.big_event.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.big_event.pojo.picture.Tagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public  class illegal_test {
    @Autowired
    private UserServiceImp userServiceImp;
    public  boolean Is_illegal(String base_url,Integer QUEUE_NUM){
        Tagger tagger=new Tagger(base_url,"wd-v1-4-moat-tagger.v2",0.20,"","");
                String url_Tagger = userServiceImp.getSdUrl(QUEUE_NUM)+"/tagger/v1/interrogate";
                String response_Tagger = SendToApi.sendToApi(url_Tagger, null, tagger, null);
                JSONObject jsonObject = JSON.parseObject(response_Tagger);
                JSONObject rating = jsonObject.getJSONObject("caption").getJSONObject("rating");
                double questionableValue = rating.getDoubleValue("questionable");
                double explicitValue = rating.getDoubleValue("explicit");
                if(questionableValue>0.5||explicitValue>0.5){
                    return true;
                }
                else return false;
    }
}
