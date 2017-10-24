package com.blue.OkHttpUtil.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blue.spring.Base.CustomException;
import okhttp3.*;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: okhttp 工具类（新的HTTP请求类）
 * @email:
 * @author: lizhixin
 * @createDate: 14:04 2017/10/23
 */
public class OkHttpUtil {

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

        if(response.isSuccessful()){
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
            StringBuilder urlPath = new StringBuilder(sendUrl);
            if(dataMap.size() > 0){
                Iterator iterator = dataMap.entrySet().iterator();
//                sendUrl = sendUrl + "?";
                urlPath.append("?");

                while (iterator.hasNext()){
                    Map.Entry<String, String> param = (Map.Entry)iterator.next();
//                    sendUrl = sendUrl + param.getKey() + "=" + URLEncoder.encode(param.getValue(),"utf-8") + "&";

                    urlPath.append(param.getKey())
                            .append("=")
                            .append(URLEncoder.encode(param.getValue(),"utf-8"))
                            .append("&");
                }
//                sendUrl = sendUrl.substring(0,sendUrl.length()-1);
                urlPath.deleteCharAt(urlPath.length() - 1);
            }


//            URL url = new URL(sendUrl);
            URL url = new URL(urlPath.toString());
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

        if(response.isSuccessful()){
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

        String url = "http://cbase.cnlive.com/nry_test/add/live";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name","test");
//        String dateJson = JSON.toJSONString(jsonObject);
        String dateJson = "{\"createTime\":\"2017-10-24 15:19:03\",\"weight\":\"10\",\"spName\":\"\",\"pageUrl\":\"http://v.cnlive.com/other/v_ha4dgmbxha.html\",\"copyrightId\":\"1465679\",\"img\":\"[{\\\"format\\\":\\\"600*600\\\",\\\"url\\\":\\\"http://yweb0.cnliveimg.com/bc/82/2017/1023/1508727199486_6264.jpg\\\"}]\",\"contentType\":\"live\",\"extensions\":{\"rates\":\"2\"},\"cpId\":\"82\",\"subTitle\":\"\",\"categoryName\":\"其他\",\"title\":\"内容库测试\",\"area\":\"\",\"editorId\":\"1465679\",\"verTimestamp\":\"1508830549453\",\"description\":\"1111\",\"cpName\":\"李志鑫\",\"spId\":\"82\",\"actors\":\"\",\"year\":\"\",\"groupType\":\"program\",\"keywords\":\"live\",\"albumName\":\"\",\"publishTime\":\"2017-10-24 15:35:49\",\"status\":\"1\",\"categoryId\":\"100026\",\"director\":\"\",\"editorName\":\"李志鑫\",\"extension1\":\"\",\"extension2\":\"\",\"uuid\":\"82_7acf18aad9ae4ceea8178b207b2d271d\"}";
        Response response = postJson(url,dateJson);
        //获取返回的参数进行后续的判断处理
        System.out.println(JSON.parseObject(response.body().string()).toString());

//        Map<String,String> map = new HashMap<String, String>();
//        map.put("1","1");
//        map.put("2","2");
//        map.put("3","3");
//        Response response = getForValueOnUrl(url,map);

    }


}
