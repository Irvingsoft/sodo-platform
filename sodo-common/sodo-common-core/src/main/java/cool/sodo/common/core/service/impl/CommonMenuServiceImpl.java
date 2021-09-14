package cool.sodo.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.domain.Menu;
import cool.sodo.common.core.mapper.CommonMenuMapper;
import cool.sodo.common.core.service.CommonMenuService;
import cool.sodo.common.core.service.CommonRoleToMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonMenuServiceImpl implements CommonMenuService {

    @Resource
    private CommonMenuMapper commonMenuMapper;
    @Resource
    private CommonRoleToMenuService roleToMenuService;

    @Override
    public List<String> listCode(List<String> roleIdList) {

        List<String> menuIdList = roleToMenuService.listMenuIdByRole(roleIdList);
        if (StringUtil.isEmpty(menuIdList)) {
            return null;
        }
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = Wrappers.lambdaQuery();
        menuLambdaQueryWrapper.select(Menu::getCode)
                .in(Menu::getMenuId, menuIdList);
        return commonMenuMapper.selectList(menuLambdaQueryWrapper)
                .stream()
                .map(Menu::getCode)
                .collect(Collectors.toList());
    }
}
