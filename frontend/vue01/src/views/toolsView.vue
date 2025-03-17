<template>
    <div>
        <!-- <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">通道：</span> -->
                    <!-- <el-select v-model="cannel" 
                    style="width: 249.25px;">
                    <el-option v-for="item in cannels" :key="item.value" :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select> -->
        <el-tabs v-model="activeTab">
            <!-- <el-tab-pane label="我擦擦擦" name="first">
                <el-row>
                    <el-col :span="12">

                        <div ref="divStyle" style="height: 500px;width: 500px; outline:none;align-items: center;justify-content: center; background-color: rgb(235, 238, 245);">
                            <canvas id="canvas" ref="vueCanvas" @mousedown="startDrawing" @mousemove="draw"
                                @mouseup="stopDrawing">
                            </canvas>
                        </div>
                        <canvas id="canvas2" ref="Canvas2" style="display: none;"></canvas>

                        <el-upload action="#" :on-change="imagetocanvas" :auto-upload="false" :show-file-list="false" accept="image/*">
                            <el-button type="primary">选择图片<i class="el-icon-picture el-icon--right"></i></el-button>
                        </el-upload>
                        <el-button type="danger" ref="clearallmask" @click="clearMask">清除遮罩<i class="el-icon-delete el-icon--right"></i>
                        </el-button>
                        <el-button type="primary" @click="undoSave">撤销</el-button>
                        <el-button type="primary" @click="exportMaskImage">保存</el-button>
                        <div class="">画笔宽度</div>
                        <el-slider v-model="value" :step="5" :min="5"></el-slider>
                    </el-col>

                  <el-col :span="10">
                  <div
                    style="height:460px ;width: 460px; background-color: #EBEEF5;border-radius: 10px;text-align: center;">
                    <div v-show="isShowBar_Lg"
                      style="height: 20px;width: 450px;background-color: #424C5B; text-align: left;line-height: 20px;border-radius: 10px;color: #dcdfe6;font-size: 14px;padding-left: 10px;">
                      {{ message_Lg }}
                    </div>
                    <el-image :src="Lg_result" fit="contain" style="height: 400px;width: 400px;margin-top: 30px;">
                      <div slot="error" class="image-slot" style="margin-top: 50px;">
                        <i class="el-icon-picture-outline"></i>
                      </div>
                    </el-image>
                  </div>
                  <div style="width: 300px;margin-top: 10px;">
                    <a :href="Lg_result" v-if="Lg_result !== ''" download="Peaky_Tr.png" target="_blank">下载图片</a>
                  </div>
                </el-col>

                </el-row>
            </el-tab-pane> -->
            <el-tab-pane label="Prompt提取器" name="second">
                <el-upload style="margin-left: auto;" :action="AN_neverUpload()" :before-upload="AN_beforeUpload"
                    :on-change="AN_handleChange" :file-list="AN_fileList" :show-file-list="false" draggable
                    @drop.native.prevent="AN_onDrop" @dragover.native.prevent accept="image/*">
                    <div class="upload-container">
                        <div class="el-upload-dragger">
                            <img v-if="AN_imageSrc" :src="AN_imageSrc" alt="预览图" class="preview-image">
                            <div v-if="!AN_imageSrc">将图片拖到此处，或<em>点击上传</em></div>
                            <div v-if="!AN_imageSrc"><i class="el-icon-upload"></i></div>
                        </div>
                    </div>
                </el-upload>
                <div style="text-align: center;margin-left: auto;">
                    <div v-show="isShowBar_Tagger"
                        style="height: 20px;width: 450px;background-color: #424C5B; text-align: left;line-height: 20px;border-radius: 10px;color: #dcdfe6;font-size: 14px;padding-left: 10px;">
                        {{ message_Tagger }}
                    </div>
                </div>
                <!-- 提取按钮 -->
                <el-button @click="generateTagger" style="margin-left: auto;margin-top: 5px; color: rgb(231, 97, 0);width: 300px; height: 60px;   background: linear-gradient(rgb(255, 208, 137), rgb(255, 181, 69));
                      border-radius: 8px;">立即提取</el-button>
                <!-- 显示框 -->
                <div v-show="Tagger != null"
                    style="display: block;width: 500px; ;border: 1px dashed #d9d9d9; padding: 8px 16px ;color: rgb(79, 79, 79);font-size: 14px;">
                    {{ Tagger }}
                </div>
            </el-tab-pane>
            <!-- <el-tab-pane label="照片物体擦除" name="third">

            </el-tab-pane> -->
        </el-tabs>

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
import router from '@/router'
export default {
    props:['isLogined'],

    data: function () {
        return {
            isShowBar_Lg:false,
            message_Lg:'',
            Lg_result:'',
            isGenerating_Lg:false,
            Lg_requestId:'',
            Lg_queueNum:'',
            cannel:1,
            cannels:[
        {
        value: 1,
        label: '通道1'
      },{
        value: 2,
        label: '通道2'
      }
      ],
            // 擦除物体实体
            cleanEntity:
            {
                init_images:[],
                mask: '',
                prompt: "(masterpiece), (best quality),",
                negative_prompt: "EasyNegative, bad-hands-5, (worst quality, low quality:1.4),, lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry,artist name",
                denoising_strength: 1,
                mask_blur: 4,
                seed: -1,
                sampler_name: "DPM++ 2M Karras",
                batch_size: 1,
                n_iter: 1,
                steps: 20,
                cfg_scale: 7,
                width:null ,
                height:null ,
                sampler_index: "DPM++ 2M Karras",
                override_settings: {
                    sd_model_checkpoint: "sd-v1-5-inpainting",
                    sd_vae: "[StabilityAI]vae-ft-mse-840000-ema-pruned.safetensors",
                },
            },
            isDrawing: false,
            value: 20,
            img_percentage: 1,
            full_percentage: 1,
            magnifier: "el-icon-zoom-in",
            fristtime: true,
            undoList: [], // 用于撤销的操作历史
            redoList: [], // 用于重做的操作历史
            currentCanvasState: null, // 当前Canvas状态
            activeTab: 'second',
            //获取tagger请求实体
            getTagger: {
                image: '',
                model: 'wd-v1-4-moat-tagger.v2',
                threshold: '0.30',
                queue: '',
                name_in_queue: ''
            },
            isTaggering: false,
            isShowBar_Tagger: false,
            message_Tagger: null,
            Tagger: null,
            AN_fileList: [],
            AN_imageSrc: null,
            dialogVisible_gotoLogin:false,
        };
    },

    created() {
        this.wipe();
    },

    methods: {


    // -----生成光影字
    async generateLightWord() {

      if (this.cleanEntity.init_images[0] == null) {
        return this.$message.error("请先选择图片")
      }
      // P操作
      if (this.isGenerating_Lg==true) {
        return this.$message.error("图片正在生成中，请勿重复点击")
      }
      this.isGenerating_Lg = true
      this.isShowBar_Lg = true
      this.message_Lg = "请求正在处理...";

      //判断生成次数
      let isNumOk = true;
      await request.get("/user/getNum").then(res => {
        if (res != null) {
          if (res.data < 3) {
            isNumOk = false
            this.$message.error("剪刀数量不足")
          }
        }
      })
      if (isNumOk == false) {
        this.isShowBar_Lg = false
        return this.isGenerating_Lg = false
      }

      await this.exportMaskImage();
      request.post("/user/imgToImg", this.cleanEntity,{
        params:{
          cannel:this.cannel
        }
      }).then(res => {
        if (res != null) {
          if (res.code == 0) {
            // 处理生成图片成功的逻辑
            let parts = res.data.split(';');
            this.Lg_requestId = parts[0];
            this.Lg_queueNum= parseInt(parts[1], 10); // 提取出的数字，转换为整数

            // 设置定时器以间隔6秒检查请求状态
            let interval_Lg = setInterval(() => {
              request.get("/user/getUrl", {
                params: {
                  requestId: this.Lg_requestId,
                  queueNum: this.Lg_queueNum
                }
              }).then(res => {
                if (res.code === 1) {
                  // 情况1：图片生成失败
                  clearInterval(interval_Lg);
                  this.isShowBar_Lg = false
                  this.isGenerating_Lg = false;
                  this.$message.error("图片生成失败")
                } else if (res.code === 0) {
                  if (res.data > 1) {
                    // 情况2：请求排队中
                    this.message_Lg = `排队中，当前位置：${res.data}`;
                  } else if (res.data === 1) {
                    // 情况3：图片正在生成
                    this.message_Lg = "图片正在生成，请稍候...";
                  } else {
                    // 情况4：图片数据获取成功
                    clearInterval(interval_Lg); 
                    this.Lg_result = "data:image/png;base64," + res.data; 
                    this.isShowBar_Lg = false
                    this.isGenerating_Lg = false;
                    request.get("/user/subtract", {
                      params: {
                        num: 3
                      }
                    });
                    this.$message.success("提示词提取成功");
                  }
                }
              }).catch(error => {
                clearInterval(interval_Lg); // 在出错时停止定时器
                this.$message.error("图片生成失败")
                this.isGenerating_Lg = false;
                this.isShowBar_Lg = false
                console.log(error)
              });
            }, 6000);

          } else {
            this.isGenerating_Lg = false;
            this.$message.error("图片生成失败")
          }
        }
      }).catch(() => {
        this.$message.error("图片生成失败")
        this.isShowBar_Lg = false
        this.isGenerating_Lg = false
      })
    },

        wipe() {
            request.get("/user/wipeMessageMy").then((res) => {
                if (res != null) {
                    return
                }
            })
        },

        goToLogin(){
            this.dialogVisible_gotoLogin=false;
            router.push("/login")
        },

async  imagetocanvas(file) {
    const reader = new FileReader();
    reader.readAsDataURL(file.raw);
    reader.onload = (e) => {
        const img = new Image();
        img.onload = () => {
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            this.cleanEntity.height=canvas.height
            this.cleanEntity.width=canvas.width
            this.cleanEntity.init_images[0]=canvas.toDataURL('image/jpeg')
        };
        img.src = e.target.result;
    };    
},


        startDrawing(event) {
            this.isDrawing = true;
            const ctx = this.$refs.vueCanvas.getContext('2d');
            this.saveState();
            ctx.beginPath();
            ctx.moveTo(event.offsetX, event.offsetY);
        },

        draw(event) {
            if (!this.isDrawing) return;
            const ctx = this.$refs.vueCanvas.getContext('2d');
            ctx.lineTo(event.offsetX, event.offsetY);
            ctx.strokeStyle = 'rgba(0,0,0,1)'; // 设置线条颜色为黑色
            ctx.lineWidth = this.value; // 设置线条宽度
            ctx.lineCap = 'round'; // 设置线条末端形状为圆形
            ctx.stroke();
        },

        stopDrawing() {
            this.isDrawing = false;
        },

        clearMask() {
            if (this.undoList.length > 0) {
                var stateToRestore = this.undoList.shift();
                this.undoList = []; // 清空撤销列表
                this.redoList = [];
                this.restoreCanvasState(stateToRestore);
            }
        },

        exportMaskImage() {
    const canvas = this.$refs.vueCanvas;
    const tempCanvas = this.$refs.Canvas2;
    const tempCtx = tempCanvas.getContext("2d");

    tempCanvas.width = canvas.width;
    tempCanvas.height = canvas.height;

    tempCtx.drawImage(canvas, 0, 0);
    this.invertColors(tempCtx);
    this.cleanEntity.mask = tempCanvas.toDataURL('image/png');
    const link = document.createElement('a');
    link.href = this.cleanEntity.mask; // 使用Base64字符串作为href
    link.download = 'mask.png';
    link.click();
},

        invertColors(context) {
            const canvas = this.$refs.vueCanvas;
            const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
            const data = imageData.data;

            for (let i = 0; i < data.length; i += 4) {
                if (data[i] === 0 && data[i + 1] === 0 && data[i + 2] === 0) {
                    // 将黑色变为白色
                    data[i] = 255; // red
                    data[i + 1] = 255; // green
                    data[i + 2] = 255; // blue
                } else {
                    // 将其他颜色变为黑色
                    data[i] = 0; // red
                    data[i + 1] = 0; // green
                    data[i + 2] = 0; // blue
                }
            }
            context.putImageData(imageData, 0, 0);
        },

        getMaskImageData() {
            const canvas = this.$refs.vueCanvas;
            const tempCanvas = this.$refs.Canvas2;
            const tempCtx = tempCanvas.getContext('2d');

            tempCanvas.width = canvas.width;
            tempCanvas.height = canvas.height;

            tempCtx.drawImage(canvas, 0, 0);
            this.invertColors(tempCtx);

            return tempCanvas.toDataURL('image/png');
        },

        saveState() {
            const dataUrl = this.$refs.vueCanvas.toDataURL();
            this.undoList.push(dataUrl);
            this.currentCanvasState = dataUrl;
        },

        undoSave() {
            if (this.undoList.length > 0) {
                var stateToRestore = this.undoList.pop();
                this.redoList.push(this.currentCanvasState);
                this.restoreCanvasState(stateToRestore);
            }
        },
        restoreCanvasState(dataUrl) {
            const canvas = this.$refs.vueCanvas;
            const ctx = canvas.getContext('2d');
            const img = new Image();
            img.onload = function () {
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            };
            img.src = dataUrl;
            this.currentCanvasState = dataUrl;
        },


        AN_onDrop(event) {
            event.preventDefault();
            if (event.dataTransfer.files.length) {
                const file = event.dataTransfer.files[0];
                const isImage = file.type.startsWith('image/');
                if (isImage) {
                    this.AN_handleChange({ raw: file }, [{ raw: file }]);
                } else {
                    this.$message.error('只能拖拽图片文件！');
                }
            }
        },
        AN_neverUpload() {
            // 模拟上传 URL
            return 'https://example.com/upload';
        },
        AN_beforeUpload(file) {
            // 仅允许上传图片文件
            const isImage = file.type.startsWith('image/');
            if (!isImage) {
                this.$message.error('只能上传图片文件！');
            }
            return isImage;
        },
        //对图片进行压缩至1200px，以及0.7的质量压缩，并赋值给动漫头像实体
        AN_handleChange(file, fileList) {
            this.AN_fileList = fileList.slice(-1);
            const reader = new FileReader();
            reader.readAsDataURL(file.raw);
            reader.onload = (e) => {
                const image = new Image();
                image.onload = () => {
                    // 创建 canvas 用于处理图片
                    const canvas = document.createElement('canvas');
                    const ctx = canvas.getContext('2d');

                    // 计算缩放尺寸
                    const maxDimension = 1200;
                    let scale = 1;
                    if (image.width > maxDimension || image.height > maxDimension) {
                        scale = maxDimension / Math.max(image.width, image.height);
                    }

                    canvas.width = image.width * scale;
                    canvas.height = image.height * scale;

                    // 在 canvas 上绘制缩放后的图片
                    ctx.drawImage(image, 0, 0, canvas.width, canvas.height);

                    // 获取调整大小和压缩后的图片的 Base64 编码
                    const quality = 0.6; // 质量压缩比
                    const resizedImageBase64 = canvas.toDataURL('image/jpeg', quality);
                    this.AN_imageSrc = resizedImageBase64
                };
                image.src = e.target.result;
            };
        },


        //获取Tagger
        async generateTagger() {
            if(this.isLogined==false){
                this.dialogVisible_gotoLogin=true
                return
            }
            this.getTagger.image = this.AN_imageSrc
            if (this.getTagger.image == null) {
                return this.$message.error("请先选择图片")
            }
            // P操作
            if (this.isTaggering)
                return this.$message.error("图片描述词正在获取中，请勿重复点击")
            this.isTaggering = true
            this.isShowBar_Tagger = true
            this.message_Tagger = "请求正在处理...";

            //判断生成次数
            let isNumOk = true;
            await request.get("/user/getNum").then(res => {
                if (res != null) {
                    if (res.data < 1) {
                        isNumOk = false
                        this.$message.error("生成次数不足")
                    }
                }
            })
            if (isNumOk == false) {
                this.isShowBar_Tagger = false
                return this.isTaggering = false
            }

            //发送请求
            request.post("/user/getTagger", this.getTagger).then(res => {
                if (res != null) {
                    if (res.code == 0) {
                        let requestId = res.data
                        // 设置定时器以间隔6秒检查请求状态
                        let interval_Tigger = setInterval(() => {
                            request.get("/user/getUrl", {
                                params: {
                                    requestId: requestId,
                                    queueNum: 1
                                }
                            }).then(res => {
                                if (res.code === 1) {
                                    // 情况1：图片生成失败
                                    clearInterval(interval_Tigger);
                                    this.isShowBar_Tagger = false
                                    this.isTaggering = false;
                                    this.$message.error("Prompt提取失败")
                                } else if (res.code === 0) {
                                    if (res.data > 1) {
                                        // 情况2：请求排队中
                                        this.message_Tagger = `排队中，当前位置：${res.data}`;
                                    } else if (res.data === 1) {
                                        // 情况3：图片正在生成
                                        this.message_Tagger = "Tigger正在提取，请稍候...";
                                    } else {
                                        // 情况4：图片数据获取成功
                                        clearInterval(interval_Tigger); // 停止定时器
                                        this.Tagger = res.data
                                        this.isShowBar_Tagger = false
                                        this.isTaggering = false;
                                        request.get("/user/subtract", {
                                            params: {
                                                num: 1
                                            }
                                        });
                                        this.$message.success("Tagger提取成功");
                                    }
                                }
                            }).catch(error => {
                                clearInterval(interval_Tigger); // 在出错时停止定时器
                                this.$message.error("Tagger提取失败")
                                this.isTaggering = false;
                                this.isShowBar_Tagger = false
                                console.log(error)
                            });
                        }, 6000);

                    } else {
                        this.isTaggering = false;
                        this.$message.error("Tagger提取失败")
                    }
                }
            }).catch(() => {
                this.$message.error("Tagger提取失败")
                this.isShowBar_Tagger = false
                this.isTaggering = false
            })
        },
    }
}

</script>
  

<style scoped>
.headline {
    font-size: 30px;
    margin-bottom: 150px;
}

.controller {
    line-height: 1px;
}

.el-header,
.el-footer {
    background-color: #111;
    color: #DDD;
    text-align: center;
    line-height: 60px;
}

.el-aside {
    background-color: #222;
    color: #DDD;
    text-align: center;
    line-height: 200px;
}

.el-main {
    background-color: #555;
    color: #DDD;
    text-align: center;
    line-height: 100px;
}

body>.el-container {
    margin-bottom: auto;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
    line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
    line-height: 320px;
}

.editor-container {
    display: flex;
}

.floatblock {
    float: center;
    width: 500px;
}


/* ------------- */


.upload-container {
    position: relative;
    text-align: center;
}

.el-upload-dragger {
    position: relative;
    width: 500px !important;
    height: 500px !important;
}

.preview-image {
    max-width: 500px;
    max-height: 500px;
    display: block;
    margin: auto;
}</style>
