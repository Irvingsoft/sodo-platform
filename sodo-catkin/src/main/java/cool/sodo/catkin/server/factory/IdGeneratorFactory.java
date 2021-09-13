package cool.sodo.catkin.server.factory;

import cool.sodo.catkin.server.generator.IdGenerator;

/**
 * @author TimeChaser
 * @date 2021/9/12 10:28
 */
public interface IdGeneratorFactory {

    /**
     * 根据 bizType 创建 ID 生成器
     *
     * @param bizType bizType
     * @return ID 生成器
     */
    IdGenerator getIdGenerator(String bizType);
}
