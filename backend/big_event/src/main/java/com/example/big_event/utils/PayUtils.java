package com.example.big_event.utils;
import java.util.*;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.codec.binary.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class PayUtils {

    /**
     *
     * @param product_type
     * @param price
     * @param num

     * @return
     */
    public static String pay(String product_type, Double price, int num,int userId) {
        try {
            /**
             * @param appid appid
             * @param appsecret 密钥
             * @param url  订单id
             */
            String appid = "201906164748";
            String appsecret = "90fa66450b1c2a08441b135e69e9690e";
            String trade_order_id = UUID.randomUUID().toString().replace("-", "");
            Map<String, Object> sortParams = new HashMap<>();
            sortParams.put("version", "1.1");
            sortParams.put("appid", appid);
            sortParams.put("trade_order_id", trade_order_id);
            sortParams.put("total_fee", price);
            sortParams.put("title", product_type+"_"+userId);
            sortParams.put("time", getSecondTimestamp(new Date()));
            // 非必填 用户取消支付，跳转的页面   经过测试，没有触发机制，建议不传递
            sortParams.put("callback_url", "http://58.87.103.114:7651/payCallBack");
            //必填。用户支付成功后，我们服务器会主动发送一个post消息到这个网址(注意：当前接口内，SESSION内容无效)
            sortParams.put("notify_url", "http://58.87.103.114:7651/payCallBack");
            //用户支付后，支付url重定向为目标地址，url_qrcode ，不会直接跳转，只有当支付完成后，再次刷新 url_qrcode中的连接，才会跳转。
            sortParams.put("return_url", "http://www.dirge.cc/#/userIfo/myStore");
            sortParams.put("plugins", "我是备注信息");
            sortParams.put("nonce_str", getRandomNumber(9));
            sortParams.put("hash", createSign(sortParams, appsecret));
            System.out.println("开始调三方接口");
            String response = HttpUtils.httppostjson("https://api.xunhupay.com/payment/do.html", JSON.toJSONString(sortParams));
            System.out.println("调三方接口结束");
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject != null) {
                Integer errorcode = jsonObject.getInteger("errcode");
                String errmsg = jsonObject.getString("errmsg");
                if (errorcode == 0 && StringUtils.equals(errmsg, "success!")) {
//                    System.out.println("返回跳转连接成功");
//                    System.out.println(jsonObject.get("url_qrcode")); // 可用的收款二维码
                    return jsonObject.getString("url_qrcode");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密钥
     *
     * @param params
     * @param privateKey
     * @return
     */
    public static String createSign(Map<String, Object> params, String privateKey) {
        StringBuilder sb = new StringBuilder();
        params.entrySet().stream().sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey())).forEach(a ->{
            sb.append(a).append("&");});
        sb.deleteCharAt(sb.length()-1);
        sb.append(privateKey);
        String s = SecureUtil.md5(sb.toString());
        return s;
    }

    /**
     * 获取精确到秒的时间戳   原理 获取毫秒时间戳，因为 1秒 = 100毫秒 去除后三位 就是秒的时间戳
     *
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    /**
     * 生成一个随机数字
     *
     * @param length 长度自定义
     * @return
     */
    public static String getRandomNumber(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
