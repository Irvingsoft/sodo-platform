package cool.sodo.common.service;

import cool.sodo.common.domain.User;

/**
 * 通用 用户服务层
 *
 * @author TimeChaser
 * @date 2021/8/23 22:12
 */
public interface CommonUserService {

    /**
     * 根据用户 ID 查询用户基本信息
     * <p>
     * 适用于查询展示数据时数据创建者简介
     *
     * @param userId 用户 ID
     * @return cool.sodo.common.domain.User
     */
    User getBase(String userId);

    /**
     * 获取用户的身份认证信息
     *
     * @param identity 身份认证关键字，包括 userId、username、openId、phone、email
     * @return cool.sodo.common.domain.User
     */
    User getIdentity(String identity);

    /**
     * 获取用户的身份认证信息，包括其权限列表
     *
     * @param identity 身份认证关键字，包括 userId、username、openId、phone、email
     * @return cool.sodo.common.domain.User
     */
    User getIdentityDetail(String identity);

    /**
     * 检查用户名是否是合法的身份认证关键字
     *
     * @param username 用户名
     */
    void checkUsername(String username);

    /**
     * 检查手机号是否是合法的身份认证关键字
     *
     * @param phone 手机号
     */
    void checkPhone(String phone);

    /**
     * 检查邮箱是否是合法的身份认证关键字
     *
     * @param email 邮箱
     */
    void checkEmail(String email);
}
