package com.example.big_event.interceptor;

import com.example.big_event.exception.ServiceException;
import com.example.big_event.pojo.Result;
import com.example.big_event.pojo.User;
import com.example.big_event.service.imp.UserServiceImp;
import com.example.big_event.utils.JwtUtil;
import com.example.big_event.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImp userServiceImp;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果无法从请求中获取该请求头，不会报错，所以不用放到try catch里
         String token= request.getHeader("Authorization");
         if(token==null){
             //未登录
             throw new ServiceException(401,"您尚未登录");
         }
        //如果token解析失败，是会报错的，所以用try catch包裹起来
        try {
            Map<String,Object> claims= JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            //根据userName去数据库查找，没找到就不让进。防止管理员将该用户删除，而该token还有效的情况
            String userName=(String)  claims.get("userName");
            User user= userServiceImp.findByUserName(userName);
            if(user==null){
                 throw new ServiceException(402,"用户不存在,请重新登录");
            }
//return true 就是放行
            return  true;

        } catch (Exception e) {
            throw new ServiceException(402,"登录过期，请重新登录");
//不放行 且将状态码修改为401
        }
    }
    //每次响应结束后，就将threadlocal里的数据删除
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
