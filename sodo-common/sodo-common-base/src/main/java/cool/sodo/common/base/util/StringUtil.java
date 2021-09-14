package cool.sodo.common.base.util;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 字符串工具类
 *
 * @author TimeChaser
 * @date 2021/7/26 16:56
 */
public class StringUtil extends StringUtils {

    public static boolean isEmpty(Object object) {
        return object == null;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isEmpty(String[] strings) {
        return strings == null || strings.length == 0;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}