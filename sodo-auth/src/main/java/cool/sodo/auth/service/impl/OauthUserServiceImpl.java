package cool.sodo.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.auth.mapper.OauthUserMapper;
import cool.sodo.auth.service.OauthUserService;
import cool.sodo.common.starter.domain.OauthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class OauthUserServiceImpl implements OauthUserService {

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_INFO = 1;

    @Resource
    private OauthUserMapper oauthUserMapper;

    @Override
    public Integer countByOpenId(String openId) {
        LambdaQueryWrapper<OauthUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(OauthUser::getOpenId, openId);
        return oauthUserMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer insert(OauthUser oauthUser) {
        return oauthUserMapper.insert(oauthUser);
    }
}
