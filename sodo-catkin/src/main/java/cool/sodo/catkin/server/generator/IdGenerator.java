package cool.sodo.catkin.server.generator;

import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/12 10:31
 */
public interface IdGenerator {

    /**
     * 获取下一个 ID
     *
     * @return id
     */
    Long nextId();

    /**
     * 获取下一个 ID 列表
     *
     * @param batchSize 列表长度
     * @return ID 列表
     */
    List<Long> nextId(Integer batchSize);
}
