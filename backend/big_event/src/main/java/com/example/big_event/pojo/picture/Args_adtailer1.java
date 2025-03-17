package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Args_adtailer1 {
    private String    ad_model= "face_yolov8n.pt";
    private String    ad_prompt="";
    private String     ad_negative_prompt= "";
     private double       ad_confidence =0.3;
    private Integer         ad_mask_k_largest= 0;
    private double        ad_mask_min_ratio=0.0;
    private double        ad_mask_max_ratio =1.0;
    private Integer         ad_dilate_erode =32;
    private Integer        ad_x_offset= 0;
    private Integer        ad_y_offset= 0;
    private String     ad_mask_merge_invert ="None";
    private Integer        ad_mask_blur= 4;
    private double       ad_denoising_strength =0.4;
   private boolean        ad_inpaint_only_masked =true;
    private Integer       ad_inpaint_only_masked_padding= 0;
    private boolean        ad_use_inpaint_width_height =false;
    private Integer       ad_inpaint_width=512;
    private Integer       ad_inpaint_height= 512;
    private boolean        ad_use_steps =true;
    private Integer       ad_steps= 28;
    private boolean        ad_use_cfg_scale =false;
    private double      ad_cfg_scale= 7.0;
    private boolean       ad_use_sampler= false;
    private String       ad_sampler= "DPM++ 2M Karras";
    private boolean       ad_use_noise_multiplier= false;
    private double      ad_noise_multiplier= 1.0;
    private boolean       ad_use_clip_skip= false;
    private Integer       ad_clip_skip= 1;
    private boolean       ad_restore_face= false;
    private String     ad_controlnet_model= "None";
    private String       ad_controlnet_module= "None";
    private double       ad_controlnet_weight= 1.0;
    private double        ad_controlnet_guidance_start= 0.0;
    private double       ad_controlnet_guidance_end= 1.0;
}
