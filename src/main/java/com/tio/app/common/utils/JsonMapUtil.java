package com.tio.app.common.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json与Map操作的工具类
 */
public class JsonMapUtil {
    /**
     * 将json字符串转换成Map集合
     *
     * @param json 可以是String,Map,javaBean等等
     *             例子:
     *             String json = "{filter:{objName:'acenout',mtdNae:'getPage'},field={acenout.code:'xxx',jsono:{name:'阿布',age:23},txdate:'2012-11-13',score:98.0},arrList:['这里是ArrayList','测试2','xx']}";
     * @return resource 类型转化后的值
     * @author zhl
     * @version 1.0
     * <p>
     * 增加注释
     * <p>
     * 增加了方法描述和返回值描述
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String json) {
        Map<String, Object> resource = parseToJavaObject(json, HashMap.class);
        Set<Entry<String, Object>> entrySet = resource.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            if (entry.getValue() instanceof JSONObject) {
                entry.setValue(JSON.toJavaObject((JSONObject) entry.getValue(), Map.class));
            } else if (entry.getValue() instanceof JSONArray) {
                entry.setValue(JSON.toJavaObject((JSONArray) entry.getValue(), List.class));
            }
        }
        return resource;
    }

    /**
     * 将参数一json字符串转为参数二所示的类型，参数二可以是类似List<Page>的泛型类型
     *
     * @param json  可以是String,Map,javaBean等等
     * @param clazz 可以是类似List<Page>的泛型类型
     * @return JSON.parseObject(json, clazz) 类型转化后的值
     * @version 1.0
     * <p>
     * 增加注释
     * <p>
     * 增加了参数json、clazz描述和返回值描述
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    public static <T> T json2Object(String json, Type clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将json字符串转为JSONObject
     *
     * @param json 可以是String,Map,javaBean等等
     * @return JSON.parseObject(json) 类型转化后的值
     * @version 1.0
     * <p>
     * 增加注释
     * <p>
     * 增加了返回值描述
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    public static JSONObject json2Object(String json) {
        return JSON.parseObject(json);
    }

    /**
     * 提供object向 {@link JSONObject}的转换。
     * @param object 可以是Map、javabean及json字符串
     * @param jsonConfig 转换规则
     * @return {@link JSONObject}实例
     * @version 1.0
     *//*
	public static JSONObject object2JsonObject(Object object,JsonConfig jsonConfig){
		return jsonConfig==null?JSONObject.fromObject(object):JSONObject.fromObject(object, jsonConfig);
	}*/

    /**
     * 将json字符串转为class对象
     *
     * @param json  可以是String,Map,javaBean等等
     * @param clazz 要转换的目标对象
     * @return JSON.parseObject(json, clazz) 类型转化后的值
     * @author 未知
     * @version 1.0
     * @date 未知
     * <p>
     * 增加注释
     * <p>
     * 本来没有注释，该注释为今天新增的
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    public static <T> T parseToJavaObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将map对象转换成json字符串
     *
     * @param map map对象
     * @return JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat) 类型转化后的值
     * @author 未知
     * @version 1.0
     * @date 未知
     * <p>
     * 增加注释
     * <p>
     * 本来没有注释，该注释为今天新增的
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    public static String map2Json(Map<String, Object> map) {
//		JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss");
        return JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 将参数object转换为json字符串
     *
     * @param object 可以是String,javaBean等等
     * @return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat) 类型转化后的值
     * @version 1.0
     * <p>
     * 增加注释
     * <p>
     * 增加了参数object的描述和返回值描述
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    public static String object2Json(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 将参数object转换为json字符串
     *
     * @param object 可以是String,javaBean等等
     * @param filter 不知道
     * @return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat) 类型转化后的值
     * @version 1.0
     * <p>
     * 增加注释
     * <p>
     * 增加了参数object、filter的描述和返回值描述
     * @modify 苏永民
     * @moddate 2015-9-1
     */
    public static String object2Json(Object object, SerializeFilter filter) {
        return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
    }
}
