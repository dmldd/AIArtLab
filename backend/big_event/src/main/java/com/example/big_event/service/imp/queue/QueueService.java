package com.example.big_event.service.imp.queue;

import com.example.big_event.pojo.QueueWrapper;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class QueueService {
    private Queue<QueueWrapper> requestQueue1 = new ConcurrentLinkedQueue<>();
    private Queue<QueueWrapper> requestQueue2 = new ConcurrentLinkedQueue<>();
    private Queue<QueueWrapper> requestQueue3 = new ConcurrentLinkedQueue<>();
    private Queue<QueueWrapper> requestQueue4 = new ConcurrentLinkedQueue<>();
    private Queue<QueueWrapper> requestQueue5 = new ConcurrentLinkedQueue<>();
    private Queue<QueueWrapper> requestQueue6 = new ConcurrentLinkedQueue<>();
    private Queue<QueueWrapper> requestQueue7 = new ConcurrentLinkedQueue<>();

    //入队列
    public void enqueue(QueueWrapper request,Integer queueNum) {
        if (queueNum==1){
            requestQueue1.add(request);
        }
        else if(queueNum==2){
            requestQueue2.add(request);
        }
        else if(queueNum==3){
            requestQueue3.add(request);
        }
        else if(queueNum==4){
            requestQueue4.add(request);
        }
        else if(queueNum==5){
            requestQueue5.add(request);
        }
        else if(queueNum==6){
            requestQueue6.add(request);
        }
        else if(queueNum==7){
            requestQueue7.add(request);
        }
    }

    //出队列
    public QueueWrapper dequeue(Integer queueNum) {
        if (queueNum==1){
            return requestQueue1.poll();
        }
        else if(queueNum==2){
            return requestQueue2.poll();
        }
        else if(queueNum==3){
            return requestQueue3.poll();
        }
        else if(queueNum==4){
            return requestQueue4.poll();
        }
        else if(queueNum==5){
            return requestQueue5.poll();
        }
        else if(queueNum==6){
            return requestQueue6.poll();
        }
        else if(queueNum==7){
            return requestQueue7.poll();
        }
        return null;
    }

    //判断是否为空
    public boolean isEmpty(Integer queueNum) {
        if (queueNum==1){
            return requestQueue1.isEmpty();
        }
        else if(queueNum==2){
            return requestQueue2.isEmpty();
        }
        else if(queueNum==3){
            return requestQueue3.isEmpty();
        }
        else if(queueNum==4){
            return requestQueue4.isEmpty();
        }
        else if(queueNum==5){
            return requestQueue5.isEmpty();
        }
        else if(queueNum==6){
            return requestQueue6.isEmpty();
        }
        else if(queueNum==7){
            return requestQueue7.isEmpty();
        }

        return true;
    }

    //队列大小
    public  Integer getSize(Integer queueNum){
        if (queueNum==1){
            return requestQueue1.size();
        }
        else if(queueNum==2){
            return requestQueue2.size();
        }
        else if(queueNum==3){
            return requestQueue3.size();
        }
        else if(queueNum==4){
            return requestQueue4.size();
        }
        else if(queueNum==5){
            return requestQueue5.size();
        }
        else if(queueNum==6){
            return requestQueue6.size();
        }
        else if(queueNum==7){
            return requestQueue7.size();
        }
        return null;
    }

    //取消排队
    public void cancerQueue(String requestId,Integer queueNum){
        if (queueNum==1){
            Iterator<QueueWrapper> iterator = requestQueue1.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
        else if(queueNum==2){
            Iterator<QueueWrapper> iterator = requestQueue2.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
        else if(queueNum==3){
            Iterator<QueueWrapper> iterator = requestQueue3.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
        else if(queueNum==4){
            Iterator<QueueWrapper> iterator = requestQueue4.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
        else if(queueNum==5){
            Iterator<QueueWrapper> iterator = requestQueue5.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
        else if(queueNum==6){
            Iterator<QueueWrapper> iterator = requestQueue6.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
        else if(queueNum==7){
            Iterator<QueueWrapper> iterator = requestQueue7.iterator();
            while (iterator.hasNext()) {
                QueueWrapper item = iterator.next();
                if (item.getRequestId().equals(requestId)) {
                    iterator.remove(); // 移除找到的元素
                }
            }
        }
    }

    //打印队列
    public void print(){
        System.out.println("ok"+requestQueue1);
    }

}
