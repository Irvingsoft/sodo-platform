package cool.sodo.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用接口返回实体
 *
 * @author TimeChaser
 * @date 2021/5/26 17:58
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;
    private String desc;
    private String detail;
    private T data;

    private Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDescription();
    }

    private Result(ResultEnum resultEnum, String detail) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDescription();
        this.detail = detail;
    }

    private Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.desc = resultEnum.getDescription();
        this.data = data;
    }

    private Result(Integer code, String desc, String detail) {
        this.code = code;
        this.desc = desc;
        this.detail = detail;
    }

    public static Result of(ResultEnum resultEnum) {
        return new Result<>(resultEnum);
    }

    public static Result of(ResultEnum resultEnum, String detail) {
        return new Result<>(resultEnum, detail);
    }

    public static Result of(Integer code, String desc, String detail) {
        return new Result<>(code, desc, detail);
    }

    public static <T> Result of(ResultEnum resultEnum, T data) {
        return new Result<>(resultEnum, data);
    }

    public static Result success() {
        return new Result<>(ResultEnum.SUCCESS);
    }

    public static <T> Result success(T data) {
        return new Result<>(ResultEnum.SUCCESS, data);
    }

    public static Result fail() {
        return new Result<>(ResultEnum.SERVER_ERROR);
    }

    public static Result fail(String detail) {
        return new Result<>(ResultEnum.SERVER_ERROR, detail);
    }

    public static Result unauthorized() {
        return new Result<>(ResultEnum.UNAUTHORIZED);
    }

    public static Result unauthorized(String detail) {
        return new Result<>(ResultEnum.UNAUTHORIZED, detail);
    }

    public static Result badRequest() {
        return new Result<>(ResultEnum.BAD_REQUEST);
    }

    public static Result badRequest(String detail) {
        return new Result<>(ResultEnum.BAD_REQUEST, detail);
    }

    public static Result<?> notFound() {
        return new Result<>(ResultEnum.NOT_FOUND);
    }
}
