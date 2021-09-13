package cool.sodo.catkin.server.entity;

/**
 * @author TimeChaser
 * @date 2021/9/12 10:21
 */
public class ResultCode {

    /**
     * 正常可用
     */
    public static final int NORMAL = 1;

    /**
     * 需要去加载nextId
     */
    public static final int LOADING = 2;

    /**
     * 超过maxId 不可用
     */
    public static final int OVER = 3;
}
