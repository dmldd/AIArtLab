<template>
   <div>
        <div v-for="(item,index) in messageMyList" :key="index" style="height: 95px;border-bottom: 1px solid #e5e9ef;">
                        <el-row>
                            <el-col :span="1.7" style="">
                                <el-avatar :src="item.picture"  style="width: 46px;height: 46px;margin-top: 25.5px;"></el-avatar>
                            </el-col>
                               
                            <el-col :span="20" >
                                <div style="margin-left: 10px;margin-top: 24.5px;">
                                <div >
                                    <span style="font-weight: bold;font-size: 14px;color:rgb(33 32 32);;">{{ item.nickname }}</span>
                                    <span style="font-size: 14px;color:#505050;margin-left: 14px;">评论了我的作品</span>
                                    <span style="font-size: 12px;line-height: 22px;">  {{ item.time }}</span>
                                </div>
                                <div style="margin-top: 10px;height: 22px;font-size: 14px;line-height: 22px;color: #222;">
                                    {{ item.content }}
                                </div>
                            </div>
                            </el-col>
                            <el-col :span="2" style="line-height: 95px;">
                                <span>作品ID：</span><span style="font-size: 14px;color:rgb(33 32 32);font-weight: bold;">{{ item.picId }}</span>
                            </el-col>
                        </el-row>
                    </div>
    </div>
</template>

<script>

import request from '@/utils/request';
export default {
    data: function () {
        return {
            messageMyList:[],
        };
    },
    methods: {
        wipe(){
        request.get("/user/wipeMessageMy").then((res)=>{
            if(res!=null){
                return
            }
        })},

        getData(){
            request.get("/user/getMessageMy").then(res=>{
                this.messageMyList=res.data
    })
        }

    },
    created(){
        this.wipe();
        this.getData();
    }
}

</script>
  

<style></style>
