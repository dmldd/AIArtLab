package com.example.big_event.service;

import com.example.big_event.pojo.*;
import com.example.big_event.pojo.picture.Args;
import com.example.big_event.pojo.picture.PictureIfo;
import com.example.big_event.pojo.picture.PictureIfo_userIfo;
import com.example.big_event.pojo.picture.StableDiffusionTextToImg;

import java.util.List;

public interface UserService {
    User findByUserName(String userName);

    void register(String userName, String password,String email);

    Result findPage(Integer pageNum,Integer pageSize,String userName);


    void addUser(User user);

    void edit(User user);

    void deleteUser(User user);

    void deleteBatch(int[] ids);

    void passwordChange(String NewPassword,String userName);

    void updateAvatar(String url);

    void SharePic(StableDiffusionTextToImg picIfo,String picUrl,Integer type);

    List<PictureIfo_userIfo> getStorePicList(Integer pageNum,Integer pageSize);

    void StorePic(Integer picId);

//    void LikePic(Integer picId);

    List<PictureIfo_userIfo> getSharePic(Integer pageNum,Integer pageSize);

    List<PictureIfo_userIfo> getAllSharePic(Integer pageNum,Integer pageSize);

    void updateNickName(String nickname);

    Integer findStore(Integer picId);

    Integer getNum();

    void subtract(Integer num);

    Integer ifAdmin();
//更新用户的生成次数
    void updateUrl(Integer id, String url);


    String getSdUrl(Integer id);

    Integer IfRecommendCodeExist(String recommendCode);

    void addRecommendCode(String recommendCode);


    Integer IfRecommendCodeExistAndValid(String recommendCode);

    void changeRecommendCodeStatus(Integer id);

    void substractShareTime();

    Integer getShareTimes();

    void cancerStore(Integer picId);

    void  isOnline(int username);


    void addUnreadNum(Integer userId);

    List<messageStore> getMessageStore();

    List<messageSystem> getMessageSystem();

    void wipeMessageSystem();

    void wipeMessageStore();

    void wipeMessageMy();


    Integer findByEmail(String email);

    void sendEmail(String email);

    void commend(String content, Integer picId,Integer hostId);

    Comment[] getComments(Integer picId);

    List<Comment> getMessageMy();

    void addJianDao(String type, int userId);
}
