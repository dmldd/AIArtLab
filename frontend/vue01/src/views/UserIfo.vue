<template>
    <div>
        <!-- 头像 -->
        <div style="margin-top: 24px;text-align: center;">
            <el-avatar :size="96" :src="userIfo.picture"></el-avatar>
        </div>
        <!-- nickname -->
        <div style="text-align: center;line-height: 38px;font-weight: 600;font-size: 32px;margin-top: 18px;">
            <span style="color: var(--text-primary);"> {{ userIfo.nickname }}</span>
        </div>
        <!-- 剩余次数 -->
        <!-- <div style="text-align: center;line-height: 38px;font-weight: 600;font-size: 22px;margin-top: 18px;">
            <span>今日剪刀所剩个数:</span><span>{{ userIfo.num }}</span></div>
        <div style="text-align: center;">
            <el-button :plain="true" @click="dialogFormVisible_rule=true">查看剪刀扣除规则</el-button>
        </div> -->
        <el-dialog width="30%" title="剪刀扣除规则" :visible.sync="dialogFormVisible_rule" >
            <div style="font-size: 20px;font-weight: 500;color: #303133;">
                <div>🎮用户每日获得100剪刀</div>
                <div style="margin-top:10px ;">🚀Prompt提取器:1剪刀/次 </div> 
                <div style="margin-top:10px ;">📕文生图:2剪刀/次</div>
                <div style="margin-top:10px ;">🎁风格转绘:4剪刀/次</div>
                <div style="margin-top:10px ;">🍱艺术二维码:8剪刀/次</div>
                <div style="margin-top:10px ;margin-bottom: 15px;">🐨光影字:8剪刀/次</div> 
                <div>打赏可获得更多剪刀哦 <el-link type="success" @click="dialogVisible_pay=true"> 点击打赏</el-link></div>
            </div>
        </el-dialog>
           
        <!-- 编辑资料 -->
        <div style="margin-top: 18px;text-align: center;">
            <el-button tupe="primary" @click="editButton">编辑资料</el-button>
        </div>
        <div style="margin-top: 8px;text-align: center;">
            <el-button tupe="primary" @click="changePassword">修改密码</el-button>
        </div>
        <!-- 编辑资料弹框 -->
        <el-dialog title="编辑资料" :visible.sync="dialogFormVisible_add" style="width: 1000px;margin-left: 300px;">
            <div style="margin-bottom: 10px;"><span style="font-size: large;">头像</span><span>(点击头像进行修改)</span></div>
            <input id="fileInput" type="file" @change="onFileChange" accept="image/*" style="display: none;">
            <div @click="triggerFileInput" style="cursor: pointer;">
                <el-avatar size="large" :src="avatar"></el-avatar>
            </div>
            <div style="margin-top: 20px;margin-bottom: 10px;"><span style="font-size: large;">昵称</span></div>
            <el-input v-model="nickname" style="" maxlength="6"></el-input>
            <el-button type="primary" @click="submitEdit" style="margin-top: 15px;">保存</el-button>
        </el-dialog>
        <!-- 修改密码弹框 -->
        <el-dialog title="修改密码" :visible.sync="dialogFormVisible_password" style="width: 1000px;margin-left: 300px;">
            <span style="font-size: medium;">旧密码</span> <el-input v-model="password"></el-input>
            <span style="font-size: medium;">新密码</span> <el-input v-model="NewPassword"></el-input>
            <span style="font-size: medium;">确认密码</span> <el-input v-model="ReNewPassword"></el-input>
            <el-button type="primary" @click="submitPassword" style="margin-top: 15px;">提交</el-button>
        </el-dialog>

        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="我的收藏" name="first">
            </el-tab-pane>
            <el-tab-pane label="我的发表" name="second">
            </el-tab-pane>

        </el-tabs>
        <RouterView></RouterView>

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
import { Avatar } from 'element-ui';

import Masonry from 'masonry-layout';
export default {
    components: {
        [Avatar.name]: Avatar,

    },
    data: function () {
        return {
            //pay
      dialogVisible_pay:false,
      dialogVisible_pay_QRcode:false,
      pay_QRcode:null,
      pay_item:{
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/coffee_v2.png",
        title:"请格温小姐喝咖啡",
        price:"1.9",
        welfares:"赠送300剪刀",
        productType:0,
        credit:1
      },

      pay_items:[
      {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/coffee_v2.png",
        title:"请格温小姐喝咖啡",
        price:"1.9",
        welfares:"赠送300剪刀",
        productType:0,
        credit:1
      },
        {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/flower.png",
        title:"送格温小姐一朵花",
        price:"5.20",
        welfares:"每日赠送300剪刀 解锁全部lora、大模型 × 7 天",
        productType:1,
        credit:1
      },
        {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/eat.png",
        title:"请格温小姐吃顿饭",
        price:"9.9",
        welfares:"每日赠送300剪刀 解锁全部lora、大模型 × 30 天",
        productType:2,
        credit:1
      },
      {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/moonshot.png",
        title:"和格温小姐一起登月",
        price:"19.9",
        welfares:"每日赠送600剪刀 解锁全部lora、大模型 × 30 天",
        productType:3,
        credit:1
      }
      ],
      pay_item_currentNumber:0,


            dialogFormVisible_rule:false,
            password: '',
            NewPassword: '',
            ReNewPassword: '',
            dialogFormVisible_password: false,
            activeName: 'first',
            userIfo: {},
            avatar: '',
            nickname: '',
            // email:'',
            dialogFormVisible_add: false,
            selectedFile: null,
            previewImage: null,
            sharePic: [],
            storePic: [],
            tabFlag: 'first'

        };
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

        initTabFlag() {
            if (this.$route.path == "/userIfo/myShare") {
                this.tabFlag = "second";
                this.activeName = "second";
            }
            else {
                this.tabFlag = "first"
                this.activeName = "first";
            }
        },
        submitPassword() {
            request.get("/user/passwordChange", {
                params: {
                    password: this.password,
                    NewPassword: this.NewPassword,
                    ReNewPassword: this.ReNewPassword,

                }
            }).then(res => {
                if (res != null) {
                    if (res.code == 0) {
                        this.$message.success("密码修改成功")
                        this.dialogFormVisible_password = false
                    } else this.$message.error(res.message)
                }
            })
        },
        changePassword() {
            this.dialogFormVisible_password = true
        },

        handleClick(tab) {

            if (tab.name === 'second' && this.tabFlag == 'first') {
                this.tabFlag = 'second'
                this.$router.push('/userIfo/myShare');
            }
            else if (tab.name === 'first' && this.tabFlag == 'second') {
                this.tabFlag = 'first'
                this.$router.push('/userIfo/myStore');
            }
            else return
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

        // 获取用户信息
        load() {
            request.get("/user/getUserIfo").then(res => {
                if (res != null) {
                    this.userIfo = res.data
                }
            });
            this.$message.success("若图片叠层，请刷新网页")
        },

        triggerFileInput() {
            document.getElementById('fileInput').click();
        },

        editButton() {
            this.dialogFormVisible_add = true
            this.avatar = this.userIfo.picture
            this.nickname = this.userIfo.nickname
        },

        onFileChange(e) {
            const file = e.target.files[0];
            if (!file) return;

            // 创建 FileReader 对象来读取文件
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) => {
                const img = new Image();
                img.src = e.target.result;

                img.onload = () => {
                    // 创建 canvas 元素并设置尺寸
                    const canvas = document.createElement('canvas');
                    const ctx = canvas.getContext('2d');
                    canvas.width = img.width;
                    canvas.height = img.height;

                    // 绘制图片到 canvas
                    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

                    // 压缩图片
                    canvas.toBlob((blob) => {
                        // 创建一个新的文件对象
                        const compressedFile = new File([blob], file.name, {
                            type: 'image/jpeg',
                            lastModified: Date.now()
                        });
                        this.selectedFile = compressedFile;
                        this.avatar = URL.createObjectURL(compressedFile);
                    }, 'image/jpeg', 0.2);
                };
            };
        },

        submitEdit() {
            if (this.selectedFile != null) {
                const formData = new FormData();
                formData.append('file', this.selectedFile);
                request.post("/user/updateAvatar", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
            }
            request.get("/user/updateNick", {
                params: { nickname: this.nickname }
            }
            ).then(res => {
                if (res != null) {
                    this.$message.success("保存成功");
                    this.load();
                    this.dialogFormVisible_add = false;
                }
            })
        },

    },

    created() {
        this.load();
        // this.getSharePic();
        // this.getStorePic();
    },

    mounted() {
        this.initTabFlag();
    }
}

</script>
  


<style scoped>
.masonry {
    column-count: 4;
    column-gap: 1em;
    position: relative;
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

img {
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
}</style>