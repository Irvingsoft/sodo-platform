package cool.sodo.housekeeper.service.impl;

import cool.sodo.common.mapper.CommonUserMapper;
import cool.sodo.housekeeper.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private CommonUserMapper userMapper;
}
