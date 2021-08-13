package cool.sodo.common.exception;

/**
 * 异步任务异常类
 *
 * @author TimeChaser
 * @date 2021/6/18 10:55
 */
public class AsyncException extends RuntimeException {

    public AsyncException(String message) {
        super(message);
    }
}
