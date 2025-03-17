package com.example.big_event.service.imp.queue;

import com.example.big_event.pojo.picture.Return_pic;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public  class RequestUrlService {
    private  Map<String, Return_pic> requestUrlMap = new ConcurrentHashMap<>();
    public void createRequestUrl(String id,Return_pic return_pic){
        requestUrlMap.put(id,return_pic);
    }
    public Return_pic getRequestUrl(String id){
        return requestUrlMap.get(id);
    }
    public void removeUrl(String id){
        requestUrlMap.remove(id);
    }
    //打印map
    public  void  printMap(){
        StringBuilder sb = new StringBuilder();
        for (String key : requestUrlMap.keySet()) {
            sb.append(key).append("; ");
        }
        // 输出格式化后的 requestStatusMap 中的所有键
        System.out.println(sb.toString().trim());
    }
}
