package com.example.big_event.service.imp;
import com.example.big_event.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyTask {
    @Autowired
    UserMapper userMapper;
    @Scheduled(cron = "0 0 0 * * ?") // 每天午夜执行，表达式可以根据需要调整
    public void resetUserNum() {
        // 调用更新数据库的方法
        userMapper.resetNumOfNor();
        userMapper.resetNumOfVip();
        userMapper.updateShareTime();
        userMapper.resetNumOfSuperVip();
}}
