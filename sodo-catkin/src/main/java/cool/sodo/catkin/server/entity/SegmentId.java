package cool.sodo.catkin.server.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TimeChaser
 * @date 2021/9/12 9:53
 */
@Data
public class SegmentId implements Serializable {

    private Long maxId;
    private Long loadingId;
    private AtomicLong currentId;
    private int delta;
    private int remainder;
    private volatile boolean init;

    /**
     * 单例初始化
     * <p>
     * 如果 currentId 正好符合 remainder 和 delta 递增规律，则直接初始化成功
     * <p>
     * 否则，currentId 自增直至符合规律，然后回退一个 delta，节省一个系统占用的 ID
     *
     * @author TimeChaser
     * @date 2021/9/12 10:02
     */
    public void init() {

        if (init) {
            return;
        }
        synchronized (this) {
            if (init) {
                return;
            }
            long id = currentId.get();
            if (id % delta == remainder) {
                init = true;
                return;
            }
            for (int i = 0; i <= delta; i++) {
                id = currentId.incrementAndGet();
                if (id % delta == remainder) {
                    currentId.addAndGet(-delta);
                    init = true;
                    return;
                }
            }
        }
    }

    public Result nextId() {

        init();
        long id = currentId.addAndGet(delta);
        if (id > maxId) {
            return new Result(ResultCode.OVER, id);
        }
        if (id >= loadingId) {
            return new Result(ResultCode.LOADING, id);
        }
        return new Result(ResultCode.NORMAL, id);
    }

    public boolean useful() {
        return currentId.get() <= maxId;
    }
}
