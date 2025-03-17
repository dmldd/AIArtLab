package com.example.big_event.service.imp;

import cn.hutool.core.util.RandomUtil;
import com.example.big_event.mapper.UserMapper;
import com.example.big_event.pojo.*;
import com.example.big_event.pojo.picture.PictureIfo;
import com.example.big_event.pojo.picture.PictureIfo_userIfo;
import com.example.big_event.pojo.picture.StableDiffusionTextToImg;
import com.example.big_event.service.UserService;
import com.example.big_event.utils.Md5Util;
import com.example.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper;
//根据用户名查找用户
    @Override
    public User findByUserName(String userName) {
         User user= userMapper.findByUserName(userName);
        return user;
    }
//注册
    @Override
    public void register(String userName, String password,String email) {
        //加密
        String newPassword= Md5Util.getMD5String(password);
        userMapper.register(userName,newPassword,email);
    }
//分页查询

    public Result findPage(Integer pageNum,Integer pageSize,String userName) {
        Integer NewPageNum=(pageNum-1)*pageSize;
        String newUserName="%"+userName+"%";
        List<User> userList=  userMapper.findPage(NewPageNum,pageSize,newUserName);
        Integer totall=userMapper.findPageTotall();
        Map<String,Object> map=new HashMap<>();
        map.put("total",totall);
        map.put("data",userList);
        return Result.success(map);
    }
//添加用户
    @Override
    public void addUser(User user) {
        String password=  Md5Util.getMD5String("88888888")  ;
        user.setPassword(password);
        System.out.println(user.getNum());
        userMapper.addUser(user);
    }
//编辑用户
    @Override
    public void edit(User user) {
        if(user.getPassword()!=null){
         String passwor= Md5Util.getMD5String(user.getPassword()) ;
         user.setPassword(passwor);}
        userMapper.editUser(user);
    }
//删除用户
    @Override
    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }
//根据id批量删除用户
    @Override
    public void deleteBatch(int[] ids) {
        String idsString= Arrays.toString(ids).replace("[","").replace("]","");

        userMapper.deleteBatch(idsString);
    }

    @Override
    public void passwordChange(String NewPassword,String userName) {
        String password= Md5Util.getMD5String(NewPassword);
        userMapper.passwordChange(password,userName);
    }
//更新用户头像
    @Override
    public void updateAvatar(String url) {
        HashMap claims= ThreadLocalUtil.get();
        int id= (int)  claims.get("id");
        userMapper.addAvatar(url,id);

    }


    @Override
    public void SharePic(StableDiffusionTextToImg picIfo,String picUrl,Integer type) {
        HashMap claims= ThreadLocalUtil.get();
         int UserId=(int)  claims.get("id");
         PictureIfo pictureIfo=new PictureIfo();
         pictureIfo.setHostId(UserId);
         pictureIfo.setUrl(picUrl);
         pictureIfo.setSd_model_checkpoint(picIfo.getOverride_settings()==null? "" : picIfo.getOverride_settings().getSd_model_checkpoint());
         pictureIfo.setSd_vae(picIfo.getOverride_settings()==null? "" : picIfo.getOverride_settings().getSd_vae());
         pictureIfo.setPrompt(picIfo.getPrompt()==null? "" : picIfo.getPrompt());
         pictureIfo.setNegative_prompt(picIfo.getNegative_prompt()==null? "" : picIfo.getNegative_prompt());
         pictureIfo.setSampler_name(picIfo.getSampler_index()==null? "" : picIfo.getSampler_index());
         pictureIfo.setCfg_scale(picIfo.getCfg_scale()==null? 0 : picIfo.getCfg_scale());
         pictureIfo.setSteps(picIfo.getSteps()==null? 0 : picIfo.getSteps());
         pictureIfo.setWidth(picIfo.getWidth()==null? 0 : picIfo.getWidth());
         pictureIfo.setHeight(picIfo.getHeight()==null? 0: picIfo.getHeight());
         pictureIfo.setSeed(picIfo.getSeed()==null? 0:picIfo.getSeed());
         pictureIfo.setType(type);
         userMapper.SharePic(pictureIfo);
    }

    @Override
    public List<PictureIfo_userIfo> getStorePicList(Integer pageNum,Integer pageSize) {
        HashMap claims=ThreadLocalUtil.get();
        int id= (int)claims.get("id");
        Integer newPageNum=(pageNum-1)*pageSize;
        List<PictureIfo_userIfo> pictureIfoList= userMapper.getStorePicList(id,newPageNum,pageSize);
        return pictureIfoList;
    }


//收藏图片
    @Override
    public void StorePic(Integer picId) {
       HashMap claims=  ThreadLocalUtil.get();
        int UserId=(int) claims.get("id");
        userMapper.StorePic(picId,UserId);
        userMapper.StoreAdd(picId);
    }

//取消收藏
    @Override
    public void cancerStore(Integer picId) {
        HashMap claims=ThreadLocalUtil.get();
        Integer userId=(Integer) claims.get("id");
        userMapper.cancerStore(picId,userId);
        userMapper.storeSustract(picId);
    }

    //获取自己分享的图片
    @Override
    public List<PictureIfo_userIfo> getSharePic(Integer pageNum,Integer pageSize) {
         HashMap claims= ThreadLocalUtil.get();
         int userId=(int) claims.get("id");
         Integer newPageNum=(pageNum-1)*pageSize;
         List<PictureIfo_userIfo> pictureIfo_userIfoList= userMapper.getSharePic(userId,newPageNum,pageSize);
        return pictureIfo_userIfoList;
    }
//分页获取所有分享的图片
    @Override
    public List<PictureIfo_userIfo> getAllSharePic(Integer pageNum,Integer pageSize) {
        HashMap claims= ThreadLocalUtil.get();
        Integer userId;
        if (claims==null){
            userId=null;
        }
        else {
            userId=  (Integer)claims.get("id");
        }
        Integer newPageNum=(pageNum-1)*pageSize;
        List<PictureIfo_userIfo> pictureIfo_userIfoList= userMapper.getAllSharePic(userId,newPageNum,pageSize);
        return pictureIfo_userIfoList;
    }



//更新nickname
    @Override
    public void updateNickName(String nickname) {
        HashMap claims= ThreadLocalUtil.get();
        int userId=(int)  claims.get("id");
        userMapper.updateNickName(userId,nickname);
    }

    @Override
    public Integer findStore(Integer picId) {
        HashMap claims=  ThreadLocalUtil.get();
        int userId=(int)  claims.get("id");
        return userMapper.findStore(userId,picId);
    }

    @Override
    public Integer getNum() {
        HashMap claims=  ThreadLocalUtil.get();
        int userId=(int)  claims.get("id");
        Integer num= userMapper.getNum(userId);
        return num;
    }

    //减少剪刀数量
    @Override
    public void subtract(Integer num) {
        HashMap claims=  ThreadLocalUtil.get();
        int userId=(int)  claims.get("id");
        Integer ifSuperVip= userMapper.ifSuperVip(userId);
        if(ifSuperVip==2){
            return;
        }
        userMapper.subtract(userId,num);
    }

    @Override
    public Integer ifAdmin() {
        HashMap claims=  ThreadLocalUtil.get();
        Integer userId=(Integer)  claims.get("id");
       return userMapper.ifAdmin(userId);
    }
//更新sd网址
    @Override
    public void updateUrl(Integer id, String url) {
        userMapper.updateUrl(id,url);
    }

    @Override
    public String getSdUrl(Integer id) {
       return userMapper.getSdUrl(id);
    }

    @Override
    public Integer IfRecommendCodeExist(String recommendCode) {
      return    userMapper.IfRecommendCodeExist(recommendCode);
    }

    @Override
    public void addRecommendCode(String recommendCode) {
        userMapper.addRecommendCode(recommendCode);
    }

    @Override
    public Integer IfRecommendCodeExistAndValid(String recommendCode) {
        return userMapper.IfRecommendCodeExistAndValid(recommendCode);
    }

    @Override
    public void changeRecommendCodeStatus(Integer id) {
        userMapper.changeRecommendCodeStatus(id);
    }
//减少分享次数
    @Override
    public void substractShareTime() {
        HashMap claims=ThreadLocalUtil.get();
        Integer userId= (Integer)claims.get("id");
        userMapper.substractShareTime(userId);
    }

//增加剪刀数量
    @Override
    public void addJianDao(String type,int userId){

        if(type.equals("0")){
            userMapper.addJianDao(userId,300);
        }
        else if (type.equals("1")){
            Date endTime = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

            userMapper.setVipEndTime( endTime,userId);
            userMapper.setVip1(userId);
            userMapper.addJianDao(userId,300);

        }
        else if(type.equals("2")){
            Date  endTime = new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000);
            userMapper.setVipEndTime( endTime,userId);
            userMapper.setVip2(userId);
            userMapper.addJianDao(userId,300);
        }
        else{
            Date  endTime = new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000);
            userMapper.setVipEndTime( endTime,userId);
            userMapper.setVip3(userId);
            userMapper.addJianDao(userId,600);
        }
    }

//查询分享次数
    @Override
    public Integer getShareTimes() {
        HashMap claims=  ThreadLocalUtil.get();
        Integer userId=(Integer)  claims.get("id");
        Integer times= userMapper.getShareTimes(userId);
        return times;
    }

//上线用户
    public void isOnline(int username) {
        userMapper.isOnline(username);
    }

//添加未读信息数目
    @Override
    public void addUnreadNum(Integer userId) {
        userMapper.addUnreadNum(userId);
    }

//获取收藏消息
    @Override
    public List<messageStore> getMessageStore() {
        Map claims=  ThreadLocalUtil.get();
        Integer userId=(Integer) claims.get("id");
        return userMapper.getMessageStore(userId);

    }

//获取系统消息
    @Override
    public List<messageSystem> getMessageSystem() {
        return  userMapper.getMessageSystem();
    }

    @Override
    public void wipeMessageSystem() {
        HashMap claims= ThreadLocalUtil.get();
        Integer userId= (Integer)claims.get("id");
        userMapper.wipeMessageSystem(userId);
    }

    @Override
    public void wipeMessageStore() {
        HashMap claims= ThreadLocalUtil.get();
        Integer userId= (Integer)claims.get("id");
        userMapper.wipeMessageStore(userId);
    }

    @Override
    public void wipeMessageMy() {
        HashMap claims= ThreadLocalUtil.get();
        Integer userId= (Integer)claims.get("id");
        userMapper.wipeMessageMy(userId);
    }

    @Override
    public Integer findByEmail(String email) {
        Integer userId= userMapper.findByEmail(email);
        return userId;
    }

//发送给邮箱
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    EmailService emailService;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    public void sendEmail(String email) {
        String code=RandomUtil.randomNumbers(4);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setText("这是您的注册验证码 "+code+"。有效时间为10分钟");
        message.setSubject("格温画室：注册验证码 "+code);
        message.setTo(email);
        message.setFrom(from);
        javaMailSender.send(message);
        emailService.addEamilCode(email,code);
        emailService.addEmaiTimeOut(email, LocalDateTime.now().plusMinutes(10));
    }

    //添加评论
    @Override
    public void commend(String content, Integer picId,Integer hostId) {
        HashMap claims= ThreadLocalUtil.get();
        Integer userId= (Integer)claims.get("id");
        userMapper.commend(content,picId,userId);
        userMapper.addMessageMy(hostId);
        userMapper.addCommentNum(picId);
    }

    @Override
    public Comment[] getComments(Integer picId) {
       return userMapper.getComments(picId);
    }

    @Override
    public List<Comment> getMessageMy() {
        HashMap claims= ThreadLocalUtil.get();
        Integer userId= (Integer)claims.get("id");
        System.out.println(userId);
        return userMapper.getMessageMy(userId);
    }


}
