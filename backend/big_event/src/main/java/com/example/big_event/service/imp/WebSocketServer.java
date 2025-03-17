package com.example.big_event.service.imp;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.big_event.utils.SpringContextUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint(value = "/imserver/{username}")
@Component
public class WebSocketServer {

    private static final Map<String, Session> sessionMap=new ConcurrentHashMap<>() ;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username){
        try {
            UserServiceImp userServiceImp = SpringContextUtil.getBean(UserServiceImp.class);
//        UserServiceImp userServiceImp1=new UserServiceImp();
        sessionMap.put(username,session);
        int userId = Integer.parseInt(username);
            System.out.println(userId );
            userServiceImp.isOnline(userId);
        } catch (Exception e) {
            e.printStackTrace(); // 或使用日志框架记录错误
            // 可能需要发送错误信息给客户端或进行其他错误处理
        }

    }
    @OnMessage
    public void onMessage(String message,Session session,@PathParam("username") String username){
        System.out.println("from"+username);
        JSONObject obj= JSONUtil.parseObj(message);
        String toUserName =obj.getStr("to");
        System.out.println("to"+toUserName);
        String pic_id=obj.getStr("pic_id");
        System.out.println("picid:"+pic_id);
        Session toSession=sessionMap.get(toUserName);
        if(toSession!=null){
            JSONObject jsonObject=new JSONObject();
            jsonObject.set("from",username);
            jsonObject.set("pic_id",pic_id);
            try {
                toSession.getBasicRemote().sendText(jsonObject.toString());
            }catch (Exception e){
                System.out.println(e);
            }
        }else {
            System.out.println("用户未登录，消息发送失败");
        }
    }
    @OnClose
    public void onClose(Session session,@PathParam("username") String username){
        sessionMap.remove(username);
    }
    @OnError
    public  void onError(Session session,Throwable error){
    }

}
