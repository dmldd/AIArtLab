package com.example.big_event.mapper;

import com.example.big_event.pojo.Comment;
import com.example.big_event.pojo.User;
import com.example.big_event.pojo.messageStore;
import com.example.big_event.pojo.messageSystem;
import com.example.big_event.pojo.picture.PictureIfo;
import com.example.big_event.pojo.picture.PictureIfo_userIfo;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from big_event.admin where user_name=#{userName}")
     User findByUserName(String userName);

    @Insert("insert into big_event.admin(user_name,password,create_time,update_time,email) values (#{userName},#{password},now(),now(),#{email})")
    void register(String userName, String password,String email);
    @Select("select * from admin where user_name like #{newUserName}  limit #{pageNum},#{pageSize}")
    List<User> findPage(Integer pageNum,Integer pageSize,String newUserName);
//查找总页数
    @Select("select count(*) from admin")
    Integer findPageTotall();
//添加成员
    @Insert("insert into admin(user_name,nickname,password,num,update_time) values (#{userName},#{nickname},#{password},num,now())")
    void addUser(User user);
//编辑成员
    @Update("update admin set user_name=#{userName},nickname=#{nickname},num=#{num},update_time=now() where id=#{id}")
    void editUser(User user);
//删除用户
    @Delete("delete from admin where id=#{id}")
    void deleteUser(User user);
//批量删除
    @Delete("delete from admin where id in (${ids}) ")
    void deleteBatch(String ids);

//根据用户名修改密码
    @Update("update admin set password=#{rePassword},update_time=now() where user_name=#{userName}")
    void passwordChange(String rePassword, String userName);

//更新用户头像
    @Update("update admin set picture=#{url},update_time=now() where id=#{id}")
    void addAvatar(String url, int id);

//用户分享图片
    @Insert("insert into showpicturelist(url,host_id,sd_model_checkpoint,sd_vae,prompt,negative_prompt,sampler_name,cfg_scale,steps,width,seed,height,type,time) values (#{url},#{hostId},#{sd_model_checkpoint},#{sd_vae},#{prompt},#{negative_prompt},#{sampler_name},#{cfg_scale},#{steps},#{width},#{seed},#{height},#{type},now())")
    void SharePic(PictureIfo pictureIfo);

 //获取收藏图片
    @Select("select s.Pic_id,s.url,s.store,u.nickname,u.picture,s.sd_model_checkpoint,s.sd_vae,s.prompt,s.negative_prompt,s.sampler_name,s.cfg_scale,s.steps,s.width,s.seed,s.height from showpicturelist s inner join admin u on s.host_id=u.id  where Pic_id in (select Pic_id from storelist where user_id=#{id}) limit #{pageNum},#{pageSize}")
    List<PictureIfo_userIfo> getStorePicList(int id,Integer pageNum,Integer pageSize);

//收藏图片
    @Insert("insert into storelist(user_id, Pic_id,store_time) values (#{userId},#{picId},now())")
    void StorePic(Integer picId, int userId);

//增加收藏数字
    @Update("update showpicturelist set store=store+1 where Pic_id=#{picId}")
    void StoreAdd(Integer picId);

//取消收藏图片
    @Delete("delete from storelist where Pic_id=#{picId} and user_id=#{userId}")
    void cancerStore(Integer picId,Integer userId);

//减少收藏数字
    @Update("update showpicturelist set store=store-1 where Pic_id=#{picId}")
    void storeSustract(Integer picId);


//获取自己分享的图片
    @Select("select s.Pic_id,s.url,s.store,u.nickname,u.picture,s.sd_model_checkpoint,s.sd_vae,s.prompt,s.negative_prompt,s.sampler_name,s.cfg_scale,s.steps,s.width,s.seed,s.height from showpicturelist s inner join admin u on s.host_id=u.id where host_id=#{userId} limit #{pageNum},#{pageSize}")
    List<PictureIfo_userIfo> getSharePic(int userId,Integer pageNum,Integer pageSize);

//分页获取所有分享图片
    @Select("SELECT \n" +
            "    s.Pic_id,u.id, s.url, s.store, u.nickname, u.picture, \n" +
            "    s.sd_model_checkpoint, s.sd_vae, s.prompt, s.negative_prompt, \n" +
            "    s.sampler_name, s.cfg_scale, s.steps, s.width, s.seed, s.height,s.type,\n" +
            "    CASE WHEN st.user_id IS NOT NULL THEN 1 ELSE 0 END as isCollected\n" +  //收藏为1 否则为0
            "FROM \n" +
            "    showpicturelist s\n" +
            "INNER JOIN \n" +
            "    admin u ON s.host_id = u.id\n" +
            "LEFT JOIN \n" +
            "    storelist st ON s.Pic_id = st.Pic_id AND st.user_id = #{userId} limit #{pageNum},#{pageSize}")
    List<PictureIfo_userIfo> getAllSharePic(Integer userId,Integer pageNum,Integer pageSize);


//更新用户nickname
    @Update("update admin set nickname=#{nickname},update_time=now() where id=#{userId}")
    void updateNickName(int userId, String nickname);

//查找用户是否已经收藏图片
    @Select("select id from storelist where Pic_id=#{picId} and user_id=#{userId}")
    Integer findStore(int userId, Integer picId);
    @Select("select num from admin where id=#{userId}")
    Integer getNum(int userId);
    //减少剪刀数目
    @Update("update admin set num=num- #{num}  where id=#{userId}")
    void subtract(int userId, Integer num);

    //增加/添加 剪刀数目
    @Update("update admin set num=num+ #{num} where id=#{userId} ")
    void addJianDao(int userId,Integer num);

    //重置普通用户剪刀数目
    @Update("update admin set num=100 where vip=0")
    void resetNumOfNor();

    //重置普通vip用户生成次数
    @Update("update admin set num=300 where vip in (1,2)")
    void resetNumOfVip();

    //重置超级vip用户生成次数
    @Update("update admin set num=600 where vip=3")
    void resetNumOfSuperVip();

    //设置为普通vip周费
    @Update("update admin set vip=1 where id=#{userId}")
    void setVip1(int userId);

    //设置为普通vip月费
    @Update ("update admin set vip=2 where id=#{userId}")
    void setVip2(int userId);

    //设置为超级vip月费
    @Update ("update admin set vip=3 where id=#{userId}")
    void setVip3(int userId);

    //判断是否为超级月费
    @Select("select vip from admin where id=#{userId}")
    Integer ifSuperVip(int userId);

    //设置会员到期时间
    @Update("update admin set vip_time=#{endTime} where id=#{userId}")
    void setVipEndTime(Date endTime,int userId);

    @Select("select admin from admin where id=#{userId}")
    Integer ifAdmin(Integer userId);
//更新sd网址
    @Update("update urls set url=#{url} where id=#{id}")
    void updateUrl(Integer id, String url);

//查询sd地址
    @Select("select url from urls where id=#{id}")
    public String getSdUrl(Integer id);
    @Select("select id from recommendcode where code=#{recommendCode}")
    Integer IfRecommendCodeExist(String recommendCode);
    @Insert("insert into recommendcode(code) values(#{recommendCode})")
    void addRecommendCode(String recommendCode);

    @Select("select id from recommendcode where code=#{recommendCode} and status=0")
    Integer IfRecommendCodeExistAndValid(String recommendCode);
    @Update("update recommendcode set status=1 where id=#{id}")
    void changeRecommendCodeStatus(Integer id);

//更新用户分享次数
    @Update("update admin set share_times=5")
    void updateShareTime();

//减少分享次数
    @Update("update admin set share_times=share_times-1 where id=#{userId}")
    void substractShareTime(Integer userId);

//查询分享次数
    @Select("select share_times from admin where id=#{userId}")
    Integer getShareTimes(Integer userId);

//上线用户
    @Update("update admin set is_online=1 where id=#{username}")
    void isOnline(int username);
    @Update("update admin set message_store=message_store+1 where id=#{userId}")
    void addUnreadNum(Integer userId);

//获取收藏消息
    @Select("select b.store_time,b.Pic_id,a.nickname,a.picture from admin a join (select user_id,store_time,Pic_id from storelist where Pic_id in (select Pic_id from showpicturelist where host_id=#{userId})) b on a.id=b.user_id ORDER BY store_time  DESC")
    List<messageStore> getMessageStore(Integer userId);

//获取作品评论消息
    @Select("select a.nickname,a.picture,c.time,c.content,c.pic_id from comment c join admin a on a.id=c.user_id where c.pic_id in(select Pic_id from showpicturelist where host_id=#{userId}) ORDER BY c.time DESC")
    List<Comment> getMessageMy(Integer userId);

//获取系统消息
    @Select("select message_system,time from message_system")
    List<messageSystem> getMessageSystem();

//擦除系统消息条数
    @Update("update admin set message_system=0 where id=#{userId}")
    void wipeMessageSystem(Integer userId);

//擦除收藏消息条数
    @Update("update admin set message_store=0 where  id=#{userId}")
    void wipeMessageStore(Integer userId);

    @Update("update admin set message_my=0 where  id=#{userId}")
    void wipeMessageMy(Integer userId);
//根据email查询用户
    @Select("select id from admin where email=#{email}")
    Integer findByEmail(String email);

    @Select("insert into comment(content, user_id, time, pic_id) values (#{content},#{userId},now(),#{picId})")
    void commend(String content, Integer picId, Integer userId);

    @Select("select content,nickname,picture,time from comment join admin on user_id=admin.id where pic_id=#{picId}")
    Comment[] getComments(Integer picId);

    @Update("update showpicturelist set comments_num=comments_num+1 where Pic_id=#{picId}")
    void addCommentNum(Integer picId);

    @Update("update admin set message_my=message_my+1 where id=#{hostId}")
    void addMessageMy(Integer hostId);
}
