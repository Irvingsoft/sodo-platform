package cool.sodo.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分布式消息实体
 *
 * @author TimeChaser
 * @date 2021/6/16 17:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Notification implements Serializable {

    /**
     * 消息类型
     */
    private String eventType;
    /**
     * 消息来源
     */
    private String origin;
    /**
     * 数据内容
     */
    private Object data;
}
