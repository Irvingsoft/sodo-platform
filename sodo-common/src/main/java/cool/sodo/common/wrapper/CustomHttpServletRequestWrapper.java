package cool.sodo.common.wrapper;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

/**
 * 删除时清除 google guava 依赖包
 *
 * @author TimeChaser
 * @date 2021/6/11 10:08
 */
@Slf4j
@Deprecated
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream is = request.getInputStream();
        new HttpServletRequestWrapper(request);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int read;
        while ((read = is.read(buff)) > 0) {
            byteArrayOutputStream.write(buff, 0, read);
        }
        body = byteArrayOutputStream.toByteArray();
    }

    public static String getBodyMap(ServletInputStream in) {
        String param = "";
        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder bodyStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                bodyStrBuilder.append(inputStr);
            }
            return bodyStrBuilder.toString();
        } catch (Exception e) {
            log.error("获取请求体参数失败：", e);
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    log.error("关闭流失败:", e);
                }
            }

        }
        return null;
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

        };
    }

    public Map<String, String> getAllHeaders() {
        Enumeration<String> headerNames = this.getHeaderNames();
        Map<String, String> headerMaps = Maps.newHashMap();
        while (headerNames.hasMoreElements()) {
            String names = headerNames.nextElement();
            headerMaps.put(names, this.getHeader(names));
        }
        return headerMaps;
    }
}