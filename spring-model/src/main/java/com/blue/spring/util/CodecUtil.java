package com.blue.spring.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

public class CodecUtil
{
  private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

  /**
   *@description: encodeURL编码
   *@params:
   *@author: lizhixin
   *@createDate: 17:34 2017/11/23
  */
  public static String encodeURL(String str)
  {
    String target;
    try
    {
      target = URLEncoder.encode(str, "UTF-8");
    }
    catch (Exception e)
    {
      logger.error("编码出错!", e);
      throw new RuntimeException(e);
    }
    return target;
  }

  /**
   *@description: decodeURL解码
   *@params:
   *@author: lizhixin
   *@createDate: 17:34 2017/11/23
  */
  public static String decodeURL(String str)
  {
    String target;
    try
    {
      target = URLDecoder.decode(str, "UTF-8");
    }
    catch (Exception e)
    {
      logger.error("编码出错!", e);
      throw new RuntimeException(e);
    }
    return target;
  }
  
  /**
   *@description: encodeBASE64编码（String）
   *@params: 
   *@author: lizhixin
   *@createDate: 17:34 2017/11/23 
  */
  public static String encodeBASE64(String str)
  {
    String target;
    try
    {
      target = Base64.encodeBase64URLSafeString(str.getBytes("UTF-8"));
    }
    catch (UnsupportedEncodingException e)
    {
      logger.error("编码出错!", e);
      throw new RuntimeException(e);
    }
    return target;
  }

  /**
   *@description: encodeBASE64编码（byte[]）
   *@params:
   *@author: lizhixin
   *@createDate: 17:36 2017/11/23
  */
  public static String encodeBASE64(byte[] data)
  {
    String target = Base64.encodeBase64String(data);
    return target;
  }

  /**
   *@description: decodeBASE64解码（String）
   *@params:
   *@author: lizhixin
   *@createDate: 17:36 2017/11/23
  */
  public static String decodeBASE64(String str)
  {
    String target;
    try
    {
      target = new String(Base64.decodeBase64(str), "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      logger.error("编码出错!", e);
      throw new RuntimeException(e);
    }
    return target;
  }
  
  public static String encryptMD5(String str)
  {
    return DigestUtils.md5Hex(str);
  }
  
  public static String encryptSHA(String str)
  {
    return DigestUtils.sha1Hex(str);
  }
  
  public static String createRandom(int count)
  {
    return RandomStringUtils.randomNumeric(count);
  }
  
  public static String createUUID()
  {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
