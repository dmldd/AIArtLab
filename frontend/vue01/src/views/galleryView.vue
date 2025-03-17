<template>
    <div>
        <!-- 图片参数弹框 -->
        <el-dialog  :visible.sync="dialogVisible" width="31%"    >
            <div v-if="SharePic.type==1">
                                <div style="display: flex;">
                                <div  style="color: #222222; font-size: 14px;font-weight: 600;margin-bottom: 6px;">Prompt</div>
                                <div @click="copyText(1)" style="margin-left: 365px;"> <svg data-v-780518c3="" width="24" height="20" viewBox="0 0 24 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path d="M16.8653 13.9711C16.6648 13.9711 16.4725 13.8915 16.3307 13.7497C16.189 13.6079 16.1093 13.4156 16.1093 13.2151C16.1093 13.0146 16.189 12.8223 16.3307 12.6805C16.4725 12.5388 16.6648 12.4591 16.8653 12.4591C17.0592 12.4591 17.2451 12.3821 17.3822 12.245C17.5193 12.1079 17.5963 11.922 17.5963 11.7281V5.17611C17.5963 4.98223 17.5193 4.7963 17.3822 4.65921C17.2451 4.52212 17.0592 4.44511 16.8653 4.44511H10.3133C10.1194 4.44511 9.93351 4.52212 9.79642 4.65921C9.65933 4.7963 9.58231 4.98223 9.58231 5.17611C9.58231 5.37661 9.50266 5.5689 9.36089 5.71068C9.21911 5.85246 9.02682 5.93211 8.82631 5.93211C8.62581 5.93211 8.43352 5.85246 8.29174 5.71068C8.14996 5.5689 8.07031 5.37661 8.07031 5.17611C8.07163 4.58163 8.30837 4.01188 8.72873 3.59152C9.14909 3.17117 9.71884 2.93443 10.3133 2.93311H16.8653C17.4598 2.93443 18.0295 3.17117 18.4499 3.59152C18.8703 4.01188 19.107 4.58163 19.1083 5.17611V11.7281C19.107 12.3226 18.8703 12.8923 18.4499 13.3127C18.0295 13.733 17.4598 13.9698 16.8653 13.9711Z"></path><path d="M13.5131 17.248H7.23814C6.92693 17.2484 6.6187 17.1874 6.3311 17.0685C6.0435 16.9495 5.78219 16.7751 5.56213 16.555C5.34207 16.3349 5.16758 16.0736 5.04867 15.786C4.92976 15.4984 4.86875 15.1902 4.86914 14.879V8.57899C4.86862 8.26769 4.92953 7.95935 5.04838 7.67164C5.16724 7.38392 5.3417 7.12249 5.56177 6.90232C5.78185 6.68216 6.0432 6.50758 6.33087 6.38861C6.61853 6.26963 6.92685 6.20859 7.23814 6.20899H13.5131C13.8244 6.20859 14.1326 6.2696 14.4202 6.38851C14.7078 6.50743 14.9691 6.68191 15.1892 6.90197C15.4092 7.12203 15.5837 7.38334 15.7026 7.67094C15.8215 7.95854 15.8825 8.26677 15.8821 8.57799V14.878C15.9071 16.189 14.8241 17.248 13.5131 17.248ZM7.23814 7.72099C7.12534 7.72005 7.01348 7.74158 6.90908 7.78432C6.80469 7.82706 6.70984 7.89015 6.63007 7.96992C6.55031 8.04968 6.48722 8.14453 6.44448 8.24893C6.40174 8.35332 6.38021 8.46518 6.38114 8.57799V14.878C6.38021 14.9908 6.40174 15.1026 6.44448 15.207C6.48722 15.3114 6.55031 15.4063 6.63007 15.4861C6.70984 15.5658 6.80469 15.6289 6.90908 15.6717C7.01348 15.7144 7.12534 15.7359 7.23814 15.735H13.5131C13.6259 15.7359 13.7378 15.7144 13.8422 15.6717C13.9466 15.6289 14.0414 15.5658 14.1212 15.4861C14.201 15.4063 14.2641 15.3114 14.3068 15.207C14.3495 15.1026 14.3711 14.9908 14.3701 14.878V8.57799C14.3711 8.46518 14.3495 8.35332 14.3068 8.24893C14.2641 8.14453 14.201 8.04968 14.1212 7.96992C14.0414 7.89015 13.9466 7.82706 13.8422 7.78432C13.7378 7.74158 13.6259 7.72005 13.5131 7.72099H7.23814Z"></path></svg>
                                </div>
                                </div>  
                                <div style=" border-radius: 5px;padding: 6px 10px; width: 430px;height: 100px; overflow-y: auto;overflow-x: hidden;background-color:rgba(0, 0, 0, 0.05) ;" >
                                    <span  style="color: #666666; font-size: 14px;    font-family: PingFang SC,system-ui,Avenir,Helvetica,Arial,sans-serif;"  >{{ SharePic.prompt }}</span>
                                </div>

                                <div style="display: flex;">
                                <div style="color: #222222; font-size: 14px;font-weight: 600; margin-top: 10px;margin-bottom: 6px;" >Negative Prompt</div>
                                 <div @click="copyText(2)" style="margin-left: 300px; margin-top: 10px;margin-bottom: 6px;"> <svg data-v-780518c3="" width="24" height="20" viewBox="0 0 24 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path d="M16.8653 13.9711C16.6648 13.9711 16.4725 13.8915 16.3307 13.7497C16.189 13.6079 16.1093 13.4156 16.1093 13.2151C16.1093 13.0146 16.189 12.8223 16.3307 12.6805C16.4725 12.5388 16.6648 12.4591 16.8653 12.4591C17.0592 12.4591 17.2451 12.3821 17.3822 12.245C17.5193 12.1079 17.5963 11.922 17.5963 11.7281V5.17611C17.5963 4.98223 17.5193 4.7963 17.3822 4.65921C17.2451 4.52212 17.0592 4.44511 16.8653 4.44511H10.3133C10.1194 4.44511 9.93351 4.52212 9.79642 4.65921C9.65933 4.7963 9.58231 4.98223 9.58231 5.17611C9.58231 5.37661 9.50266 5.5689 9.36089 5.71068C9.21911 5.85246 9.02682 5.93211 8.82631 5.93211C8.62581 5.93211 8.43352 5.85246 8.29174 5.71068C8.14996 5.5689 8.07031 5.37661 8.07031 5.17611C8.07163 4.58163 8.30837 4.01188 8.72873 3.59152C9.14909 3.17117 9.71884 2.93443 10.3133 2.93311H16.8653C17.4598 2.93443 18.0295 3.17117 18.4499 3.59152C18.8703 4.01188 19.107 4.58163 19.1083 5.17611V11.7281C19.107 12.3226 18.8703 12.8923 18.4499 13.3127C18.0295 13.733 17.4598 13.9698 16.8653 13.9711Z"></path><path d="M13.5131 17.248H7.23814C6.92693 17.2484 6.6187 17.1874 6.3311 17.0685C6.0435 16.9495 5.78219 16.7751 5.56213 16.555C5.34207 16.3349 5.16758 16.0736 5.04867 15.786C4.92976 15.4984 4.86875 15.1902 4.86914 14.879V8.57899C4.86862 8.26769 4.92953 7.95935 5.04838 7.67164C5.16724 7.38392 5.3417 7.12249 5.56177 6.90232C5.78185 6.68216 6.0432 6.50758 6.33087 6.38861C6.61853 6.26963 6.92685 6.20859 7.23814 6.20899H13.5131C13.8244 6.20859 14.1326 6.2696 14.4202 6.38851C14.7078 6.50743 14.9691 6.68191 15.1892 6.90197C15.4092 7.12203 15.5837 7.38334 15.7026 7.67094C15.8215 7.95854 15.8825 8.26677 15.8821 8.57799V14.878C15.9071 16.189 14.8241 17.248 13.5131 17.248ZM7.23814 7.72099C7.12534 7.72005 7.01348 7.74158 6.90908 7.78432C6.80469 7.82706 6.70984 7.89015 6.63007 7.96992C6.55031 8.04968 6.48722 8.14453 6.44448 8.24893C6.40174 8.35332 6.38021 8.46518 6.38114 8.57799V14.878C6.38021 14.9908 6.40174 15.1026 6.44448 15.207C6.48722 15.3114 6.55031 15.4063 6.63007 15.4861C6.70984 15.5658 6.80469 15.6289 6.90908 15.6717C7.01348 15.7144 7.12534 15.7359 7.23814 15.735H13.5131C13.6259 15.7359 13.7378 15.7144 13.8422 15.6717C13.9466 15.6289 14.0414 15.5658 14.1212 15.4861C14.201 15.4063 14.2641 15.3114 14.3068 15.207C14.3495 15.1026 14.3711 14.9908 14.3701 14.878V8.57799C14.3711 8.46518 14.3495 8.35332 14.3068 8.24893C14.2641 8.14453 14.201 8.04968 14.1212 7.96992C14.0414 7.89015 13.9466 7.82706 13.8422 7.78432C13.7378 7.74158 13.6259 7.72005 13.5131 7.72099H7.23814Z"></path></svg>
                                 </div>
                                </div> 
                                    <div style="  border-radius: 5px;padding: 6px 10px; width: 430px;height: 100px; overflow-y: auto;overflow-x: hidden; background-color:rgba(0, 0, 0, 0.05)">
                                    <span style="color: #666666;font-size: 14px;    font-family: PingFang SC,system-ui,Avenir,Helvetica,Arial,sans-serif;"  >{{ SharePic.negativePrompt }}</span>
                                </div>

                                <div  style="">
                                   <div style="margin-bottom: 10px;margin-top: 10px;"><span style="color: #222222;font-size: 14px;font-weight: 600;">模型</span> <span  style="color: #999;font-size: 14px;font-weight: 600;">Model</span></div> 
                                   <div style=" border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;" > <span style="color: #666666;font-size: 14px; ">{{ SharePic.sdModelCheckpoint }}  </span></div>
                                </div>

                                <!-- <div style="display: flex;margin-top: 23px;">
                                   <div> <span style="color: #222222;font-size: 14px;font-weight: 600;">采样</span> <span  style="color: #999;font-size: 14px;font-weight: 600;">Sampler</span></div>
                                  <div style="margin-left: 7px;">  <span style="color: #666666;font-size: 14px;  border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;">{{ SharePic.samplerName }}  </span>  </div>
                                </div>
                                
                                <el-row style="margin-top: 23px;">
                                    <el-col :span="12">
                                <div style="display: flex;">
                                  <div>  <span style="color: #222222;font-size: 14px;font-weight: 600;">提示词相关性</span> <span  style="color: #999;font-size: 14px;">CFG</span> </div>
                                  <div style="margin-left: 7px;"> <span style="color: #666666;font-size: 14px;  border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;">{{ SharePic.cfgScale }}  </span> </div>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div style="display: flex;">
                                   <div> <span style="color: #222222;font-size: 14px;font-weight: 600;">步数</span> <span  style="color: #999;font-size: 14px;font-weight: 600;">steps</span>  </div>
                                   <div style="margin-left: 7px;"> <span style="color: #666666;font-size: 14px;  border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;">{{ SharePic.steps }}  </span>   </div>
                                </div>
                            </el-col>
                                 </el-row>    -->
                                <el-row style="margin-top: 23px;">
                                    <el-col :span="12">
                                <div style="display: flex; ">
                                   <div> <span style="color: #222222;font-size: 14px;font-weight: 600;">高度</span> <span  style="color: #999;font-size: 14px;font-weight: 600;">Height</span> </div>
                                   <div style="margin-left: 7px;" > <span style="color: #666666;font-size: 14px;  border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;">{{ SharePic.height }}  </span>  </div>
                                </div>
                            </el-col>
                            <el-col :span=12>
                                <div style="display: flex;">
                                    <div><span style="color: #222222;font-size: 14px;font-weight: 600;">宽度</span> <span  style="color: #999;font-size: 14px;">Width</span> </div>
                                    <div style="margin-left: 7px;"><span style="color: #666666;font-size: 14px;  border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;">{{ SharePic.width }}  </span> </div>
                                </div>
                            </el-col>
                            </el-row>

                                <!-- <div style="display: flex; margin-top: 23px;">
                                   <div> <span style="color: #222222;font-size: 14px;font-weight: 600;">随机种子</span> <span  style="color: #999;font-size:14px;font-weight: 600;">seed</span> </div>
                                   <div > <span style="color: #666666;font-size: 14px;  border-radius: 5px;padding: 6px 10px; background-color:rgba(0, 0, 0, 0.05) ;">{{ SharePic.seed }}  </span>  </div>
                                </div> -->
             </div>
             <div v-if="SharePic.type==2">该图片类型为“艺术二维码”，暂不支持查看生成参数</div>
             <div v-if="SharePic.type==3">该图片类型为“风格转绘”，暂不支持查看生成参数</div>
             <div v-if="SharePic.type==4">该图片类型为“光影字”，暂不支持查看生成参数</div>

        </el-dialog>

<!-- 评论弹框 -->
        <el-dialog title="🌙评论"  :visible.sync="dialogVisible_comment" width="31%" >
            <span style="line-height: 24px;font-weight: 600;font-size: 16px;" v-if="commentsList.length==0">还没有评论</span>
            <div v-for="(item,index) in commentsList" :key="index" style="margin-bottom: 10px;">
                <el-row>
                    <el-col :span="2">
                        <el-avatar :src="item.picture" size="medium"></el-avatar>
                    </el-col>
                    <el-col :span="22">
                        <div><span style="margin-right: 8px;font-weight: 600; color: #000000D9;">{{ item.nickname }}</span><span style="color: 00000073;font-size: 12px;">{{ item.time }}</span> </div>
                        <div  style="color: #000000D9;font-size: 14px">{{item.content }}</div>
                    </el-col>
                </el-row>
            </div>

            <el-row style="margin-top: 20px;">
                <el-col :span="2">
                    <el-avatar :src="userIfo.picture" size="medium"></el-avatar>
                </el-col >

                <el-col style="margin-top: 5px;" :span="22">
                    <el-input
                        type="textarea"
                        autosize
                        maxlength="100"
                        show-word-limit
                        placeholder="哎呦，不错哦，发条评论吧"
                        v-model="comment">
                    </el-input>
                </el-col>
            </el-row>
            <div style="width: 100%;text-align: right;" >
                <div>
                    <el-button type="primary" @click="commitComment">评论</el-button>
                </div>
            </div>
            
        </el-dialog>
  

        <div ref="masonry" class="masonry">
            <div v-for="(item, index) in allSharePic" :key="index" class="masonry-item">
                <img :src="item.url" :alt="item.nickname" class="img" lazy @load="onImageLoad">
                <div class="info-overlay" style=" color:hsla(0,0%,100%,.8);   font-weight: 500;font-size: 15px;width: 100%;">
                    <el-row>
                        <el-col :span="12">
                            <el-avatar :src="item.picture" size="small"></el-avatar>
                            <span  style="margin-left: 5px;user-select: none;">{{ item.nickname }}</span>
                        </el-col>
                        <el-col :span="5">
                            <el-button @click="store(item)" :icon="item.isCollected==true? 'el-icon-star-on' : 'el-icon-star-off'" type="info" circle :class="{'custom-collected-icon': item.isCollected === 1}" 
                        style="font-size: 20px;padding:5px ;margin-top:0px;color: #ffd000;"></el-button>
                        <span style="margin-left: 5px">{{ item.store }}</span>
                    </el-col>

                        <el-col :span="7">
                            <el-button @click="showComments(item.picId,item.id)" style="background-color: transparent;color:hsla(0,0%,100%,.8) ;border: 0px;"> 
                                <svg preserveAspectRatio="xMidYMid meet" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="none" viewBox="0 0 24 24"><!--?lit$399099284$--><g><path data-follow-fill="currentColor" d="M1 7a5 5 0 0 1 5-5h12a5 5 0 0 1 5 5v8a5 5 0 0 1-5 5h-6.264l-4.789 2.394A1 1 0 0 1 5.5 21.5v-1.525A5 5 0 0 1 1 15V7Zm5-3a3 3 0 0 0-3 3v8a3 3 0 0 0 3 3h.5a1 1 0 0 1 1 1v.882l3.553-1.776A.999.999 0 0 1 11.5 18H18a3 3 0 0 0 3-3V7a3 3 0 0 0-3-3H6Z" fill="currentColor" fill-opacity=".85"></path></g></svg>
                                评论
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
                <el-button @click="showPicMes(item)" class="param-button" style="background-color: transparent;color:hsla(0,0%,100%,.8) ;border: 0px;"> <el-icon name="view"
                        class="view-icon"></el-icon> 图片参数
                </el-button>

                 <!-- <el-button @click="store(item)" :icon="item.isCollected==true? 'el-icon-star-on' : 'el-icon-star-off'" type="info" circle :class="{'custom-collected-icon': item.isCollected === 1}" 
                        style="font-size: 20px;padding:5px;margin-right: 320px;margin-top:0px;color: #ffd000;"></el-button> -->
               
            </div>
        </div>

        <!-- 登录提示 -->
         <el-dialog
             title="🌙提示"
              :visible.sync="dialogVisible_gotoLogin"
             width="30%">
                 <span style="font-size: 15px; color: #303133;">请先登录</span>
                 <span slot="footer" class="dialog-footer">
                 <el-button type="primary" @click="goToLogin">去登录</el-button>
             </span>
         </el-dialog>

    </div>
</template>

<script>
import request from '@/utils/request';
import { Avatar } from 'element-ui';
import router from '@/router'
import Masonry from 'masonry-layout';
export default {
    props:['isLogined','userIfo'],
    //从父路由中注入webSocket
    inject: ['getSocket'],

    components: {
        [Avatar.name]: Avatar,
    },
    data: function () {
        return {
            allSharePic: [],
            SharePic:{},
            dialogVisible:false,
            loadedImagesCount: 0, // 新增计数器
            totalImagesCount: 0, // 新增图片总数

            pageNum:1,
            pageSize:20,
            isFetching: false, // 是否正在获取数据
            allImagesLoaded: false, // 所有图片是否已加载
            dialogVisible_gotoLogin:false,
            dialogVisible_comment:false,
            commentsList:[],
            comment:'',
            picId:null,
            hostId:null,
        };
    },

    methods: {
        //发送评论
        commitComment(){
            request.get("/user/comment",{
                params:{
                    content:this.comment,
                    picId:this.picId,
                    hostId:this.hostId
                }
            }).then(res=>{           
                if(res.code==0){
                    this.$message.success("评论成功")
                    this.commentsList.push({
                        'content':this.comment,
                        'nickname':this.userIfo.nickname,
                        'picture':this.userIfo.picture  ,
                        'time':new Date()
                    })
                    this.comment=''  
                    
                }
            })
            // this.commentsList=this.commentsList
        },

        showComments(picId,id){
            if(this.isLogined==false){
                this.dialogVisible_gotoLogin=true
                return
            }
            this.dialogVisible_comment=true
            this.getCommentsList(picId);
            this.picId=picId
            this.hostId=id
            this.comment=''
        },
        
        getCommentsList(picId){
            console.log(picId)
            request.get("/user/getComment",{
                params:{
                    picId:picId
                }
            }).then(res => {
                console.log(res.data)
                this.commentsList=res.data
                console.log(this.commentsList)
            })

        },

        copyText(num) {
      const textArea = document.createElement("textarea");
     
      textArea.value = num===1? this.SharePic.prompt : this.SharePic.negativePrompt
    
      document.body.appendChild(textArea);
      textArea.select();
      document.execCommand("copy");
      document.body.removeChild(textArea);
      this.$message.success("复制成功")
    },

        onImageLoad() {
        this.loadedImagesCount++;
        if (this.loadedImagesCount === this.totalImagesCount) {
            this.initMasonry();
        }
    },
        showPicMes(item){
            if(this.isLogined==false){
                this.dialogVisible_gotoLogin=true
                return
            }
            if(item.isCollected==true){
            this.SharePic=item;
            this.dialogVisible=true;}
            else{
                this.$message.error("请收藏后再查看")
            }

        },
        initMasonry() {
            this.$nextTick(() => {
                new Masonry(this.$refs.masonry, {
                    itemSelector: '.masonry-item',
                    columnWidth: '.masonry-item',
                    percentPosition: true
                });
            });
        },

        getSharePic() {
            if(this.isFetching||this.allImagesLoaded){
                return;
            }
            this.isFetching=true
            request.get("/user/getAllSharePic",{
                params:{
                    pageNum:this.pageNum,
                    pageSize:this.pageSize
                }
            }).then(res => {
                if (res != null) {
                    if(res.data.length>0){
                    this.allSharePic.push(...res.data) ;
                    this.pageNum++; // 增加页码
                    this.totalImagesCount = this.allSharePic.length; // 设置总图片数
                    this.initMasonry();
                    }
                    else{
                        this.allImagesLoaded=true
                    }
                }
            }).finally(()=>{
                this.isFetching=false;
            })
        },


        store(item) {
            if(this.isLogined==false){
                this.dialogVisible_gotoLogin=true
                return
            }

            request.get("/user/storePic", {
                params: {
                    PicId: item.picId,
                    userId:item.id
                }
            }).then(res => {
                if(res.code==0){
                    item.isCollected=item.isCollected===true? false : true;
                    if(item.isCollected==true){
                        item.store++
                        this.$message.success("收藏成功,已保存至'我的'里");
                        // 向WebSocket发送收藏消息
                        const socket = this.getSocket();
                        if (socket && socket.readyState === WebSocket.OPEN) {
                            socket.send(JSON.stringify({
                            to: item.id, // 图片发布者的用户名
                            pic_id: item.picId // 收藏的图片ID    
                    }));
                }}
                    else this.$message.success("已取消收藏");
            }})
        },

        handleScroll() {
            const scrollY = this.$refs.masonry.scrollTop;
            const visible = this.$refs.masonry.clientHeight;
            const contentHeight = this.$refs.masonry.scrollHeight;
            const bottomOfWindow = scrollY + visible >= contentHeight - 100; // 滚动到底部前 100px

            if (bottomOfWindow) {
                this.getSharePic();
            }
        },

        goToLogin(){
      this.dialogVisible_gotoLogin=false;
      router.push("/login")
    },
        },


    mounted() {
        this.$nextTick(() => {
            this.initMasonry();
            // 绑定滚动事件监听器到 .masonry 容器
            const masonryContainer = this.$refs.masonry;
            if (masonryContainer) {
                masonryContainer.addEventListener('scroll', this.handleScroll);
            }
        });
    },

    beforeDestroy() {

       // 移除 .masonry 容器上的滚动事件监听器
       const masonryContainer = this.$refs.masonry;
        if (masonryContainer) {
            masonryContainer.removeEventListener('scroll', this.handleScroll);
        }
    },

    created() {
        this.getSharePic();
        // this.getStorePic();
        this.$message.success("若图片叠层，请刷新网页")
    },
}

</script>
  

<style scoped>
.masonry {
    column-count: 4;
    column-gap: 1em;
    position: relative;
    max-height: 87.1vh;
    overflow-y: auto;
    overflow-x: hidden;
}


.masonry-item {
    margin-bottom: 1em;
    break-inside: avoid;
    width: 400px;
    /* 防止在元素内部断行 */
    margin-bottom: 10px;
    /* 调整底部外边距 */
    margin-right: 25px;
    /* 右侧外边距 */
    width: calc(25% - 25px);
    /* 调整宽度以适应外边距 */
    break-inside: avoid;
    /* 防止在元素内部断行 */
}

.img {
    max-width: 100%;
    width: 100%;
    height: auto;
    display: block;
    /* 确保图片不会有额外的空间 */
    border-radius: 15px;
    transition: opacity 0.3s ease-in-out;
}

.info-overlay,
.param-overlay {
    position: absolute;
    visibility: hidden;
    opacity: 0;
    transition: visibility 0s, opacity 0.5s linear;
    z-index: 1000;
}

.masonry-item:hover .info-overlay {
    visibility: visible;
    opacity: 1;
    top: 10px;
    left: 10px;
    /* 可以添加更多样式 */
}

.param-button {
    display: none;
    position: absolute;
    bottom: 10px;
    right: 10px;
    /* 可以添加更多样式 */
}
.param-button-2 {
    display: none;
    position: absolute;
    bottom: 10px;
    right: 30px;
    /* 可以添加更多样式 */
}

.masonry-item:hover .param-button {
    display: block;
    /* 或 inline-block，取决于您的布局需求 */
}

.param-button:hover+.param-overlay {
    visibility: visible;
    opacity: 1;
    bottom: 45px;
    /* 按钮高度 + 间距 */
    right: 10px;
    /* 可以添加更多样式 */
}

.masonry-item img:hover {
    opacity: 0.5;
}

.masonry-item::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
    /* 初始时完全透明 */
    transition: background-color 0.3s ease-in-out;
    border-radius: 15px;
}

.masonry-item:hover::before {
    background-color: rgba(0, 0, 0, 0.5);
    /* 半透明黑色 */
}

.masonry-item img:hover {
    opacity: 0.5;
}
.masonry-item:hover .param-overlay,
.param-overlay:hover {
    visibility: visible;
    opacity: 1;
}

.custom-collected-icon {
    color: yellow; /* 收藏图标的填充颜色 */
    font-size: 20px; /* 收藏图标的大小 */
}



</style>
