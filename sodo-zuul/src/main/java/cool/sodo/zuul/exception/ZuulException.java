package cool.sodo.zuul.exception;

import cool.sodo.common.exception.SoDoException;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于处理在 ZuulFilter 中抛出的异常
 *
 * @author TimeChaser
 * @date 2021/7/18 13:59
 */
@Data
public class ZuulException implements Serializable {

    private final Integer code;
    private final String description;
    private final String detail;

    public ZuulException(SoDoException soDoException) {
        this.code = soDoException.getResultEnum().getCode();
        this.description = soDoException.getResultEnum().getDescription();
        this.detail = soDoException.getDetail();
    }
}
