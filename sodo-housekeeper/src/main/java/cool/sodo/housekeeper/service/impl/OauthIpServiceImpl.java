package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.OauthIp;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.housekeeper.entity.OauthIpRequest;
import cool.sodo.housekeeper.mapper.OauthIpMapper;
import cool.sodo.housekeeper.service.OauthIpService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class OauthIpServiceImpl implements OauthIpService {

    public static final String ERROR_INSERT = "OauthIp 新增失败！";
    public static final String ERROR_DELETE = "OauthIp 删除失败！";
    public static final String ERROR_UPDATE = "OauthIp 更新失败！";
    public static final String ERROR_SELECT = "OauthIp 不存在！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private OauthIpMapper oauthIpMapper;

    private LambdaQueryWrapper<OauthIp> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getClientId);
                break;
            case SELECT_BASE:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getIp, OauthIp::getClientId
                        , OauthIp::getValid, OauthIp::getValidNum);
                break;
            case SELECT_INFO:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getIp, OauthIp::getDescription
                        , OauthIp::getClientId, OauthIp::getValid, OauthIp::getValidNum, OauthIp::getCreateBy
                        , OauthIp::getCreateAt, OauthIp::getUpdateBy, OauthIp::getUpdateAt);
                break;
            default:
                break;
        }
        return oauthIpLambdaQueryWrapper;
    }

    @Override
    public void insertOauthIp(OauthIp oauthIp) {

        if (oauthIpMapper.insert(oauthIp) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
    }

    @Override
    public void deleteOauthIp(String id) {

        if (oauthIpMapper.deleteById(id) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void updateOauthIp(OauthIp oauthIp) {

        OauthIp oauthIpOld = getOauthIp(oauthIp.getIpId());

        oauthIpOld.update(oauthIp);
        if (oauthIpMapper.updateById(oauthIpOld) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    public OauthIp getOauthIp(String id) {

        OauthIp oauthIp = oauthIpMapper.selectById(id);
        if (StringUtils.isEmpty(oauthIp)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthIp;
    }

    @Override
    public OauthIp getOauthIpIdentity(String id) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthIpLambdaQueryWrapper.eq(OauthIp::getIpId, id);
        OauthIp oauthIp = oauthIpMapper.selectOne(oauthIpLambdaQueryWrapper);
        if (StringUtils.isEmpty(oauthIp)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthIp;
    }

    @Override
    public OauthIp getOauthIpIdentityNullable(String id) {
        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthIpLambdaQueryWrapper.eq(OauthIp::getIpId, id);
        return oauthIpMapper.selectOne(oauthIpLambdaQueryWrapper);
    }

    @Override
    public IPage<OauthIp> pageOauthIpInfo(OauthIpRequest oauthIpRequest) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);

        if (!StringUtils.isEmpty(oauthIpRequest.getClientId())) {
            oauthIpLambdaQueryWrapper.eq(OauthIp::getClientId, oauthIpRequest.getClientId());
        }
        if (!StringUtils.isEmpty(oauthIpRequest.getContent())) {
            oauthIpLambdaQueryWrapper.and(
                    wrapper -> wrapper.like(OauthIp::getIp, oauthIpRequest.getContent())
                            .or()
                            .like(OauthIp::getDescription, oauthIpRequest.getContent())
            );
        }

        return oauthIpMapper.selectPage(new Page<>(oauthIpRequest.getPageNum(), oauthIpRequest.getPageSize()), oauthIpLambdaQueryWrapper);
    }
}
