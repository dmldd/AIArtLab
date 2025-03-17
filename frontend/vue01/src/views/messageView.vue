<template>
    <div>
        <el-tabs>
        <div class="total" style="margin: auto;width: 1140px;">
           
        <el-row>   
            <el-col :span="3">
                <el-card :body-style="{ padding: '0px' }">
                <div class="left" style="height:800px ;background-color: rgba(255,255,255,0.8);">
                    <div style="text-align: center;height: 62px;line-height:62px ;">
                        <i style="font-size: 18px;color: #333;" class="el-icon-s-promotion"></i>
                        <span style="color: #333;font-size: 14px;font-weight: 700;margin-left: 8px;">消息中心</span>
                    </div>
                    <div class="hover" @click="toStore" style="height: 40px;line-height: 40px;text-align: center;">
                        <span :class="{ 'highlighted': isHighlightedStore }" style="color: #6b757b;font-size: 14px;font-weight: 700;user-select: none;">收到的💐</span>
                    </div>
                    <div class="hover" @click="toSystem" style="height: 40px;line-height: 40px;text-align: center;">
                        <span :class="{ 'highlighted': isHighlightedSystem }" style="color: #6b757b;font-size: 14px;font-weight: 700;user-select: none;">系统消息</span>
                    </div>
                    <div class="hover" @click="toMy" style="height: 40px;line-height: 40px;text-align: center;">
                        <span :class="{ 'highlighted': isHighlightedMy }"  style="color: #6b757b;font-size: 14px;font-weight: 700;user-select: none;">我的消息</span>
                    </div>
                </div>
            </el-card>
            </el-col >
       

            <el-col :span="20" >
                <div class="right" style="height: 700px;background-color: rgba(255,255,255,0.5);font-size: 12px;line-height: 12px;color: #666;">
                    <el-card :body-style="{ padding: '0px' }">
                    <div class="head" style="height: 42px; background-color: #fff;line-height: 42px;font-size: 15px;color: #666;border-radius: 4px;">
                        <span style="margin-left: 16px;">{{ headWords }}</span>
                    </div>
                </el-card >
                <div class="body">
                    <RouterView></RouterView>.
                </div>
            </div>
                
            </el-col>

         </el-row>
    </div>  
</el-tabs>
    </div>
</template>

<script>

// import request from '@/utils/request';
export default {
    data: function () {
        return {
            messageStoreList:[],
            isHighlightedStore: false,
            isHighlightedSystem: false,
            isHighlightedMy: false,
            headWords:null,
        };
    },
    methods: {
        toStore(){
            if (this.$route.path == "/message/store") {
                return
             }
             this.$router.push("/message/store")
             this.isHighlightedStore=true
             this.isHighlightedSystem=false
             this.isHighlightedMy=false
             this.headWords='收到的💐'
             
        },

        toSystem(){
            if (this.$route.path == "/message/system") {
        return
    }
        this.$router.push("/message/system")
        this.isHighlightedSystem=true
        this.isHighlightedMy=false
        this.isHighlightedStore=false
        this.headWords='系统消息'

             
        },

        toMy(){
            if (this.$route.path == "/message/my") {
        return
    }
        this.$router.push("/message/my")
        this.isHighlightedMy=true
        this.isHighlightedStore=false
        this.isHighlightedSystem=false
        this.headWords='我的消息'
             
        },

        initHeadWords(){
            if(this.$route.path == "/message/store"){
                this.headWords='收到的💐'
                this.isHighlightedStore=true
                console.log("store")
            }
            else if(this.$route.path == "/message/system"){
                this.headWords='系统消息'
                this.isHighlightedSystem=true
                console.log("system")
            }else{
                this.headWords='我的消息'
                this.isHighlightedMy=true
                console.log("my")
            }
        }

    },
    mounted() {
        this.initHeadWords();

   
  },
  created(){

  }
}

</script>
  

<style>
.hover:hover span{
    color: #2faee3 !important;
}
.hover:active span{
    color: #2faee3 !important;
}
.highlighted{
    color: #2faee3 !important;
}
</style>
