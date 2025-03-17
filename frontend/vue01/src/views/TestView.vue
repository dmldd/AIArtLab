<template>
  <div>
    <el-container style="height: 100%; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(48, 65, 86);height: 840px;position: relative;">

        <el-menu background-color=" rgb(48, 65, 86)" text-color="#fff" active-text-color="#ffd04b" router :default-active="menue_active">
          <div style="line-height: 60px; height: 60px;text-align: center;color: white;">
            <img src="../assets/jiandao.png" width="22px" style="position: relative; top: 5px;margin-right: 5px;">
            <b style="font-size: 19px;">格温画室</b>
          </div>
          <el-menu-item index="/home">
            <i class="el-icon-s-promotion"></i>
            <span>画室</span>
          </el-menu-item>
          <el-menu-item index="/gallery">
            <i class="el-icon-house"></i>
            <span>画廊</span>
          </el-menu-item>
          <el-menu-item index="/tools">
            <i class="el-icon-s-cooperation"></i>
            <span>工具箱</span>
          </el-menu-item>
          <el-menu-item index="/userIfo/myStore">
            <i class="el-icon-user-solid"></i>
            <span>我的</span>
          </el-menu-item>
          <el-menu-item index="/message/store">
            <i class="el-icon-s-comment"></i>
            <span>消息</span>
          </el-menu-item>

          <el-submenu v-if="userIfo.admin == 0" index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="/user">
              <i class="el-icon-s-custom"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>

        <div style="color: #909399;font-weight: 500;font-size: .75rem;  position: absolute;bottom: 60px;left: 10px; " type="info" @click="toMyInfo">qq群：904865074</div>
        <div style="color: #909399; font-weight: 500;font-size: .75rem;position: absolute;bottom: 40px;left: 10px; ">备案号：</div>
        <el-link style="font-weight: 500;font-size: .75rem;  position: absolute;bottom: 20px;left: 10px; " type="info" href="https://beian.miit.gov.cn/#/">  辽ICP备2024018058号-1</el-link>
       
      </el-aside>

      <el-container>

        <el-header style="text-align: right; font-size: 12px;border-bottom: 1px solid #ccc;line-height: 60px;display:flex; justify-content: flex-end;">
         
          <el-button v-if="!isLogined" @click="goToLogin">登录/注册</el-button>
          <!-- --------打赏----------------- -->
          <div v-if="isLogined"  style="margin-top: 5px;cursor: pointer;" @click="dialogVisible_pay=true"><i class="el-icon-tableware" style="font-size: 30px;"></i></div>

          <!-- 消息 -->
          <div>
          <el-dropdown v-if="isLogined" style="width: 70px; margin-top: 5px;">
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div   @click="toMessageStore" class="menu-item" style="display: flex;line-height: 36px;">
                  <div class="label">收到的💐</div>
                  <div v-if="userIfo.messageStore>0" class="message" style=" background-color: #fa5a57;color: white;height: 16px;width: 26px;border-radius: 8px;line-height: 16px;text-align: center;padding: 0 5px;margin-top: 10px;margin-left: 37px;">
                    <div  class="num" style="top: 0px;text-align: center;font-size: 12px;">{{userIfo.messageStore}}</div>
                  </div>
                </div>
              </el-dropdown-item>

              <el-dropdown-item>
                <div  @click="toMessageSystem" class="menu-item" style="display: flex;line-height: 36px;">
                  <div class="label">系统通知</div>
                  <div v-if="userIfo.messageSystem>0"  class="message" style=" background-color: #fa5a57;color: white;height: 16px;width: 26px;border-radius: 8px;line-height: 16px;text-align: center;padding: 0 5px;margin-top: 10px;margin-left: 40px;">
                    <div  class="num" style="top: 0px;text-align: center;font-size: 12px;">{{userIfo.messageSystem}}</div>
                  </div>
                </div>
              </el-dropdown-item>

              <el-dropdown-item>
                <div  @click="toMessageMy" class="menu-item" style="display: flex;line-height: 36px;">
                  <div class="label">我的消息</div>
                  <div v-if="userIfo.messageMy>0" class="message" style=" background-color: #fa5a57;color: white;height: 16px;width: 26px;border-radius: 8px;line-height: 16px;text-align: center;padding: 0 5px;margin-top: 10px;margin-left: 40px;">
                    <div  class="num" style="top: 0px;text-align: center;font-size: 12px;">{{userIfo.messageMy}}</div>
                  </div>
                </div>
              </el-dropdown-item>
            </el-dropdown-menu>

            <div class="message-wrapper" >
              <div class="message">
                <svg @click="toMessage" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg"
                  class="mail" style="color: #18191c;-webkit-font-smoothing: antialiased;margin-bottom: -2px;font-size: 7px;text-decoration: none;text-decoration: none;background-color: transparent;text-decoration: none;font-family: inherit;font-weight: 400;font-style: normal;line-height: 1.25;cursor: pointer;    list-style-type: none;-webkit-text-size-adjust: 100%;
    -webkit-tap-highlight-color: transparent;">
                  <path
                    d="M15.435 17.7717H4.567C2.60143 17.7717 1 16.1723 1 14.2047V5.76702C1 3.80144 2.59942 2.20001 4.567 2.20001H15.433C17.3986 2.20001 19 3.79943 19 5.76702V14.2047C19.002 16.1703 17.4006 17.7717 15.435 17.7717ZM4.567 4.00062C3.59327 4.00062 2.8006 4.79328 2.8006 5.76702V14.2047C2.8006 15.1784 3.59327 15.9711 4.567 15.9711H15.433C16.4067 15.9711 17.1994 15.1784 17.1994 14.2047V5.76702C17.1994 4.79328 16.4067 4.00062 15.433 4.00062H4.567Z"
                    fill="currentColor"></path>
                  <path
                    d="M9.99943 11.2C9.51188 11.2 9.02238 11.0667 8.59748 10.8019L8.5407 10.7635L4.3329 7.65675C3.95304 7.37731 3.88842 6.86226 4.18996 6.50976C4.48954 6.15544 5.0417 6.09699 5.4196 6.37643L9.59412 9.45943C9.84279 9.60189 10.1561 9.60189 10.4067 9.45943L14.5812 6.37643C14.9591 6.09699 15.5113 6.15544 15.8109 6.50976C16.1104 6.86409 16.0478 7.37731 15.6679 7.65675L11.4014 10.8019C10.9765 11.0667 10.487 11.2 9.99943 11.2Z"
                    fill="currentColor"></path>
                </svg>
                <!--          <img class="mail" src="./mail.png" />-->
                <div class="num" v-if="(userIfo.messageStore+userIfo.messageSystem+userIfo.messageMy)>0">{{userIfo.messageStore+userIfo.messageSystem+userIfo.messageMy}}</div>
              </div>
            </div>
            <!-- </template> -->
          </el-dropdown>
        </div>
          <!-- ----------------- -->
        <div>
          <el-dropdown v-if="isLogined" style="width: 170px; margin-top: 5px;">
            <span>
              <el-avatar :src="userIfo.picture"></el-avatar>
              <span style="user-select: none; ">{{ userIfo.nickname }}</span>
              <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item><span @click="toMy">个人信息</span></el-dropdown-item>
              <el-dropdown-item><span @click="loginOut">退出登录</span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
     
        </el-header>

        <el-main style="height: 780px;">
          <!-- 主体 -->
          <RouterView :isLogined="isLogined" :userIfo="userIfo"></RouterView>
        </el-main>
      </el-container>
    </el-container>
                     <!-- 登录提示 -->
                     <el-dialog
      title="🌙提示"
      :visible.sync="dialogVisible_gotoLogin"
      width="30%">
      <span style="font-size: 15px; color: #303133;">您尚未登录，请先登录</span>
      <span slot="footer" class="dialog-footer">
       <el-button type="primary" @click="goToLogin">去登录</el-button>
      </span>
    </el-dialog>  


<!-- 打赏 -->
    <el-dialog
      title="🌙打赏"
      :visible.sync="dialogVisible_pay"
      width="40%" style="text-align: center;" >
      <div >  <el-image :src="pay_item.image" style="width: 200px;height: 200px"></el-image></div>
      <div > <span style="font-size: 24px; font-weight: 600;line-height: 36px; color: #060607;">{{pay_item.title  }}</span></div>
      <div><span style="margin-top: 12px; font-weight: 600; line-height: 45px; font-size: 30px;color: #060607;">￥{{pay_item.price   }}</span></div>
      <div><span style=" color: #060607;font-weight: 400;font-size: 14px;line-height: 24px;">{{pay_item.welfares  }}</span></div>
       <div style="margin-top: 16px"><el-button type="info" icon="el-icon-refresh"   @click="Change_pay_item">换一个</el-button></div>
      
      <div style="margin-top: 48px"><el-button @click="pay" type="primary" style="width: 256px; height: 44px; border-radius: 8px;  min-width: 64px; line-height: 1.75; font-size: 0.875rem; font-weight: 500;color: rgb(255, 255, 255);">去支付</el-button></div>
    </el-dialog>

    <el-dialog width="40%" :visible.sync="dialogVisible_pay_QRcode" style="text-align: center">
      <div style="font-size: 18px;font-weight:600;line-height:28px;color: #060607;">微信扫码付款</div>
      <div style="font-size: 18px;font-weight:600;line-height:28px;color: #060607;">￥{{pay_item.price }}</div>
     <div  style="border-radius: 8px;background-color: #f3f5fc;padding: 18px;"><el-image style="height:220px;width: 220px" :src="pay_QRcode"></el-image></div> 
    </el-dialog>

  </div>
</template>
  
<script>

import request from '@/utils/request';
import { RouterView } from 'vue-router';
import router from '@/router'
export default {
  data: function () {
    return {
      //打赏
      dialogVisible_pay:false,
      dialogVisible_pay_QRcode:false,
      pay_QRcode:null,
      pay_item:{
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/coffee_v2.png",
        title:"请格温小姐喝咖啡",
        price:"1.9",
        // welfares:"赠送300剪刀",
        productType:0,
        credit:1
      },

      pay_items:[
      {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/coffee_v2.png",
        title:"请格温小姐喝咖啡",
        price:"1.9",
        // welfares:"赠送300剪刀",
        productType:0,
        credit:1
      },
        {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/flower.png",
        title:"送格温小姐一朵花",
        price:"5.20",
        // welfares:"每日赠送300剪刀 解锁全部lora、大模型 × 7 天",
        productType:1,
        credit:1
      },
        {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/eat.png",
        title:"请格温小姐吃顿饭",
        price:"9.9",
        // welfares:"每日赠送300剪刀 解锁全部lora、大模型 × 30 天",
        productType:2,
        credit:1
      },
      {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/moonshot.png",
        title:"和格温小姐一起登月",
        price:"19.9",
        // welfares:"每日赠送600剪刀 解锁全部lora、大模型 × 30 天",
        productType:3,
        credit:1
      }
      ],
      pay_item_currentNumber:0,


      userIfo: {},
      socket: null,
      isLogined:false,
      dialogVisible_gotoLogin:false,
      menue_active:'/'

    }
  },

  methods: {
    pay(){
      request.get("/pay", {
        params:{
        productType:this.pay_item.productType,
        price:this.pay_item.price,
        credit:1
        }
      }).then(res => {
        if(res.code==1){
          this.$message.error("支付接口获取失败，请稍后再尝试")
        }
        else{
          this.pay_QRcode=res.data
          // this.dialogVisible_pay=false
          this.dialogVisible_pay_QRcode=true
        }
    })},

    Change_pay_item(){
      if(this.pay_item_currentNumber!=3){
        this.pay_item_currentNumber++
      }
      else{
        this.pay_item_currentNumber=0
      }
      this.pay_item=this.pay_items[this.pay_item_currentNumber]
    },


    handleMessageClick(){
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true;
        return
      }
      if (this.$route.path == "/message" ) {
        return
      }
      this.$router.push("/message" )
    },
    goToLogin(){
            this.dialogVisible_gotoLogin=false;
            router.push("/login")
        },
     
    handleMyClick(){
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true;
        return
      }
      if (this.$route.path == "/userIfo/myStore") {
        return
      }
      this.$router.push("/userIfo")
    },


        
    toMyInfo(){
      this.$router.push("/myInfo")
    },

    toMy() {
      if (this.$route.path == "/userIfo/myStore") {
        return
      }
      this.$router.push("/userIfo")
    },
    //请求数据
    loginOut() {
      localStorage.removeItem("token");
      // this.$router.push("/login")
      this.$router.push("/login")
    },

// 跳转到消息页面    
    toMessage(){
      if (this.$route.path == "/message/store") {
        //刷新
        this.$router.go(0);
        return
      }
      this.$router.push("/message/store")
      this.userIfo.messageStore=0
    },
// 跳转到消息页面
    toMessageStore(){
      if (this.$route.path == "/message/store") {
        //刷新
        this.$router.go(0);
        return
      }
      this.$router.push("/message/store")
      this.userIfo.messageStore=0
    },
// 跳转到消息页面
    toMessageSystem(){
      if (this.$route.path == "/message/system") {
        //刷新
        this.$router.go(0);
        return
      }
      this.$router.push("/message/system")
      this.userIfo.messageSystem=0
    },

    toMessageMy(){
      if (this.$route.path == "/message/my") {
        //刷新
        this.$router.go(0);
        return
      }
      this.$router.push("/message/my")
      this.userIfo.messageMy=0
    },
// 建立websocket连接
    connectWebSocket() {
      this.socket = new WebSocket('ws://58.87.103.114:7651/imserver/'+this.userIfo.id);
      this.socket.onopen = function (event) {
        console.log('WebSocket连接已打开', event);
      };

      this.socket.onmessage = (event) => {
        let message = JSON.parse(event.data);
        console.log(message);
        this.userIfo.messageStore++;
        // 在这里处理从服务器收到的消息
        // 例如：更新UI，显示通知等
      };

      this.socket.onclose = function (event) {
        console.log('WebSocket连接已关闭', event);
      };

      this.socket.onerror = function (error) {
        console.error('WebSocket出现错误', error);
      };
    },

    startHeartbeat() {
    this.heartbeatInterval = setInterval(() => {
      if (this.socket.readyState === WebSocket.OPEN) {
        this.socket.send('1');
      }
    }, 60000*5); // 每5min发送一次心跳
  },

  stopHeartbeat() {
    if (this.heartbeatInterval) {
      clearInterval(this.heartbeatInterval);
    }
  },

    async getUserInfo(){
      await  request.get("/user/getUserIfo").then(res => {
        if(res==null){
          //没登录或者登录失效情况
          this.isLogined=false;
        }
      if (res != null) {
        console.log("登录成功")
        //登录情况
        this.isLogined=true
        this.userIfo = res.data
      }
    })
    }
  },

  // provide生命周期函数
  provide() {
    return {
      getSocket: () => this.socket
    };
  },

  //mounted 生命周期函数
  mounted() {
    // request.get("/user/getMessageStore").then(res=>{
    //   if(res!=null){
    //     console.log(res)
    //   }
    // })
  },

  // beforeDestroy生命周期函数
  beforeDestroy() {
    this.stopHeartbeat();
    if (this.socket) {
      this.socket.close();
    }
  },

  async created() {
    this.menue_active=this.$route.path
    await this.getUserInfo();
    this.connectWebSocket();
    
  },

  watch:{
  $route(to){
    this.menue_active=to.path
    console.log(to.path)
  }
},


  components: { RouterView }
}
</script>
sass-loader
<style scoped lang="scss">
.menu-list {
  display: flex;
  flex-direction: column;

  .menu-item {
    padding: 5px 10px;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .message {
      background-color: #fa5a57;
      color: white;
      height: 16px;
      width: 26px;
      border-radius: 8px;
      line-height: 16px;
      text-align: center;
      padding: 0 5px;
    }

    &:hover {
      //color: var(--el-color-primary-dark-2);
      //background-color: var(--el-color-primary-light-8);
      background-color: rgba(black, 0.1);
      cursor: pointer;
    }
  }
}

.message-wrapper {
  display: inline-block;

  .message {
    position: relative;

    .mail {
      width: 30px;
      height: 30px;
    }

    .num {
      top: 0px;
      right: -10px;
      text-align: center;
      position: absolute;
      background-color: #fa5a57;
      height: 16px;
      line-height: 16px;
      width: 26px;
      border-radius: 8px;
      color: white;
      font-size: 12px;
    }
  }
}
</style>

  