package cool.sodo.common.core.service;

import cool.sodo.common.core.domain.User;

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
     * @return cool.sodo.common.starter.domain.User
     */
    User getBase(String userId);

    /**
     * 获取用户的身份认证信息
     *
     * @param identity 身份认证关键字，包括 userId、username、openId、phone、email
     * @param clientId 客户端 ID
     * @return cool.sodo.common.starter.domain.User
     */
    User getIdentity(String identity, String clientId);

    /**
     * 检查用户状态（status）
     *
     * @param user getIdentity() 返回的实体
     */
    void checkUserStatus(User user);

    /**
     * 检查用户名是否是合法的身份认证关键字
     * <p>
     * 1. 用户 ID 为空表示新增，则验证该客户端是否存在该身份认证关键字
     * <p>
     * 2. 用户 ID 为空表示更新，如果身份验证关键字没变则无需验证
     *
     * @param userId   用户 ID
     * @param username 用户名
     * @param clientId 客户端 ID
     */
    void checkUsername(String userId, String username, String clientId);

    /**
     * 检查手机号是否是合法的身份认证关键字
     *
     * @param userId   用户 ID
     * @param phone    手机号
     * @param clientId 客户端 ID
     */
    void checkPhone(String userId, String phone, String clientId);

    /**
     * 检查邮箱是否是合法的身份认证关键字
     *
     * @param userId   用户 ID
     * @param email    邮箱
     * @param clientId 客户端 ID
     */
    void checkEmail(String userId, String email, String clientId);
}
