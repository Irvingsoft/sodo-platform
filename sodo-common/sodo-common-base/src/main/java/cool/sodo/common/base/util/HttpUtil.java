package cool.sodo.common.base.util;

import javax.servlet.ServletInputStream;
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
            String line = "";
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

    public static String getBodyMap(ServletInputStream in) {
        String param = "";
        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder bodyStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                bodyStrBuilder.append(inputStr);
            }
            return bodyStrBuilder.toString();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException ioException) {
                    System.out.println(ioException);
                }
            }

        }
        return null;
    }

    /**
     * 添加自定义的信息到请求体中
     *
     * @param newReqBodyStr 自定义请求体信息
     * @return java.lang.String
     */
    public static String appendCustomMsgToReqBody(String newReqBodyStr) {

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        String newReqBody = null;
        try {
            //通过字符串构造输入流;
            inputStream = stringToInputStream(newReqBodyStr);
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
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
        //返回字符串;
        newReqBody = sb.toString();
        return newReqBody;

    }

    public static InputStream stringToInputStream(String str) {

        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }
}
