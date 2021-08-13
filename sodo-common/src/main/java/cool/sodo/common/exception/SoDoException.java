package cool.sodo.common.exception;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cool.sodo.common.entity.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 平台通用异常
 *
 * @author TimeChaser
 * @date 2021/7/18 13:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SoDoException extends RuntimeException {

    private ResultEnum resultEnum;

    private String detail;

    @JsonIgnore
    private Object params;

    public SoDoException(String detail) {
        super(detail);
        this.detail = detail;
    }

    public SoDoException(ResultEnum resultEnum, String detail) {
        super(detail);
        this.resultEnum = resultEnum;
        this.detail = detail;
    }

    public SoDoException(ResultEnum resultEnum, String detail, Object params) {
        super(detail);
        this.resultEnum = resultEnum;
        this.detail = detail;
        this.params = JSON.toJSONString(params);
    }
}
