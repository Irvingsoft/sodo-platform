package cool.sodo.zuul.exception;

import cool.sodo.common.entity.ResultEnum;
import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {

    private final ResultEnum resultEnum;

    public AuthorizationException(ResultEnum resultEnum) {
        super(resultEnum.getDescription());
        this.resultEnum = resultEnum;
    }

}
