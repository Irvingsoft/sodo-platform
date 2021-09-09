package cool.sodo.goods.exception;

import cool.sodo.common.base.entity.ResultEnum;
import lombok.Data;

/**
 * 商品服务异常类
 *
 * @author TimeChaser
 * @date 2021/5/31 20:58
 */
@Data
public class GoodsException extends RuntimeException {

    private ResultEnum resultEnum;
    private String detail;
    private String log;

    public GoodsException(String message) {
        super(message);
    }

    public GoodsException(ResultEnum resultEnum, String detail) {
        this.resultEnum = resultEnum;
        this.detail = detail;
    }

    public GoodsException(ResultEnum resultEnum, String detail, String log) {
        this.resultEnum = resultEnum;
        this.detail = detail;
        this.log = log;
    }
}
