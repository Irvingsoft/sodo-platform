package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.OauthApi;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonOauthApiMapper;
import cool.sodo.common.service.CommonOauthApiService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 通用 OauthApi 服务
 *
 * @author TimeChaser
 * @date 2021/6/15 17:21
 */
@Service
public class CommonOauthApiServiceImpl implements CommonOauthApiService {

    public static final String ERROR_SELECT = "未找到相关的 OauthApi！";
    public static final String ERROR_UPDATE = "异步任务更新 OauthApi 访问数据失败！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private CommonOauthApiMapper commonOauthApiMapper;

    private LambdaQueryWrapper<OauthApi> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthApiLambdaQueryWrapper.select(OauthApi::getApiId, OauthApi::getPath, OauthApi::getInUse,
                        OauthApi::getAuth, OauthApi::getLog, OauthApi::getLimitNum, OauthApi::getLimitPeriod,
                        OauthApi::getRequestLimit);
                break;
            case SELECT_BASE:
                oauthApiLambdaQueryWrapper.select(OauthApi::getApiId, OauthApi::getName, OauthApi::getPath,
                        OauthApi::getRequestDay, OauthApi::getRequestWeek, OauthApi::getRequestMonth,
                        OauthApi::getRequestAll);
                break;
            default:
                break;
        }
        return oauthApiLambdaQueryWrapper;
    }

    @Override
    public OauthApi getOauthApiBaseNullable(String apiId) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getApiId, apiId);

        return commonOauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);
    }

    @Override
    public OauthApi getOauthApiIdentityByPathAndMethod(String path, String method) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getPath, path)
                .eq(OauthApi::getMethod, method);

        OauthApi oauthApi = commonOauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);
        if (StringUtils.isEmpty(oauthApi)) {
            throw new SoDoException(ResultEnum.UNKNOWN_API, ERROR_SELECT);
        }
        return oauthApi;
    }
}
