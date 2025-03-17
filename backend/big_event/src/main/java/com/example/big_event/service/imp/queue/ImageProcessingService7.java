package com.example.big_event.service.imp.queue;
import com.alibaba.fastjson.JSON;
import com.example.big_event.service.imp.SendToApi;
import com.example.big_event.pojo.ApiResponse;
import com.example.big_event.pojo.QueueWrapper;
import com.example.big_event.pojo.picture.Return_pic;
import com.example.big_event.pojo.picture.StableDiffusionImg2Img;
import com.example.big_event.pojo.picture.StableDiffusionTextToImg;
import com.example.big_event.pojo.picture.Tagger;
import com.example.big_event.service.imp.UserServiceImp;
import com.example.big_event.service.imp.illegal_test;
import com.example.big_event.utils.ImageCompressor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
@Service
public class ImageProcessingService7 {
    private static  final Integer  QUEUE_NUM=7;
    @Autowired
    private RequestStatusService requestStatusService;
    @Autowired
    private QueueService queueService;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private RequestUrlService requestUrlService;
    @Autowired
    private illegal_test illegal_test;
    @Value("${ENDPOINT}")
    private String ENDPOINT;
    @Value("${ACCESS_KEY_ID}")
    private String ACCESS_KEY_ID;
    @Value("${SECRET_ACCESS_KEY}")
    private String SECRET_ACCESS_KEY;
    @Value("${BUCKET_NAME}")
    private String BUCKET_NAME;

    private InputStream base64ToInputStream(String base64String) {
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        return new ByteArrayInputStream(imageBytes);
    }

    // 当前正在处理的请求数量
    private AtomicInteger currentProcessingCount = new AtomicInteger(0);
    // API允许的最大排队请求数量
    final int maxQueueSize = 3; // 2个排队加上1个正在处理的请求
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    @Async
    public void processQueue() {
        if (isProcessing.getAndSet(true)) {
            return;
        }
        // 定义一个局部变量来引用sendRequestToApi方法
        Consumer<QueueWrapper> sendRequest = this::sendRequestToApi;
        // 使用一个单独的线程来处理队列
        new Thread(() -> {
            try {
                while (!queueService.isEmpty(QUEUE_NUM)) {
                    if (currentProcessingCount.get() < maxQueueSize) {
                        QueueWrapper requestWrapper = queueService.dequeue(QUEUE_NUM);
                        sendRequest.accept(requestWrapper);
                        // 增加正在处理的请求计数
                        currentProcessingCount.incrementAndGet();
                    } else
                        try {
                            // 如果当前处理的数量达到最大值，暂停一段时间
                            Thread.sleep(1000 * 10); // 例如，等待10秒
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // 恢复中断状态
                            break; // 退出循环
                        }
                }
            } finally {
                isProcessing.set(false);
            }
        }).start();
    }

    private void sendRequestToApi(QueueWrapper requestWrapper) {
        new Thread(() -> {
            try {
                processRequest(requestWrapper);
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
    }

    private void processRequest(QueueWrapper requestWrapper) {
        String requestId = requestWrapper.getRequestId();
        //文生图
        if (requestWrapper.getTextToImgIfo() != null) {
            String url = userServiceImp.getSdUrl(QUEUE_NUM) + "/sdapi/v1/txt2img";
            StableDiffusionTextToImg stableDiffusionTextToImg = requestWrapper.getTextToImgIfo();
            try {
                // 更改状态
                requestStatusService.updateRequestStatus(requestId, currentProcessingCount.get(), QUEUE_NUM);
                // 向API发送请求，并接收响应
                String response = SendToApi.sendToApi(url, stableDiffusionTextToImg, null, null);
                ApiResponse apiResponse = JSON.parseObject(response, ApiResponse.class);

                //对图片压缩
                apiResponse.getImages()[0]=  ImageCompressor.compressImageBase64(apiResponse.getImages()[0], (float) 0.9);
                //检测是否违规
                Boolean is_illegal= illegal_test.Is_illegal(apiResponse.getImages()[0],QUEUE_NUM);

                // 将生成的图片URL放入urlMap
                Return_pic return_pic=new Return_pic(apiResponse.getImages()[0],is_illegal);
                requestUrlService.createRequestUrl(requestId, return_pic);

                // 更新请求状态为-1（处理完成）
                requestStatusService.updateRequestStatus(requestId, -1, QUEUE_NUM);
            } catch (Exception e) {
                System.out.println("Error while processing request " + requestId + ": " + e.getMessage());
                // 在出现异常时更新请求状态
                requestStatusService.updateRequestStatus(requestId, -2, QUEUE_NUM); // 假设-2表示出错
            } finally {
                currentProcessingCount.decrementAndGet(); // 减少正在处理的请求计求
                requestStatusService.updataAllStatus(QUEUE_NUM);
            }
        }
        //图生图
        else {
            StableDiffusionImg2Img stableDiffusionImg2Img= requestWrapper.getImg2ImgIfo();
            String url = userServiceImp.getSdUrl(QUEUE_NUM) + "/sdapi/v1/img2img";
            try {
                // 更改状态
                requestStatusService.updateRequestStatus(requestId, currentProcessingCount.get(), QUEUE_NUM);
                if(stableDiffusionImg2Img.getAlwayson_scripts()!=null){
                    //二次元头像
                    Tagger tagger=new Tagger(stableDiffusionImg2Img.getInit_images().get(0),"wd-v1-4-moat-tagger.v2",0.20,"","");
                    String url_Tagger = userServiceImp.getSdUrl(QUEUE_NUM)+"/tagger/v1/interrogate";
                    try {
                        String response_Tagger = SendToApi.sendToApi(url_Tagger, null, tagger, null);
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode rootNode = mapper.readTree(response_Tagger);
                        JsonNode tagNode = rootNode.path("caption").path("tag");
                        StringJoiner joiner = new StringJoiner(", ");
                        Iterator<String> fieldNames = tagNode.fieldNames();
                        while (fieldNames.hasNext()) {
                            joiner.add(fieldNames.next());
                        }
                        String result_Tagger = joiner.toString();
                        stableDiffusionImg2Img.setPrompt(stableDiffusionImg2Img.getPrompt()+result_Tagger);
                    }catch (Exception e){
                        System.out.println("获取Tagger失败 " + requestId + ": " + e.getMessage());
                        // 在出现异常时更新请求状态
                        requestStatusService.updateRequestStatus(requestId, -2, QUEUE_NUM); // 假设-2表示出错
                    }
                }
                // 向API发送图生图请求，并接收响应
                String response = SendToApi.sendToApi(url, null, null, stableDiffusionImg2Img);
                ApiResponse apiResponse = JSON.parseObject(response, ApiResponse.class);
                //根据是否为"我擦擦擦"功能进行图片压缩
                if(stableDiffusionImg2Img.getMask()==null){
                    apiResponse.getImages()[0]=  ImageCompressor.compressImageBase64(apiResponse.getImages()[0], (float) 0.9);
                }
                //检测是否违规
                Boolean is_illegal= illegal_test.Is_illegal(apiResponse.getImages()[0],QUEUE_NUM);

                // 将生成的图片URL放入urlMap
                Return_pic return_pic=new Return_pic(apiResponse.getImages()[0],is_illegal);
                requestUrlService.createRequestUrl(requestId, return_pic);

                // 更新请求状态为-1（处理完成）
                requestStatusService.updateRequestStatus(requestId, -1, QUEUE_NUM);
            } catch (Exception e) {
                System.out.println("生成图片失败" + requestId + ": " + e.getMessage());
                // 在出现异常时更新请求状态
                requestStatusService.updateRequestStatus(requestId, -2, QUEUE_NUM); // 假设-2表示出错
            } finally {
                currentProcessingCount.decrementAndGet(); // 减少正在处理的请求计求
                requestStatusService.updataAllStatus(QUEUE_NUM);
            }
        }

    }

}
