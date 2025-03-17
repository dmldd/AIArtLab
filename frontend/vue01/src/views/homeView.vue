<template>
  <div>
    <div>
      <!-- <el-row>
        <div class="text">
                    <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">通道：</span>
                    <el-select v-model="cannel" 
                    style="width: 249.25px;">
                    <el-option v-for="item in cannels" :key="item.value" :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                  <el-tooltip class="item" effect="dark" content="通道1比通道生成图片快10秒左右，通道1生成图片失败可换2"
                          placement="right-start">
                          <i class="el-icon-question" style="color: #979797;font-size: 14px;margin-left: 5px;"></i>
                        </el-tooltip>
                    <span   style="color: #999;font-size: 14px;">CheckPoint</span>  -->
                  <!-- </div> -->
      <!-- </el-row> --> 

      <!-- 功能列表 -->
      <el-row style="margin-top: 8px;">
        <el-col :span="22" style="color: #303133;">
          <el-tabs v-model="activeTab">
            <!-- 图生图功能    -->
            <el-tab-pane label="文生图" name="first">
              <el-row>
                <!-- 大模型 -->
                <el-col :span="3.5">
                  <div class="text">
                    <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">大模型</span>
                    <!-- <span   style="color: #999;font-size: 14px;">CheckPoint</span>  -->
                  </div>

                  <el-select v-model="pictureIfo.override_settings.sd_model_checkpoint" placeholder="大模型"
                  style="width: 249.25px;">
                    <el-option v-for="item in CkeckPointoptionsList" :key="item.value" :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-col>



                <!-- <el-col :span="2" style="margin-left: 10px;height: 70.5px;">
          <div
            style="border: 1px solid #E4E7ED; border-radius: 4px;height: 38px;width: 38px;line-height: 38px;text-align: center;margin-top: 30px;">
            <a class="el-icon-refresh" style="font-size: 18px;color:#4f4f4f ;"></a>
          </div>
        </el-col> -->
              </el-row>


              <!-- 正面提示词，生成按钮 -->
              <el-row style="margin-top:20px">
                <el-col :span="17">
                  <div>
                    <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">正面描述词</span>
                    <span style="color: #999;font-size: 14px;">Prompt</span>
                    <el-button @click="getRandom_TextToImg" style="padding: 2px;margin-left: 20px;">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                        stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="lucide lucide-dices w-4 h-4 mr-1">
                        <rect width="12" height="12" x="2" y="10" rx="2" ry="2"></rect>
                        <path d="m17.92 14 3.5-3.5a2.24 2.24 0 0 0 0-3l-5-4.92a2.24 2.24 0 0 0-3 0L10 6"></path>
                        <path d="M6 18h.01"></path>
                        <path d="M10 14h.01"></path>
                        <path d="M15 6h.01"></path>
                        <path d="M18 9h.01"></path>
                      </svg>
                      <span>Randomize</span>
                    </el-button>
                  </div>
                  <el-input type="textarea" rows="4" placeholder="请输入提示词，支持中英文" v-model="pictureIfo.prompt" maxlength="600"
                    show-word-limit @focus="inputPrompt"
                    style="font-size: 16px;color: #191919;line-height: 16px;padding: 10px 0px;">
                  </el-input>
                </el-col>
                <el-col :span="6" style="margin-top: 33px;margin-left: 20px;">
                  <el-button  :loading="isGenerating" @click="generatePic" style="width: 332px;height: 104px;color: #e76100;font-weight: 600;font-size: 18px;background: linear-gradient(180deg,#ffd089,#ffb545);
    border-radius: 8px;">生成图片</el-button>
                </el-col>


                
              </el-row>
              <!-- 负向提示词，预设 -->
              <el-row style="margin-top: 10px;">
                <el-col :span="17">
                  <div><span style="color: #4f4f4f;font-size: 14px;">负面描述词</span>
                    <span style="color: #999;font-size: 14px;"> NegtivePrompt</span>
                  </div>
                  <el-input type="textarea" rows="4" placeholder="请输入提示词，支持中英文" @focus="inputNegPrompt"
                    v-model="pictureIfo.negative_prompt" maxlength="600" show-word-limit
                    style="font-size: 16px;color: #191919;line-height: 16px;padding: 10px 0px;">
                  </el-input>
                </el-col>
                <!-- 预设 -->
                <!-- <el-col :span="3.5" style="margin-left: 20px;">
                  <el-row style="height: 40;">
                    <el-tooltip class="item" effect="dark" content="开发中" placement="bottom-start">
                      <el-button icon="el-icon-search" circle
                        style="background-color:  #F2F6FC;font-size: 20px;padding: 10px;"></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="开发中" placement="bottom-start">
                      <el-button icon="el-icon-delete-solid" circle
                        style="background-color:  #F2F6FC;font-size: 20px;padding: 10px;margin-left: 20px;"></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="开发中" placement="bottom-start">
                      <el-button icon="el-icon-s-promotion" circle
                        style="background-color:  #F2F6FC;font-size: 20px;padding: 10px;margin-left: 20px;"></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="开发中" placement="bottom-start">
                      <el-button icon="el-icon-folder-add" circle
                        style="background-color:  #F2F6FC;font-size: 20px;padding: 10px;margin-left: 20px;"></el-button>
                    </el-tooltip>
                  </el-row>
                  <el-row style="margin-top: 10px;">
                    <el-select disabled style="width: 226px;height: 40px;" placeholder="预设" v-model="pre">

                      <el-option v-for="item in CkeckPointoptionsList" :key="item.value" :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <el-tooltip class="item" effect="dark" content="开发中" placement="right-start">
                      <el-button class="el-icon-folder-add" circle
                        style="background-color:  #F2F6FC;font-size: 20px;padding: 10px;margin-left: 10px;"></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="开发中" placement="right-start">
                      <el-button class="el-icon-folder-add" circle
                        style="background-color:  #F2F6FC;font-size: 20px;padding: 10px;margin-left: 10px;"></el-button>
                    </el-tooltip>
                  </el-row>
                </el-col> -->
              </el-row>
              <el-row>
              <!-- --------------------------------------------------------------------------------------------------- -->
              <div style="hei">
                <el-collapse v-model="activeNames">
                  <el-collapse-item title="描述词商城" name="1">
                    <div class="container">
                      <el-tabs v-model="activeTab1">

                        <el-tab-pane label="Cloth" name="Cloth">
                          <div class="scroll-container">
                            <el-row v-for="(cloths, category) in clothData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in cloths" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>

                        </el-tab-pane>

                        <el-tab-pane label="Hair" name="Hair">
                          <div class="scroll-container">
                            <el-row v-for="(hairs, category) in hairsData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in hairs" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>

                        </el-tab-pane>

                        <el-tab-pane label="Action" name="Action">
                          <div class="scroll-container">
                            <el-row v-for="(actions, category) in actionsData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in actions" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>
                        </el-tab-pane>

                        <el-tab-pane label="Expression" name="Expression">
                          <div class="scroll-container">
                            <el-row v-for="(expressions, category) in expressionData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in expressions" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>
                        </el-tab-pane>

                        <el-tab-pane label="AnimalCloth" name="AnimalCloth">
                          <div class="scroll-container">
                            <el-row v-for="(animalCloths, category) in animalClothData" :key="category"
                              class="category-row" :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in animalCloths" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>
                        </el-tab-pane>

                        <el-tab-pane label="Background" name="Background">
                          <div class="scroll-container">
                            <el-row v-for="(backgrounds, category) in backgroundData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in backgrounds" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>
                        </el-tab-pane>

                        <el-tab-pane label="NewCloth" name="NewCloth">
                          <div class="scroll-container">
                            <el-row v-for="(newCloths, category) in newClothData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in newCloths" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>
                        </el-tab-pane>

                        <el-tab-pane label="Example" name="Example">
                          <div class="scroll-container">
                            <el-row v-for="(examples, category) in exampleData" :key="category" class="category-row"
                              :gutter="10">
                              <el-col :span="24">
                                <h2>{{ category }}</h2>
                              </el-col>
                              <el-col :span="4" v-for="(english, chinese) in examples" :key="chinese">
                                <el-button @click="addToInput(english)" type="text">{{ chinese }}</el-button>
                              </el-col>
                            </el-row>
                          </div>
                        </el-tab-pane>

                      </el-tabs>


                    </div>
                  </el-collapse-item>
                </el-collapse>
              </div>
            </el-row>

              <!-- ----------------------------------------------------- -->

              <!-- 宽高，步数,图片展示区等等 -->
              <el-row>
                <!-- 左半区 -->
                <el-col :span="12">

                  <!-- 采样方法，迭代步数 -->
                  <!-- <el-row style="margin-top: 30px;"> -->
                    <!-- <el-col :span="10">
                      <div style="margin-bottom: 10px;"><span
                          style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">采样方法</span>
                        <span style="color: #999;font-size: 14px;">Sampling Method</span>
                      </div>
                      <el-select style="width: 215px;height: 40px;" placeholder="采样方法" v-model="pictureIfo.sampler_name">
                        <el-option v-for="item in SamplerList" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                      </el-select>
                    </el-col> -->
                    <!-- <el-col :span="10"> 
                      <div style="margin-bottom: 10px;"><span
                          style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">迭代步数</span>
                        <span style="color: #999;font-size: 14px;">Steps</span>
                      </div>
                      <el-slider :max="50" :min="20" v-model="pictureIfo.steps" input-size="small"
                        style="width: 300px;"></el-slider>
                    </el-col> -->

                  <!-- </el-row> -->
                  <!-- 面部修复， -->

                  <!-- 宽度，高度 -->
                  <el-row style="margin-top:;">
                    <el-col >
                      <div style="margin: 10px 0px;">
                        <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">图片宽度</span>
                        <span style="color: #999;font-size: 14px;">Width</span>
                        <input type="text" v-model="pictureIfo.width"
                          style="margin-left: 60px; width: 40px;height: 28px;line-height: 28px;padding: 0px;text-align: center;-webkit-appearance: none;background-color: #fcfdff;border-radius: 4px;    border: 1px solid #dcdfe6;box-sizing: border-box;color: #606266;outline: 0;transition: border-color .2s cubic-bezier(.645,.045,.355,1);">
                      </div>
                      <el-slider v-model="pictureIfo.width" :max="1200" :min="512" input-size="small"
                        style="margin-left: 10px;"></el-slider>
                    </el-col>
                  </el-row>

                  <el-row style="margin-top: 30px;">
                    <el-col :span="24">
                      <div style="margin: 10px 0px;">
                        <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">图片高度</span>
                        <span style="color: #999;font-size: 14px;">Height</span>
                        <input type="text" v-model="pictureIfo.height" style="margin-left: 60px; width: 40px;height: 28px;line-height: 28px;padding: 0px;text-align: center;-webkit-appearance: none;background-color: #fcfdff;border-radius: 4px;    border: 1px solid #dcdfe6;box-sizing: border-box;color: #606266;outline: 0;transition: border-color .2s cubic-bezier(.645,.045,.355,1);">
                      </div>
                      <el-slider :min="512" :max="1200" v-model="pictureIfo.height" input-size="small"
                        style="margin-left: 10px;"></el-slider>
                    </el-col>
                  </el-row>



                  <!--Batch Size -->
                  <el-row style="margin-top: 35px;">
                    <el-col :span="24">
                      <div style="margin: 10px 0px;"><span
                          style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">图片数量</span>
                        <span style="color: #999;font-size: 14px;">Number of images</span>
                        <input disabled type="text" v-model="pictureIfo.n_iter"
                          style="margin-left: 60px; width: 40px;height: 28px;line-height: 28px;padding: 0px;text-align: center;-webkit-appearance: none;background-color: #fcfdff;border-radius: 4px;    border: 1px solid #dcdfe6;box-sizing: border-box;color: #606266;outline: 0;transition: border-color .2s cubic-bezier(.645,.045,.355,1);">
                      </div>
                      <el-slider :step="1" show-stops :min="1" :max="4" disabled v-model="pictureIfo.n_iter"
                        input-size="small" style=""></el-slider>
                    </el-col>
                  </el-row>
                  <!-- <el-collapse v-model="activeNames_TextToImg" style="margin-top: 30px;">
                    <el-collapse-item title="+ 更多参数" name="1">
                  <el-row style="margin-top: 20px;">
                    <div><span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">描述词相关性</span><span
                        style="color: #999;font-size: 14px;">CFG</span>
                      <input type="text" v-model="pictureIfo.cfg_scale"
                        style="margin-left: 60px; width: 40px;height: 28px;line-height: 28px;padding: 0px;text-align: center;-webkit-appearance: none;background-color: #fcfdff;border-radius: 4px;    border: 1px solid #dcdfe6;box-sizing: border-box;color: #606266;outline: 0;transition: border-color .2s cubic-bezier(.645,.045,.355,1);">
                    </div>
                    <el-slider :step="1" show-stops v-model="pictureIfo.cfg_scale" :max="15" :min="1" input-size="small"
                      style="margin-left: 10px;margin-top: 10px;"></el-slider>

                  </el-row>
                  <el-row style="margin-top: 20px;">
                    <el-col>
                      <div style="">
                        <span style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">随机数种子</span>
                        <span style="color: #999;font-size: 14px;">Seed</span>
                        <el-tooltip class="item" effect="dark" content="决定随机数生成器输出的值   以相同参数和相同种子进行多次生成，会得到相同的多张图像
🎲能将随机种子设置为-1，即每次都会使用一个新的随机数。如果没懂，只需要知道：如果想用同样的参数生成不同的照片，请加其设置为-1" placement="right-start">
                          <i class="el-icon-question" style="color: #979797;font-size: 14px;margin-left: 5px;"></i>
                        </el-tooltip>
                      </div>
                      <el-input :max="99999999999" type="number" v-model="pictureIfo.seed" style="width: 500px;margin-top: 10px;">
                      </el-input>
                      <el-tooltip class="item" effect="dark" content="将随机数种子设置为-1，则每次都会使用一个新的随机数" placement="bottom-start">
                        <el-button @click="changeSeed"
                          style="padding: 10px 18px; font-size: 20px;margin-left: 5px;">🎲️</el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="复用上一次使用的随机种子，对于固定输出结果有用" placement="bottom-start">
                        <el-button @click="fixSeed" style="font-size: 20px;padding: 10px 18px;">♻️</el-button>
                      </el-tooltip>
                    </el-col>
                  </el-row>
                    </el-collapse-item>
                  </el-collapse> -->

                  <!-- <el-col :span="12">
                      <div style="margin: 10px 0px;margin-left: 25px;"><span
                          style="color: #4f4f4f;font-size: 14px;margin-right: 8px;">每批数量</span>
                        <span style="color: #999;font-size: 14px;">Batch Size </span>
                        <input disabled type="text" v-model="pictureIfo.batch_size"
                          style="margin-left: 60px; width: 40px;height: 28px;line-height: 28px;padding: 0px;text-align: center;-webkit-appearance: none;background-color: #fcfdff;border-radius: 4px;    border: 1px solid #dcdfe6;box-sizing: border-box;color: #606266;outline: 0;transition: border-color .2s cubic-bezier(.645,.045,.355,1);">
                      </div>
                      <el-slider disabled v-model="pictureIfo.batch_size" input-size="small"
                        style="width: 324px;margin-left: 10px;margin-left: 25px;"></el-slider>
                    </el-col> -->


                </el-col>

                <el-col :span="2">
                  <div style="border: 1px solid transparent;"></div>
                </el-col>

                <!-- 右半区，生成图片展示区 -->
                <el-col :span="10" style=" margin-top: 40px;">
                  <div
                    style="height:460px ;width: 460px; background-color: #f1f2f6;border-radius: 10px;text-align: center;">
                    <div v-show="isShowBar"
                      style="height: 20px;width: 450px;background-color: #424C5B; text-align: left;line-height: 20px;border-radius: 10px;color: #dcdfe6;font-size: 14px;padding-left: 10px;">
                      {{ message }}
                    </div>
                    <el-image :src="this.pictureBase64" fit="contain"
                      style="height: 400px;width: 400px;margin-top: 30px;"   :preview-src-list="srcList_text2image">
                      <div slot="error" class="image-slot" style="margin-top: 50px;">
                        <i v-if="!isGenerating"   class="el-icon-picture-outline"></i>
                        <i v-if="isGenerating"  class="el-icon-loading"></i>
                        
                      </div>
                    </el-image>
                  </div>
                  <div style="width: 300px;margin-top: 10px;">
                    <el-button :loading="this.shareFlag==3" type="primary" @click="sharePic(1)" style="margin-right: 10px;">分享图片</el-button>
                    <a :href="pictureBase64" v-if="pictureBase64 !== ''" download="PEAKY_tr.png" target="_blank">下载图片</a>
                  </div>
                </el-col>
              </el-row>
            </el-tab-pane>
            <!-- 二维码 -->
            <el-tab-pane label="艺术二维码" name="second">

              <el-row>
                <el-col :span="10">
                  <div style="display: flex;">
                    <div style="">URL/Text</div>
                    <div style="margin-left: 340px;">{{ QR_url_length }}</div>
                    <div>/75</div>
                    <input  type="file" @change="onFileChange_Qr" ref="fileInput_Qr" style="display: none;">
                    <el-button @click="triggerFileSelect_Qr" style="padding: 2px;margin-left: 10px;">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                        stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="lucide lucide-scan w-4 h-4 mr-1">
                        <path d="M3 7V5a2 2 0 0 1 2-2h2"></path>
                        <path d="M17 3h2a2 2 0 0 1 2 2v2"></path>
                        <path d="M21 17v2a2 2 0 0 1-2 2h-2"></path>
                        <path d="M7 21H5a2 2 0 0 1-2-2v-2"></path>
                      </svg>
                      <span>Scan</span>
                    </el-button>
                  </div>

                  <el-input show-word-limit maxlength="75" style="margin-top: 5px;" placeholder="可输入网址、文本" v-model="Qr_UrlInput"
                    @input="updateQR_URL_length"></el-input>

                  <div style="display: flex;margin-top: 30px;">
                    <div>Prompt</div>
                    <div style="margin-left: 303px;">{{ QR_prompt_length }}</div>
                    <div>/200</div>
                    <el-button @click="getRandom_Qr" style="padding: 2px;margin-left: 10px;">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                        stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="lucide lucide-dices w-4 h-4 mr-1">
                        <rect width="12" height="12" x="2" y="10" rx="2" ry="2"></rect>
                        <path d="m17.92 14 3.5-3.5a2.24 2.24 0 0 0 0-3l-5-4.92a2.24 2.24 0 0 0-3 0L10 6"></path>
                        <path d="M6 18h.01"></path>
                        <path d="M10 14h.01"></path>
                        <path d="M15 6h.01"></path>
                        <path d="M18 9h.01"></path>
                      </svg>
                      <span>Randomize</span>
                    </el-button>
                  </div>

                  <el-input v-model="QrPrompt" type="textarea" :rows="3" placeholder="暂不支持中文，请输入英文提示词，建议搭配有道字典使用"
                    style="margin-top: 5px;font-size: 16px;color: #191919; " maxlength="200"
                    @input="updateQR_prompt_length"></el-input>
                  

                  <el-collapse v-model="activeNames_QRcode">
                    <el-collapse-item title="+ 更多参数" name="1">
                      <div style="font-size: 16px;">Negtive Prompt</div>
                      <el-input v-model="QrNegtive" maxlength="300" placeholder="暂不支持中文，请输入英文提示词，建议搭配有道字典使用" type="textarea" :rows="3"
                        style="margin-top: 5px;font-size: 16px;color: #191919;"></el-input>
                      <div style="margin-top: 5px;display: flex;">
                        <div style="font-size: 16px;">强度控制</div>
                        <el-tooltip class="item" effect="dark" content="原二维码对艺术二维码的影响程度，值越高，扫描成功率越高，同时观赏性越低，反之亦然"
                          placement="right-start">
                          <i class="el-icon-question" style="color: #979797;font-size: 14px;margin-left: 5px;"></i>
                        </el-tooltip>
                      </div>
                      <div> <el-slider v-model="QrControlStrength" style="margin-left: 5px;"></el-slider></div>
                      <div style="margin-top: 5px;display: flex;">
                        <div style="font-size: 16px;">边距控制</div>
                        <el-tooltip class="item" effect="dark" content="二维码的Margin，即边缘码点和图片边缘之间的距离，单位px"
                          placement="right-start">
                          <i class="el-icon-question" style="color: #979797;font-size: 14px;margin-left: 5px;"></i>
                        </el-tooltip>
                      </div>
                      <div> <el-slider v-model="Qr_padding" :max="200" style="margin-left: 5px;"></el-slider></div>
                    </el-collapse-item>
                  </el-collapse>


                  <el-button :loading="isGenerating_Qr" @click="generateQrcode" style="margin-top: 5px; color: rgb(231, 97, 0);width: 300px; height: 60px;   background: linear-gradient(rgb(255, 208, 137), rgb(255, 181, 69));
                      border-radius: 8px;">立即生成
                  </el-button>
                </el-col>
                <el-col :span="3">
                  <div style="border: 1px solid transparent;"></div>
                </el-col>


                <!-- 二维码图片展示区 -->
                <el-col :span="11">
                  <div
                    style="height:460px ;width: 460px; background-color: #EBEEF5;border-radius: 10px;text-align: center;">
                    <div v-show="isShowBar_Qr"
                      style="height: 20px;width: 450px;background-color: #424C5B; text-align: left;line-height: 20px;border-radius: 10px;color: #dcdfe6;font-size: 14px;padding-left: 10px;">
                      {{ message_Qr }}
                    </div>
                    <el-image :src="QrcodeBase64" fit="contain" style="height: 400px;width: 400px;margin-top: 30px;" :preview-src-list="srcList_Qrcode">
                      <div slot="error" class="image-slot" style="margin-top: 50px;">
                        <i v-if="!isGenerating_Qr" class="el-icon-picture-outline"></i>
                        <i v-if="isGenerating_Qr"  class="el-icon-loading"></i>
                      </div>
                    </el-image>
                  </div>
                  <div style="width: 300px;margin-top: 10px;">
                    <el-button type="primary" :loading="this.shareFlag_Qr==3" @click="sharePic(2)" style="margin-right: 10px;">分享图片</el-button>
                    <a :href="QrcodeBase64" v-if="QrcodeBase64 !== ''" download="Peaky_Tr.png" target="_blank">下载图片</a>
                  </div>
                </el-col>
              </el-row>


              <!-- <img style="width: 187px;height: 187px;" src="@/assets/moonset.png" alt="Moonset" /> -->



            </el-tab-pane>

            <!-- 风格转绘 -->
            <!-- <el-tab-pane label="风格转绘" name="third">
              <el-row>
                <el-col :span="11">
                  <div style="margin-bottom: 5px;">
                    选择图片
                  </div>
                  <el-upload  :action="AN_neverUpload()" :before-upload="AN_beforeUpload"
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


                  <div style="margin-bottom: 5px;margin-top: 30px;">
                  
                    <div>选择风格</div>
                  </div>
                  <div class="gallery">
                    <el-row :gutter="10">
                      <el-col :span="8" v-for="portrait in portraits" :key="portrait.id">
                        <div class="portrait-card"
                          :class="{ 'is-selected': selectedPortrait === portrait.id || hoverPortrait === portrait.id }"
                          @click="selectPortrait(portrait.id)" @mouseover="hoverPortrait = portrait.id"
                          @mouseleave="hoverPortrait = null">
                          <div class="image-container">
                            <img :src="portrait.image" class="image" />

                            <div class="overlay"
                              v-show="selectedPortrait === portrait.id || hoverPortrait === portrait.id">
                              {{ portrait.name }}
                            </div>
                          </div>
                        </div>
                      </el-col>
                    </el-row>
                  </div>


                  <el-collapse v-model="activeNames_AN">
                    <el-collapse-item title="+ 更多参数" name="1">
                      <div>Prompt</div>
                      <el-input type="textarea" rows="3" placeholder="暂不支持中文，请输入英文提示词，建议搭配有道字典使用" v-model="userFacePrompt" maxlength="200"
                        show-word-limit style="font-size: 16px;color: #191919;line-height: 16px;padding: 10px 0px;">
                      </el-input>

                      <div>NegtivePrompt</div>
                      <el-input type="textarea" rows="3" placeholder="暂不支持中文，请输入英文提示词，建议搭配有道字典使用" v-model="AN_NegtivePrompt" maxlength="200"
                        show-word-limit style="font-size: 16px;color: #191919;line-height: 16px;padding: 10px 0px;">
                      </el-input>


                      <div style="margin-top: 5px;display: flex;">
                        <div style="font-size: 16px;">重绘幅度</div>
                        <el-tooltip class="item" effect="dark" content="生成图片与参考图片的相似度。值越高，风格越明显，但越容易崩图"
                          placement="right-start">
                          <i class="el-icon-question" style="color: #979797;font-size: 14px;margin-left: 5px;"></i>
                        </el-tooltip>
                      </div>
                      <el-slider v-model="userFaceValue" style="margin-top: 5px;"></el-slider>
                    </el-collapse-item>
                  </el-collapse>
                  <el-button :loading="isGenerate_AN" @click="generateUserFace" style="margin-top: 5px; color: rgb(231, 97, 0);width: 300px; height: 60px;   background: linear-gradient(rgb(255, 208, 137), rgb(255, 181, 69));
                      border-radius: 8px;">立即生成
                  </el-button>
                </el-col>


                <el-col :span="2">
                  <div style="border: 1px solid transparent;"></div>
                </el-col>


                
                <el-col :span="10">
                  <div
                    style="height:460px ;width: 460px; background-color: #EBEEF5;border-radius: 10px;text-align: center;">
                    <div v-show="isShowBar_AN"
                      style="height: 20px;width: 450px;background-color: #424C5B; text-align: left;line-height: 20px;border-radius: 10px;color: #dcdfe6;font-size: 14px;padding-left: 10px;">
                      {{ message_AN }}
                    </div>
                    <el-image :src="userFaceRes" fit="contain" style="height: 400px;width: 400px;margin-top: 30px;" :preview-src-list="srcList_stylechange">
                      <div slot="error" class="image-slot" style="margin-top: 50px;">
                        <i v-if="!isGenerate_AN"  class="el-icon-picture-outline"></i>
                        <i v-if="isGenerate_AN"  class="el-icon-loading"></i>
                      </div>
                    </el-image>
                  </div>
                  <div style="width: 300px;margin-top: 10px;">
                    <el-button type="primary" :loading="this.shareFlag_AN==3" @click="sharePic(3)" style="margin-right: 10px;">分享图片</el-button>
                    <a :href="userFaceRes" v-if="userFaceRes !== ''" download="Peaky_Tr.png" target="_blank">下载图片</a>
                  </div>
                </el-col>

              </el-row>
              <div style="margin-top: 60px;">

              </div>
            </el-tab-pane> -->

            <!-- 光影字 -->
            <el-tab-pane label="光影字" name="fourth">
              <el-row>
                <el-col :span="11">
                  <div style="display: flex;">
                    <div style="">输入文字</div>
                  </div>
                  <el-input  style="margin-top: 5px;" placeholder="可输入中文、英文、韩文等" v-model="Lg_word"
                    maxlength="2" show-word-limit></el-input>

                  <div style="margin-bottom: 5px;margin-top: 30px;">
                    <div>选择样式</div>
                  </div>

                  <div class="gallery">
                    <el-row :gutter="10">
                      <el-col :span="8" v-for="Lg_style in Lg_styles" :key="Lg_style.id">
                        <div class="portrait-card"
                          :class="{ 'is-selected': Lg_selectedPortrait === Lg_style.id || Lg_hoverPortrait === Lg_style.id }"
                          @click="Lg_selectStyle(Lg_style.id)" @mouseover="Lg_hoverPortrait = Lg_style.id"
                          @mouseleave="Lg_hoverPortrait = null">
                          <div class="image-container">
                            <img :src="Lg_style.image" class="image" />
                            <div class="overlay"
                              v-show="Lg_selectedPortrait === Lg_style.id || Lg_hoverPortrait === Lg_style.id">
                              {{ Lg_style.name }}
                            </div>
                          </div>
                        </div>
                      </el-col>
                    </el-row>
                  </div>

                  <el-collapse v-model="activeNames_Lg">
                    <el-collapse-item title="+ 更多参数" name="1">
                      <div>字体</div>
                      <el-select v-model="option_value_Lg" placeholder="请选择">
                        <el-option
                          v-for="item in options_Lg"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                          </el-option>
                      </el-select>
                    
                    </el-collapse-item>
                  </el-collapse>

                  <el-button :loading="isGenerating_Lg"  @click="generateLightWord" style="margin-top: 5px; color: rgb(231, 97, 0);width: 300px; height: 60px;   background: linear-gradient(rgb(255, 208, 137), rgb(255, 181, 69));
                      border-radius: 8px;">立即生成
                  </el-button>
                   <!-- 展示生成的图片 -->
                </el-col>

                <el-col :span="2">
                  <div style="border: 1px solid transparent;"></div>
                </el-col>

                <el-col :span="10">
                  <div
                    style="height:460px ;width: 460px; background-color: #EBEEF5;border-radius: 10px;text-align: center;">
                    <div v-show="isShowBar_Lg"
                      style="height: 20px;width: 450px;background-color: #424C5B; text-align: left;line-height: 20px;border-radius: 10px;color: #dcdfe6;font-size: 14px;padding-left: 10px;">
                      {{ message_Lg }}
                    </div>
                    <el-image :src="Lg_result" fit="contain" style="height: 400px;width: 400px;margin-top: 30px;" :preview-src-list="srcList_Lg">
                      <div slot="error" class="image-slot" style="margin-top: 50px;">
                        <i v-if="!isGenerating_Lg" class="el-icon-picture-outline"></i>
                        <i v-if="isGenerating_Lg"  class="el-icon-loading"></i>
                      </div>
                    </el-image>
                  </div>
                  <div style="width: 300px;margin-top: 10px;">
                    <el-button type="primary" :loading="this.shareFlag_Lg==3" @click="sharePic(4)" style="margin-right: 10px;">分享图片</el-button>
                    <a :href="Lg_result" v-if="Lg_result !==null" download="Peaky_Tr.png" target="_blank">下载图片</a>
                  </div>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>

        </el-col>
        <el-col :span="2" style="color: #303133;">
          <el-button @click="instructionChange" style="background-color: #1dd392;color: #fff;padding: 10px 20px;"><a
              class="el-icon-moon-night" style="font-size: 18px;"></a> 说明书</el-button>
        </el-col>

      </el-row>
    </div>

    <el-dialog
      title="🌙提示"
      :visible.sync="dialogVisible_gotoLogin"
      width="30%">
      <span style="font-size: 15px; color: #303133;">请先登录</span>
      <span slot="footer" class="dialog-footer">
       <el-button type="primary" @click="goToLogin">去登录</el-button>
      </span>
    </el-dialog>


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
import QRCode from 'qrcodejs2';
import { scan } from 'qr-scanner-wechat';
import router from '@/router'
export default {
  props:['isLogined'],

  data: function () {
    return {
      selectedPortrait: null,
      Lg_selectedPortrait: null,
      hoverPortrait: null,
      Lg_hoverPortrait:null,
      srcList_text2image:[],
      srcList_Qrcode:[],
      srcList_stylechange:[],
      srcList_Lg:[],

//pay
      dialogVisible_pay:false,
      dialogVisible_pay_QRcode:false,
      pay_QRcode:null,
      pay_item:{
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/coffee_v2.png",
        title:"请格温小姐喝咖啡",
        price:"0.1",
        welfares:"赠送300剪刀",
        productType:0,
        credit:1
      },

      pay_items:[
      {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/coffee_v2.png",
        title:"请格温小姐喝咖啡",
        price:"0.1",
        welfares:"赠送300剪刀",
        productType:0,
        credit:1
      },
        {
        image:"https://kimi-img.moonshot.cn/prod-chat-kimi/avatar/kimi/flower.png",
        title:"送格温小姐一朵花",
        price:"0.1",
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
        price:"0.1",
        welfares:"每日赠送600剪刀 解锁全部lora、大模型 × 30 天",
        productType:3,
        credit:1
      }
      ],
      pay_item_currentNumber:0,


      //文生图请求实体      
      pictureIfo: {
        alwayson_scripts: {
          ADetailer: {
            args: [
              {
                ad_model: "nface_yolov8n.pt",
              },
              {
                ad_model: "none",  
              }
            ]
          },
          controlnet: {
            args: [
              {
                control_mode: "Balanced",
                enabled: false,
                guidance_end: 0.5,
                guidance_start: 0.0,
                image: "base64SrcImg",
                low_vram: false,
                model: "control_v11f1p_sd15_depth [cfd03158]",
                module: "depth_midas",
                pixel_perfect: true,
                processor_res: 0,
                resize_mode: 1,
                threshold_a: 0,
                threshold_b: 0,
                weight: 0.75
              }
            ]
          }
        },
        cfg_scale: 7,
        height: 960,
        width: 768,
        negative_prompt: null,
        override_settings: {
          sd_model_checkpoint: "dreamshaperXL_v21TurboDPMSDE.safetensors",
          sd_vae: "[StabilityAI]vae-ft-mse-840000-ema-pruned.safetensors"
        },
        clip_skip: 2,
        prompt: null,
        restore_faces: false,
        sampler_index: "Euler a",
        sampler_name: "Euler a",
        script_args: [
        ],
        seed: -1,
        steps: 20,
        tiling: false,
        batch_size: 1,
        n_iter: 1,

      },

      //动漫头像请求实体
      generateUserFaceEntity: {
        prompt: "",
        negative_prompt: "",

        styles: [
          "string"
        ],
        steps: 20,
        cfg_scale: 7,
        width: 536,
        height: 860,
        denoising_strength: 0.55,
        seed: -1,
        subseed: -1,
        override_settings: {
          sd_model_checkpoint: null,
          sd_vae: "None"
        },
        init_images: [],
        sampler_index: "Euler a",
        sampler_name: "Euler a",
        alwayson_scripts: {
          ADetailer: {
            args: [
              {
                ad_model: "none",
              }
            ]
          },
          controlnet: {
            args: [
              {
                enabled: true,
                image: null,
                module: "lineart_realistic",
                model: "control_v11p_sd15s2_lineart_anime_fp16 [c58f338b]",
                weight: 0.55,
                resize_mode: 1,   //缩放模式 0,1,2 对应的 Just Resize,Crop and Resize,Resize and Fill
                guidance_start: 0,
                guidance_end: 1,
                pixel_perfect: true,
                control_mode: "Balanced"
              },
              {
                enabled: true,
                image: null,
                module: "depth_midas",
                model: "control_v11f1p_sd15_depth_fp16 [4b72d323]",
                weight: 0.65,
                resize_mode: 1,   //缩放模式 0,1,2 对应的 Just Resize,Crop and Resize,Resize and Fill
                guidance_start: 0,
                guidance_end: 1,
                pixel_perfect: true,
                control_mode: "Balanced"
              }
            ]
          }
        },
      },
      // 二维码实体    
      pictureIfo_Qrcode: {
        alwayson_scripts: {
          ADetailer: {
            args: [
              {
                ad_model: "face_yolov8n.pt"
              },
              {
                ad_model: "none"
              }
            ]
          },
          controlnet: {
            args: [
              {
                control_mode: "Balanced",
                enabled: true,
                guidance_end: 0.7,
                guidance_start: 0.17,
                image: "",
                low_vram: false,
                model: "control_v1p_sd15_qrcode_monster_v2 [5e5778cb]",
                module: "none",
                pixel_perfect: true,
                processor_res: 0,
                resize_mode: 1,
                threshold_a: 0,
                threshold_b: 0,
                weight: 1.15
              },
              {
                control_mode: "Balanced",
                enabled: true,
                guidance_end: 0.87,
                guidance_start: 0.7,
                image: "",
                low_vram: false,
                model: "control_v1p_sd15_brightness [5f6aa6ed]",
                module: "none",
                pixel_perfect: true,
                processor_res: 0,
                resize_mode: 1,
                threshold_a: 0,
                threshold_b: 0,
                weight: 0.25
              },
              {
                control_mode: "Balanced",
                enabled: true,
                guidance_end: 0.92,
                guidance_start: 0.7,
                image: "",
                low_vram: false,
                model: "control_v1p_sd15_qrcode_monster_v2 [5e5778cb]",
                module: "none",
                pixel_perfect: true,
                processor_res: 0,
                resize_mode: 1,
                threshold_a: 0,
                threshold_b: 0,
                weight: 1.3
              },
            ]
          }
        },
        cfg_scale: 7,
        height: 776,
        width: 776,
        negative_prompt: ",EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad-hands-5, (worst quality, low quality:1.4), ng_deepnegative_v1_75t, badhand-v4,( blurry:1.5), lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, jpeg artifacts, signature, watermark, username, blurry, artist name, (monochrome:1.1), bad anatomy,bad hands, ((monochrome)),((grayscale)),futanari,full-package_futanari,penis_from_girl,newhalf,nipplepierces,",
        override_settings: {
          sd_model_checkpoint: "breakdomain_M2150.safetensors",
          sd_vae: "[二次元]Orangemixs.pt"
        },
        clip_skip: 2,
        prompt: "",
        restore_faces: false,
        sampler_index: "Euler a",
        sampler_name: "",
        script_args: [
        ],
        seed: -1,
        steps: 27,
        tiling: false,
        batch_size: 1,
        n_iter: 1,

      },

      //光影字实体
      pictureIfo_Lg:{
        alwayson_scripts: {
          ADetailer: {
            args: [
              {
                ad_model: "face_yolov8n.pt"
              },
                {
                ad_model: "none"
              }
              
            ]
          },
          controlnet: {
            args: [
              {
                control_mode: "Balanced",
                enabled: true,
                guidance_end: 0.8,
                guidance_start: 0.1,
                image: "",
                low_vram: false,
                model: "control_v1p_sd15_brightness [5f6aa6ed]",
                module: "none",
                pixel_perfect: true,
                weight: 0.5
              },
            ]
          }
        },
        cfg_scale: 8,
        height: 924,
        width: 660,
        negative_prompt: "nsfw, ((bright background:1.5)), ",
        override_settings: {
          sd_model_checkpoint: "麦橘v6.safetensors",
          sd_vae: "[StabilityAI]vae-ft-mse-840000-ema-pruned.safetensors"
        },
        clip_skip: 2,
        prompt: "",
        restore_faces: false,
        sampler_index: "Euler a",
        sampler_name: "Euler a",
        script_args: [
        ],
        seed: -1,
        steps: 28,
        tiling: false,
        batch_size: 1,
        n_iter: 1,
      },

      //大模型checkpoint列表  
      CkeckPointoptionsList: [
      {
        value: 'dreamshaperXL_v21TurboDPMSDE.safetensors',
        label: '通用|Dream'
      },
      {
         value: 'Cute.safetensors',
         label: '二次元|特萌Cute'
       },
       {
         value: 'Animagine.safetensors',
         label: '二次元XL|Animagine'
       },
       {
         value: 'Juggernaut.safetensors',
         label: '通用XL|Juggernaut'
       },
      {
        value: '麦橘v6.safetensors',
        label: '写实|性感Majic'
      },
      // {
      //   value: 'ReVAnimated.safetensors',
      //   label: '二次元|Reva'
      // }
      
      // {
      //   value: 'SHMILY.safetensors',
      //   label: '水彩|Shmi'
      // },
       {
        value: 'breakdomain_M2150.safetensors',
        label: '二次元|Break'
      }, 
      // {
      //   value: 'Manmarumix.safetensors',
      //   label: '二次元|Maru'
      // }
      
      
     ],
      //采样器列表      
      SamplerList: [
        {
          value: 'Euler a',
          label: '采样器1'
        }, {
          value: 'Euler',
          label: '采样器2'
        }, {
          value: 'DPM++ 2M SDE Karras',
          label: '采样器3'
        }, {
          value: 'DPM++ 2M',
          label: '采样器4'
        }, {
          value: 'DPM fast',
          label: '采样器5'
        }, {
          value: 'DPM++ 2M SDE Heun Karras',
          label: '采样器6'
        }, {
          value: 'Restart',
          label: '采样器7'
        }
      ],

      //文生图模块
      TextToImg_requestId:null,
      TextToImg_queueNum:null,
      TextToImg_is_illegal:false,


      //二维码模块：
      Qr_UrlInput: '', // 用户输入的网址/文本
      activeNames_QRcode: [],
      QR_prompt_length: 0,
      QR_url_length: 0,
      isShowBar_Qr: false,
      message_Qr: "",
      QrControlStrength: 50,
      Qr_padding: 93,
      QrPrompt: '',
      QrNegtive: '',
      QrcodeBase64: '',
      Qr_requestId:null,
      Qr_queueNum:null,
      Qr_is_illegal:false,
      randomize: ["windswept, windy, floating hair, floating clothes, floating flower petals, hair flowers, chinese clothes, hanfu, (baihuaniang:1.2), (many flowers:1.4), cowboy shot, (golden hour, garden, bushes,", "(1 girl:1.5),(dolorous:1.5), full body, from side, ultra wide shot, dress,  (grey long hair:1.3), (white ribbon:1.6), (white lace:1.6), BREAK, ", "(1 girl:1.5),happy,joyful,(colorful bubble:1.6),tide,water, seabed, (1 girl:1.6), full body, from side, ultra wide shot, dress, (white ribbon:1.6), (white lace:1.6),  (grey long hair:1.3), BREAK,, masterpiece, best quality", "love elements, hearts, romance, affection, love symbols, sweetheart, love sign, valentine, affectionate, amour", "back alley, bricks, gangland, wall text graffiti, messy town, gang, shutters, darksome, spooky, dangerous atmosphere, badness, midnight, night, night road, fluorescent lamp", "fantasy, castle and town, castle town, beautiful building, how people live, flowers, trees, nature, romantic, shopping street, street stall, blue sky", "ancient Greek mythology, Olympus, Zeus, Athena, Aphrodite, classical myths, titans, Hercules, mythology, minotaur", "multicolored, colorful, vibrant, diverse, hues, shades, tints, rainbow colors, vivid, technicolor", "broken glass, peeled walls, jungle, city, city center, building, moss, ivy, blue sky, cumulonimbus, collapsed town, broken town, deserted town, roots crawling on walls, outdoor, rubble, ruins", "winter landscape, snowfall, snowflakes, snow-covered, frost, winter scene, snowy weather, white winter, snowscape, cold season", "marine adventure, pirates, treasure, shipwreck, ocean exploration, sea voyage, nautical, diving, sea monsters, sailing", "poker card designs, clubs, diamonds, hearts, spades, face cards, deck of cards, playing cards, joker, royal flush", "old-fashioned, street café, nostalgic, city, cobblestone, chairs, tables, people, relaxing, architecture", "steampunk, Victorian, retro-futuristic, gears, goggles, steam engine, alternative history, clockwork, anachronistic, industrial revolution", "beautiful ballet performance, elegant, dancers, stage, fluid movement, grace, spotlight", "forest sprites, fairies, fantasy, woodland creatures, nature spirits, magical beings, enchantment, pixies, forest magic, woodland", "starry sky, galaxy, universe, space, nebula, constellation, milky way, cosmos, night sky, celestial", "marine life, ocean creatures, sea animals, undersea world, aquatic ecosystem, coral reef, deep ocean, sea world, aqua life, marine species", "wandering, mystical forest, enchanted, trees, moss, soft light, magic, path, foliage, mysterious", "Giant trees, leaves that cover the sky, maple leaves, Autumn leaves, autumn, red mountain, Fallen leaves, waterfall", "popular cartoons, animated characters, anime, animation, cartoonist, comic strip, kids show, toons, caricature, manga", "back alley, bricks, gangland, wall text graffiti, messy town, gang, shutters, darksome, spooky, dangerous atmosphere, badness, midnight, night, night road, fluorescent lamp", "ancient roman architecture, coliseum, pantheon, roman ruins, classical, columns, arches, roman art, roman empire, basilica", "fashion trends, style, latest fashion, vogue, chic, couture, modish, glamour, trendsetter, haute couture", "empty theater stage, after the show, quiet, spotlight, curtains, performance, atmosphere, solitude", "Amusement park, attractions, theme parks, roller coaster, ferris wheel, flower, fountain, outdoor, blue sky", "mysterious cave exploration, unknown, torchlight, stalactites, subterranean, hidden treasures, darkness", "ancient tomb excavation, archeology, hieroglyphics, sarcophagus, dusty, mysterious, historical", "ancient egyptian hieroglyphs, pictorial script, inscription, pharaoh, egyptology, ankh, pyramids, scribes, rosetta stone, cartouche", "Victorian style, antique, prudish, gothic revival, queen victoria, ornate, victorian fashion, 19th century, vintage, victorian architecture", "precision instruments, microscope, telescope, precision tools, equipment, laboratory, spectroscopy, microscope, precision measurement, scientific instrument",],

      //光影字模块
      Lg_word:'',
      isShowBar_Lg: false,
      message_Lg: "",
      Lg_requestId:null,
      Lg_queueNum:null,
      Lg_result:null,
      Lg_is_illegal:false,
      activeNames_Lg: [],
      options_Lg: [{
          value: 'Arial',
          label: 'Arial'
        }, {
          value: 'MaShanZheng-Regular',
          label: 'MaShanZheng-Regular'
        }],
        option_value_Lg: 'Arial',
      Lg_styles: [
        { id: 1, name: '夜晚街道', image: require('@/assets/Lg1.jpg') },
        { id: 2, name: '夜晚城市', image: require('@/assets/Lg2.jpg') },
        { id: 3, name: '晚街女孩', image: require('@/assets/Lg3.jpg') },
        { id: 4, name: '春水梨花', image: require('@/assets/Lg4.jpg') },
      ],


      //动漫头像模块
      AN_fileList: [],
      AN_imageSrc: '',
      activeNames_AN: [],
      AN_NegtivePrompt: '',
      isShowBar_AN: false,
      message_AN: "",
      userFaceRes: '',
      AN_lora: '',
      AN_requestId:null,
      AN_queueNum:null,
      AN_is_illegal:false,
      AN_prompt: "(masterpiece, top quality, best quality, official art), 4k,  ultra - detailed, amazing fine details，ultra high res,Fine art, perfect ligthing,",
      AN_negative_prompt: 'EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad-hands-5, (worst quality, low quality:1.4), ng_deepnegative_v1_75t, badhand-v4,( blurry:1.5), lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, jpeg artifacts, signature, watermark, username, blurry, artist name, (monochrome:1.1), bad anatomy,bad hands, ((monochrome)),((grayscale)),futanari,full-package_futanari,penis_from_girl,newhalf,nipplepierces,',
      portraits: [
        { id: 1, name: '水彩', image: require('@/assets/download.jpg') },
        { id: 2, name: '童话', image: require('@/assets/download1.jpg') },
        { id: 3, name: '彩墨', image: require('@/assets/download2.jpg') },
        { id: 4, name: '手办', image: require('@/assets/download3.jpg') },
        { id: 5, name: '未命名', image: require('@/assets/download4.jpg') },
      ],
      

      activeNames_TextToImg: [],
      randomize_TextToImg:["详细的背景，(平静的春夜景观)，在郁郁葱葱的绿色植物，美丽的景色，蔓生的夹竹桃盛开，蔓生的夹竹桃，清晨，日出的天空，美丽的云，斑驳的阳光，户外座位，一盏灯，宁静的湖，湖上的船，景深，杰作，最好的质量，超细节，非常美学，插图，完美的构图，复杂的细节，荒谬，忧郁的照明，光线，没有人，",
                           "电影照明, 一个女孩，走在巷子里穿着黑西装白领衬衫, 从上面看, 赛博朋克, 霓虹灯",
                           "电影海报页，(宣传海报)，初音未来，1女，独唱，人形机器人，青色头发，青色眼睛，歌手制服，耳机，WeirdOutfit风格，演唱会，日本武道厅，glowneon，发光，火花，闪电，阴影极简主义，(最佳质量)，(杰作)，细节，美丽的细节眼睛，完美的解剖，完美的身体，完美的脸，完美的头发，完美的腿，完美的手，完美的手臂，完美的手指，细节的头发，细节的脸，细节的眼睛，细节的眼睛，细节的眼睛，细节的衣服，细节皮肤，超细节，(全身)，(上身)，(高质量)，波普艺术，",
                           "性感的女人配上金色和绿色的机械身体，发光的绿色脸，发光的绿色皮肤，电影摄影，金色的蜘蛛网，雄伟，令人敬畏，鼓舞人心，特写，光滑，大气的雾霾，电影构图，柔和的阴影，国家地理风格，绿色的辉光，极其细致",
                           "美丽的天空，舞蹈的剪影，复杂的景观，最好的质量，柔和的颜色，淡蓝色调，美学的空灵，Effulgent，田园诗般的氛围，宁静，空灵的质量，电影般的体积，闪烁的灯光，发光的光环，全局照明，发光霓虹灯，各向异性过滤，无与伦比的作品，非常规的，超细节，HDR，",
                          "狐狸女孩，蓝眼睛，明亮的瞳孔，白色的头发，狐狸尾巴，多尾，狐狸耳朵，短发，直直的刘海，美丽，性感，苗条的身体，大胸部，宽臀部，敞开的衬衫，微短裤，(手臂在头后)，(看着观众)，(上身)",
                        "绿头发的女孩坐在树下的长凳上，女孩，裙子，独奏，户外，坐着，长发，看着观众，胸部，黑发，刘海，百褶裙，衬衫，白天，乳沟，短袖，高腰裙，锁骨，腮红，发带，树，绿色裙子，张开的嘴唇，黄眼睛，白衬衫，自然",
                      "全息图女孩，全息图，没有人类，只有轮廓，线框，发光的线框，漂亮的女孩，彩虹全息图，未来主义，透明身体，虚空身体，只有轮廓霓虹灯管，双尾，低双尾，蓬松的头发，彩虹色，小翅膀，美学网格，剪影，more棱镜背景，鲜艳的颜色，more细节:1.5",
                    "床上姿势，乳沟，背心，热裤，酒店，卧室，美丽的黑发，马尾，美丽的光蓝眼睛，多色眼睛，1_beautiful_girl，美丽，完美品质，完美解剖，杰作，月亮，夜晚，月光，美丽星空，",
                  "1少女，井生樱屋，touhou，独奏，女仆头饰，女仆，围裙，短袖，连衣裙，闭着嘴，白围裙，看着观众，女仆围裙，蓬松的短袖，褶边，蓬松的袖子，黑色连衣裙，褶边围裙，头发在两眼之间，户外，腮红，扔刀，刀，严肃的脸，上身，杰作，最好的质量，非常美学，荒诞",
                "(难以置信的细节:1.4),细致的皮肤，眼影，狐狸精，狐狸精耳朵，湿漉漉的，刚洗完澡，毛巾裙，公寓走廊",
              "温泉、薄雾、岩石、樱花、闪闪发光的薄雾、月光、天空、天上的星星、夜空的倒影、浮在水面上的灯笼、水中的花瓣、天上的月亮、动漫艺术风格、<lora:more_details:0.5>、世界主义、",
            "(游戏像素)、(像素美术)、(像素美术)、(焦点人物)、卡通、(高保真风格)、像素美术、可爱棉、(赛博朋克风格),女人，单身，长发，粉色头发，黑色项链，绿色眼睛，一只眼睛被头发遮住，侧卧，眼线，华丽的嘴唇，白色西装，领带，办公室女士，向下看,吸烟，拿打火机，点燃香烟，夜晚，被打火机照亮的脸",
          "自由女神像，半埋在沙漠中，破碎的石头，破碎的火炬，褪色的颜色，贫瘠的景观，压抑的天空，遥远的废墟，沙尘暴即将来临，荒凉，景深，杰作，最好的质量，超细节，非常美学，插图，完美的构图，复杂的细节，荒诞，忧郁的灯光，缕缕光线，",
        "1女人，独身，长发，粉色头发，黑色项链，绿色眼睛，一只眼睛被头发遮住，眼线，华丽的嘴唇，深蹲，教室，黑色蕾丝胸罩，校服，开放式衬衫，裙子，老师，腿上紧身衣，坐在桌子上，办公室制服，领带，优雅的脸，苗条，尖鼻子，一个美丽的27岁自然女人的特写照片的HDR照片的电影照片，",
      "1个女孩，独奏，动作镜头，瞄准，拿枪，护目镜，斗篷，运动模糊，沙漠，沙尘，轨迹，",
    ", lucy (cyberpunk), cyberpunk (series),不对称的头发，短发，蓝色头发，绿色头发，粉色头发，多色头发，蓝色眼睛，眼线，眼影，化妆，裸露的肩膀，肖像，看着观众，赛博朋克，hlpr，未来主义，全息图，故障，全息脸，ui，界面，节点，粒子，景深，散景，",
  "1名女子，单人，长发，粉色头发，黑色项链，绿色眼睛，眼线，华丽的嘴唇，电影，(杰作)，(最佳质量)，(超细节)，非常美学，插图，完美的构图，复杂的细节，荒谬，看向远处，详细的脸，臀部攻击，从下面，跳跃，运动模糊，景深，",
"日本美学和时尚装饰，((((deco)))) decora，东京时尚，五颜六色的衣服，上面环绕着其他五颜六色的物体，头上的娃娃，毛绒玩具，衣领，动物，脸上，身上的贴纸，头发装饰",
"描绘一个宁静的，原始的湖泊坐落在高耸的雪山在晚上。山峰应该在清澈的水中完美地反射，山峰沐浴在月亮的寒光中。湖泊四周有一片茂密的树林。沿着湖边增加一条蜿蜒的小路，让观众想象一个宁静的散步。包括与月亮一起出现的明亮星空，隐约可见银河系。用柔和、发光的光来增强场景，创造一种神奇的、几乎超凡脱俗的氛围。",
"Nahida /(Genshin impact)/,夜晚，可爱，躺在床上，复杂的细节， 红唇，完美的嘴唇，有光泽的皮肤，超级偶像的脸， 闭上眼睛，",
"鬼魂出没的草地，深夜，银色的月光，飘渺的薄雾，精灵狐狸，精致的植物，反射的溪流，消失的路径，神秘的生物，阴影景观，复杂的草，软灰色和淡紫色，阴霾，复杂的背景，发光的元素，宁静的神秘",
"诱人地躺在车上，诱人的衣服，露出腿，看着观众，项链，红色的车，看着相机，建筑，头发，大乳房，诱人的姿势，华丽的眼睛，明亮的眼睛，从上面的镜头",
"1个女孩，悲伤的表情，被樱花覆盖，(被樱花枝包裹)，被樱花枝超越，穿着一件空白的赛博朋克风格的和服，giesha, cyberware，从她身上长出来的樱花枝，夜晚昏暗的灯光，干净，美丽，远离镜头，(发光的花瓣)，(发光的樱花花瓣)，上身拍摄，全身拍摄，简单的背景，",
"帆布油画，详细的背景，(宁静的雪山)，冰雪覆盖的橡树，没有叶子，地上有雪，美丽的景色，冬青，槲寄生，清晨，日出的天空，橙色的云，红色的天空，斑驳的阳光，美丽的，雪中的火车轨道，鸟巢，死树，景深，插图，完美的构图，复杂的细节，荒谬，忧郁的灯光，缕缕的光，没有人，(baroque style: 0.8), (Bob Ross style: 0.6)",
"长发，金发，头发蝴蝶结，头饰，蓝眼睛，耳环，橙色颈链，锁骨，橙色水手领，蓝色领结，白衬衫，白紧身衣，肘部手套，白手套，百褶裙，橙色裙子, 微笑，安静，向前倾斜，站立，室内，手指对嘴，",
"详细的背景，(温暖的春天景观)，滨江街，盛开的樱花树，樱花树，白天，斑驳的阳光，宁静的河流，迷人的咖啡馆，户外座位，景深，杰作，最好的质量，超细节，非常美学，插图，完美的构图，复杂的细节，荒诞，忧郁的灯光，",
"女孩，独唱，华丽的嘴唇，优雅的脸庞，光粒子，粒子，阴影，Kochou Shinobu，恶魔杀手，细节嘴唇，黑色细节瞳孔，浮肿嘴唇，宽嘴唇厚嘴唇，大嘴唇，大嘴唇，腮红，上身，屁股，从后面，",
"美丽的雄鹰飞翔，翅膀上划过天空，",
"修女，黄眼睛，浅蓝色头发，白手套，手肘手套，教堂，户外，十字架，十字架首饰，白色绑腿，大胸，刺绣绑腿，分开的嘴唇，富有表情，淡淡的微笑，白色习惯，乳沟，盖胸，短裙",
"女人，独身，长发，粉色头发，黑色项链，绿色的眼睛，眼线，华丽的嘴唇，深蹲，马尾辫，和服，印花，发梢，和服，白色和服，日本的节日，夜晚，空中烟花，淡淡的微笑，看着观众",
"性感裸女配金色和绿色的机械蜘蛛腿，电影摄影，金色的蜘蛛网，磅礴，令人敬畏，鼓舞人心，特写，光滑，铁锈，质朴，棕褐色，大气的薄雾，电影构图，柔和的阴影，国家地理风格，绿色的辉光",
"面具，gaz面具，斗篷，兜帽，背面gaz红色坦克，手持火焰喷射器武器，火焰痕迹，侧视图，深色背景，黑色背景，牛仔射击，护目镜，余烬，杰作，质量最好，非常美学，荒诞，火焰背景，包裹在火焰中",
"最近的电影照明最小的清洁机械机器人,透明盔甲，水,冰冻,风, 雪",
"详细的背景，英灵殿，巨大的英灵殿之门，金门，错综复杂的门，封闭的门，北欧人，雪景中的极光，美丽的景色，巨大的山脉，梦幻般的，清晨，日落的天空，美丽的云，斑驳的阳光，户外座位，雪山，白云，景深，杰作，最好的质量，超细节，非常美学，插图，完美的构图，复杂的细节，荒诞，忧郁的照明，光线，RAW摄影，镜头光晕，低广角，，(细节清晰度:1.1)，佳能EOS 5D Mark IV，杰作，(标志性照片:1.4)，(视觉叙事:1.2)，胶片纹理，获奖",
"1个女人，绿色长发，活泼的表情，和服，树叶和树的图案，4个球体漂浮在她周围(火球，风球，水球，土球)，在森林里，杰作，最好的质量，非常美学，荒诞，复杂的细节",
"1girl, yamagishi yukako, jojo no kimyou na bouken, aged up, cleavage, masterpiece, best quality",
"辫子，发带，红丝带，珠宝，白色的领结，胸针，蓝色夹克，长袖，机械手，白色连衣裙，长连衣裙，紫罗兰evergarden，安静，连衣裙提升，蕾丝内裤，吊袜带，腮红，室内，",
"1个女孩，独奏，广角镜头，看着相机，心形的手，心的手，可爱，长发，粉红色的头发，粉红色的丝带，黑色蕾丝紧身衣，眼线，电影，(杰作)，(最佳质量)，(超细节)，非常美学，完美的构图，复杂的细节，荒诞，详细的脸，(动漫，杰作，复杂的:1.3)，(最佳质量，聘请纹理，高细节:1.2)，(4k)，(令人难以置信的细节:1.4)，粉红色的背景，环境照明",
"hu tao \\(genshin impact\\), boo tao \\(genshin impact\\),1girl, solo, long hair, looking at viewer, long sleeves, hat, very long hair, full body, flower,  thighs, shorts, socks,  short shorts, thick thighs, tassel, hat flower,",
"1girl, blurry shading, huge cyan crystalized eyes, (pointy nose:0.7), black skin, long eyelashes, pearlescent eyeshadow, extremely close shot, (Gaussian blur), masterpiece, absurdity",
"original, masterpiece, best quality, realism, hyper realistic (ultrahigh resolution textures) bokeh, solo sexy young woman, Kamisato Ayaka Kuki Shinobu,  Genshin Impact, looking at viewer, indoors, blush, medium breasts, glowing ice blue eyes, hair between eyes, torogoa, square half rim glasses, hairclip,  wearing intricate Tron Legacy bodysuit, skin tight body suit glowing accents, sexy evil poses,, wet, on bed at night, soft evil smile, evil expression",
"SouthParkRay  1girl, frieren, sousou no frieren, elf,  South Park background",
"low light, back light, fairy light, cave, crystal_pillars, colorful, 1girl, monochrome skin, beautiful face, contrast lighting, full body, blurry barefeet, lying on rock, gold eyes, white long hair, sliver floral pattern white dress, close shot, depth of field, Gaussian blur, dynamic lighting, lens flare, ray tracing, masterpiece",
"masterpiece, best quality,1girl,nahida \\(genshin impact\\),school uniform,ex micro miniskirt,shot shirt,sleeveless, bow,white thighhighs,standing in school gate,(pixel art:1.1), retro artstyle,",
"masterpiece, best quality,1girl,ganyu \\(genshin impact\\),underwear, solo, panties, panty pull, toilet, white panties, graffiti, tiles, tile wall, breasts, tank top, sitting, bathroom, cleavage, against wall, tile floor, indoors,  slippers, toilet use, closed eyes, closed mouth, knees together feet apart, camisole, barefoot, toilet paper,",
"1girl, neurosama，独立虚拟youtuber，悲伤，评分:一般，:3，^_^，绷带，蓝色眼睛，蓝色水手领，蓝色裙子，腮红，边框，蝴蝶结，领结，电缆，开襟，人物名字，闭上眼睛，光标，衍射刺，电源插头，睫毛，浮动，浮动头发，gears，绿色蝴蝶结，绿色领结，头发装饰，半色调，手光标，心形，心形头发装饰，心形印花，沙漏，浅棕色头发，长发，看着观众，抬头看，放大镜，麦克风，像素艺术，像素心形，插头，加号，水手领，校服，serafuku，裙子，solo，星星(符号)，星星印花，虚拟youtuber，",
"一大杯冒着泡沫的绿色液体，黄色的大眼睛，不祥地放在黑暗的地牢中央。液体发出嘶嘶声和气泡，而眼睛漂浮和眨眼，给人一种警惕的、怪异的感觉。风格令人难以忘怀的黑暗幻想，VHS，恐怖，80年代的恐怖与鲜艳的色彩,灵感来自Sean Aaberg的朋克美学，",
"cake female head on pillow smiling, skin made of marzipan, hair made of chocolate, eyes made of caramel, tears from milk cream, on a plate, piece of cake from face cut off, layers of sponge and cream visible, cranberry cream pouring out. gourmet, pastry art, intricate, maximalist, light and shadow, photo by Irwin Penn",
"One object, gray background, grapes made out of tennis balls",
"Ultra realistic bull, horrified expression upon viewing a steak on a plate, exaggerated cartoon style",
"Create an image of a whimsical creature that resembles a small kitten with the texture and color of a ripe strawberry. The creature's body and face are covered in strawberry red, with characteristic seed-dotted skin. It has green leaf-like structures mimicking ears and a ruff around its neck, as if the top of a strawberry was placed on its head. This kitten-like entity has round, innocent eyes, a small pink nose, and delicate white whiskers. Its pose is seated, with one paw slightly raised as if it were about to take a gentle step, and its tail curls behind its body, appearing plump and strawberry-like as well. Surrounding the creature are actual strawberries of a similar shade of red and with green caps, scattered on a glossy wooden surface. In the soft-focused background, hints of indoor plants in shadows create an almost ethereal setting. The overall atmosphere is quaint and charming, evoking a sense of magical realism.",
"a man posing for a picture in the street, close shot, portrait, wearing tuxedo black suit with a yellow duck drawn on it",
"ASCIIa cat, wearing a tuxedo, looking for love, holding a red rose looking at viewer, realistic, high class vibes, warm tones, soft focus, bokeh background",
"Realistic photograph of a cat in comically ill fitting bee-costume-, (holding sign with text:'fix buzz PLEZ':1.4 ), in the style of ilya kuvshinov, seth macfarlane, meticulous photorealistic still lifes, (holding sign that reads 'fix buzz plez' ),  david teniers the younger, charming character illustrations, use of screen tones, 32k, subsurface scattering, Photorealistic, Hyperrealistic, analog style, realistic, film photography, soft lighting, heavy shadow",
"A breathtaking image of a white human skull with liquid gold being poured on its head, highly detailed, high quality surreal image, movie poster style",
"A breathtaking image of a human eyeball contained within a small glass box on a desk, closeup, macro, highly detailed, high quality surreal image, movie poster style",
"high quality, 8K Ultra HD, Imagine a vibrant canvas illuminated by a cascade of colorful binary code, forming the silhouette of a captivating woman, The dynamic lines and patterns, reminiscent of a digital dance, weave together to create a harmonious fusion of technology and art, The binary code, in hues ranging from electric blues to radiant reds and vivid greens, breathes life into the woman's form, Her silhouette emerges as a synthesis of the digital and the organic, a testament to the seamless integration of technology and beauty in your masterful creation, by yukisakura, high detailed,",
"(Digital Artwork:1.3) of (Sketched:1.1) octane render of a mysterious dense forest with a large (magical:1.2) gate (portal:1.3) to the eternal kingdom, blade runner, intricate (vine:1.2), massive tree in liquid metal, realistic digital painting portrait, shot at 8k resolution, petrol liquid, pastel color, splash art, blue and purple magic universe, light engrave in intricate details, (light particle:1.2), (game concept:1.3), (depth of field:1.3), global illumination,Highly Detailed,Trending on ArtStation",
"portrait half body female Russian concubine with slim curvy body painting by gaston bussiere, greg rutkowski, yoji shinkawa, yoshitaka amano, tsutomu nihei, donato giancola, tim hildebrandt, oil on canvas, trending on artstation, featured on pixiv, cinematic composition, extreme detail, metahuman creator",
"芭蕾舞演员，女性，白色芭蕾舞裙，芭蕾舞姿势，舞蹈，优雅，室内，自然光，紧身衣，芭蕾舞鞋，优雅，年轻人，安详的表情，闭上眼睛，白色房间，阳光，高加索，站立，艺术，表演服装，独奏演员",
"A mid shot photograph of Ryan Gosling dressed in an action pose in a magical girl outfit SailorMoon, blue skirt, white shirt, white gloves, large red bow on chest, background Tokyo City, long blonde hair",
"幻想艺术杰作,从下面,低角度,奇幻森林,微粒的光挂在空中,神奇的感觉,色彩斑斓的鲜花盛开,绿色的藤蔓,健康的植物,树木制成的机甲穿过森林,他比树高,头上的云,从下面看,他是由地球和石头,他的身体是由树叶、树枝和树干,他在叶子和葡萄树和鲜花覆盖,他看起来向远处,神秘的云在天空漩涡,萤火虫在底部边缘飞舞，空气中弥漫着魔力，",
"(8k, RAW photo, highest quality), beautiful (white skin woman), close up, dress, (detailed eyes:0.8), (looking at the camera:1.4), (high-resolution image), (best shadow) , skin textures, intricate details, interior, studio,",
"电影电影仍空灵梦幻的概念艺术的杰作写实风景照片，一个宏伟的白色和金色的高精灵宫殿和法师塔在魔法森林，天，远距离拍摄，SFW。宏伟的，天上的，空灵的，史诗般的，雄伟的，神奇的，幻想艺术，封面艺术，梦幻。浅景深，小插图，高度详细，高预算，散景，电影，喜怒哀乐，史诗，华丽，电影颗粒，颗粒",
"桌上摆着一杯马提尼杯带胡须的巧克力冰淇淋，奶昔有一张可爱的白猫脸，巧克力的眼睛，令人振奋的泡泡气氛，",
"a women with eyes that are looking out of a hole in the ground with red balls floating above her, Anka Zhuravleva, pop surrealism, big eyes, poster art OverallDetail",
"特写镜头，毛茸茸的可爱的白猫在玩泡泡，爪子垫，彩虹的眼睛，闪闪发光的泡泡，令人振奋的气氛",
"星光灿烂的天空，神奇的智能手机捕捉到充满活力的彩虹，彩色的玫瑰和闪闪发光的水晶鹅卵石的本质。这个天体装置沐浴在星星、流星、银色新月和银河系的光芒中，营造出梦幻般的氛围。它编织了丰富的色彩，从鲜艳的红色过渡到深紫色，以及粉红色，黄色，蓝色和绿色的色调。",
"高分辨率，巨大的虎斑猫，睡在云上，玫瑰色的云，天空，黎明，",
"一个拟人化的企鹅坐在咖啡馆里看书和喝咖啡的电影照片。",
"女孩，邪恶的微笑，戏剧性的灯光，束腰，渔网，连裤袜，裸露的肩膀，伸展的腿，",
"black Spider Man costume,1girl,cleavage,waist up,solo,lips,(shiny skin:1.2),(White hair:1.1),spider web print,gloves,black Spider Man costume:0.7>,blury background,depth of field,christmas night,christmas decoration,indoor,a woman in the cabin,",
"RAW photograph,official art,enhancing contrast,(ultra-wide panoramic full body movie shot:1.4),highlights,shadowtransparent,ray tracing,well-lit,looking at viewer,//stunning delicate slender pretty face,a skinny tall maiden,gloss pale girlish skin texture,//beautiful shiny low-tied long hair,blunt bangs,parted lips,(elegant dignified,giggle,thick pubic hair),//((baseboll cap,school uniform,collared shirt,blazer,necktie,cardigan,plaid skirt,checkered skirt,dark pantyhose,beautiful high heeled evening shoes,pornographic,breasts out,open clothes)),//a pair of well-defined pointy round huge breasts,sagging breasts,//narrow waist,(extra-gorgeous long legs ",
"，有光泽的皮肤，(白皮肤:1.3)，(破连裤袜:1.2)，连裤袜，solo，辫子，高跟鞋，长发，双辫子，手套，黑发，全身，向后看，紧身衣，化妆，指甲油，水手衬衫，高腿，",
" standing split, flexible, leg lift, 1girl, leg up, kneepits, split, spread legs, highres, very long hair, ahoge, braid, tiara, hair ribbon, red ascot, cleavage, shrug (clothing), shoulder armor, white dress, short sleeves, white gloves, pleated skirt, red skirt, outdoors, smile, open mouth, pecorine \\(princess connect!\\) ",
],
      changeTo: false,
      userFaceValue: 55,
      isShowBar: false,
      message: '',
      pre: '',
      selectedFile: '',
      activeTab: 'first',
      userFacePrompt: '',
      picShare: {},
      // 防止重复分享  0表示可以分享，1表示不可重复分享 , 2表示图片还未生成，3表示正在分享
      shareFlag: 2,
      shareFlag_Qr: 2,
      shareFlag_AN: 2,
      shareFlag_Lg: 2,
      //生成按钮同步
      isGenerating: false,
      //获取tag按钮同步
      //生成动漫头像按钮同步
      isGenerate_AN: false,
      //生成Qrcode 按钮同步
      isGenerating_Qr: false,

      isGenerating_Lg:false,
      QrcodeFlag: 0,
      pictureIfo2: {},
      QrcodeTemplate: '01',
      pictureBase64: '',
      
      dialogVisible_gotoLogin:false,
      inputNegPromptValue: false,
      inputPromptValue: true,
      activeTab1: 'Cloth',
      activeNames: [],
      clothData: { "日常服装": { "女式衬衫": "blouse", "有领衬衫": "collared_shirt", "水手服衬衫": "sailor_shirt", "T恤": "t-shirt", "背心(居家)": "tank_top", "背心(正式)": "vest", "休闲": "casual", "卫衣": "hoodie", "居家服": "homewear", "睡衣": "pajamas", "印花睡衣": "print_pajamas", "波点睡衣": "polka_dot_pajamas", "西装外套": "blazer", "大衣": "overcoat", "皮草大衣": "fur_coat", "夹克衫": "jacket", "皮夹克": "leather_jacket", "牛仔夹克": "denim_jacket", "毛衣": "sweater", "高领毛衣": "turtleneck_sweater", "罗纹毛衣": "ribbed_sweater", "毛衣背心": "sweater_vest", "露背毛衣": "backless_sweater", "米色毛衣": "beige_sweater", "棕色毛衣": "brown_sweater", "连帽毛衣": "hooded_sweater", "露肩毛衣": "off-shoulder_sweater", "菱紋毛衣": "ribbed_sweater", "条纹毛衣": "striped_sweater", "处男杀手毛衣": "virgin_killer_sweater", "羽绒服": "puffer_jacket", "雨衣": "raincoat", "连衣裙": "dress", "长连衣裙": "long_dress", "露肩连衣裙": "off-shoulder_dress", "围裙连衣裙": "sweater_dress", "夏日长裙": "summer_dress", "短裙": "skirt", "超短裙": "microskirt", "迷你裙": "miniskirt", "分层裙": " layered skirts", "百褶裙": "pleated_skirt", "短铅笔裙": "pencil_skirt", "蓬蓬裙": "bubble_skirt", "芭蕾舞裙": "tutu", "绷紧的连衣裙": "taut_dress", "铅笔裙": "pencil_dress", "牛仔裙": "denim_skirt", "雪紡裙": "chiffon_skirt", "花边裙子": "frilled_skirt", "毛皮镶边短裙": "fur-trimmed_skirt", "蕾絲短裙": "lace_skirt", "缎带饰边短裙": "ribbon-trimmed_skirt", "短裤": "shorts", "条纹短裤": "striped_shorts", "吊带短裤": "suspender_shorts", "牛仔短裤": "denim_shorts", "瑜伽裤": "yoga_pants", "牛仔裤": "jeans", "七分裤": "capri_pants", "灯笼裤": "bloomers" }, "袜子鞋子": { "裸腿": "bare_legs", "裤袜": "pantyhose", "黑丝裤袜": "black_pantyhose", "白丝裤袜": "white_pantyhose", "肉丝裤袜": "nude_pantyhose", "油亮裤袜": "shiny_pantyhose", "有腿环的裤袜": "thighband_pantyhose", "破损的裤袜": "torn_pantyhose", "网袜(材质)": "fishnets", "丝袜": "stockings", "渔网袜": "stirrup_legwear", "长筒袜": "thighhighs", "白色长筒袜": "white_thighhighs", "黑色长筒袜": "black_thighhighs", "粉色长筒袜": "pink_thighhighs", "吊带袜": "garter_straps", "腰带(吊带袜的)": "garter_straps", "损坏了的长筒袜": "torn_thighhighs", "透明的袜子": "see-through_legwear", "花边袜": "frilled_legwear", "蕾丝边袜": "lace-trimmed_legwear", "泡泡袜": "loose_socks", "踝袜": "ankle_socks", "腿套|暖腿袜": "leg_warmers", "横条短袜": "striped_socks", "连体黑丝": "black_bodystocking", "连体白丝": "white_bodystocking", "赤脚": "barefoot", "没有鞋子": "no_shoes", "脱下的鞋子": "shoes_removed", "单鞋": "the_only_shoe", "脱下的鞋": "shoes_removed", "一只脚没穿鞋子": "single_shoe", "高跟鞋": "high_heels", "运动鞋": "sneakers", "裸足凉鞋": "barefoot_sandals", "木屐凉鞋": "clog_sandals", "乐福鞋(小皮鞋)": "black_loafers", "靴子": "boots", "大腿靴": "thigh_boots", "高跟靴": "high_heel_boots", "雪地靴": "snow_boots", "皮靴": "leather_boots", "马丁靴": "doc_martens", "旱冰鞋": "roller_skates", "脚环": "anklet", "镣铐": "shackles" }, "佩饰道具": { "颈部饰品": "choker", "黑色颈圈": "black_choker", "领巾": "neckerchief", "红领巾": "red_neckerchief", "领带": "necktie", "蝴蝶结领带": "bowtie", "挂在脖子上的耳机": "headphones_around_neck", "脖子上护目镜": "goggles_around_neck", "颈铃": "neck_bell", "颈部纹身": "neck_tattoo", "十字架项链": "cross_necklace", "珍珠项链": "pearl_necklace", "心形项链": "labcoat", "骷髅项链": "skull_necklace", "花项链": "flower_necklace", "贝壳项链": "shell_necklace", "金项链": "gold_necklace", "羽毛项链": "feather_necklace", "格子围巾": "plaid_scarf", "条纹围巾": "striped_scarf", "格纹围巾": "checkered_scarf", "印花围巾": "print_scarf", "波点围巾": "polka_dot_scarf", "披肩": "capelet", "棒球帽": "Baseball  cap", "针织帽": "Beanie", "遮阳帽": "Visor  cap", "圆顶礼帽": "Bowler  hat", "侦探帽": "Fedora", "牛仔帽": "Cowboy  hat", "厨师帽": "Chef  hat", "军官帽": "Military  hat", "圣诞帽": "Santa  hat", "派对帽": "Party  hat", "小丑帽": "Jester  cap", "女巫帽": "witch  hat", "贝雷帽": "beret", "鸭舌帽": "peaked  cap", "草帽": "Straw  hat" }, "制服COS": { "职场制服": "business_suit", "校服": "school_uniform", "水手服": "serafuku", "护士": "nurse", "警服": "police_uniform", "海军制服": "naval_uniform", "陆军制服": "military_uniform", "女仆装": "maid", "围裙": "apron", "厨师工装": "chef_uniform", "宇航服": "space_suit", "实验服": "labcoat", "啦啦队服": "cheerleader", "燕尾服": "tuxedo", "礼服": "evening_gown", "婚纱": "wedding_dress", "巫女服": "miko", "修女服": "nun_cloth", "兔子服装": "bunny_costume", "猫系服装": "cat_costume", "皮套狗": "dog_costume", "熊套装": "bear_costume", "圣诞风格服装": "santa_costume", "盔甲": "armor", "比基尼盔甲": "bikini_armor", "穿着全套盔甲的": "full_armor", "板甲": "plate_armor", "日本铠甲": "japanese_armor", "动力装甲": "power_armor", "机甲": "mecha", "头盔": "helmet", "头盔(日式)": "kabuto", "無肩甲盔甲": "off-shoulder_armor", "肩甲": "shoulder_armor", "日本弓道護胸甲": "muneate", "胸甲": "breastplate", "腹甲": "faulds", "胫甲": "shin_guards", "装甲靴": "armored_boots", "道袍": "Taoist robe", "长袍": "robe", "混合长袍": "robe_of_blending", "斗篷": "cloak", "羽衣": "hagoromo" }, "传统服饰": { "中国服饰": "china_dress", "中国风": "chinese_style", "旗袍": "china_dress", "印花旗袍": "print_cheongsam", "汉服": "hanfu", "汉服唐风": "hanfu, tang style", "上杉": "upper shan", "长上杉": "long upper shan", "齐胸破裙": "chest po skirt", "齐胸褶裙": "chest pleated skirt", "系带": "waistband", "披帛": "pibo", "汉服宋风": "hanfu, song style outfits", "短杉": "short shan", "长杉": "long shan", "宋抹": "songmo", "百褶裙": "pleated skirt", "汉服明风": "hanfu, ming style outfits", "短袄": "short coat", "长袄": "long coat", "马面裙": "mamian skirt", "交领": "overlapping collar", "圆领": "round collar", "立领": "standing collar", "方领": "square collar", "云肩": "overlapping collar", "刺绣": "embroidery", "织金": "woven gold", "妆花": "makeup flower", "补服": "bufu", "和服": "kimono", "印花和服": "print_kimono", "衣带(和服用)": "obi", "浴衣": "yukata", "韩服": "hanbok", "朝鲜服饰": "korean_clothes", "西部风格": "western", "德国服装": "german_clothes", "哥特风格": "gothic", "洛丽塔风格": "gothic_lolita", "拜占庭风格": "byzantine_fashion", "热带特征的": "Tropical", "印度风格": "indian_style", "越南校服（奥黛）": "Ao_Dai", "阿伊努人的服饰": "ainu_clothes", "阿拉伯服饰": "arabian_clothes", "埃及风格服饰": "egyptian_clothes" }, "运动泳装": { "运动服": "gym_uniform", "体操服": "athletic_leotard", "足球服": "volleyball_uniform", "网球衫": "tennis_uniform", "棒球服": "baseball_uniform", "棒球夹克": "letterman_jacket", "排球服": "volleyball_uniform", "骑行套装": "bikesuit", "泳装": "swimsuit", "学校泳装（死库水）": "school_swimsuit", "赛用泳衣": "competition_swimsuit", "连体泳衣": "casual_one-piece_swimsuit", "高开衩的泳衣": "highleg_swimsuit", "一体式泳衣": "one-piece_swimsuit", "比基尼": "bikini", "微比基尼": "micro_bikini", "女仆比基尼": "maid_bikini", "水手服款比基尼": "sailor_bikini", "贝壳比基尼": "shell_bikini", "运动比基尼": "sports_bikini", "系绳比基尼": "string_bikini" } },
      hairsData: { "头发颜色": { "绿头发": "green hair", "粉色头发": "pink hair", "红头发": "red hair", "铂金色头发": "platinum blonde hair", "青色头发": "azure hair", "水蓝色头发": "aqua hair", "红宝石色头发": "ruby hair", "两色头发": "two-tone hair", "多色的头发": "multicolored hair", "渐变头发": "gradient hair", "分色头发": "split-color hair", "彩虹头发": "rainbow hair" }, "头发长度": { "长发": "long hair", "很短的头发": "very short hair", "短发": "short hair", "后短发，前长发": "short hair with long locks", "中等头发": "medium hair", "很长的头发": "very long hair", "超级长的头发": "absurdly long hair" }, "马尾": { "马尾": "ponytail", "双马尾": "twintails", "高双马尾": "canonicals", "低双马尾": "low twintails", "披肩单马尾": "one side up", "披肩双马尾": "two side up", "短马尾": "short ponytail", "侧马尾": "side ponytail" }, "辫子": { "辫子": "braid", "法式辫子": "french braid", "辫子头发": "braiding hair", "双辫子": "twin braids", "三股辫": "braid", "短辫子": "short braid", "长辫子": "long braid", "辫子刘海": "braided bangs", "辫式发髻": "braided bun", "麻花辫马尾": "braided ponytail", "法式冠编发": "crown braid", "多股(麻花)辫": "multiple braids", "披在一侧的单条辫": "side braid", "披在两侧的两条辫": "side braids", "单股辫": "single braid", "两条辫子": "twin braids" }, "刘海": { "进气口发型": "hair intakes", "发瓣": "hair flaps", "刘海": "bangs", "空气刘海": "air bangs", "齐刘海": "blunt bangs", "侧面空气刘海": "side blunt bangs", "中分刘海": "parted bangs", "斜刘海": "swept bangs", "不对称刘海": "asymmetric bangs" }, "其他发型": { "眼睛之间的头发": "hair between eyes", "头发覆盖一只眼": "hair over one eye", "头发遮住眉毛": "hair over one eyebrow", "透过头发可见腮红": "blush visible through hair", "透过头发可见眼睛": "eyes visible through hair", "头发撩到耳后": "hair behind ear", "披肩发": "hair over shoulder", "头发遮住三点": "hair censor", "头发披在胸上": "hair over breasts", "直发": "straight hair", "卷发": "curly hair", "波浪卷": "wavy hair", "钻头(配双)": "drill hair", "姬发式(齐刘海后长黑发": "hime cut", "齐而短头发(波波发": "bob cut", "公主发型": "princess head", "上半部分束起": "Half-up", "额头": "forehead", "扎过的头发": "tied hair", "低扎头发": "low tied hair", "多扎头发": "multi-tied hair", "丸子头": "double bun", "圆发髻": "hair bun", "芭蕾髻": "ballet hair bun", "尖头头发": "pointy hair", "羽毛头发": "feather hair", "弓形头发": "bow-shaped hair", "孤颈头发": "lone nape hair", "变换发型": "alternate hairstyle", "与原设不同头发长度": "alternate hair length", "鬓角": "sideburns", "长鬓角": "long sideburns", "侧边发辫": "sidelocks", "秃头": "bald", "鸟窝头|爆炸头": "afro", "尖刺的头发": "spiked hair" } },
      actionsData: { "热门动作": { "行走": "Walking", "转身": "Turning", "摆动": "Swinging", "弯腰": "Bending", "高抬腿": "High leg lift", "拉伸": "Stretching", "手势": "Gestures", "双手放腰": "Hands on waist", "站立": "standing", "躺": "on back", "趴": "on stomach", "跪": "kneeling", "侧卧": "on side", "靠在物体上": "leaning on object", "躺着的": "lying", "胎儿姿势(躺)": "fetal position" }, "手部动作": { "手放在嘴边": "hand to mouth", "爪手势": "claw pose", "招财猫手势(下弯手腕)": "paw pose", "狐狸手势": "fox shadow puppet", "手指枪手势": "finger gun", "胜利手势": "v", "双 v": "double v", "翘大拇指": "thumbs up", "食指抬起": "index finger raised", "国际友好手势": "middle finger", "用手指做出笑脸": "fingersmile", "擦眼泪": "wiping tears", "OK手势": "ok sign", "遮阳手势": "shading eyes", "嘘(手势)": "shushing", "手指伸进嘴里": "finger in mouth", "手指卷着头发": "hair twirling", "双手手指交叉|双手紧握": "hands clasped", "握着头发|手指绕着头发": "holding hair", "扶眼镜": "adjusting eyewear", "拨弄手套": "adjusting gloves", "理头发": "adjusting hair", "整理帽子": "adjusting hat", "掀起自己的衣物": "lifted by self", "拉起掀起卷起衬衫": "shirt lift", "指掀起裙子时形成的篮子形状": "skirt basket", "剥下裙子胸口的部分": "dress pull", "往上剥开的比基尼": "bikini lift", "将裙子掀上去": "skirt lift", "往上掰衣物的吊带": "strap lift", "起风效果|上升气流": "wind lift", "拉着口罩": "mask pull", "拉着裤子": "pants pull", "手抓裆部": "crotch grab", "用手遮住胸部": "covering chest by hand", "捏脸颊": "cheek pinching", "戳脸颊": "cheek poking", "摸下巴": "chin stroking", "展现魅力的姿势": "curvy", "手持辫子": "bunching hair" }, "腿部动作": { "张腿": "spread legs", "两腿并拢": "legs together", "二郎腿": "crossed legs", "I字摆腿": "standing split leg up", "屈膝礼（女仆行礼）": "curtsy", "双腿之间的手": "hand between legs", "稍息": "open stance", "挡住关键部位的腿": "convenient leg", "V字张腿": "spread legs", "用双腿夹住": "leg lock", "双腿抬起": "legs up", "双腿交叉站姿": "watson cross", "膝盖合并，两脚分开": "knees together feet apart", "膝盖上有动物": "animal on lap", "手放在自己的膝盖上": "hand on own knee", "顶起膝盖": "knee up", "膝盖蜷到胸前": "knees on chest", "膝盖顶到胸部": "knees to chest", "鸭子坐": "wariza", "正坐": "seiza", "跨坐": "straddling", "侧身坐": "yokozuwari", "蹲下，张开双腿": "squatting, open legs", "一只膝盖": "one knee", "下跪": "kneeling", "四肢着地": "all fours", "凹版姿势": "gravure pose", "高踢": "high kick", "泡脚": "soaking feet" }, "多人动作": { "胸部互碰|胸顶着胸": "asymmetrical docking", "背对背": "back-to-back", "眼对眼（对视）": "eye contact", "二人面对面(脸贴得很近)": "facing another", "喂食": "feeding", "口内手指": "finger in another's mouth", "牵手": "holding hands", "拥抱": "hug", "即将接吻": "imminent kiss", "递食物": "incoming food", "递礼物": "incoming gift", "等待接吻|献吻": "incoming kiss", "壁咚": "Kabedon", "膝枕": "lap pillow", "掏耳勺": "mimikaki", "公主抱": "princess carry", "舌头": "tongue", "吐舌头": "tongue out", "小舌头|口盖垂|悬雍垂": "uvula", "咬耳朵": "ear biting" }, "常见动作": { "化妆": "applying makeup", "洗澡": "bathing", "入浴": "bathing", "咬": "biting", "鞠躬": "bowing", "刷牙": "brushing teeth", "吹泡泡": "bubble blowing", "追逐": "chasing", "打扫": "cleaning", "攀爬": "climbing", "烹饪": "cooking", "哭": "crying", "拥抱": "cuddling", "跳舞": "dancing", "潜水": "diving", "拉弓": "drawing bow", "喝": "drinking", "驾驶": "driving", "(意外)掉落": "dropping", "弄干(浴后)": "drying", "双持": "dual wielding", "吃饭": "eating", "做运动": "exercise", "战斗姿态|摆着架势的": "fighting stance", "射击": "firing", "钓鱼": "fishing", "秀肌肉": "flexing", "飞踢": "flying kick", "梳头": "hair brushing", "撩头发": "hair tucking", "吊起来的": "hanging", "击打": "hitting", "在想象的": "imagining", "跳跃": "jumping", "踢": "kicking", "演奏乐器": "playing instrument", "钢管舞": "pole dancing", "祈祷": "praying", "展示": "presenting", "挥拳": "punching", "推搡": "pushing", "阅读": "reading", "骑": "riding", "奔跑": "running", "缝纫": "sewing", "购物": "shopping", "淋浴": "showering", "唱歌": "sing", "扑克牌": "playing card", "打游戏": "playing games" }, "眼部动作": { "面向镜头": "facing_viewer", "看向镜头": "looking_at_viewer", "眼神接触": "eye-contact", "盯着看": "eyeball", "凝视": "staring", "回眸": "looking_back", "人物倾斜": "gradient", "人物视角向下看↘": "looking_down", "人物视角抬头看↗": "looking_up", "面向别处": "facing_away", "看向侧面": "looking_to_the_side", "看着别处": "looking_away", "遥望": "looking_afar", "向外看": "looking_outside", "歪头": "head_tilt", "低头": "head_down", "照镜子": "looking_at_mirror" } },
      expressionData: { "眼睛相关": { "眼袋": "bags under eyes", "缠着绷带的单眼": "bandage over one eye", "遮眼|眼罩": "blindfold", "眼罩": "eyepatch", "眼影": "eyeshadow", "医用眼罩": "medical eyepatch", "眼睛上的疤痕": "scar across eye", "闭眼": "eyes closed", "半闭双眼": "half closed eyes", "眯起眼睛": "narrowed eyes", "拉下眼睑的鬼脸": "akanbe", "睁大眼睛": "wide eyed", "一只眼睛闭着": "one eye closed", "蒙眼": "blindfold", "眨眼": "wink", "失去高光的眼睛": "empty eyes", "翻白眼": "rolling eyes", "眼泪": "tears", "锐利的眼": "slanted eyes", "低眉顺眼": "tareme", "上翘的眼睛": "upturned eyes" }, "瞳孔": { "瞳孔": "pupils", "明亮的瞳孔": "bright pupils", "异色瞳": "heterochromia", "竖的瞳孔/猫眼": "slit pupils", "蛇瞳孔": "snake pupils", "瞳孔闪光": "pupils sparkling", "爱心形瞳孔": "heart shaped pupils", "钻石形状瞳孔": "diamond shaped pupils", "五角星形状瞳孔": "star shaped pupils", "瞳孔散大": "dilated pupils", "没有瞳孔": "no pupils", "缩瞳": "constricted pupils", "眼睛里的星星": "star in eye", "星形瞳孔": "star shaped pupils", "X形瞳孔": "x shaped pupils", "水平瞳孔": "horizontal pupils", "虚线的眼睛": "dashed eyes", "蝴蝶形瞳孔": "butterfly shaped pupils", "菱形瞳孔": "diamond shaped pupils", "长方形瞳孔": "rectangular pupils", "方形瞳孔": "square pupils", "点瞳孔": "dot pupils", "额外的瞳孔": "extra pupils", "十字形瞳孔": "cross shaped pupils", "符号形瞳孔": "symbol shaped pupils" }, "嘴部表情": { "张嘴": "open mouth", "喘气（张大嘴）": "gasping", "嘴巴微微张开": "Slightly open mouth", "波浪嘴": "wavy mouth", "闭嘴": "close mouth", "点嘴": "dot mouth", "堵嘴": "gag", "啃": "gnaw", "努嘴": "pout", "嘴唇张开": "parted lips", "吃惊": "surprised", "勒住嘴": "bit gag", "栗子嘴": "chestnut mouth", "被封住嘴": "cleave gag", "闭着的嘴": "closed mouth", "蒙住的嘴": "covered mouth", "嘴里有头发": "hair in mouth", "嘟嘴|抿嘴": "homu", "用嘴叼着": "mouth hold", "张开的嘴唇": "parted lips", "撅起的嘴唇": "puckered lips", "把嘴画在侧脸": "sideways mouth", "嘴里含着勺子": "spoon in mouth", "三角嘴": "triangle mouth", "唾液": "saliva", "流口水": "drooling", "嘴角画着口水滴形状的缺口": "mouth drool" }, "正面的表情": { "微笑": "smile", "善良的微笑": "kind smile", "大笑": "laughing", "开心": "happy", "露齿咧嘴笑": "grin", "咧嘴傻笑": "teasing smile", "魅惑的微笑": "seductive smile", "自鸣得意的笑": "smirk", "咯咯傻笑": "giggling", "洋洋得意": "smug", "调皮的脸": "naughty face", "邪恶笑": "evil smile", "疯狂的笑": "crazy smile", "快乐|幸福": "happy", "生日快乐": "happy birthday", "万圣节快乐": "happy halloween", "新年快乐": "happy new year", "开心的眼泪": "happy tears", "情人节快乐": "happy valentine" }, "其他表情": { "面无表情": "embarrass", "困乏的": "sleepy", "喝醉的": "drunk", "无聊的": "bored", "使困惑": "confused", "思考": "thinking", "孤独": "lonely", "决心的，坚定的": "determined", "阴沉脸": "shaded", "阴影": "shadow", "凝视|盯": "staring", "咬紧牙关": "clenched teeth", "露出虎牙|露出尖牙": "fang out", "尖牙": "fangs", "鲨鱼牙|锯齿牙": "sharp teeth", "慌张的": "flustered", "流汗": "sweat", "害怕的": "scared", "轻微脸红": "light  blush", "脸红": "blush", "害羞的": "shy", "害羞的(尴尬的)": "embarrass", "紧张的": "nervous", "捂脸": "facepalm" }, "负面情绪表情": { "阿黑颜": "ahegao", "忍耐的表情": "endured face", "忍耐": "restrained", "黑化的": "dark persona", "疯狂的": "crazy", "筋疲力尽的": "exhausted", "傲娇": "Tsundere", "病娇": "yandere", "多重人格": "multiple persona", "颤抖": "trembling", "翻白眼(高潮眼）": "rolling eyes", "嫉妒": "envy", "绝顶": "female orgasm", "重呼吸": "heavy breathing", "淘气": "naughty", "呻吟": "moaning", "嫌弃的眼神": "scowl", "轻蔑": "disdain", "蔑视": "contempt", "脸上有阴影，配合蔑视": "shaded face", "鄙夷的眼神": "jitome", "皱眉/蹙额": "wince", "眉头紧锁": "furrowed brow", "害怕侧目": "fear kubrick", "扬起眉毛": "raised eyebrows", "邪恶的": "evil", "生闷气": "sulking", "尖叫": "screaming", "喊叫": "shouting", "生气的": "angry", "惹恼": "annoy", "怒目而视": "glaring", "严肃的": "serious", "侧头瞪着你": "kubrick stare", "伤心": "sad", "啜泣": "tear", "大哭": "crying", "泪如雨下": "streaming tears", "睁着眼睛哭": "crying with eyes open", "流泪": "streaming tears", "泪珠": "teardrop", "撕破衣服": "tearing clothes", "要哭的表情": "tearing up", "眼泪": "tears", "擦眼泪": "wiping tears", "心情不好": "badmood", "不开心的": "unamused", "沮丧": "frustrated", "沮丧的眉头": "frustrated brow", "苦恼的": "annoyed", "苦闷": "anguish", "叹气": "sigh", "忧郁的": "gloom", "失望的": "disappointed", "绝望": "despair", "疼痛": "pain" } },
      animalClothData: { "翅膀": { "翅膀": "wings", "蝙蝠翅膀": "bat wings", "天使翅膀": "feathered wings", "恶魔翅膀": "demon wings", "蝴蝶翅膀": "butterfly wings", "冰纹理翅膀": "ice crystal texture wings", "龙翅膀": "dragon wings", "妖精翅膀": "fairy wings", "假翅膀": "fake wings", "头上翅膀": "head wings", "低位翅膀": "low wings", "迷你翅膀": "mini wings", "分离的翅膀": "detached wings" }, "尾巴": { "尾巴": "tail", "兔尾    ": "bunny tail", "猫尾    ": "cat tail", "狗尾    ": "dog tail", "鼠尾    ": "mouse tail", "狐狸尾  ": "fox tail", "老虎尾  ": "tiger tail", "松鼠尾  ": "squirrel tail", "狼尾    ": "wolf tail", "龙尾    ": "dragon tail", "马尾巴  ": "horse tail", "浣熊尾  ": "raccoon tail", "牛尾    ": "cow tail", "恶魔尾巴": "demon tail" }, "耳朵": { "兽耳  ": "animal ears", "蝙蝠耳": "bat ears", "猫耳  ": "cat ears", "狗耳  ": "dog ears", "狐耳  ": "fox ears", "兔耳  ": "bunny ears", "浣熊耳": "raccoon ears", "老鼠耳": "mouse ears", "松鼠耳": "squirrel ears", "熊耳  ": "bear ears", "虎耳  ": "tiger ears", "狼耳  ": "wolf ears", "马耳  ": "horse ears", "牛耳  ": "cow ears", "羊耳  ": "sheep ears", "山羊耳": "goat ears", "狮耳  ": "lion ears", "熊猫耳": "panda ears", "鹿耳  ": "deer ears", "猴耳  ": "monkey ears", "猪耳  ": "pig ears", "鼬耳  ": "ferret ears", "羊驼耳": "alpaca ears", "尖耳  ": "pointy ears", "长尖耳": "long pointy ears", "垂耳  ": "floppy ears" }, "角": { "山羊角": "goat  horns", "羊角": "sheep  horns", "机械角": "mechanical  horns", "奶牛角": "cow  horns", "恶魔之角": "demon  horns", "断角": "broken  horn", "龙之角": "dragon  horns" } },
      backgroundData: { "自然环境": { "大海": "ocean", "海滩": "beach", "湖泊": "lake", "瀑布": "waterfall", "夜空": "night_sky", "星空": "starry_sky", "星云": "nebula", "超级银河": "hyper_galaxy", "星星轨迹": "star_trail", "落日": "sunset", "月亮": "moon", "月牙": "crescent", "新月": "crescent_moon", "满月": "full_moon", "月光": "moonlight", "猩红月亮": "scarlet_moon", "宇宙": "universe", "太空": "space", "星球": "planet", "地平线": "horizon", "多山的地平线": "mountainous horizon", "牧场": "pasture", "高原": "plateau", "花园": "garden", "田园": "fields", "浮岛": "floating_island", "森林": "forest", "草原": "grasslands", "花田": "flower field", "花海": "flower ocean", "火山": "volcano", "悬崖": "cliff", "雪山": "snowy mountain", "春": "spring", "秋": "autumn", "冬": "winter", "夏": "summer" }, "室外场景": { "公园": "park", "吊架；秋千": "Trapeze", "面包店": "bakery", "咖啡厅": "cafe", "植物园": "greenhouse", "城堡": "castle", "竞技场": "arena", "礼堂": "Auditorium", "音乐会": "concert", "书店": "bookstore", "日本旅馆": "Japanese_hotel", "监狱酒吧": "jail_bars", "在电影院里": "in_the_movie_theatre", "舞台": "stage", "港口": "harbor", "赌场": "casino", "台球桌": "billiard", "地牢": "dungeon", "坟墓": "grave", "泰姬陵": "Taj_Mahal", "马丘比丘": "Machu_Picchu", "明石海峡大桥": "Akashi_Kaikyo_Bridge", "东京天空树": "Skytree", "富士山": "Fuji_Mountain", "东京塔": "Tokyo_Tower", "伏见稻荷": "fushimi_inari", "大峡谷": "grand_canyon", "长城": "great_wall_of_china", "希腊_圣托里尼": "santorini", "意大利_威尼斯": "venice", "铁路": "railway" }, "城市风景": { "天际线": "city", "城市风景": "cityscape", "街道": "street", "市中心": "downtown", "人群": "crowd", "小巷": "alleyway", "大道": "in_main_street", "路口": "Intersection", "草坪": "lawn", "路径": "path", "路面": "pavement", "路标": "road_sign", "路障": "traffic_cone", "路灯": "lamppost", "灯柱": "lamppost", "电线杆和电线": "power_lines", "长凳": "bench", "自动贩卖机": "vending_machine", "摩天楼": "Skyscraper" }, "建筑": { "建筑": "magnificent_architecture", "摩天楼": "Skyscraper", "东亚建筑": "east_asian_architecture", "鸟居/牌坊": "torii", "大教堂": "cathedral", "中式阁楼": "chinese_style_loft", "传统中式客房": "traditional_chinese_room", "城堡": "castle", "塔楼": "turret", "清真寺": "mosque", "水库": "reservoir", "铁路": "railway", "桥": "bridge", "桥下": "under_bridge", "遗迹": "ruins", "废墟堆": "remains", "建筑废墟": "rubble_ruins" }, "室内环境及装饰": { "室内泳池": "indoor pool,", "混合控制台": "mixing_console", "汽车驾驶室内": "car_seat,_seatbelt", "更衣室": "locker_room", "在泳池边": "poolside", "洗衣店": "on laundry", "在房间内": "in the room,", "研究所": "on laboratory", "灯笼环境": "lantern_floating", "豪华房间内": "mansion", "厕所里": "on toilet", "在床上": "on_bed", "在沙发上": "on_couch", "在桌子上": "on_desk", "在巴士内": "on_the_bus", "电车内": "on_the_train", "咖啡厅": "cafe", "居酒屋": "Izakaya", "宴会": "banquet", "木质地板": "tile_floor", "榻榻米": "tatami", "床": "bed", "沙发": "safa", "被炉": "kotatsu", "壁炉": "fireplace", "窗": "window", "窗帘": "curtains", "门": "door", "推拉门": "sliding_doors", "衣架": "clothes_rack", "空调": "air_conditioner", "床单": "bed_sheet", "床垫": "futon", "坐垫": "cushion", "抱枕": "pillow_hug", "毛绒兔子": "stuffed_bunny", "小熊": "koakuma" }, "简单背景": { "单色背景": "monochrome_background", "简单的背景": "simple_background", "无背景|平铺的背景": "flats", "黑色背景": "black_background", "白色背景": "white_background", "透明背景": "transparent_background", "米色背景": "beige_background", "棕色背景": "brown_background", "棕褐色背景": "tan_background", "灰色背景": "grey_background", "双色调背景": "two-tone_background", "渐变的背景": "gradient_background", "多彩的背景": "multicolored_background", "彩虹背景": "rainbow_background", "抽象背景": "abstract_background", "菱形背景": "argyle_background", "方格背景": "checkered_background", "花朵点缀的背景": "floral_background", "网点图背景": "halftone_background", "桃色背景": "heart_background", "蜂窝风格背景": "honeycomb_background", "格子呢背景": "plaid_background", "圆斑背景": "striped_background" } },
      newClothData: {
        "上装": {
          "连衣裙": "dress",
          "裙子": "skirt",
          "衬衫": "blouse",
          "t 恤": "t-shirt",
          "毛衣": "sweater",
          "夹克": "jacket",
          "背心": "tank top",
          "卫衣": "hoodie",
          "马甲": "vest",
          "风衣": "trench coat",
          "西装": "suit",
          "骑士夹克": "biker jacket",
          "外套": "coat",
          "罩衫": "shawl",
          "披肩": "cape",
          "庞乔斗篷": "poncho",
          "大衣": "overcoat",
          "毛皮大衣": "fur coat",
          "斗篷": "cape",
          "披风": "cloak",
          "皮衣": "leather jacket"
        },
        "下装": {
          "牛仔裤": "jeans",
          "裤子": "pants",
          "连体裤": "jumpsuit",
          "短裤": "shorts",
          "长裤": "trousers",
          "裙子": "skirt",
          "打底裤": "leggings",
          "紧身裤": "leggings",
          "便裤": "slacks",
          "阔腿裤": "wide-leg pants",
          "七分裤": "capri pants",
          "短裙": "mini skirt",
          "中裙": "midi skirt",
          "长裙": "maxi skirt",
          "运动裤": "sweatpants",
          "卡其裤": "khaki pants",
          "牛仔短裤": "denim shorts",
          "裙裤": "skort",
          "束脚裤": "joggers",
          "瑜伽裤": "yoga pants",
          "哈伦裤": "harem pants"
        },
        "泳装": {
          "泳衣": "swimsuit",
          "比基尼": "bikini",
          "连体泳衣": "one-piece swimsuit",
          "泳裙": "swim skirt",
          "沙滩裙": "beach cover-up",
          "遮阳衣": "rash guard",
          "泳裤": "swim shorts",
          "泳帽": "swim cap",
          "浮潜套装": "snorkel set",
          "潜水衣": "wetsuit",
          "潜水裤": "wetsuit pants",
          "冲浪裤": "boardshorts"
        },
        "运动装": {
          "运动文胸": "sports bra",
          "运动上衣": "sports top",
          "运动裤": "sports pants",
          "运动短裤": "athletic shorts",
          "运动紧身衣": "compression suit",
          "运动套装": "athletic set",
          "运动连衣裙": "athletic dress",
          "健身服": "gym wear",
          "跑步裤": "running pants",
          "篮球裤": "basketball shorts",
          "网球裤": "tennis shorts",
          "足球裤": "soccer shorts",
          "高尔夫服": "golf attire",
          "瑜伽服": "yoga wear",
          "太极服": "tai chi suit",
          "拳击装": "boxing attire",
          "羽毛球服": "badminton attire",
          "游泳裤": "swim trunks",
          "健身紧身衣": "fitness tights",
          "长跑服": "marathon suit"
        },
        "内衣": {
          "文胸": "bra",
          "内裤": "panties",
          "丁字裤": "thong",
          "连体内衣": "bodysuit",
          "睡衣": "pajamas",
          "底裤": "long johns",
          "吊袜带": "garter belt",
          "情趣内衣": "lingerie",
          "背心": "camisole",
          "泳衣内衣": "swimwear bra",
          "塑身内衣": "shapewear",
          "胸衣": "bustier",
          "纹身文胸": "tattoo bra",
          "丰胸垫": "push-up pad",
          "吊带内衣": "strapless bra",
          "无肩带文胸": "convertible bra",
          "无线文胸": "wireless bra",
          "无痕内衣": "seamless underwear",
          "泳装内衣": "swimwear bra",
          "小胸文胸": "small cup bra",
          "大胸文胸": "large cup bra"
        },
        "配饰": {
          "项链": "necklace",
          "耳环": "earrings",
          "手链": "bracelet",
          "戒指": "ring",
          "围巾": "scarf",
          "帽子": "hat",
          "手套": "gloves",
          "皮带": "belt",
          "太阳镜": "sunglasses",
          "包包": "handbag",
          "披肩": "shawl",
          "发夹": "hairpin",
          "发带": "headband",
          "项链套装": "necklace set",
          "手表": "watch",
          "腰链": "waist chain",
          "胸针": "brooch",
          "手镯": "bangle",
          "项链链子": "chain necklace",
          "胸花": "corsage",
          "围巾固定器": "scarf clip"
        },
        "鞋类": {
          "高跟鞋": "high heels",
          "运动鞋": "sneakers",
          "平底鞋": "flats",
          "靴子": "boots",
          "凉鞋": "sandals",
          "拖鞋": "slippers",
          "尖头鞋": "pointed-toe shoes",
          "帆布鞋": "canvas shoes",
          "平底便鞋": "loafers",
          "牛仔鞋": "cowboy boots",
          "坡跟鞋": "wedges",
          "男鞋": "oxfords",
          "马丁靴": "ankle boots",
          "水鞋": "water shoes",
          "人字拖": "flip-flops",
          "木底拖鞋": "clogs",
          "运动凉鞋": "sport sandals",
          "豆豆鞋": "penny loafers"
        },
        "睡衣": {
          "睡衣": "pajamas",
          "睡袍": "robe",
          "睡裙": "nightgown",
          "睡袍套装": "robe set",
          "家居服": "loungewear",
          "居家裤": "lounging pants",
          "睡裤": "sleep shorts",
          "睡裙套装": "nightgown set",
          "短睡裤套装": "sleep shorts set",
          "居家内衣": "lounging lingerie",
          "睡裤套装": "sleep shorts set",
          "夜袍": "night robe",
          "夜裙": "evening dress"
        },
        "帽子": {
          "帽子": "hat",
          "棒球帽": "baseball cap",
          "遮阳帽": "sun hat",
          "渔夫帽": "bucket hat",
          "毛线帽": "beanie",
          "礼帽": "top hat",
          "鸭舌帽": "duckbill cap",
          "贝雷帽": "beret",
          "军帽": "military cap",
          "斗篷": "cape",
          "围巾": "scarf",
          "披肩": "shawl",
          "发带": "headband",
          "发夹": "hairpin",
          "草帽": "straw hat",
          "兔毛帽": "fur hat",
          "军官帽": "officer's cap",
          "帽套": "hat cover",
          "卡其帽": "newsboy cap",
          "洪堡帽": "homburg hat",
          "英式帽": "bowler hat",
          "圆顶礼帽": "derby hat"
        },
        "连袜裤": {
          "连袜裤": "tights",
          "裤袜": "pantyhose",
          "厚连裤袜": "opaque tights",
          "薄连裤袜": "sheer tights",
          "蕾丝连袜裤": "lace tights",
          "鱼网袜": "fishnet tights",
          "中筒袜": "knee-high socks",
          "长筒袜": "thigh-high socks",
          "踝筒袜": "ankle socks",
          "脚趾袜": "toe socks",
          "冬季连袜裤": "winter tights",
          "体育袜": "sport socks",
          "格子连袜裤": "plaid tights",
          "格子袜": "argyle socks",
          "袜套": "sock covers",
          "睡袜": "sleep socks",
          "美腿袜": "leggings"
        },
        "围巾": {
          "围巾": "scarf",
          "丝巾": "silk scarf",
          "披肩": "shawl",
          "围巾固定器": "scarf clip",
          "领巾": "neckerchief",
          "围巾环": "scarf ring",
          "围脖": "snood",
          "飘带": "streamer",
          "绒线围巾": "knit scarf",
          "披风": "cape",
          "莱卡围巾": "lycra scarf",
          "领巾固定器": "neckerchief clip",
          "鸟巾": "boa",
          "围巾链子": "scarf chain"
        },
        "丝袜": {
          "丝袜连裤袜": "pantyhose",
          "网纹丝袜": "fishnet stockings",
          "压力丝袜": "compression stockings",
          "无缝丝袜": "seamless stockings",
          "花边丝袜": "lace stockings",
          "大网格丝袜": "large fishnet stockings",
          "薄款丝袜": "sheer stockings",
          "加强型丝袜": "reinforced stockings",
          "超薄丝袜": "ultra-sheer stockings",
          "丝袜袜子": "stocking socks",
          "丝袜裤袜": "tights",
          "蕾丝丝袜": "lace tights",
          "鱼网丝袜": "fishnet tights",
          "肉色丝袜": "nude stockings",
          "彩色丝袜": "colored stockings",
          "黑色丝袜": "black stockings",
          "白色丝袜": "white stockings",
          "红色丝袜": "red stockings",
          "粉色丝袜": "pink stockings",
          "紫色丝袜": "purple stockings",
          "蓝色丝袜": "blue stockings",
          "绿色丝袜": "green stockings",
          "黄色丝袜": "yellow stockings",
          "灰色丝袜": "gray stockings",
          "褐色丝袜": "brown stockings",
          "条纹丝袜": "striped stockings",
          "格子丝袜": "checkered stockings",
          "花卉丝袜": "floral stockings",
          "圆点丝袜": "polka dot stockings",
          "复古丝袜": "vintage stockings",
          "半透明丝袜": "semi-opaque stockings",
          "珠光丝袜": "shimmer stockings",
          "亮片丝袜": "glitter stockings",
          "珍珠丝袜": "pearl stockings",
          "水钻丝袜": "rhinestone stockings",
          "厚款丝袜": "opaque stockings",
          "坎肩丝袜": "cowl neck stockings",
          "连体丝袜": "bodystocking",
          "性感丝袜": "sexy stockings",
          "丝袜内衣套装": "lingerie and stockings set",
          "高腰丝袜": "high-waisted stockings",
          "丝袜配饰": "stocking accessories",
          "网纹袜套": "fishnet leg warmers",
          "丝袜腿套": "stocking leg warmers",
          "丝袜吊带": "stocking garters"
        },
        "深V": {
          "深V领连衣裙": "deep v-neck dress",
          "深V领毛衣": "deep v-neck sweater",
          "深V领上衣": "deep v-neck top",
          "深V领连体裤": "deep v-neck jumpsuit",
          "深V领紧身衣": "deep v-neck bodysuit",
          "深V领背心": "deep v-neck camisole",
          "深V领吊带连衣裙": "deep v-neck halter dress",
          "深V领连身短裤": "deep v-neck romper",
          "深V领礼服连衣裙": "deep v-neck evening gown",
          "深V领薄款针织衫": "deep v-neck lightweight knit sweater",
          "深V领运动上衣": "deep v-neck athletic top",
          "深V领丝绒连衣裙": "deep v-neck velvet dress",
          "深V领夏季连衣裙": "deep v-neck summer dress",
          "深V领短袖衬衫": "deep v-neck short sleeve shirt",
          "深V领包裹裙": "deep v-neck bodycon skirt",
          "深V领流苏裙": "deep v-neck fringe skirt",
          "深V领蕾丝连衣裙": "deep v-neck lace dress",
          "深V领连衣裙套装": "deep v-neck dress set",
          "深V领吊带上衣": "deep v-neck halter top",
          "深V领连衣裙配饰": "deep v-neck dress accessories",
          "深V领连衣裙外套": "deep v-neck dress coat",
          "深V领连衣裙帽衫": "deep v-neck dress hooded sweater",
          "深V领拼接连衣裙": "deep v-neck patchwork dress",
          "深V领卫衣连衣裙": "deep v-neck hoodie dress",
          "深V领夜店装": "deep v-neck clubwear",
          "深V领网纱连衣裙": "deep v-neck mesh dress",
          "深V领迷彩连衣裙": "deep v-neck camouflage dress",
          "深V领蕾丝上衣": "deep v-neck lace top",
          "深V领透明连衣裙": "deep v-neck sheer dress",
          "深V领复古连衣裙": "deep v-neck vintage dress",
          "深V领长款连衣裙": "deep v-neck long dress",
          "深V领迷你连衣裙": "deep v-neck mini dress",
          "深V领宴会装": "deep v-neck cocktail dress",
          "深V领无袖连衣裙": "deep v-neck sleeveless dress",
          "深V领绒线连衣裙": "deep v-neck knit dress",
          "深V领运动连衣裙": "deep v-neck athletic dress",
          "深V领修身连衣裙": "deep v-neck fitted dress",
          "深V领皮质连衣裙": "deep v-neck leather dress",
          "深V领连衣裙裙子": "deep v-neck dress skirt",
          "深V领裹胸连衣裙": "deep v-neck wrap dress",
          "深V领露肩连衣裙": "deep v-neck off-shoulder dress",
          "深V领背带连衣裙": "deep v-neck suspender dress",
          "深V领夏日连衣裙": "deep v-neck summer dress",
          "深V领金属纹裙": "deep v-neck metallic skirt",
          "深V领高腰连衣裙": "deep v-neck high-waisted dress",
          "深V领包裹上衣": "deep v-neck bodycon top"
        },
        "包臀": {
          "包臀连衣裙": "bodycon dress", "包臀裙": "bodycon skirt", "包臀上衣": "bodycon top", "包臀连体裤": "bodycon jumpsuit", "包臀紧身裙": "bodycon skirt", "包臀礼服": "bodycon gown",
          "包臀针织裙": "knit bodycon dress",
          "包臀运动短裤": "sports booty shorts",
          "包臀派对服": "party bodycon dress",
          "包臀露背连衣裙": "backless bodycon dress",
          "包臀剧院装": "theater bodycon dress",
          "包臀半身裙": "mini bodycon skirt",
          "包臀连衣裙套装": "bodycon dress set",
          "包臀纹理裙": "textured bodycon skirt",
          "包臀夏季连衣裙": "summer bodycon dress",
          "包臀针织上衣": "knit bodycon top",
          "包臀夏日裙子": "summer bodycon dress",
          "包臀毛织裙": "knit bodycon skirt",
          "包臀花纹裙": "patterned bodycon dress",
          "包臀网纱连衣裙": "mesh bodycon dress",
          "包臀迷你连衣裙": "mini bodycon dress",
          "包臀薄款上衣": "lightweight bodycon top",
          "包臀复古连衣裙": "vintage bodycon dress",
          "包臀夜店装": "clubwear bodycon dress",
          "包臀蕾丝裙": "lace bodycon skirt",
          "包臀宴会装": "cocktail bodycon dress",
          "包臀皮质短裙": "leather bodycon skirt",
          "包臀纯色连衣裙": "solid color bodycon dress",
          "包臀迷你半身裙": "mini bodycon skirt",
          "包臀吊带连衣裙": "strapless bodycon dress",
          "包臀连衣裙配饰": "bodycon dress accessories",
          "包臀高腰裙": "high-waisted bodycon skirt",
          "包臀透明连衣裙": "sheer bodycon dress",
          "包臀金属纹裙": "metallic bodycon skirt",
          "包臀卫衣连衣裙": "hooded bodycon dress",
          "包臀深V领连衣裙": "deep v-neck bodycon dress",
          "包臀丝绒连衣裙": "velvet bodycon dress",
          "包臀迷你连衣裙套装": "mini bodycon dress set",
          "包臀牛仔短裙": "denim bodycon skirt",
          "包臀低背连衣裙": "low-back bodycon dress",
          "包臀修身连衣裙": "fitted bodycon dress",
          "包臀雪纺连衣裙": "chiffon bodycon dress",
          "包臀长款连衣裙": "long bodycon dress",
          "包臀背心上衣": "sleeveless bodycon top",
          "包臀无袖连衣裙": "sleeveless bodycon dress",
          "包臀蕾丝连衣裙": "lace bodycon dress",
          "包臀紧身衬衫": "bodycon shirt",
          "包臀绒线连衣裙": "knit bodycon dress",
          "包臀薄纱连衣裙": "chiffon bodycon dress",
          "包臀高领连衣裙": "high-neck bodycon dress",
          "包臀纯色半身裙": "solid color bodycon skirt"
        },
        "蕾丝": {
          "蕾丝连衣裙": "lace dress",
          "蕾丝上衣": "lace top",
          "蕾丝裙子": "lace skirt",
          "蕾丝连体裤": "lace jumpsuit",
          "蕾丝裤子": "lace pants",
          "蕾丝外套": "lace jacket",
          "蕾丝背心": "lace camisole",
          "蕾丝内衣": "lace lingerie",
          "蕾丝袜子": "lace stockings",
          "蕾丝长袜": "lace thigh-high socks",
          "蕾丝长袖上衣": "lace long-sleeve top",
          "蕾丝吊带上衣": "lace camisole top",
          "蕾丝连衣裙配饰": "lace dress accessories",
          "蕾丝短袖上衣": "lace short-sleeve top",
          "蕾丝披肩": "lace shawl",
          "蕾丝长袖裙": "lace long-sleeve dress",
          "蕾丝袖套": "lace sleeves",
          "蕾丝贴边裙": "lace-edged skirt",
          "蕾丝花边上衣": "lace trim top",
          "蕾丝贴边连衣裙": "lace-edged dress",
          "蕾丝夏季裙": "lace summer dress",
          "蕾丝春季外套": "lace spring jacket",
          "蕾丝晚礼服": "lace evening gown",
          "蕾丝迷你裙": "lace mini skirt",
          "蕾丝修身裙": "lace bodycon skirt",
          "蕾丝冬季外套": "lace winter jacket",
          "蕾丝紧身上衣": "lace fitted top",
          "蕾丝包臀连衣裙": "lace bodycon dress",
          "蕾丝裙裤套装": "lace skirt and pants set",
          "蕾丝夜店装": "lace clubwear",
          "蕾丝流苏裙": "lace fringe skirt",
          "蕾丝露背连衣裙": "lace backless dress",
          "蕾丝深V领上衣": "lace deep v-neck top",
          "蕾丝高腰裙": "lace high-waisted skirt",
          "蕾丝透明连衣裙": "lace sheer dress",
          "蕾丝圆领上衣": "lace round neck top",
          "蕾丝半袖连衣裙": "lace short-sleeve dress",
          "蕾丝花边连衣裙": "lace trim dress",
          "蕾丝低背连衣裙": "lace low-back dress",
          "蕾丝包臀上衣": "lace bodycon top",
          "蕾丝薄纱连衣裙": "lace chiffon dress",
          "蕾丝针织裙": "lace knit skirt",
          "蕾丝连衣裙外套": "lace dress coat",
          "蕾丝背心上衣": "lace camisole top"
        },
        "裙子": {
          "连衣裙": "dress",
          "迷你裙": "miniskirt",
          "中裙": "midi skirt",
          "长裙": "maxi skirt",
          "半身裙": "skirt",
          "包臀裙": "bodycon skirt",
          "百褶裙": "pleated skirt",
          "牛仔裙": "denim skirt",
          "礼服裙": "gown",
          "夏季裙": "summer dress",
          "冬季裙": "winter dress",
          "花卉裙": "floral dress",
          "印花裙": "printed dress",
          "纱裙": "chiffon dress",
          "针织裙": "knit skirt",
          "派对裙": "party dress",
          "背心裙": "camisole dress",
          "吊带裙": "halter dress",
          "圆领裙": "round neck dress",
          "蕾丝裙": "lace dress",
          "修身裙": "fitted skirt",
          "流苏裙": "fringe skirt",
          "包裹裙": "wrap dress",
          "无袖裙": "sleeveless dress",
          "宴会裙": "cocktail dress",
          "露肩裙": "off-shoulder dress",
          "连体裙": "jumpsuit",
          "迷你连衣裙": "minidress",
          "深V领连衣裙": "deep v-neck dress",
          "丝绒裙": "velvet dress",
          "复古裙": "vintage dress",
          "亮片裙": "sequin skirt",
          "亮丝裙": "shimmer dress",
          "格子裙": "plaid skirt",
          "牛仔迷你裙": "denim miniskirt",
          "棉质连衣裙": "cotton dress",
          "运动裙": "athletic skirt",
          "低背连衣裙": "low-back dress",
          "高领连衣裙": "turtleneck dress",
          "珍珠裙": "pearl skirt",
          "水钻裙": "rhinestone skirt",
          "条纹裙": "striped skirt",
          "亮片迷你裙": "sequin miniskirt",
          "印花迷你裙": "printed miniskirt",
          "薄纱连衣裙": "chiffon minidress",
          "无袖连衣裙": "sleeveless minidress",
          "背带连衣裙": "suspender dress",
          "麂皮迷你裙": "suede miniskirt",
          "纯色连衣裙": "solid color dress"
        }

      },
      exampleData: { "艺术风格": { "8bit游戏": "8  Bit  Game", "80动画": "1980s  anime", "迪士尼电影": "disney  movie", "哥特摇滚": "goth", "80电影": "80s  movie", "泡泡龙": "bubble  bobble", "皮克斯动画": "style  of  Pixar", "宝丽来艺术": "Polaroid  art", "万花筒摄影": "Kaleidoscope  Photography", "欧泊渲染": "opal  render", "色谱图": "chemigram", "吉卜力风格": "Studio  Ghibli", "梦幻": "dreamlike", "签绘风格": "(faux  traditional  media)", "原神冲击": "genshin  impact", "碧蓝航线": "azur  lane", "舰队收藏": "kantai  collection", "边缘行者": "rebecca  (cyberpunk)", "电锯人": "chainsaw  man", "魔法旋涡": "Magic  Vortex", "柴油朋克": "((dieselpunk))", "四格": "4koma", "杂志扫描": "magazine  scan", "专辑封面": "album  cover", "线条变粗": "(lineart)", "蒸汽波": "synthwave", "洛可可": "(illustration),(paper figure),(lococo),((impasto)),(shiny skin)" }, "艺术类型": { "单色图片": "monochrome", "拼贴艺术": "Collage", "彩色玻璃": "Dalle  de  verre", "像素画": "pixel  art", "瓷画": "Encaustic  painting", "水墨画": "Ink  wash  painting", "铜版雕刻": "Mezzotint", "剪影": "silhouette", "插画": "illustration", "水彩画": "(((ink))),  ((watercolor))", "浮世绘": "illustration,(((ukiyoe))),((sketch)),((japanese_art))", "中国风": "((wash  painting)),((ink  s...", "油画": "((dyeing)),((oil painting)),((impasto))", "黑白草图": "(posing sketch), (monochrome)", "手画草稿": "sketch", "铅笔速写": "(monochrome), (gray scale), (pencil sketch lines", "彩铅画": "(watercolor  pencil)" }, "艺术派系": { "新艺术主义": "((art  nouveau))", "古典主义": "((classicism))", "未来主义": "((futurism))", "达达主义": "((Dadaism))", "抽象艺术": "((abstract  art))", "ASCII艺术": "((ASCII  art))", "穆夏风格": "((alphonse  mucha))", "莫奈风格": "((Monet  style))" }, "魔法1.0": { "水魔法": "((water drops)),wet clothes,((beautiful detailed water)),((floating)),dynamic angle", "冰魔法": "beautiful detailed glow, (detailed ice), beautiful detailed water", "冰系改": "beautiful detailed glow,detailed ice,beautiful detailed water,(floating palaces:1.2),(ice crystal texture wings),（Iridescence and rainbow hair:2.5）", "星冰乐": "beautiful detailed glow,detailed ice,beautiful detailed water, (floating palaces:1.3),(((ice crystal texture wings)))", "森林冰": "((detailed beautiful snow forest with trees)), ((snowflakes)), floating", "结晶法": "(((crystals texture Hair))),((beautiful detailed glass hair)),((glass shaped texture hand)),((crystallize texture body)),gem body,hands as clear as jewels,crystallization of clothes,((crystals texture skin)),sparkle, lens flare, light leaks,broken glass,detailed glass shaped clothes,beautiful detailed gemstone sky, gemstone sea, crystals texture flowers,((detailed crystallized clothing))", "核爆法": "beautiful detailed glow,((flames of war)),(((nuclear explosion behide)))", "风魔法": "((breeze)), flying splashes,  flying petals, wind", "流沙法": "((surrounded by heavy floating sand flow and floating sharp stones)),((((ink)))),((illustration)),((watercolor))", "雷电法": "(detailed light), ((lightning in hand)),lightning surrounds,(((lightning chain)))", "圣光法": "(sunlight),(angel),dynamic angle, floating, wing, halo, floating white silk,(Holy Light),silver stars", "苇名法": "beautiful detailed pampas grass field, open hakama, surrounded by floating sakura, yellow full moon, beautiful detailed dark midnight sky, messy white long hair", "自然法": "beautiful and delicate water, the finest grass, very delicate light, nature, painting, water spray, breeze, flowers and grass meadow, near the water edge, (sunset, starry sky in a circle), randomly distributed clouds, river, splashing water, falling petals", "森林法": "(detailed light) , feather, leaves, nature, (sunlight), river, (forest),(bloom)", "虹彩法": "floating and rainbow long hair,Iridescence and rainbow, beautiful detailed starry sky", "暗锁法": "chain ring, chain storm, dark chain,((wholeblack bloomer)), darkside, night, deep dark, darkness, ((dark clouds)),((ruins)),shadow, death garden", "火烧云": "beautiful detailed glow, floating ashes, beautiful and detailed explosion, red moon, fire, fire cloud, wings on fire, a cloudy sky, smoke of gunpowder, burning, black dress, dove of peace, (floating cloud:1.2)", "城堡法": "beautiful detailed glow, detailed ice, beautiful detailed water, (magic circle:1.2), (floating palaces:1.3)", "黄昏法": "(water bloom), (delicate glow),  (breeze), long   Flowers meadow,(((sunset)), (less stars form a circle), randomly distributed clouds, (rivers), (willows with branches falling into the water)", "泡泡法": "((colorful bubble)),(floating),detailed light", "蔷薇法": "((rose)), (vine), cage, bandage, red rope, (detail light), falling rose petals", "星空法": "(starry tornado:1.4), starry Nebula, beautiful detailed sky", "月亮法": "((moon)), starry sky, (lighting particle), fog, snow,(bloom)", "雪月法": "beautiful detailed glow, detailed ice, beautiful detailed water, (cold full moon), snowflake, (floating cloud:1.2)", "森火法": "((burning forest)), spark, light leaks, burning sky, flame, flames burning around, (flying sparks)", "废土法": "((destroyed)),explosion, buildings in disarray, The residual eaves DuanBi, cumulus, mouldy, floating, wind, Dead end machine,(broken robot),(Mechanical robot girl), in the rubble of a devastated city", "战甲法": "mecha clothes, robot girl, sliver bodysuit,((sliver)) and (broken) body", "黄金法": "Extremely gorgeous metal style, Metal crown with ornate stripes, Various metals background, Sputtered molten iron, floating hair, Hair like melted metal, Clothes made of silver, Clothes with gold lace, flowing gold and silver, everything flowing and melt, flowing iron, flowing silver, lace flowing and melt", "机娘法": "mecha clothes, robot girl", "死灵法": "ink,(((bone))), (((ribs))), rose, black hair, blue eyes, greyscale, no shadow, simple background, bright skin", "水晶法": "(((gorgeous crystal armor))),(((crystal wings))),((altocumulus)),(clear_sky),(snow mountain),((flowery flowers)),(flowery bubbles),cloud map plane, crystal, crystal poppies,Brilliant light, thick_coating, glass tint,(watercolor)", "水森法": "((an extremely delicate and beautiful)),floating, (detailed wet clothes), (detailed light),feather, nature, (sunlight), river, floating palace, beautiful and delicate water,(bloom),(shine)", "冰火法": "(blue spark),(red and blue hair),blue eyes, burning sky,flame,Rainbow in the sky,((Flames burning ice)),(((fire  butterflies))),(((ice crystal texture wings))),(Flying sparks),(detailed ice),((a lot of luminous ice crystals)),((burning feathers)),(feathers made of ice),(frozen feathers),(((ice and fire together)))", "龙骑士": "(anger), dragon horns, (silver armor), metal, complex pattern, cape, indifference", "坠落法": "((full body)),(helpless),tear, crying,(((((falling from the sky))))),((Weathering With You)),((falling)),((face towards the sky))，(hair flows upwards),((disheveled hair)),(1 girl), floating, beautiful detailed sky", "水下法": "((underwater)),(beautiful detailed water),((coral)),dynamic angle, floating,(detailed light),floating hair,(splash),((fishes)),leaves dress, feather, nature,(sunlight),(underwater forest),(bloom),(detailed glow),drenched, seaweed, fish,(((Tyndall effect)))", "秘境法": "(extremely detailed CG unity 8k wallpaper),(((masterpiece))), (((best quality))), ((ultra-detailed)), (best illustration),(best shadow), ((an extremely delicate and beautiful)),dynamic angle,floating, fairyland,dynamic angle,sea of flowers,beautiful detailed garden,wind,classic,spring, (detailed light),feather, nature, (sunlight), river, forest,(((floating palace))),((the best building)),beautiful and delicate water,(painting),(sketch),(bloom),(shine)", "摄影法": "(((masterpiece))), ((the best quality, super fine illustrations, beautiful and delicate water)), ((very delicate light)), ((nature, painting)), ((fine lighting, more transparent stars, high-quality snowflakes, high-quality mountains, very fine 8KCG wallpapers)), (plateau), (((snow mountain))), sunrise, randomly distributed clouds, (snow field), cliff, ((rotating star sky)), ((lake in mountain stream)), luminous particles", "摩登法": "1980s (style),simple background, retro artstyle", "血魔法": "(white hair), red long hair, red eyes, (full body), (((with sword))), angry face, (beautiful detailed eyes), Blood drop,Blood fog, floating hair, disheveled hair,  Splashing blood,(Bloodstain)", "唤龙术": "((dragon)), ((dragon background))", "战姬法": "hair fluttering in the wind,((mechanical arm armor)),((mechanical body armor)),riding motor, bodysuit,((ruins of city in war, fire, burning cars, burning buildings)),air force fleet in the sky", "龙机法": "mecha clothes, robot girl, sliver bodysuit, dragon wings, ((a dragon  stands behind the girl)), beautiful detailed sliver dragon armor", "星蝶术": "((Beautiful butterflies in detail)),((Beautiful stars in detail)),(((halter dress ))),particle,(Starry sky in beautiful detail),(Hazy fog),(Ruins of beautiful details),(((Standing on the surface of the sea)))", "学院法": "blonde wavy hair, (shiny long hair), ((Gothic Lolita)), blue white skirt, ((short skirt)), black Headdress, bowknot, (((hair ornament))), (hair flower), Lace, cross-laced footwear, ribbon-trimmed sleeves, building architecture, ((gothic architecture)), starry sky, outdoors, church, (castle)", "星霞海": "walking, waves, wind,(((glistening light of waves))),(detailed sunset glow),(floating flow),((coral)),(Luminous),coast,(floating colorful bubbles),beautiful detailed sky,(fluorescence),detailed shadow,(conch),beautiful detailed water, starfish, meteor, rainbow,(seabirds),(glinting stars), (glowworm),(splash),detailed cloud, shell,(fireworks)", "冬雪法": "beautiful detailed sky, night, stars, (red plum blossom),((winter)),(((snowflakes))), ((red and white flowers))，(starry sky),(sitting),((colorful)),scenery, lantern,(starfall)" }, "魔法1.5": { "万物熔炉": "((((melt)))),(((((black and white melt))))),(((wear Black and white Taoist robes))),((((gold and silver lace)))),(((gold and silver lace lace))),(((flowing ((black)) and white background))),extremely detailed gorgeous tiara", "暗鸦法": "Floating black ashes, Beautiful and detailed black, red moon, ((The black clouds)), (black Wings) , a black cloudy sky, burning, black dress, ((Black fog)), Red eyes, (black smoke), ((Black feathers floating in the air)),bat, (floating black cloud:1.5),", "花火基础": "dynamic angle, finely detail, (bloom), (shine), glinting stars, ((((best detailed fireworks)))), ((((depth of field)))), (((hanabi))),(((backlight))),", "星之彩": "stars in the eyes, messy floating hair, colored inner hair, Starry sky adorns hair, (lots of big colorful Bubble), (pearl), (Galaxy), depth of field", "沉入星海": "stars in the eyes, messy floating hair, colored inner hair, Starry sky adorns hair, depth of field", "百溺法": "dynamic angle, detailed wet clothes, blank stare, overexplosure, floating, black long straight, red eyes, aqua eyes, gradient eyes, ((blood)), white dress, frills, ((expressionless)), extremely beautiful detailed water, ((lying on the lake)), (bloodred water:1.5), (red background:1.3)", "辉煌阳光法": "((messy hair)),(grassland),(yellow eyes),incredibly absurdres,(gold hair),floating hair,Large number of environments,the medieval ,grace,prospect,water eyes,wind,breeze,god ray,lawn,Mountains and lakes in the distance,The sun shines through the trees,A vast expanse of grassland", "星鬓法": "Starry sky adorns hair, sparkling anime eyes,beautiful detailed stars,blighting stars,emerging dark purple across with white hair,multicolored hair,beautiful detailed sky, beautiful detailed water, cinematic lighting, dramatic angle,", "森罗法": "There are many scattered luminous petals,Hidden in the light yellow flowers,Depth of field,Many flying drops of water,Many scattered leaves,branch ,angle ,contour deepening,cinematic angle", "星天使": "sunlight, extremely light, extremely clothes, Holy Light, dynamic angle, Light particle, very long hair, white hair, yellow eyes, glowing eyes, expressionless, ((light smile)), ((((white Tulle skirt)))), white silk, looking at viewer, angel wings, large wings, multiple wings, angel halo, (((starry sky))), dusk sky, Floating light spot, Lots of feathers", "黄金律": "blonde hair, yellow eyes, white skin,  mature female, sunrise, golden sky, magnificent architecture, beautiful detailed sky, overexposure, delicate gold metal decorations", "机械姬": "mechanical prosthesis,mecha coverage,emerging dark purple across with white hair,fluorescent purple,cool movement,rose red eyes,beatiful detailed cyberpunk city, hd semirealistic anime cg concept art digital painting", "人鱼法": "(incredibly absurdres), (highly detailed beautiful fishtail:1.6), (((human hands))), (the lower body is a fish:1.9)AND(no human thigh:1.2), seaweed, (full body), (white seashell), (curved and slender fish tail), (the lower body is bare:1.1), {beautiful tailfin}, ((underwater)), (illustration), detailed water,  ((fishes)), (floating), watercolor (medium), (small whirlpool), ((The sensation of water flowing)), (detailed scales on a mermaid)", "末日": "Blood Mist, background Urban rooftop,despair,Blood Cherry Blossom,torn clothes,crying with eyes open,solo,Blood Rain,bandages,Gunpowder smoke,beautiful deatailed shadow, Splashing blood,dust,tyndall effect", "碎梦": "Space opera,Space port,robot arm,elbow gloves,night,glisten,stare,cyberpunk,((((citylight)))),science fiction,bodysuit,Mechanical armor headdress,(bare shoulders)", "幻碎梦": "dynamic angle,rainbow hair,detailed cute anime face,((loli)),flower,cry,water,corrugated,flowers tire,broken glass,(broken screen),atlantis,transparent glass", "血改法": "(white hair),(((red streaked hair))), red eyes, (((full body))),(red hair), (((((Hold a red sword))))), (angry face),((Blood drop)),((Blood fog)),light shafts, soft focus, ((Splashing blood))),Long hair,((Bloodstain)),Fighting stance,{{{{{watercolor (medium)}}}},(((masterpiece))),((white clock)),((ultradetailed)),((Covered in blood)),flowing hair,Exquisite Flame,dynamic angle, floating, (shine), extremely delicate and beautiful girls, bright skin, lying red petals,Holy lighting", "留影术": "dramatic shadows,black and white,monochrome,{{comic}},cross necklace,Cassock", "飘花法": "((ink)),(water color),bloom effect,detailed beautiful grassland with petal,flower,butterfly,necklace,smile,petal,(((surrounded by heavy floating petal flow)))" } },
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

    getRandom_TextToImg(){
      let random = Math.floor(Math.random() * this.randomize_TextToImg.length);
      this.pictureIfo.prompt = this.randomize_TextToImg[random]
    },


    //给提示词加1.5的权重
    processPrompt(prompt) {
      const prompts = prompt.split(',').map(s => s.trim());
      const processedPrompts = prompts.map(word => {
        // 检查该词是否不以 '(' 开头和不以 ')' 结尾
        if (!word.startsWith('(') || !word.endsWith(')')) {
          return `(${word}:1.5)`;
        }
        return word;
      });
      return processedPrompts.join(', ');
    },


    Lg_selectStyle(id) {
      this.Lg_selectedPortrait = id;
      if (id == 1) {
        this.pictureIfo_Lg.prompt="masterpiece, best quality:1.2), ultra detailed, cinematic lighting, HDR, ilustration, corner store, rain, vending machine, no humans, night, scenery, outdoors, shop, convenience store, road, trash can, street, power lines, building, can, dark, coca-cola, sky, sign, motor vehicle, ground vehicle, utility pole, alley, lamppost, car,, (contrast between dark and bright：1.3),, masterpiece, best quality"
        this.pictureIfo_Lg.negative_prompt="nsfw, (street lamp:1.5),  ((bright background:1.5)),  EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad_prompt_version2, lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry,artist name"
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].weight=0.5 
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_start=0.2
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_end=0.5
      }
      else if (id == 2) {
        this.pictureIfo_Lg.prompt="masterpiece, best quality:1.2), ultra detailed, cinematic lighting, HDR, ilustration, scenery, no humans, city, cityscape (城市景观), building, city lights, night, skyscraper, 反射, outdoors, sky, science fiction, water, electricity, watermark, bridge, architecture (建筑), night sky, artist name, from above, cloud, light (照明艺术), (contrast between dark and bright：1.3),, masterpiece, best quality"
        this.pictureIfo_Lg.negative_prompt="nsfw, (street lamp:1.5),  ((bright background:1.5)),EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad_prompt_version2, lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry,artist name"
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].weight=0.5 
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_start=0.2
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_end=0.5

      }
      else if (id == 3) {
        this.pictureIfo_Lg.prompt="(at night, on street:1.5),(dark:1.5) ,1girl, (dim:2), deep shadow, best quality, masterpiece, ultra highres, (photorealistic:1.5), ((masterpiece:1.5)), ((best quality:1.5)), ((detailed)), ((realistic:1.2)), ((Hi-Res lossless)), (( RAW photo, 8K, high resolution, depth of field, huge filesize, )), 1girl, solo, black hair, thighhighs, skirt, plaid, long hair, white thighhighs, realistic, clothes around waist, night, looking at viewer, outdoors, plaid skirt, shirt, smile, blurry, standing, bangs, short sleeves, arms behind back, (contrast between dark and bright：1.3),, masterpiece, best quality"
        this.pictureIfo_Lg.negative_prompt="nsfw, (street lamp:1.5), ((bright background:1.5)), EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad_prompt_version2, lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry,artist name"
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].weight=0.6 
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_start=0.2
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_end=0.5
      }
      else if (id == 4) {
        this.pictureIfo_Lg.prompt="(masterpiece, best quality:1.2), ultra detailed, (1 girl:1.6), dress, realistic, eye and face details, standing, (photorealistic:1.4), 4k, (light and shadow:1.3), beautiful shadow, (indoor:1.1), (sunlight:1.3), (contrast between light and dark:1.3),  (stand sideways:1.3), (ray tracing), raw, perfect ligthing,professional photography,, masterpiece, best quality"
        this.pictureIfo_Lg.negative_prompt="nsfw, (2 girls:1.5),EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad_prompt_version2, lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits,cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry,artist name, lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer， digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry,artist name"
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].weight=0.3 
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_start=0.2
        this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].guidance_end=0.65
      }
    },

    //漫改头像：根据用户选择风格更改checkpoint和lora
    selectPortrait(id) {
      this.selectedPortrait = id;
      if (id == 1) {
        this.generateUserFaceEntity.override_settings.sd_model_checkpoint = "SHMILY.safetensors";
      }
      else if (id == 2) {
        this.generateUserFaceEntity.override_settings.sd_model_checkpoint = "Manmarumix.safetensors";
      }
      else if (id == 3) {
        this.generateUserFaceEntity.override_settings.sd_model_checkpoint = "SHMILY.safetensors";
        this.AN_lora = ", <lora:MoXinV1:0.6>, ";
      }
      else if (id == 4) {
        this.generateUserFaceEntity.override_settings.sd_model_checkpoint = "ReVAnimated.safetensors";
        this.AN_lora = ",<lora:blindbox_v1_mix:1>,";
      }
      else if (id == 5) {
        this.generateUserFaceEntity.override_settings.sd_model_checkpoint = "breakdomain_M2150.safetensors";
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
          const quality = 0.7; // 质量压缩比
          const resizedImageBase64 = canvas.toDataURL('image/jpeg', quality);
          this.generateUserFaceEntity.alwayson_scripts.controlnet.args[0].image = resizedImageBase64.split(',')[1];
          this.generateUserFaceEntity.alwayson_scripts.controlnet.args[1].image = resizedImageBase64.split(',')[1];
          this.generateUserFaceEntity.init_images[0] = resizedImageBase64.split(',')[1];
          this.generateUserFaceEntity.width = canvas.width
          this.generateUserFaceEntity.height = canvas.height
          this.AN_imageSrc = resizedImageBase64
        };
        image.src = e.target.result;
      };
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

    // 识别二维码
    async onFileChange_Qr(e) {
      const file = e.target.files[0];
      if (!file) return;

      const img = new Image();
      img.src = URL.createObjectURL(file);
      await img.decode();

      const canvas = document.createElement('canvas');
      canvas.width = img.naturalWidth;
      canvas.height = img.naturalHeight;

      const ctx = canvas.getContext('2d');
      ctx.drawImage(img, 0, 0);

      try {
        const result = await scan(canvas);
        if (result?.text) {
          this.Qr_UrlInput = result.text;
          this.QR_url_length = this.Qr_UrlInput.length
        } else {
          alert('无法识别二维码');
        }
      } catch (error) {
        console.error('二维码扫描错误:', error);
      }
    },

    triggerFileSelect_Qr() {
      // 触发文件输入框的点击事件
      this.$refs.fileInput_Qr.click();
    },

    getRandom_Qr() {
      //产生随机数
      // this.Qr_UrlInput= this.randomize[r]
      let random = Math.floor(Math.random() * 31);
      this.QrPrompt = this.randomize[random]
      this.QR_prompt_length = this.QrPrompt.length
    },

    updateQR_prompt_length() {
      this.QR_prompt_length = this.QrPrompt.length
    },

    updateQR_URL_length() {
      this.QR_url_length = this.Qr_UrlInput.length
    },

    //文生图
    async generatePic() {
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true
        return 
      }

      // 上锁
      this.isGenerating = true;

      this.isShowBar = true
      this.message = "请求正在处理...";
      //检查次数
      let isNumok = true;
      await request.get("/user/getNum").then(res => {
        var num = res.data;
        if (num < 2) {  
          isNumok = false;
          this.$message.error("剪刀数量不足")
          this.dialogVisible_pay=true
        }
      })
      if (isNumok == false) {
        this.isShowBar = false
        return this.isGenerating = false;
      }

      this.pictureIfo2 = this.pictureIfo
      // 发送文生图请求
      request.post("/user/textToImg", this.pictureIfo).then(res => {
        if (res != null) {
          if (res.code == 0) {
            // 处理请求发送成功的逻辑
            let parts = res.data.split(';');
            this.TextToImg_requestId= parts[0];
            this.TextToImg_queueNum=parseInt(parts[1], 10); 

            // request.get("/user/subtract", {
            //   params: {
            //     num: 2
            //   }
            // });
            // 设置定时器以间隔6秒检查请求状态
            let interval = setInterval(() => {
              request.get("/user/getUrl", {
                params: {
                  requestId: this.TextToImg_requestId,
                  queueNum: this.TextToImg_queueNum
                }
              }).then(res => {
                this.isShowBar = true
                if (res.code === 1) {
                  // 情况1：图片生成失败
                  if(res.message=="生成图片失败"){
                    this.$message.error("'文生图'生成失败")
                  }
                 
                  clearInterval(interval);
                  this.isShowBar = false
                  this.isGenerating = false;
                  
                } else if (res.code === 0) {
                  if (res.data > 1) {
                    // 情况3：请求排队中
                    this.message = `排队中，当前位置：${res.data}`;
                  } else if (res.data === 1) {
                    // 情况4：图片正在生成
                    this.message = "图片正在生成，请稍候...";
                  } else {
                    // 情况5：图片数据获取成功
                    this.shareFlag = 0;
                    clearInterval(interval); // 停止定时器
                    this.pictureBase64 = "data:image/png;base64," + res.data.url; // 显示图片
                    this.TextToImg_is_illegal=res.data._illegal
                    console.log(res.data._illegal)
                    this.srcList_text2image[0]=this.pictureBase64
                    this.isShowBar = false
                    this.isGenerating = false;
                    this.$message.success("'文生图'图片生成完毕");
                  }
                }
              }).catch(error => {
                clearInterval(interval); // 在出错时停止定时器
                this.$message.error("图片生成失败")
                this.isGenerating = false;
                this.isShowBar = false
                console.log(error)
              });
            }, 6000);

          } else {
            this.isGenerating = false;
            this.$message.error("图片生成失败")
          }
        }
      }).catch(() => {
        this.$message.error("图片生成失败")
        this.isShowBar = false
        this.isGenerating = false;
      })
    },

    //风格转绘
    async generateUserFace() {
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true
        return 
      }
      if (this.generateUserFaceEntity.init_images[0] == null) {
        return this.$message.error("请先选择图片")
      }
      if (this.selectedPortrait == null) {
        return this.$message.error("请选择风格")
      }
      // P操作
      if (this.isGenerate_AN) {
        return this.$message.error("图片正在生成中，请勿重复点击")
      }
      this.isGenerate_AN = true
      this.isShowBar_AN = true
      this.message_AN = "请求正在处理...";

      //判断生成次数
      let isNumOk = true;
      await request.get("/user/getNum").then(res => {
        if (res != null) {
          if (res.data < 4) {
            isNumOk = false
            this.$message.error("剪刀数量不足")
            this.dialogVisible_pay=true
          }
        }
      })
      if (isNumOk == false) {
        this.isShowBar_AN = false
        return this.isGenerate_AN = false
      }
      const processedPrompt = this.processPrompt(this.userFacePrompt);
      this.generateUserFaceEntity.prompt = processedPrompt + this.AN_prompt + this.AN_lora
      const processedNeg = this.processPrompt(this.AN_NegtivePrompt);
      this.generateUserFaceEntity.negative_prompt = processedNeg + this.AN_negative_prompt
      this.generateUserFaceEntity.denoising_strength = this.userFaceValue / 100
      request.post("/user/imgToImg", this.generateUserFaceEntity).then(res => {
        if (res != null) {
          if (res.code == 0) {
            // 处理生成图片成功的逻辑
            let parts = res.data.split(';');
            this.AN_requestId = parts[0];
            this.AN_queueNum= parseInt(parts[1], 10); // 提取出的数字，转换为整数

            // 设置定时器以间隔6秒检查请求状态
            let interval_AN = setInterval(() => {
              request.get("/user/getUrl", {
                params: {
                  requestId: this.AN_requestId,
                  queueNum: this.AN_queueNum
                }
              }).then(res => {
                if (res.code === 1) {
                  // 情况1：图片生成失败
                  clearInterval(interval_AN);
                  this.isShowBar_AN = false
                  this.isGenerate_AN = false;
                  this.$message.error("图片生成失败")
                } else if (res.code === 0) {
                  if (res.data > 1) {
                    // 情况2：请求排队中
                    this.message_AN = `排队中，当前位置：${res.data}`;
                  } else if (res.data === 1) {
                    // 情况3：图片正在生成
                    this.message_AN = "图片正在生成，请稍候...";
                  } else {
                    // 情况4：图片数据获取成功
                    clearInterval(interval_AN); // 停止定时器
                    this.userFaceRes = "data:image/png;base64," + res.data.url; // 显示图片
                    this.AN_is_illegal=res.data.is_illegal
                    this.srcList_stylechange[0]=this.this.userFaceRes
                    this.isShowBar_AN = false
                    this.isGenerate_AN = false;
                    this.shareFlag_AN = 0;
                    // request.get("/user/subtract", {
                    //   params: {
                    //     num: 4
                    //   }
                    // });
                    this.$message.success("'风格转绘'图片生成完毕");
                  }
                }
              }).catch(error => {
                clearInterval(interval_AN); // 在出错时停止定时器
                this.$message.error("图片生成失败")
                this.isGenerate_AN = false;
                this.isShowBar_AN = false
                console.log(error)
              });
            }, 6000);
          } else {
            this.isGenerate_AN = false;
            this.$message.error("图片生成失败")
          }
        }
      }).catch(() => {
        this.$message.error("图片生成失败")
        this.isShowBar_AN = false
        this.isGenerate_AN = false
      })
    },

    //生成二维码
    async generateQrcode() {
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true
        return 
      }
      if (this.Qr_UrlInput == '') {
        return this.$message.error("请先输入网址/文本")
      }

      // P操作
      if (this.isGenerating_Qr) {
        return this.$message.error("图片正在生成中，请勿重复操作");
      }
      this.isGenerating_Qr = true

      this.isShowBar_Qr = true
      this.message_Qr = "请求正在处理...";

      //判断次数
      let isNumOk = true
      await request.get("/user/getNum").then(res => {
        if (res != null) {
          if (res.data < 8) {
            isNumOk = false
            this.$message.error("剪刀数量不足")
            this.dialogVisible_pay=true
          }
        }
      })
      if (!isNumOk) {
        this.isShowBar_Qr = false
        return this.isGenerating_Qr = false
      }

      //修改请求实体
      this.pictureIfo_Qrcode.prompt = this.QrPrompt
      this.pictureIfo_Qrcode.negative_prompt += this.QrNegtive
      this.pictureIfo_Qrcode.alwayson_scripts.controlnet.args[0].weight = 1.15 + this.QrControlStrength / 100 - 0.5
      this.pictureIfo_Qrcode.alwayson_scripts.controlnet.args[1].weight = 0.25 + this.QrControlStrength / 100 - 0.5
      this.pictureIfo_Qrcode.alwayson_scripts.controlnet.args[2].weight = 1.30 + this.QrControlStrength / 100 - 0.5

      //生成朴素二维码，并赋值
      this.generateQRCode();
      //发送请求
      request.post("/user/textToImg", this.pictureIfo_Qrcode).then(res => {
        if (res != null) {
          if (res.code == 0) {
            // 处理生成图片成功的逻辑
            let parts = res.data.split(';');
            // let requestId=parts[0];
            // let queueNum= parseInt(parts[1], 10);
            this.Qr_requestId = parts[0];
            this.Qr_queueNum = parseInt(parts[1], 10); // 提取出的数字，转换为整数

            // 设置定时器以间隔6秒检查请求状态
            let interval_Qr = setInterval(() => {
              request.get("/user/getUrl", {
                params: {
                  requestId: this.Qr_requestId,
                  queueNum: this.Qr_queueNum
                }
              }).then(res => {
                if (res.code === 1) {
                  // 情况1：图片生成失败
                  clearInterval(interval_Qr);
                  this.isShowBar_Qr = false
                  this.isGenerating_Qr = false;
                  this.$message.error("图片生成失败")
                } else if (res.code === 0) {
                  if (res.data > 1) {
                    // 情况2：请求排队中
                    this.message_Qr = `排队中，当前位置：${res.data}`;
                  } else if (res.data === 1) {
                    // 情况3：图片正在生成
                    this.message_Qr = "图片正在生成，请稍候...";
                  } else {
                    // 情况4：图片数据获取成功
                    clearInterval(interval_Qr); // 停止定时器
                    this.QrcodeBase64 = "data:image/png;base64," + res.data.url; // 显示图片
                    this.Qr_is_illegal=res.data.is_illegal
                    this.srcList_Qrcode[0]=this.QrcodeBase64
                    this.shareFlag_Qr = 0;
                    this.isShowBar_Qr = false
                    this.isGenerating_Qr = false;
                    // request.get("/user/subtract", {
                    //   params: {
                    //     num: 8
                    //   }
                    // });
                    this.$message.success("'艺术二维码'图片生成完毕");
                  }
                }
              }).catch(error => {
                clearInterval(interval_Qr); // 在出错时停止定时器
                this.$message.error("图片生成失败")
                this.isGenerating_Qr = false;
                this.isShowBar_Qr = false
                console.log(error)
              });
            }, 6000);

          } else {
            this.isGenerating_Qr = false;
            this.$message.error("图片生成失败")
          }
        }
      }).catch(() => {
        this.$message.error("图片生成失败")
        this.isShowBar_Qr = false
        this.isGenerating_Qr = false
      })
    },

    // -----生成光影字
    async generateLightWord() {
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true
        return 
      }
      if (this.Lg_word.length == 0) {
        return this.$message.error("请先输入文字")
      }
      if (this.Lg_selectedPortrait == null) {
        return this.$message.error("请选择样式")
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
          if (res.data < 8) {
            isNumOk = false
            this.$message.error("剪刀数量不足")
            this.dialogVisible_pay=true
          }
        }
      })
      if (isNumOk == false) {
        this.isShowBar_Lg = false
        return this.isGenerating_Lg = false
      }

      await this.generateImage();
      request.post("/user/textToImg", this.pictureIfo_Lg).then(res => {
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
                    this.Lg_result = "data:image/png;base64," + res.data.url; 
                    this.Lg_is_illegal=res.data.is_illegal
                    this.srcList_Lg[0]=this.Lg_result
                    this.isShowBar_Lg = false
                    this.isGenerating_Lg = false;
                    this.shareFlag_Lg = 0;
                    // request.get("/user/subtract", {
                    //   params: {
                    //     num: 8
                    //   }
                    // });
                    this.$message.success("'光影字'图片生成完毕");
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


    async  generateImage() {
    return new Promise((resolve, reject) => {
  const canvas = document.createElement('canvas');
  canvas.width = 660;
  canvas.height = 924;
  const ctx = canvas.getContext('2d');

  // 设置Canvas的字体和绘制逻辑
  const setCanvasFontAndDraw = (font, bold = false) => {
    ctx.fillStyle = 'black';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = 'white';
    ctx.font = `${bold ? 'bold' : ''} 400px ${font}`; // 根据bold参数调整字体样式
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    // 绘制文本
    const chars = this.Lg_word.split('');
    const charHeight = 400;
    const totalCharsHeight = charHeight * chars.length;
    const startHeight = canvas.height * 4 / 7 - totalCharsHeight / 2 + charHeight / 2;

    chars.forEach((char, index) => {
      ctx.fillText(char, canvas.width / 2, startHeight + index * charHeight);
    });
    this.pictureIfo_Lg.alwayson_scripts.controlnet.args[0].image = canvas.toDataURL('image/png');
     // 图片绘制完成后解决Promise
     resolve(canvas.toDataURL('image/png'));
  };

  if (this.option_value_Lg === 'MaShanZheng-Regular') {
    // 加载MaShanZheng-Regular字体，不使用bold
    const font = new FontFace('MaShanZheng-Regular', `url(${require('@/assets/MaShanZheng-Regular.ttf')})`);
    font.load().then(() => {
      setCanvasFontAndDraw('MaShanZheng-Regular');
    }).catch(error => {
      console.error('Font loading failed:', error);
      reject(error);
    });
  }  else {
      setCanvasFontAndDraw('Arial', true);
    }
  });
},


    // 分享图片
    async sharePic(kind) {
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true
        return 
      }

      try {
        let sharePicIfo = {};
        let picBase64;
        if (kind == 1) {
          if (this.shareFlag == 2) {
            this.$message.warning("请生成图片后再分享")
            return
          }
          if (this.shareFlag == 1) {
            this.$message.warning("请勿重复分享图片")
            return
          }
          if (this.TextToImg_is_illegal== true) {
            this.$message.warning("图片违规，无法分享")
            return
          }
          sharePicIfo = this.pictureIfo2
          picBase64 = await this.compressImage(this.pictureBase64)
        }

        else if (kind == 2) {
          if (this.shareFlag_Qr == 2) {
            this.$message.warning("请生成图片后再分享")
            return
          }
          if (this.shareFlag_Qr == 1) {
            this.$message.warning("请勿重复分享图片")
            return
          }
          if (this.Qr_is_illegal== true) {
            this.$message.warning("图片违规，无法分享")
            return
          }
          picBase64 = await this.compressImage(this.QrcodeBase64)
        }

        else if (kind == 3) {
          if (this.shareFlag_AN == 2) {
            this.$message.warning("请生成图片后再分享")
            return
          }
          if (this.shareFlag_AN == 1) {
            this.$message.warning("请勿重复分享图片")
            return
          }
          if (this.AN_is_illegal== true) {
            this.$message.warning("图片违规，无法分享")
            return
          }
          picBase64 = await this.compressImage(this.userFaceRes)
        }

        else if (kind == 4) {
          if (this.shareFlag_Lg == 2) {
            this.$message.warning("请生成图片后再分享")
            return
          }
          if (this.shareFlag_Lg == 1) {
            this.$message.warning("请勿重复分享图片")
            return
          }
          if (this.Lg_is_illegal== true) {
            this.$message.warning("图片违规，无法分享")
            return
          }
          picBase64 = await this.compressImage(this.Lg_result)
        }

        const shareBody = {
          picIfo: sharePicIfo,
          picBase64: picBase64,
          type:kind
        }
        console.log("ok")
        console.log(kind)
        if (kind == 1) {
              this.shareFlag = 3
              console.log(this.shareFlag)
            }
            else if (kind == 2) {
              this.shareFlag_Qr = 3
            }
            else if (kind == 3) {
              this.shareFlag_AN = 3
            }
            else if (kind ==4) {
              this.shareFlag_Lg = 3
            }
        
        request.post("/user/sharePic", shareBody
        ).then(res => {
          if(res.code==0){
            this.$message.success("图片分享成功")
            if (kind == 1) {
              this.shareFlag = 1
            }
            else if (kind == 2) {
              this.shareFlag_Qr = 1
            }
            else if (kind == 3) {
              this.shareFlag_AN = 1
            }
            else if (kind ==4) {
              this.shareFlag_Lg = 1
            }
        }})
      }
      catch (error) {
        console.log(error)
      }
    },

    //生成朴素二维码，并赋值给请求实体
    generateQRCode() {
      const qrcodeContainer = document.createElement('div');

      new QRCode(qrcodeContainer, {
        text: this.Qr_UrlInput,
        width: 593,
        height: 593,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.M
      });

      this.$nextTick(() => {
        const qrCanvas = qrcodeContainer.querySelector('canvas');
        const qrSize = 593;
        const margin = this.Qr_padding; // 边距大小
        const totalSize = qrSize + 2 * margin; // 总大小包括边距

        // 创建新的 Canvas 来绘制带有边距的二维码
        const canvas = document.createElement('canvas');
        canvas.width = totalSize;
        canvas.height = totalSize;
        const ctx = canvas.getContext('2d');

        // 绘制背景
        ctx.fillStyle = '#888888'; // 背景颜色
        ctx.fillRect(0, 0, totalSize, totalSize);
        // 绘制二维码
        ctx.drawImage(qrCanvas, margin, margin, qrSize, qrSize);

        let base64QRCode = canvas.toDataURL("image/png");
        const base64 = base64QRCode.split(',')[1];
        this.pictureIfo_Qrcode.alwayson_scripts.controlnet.args[0].image = base64;
        this.pictureIfo_Qrcode.alwayson_scripts.controlnet.args[1].image = base64;
        this.pictureIfo_Qrcode.alwayson_scripts.controlnet.args[2].image = base64;
      });
    },

    //压缩图片
    compressImage(base64Str, quality = 0.6) {
      return new Promise((resolve, reject) => {
        const img = new Image();
        img.src = base64Str;
        img.onload = () => {
          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');
          canvas.width = img.width;
          canvas.height = img.height;
          ctx.drawImage(img, 0, 0, img.width, img.height);
          const newDataUrl = canvas.toDataURL('image/jpeg', quality);
          resolve(newDataUrl);
        };
        img.onerror = reject;
      });
    },

    inputNegPrompt() {
      this.inputNegPromptValue = true
      this.inputPromptValue = false
    },

    inputPrompt() {
      this.inputNegPromptValue = false
      this.inputPromptValue = true
    },

    //添加参考描述词    
    addToInput(color) {
      if (this.inputPromptValue == true) {
        if (!this.pictureIfo.prompt.includes(color)) {
          this.pictureIfo.prompt += (this.pictureIfo.prompt.length > 0 ? ', ' : '') + color;
        }
      }
      else {
        if (!this.pictureIfo.negative_prompt.includes(color)) {
          this.pictureIfo.negative_prompt += (this.pictureIfo.negative_prompt.length > 0 ? ', ' : '') + color;
        }
      }
    },

    goToLogin(){
      this.dialogVisible_gotoLogin=false;
      router.push("/login")
    },
    

    //新手教程弹框    
    instructionChange() {
      if(this.isLogined==false){
        this.dialogVisible_gotoLogin=true
        return 
      }
      const url = 'http://www.dirge.cc/#/instruction';
      window.open(url, '_blank');
    },
    //将种子改为-1    
    changeSeed() {
      this.pictureIfo.seed = -1
    },
    //固定种子    
    fixSeed() {
      this.$message.success("功能开发中")
    },



  },

  beforeDestroy(){

    if(this.isGenerating==true){
     request.get("/user/cancerQueue",{
      params:{
        requestId:this.TextToImg_requestId,
        queueNum:this.TextToImg_queueNum
      }
     })
    }
   else if(this.isGenerate_AN==true){
     request.get("/user/cancerQueue",{
      params:{
        requestId:this.AN_requestId,
        queueNum:this.AN_queueNum
      }
     })
    }
    else if(this.isGenerating_Qr==true){
     request.get("/user/cancerQueue",{
      params:{
        requestId:this.Qr_requestId,
        queueNum:this.Qr_queueNum
      }
     })
    }
    else return
  }
  

}
</script>
  
<style scoped>

@font-face {
  font-family: 'MaShanZheng-Regular';
  src: url('~@/assets/MaShanZheng-Regular.ttf') format('truetype');
  font-weight: normal;
  font-style: normal;
}
.custom-font {
  font-family: 'MaShanZheng-Regular', sans-serif;
}
.gallery {
  height: 250px;
  overflow-y: auto;
  overflow-x: hidden;
}

.portrait-card {
  cursor: pointer;
  position: relative;
  border: 1px solid transparent;
  transition: border-color 0.3s;
  padding-top: 100%;
  /* 1:1 Aspect Ratio */
}

.portrait-card:hover,
.portrait-card.is-selected {
  border-color: #409eff;
}

.image-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  /* Adjust this to change the image size */
  height: 0;
  padding-bottom: 100%;
  /* 1:1 Aspect Ratio */
  overflow: hidden;
  border-radius: 10px;
}

.image-container img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  text-align: center;
  padding: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}


.upload-container {
  position: relative;
  text-align: center;
}

.el-upload-dragger {
  position: relative;

}


.preview-image {
  max-width: 180px;
  max-height: 180px;
  display: block;
  margin: auto;
}

.text {
  margin-bottom: 12px;
  color: #4f4f4f;
  font-size: 14px;
}


.el-textarea__inner {

  background-color: #f1f2f6 !important;
}

.category-row {
  margin-right: 0;
  margin-left: 0;
}

.el-scrollbar__wrap {
  overflow-x: hidden;
}

.container {
  max-width: 1213px;
  margin: 0 auto;
  overflow-x: hidden;
}

.el-col {
  padding-right: 2px;
  padding-left: 2px;
}

.el-scrollbar__bar.is-horizontal {
  display: none;
  /* 隐藏横向滚动条 */
}

.scroll-container {
  height: 400px;
  overflow-y: auto;
  overflow-x: hidden;
}

.selected-colors-container {
  height: 100px;
  /* 根据需要调整高度 */
  overflow-y: auto;
  overflow-x: hidden;
}

.el-textarea__inner {
  background-color: rgba(0, 0, 0, 0.04) 0 0.625rem !important;
}

.el-tabs__item {
  font-size: 16px !important;
}</style>
  
  
