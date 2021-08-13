package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 用户菜单
 *
 * @author TimeChaser
 * @date 2021/7/21 13:45
 */
@Data
@TableName(value = "menu")
public class Menu implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String menuId;

    private String parentId;

    private String code;

    private String clientId;

    private String name;

    private String icon;

    private String path;

    private String description;

    private Integer sort;

    /**
     * 菜单类型：1-菜单；2-按钮
     *
     * @date 2021/7/26 13:02
     */
    private Integer menuType;

    /**
     * 按钮类型：1-工具栏；2-操作栏；3-工具操作栏
     *
     * @date 2021/7/26 13:04
     */
    private Integer buttonType;

    /**
     * 是否开启新窗口
     *
     * @date 2021/7/26 13:04
     */
    private Boolean newWindow;

    /**
     * 是否启用
     *
     * @date 2021/7/26 13:04
     */
    private Boolean inUse;

    private Date createAt;

    private Date updateAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return menuId.equals(menu.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
