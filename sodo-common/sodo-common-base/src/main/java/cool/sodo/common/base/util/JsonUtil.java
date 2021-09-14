package cool.sodo.common.base.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import cool.sodo.common.base.entity.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author TimeChaser
 * @date 2021/9/14 16:11
 */
public class JsonUtil {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 允许不带引号的字段名称
        MAPPER.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
        // 允许单引号
        MAPPER.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        // allow int startWith 0
        MAPPER.configure(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature(), true);
        // 允许字符串存在转义字符：\r \n \t
        MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 排除空值字段
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 使用驼峰式
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        // 使用bean名称
        MAPPER.enable(MapperFeature.USE_STD_BEAN_NAMING);
        // 所有日期格式都统一为固定格式
        MAPPER.setDateFormat(new SimpleDateFormat(Constants.DATETIME_FORMAT));
        MAPPER.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE_GMT8));
    }

    /**
     * 使用默认的转换配置将对象转换成 JSON
     *
     * @param object 对象实体
     * @return java.lang.String
     */
    public static String toJsonString(Object object) {
        return toJsonString(object, false);
    }

    /**
     * 可选择是否使用自定义配置将对象转换成 JSON
     *
     * @param object 对象实体
     * @param format 是否使用自定义配置
     * @return java.lang.String
     */
    public static String toJsonString(Object object, boolean format) {

        try {
            if (object == null) {
                return "";
            }
            if (object instanceof Number) {
                return object.toString();
            }
            if (object instanceof String) {
                return (String) object;
            }
            if (format) {
                MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            }
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 转换成指定对象
     *
     * @param json   JSON 字符串
     * @param target 目标类
     * @return T
     */
    public static <T> T toObject(String json, Class<T> target) {

        if (StringUtil.isEmpty(json) || target == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, target);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为指定对象，并增加泛型转义
     * 例如：List<Integer> test = toObject(jsonStr, List.class, Integer.class);
     *
     * @param json             json字符串
     * @param parametrized     目标对象
     * @param parameterClasses 泛型对象
     * @return T
     */
    public static <T> T toObject(String json, Class<?> parametrized, Class<?>... parameterClasses) {

        if (StringUtil.isEmpty(json) || parametrized == null) {
            return null;
        }
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 字符串转换为指定对象
     *
     * @param json          json字符串
     * @param typeReference 目标对象类型
     * @return T
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        if (StringUtil.isEmpty(json) || typeReference == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为JsonNode对象
     *
     * @param json json字符串
     */
    public static JsonNode parse(String json) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转换为map对象
     *
     * @param o 要转换的对象
     */
    public static <K, V> Map<K, V> toMap(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return toObject((String) o, Map.class);
        }
        return MAPPER.convertValue(o, Map.class);
    }

    /**
     * json字符串转换为list对象
     *
     * @param json json字符串
     */
    public static <T> List<T> toList(String json) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为list对象，并指定元素类型
     *
     * @param json json字符串
     * @param cls  list的元素类型
     */
    public static <T> List<T> toList(String json, Class<T> cls) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, cls);
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
