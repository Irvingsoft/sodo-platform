package cool.sodo.common.base.util;

import javax.servlet.ServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 工具类，获取请求体
 *
 * @author TimeChaser
 * @date 2021/6/11 10:17
 */
public class HttpUtil {

    public static String getBodyString(ServletRequest request) {

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            //读取流并将流写出去,避免数据流中断;
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static InputStream stringToInputStream(String str) {

        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }
}
