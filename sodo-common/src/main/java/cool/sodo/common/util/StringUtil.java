package cool.sodo.common.util;

import org.springframework.util.StringUtils;

import java.util.List;

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
}