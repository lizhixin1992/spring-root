package com.blue.spring.util;

import com.google.common.base.Joiner;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaobin
 */
public final class HttpClientUtils {
    private static Logger _log = LoggerFactory.getLogger(HttpClientUtils.class);

    public static final String UTF_8 = "UTF-8";

    //    private static Logger log = Logger.getLogger(HttpClientUtils.class);
    private final static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);


    /**
     *@description: get请求方法
     *@params:
     *@author: lizhixin
     *@createDate: 17:39 2017/11/23
    */
    public static String get(String url, Map<String, Object> params, int timeoutMils) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        //CloseableHttpClient httpClient = HttpClients.createDefault();
        String parmstr = "?".concat(Joiner.on("&").withKeyValueSeparator("=").join(params));
        log.info("httpclient url={}", url.concat(parmstr));


        HttpGet get = new HttpGet(url + parmstr);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeoutMils)
                .setConnectTimeout(timeoutMils).setRedirectsEnabled(true)
                .setSocketTimeout(timeoutMils).build();
        get.setConfig(config);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                return result;
            }
            log.info("get resultCode={},get result : {}",responseCode  ,result);
        } catch (Exception e) {
            log.error("get is error {},   url = {}", e , url);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("get is error {},   url = {}", e , url);
            }
        }
        return "";
    }

    /**
     *@description: post请求（传参为Map,content-type为默认值）
     *@params:
     *@author: lizhixin
     *@createDate: 17:40 2017/11/23
    */
    public static String post(String url, Map<String, String> params, int timeoutMils) {
//        log.info("url=" + url + ",params=" + params);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = setHttpPostConfig(url, timeoutMils);

        CloseableHttpResponse response = null;
        try {
            List formParams = new ArrayList();
            for (String key : params.keySet()) {
                formParams.add(new BasicNameValuePair(key, params.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                return result;
            }
            log.error("post code={},post result :{}" ,responseCode, result);
        } catch (Exception e) {
            log.error("post is error {},   url = {}", e , url);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("post is error {},   url = {}", e , url);
            }
        }
        return "";
    }


    /**
     *@description: post请求方法（传参为json，content-type为application/json;charset=UTF-8）
     *@params:
     *@author: lizhixin
     *@createDate: 17:40 2017/11/23
    */
    public static String post(String url, String json, int timeout) {
        log.info("url=" + url + ",json=" + json);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = setHttpPostConfig(url, timeout);

        CloseableHttpResponse response = null;
        try {
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setEntity(new StringEntity(json, "UTF-8"));
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                return result;
            }
            log.info("post code={},post result :{}" ,responseCode, result);
        } catch (Exception e) {
            log.error("post is error {},   url = {}", e , url);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("post is error {},   url = {}", e , url);
            }
        }
        return "";
    }

    private static HttpPost setHttpPostConfig(String url, int timeout) {
        HttpPost post = new HttpPost(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).setRedirectsEnabled(true)
                .setSocketTimeout(timeout).build();
        post.setConfig(config);
        return post;
    }

    public static String get(String uri, String responseEncode) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            DefaultHttpClient httpClient = setDefaultHttpClientConfig();

            HttpGet httpGet = new HttpGet(uri);
            httpResponse = httpClient.execute(httpGet);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            String charset = responseEncode == null ? EntityUtils.getContentCharSet(httpResponse.getEntity()) : responseEncode;
            String httpEntity = EntityUtils.toString(httpResponse.getEntity(), charset);
            httpResponse.getEntity().consumeContent();
            if(responseCode == 200){
                return httpEntity;
            }
        }catch (Exception ex){
            log.error("get is error {},   url = {}", ex , uri);
        }finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
        return "";
    }


    private static DefaultHttpClient setDefaultHttpClientConfig() {
        DefaultHttpClient httpClient;HttpParams params = new BasicHttpParams();
        params.setParameter("http.connection.timeout", 5000);
        params.setParameter("http.socket.timeout", 5000);
        params.setParameter("http.protocol.element-charset", UTF_8);
        params.setParameter("http.protocol.content-charset", UTF_8);

        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
        httpClient = new DefaultHttpClient(manager, params);
        return httpClient;
    }

}
