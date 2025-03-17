package com.example.big_event.controller;

import com.example.big_event.mapper.UserMapper;
import com.example.big_event.pojo.Result;
import com.example.big_event.service.imp.UserServiceImp;
import com.example.big_event.utils.PayUtils;
import com.example.big_event.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PayController {
    @Autowired
    private UserServiceImp userServiceImp;
    @RequestMapping("/pay")
    public Result pay(String productType,Double price,Integer credit){
        //productType有四种类型：1：两元    2：5.2元周vip    3：9.9月vip   4.19.9月超级vip
        HashMap claims=  ThreadLocalUtil.get();
        int UserId=(int) claims.get("id");
       String QR_url= PayUtils.pay(productType,price,1,UserId);
       if (QR_url==null){
           return Result.fail();
        }
       else return Result.success(QR_url);
    }

    @PostMapping("/payCallBack")
    public String payCallBack(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        if("OD".equals(parameterMap.get("status")[0])){
            System.out.println("用户支付成功");
            String[] parts=parameterMap.get("order_title")[0].split("_");
            String type= parts[0];
            int userId=Integer.parseInt(parts[1]);
            userServiceImp.addJianDao(type,userId);

        }else {
            System.out.println("用户支付失败");
        }
        return  "success";
    }
}
