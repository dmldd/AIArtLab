<template>
    <div>
                    <!-- 注册 -->
                    <div class="form"  >
                <img src="../assets/jiandao.png" class="logo">
                <el-card>
                <el-form :model="form" status-icon :rules="rules" ref="form" label-width="70px" style="text-align: center;" >
                    <el-form-item label="账号" prop="username" >
                        <el-input  v-model="form.username"  prefix-icon="el-icon-user" placeholder="8 - 16 个字符,须为数字"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email" >
                        <el-input  v-model="form.email" prefix-icon="el-icon-message" ></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="registerPassword">
                        <el-input type="password" v-model="form.registerPassword" prefix-icon="el-icon-lock" show-password placeholder=" 8 - 16 个字符,须包含数字和字母"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="confirmPassword">
                        <el-input type="password" v-model="form.confirmPassword" prefix-icon="el-icon-lock" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" >
                        <el-input  v-model="form.code" prefix-icon="el-icon-smoking" >
                            <template #append>
                            <el-button size="small" @click="getVerificationCode" :disabled="countdown > 0"> {{ countdown > 0 ? `${countdown}秒后重试` : '发送验证码' }}</el-button>
                            </template>
                        </el-input>
                    </el-form-item>                   
                        <el-button type="primary" @click="submitForm_register"  class="registerButton">注册</el-button>                                 
                </el-form>
                <div  style="width:100%;background-color: #f8f9fa; height: 24px;margin-top: 24px; padding-left: 1.5rem; padding-right: 1.5rem; padding-top: 1rem;padding-bottom: 1rem;">
                    <span   @click="ToLogin" class="text-muted" style="user-select: none;cursor: pointer; font-size: .875rem;color: #6c757d;">返回登录</span> 
                </div>
                </el-card>
            </div> 
    </div>
</template>

<script>
import request from '@/utils/request'
export default {
    data: function () {
        var checkConfirmPassword = (rule, value, callback) => {
    if (value !== this.form.registerPassword) {
      callback(new Error('两次输入的密码不一致'));
    } else {
      callback();
    }
  };
        return {
            countdown: 0, // 倒计时秒数
            form:{
                username:'',
                password:'',
                confirmPassword:'',
                code:null,
                email:'',
                registerPassword: '', // 注册界面的密码
            },
            rules:{
                username:[
                { required: true, message: '请输入账号', trigger: 'blur' },
                { min: 9, max: 15, message: '长度在9到15个数字', trigger: 'blur' },
                { pattern: /^[0-9]+$/, message: '账号只能包含数字', trigger: 'blur' }
                ],
                password:[
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 8, max: 16, message: '密码长度应在 8 到 16 个字符之间', trigger: 'blur' },
        { pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!.%*#?&]{8,16}$/, message: '密码必须包含字母、数字', trigger: 'blur' }
    ],

    registerPassword:[
    { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 8, max: 16, message: '密码长度应在 8 到 16 个字符之间', trigger: 'blur' },
        { pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!.%*#?&]{8,16}$/, message: '密码必须包含字母、数字', trigger: 'blur' }
            ],

                email: [
                { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur'] }
                ],
                confirmPassword: [
        { validator: checkConfirmPassword, trigger: 'blur' }
      ]
            },
        };
    },
    methods: {
        ToLogin(){
            this.$router.push("/login")
        },
                // 注册
                submitForm_register(){
            if(this.form.code==null){
              return  this.$message.error("请输入验证码")
            }
            this.$refs['form'].validate(valid=>{
                if(!valid){
                    return this.$message.error("数据校验失败，请检查后再注册")
                } 
                else{
                        request.get("/user/register",{
                        params:{
                            userName:this.form.username,
                            password:this.form.registerPassword,
                            email:this.form.email,
                            code:this.form.code
                        }
                    }).then(res=>{
                        if(res){
                            //注册失败
                            if(res.code==1){
                            this.$message.error(res.message)
                        }
                            //注册成功
                            else {
                                this.$message.success("注册成功,可以登录咯");
                                this.ToLogin()
                            }
                        }
                        else this.$message.error("注册失败")
                        
                    })
                }            
            })
        },
               //获取验证码
       getVerificationCode(){
        this.$refs['form'].validate(valid=>{
                if(!valid){
                    return this.$message.error("数据校验失败，请检查后再发送")
                } 
                else{
        request.get("/user/sendEmail",{
            params:{
                userName:this.form.username,
                password:this.form.registerPassword,
                email:this.form.email,
            }
        }).then(res=>{
            if(res.code==1){
                this.$message.error(res.message)
                this.countdown=0
            }
        });
        // 清除旧的计时器
        if (this.interval) {
                clearInterval(this.interval);
            }
        this.countdown = 60;
        let interval = setInterval(() => {
        this.countdown--;

        // 当倒计时结束时，清除计时器
        if (this.countdown === 0) {
          clearInterval(interval);
        }
      }, 1000);  }            
            })
       } ,
    },
    mounted() {
        document.documentElement.style.backgroundColor = '#e8eaf2';
    },
    beforeDestroy() {
        // 可选：重置背景颜色
        document.documentElement.style.backgroundColor = null;
    }
}

</script>
  

<style scoped>
.form{
    width: 30%; 
    margin: auto;
    margin-top: 200px;
}

::v-deep .el-card__body  {
    padding-left: 0px !important;
    padding-bottom: 0px !important;
}

.logo{
    width: 100px;
    height: 100px;
    margin-left: 190px;
}


.registerButton{
    width: 250px;
}


.text-muted:hover {
    color: #3d4246 !important;
}
</style>
