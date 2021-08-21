package cool.sodo.common.service;

import java.util.List;

public interface CommonRoleToMenuService {

    List<String> listRoleToMenuMenuIdByRole(List<String> roleIdList);

    List<String> listRoleToMenuMenuIdByRole(String roleId);
}
