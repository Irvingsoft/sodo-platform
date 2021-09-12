package cool.sodo.common.base.util;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 字符串工具类
 *
 * @author TimeChaser
 * @date 2021/7/26 16:56
 */
public class StringUtil extends StringUtils {

    public static <T> boolean isEmpty(List<T> list) {
        return StringUtils.isEmpty(list) || list.isEmpty();
    }

    public static boolean isEmpty(String[] strings) {
        return StringUtils.isEmpty(strings) || strings.length == 0;
    }

    public static <T> boolean isEmpty(Set<T> set) {
        return StringUtils.isEmpty(set) || set.size() == 0;
    }
}