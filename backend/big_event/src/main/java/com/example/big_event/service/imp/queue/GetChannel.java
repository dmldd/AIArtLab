package com.example.big_event.service.imp.queue;

import com.example.big_event.service.imp.queue.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetChannel {
    @Autowired
    private ImageProcessingService1 imageProcessingService1;
    @Autowired
    private ImageProcessingService2 imageProcessingService2;
    @Autowired
    private ImageProcessingService3 imageProcessingService3;
    @Autowired
    private ImageProcessingService4 imageProcessingService4;
    @Autowired
    private ImageProcessingService5 imageProcessingService5;
    @Autowired
    private ImageProcessingService6 imageProcessingService6;
    @Autowired
    private ImageProcessingService7 imageProcessingService7;
    public Integer getChannel(String checkPoint){
        if (checkPoint.equals("麦橘v6.safetensors")){
                return 1;
        }
         if (checkPoint.equals("dreamshaperXL_v21TurboDPMSDE.safetensors")){
                return 2;
        }

        if (checkPoint.equals("breakdomain_M2150.safetensors")){
                return 3;
        }
//         if(checkPoint.equals("SHMILY.safetensors")||checkPoint.equals("Manmarumix.safetensors")||checkPoint.equals("ReVAnimated.safetensors")){
//            if(cannel==1){
//                return 3;
//            }
//            else if(cannel==2){
//                return 4;
//            }
//            else return null;
//        }

         if(checkPoint.equals("Cute.safetensors")){
                return 4;
        }

        else if(checkPoint.equals("Animagine.safetensors")){
                return 5;
        }

        else if(checkPoint.equals("Juggernaut.safetensors")){
                return 6;
        }

        else return null;
    }

    public void processQueue(Integer channel){
        if (channel==1){
            imageProcessingService1.processQueue();
        }
        else if (channel==2){
            imageProcessingService2.processQueue();
        }
        else if (channel==3){
            imageProcessingService3.processQueue();
        }
        else if (channel==4){
            imageProcessingService4.processQueue();
        }
        else if (channel==5){
            imageProcessingService5.processQueue();
        }
        else if (channel==6){
            imageProcessingService6.processQueue();
        }
        else if (channel==7){
            imageProcessingService7.processQueue();
        }
    }
}
