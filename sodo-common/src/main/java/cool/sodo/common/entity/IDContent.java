package cool.sodo.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Rsa 公钥及
 *
 * @author TimeChaser
 * @date 2021/7/14 14:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDContent implements Serializable {

    private String id;
    private String content;
}
