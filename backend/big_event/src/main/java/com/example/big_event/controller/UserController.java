package com.example.big_event.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.big_event.pojo.*;
import com.example.big_event.pojo.picture.*;
import com.example.big_event.service.imp.*;
import com.example.big_event.service.imp.queue.*;
import com.example.big_event.utils.AliOssUtil;
import com.example.big_event.utils.JwtUtil;
import com.example.big_event.utils.Md5Util;
import com.example.big_event.utils.ThreadLocalUtil;
//import org.codehaus.jettison.json.JSONObject;
import com.example.big_event.utils.translatorAPI.TransApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@RestController

public class UserController {
    @Value("${ENDPOINT}")
    private String ENDPOINT;
    @Value("${ACCESS_KEY_ID}")
    private String ACCESS_KEY_ID;
    @Value("${SECRET_ACCESS_KEY}")
    private String SECRET_ACCESS_KEY;
    @Value("${BUCKET_NAME}")
    private String BUCKET_NAME;
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private ObjectMapper  jacksonObjectMapper;

    @Autowired
    private RequestStatusService requestStatusService;


    @Autowired
    private EmailService emailService;


//发送邮箱
    @RequestMapping("/user/sendEmail")
    public Result sendEmail(String userName,String password,String email){
        User user = userServiceImp.findByUserName(userName);
        if(user!=null){
            return Result.fail("账号已被注册，请修改账号名");
        }
        Integer userId=userServiceImp.findByEmail(email);
        if (userId!=null){
            return Result.fail("邮箱已被其他用户绑定");
        }
        userServiceImp.sendEmail(email);
        return Result.success()  ;

    }


//注册
    @RequestMapping("/user/register")
    public Result register(String userName,String password,String email,String code){
        User user = userServiceImp.findByUserName(userName);
        if(user!=null)
        {return Result.fail("账号已被注册，请修改账号名");
        }
        //对recommend进行验证
//        Integer id=   userServiceImp.IfRecommendCodeExistAndValid(recommendCode);
//        System.out.println(id);
//        if(id==null){
//            return Result.fail("该验证码失效");
//        }
        //验证码通过，作废验证码
//        userServiceImp.changeRecommendCodeStatus(id);
        Integer userId=userServiceImp.findByEmail(email);
        if (userId!=null){
            return Result.fail("邮箱已被其他用户绑定");
        }
        if(!code.equals(emailService.getCode(email))||emailService.getTimeOut(email).isBefore(LocalDateTime.now())){
            return Result.fail("邮箱验证码错误或已经过期");
        };
        userServiceImp.register(userName,password,email);
        return Result.success();
        }


//登录
    @RequestMapping ("/user/login")
    public Result login(String userName,String password){

         User user= userServiceImp.findByUserName(userName);
         if(user==null){
             return Result.fail("账号不存在");
         }
         else if(Md5Util.getMD5String(password).equals(user.getPassword())){
             Map<String,Object> claims=new HashMap<>();  //将要存储的信息先放到map里，然后用这个map创建jwt
             claims.put("id",user.getId());
             claims.put("userName",user.getUserName());
             String token= JwtUtil.genToken(claims);
             return Result.success(token);
         }
         else return Result.fail("密码错误");
    }


//分页查询
    @RequestMapping("/user/findPage")
    public Result findPage(Integer pageNum,Integer pageSize,String userName){
        Integer ifAdmin=  userServiceImp.ifAdmin();
        if(ifAdmin==1){
            return Result.fail("你无权限哦");
        }
        return  userServiceImp.findPage(pageNum,pageSize,userName) ;
    }

    @RequestMapping("/user/add")
//添加成员
    public Result addUser(@RequestBody User user){
        Integer ifAdmin=  userServiceImp.ifAdmin();
        if(ifAdmin==1){
            return Result.fail("你无权限哦");
        }
        User user1= userServiceImp.findByUserName(user.getUserName());
        if(user1==null){
         userServiceImp.addUser(user);
         return  Result.success();}
        else return Result.fail("用户已经存在");
    }

//编辑成员，包括修改用户的生成次数
    @RequestMapping("/user/edit")
    public Result editUser(@RequestBody User user,@RequestParam int flag){
        Integer ifAdmin=  userServiceImp.ifAdmin();
        if(ifAdmin==1){
            return Result.fail("你无权限哦");
        }
        //用户名没变
        if(flag==0){
            userServiceImp.edit(user);
            return Result.success();
        }
        User user1= userServiceImp.findByUserName(user.getUserName());
        if(user1!=null){
            return Result.fail("用户名已存在");
        }
        userServiceImp.edit(user);
        return Result.success();
    }

//删除成员
    @RequestMapping("/user/delete")
    public Result deleteUser(@RequestBody User user){
        Integer ifAdmin=  userServiceImp.ifAdmin();
        if(ifAdmin==1){
            return Result.fail("你无权限哦");
        }
        userServiceImp.deleteUser(user);
        return Result.success();
    }
//批量删除成员
    @RequestMapping("/user/deleteBatch")
    public Result deleteBatch(@RequestBody int[] ids){
         Integer ifAdmin=  userServiceImp.ifAdmin();
         if(ifAdmin==1){
             return Result.fail("你无权限哦");
         }
        userServiceImp.deleteBatch(ids);
        return Result.success();
    }
// 更新sd的网址
    @RequestMapping("/user/updateUrl")
    public Result updateUrl(Integer id,String url){
        Integer ifAdmin=  userServiceImp.ifAdmin();
        if(ifAdmin==1){
            return Result.fail("你无权限哦");
        }
        userServiceImp.updateUrl(id,url);
        return Result.success();
    }

//管理员添加推荐码
    @RequestMapping("/admin/addRecommendCode")
    public Result addRecommendCode(String recommendCode){
        System.out.println(recommendCode);
      Integer ifAdmin=   userServiceImp.ifAdmin();
      if(ifAdmin==1){
          return  Result.fail("你无权限哦");
      }
     Integer id=   userServiceImp.IfRecommendCodeExist(recommendCode);
      if (id!=null){
          return Result.fail("已经存在该推荐码");
      }
      userServiceImp.addRecommendCode(recommendCode);
      return Result.success();

    }

//

//获取用户信息
    @RequestMapping("/user/getUserIfo")
    public Result getUserIfo(){
        HashMap claims=   ThreadLocalUtil.get();
         String userName=  (String) claims.get("userName");
         User user= userServiceImp.findByUserName(userName);
        return Result.success(user);
    }


//更新用户密码
    @RequestMapping("/user/passwordChange")
    public Result passwordChange(String password,String NewPassword,String ReNewPassword){
            if(!NewPassword.equals(ReNewPassword)){
                return Result.fail("两次密码不一致");
            }
            HashMap claims= ThreadLocalUtil.get();
             String userName=  (String)claims.get("userName");
             User user= userServiceImp.findByUserName(userName);
             if(!user.getPassword().equals(Md5Util.getMD5String(password))){
                 return Result.fail("原始密码错误");
             }
             userServiceImp.passwordChange(NewPassword,userName);
            return Result.success();
    }

//更新用户头像
    @PostMapping ("/user/updateAvatar")
    public Result updateAvatar(@RequestParam("file") MultipartFile file) throws Exception{
        String originalFilename=file.getOriginalFilename();
        String fileName="LNUyyds-"+ UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String url= AliOssUtil.uploadFile(fileName,file.getInputStream(),ENDPOINT,ACCESS_KEY_ID,SECRET_ACCESS_KEY,BUCKET_NAME);
        //将图片保存到数据库中
        userServiceImp.updateAvatar(url);
        return Result.success();
    }

//更新nickname
    @RequestMapping("/user/updateNick")
    public Result updateNickname(@RequestParam String nickname){
        userServiceImp.updateNickName(nickname);
        return Result.success();
    }

//点击收藏图片
    @RequestMapping("/user/storePic")
    public Result StorePic( Integer PicId,Integer userId){
          Integer id=  userServiceImp.findStore(PicId);
          //已经收藏
          if(id!=null){
              userServiceImp.cancerStore(PicId);
              return Result.success();
          }
        //未收藏
          userServiceImp.StorePic(PicId);
          userServiceImp.addUnreadNum(userId);
          return Result.success();
    }

//获取收藏的图片列表
    @RequestMapping("/user/getStorePicList")
    public Result getStorePicList(Integer pageNum, Integer pageSize){
        List<PictureIfo_userIfo> pictureIfo_userIfoList=userServiceImp.getStorePicList(pageNum,pageSize);
        return Result.success(pictureIfo_userIfoList);
    }

//获取自己分享的图片
    @RequestMapping("/user/getSharePic")
    public Result getSharePic(Integer pageNum,Integer pageSize) throws JsonProcessingException {
       List<PictureIfo_userIfo> pictureIfo_userIfoList=  userServiceImp.getSharePic(pageNum,pageSize);
       return Result.success(pictureIfo_userIfoList);
    }


//获取所有展示的图片
    @RequestMapping("/user/getAllSharePic")
    public Result getAllSharePic(Integer pageNum,Integer pageSize){
       List<PictureIfo_userIfo> pictureIfo_userIfoList=  userServiceImp.getAllSharePic(pageNum,pageSize);
       return Result.success(pictureIfo_userIfoList);
    }

//查询剩余生成次数
    @RequestMapping("/user/getNum")
    public Result getNum(){
       Integer num= userServiceImp.getNum();
       return Result.success(num);
    }

//减少生成次数
    @RequestMapping("/user/subtract")
    public  Result subtract(Integer num){
        userServiceImp.subtract(num);
        return Result.success();
    }
//查询分享次数
        @RequestMapping("/user/getShareTimes")
        public Result getShareTimes(){
        Integer shareTimes= userServiceImp.getShareTimes();
        return Result.success(shareTimes);
        }

//减少生成次数
    @RequestMapping("/user/substractShareTime")
    Result substractShareTime(){
        userServiceImp.substractShareTime();
        return Result.success();
    }

//获取收藏消息
    @RequestMapping("/user/getMessageStore")
    public Result getMessageStore(){
        List<messageStore> messageStoreList=userServiceImp.getMessageStore();
        return Result.success(messageStoreList);
    }

//获取作品评论消息
    @RequestMapping("/user/getMessageMy")
    public Result getMessageMy(){
        List<Comment> messageMyList=userServiceImp.getMessageMy();
        return Result.success(messageMyList);
    }

//获取系统消息
    @RequestMapping("user/getMessageSystem")
    public Result getMessageSystem(){
        List<messageSystem> messageSystemList= userServiceImp.getMessageSystem();
        return Result.success(messageSystemList);
    }

//清除系统消息条数
    @RequestMapping("user/wipeMessageSystem")
    public Result wipeMessageSystem(){
        userServiceImp.wipeMessageSystem();
        return Result.success();
    }

//擦除收藏消息条数
    @RequestMapping("user/wipeMessageStore")
    public Result wipeMessageStore(){
        userServiceImp.wipeMessageStore();
        return Result.success();
    }


//擦除我的消息条数
@RequestMapping("user/wipeMessageMy")
public Result wipeMessageMy(){
    userServiceImp.wipeMessageMy();
    return Result.success();
}


//用户分享图片
    @RequestMapping("/user/sharePic")
    public Result SharePic(@RequestBody ShareBody shareBody){
            InputStream inputStream = base64ToInputStream(((String) shareBody.getPicBase64()).substring("data:image/webp;base64,".length()));
            HashMap claims= ThreadLocalUtil.get();
            int UserId=(int)  claims.get("id");
            String objectName = "Peaky Tra-"+UserId+"-"+ UUID.randomUUID().toString()+".jpg";
            String picUrl1=  AliOssUtil.uploadFile(objectName, inputStream, ENDPOINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY, BUCKET_NAME);
            userServiceImp.SharePic((StableDiffusionTextToImg) shareBody.getPicIfo(),picUrl1,shareBody.getType());
            return Result.success();
    }
//评论图片
    @RequestMapping("/user/comment")
    public Result Comment(String content,Integer picId,Integer hostId){
        userServiceImp.commend(content,picId,hostId);
        return Result.success();
    }



//获取评论
    @RequestMapping("/user/getComment")
    public Result getComment(Integer picId){
        Comment[] comments=userServiceImp.getComments(picId);
        return Result.success(comments);
    }

    @Autowired
    QueueService queueService;
//生成图片
    //铺垫
private InputStream base64ToInputStream(String base64String) {
    byte[] imageBytes = Base64.getDecoder().decode(base64String);
    return new ByteArrayInputStream(imageBytes);
}

@Autowired
GetChannel getChannel;
//图生图  图生图和文生图有啥不同？  1，url后缀不同，  2，调用sendToapi的参数不同  Tagger的话，api返回的结果上处理也不同
//关于通道的几点说明：1，每一个网址对应一个模型，每个通道对应一个网址。每个通道都可以进行文生图，conotronet，图生图；
//                2，前端只负责选中文生图还是图生图还是tagger接口,后端根据模型选择对应的通道

//文生图
    @PostMapping("/user/textToImg")
    public Result generateImgTextToImg(@RequestBody StableDiffusionTextToImg pictureIfo){
        StableDiffusionTextToImg  newPictureIfo= AddPrompt.addPrompt_TextToImg(pictureIfo);
        //获取通道
        Integer channel=getChannel.getChannel(pictureIfo.getOverride_settings().getSd_model_checkpoint());
        String requestId = UUID.randomUUID().toString();
        //入队列
        queueService.enqueue(new QueueWrapper(newPictureIfo, requestId),channel);
        //更新状态
        Integer requestPosition= queueService.getSize(channel)+3;
        requestStatusService.createRequestStatus(requestId,requestPosition,channel);
        getChannel.processQueue(channel);
        return Result.success(requestId+";"+channel);
    }

//文生图-b站
    @RequestMapping("/textToImg")
    public Result generateTextToImage(@RequestParam String modelType,@RequestParam int width,@RequestParam int height,@RequestParam String prompt){
        if(width>=1500){
            width=1500;
        }
        if(width<=700){
            width=700;
        }
        if (height>=1500){
            height=1500;
        }
        if(height<=700){
            height=700;
        }
        StableDiffusionTextToImg picInfo =new StableDiffusionTextToImg();
        picInfo.setClip_skip(2);
        picInfo.setRestore_faces(false);
        picInfo.setTiling(false);
        picInfo.setPrompt("safe,<lora:add-detail-xl:1>, "+prompt);
        picInfo.setNegative_prompt("(nsfw,nude,naked,explict,:1.3),bloodcurdling, horrific, grisly, Politically relevant, XiJinPing");
        if (modelType.equals("Animagine.safetensors")){
            OverrideSettings overrideSettings=new OverrideSettings("Animagine.safetensors","[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picInfo.setOverride_settings(overrideSettings);
            picInfo.setSteps(28);
        }
        if(modelType.equals("Juggernaut.safetensors")){
            OverrideSettings overrideSettings=new OverrideSettings("Juggernaut.safetensors","[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picInfo.setPrompt(picInfo.getPrompt()+" realistic, Hyperrealistic,8k,sharp focus, soft light, <lora:MJ52:0.7> ");
            picInfo.setOverride_settings(overrideSettings);
        }
        picInfo.setHeight(height);
        picInfo.setWidth(width);
        picInfo=AddPrompt.addPrompt_TextToImg(picInfo);

        //获取通道
        Integer channel=getChannel.getChannel(picInfo.getOverride_settings().getSd_model_checkpoint());
        String requestId = UUID.randomUUID().toString();
        //入队列
        queueService.enqueue(new QueueWrapper(picInfo, requestId),channel);
        //更新状态
        Integer requestPosition= queueService.getSize(channel)+3;
        requestStatusService.createRequestStatus(requestId,requestPosition,channel);
        getChannel.processQueue(channel);
        return Result.success(requestId+";"+channel);



    }

//图生图
     @RequestMapping("/user/imgToImg")
     public Result generateImgToImg(@RequestBody StableDiffusionImg2Img pictureIfo){
         StableDiffusionImg2Img  newPictureIfo= AddPrompt.addPrompt_img2img(pictureIfo);
         Integer channel=getChannel.getChannel(newPictureIfo.getOverride_settings().getSd_model_checkpoint());
         String requestId = UUID.randomUUID().toString();
         //入队列
         queueService.enqueue(new QueueWrapper(newPictureIfo, requestId),channel);
         //更新状态
         Integer requestPosition= queueService.getSize(channel)+3;
         requestStatusService.createRequestStatus(requestId,requestPosition,channel);

         getChannel.processQueue(channel);
         return Result.success(requestId+";"+channel);
     }

// 调用tagger，  Tagger固定为1号通道
    @RequestMapping("/user/getTagger")
    public Result getTagger(@RequestBody Tagger tagger){
        String requestId = UUID.randomUUID().toString();
        //入队列
        queueService.enqueue(new QueueWrapper(tagger, requestId),1);
        //更新状态
        Integer requestPosition= queueService.getSize(1)+3;
        requestStatusService.createRequestStatus(requestId,requestPosition,1);

        getChannel.processQueue(1);
        return Result.success(requestId);
    }

//查询生成状态
@Autowired
RequestUrlService requestUrlService;
    @RequestMapping("/user/getUrl")
    public Result getUrl(@RequestParam String requestId,@RequestParam Integer queueNum){
       Integer status=  requestStatusService.getRequestStatus(requestId,queueNum);
       if(status==-2){
           requestStatusService.removeStatus(requestId,queueNum);
           return Result.fail("生成图片失败");
       }
//       if(status==-3){
//           requestStatusService.removeStatus(requestId,queueNum);
//           return Result.fail("图片违规");
//       }
       if(status==-1){
           try {
               requestStatusService.removeStatus(requestId,queueNum);
               return Result.success(requestUrlService.getRequestUrl(requestId));

           }finally {
               requestUrlService.removeUrl(requestId);
           }
       }
       else return Result.success(status);
    }

   @RequestMapping("/user/cancerQueue")
    public Result cancerQueue(@RequestParam String requestId,@RequestParam Integer queueNum){
       Integer status=  requestStatusService.getRequestStatus(requestId,queueNum);
       requestStatusService.printMap(1);
       if(status>=4){
           //取消排队
            queueService.cancerQueue(requestId,queueNum);
           requestStatusService.printMap(1);
            //更新该Id后后面所有Id的位置值
            requestStatusService.updateAfterStatus(queueNum,status);
//           requestStatusService.removeStatus(requestId,queueNum);
            return Result.success();
       }else {
           return Result.fail("取消失败");
       }

   }



}






