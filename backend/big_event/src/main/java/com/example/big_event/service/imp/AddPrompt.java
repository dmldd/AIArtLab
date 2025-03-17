package com.example.big_event.service.imp;

import com.example.big_event.pojo.picture.OverrideSettings;
import com.example.big_event.pojo.picture.StableDiffusionImg2Img;
import com.example.big_event.pojo.picture.StableDiffusionTextToImg;
import com.example.big_event.utils.translatorAPI.TranslateService;

import java.time.Year;

public class AddPrompt {
    public static StableDiffusionTextToImg  addPrompt_TextToImg(StableDiffusionTextToImg picIfo ){
        //翻译
        TranslateService translateService =new TranslateService();
        if(picIfo.getPrompt()!=null){
            picIfo.setPrompt(translateService.translateService(picIfo.getPrompt()));

        }
        if (picIfo.getNegative_prompt()!=null){
            picIfo.setNegative_prompt(translateService.translateService(picIfo.getNegative_prompt()));
        }

        //麦橘真人
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("麦橘v6.safetensors")){
            //开启修脸插件
            picIfo.getAlwayson_scripts().getADetailer().getArgs().get(0).setAd_model("face_yolov8n.pt");

            picIfo.getOverride_settings().setSd_vae("[StabilityAI]vae-ft-mse-840000-ema-pruned.safetensors");
            picIfo.setSampler_index("Euler a");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"EasyNegative, bad-hands-5, (worst quality, low quality), (text), (signature),(watermark),username,artist name,");
            picIfo.setPrompt(picIfo.getPrompt()+", ultra detailed, amazing fine details，4k, (photorealistic),  (masterpiece, top quality, best quality), ");
            return picIfo;
        }

        //SHMILY
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("SHMILY.safetensors")){
            System.out.println("SHMILY");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.getOverride_settings().setSd_vae("None");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+ ",EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad-hands-5, (worst quality, low quality:1.4), ng_deepnegative_v1_75t, badhand-v4,( blurry:1.5), lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, jpeg artifacts, signature, watermark, username, blurry, artist name, (monochrome:1.1), bad anatomy,bad hands, ((monochrome)),((grayscale)),futanari,full-package_futanari,penis_from_girl,newhalf,nipplepierces,");
            picIfo.setPrompt(picIfo.getPrompt()+ "(masterpiece, top quality, best quality, official art), 4k,  ultra - detailed, amazing fine details，ultra high res");
            return picIfo;
        }

        //Manmarumix
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("Manmarumix.safetensors")){
            System.out.println("Manmarumix");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.getOverride_settings().setSd_vae("None");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+ ",EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad-hands-5, (worst quality, low quality:1.4), ng_deepnegative_v1_75t, badhand-v4,( blurry:1.5), lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, jpeg artifacts, signature, watermark, username, blurry, artist name, (monochrome:1.1), bad anatomy,bad hands, ((monochrome)),((grayscale)),futanari,full-package_futanari,penis_from_girl,newhalf,nipplepierces,");
            picIfo.setPrompt(picIfo.getPrompt()+ "(masterpiece, top quality, best quality, official art), 4k,  ultra - detailed, amazing fine details，ultra high res");
            return picIfo;
        }

        //ReVAnimated
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("ReVAnimated.safetensors")){
            System.out.println("ReVAnimated");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.getOverride_settings().setSd_vae("[二次元]Orangemixs.pt");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+ ",EasyNegative, bad-hands-5, (worst quality, low quality:1.4), bad-hands-5, (worst quality, low quality:1.4), ng_deepnegative_v1_75t, badhand-v4,( blurry:1.5), lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, jpeg artifacts, signature, watermark, username, blurry, artist name, (monochrome:1.1), bad anatomy,bad hands, ((monochrome)),((grayscale)),futanari,full-package_futanari,penis_from_girl,newhalf,nipplepierces,");
            picIfo.setPrompt(picIfo.getPrompt()+ "(masterpiece, top quality, best quality, official art), 4k,  ultra - detailed, amazing fine details，ultra high res");
            return picIfo;
        }

        //breakdomain
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("breakdomain_M2150.safetensors")){
            picIfo.setPrompt(picIfo.getPrompt()+",(unity CG 8K wallpaper:1.05), (masterpiece, top quality, best quality, official art:1.2),Fine art, perfect ligthing");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"EasyNegative, bad-hands-5, (worst quality, low quality:1.4),  text, title, logo, signature");
            picIfo.getOverride_settings().setSd_vae("[二次元]Orangemixs.pt");
            return picIfo;
        }

        //dreamshaperXL turbo
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("dreamshaperXL_v21TurboDPMSDE.safetensors")){
            picIfo.setWidth(picIfo.getWidth()+200);
            picIfo.setHeight(picIfo.getHeight()+200);
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(low quality, worst quality, bad hands:1.4), cgi, text, signature, watermark, extra limbs, ");
            picIfo.setPrompt("(masterpiece:1.5), (highest quality:1.5), (best quality:1.5), 4k, ultra-detailed, "+picIfo.getPrompt());
            picIfo.getOverride_settings().setSd_vae("[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picIfo.setSampler_index("DPM++ SDE Karras");
            picIfo.setSteps(10);
            picIfo.setCfg_scale(2);
            return picIfo;
        }

        //Juggernaut XL -Lighting
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("Juggernaut.safetensors")){
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(low quality, worst quality, bad hands:1.3), cgi, text, signature, watermark, extra limbs,  ");
            picIfo.setPrompt("(masterpiece:1.3), (highest quality:1.3), (best quality:1.3), ultra-detailed, "+picIfo.getPrompt());
            picIfo.getOverride_settings().setSd_vae("[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picIfo.setSampler_index("DPM++ SDE");
            picIfo.setSteps(6);
            picIfo.setCfg_scale(2);
            return picIfo;
        }


        //Pony V6 Turbo merge
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("Pony.safetensors")){
            picIfo.setWidth(picIfo.getWidth()+200);
            picIfo.setHeight(picIfo.getHeight()+200);
            picIfo.setPrompt("score_9, score_8_up, score_7_up, score_6_up, score_5_up, score_4_up, "+picIfo.getPrompt());
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(low quality, worst quality, bad hands:1.4), cgi, text, signature, watermark, extra limbs,");
            picIfo.getOverride_settings().setSd_vae("sdxl_vae.safetensors");
            picIfo.setSampler_index("Euler a");
            picIfo.setSteps(10);
            picIfo.setCfg_scale(5);
            return picIfo;
        }

        //RealVisXL.safetensors
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("RealVisXL.safetensors")){
            picIfo.setWidth(picIfo.getWidth()+200);
            picIfo.setHeight(picIfo.getHeight()+200);
            picIfo.setPrompt("(masterpiece:1.5), (highest quality:1.5), (best quality:1.5),ultra-detailed, "+picIfo.getPrompt());
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(low quality, worst quality, bad hands:1.4), cgi, text, signature, watermark, extra limbs,");
            picIfo.getOverride_settings().setSd_vae("sdxl_vae.safetensors");
            picIfo.setSampler_index("DPM++ SDE");
            picIfo.setSteps(10);
            picIfo.setCfg_scale(1);
            return picIfo;
        }

        //Cute.
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("Cute.safetensors")){
            picIfo.setPrompt(picIfo.getPrompt()+",(unity CG 8K wallpaper:1.05), (masterpiece, top quality, best quality, official art:1.2),Fine art, perfect ligthing");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+" EasyNegative, error, missing fingers, extra digit, fewer digits, cropped,normal quality, jpeg artifacts, (signature:1.5), (watermark:1.5), (username:1.5), blurry, (worst quality, low quality:1.4), (bad anatomy), (inaccurate limb:1.2), (extra arms:1.2), (bad-artist:0.6),");
            picIfo.getOverride_settings().setSd_vae("[二次元]Orangemixs.pt");
            return picIfo;
        }

        //Animagine
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("Animagine.safetensors")){
            picIfo.setPrompt("very aesthetic, aesthetic, masterpiece, best quality, absurdres"+picIfo.getPrompt());
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+" watermark, username, lowres, (bad), text, error, fewer, extra, missing, worst quality, jpeg artifacts, low quality, unfinished, displeasing, oldest, early, chromatic aberration, extra digits, artistic error, username, scan, [abstract]");
            picIfo.getOverride_settings().setSd_vae("[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picIfo.setCfg_scale(7);
            picIfo.setSampler_index("Euler a");
            picIfo.setSteps(30);
            System.out.println(picIfo.getPrompt()+";");
            System.out.println(picIfo.getNegative_prompt());
            return picIfo;
        }

        return  picIfo;
    }


    //图生图
    public static StableDiffusionImg2Img  addPrompt_img2img(StableDiffusionImg2Img picIfo ){
        //翻译
        TranslateService translateService =new TranslateService();
        picIfo.setPrompt(translateService.translateService(picIfo.getPrompt()));
        picIfo.setNegative_prompt(translateService.translateService(picIfo.getNegative_prompt()));

        //麦橘真人
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("麦橘v6.safetensors")){
            if(picIfo.getAlwayson_scripts().getControlnet().getArgs().get(0)!=null){
                System.out.println("光影字");
                //表明是光影字体，什么也不添加
                return picIfo;
            }
            picIfo.getOverride_settings().setSd_vae("[StabilityAI]vae-ft-mse-840000-ema-pruned.safetensors");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"EasyNegative, bad-hands-5, (worst quality, low quality:1.4), \n" +
                    "lowres,bad anatomy,(text:1.5),error,extra digit,fewer digits,cropped,normal quality,jpeg artifacts,(signature:1.5),(watermark:1.5),username,artist name,(monochrome:1.1),(watermark:1.1)");
            picIfo.setPrompt(picIfo.getPrompt()+", ultra detailed, (photorealistic:1.4), ray tracing,(masterpiece, top quality, best quality :1.5), 4k,");

            return picIfo;
        }

        //SHMILY
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("SHMILY.safetensors")){
            System.out.println("SHMILY");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.getOverride_settings().setSd_vae("None");

            return picIfo;
        }

        //Manmarumix
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("Manmarumix.safetensors")){
            System.out.println("Manmarumix");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.getOverride_settings().setSd_vae("None");
            return picIfo;
        }

        //ReVAnimated
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("ReVAnimated.safetensors")){
            System.out.println("ReVAnimated");
            picIfo.setSampler_index("DPM++ 2M Karras");
            picIfo.getOverride_settings().setSd_vae("[二次元]Orangemixs.pt");
            return picIfo;
        }

        //breakdomain
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("breakdomain_M2150.safetensors")){
            picIfo.setPrompt(picIfo.getPrompt()+",(unity CG 8K wallpaper:1.05), (masterpiece, top quality, best quality, official art:1.2),Fine art, perfect ligthing");
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"EasyNegative, bad-hands-5, (worst quality, low quality:1.4),  text, title, logo, signature");
            picIfo.getOverride_settings().setSd_vae("[二次元]Orangemixs.pt");
            return picIfo;
        }


        //juggernautXL
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("juggernautXL_v9Rundiffusionphoto2.safetensors")){
            picIfo.setWidth(picIfo.getWidth()+400);
            picIfo.setHeight(picIfo.getHeight()+400);
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(worst quality,low resolution,bad hands,open mouth),distorted,twisted,watermark,");
            picIfo.setPrompt("masterpiece, highest quality,  "+picIfo.getPrompt());
            picIfo.getOverride_settings().setSd_vae("[StabilityAI (SDXL)]sdxl-vae.safetensors");;
            return picIfo;
        }
        //Juggernaut XL -Lighting
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("juggernautXL_v9Rdphoto2Lightning.safetensors")){
            picIfo.setWidth(picIfo.getWidth()+200);
            picIfo.setHeight(picIfo.getHeight()+200);
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(low quality, worst quality, bad hands:1.4), cgi, text, signature, watermark, extra limbs,  ");
            picIfo.setPrompt("(masterpiece:1.5), (highest quality:1.5), (best quality:1.5), 4k, ultra-detailed, "+picIfo.getPrompt());
            picIfo.getOverride_settings().setSd_vae("[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picIfo.setSampler_index("DPM++ SDE");
            picIfo.setSteps(5);
            picIfo.setCfg_scale(2);
            return picIfo;
        }


        //SDXLFaetastic_v24.safetensors
        if(picIfo.getOverride_settings().getSd_model_checkpoint().equals("SDXLFaetastic_v24.safetensors")){
            picIfo.setWidth(picIfo.getWidth()+200);
            picIfo.setHeight(picIfo.getHeight()+200);
            picIfo.setNegative_prompt(picIfo.getNegative_prompt()+"(low quality, worst quality, bad hands:1.4), cgi, text, signature, watermark, extra limbs,");
            picIfo.setPrompt("(masterpiece:1.5), (highest quality:1.5), (best quality:1.5), 4k, ultra-detailed, "+picIfo.getPrompt());
            picIfo.getOverride_settings().setSd_vae("[StabilityAI (SDXL)]sdxl-vae.safetensors");
            picIfo.setSampler_index("DPM++ 2M");
            picIfo.setSteps(25);
            picIfo.setCfg_scale(7);
            return picIfo;
        }

        return  picIfo;
    }

}
