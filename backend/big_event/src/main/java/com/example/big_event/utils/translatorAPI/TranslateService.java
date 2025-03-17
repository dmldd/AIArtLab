package com.example.big_event.utils.translatorAPI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import org.springframework.stereotype.Service;
//
//@Service
public class TranslateService {
    private static final String APP_ID = "20240630002088443";
    private static final String SECURITY_KEY = "N3sqpGr5YLHnogZxGWaf";
    // 本地ip： 223.104.236.143
    public  String translateService(String query){
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String result =api.getTransResult(query, "auto", "en");
        //解析json字符串，解析出其中的结果
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray transResultArray = jsonObject.getJSONArray("trans_result");
        JSONObject transResultObject = transResultArray.getJSONObject(0);
        String dstValue = transResultObject.getString("dst");
        return dstValue;

    }
}
