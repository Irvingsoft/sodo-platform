package cool.sodo.common.util;

import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Web 工具类
 *
 * @author TimeChaser
 * @date 2021/6/12 17:55
 */
public class WebUtil {

    public static final String UN_KNOWN = "unknown";
    public static final String ERROR_REQUEST = "获取请求实体失败！";
    public static final String ERROR_HEADER = "获取请求头参数失败！";

    public static String getIp(HttpServletRequest request) {

        Assert.notNull(request, "HttpServletRequest is null");
        String ip = request.getHeader("X-Requested-For");
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.isEmpty(ip) ? null : ip.split(",")[0];
    }

    public static HttpServletRequest getRequest() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (StringUtils.isEmpty(requestAttributes)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_REQUEST);
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (StringUtils.isEmpty(request)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_REQUEST);
        }
        return request;
    }

    public static HttpServletRequest getRequestNullable() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static HttpServletRequest getContentCachingRequest() {
        HttpServletRequest request = getRequest();
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        return request;
    }

    public static HttpServletRequest transformToContentCachingRequest(HttpServletRequest request) {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        return request;
    }

    public static HttpServletResponse getResponse() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (StringUtils.isEmpty(requestAttributes)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_REQUEST);
        }
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
        if (StringUtils.isEmpty(response)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_REQUEST);
        }
        return response;
    }

    public static HttpServletResponse getResponseNullable() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getResponse();
    }

    public static HttpServletResponse getContentCachingResponse() {
        HttpServletResponse response = getResponse();
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        return response;
    }

    public static HttpServletResponse transformToContentCachingResponse(HttpServletResponse response) {
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        return response;
    }

    public static String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    public static String getHeader(HttpServletRequest request, String key) {
        String header = request.getHeader(key);
        if (StringUtils.isEmpty(header)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_HEADER, key);
        }
        return header;
    }

    public static String getHeaderNullable(HttpServletRequest request, String key) {
        return request.getHeader(key);
    }

    public static String getHeaderNullable(WebRequest request, String key) {
        return request.getHeader(key);
    }

    public static void repairResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }

    public static String getRequestUrl(HttpServletRequest request, String servicePath) {

        String requestUrl = String.valueOf(request.getRequestURL());
        if (servicePath.equals(Constants.GATEWAY_PATH)) {
            return requestUrl;
        }
        if (!StringUtils.substringMatch(requestUrl, 0, servicePath)) {

            StringBuilder stringBuilder = new StringBuilder();
            String requestUri = request.getRequestURI();

            stringBuilder.append(requestUrl.split(requestUri)[0])
                    .append(servicePath)
                    .append(requestUri);
            return stringBuilder.toString();
        }
        return requestUrl;
    }

    public static String getAccessToken(HttpServletRequest request) {
        String header = getHeaderNullable(request, Constants.AUTHORIZATION);
        if (!StringUtils.isEmpty(header)) {
            return header.split(StringPool.BLANK)[1];
        }
        return null;
    }

    public static String getAccessToken(WebRequest request) {
        String header = getHeaderNullable(request, Constants.AUTHORIZATION);
        if (!StringUtils.isEmpty(header)) {
            return header.split(StringPool.BLANK)[1];
        }
        return null;
    }
}
