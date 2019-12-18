package com.blue.json.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

/**
 * @ClassName
 * @Description TODO
 * @Date 2019/12/17 16:35
 **/
public class JsonTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", 0);
        map.put("errorMessage", "1111111");
        map.put("data", "dataaaaaaaaaa");
        map.put("dataTest", null);
        List<String> list = new ArrayList<>();
        map.put("listTest", list);
        System.out.println(JSON.toJSONString(map));

        //输出key时是否使用双引号,默认为true
        System.out.println(JSON.toJSONString(map, SerializerFeature.QuoteFieldNames));

        //使用单引号而不是双引号,默认为false
        System.out.println(JSON.toJSONString(map, SerializerFeature.UseSingleQuotes));

        //是否输出值为null的字段,默认为false
        System.out.println(JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));

        //?
        System.out.println(JSON.toJSONString(TestEnum.values(), SerializerFeature.WriteEnumUsingToString));

        //?
        System.out.println(JSON.toJSONString(TestEnum.values(), SerializerFeature.WriteEnumUsingName));

        //Date使用ISO8601格式输出，默认为false
        System.out.println(JSON.toJSONString(new Date(), SerializerFeature.UseISO8601DateFormat));
        System.out.println(JSON.toJSONString(new Date()));


    }
}