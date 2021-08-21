package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.Role;
import cool.sodo.common.domain.RoleToMenu;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonRoleMapper;
import cool.sodo.common.util.BeanUtil;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.node.ForestNodeMerger;
import cool.sodo.housekeeper.entity.RoleDTO;
import cool.sodo.housekeeper.entity.RoleVO;
import cool.sodo.housekeeper.service.RoleService;
import cool.sodo.housekeeper.service.RoleToMenuService;
import cool.sodo.housekeeper.service.UserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    public static final int SELECT_TREE = 0;
    public static final int SELECT_LIST = 1;
    public static final int SELECT_OTHER = 2;

    @Resource
    private CommonRoleMapper roleMapper;
    @Resource
    private RoleToMenuService roleToMenuService;
    @Resource
    private UserToRoleService userToRoleService;

    private LambdaQueryWrapper<Role> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_TREE:
                roleLambdaQueryWrapper.select(Role::getRoleId, Role::getParentId, Role::getName, Role::getSort);
                break;
            case SELECT_LIST:
                roleLambdaQueryWrapper.select(Role::getRoleId, Role::getParentId, Role::getName, Role::getCode,
                        Role::getDescription, Role::getSort, Role::getCreateAt, Role::getUpdateAt);
                break;
            default:
                break;
        }
        roleLambdaQueryWrapper.orderByAsc(Role::getSort);
        return roleLambdaQueryWrapper;
    }

    private List<RoleVO> toRoleVO(List<Role> roleList) {
        return roleList.stream().map(x -> BeanUtil.copy(x, RoleVO.class)).collect(Collectors.toList());
    }

    @Override
    public void insertRole(Role role, String userId) {

        if (StringUtil.isEmpty(role.getClientId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "ClientId 不能为空！");
        }
        if (!StringUtil.isEmpty(role.getRoleId()) &&
                !StringUtil.isEmpty(getRoleNullable(role.getRoleId()))) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "已存在 RoleId 为 " + role.getRoleId() + " 的记录！");
        }
        role.init(userId);
        if (roleMapper.insert(role) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "插入 Role 记录失败！");
        }
    }

    @Override
    public void deleteRole(String roleId, String userId) {

        Role role = getRole(roleId);
        role.delete(userId);
        updateRole(role);
        if (roleMapper.deleteById(roleId) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 Role 记录失败！");
        }
        userToRoleService.deleteByRole(roleId);
        roleToMenuService.deleteByRole(roleId);
    }

    @Override
    public void deleteRole(List<String> roleIdList, String userId) {

        if (StringUtil.isEmpty(roleIdList)) {
            return;
        }
        for (String roleId : roleIdList) {
            Role role = getRole(roleId);
            role.delete(userId);
            updateRole(role);
        }
        if (roleMapper.deleteBatchIds(roleIdList) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 Role 记录失败！");
        }
        userToRoleService.deleteByRole(roleIdList);
        roleToMenuService.deleteByRole(roleIdList);
    }

    @Override
    public void updateRole(Role role, String userId) {

        if (StringUtil.isEmpty(role.getRoleId())) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "RoleId 不能为空！");
        }
        Role roleOld = getRole(role.getRoleId());
        roleOld.update(role, userId);
        if (roleMapper.updateById(roleOld) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "更新 Role 记录失败！");
        }
    }

    @Override
    public void updateRole(Role role) {
        if (roleMapper.updateById(role) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "更新 Role 记录失败！");
        }
    }

    @Override
    public Role getRole(String roleId) {

        Role role = roleMapper.selectById(roleId);
        if (StringUtil.isEmpty(role)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "不存在 RoleId 为 " + roleId + " 的记录！");
        }
        return role;
    }

    @Override
    public Role getRoleNullable(String roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    public List<RoleVO> treeRoleByClient(String clientId) {

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_TREE);
        roleLambdaQueryWrapper.eq(Role::getClientId, clientId);
        List<Role> roleList = roleMapper.selectList(roleLambdaQueryWrapper);
        return ForestNodeMerger.merge(toRoleVO(roleList));
    }

    @Override
    public List<RoleVO> treeRoleByUser(String userId) {

        List<String> roleIdList = userToRoleService.listUserToRoleRoleId(userId);
        if (StringUtil.isEmpty(roleIdList)) {
            return null;
        }
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_TREE);
        roleLambdaQueryWrapper.in(Role::getRoleId, roleIdList);
        List<Role> roleList = roleMapper.selectList(roleLambdaQueryWrapper);
        return ForestNodeMerger.merge(toRoleVO(roleList));
    }

    @Override
    public List<String> listRole(String userId) {

        List<String> roleIdList = userToRoleService.listUserToRoleRoleId(userId);
        if (StringUtil.isEmpty(roleIdList)) {
            return null;
        }
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_OTHER);
        roleLambdaQueryWrapper.in(Role::getRoleId, roleIdList)
                .select(Role::getRoleId);
        return roleMapper.selectList(roleLambdaQueryWrapper)
                .stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleVO> listRole(RoleDTO roleDTO) {

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_LIST);
        roleLambdaQueryWrapper.eq(Role::getClientId, roleDTO.getClientId());
        if (!StringUtil.isEmpty(roleDTO.getContent())) {
            roleLambdaQueryWrapper.and(wrapper -> wrapper.like(Role::getName, roleDTO.getContent())
                    .or()
                    .like(Role::getDescription, roleDTO.getContent()));
        }
        List<Role> roleList = roleMapper.selectList(roleLambdaQueryWrapper);
        return ForestNodeMerger.merge(toRoleVO(roleList));
    }

    @Override
    public void grant(List<String> roleIdList, List<String> menuIdList) {

        if (StringUtil.isEmpty(roleIdList)) {
            return;
        }
        roleToMenuService.deleteByRole(roleIdList);
        ArrayList<RoleToMenu> roleToMenuList = new ArrayList<>();
        for (String roleId : roleIdList) {
            for (String menuId : menuIdList) {
                roleToMenuList.add(new RoleToMenu(roleId, menuId));
            }
        }
        roleToMenuService.insert(roleToMenuList);
    }
}
