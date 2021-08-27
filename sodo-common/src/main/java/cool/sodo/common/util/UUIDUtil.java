package cool.sodo.common.util;

import java.util.UUID;

/**
 * 获取 UUID
 *
 * @author TimeChaser
 * @date 2021/7/14 14:11
 */
public class UUIDUtil {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
