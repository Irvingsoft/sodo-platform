package cool.sodo.catkin.base.factory;

import cool.sodo.catkin.base.generator.IdGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TimeChaser
 * @date 2021/9/12 10:36
 */
public abstract class AbstractIdGeneratorFactory implements IdGeneratorFactory {

    private static final ConcurrentHashMap<String, IdGenerator> GENERATORS = new ConcurrentHashMap<>();

    @Override
    public IdGenerator getIdGenerator(String bizType) {

        if (GENERATORS.containsKey(bizType)) {
            return GENERATORS.get(bizType);
        }
        synchronized (this) {
            if (GENERATORS.containsKey(bizType)) {
                return GENERATORS.get(bizType);
            }
            IdGenerator idGenerator = createIdGenerator(bizType);
            GENERATORS.put(bizType, idGenerator);
            return idGenerator;
        }
    }

    protected abstract IdGenerator createIdGenerator(String bizType);
}
