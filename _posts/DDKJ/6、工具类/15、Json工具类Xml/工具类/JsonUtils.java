package com.hlj.ddkj.Jsonp;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils - JSON
 */
public final class JsonUtils {

    /**
     * ObjectMapper
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // jackson 1.9 and before
        //objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // or jackson 2.0
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param value 对象
     * @return JSON字符串
     */
    public static String toJson(Object value) {
        Assert.notNull(value, "json 不允许为空");
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json      JSON字符串
     * @param valueType 类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json, "json 不允许为空");
        Assert.notNull(valueType, "valueType 不允许为空");
        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json          JSON字符串
     * @param typeReference 类型
     * @return 对象
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        Assert.hasText(json, "json 不允许为空");
        Assert.notNull(typeReference, "typeReference 不允许为空");
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json     JSON字符串
     * @param javaType 类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json, "json 不允许为空");
        Assert.notNull(javaType, "javaType 不允许为空");
        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


 /**
     * 将JSON字符串(数组或者Json) 转换为对象 用到下面这种情况的时候往往是因为使用了lombok 注解二引起
     * 报错 例如
     15:54:00.944 [main] INFO net.sf.json.JSONObject - Property 'code' of class com.duodian.youhui.admin.utils.json.data.Person has no write method. SKIPPED.
     *
     * @param json     JSON字符串
     * @param javaType 类型
     * @return 对象
     */
    public static Object toObjectOrList(String json,Class javaType) {
        Assert.hasText(json, "json 不允许为空");
        Assert.notNull(javaType, "javaType 不允许为空");
        try {
            Object jsonT = new JSONTokener(json).nextValue();
            if(jsonT instanceof JSONObject){
                return OBJECT_MAPPER.readValue(json,javaType);
            }else if (jsonT instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) jsonT;
                List objects = new ArrayList<>();
                for(int i = 0 ;i <jsonArray.size() ;i++){
                    objects.add(new ObjectMapper().readValue(jsonArray.get(i).toString(),javaType));
                }
                return objects ;
            }
            return  OBJECT_MAPPER.readValue(json,javaType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 将JSON字符串转换为树
     *
     * @param json JSON字符串
     * @return 树
     */
    public static JsonNode toTree(String json) {
        Assert.hasText(json, "json 不允许为空");
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换为JSON流
     *
     * @param writer Writer
     * @param value  对象
     */
    public static void writeValue(Writer writer, Object value) {
        Assert.notNull(writer, "writer 不允许为空");
        Assert.notNull(value, "value 不允许为空");
        try {
            OBJECT_MAPPER.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构造类型
     *
     * @param type 类型
     * @return 类型
     */
    public static JavaType constructType(Type type) {
        Assert.notNull(type, "type 不允许为空");
        return TypeFactory.defaultInstance().constructType(type);
    }

    /**
     * 构造类型
     *
     * @param typeReference 类型
     * @return 类型
     */
    public static JavaType constructType(TypeReference<?> typeReference) {
        Assert.notNull(typeReference, "typeReference 不允许为空");
        return TypeFactory.defaultInstance().constructType(typeReference);
    }

}