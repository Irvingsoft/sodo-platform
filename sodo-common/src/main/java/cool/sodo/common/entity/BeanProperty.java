package cool.sodo.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean属性
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public class BeanProperty {

    private final String name;

    private final Class<?> type;
}
