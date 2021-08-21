package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.RoleToMenu;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.housekeeper.mapper.RoleToMenuMapper;
import cool.sodo.housekeeper.service.RoleToMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleToMenuServiceImpl implements RoleToMenuService {

    @Resource
    private RoleToMenuMapper roleToMenuMapper;

    @Override
    public void insert(List<RoleToMenu> roleToMenuList) {

        for (RoleToMenu roleToMenu : roleToMenuList) {
            if (roleToMenuMapper.insert(roleToMenu) <= 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "新增 RoleToMenu 失败！");
            }
        }
    }

    @Override
    public void deleteByRole(List<String> roleIdList) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.in(RoleToMenu::getRoleId, roleIdList);
        if (roleToMenuMapper.delete(roleToMenuLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 RoleToMenu 失败！");
        }
    }
}
