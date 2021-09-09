package cool.sodo.common.base.service;

import java.util.List;

public interface CommonRoleToMenuService {

    List<String> listMenuIdByRole(List<String> roleIdList);

    List<String> listMenuIdByRole(String roleId);
}
