package com.example.big_event.service.imp;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class EmailService {
    Map<String,String> emailCodeMap=new HashMap<>();
    Map<String, LocalDateTime> emailTimeOutMap=new HashMap<>();
    public void addEamilCode(String email,String code){
        emailCodeMap.put(email,code);
    }
    public void addEmaiTimeOut(String email,LocalDateTime timeOut){
        emailTimeOutMap.put(email,timeOut);
    }
    public String getCode(String email){
       return emailCodeMap.get(email);
    }
    public LocalDateTime getTimeOut(String email){
        return emailTimeOutMap.get(email);
    }

}
