package com.example.big_event.service.imp.queue;
import jdk.jshell.Snippet;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestStatusService {
    //-2表示生成失败，-1表示生成完成 0表示正在生成 1，2表示队列位置
    private  Map<String, Integer> requestStatusMap1 = new ConcurrentHashMap<>();
    private  Map<String, Integer> requestStatusMap2 = new ConcurrentHashMap<>();
    private  Map<String, Integer> requestStatusMap3 = new ConcurrentHashMap<>();
    private  Map<String, Integer> requestStatusMap4 = new ConcurrentHashMap<>();
    private  Map<String, Integer> requestStatusMap5 = new ConcurrentHashMap<>();
    private  Map<String, Integer> requestStatusMap6 = new ConcurrentHashMap<>();
    private  Map<String, Integer> requestStatusMap7 = new ConcurrentHashMap<>();
    //添加status
    public void createRequestStatus(String requestId,Integer status,Integer queueNum){
        if(queueNum==1){
            requestStatusMap1.put(requestId, status);
        }
       else if(queueNum==2){
            requestStatusMap2.put(requestId, status);
        }
       else if(queueNum==3){
            requestStatusMap3.put(requestId, status);
        }
        else if(queueNum==4){
            requestStatusMap4.put(requestId, status);
        }
        else if(queueNum==5){
            requestStatusMap5.put(requestId, status);
        }
        else if(queueNum==6){
            requestStatusMap6.put(requestId, status);
        }
        else if(queueNum==7){
            requestStatusMap7.put(requestId, status);
        }
    }

    //更新status
    public void updateRequestStatus(String requestId, Integer status,Integer queueNum){
        if(queueNum==1){
            requestStatusMap1.put(requestId, status);
        }
        else if(queueNum==2){
            requestStatusMap2.put(requestId, status);
        }
        else if(queueNum==3){
            requestStatusMap3.put(requestId, status);
        }
        else if(queueNum==4){
            requestStatusMap4.put(requestId, status);
        }
        else if(queueNum==5){
            requestStatusMap5.put(requestId, status);
        }
        else if(queueNum==6){
            requestStatusMap6.put(requestId, status);
        }
        else if(queueNum==7){
            requestStatusMap7.put(requestId, status);
        }
    }

    //移除status记录
    public void removeStatus(String requestId,Integer queueNum){
        if(queueNum==1){
            requestStatusMap1.remove(requestId);
        }
        else if(queueNum==2){
            requestStatusMap2.remove(requestId);
        }
        else if(queueNum==3){
            requestStatusMap3.remove(requestId);
        }
        else if(queueNum==4){
            requestStatusMap4.remove(requestId);
        }
        else if(queueNum==5){
            requestStatusMap5.remove(requestId);
        }
        else if(queueNum==6){
            requestStatusMap6.remove(requestId);
        }
        else if(queueNum==7){
            requestStatusMap7.remove(requestId);
        }
    }

    //获取status
    public Integer getRequestStatus(String requestId,Integer queueNum){
        if(queueNum==1){
            return requestStatusMap1.get(requestId);
        }
        else if(queueNum==2){
            return requestStatusMap2.get(requestId);
        }
        else if(queueNum==3){
            return requestStatusMap3.get(requestId);
        }
        else if(queueNum==4){
            return requestStatusMap4.get(requestId);
        }
        else if(queueNum==5){
            return requestStatusMap5.get(requestId);
        }
        else if(queueNum==6){
            return requestStatusMap6.get(requestId);
        }
        else if(queueNum==7){
            return requestStatusMap7.get(requestId);
        }
        else return  null;
    }

   //将除了-1，-2，-3状态外所有状态减1
    public void  updataAllStatus(Integer queueNum){
        if(queueNum==1){
            requestStatusMap1.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap1.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
        else if(queueNum==2){
            requestStatusMap2.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap2.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
        else if(queueNum==3){
            requestStatusMap3.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap3.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
        else if(queueNum==4){
            requestStatusMap4.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap4.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
        else if(queueNum==5){
            requestStatusMap5.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap5.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
        else if(queueNum==6){
            requestStatusMap6.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap6.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
        else if(queueNum==7){
            requestStatusMap7.forEach((key, value) -> {
                if (value != 0 && value != -1 && value != -2 && value!=-3) {
                    requestStatusMap7.computeIfPresent(key, (k, v) -> v - 1);
                }
            });
        }
    }

    //取消排队后的更新
    public void updateAfterStatus(Integer queueNum,Integer position){
        if(queueNum==1){
            for (String requestId : requestStatusMap1.keySet()) {
                int status = requestStatusMap1.get(requestId);
                if (status > position) {
                    requestStatusMap1.put(requestId, status - 1);
                }
            }
        }
        else if(queueNum==2){
            for (String requestId : requestStatusMap2.keySet()) {
                int status = requestStatusMap2.get(requestId);
                if (status > position) {
                    requestStatusMap2.put(requestId, status - 1);
                }
            }
        }
        else if(queueNum==3){
            for (String requestId : requestStatusMap3.keySet()) {
                int status = requestStatusMap3.get(requestId);
                if (status > position) {
                    requestStatusMap3.put(requestId, status - 1);
                }
            }
        }
        else if(queueNum==4){
            for (String requestId : requestStatusMap4.keySet()) {
                int status = requestStatusMap4.get(requestId);
                if (status > position) {
                    requestStatusMap4.put(requestId, status - 1);
                }
            }
        }
        else if(queueNum==5){
            for (String requestId : requestStatusMap5.keySet()) {
                int status = requestStatusMap5.get(requestId);
                if (status > position) {
                    requestStatusMap5.put(requestId, status - 1);
                }
            }
        }
        else if(queueNum==6){
            for (String requestId : requestStatusMap6.keySet()) {
                int status = requestStatusMap6.get(requestId);
                if (status > position) {
                    requestStatusMap6.put(requestId, status - 1);
                }
            }
        }
        else if(queueNum==7){
            for (String requestId : requestStatusMap7.keySet()) {
                int status = requestStatusMap7.get(requestId);
                if (status > position) {
                    requestStatusMap7.put(requestId, status - 1);
                }
            }
        }



    }
    //打印map
    public void printMap(Integer queueNum){
        if(queueNum==1){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : requestStatusMap1.entrySet()) {
                sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("; ");
            }
            // 输出格式化后的 requestStatusMap 内容
            System.out.println(sb.toString().trim());
        }
        else if(queueNum==2){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : requestStatusMap2.entrySet()) {
                sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("; ");
            }
            // 输出格式化后的 requestStatusMap 内容
            System.out.println(sb.toString().trim());
        }
        else if(queueNum==3){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : requestStatusMap3.entrySet()) {
                sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("; ");
            }
            // 输出格式化后的 requestStatusMap 内容
            System.out.println(sb.toString().trim());
        }
    }


}
