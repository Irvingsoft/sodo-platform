package cool.sodo.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
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

    @TableId(type = IdType.ASSIGN_ID)
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

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;

    @TableLogic
    private Boolean deleted;

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

    public void init(String createBy) {

        this.createBy = createBy;
        if (!StringUtil.isEmpty(this.menuId) &&
                this.menuId.equals(this.parentId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "父菜单不能为本身！");
        }
    }

    public void update(Menu menu, String updateBy) {

        this.parentId = menu.getParentId();
        this.icon = menu.getIcon();
        if (!StringUtil.isEmpty(menu.getCode())) {
            this.code = menu.getCode();
        }
        if (!StringUtil.isEmpty(menu.getName())) {
            this.name = menu.getName();
        }
        if (!StringUtil.isEmpty(menu.getPath())) {
            this.path = menu.getPath();
        }
        if (!StringUtil.isEmpty(menu.getDescription())) {
            this.description = menu.getDescription();
        }
        if (!StringUtil.isEmpty(menu.getSort())) {
            this.sort = menu.getSort();
        }
        if (!StringUtil.isEmpty(menu.getMenuType())) {
            this.menuType = menu.getMenuType();
        }
        if (!StringUtil.isEmpty(menu.getButtonType())) {
            this.buttonType = menu.getButtonType();
        }
        if (!StringUtil.isEmpty(menu.getNewWindow())) {
            this.newWindow = menu.getNewWindow();
        }
        this.updateBy = updateBy;
        if (!StringUtil.isEmpty(this.menuId) &&
                this.menuId.equals(this.parentId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "父菜单不能为本身！");
        }
    }

    public void delete(String updateBy) {
        this.updateBy = updateBy;
    }
}
