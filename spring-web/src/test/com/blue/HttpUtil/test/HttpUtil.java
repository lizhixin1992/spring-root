package com.blue.HttpUtil.test;

import com.alibaba.fastjson.JSONObject;
import com.blue.spring.Base.CustomException;
import com.squareup.okhttp.*;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: okhttp 工具类
 * @email:
 * @author: lizhixin
 * @createDate: 14:04 2017/10/23
 */
public class HttpUtil {

    /**
     *@description: POST请求（contentType=application/json）
     *@params:
     *@author: lizhixin
     *@createDate: 14:51 2017/10/23
    */
    public static Response postJson(String url,String dataJson) throws CustomException {
        Response response = null;
        try {
            String sendType = "POST";
            String contentType = "application/json";
            OkHttpClient okHttpClient = new OkHttpClient();

            MediaType mediaType = MediaType.parse(contentType);
            RequestBody requestBody = RequestBody.create(mediaType,dataJson);

            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(url)
                    .header("User-Agent", "")
                    .addHeader("Authorization", "")
                    .build();

            response = okHttpClient.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(response.isRedirect()){
            System.out.println(response.isSuccessful());
            return response;
        }else{
            System.out.println(response.isSuccessful());
            throw new CustomException(response);
        }
    }

    public static Response getForValueOnUrl(String sendUrl,Map<String,String> dataMap) throws CustomException{
        Response response = null;
        try {
            String sendType = "GET";
//            StringBuilder urlPath = new StringBuilder(sendUrl);
            if(dataMap.size() > 0){
                Iterator iterator = dataMap.entrySet().iterator();
                sendUrl = sendUrl + "?";
//                urlPath.append("?");

                while (iterator.hasNext()){
                    Map.Entry<String, String> param = (Map.Entry)iterator.next();
                    sendUrl = sendUrl + param.getKey() + "=" + URLEncoder.encode(param.getValue(),"utf-8") + "&";

//                    urlPath.append(param.getKey())
//                            .append("=")
//                            .append(URLEncoder.encode(param.getValue(),"utf-8"))
//                            .append("&");
                }
                sendUrl = sendUrl.substring(0,sendUrl.length()-1);
//                urlPath.deleteCharAt(urlPath.length() - 1);
            }


            URL url = new URL(sendUrl);
//            URL url = new URL(urlPath.toString());
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .header("User-Agent", "")
                    .addHeader("Authorization", "")
                    .build();

            response = okHttpClient.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(response.isRedirect()){
            System.out.println(response.isSuccessful());
            return response;
        }else{
            System.out.println(response.isSuccessful());
            throw new CustomException(response);
        }
    }

    public static void main(String[] args) throws Exception{
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        Request request = new Request.Builder().url("http://www.baidu.com").build();
//
//        Response response = okHttpClient.newCall(request).execute();

        String url = "http://www.baidu.com";
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name","test");
//        String dateJson = JSON.toJSONString(jsonObject);
//        Response response = postJson(url,dateJson);

        Map<String,String> map = new HashMap<>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        Response response = getForValueOnUrl(url,map);

    }


}
