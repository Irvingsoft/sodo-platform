package cool.sodo.catkin.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TimeChaser
 * @date 2021/9/12 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private int code;
    private long id;
}
