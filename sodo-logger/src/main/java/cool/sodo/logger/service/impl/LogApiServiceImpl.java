package cool.sodo.logger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.LogApi;
import cool.sodo.common.domain.OauthApi;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.service.CommonOauthApiService;
import cool.sodo.logger.entity.LogApiRequest;
import cool.sodo.logger.mapper.LogApiMapper;
import cool.sodo.logger.service.LogApiService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class LogApiServiceImpl implements LogApiService {

    public static final String ERROR_SELECT = "LogApi 不存在！";
    public static final String ERROR_INSERT_ASYNC = "异步任务插入 LogApi 失败！";

    public static final int SELECT_BASE = 0;
    public static final int SELECT_INFO = 1;

    @Resource
    private LogApiMapper logApiMapper;
    @Resource
    private CommonOauthApiService oauthApiService;

    private LambdaQueryWrapper<LogApi> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<LogApi> logApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        switch (type) {
            case SELECT_BASE:
                logApiLambdaQueryWrapper.select(LogApi::getId, LogApi::getServiceId, LogApi::getServiceIp,
                        LogApi::getServiceHost, LogApi::getEnv, LogApi::getClientId, LogApi::getApiId,
                        LogApi::getUserId, LogApi::getUserIp, LogApi::getRequestMethod, LogApi::getTime,
                        LogApi::getCreateAt);
                break;
            case SELECT_INFO:
                logApiLambdaQueryWrapper.select(LogApi::getId, LogApi::getServiceId, LogApi::getServiceIp,
                        LogApi::getServiceHost, LogApi::getEnv, LogApi::getClientId, LogApi::getApiId,
                        LogApi::getUserId, LogApi::getUserIp, LogApi::getSystemName, LogApi::getBrowserName,
                        LogApi::getRequestUrl, LogApi::getRequestMethod, LogApi::getClassName, LogApi::getMethodName,
                        LogApi::getRequestBody, LogApi::getTime, LogApi::getCreateAt);
                break;
            default:
                break;
        }
        return logApiLambdaQueryWrapper;
    }

    @Async
    @Override
    public void insertLogApiByAsync(LogApi logApi) {

        if (logApiMapper.insert(logApi) <= 0) {
            throw new AsyncException(ERROR_INSERT_ASYNC);
        }
    }

    @Override
    public LogApi getLogApiInfoDetail(String id) {

        LambdaQueryWrapper<LogApi> logApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        logApiLambdaQueryWrapper.eq(LogApi::getId, id);
        LogApi logApi = logApiMapper.selectOne(logApiLambdaQueryWrapper);
        if (StringUtils.isEmpty(logApi)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, id);
        }
        if (!StringUtils.isEmpty(logApi.getApiId())) {
            OauthApi oauthApi = oauthApiService.getOauthApiBaseNullable(logApi.getApiId());
            if (!StringUtils.isEmpty(oauthApi)) {
                logApi.setApiName(oauthApi.getName());
                logApi.setApiPath(oauthApi.getPath());
            }
        }
        return logApi;
    }

    @Override
    public IPage<LogApi> pageLogApiBaseDetail(LogApiRequest logApiRequest) {

        LambdaQueryWrapper<LogApi> logApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);

        if (!StringUtils.isEmpty(logApiRequest.getApiId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getApiId, logApiRequest.getApiId());
        }
        if (!StringUtils.isEmpty(logApiRequest.getServiceId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getServiceId, logApiRequest.getServiceId());
        }
        if (!StringUtils.isEmpty(logApiRequest.getClientId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getClientId, logApiRequest.getClientId());
        }
        if (!StringUtils.isEmpty(logApiRequest.getUserId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getUserId, logApiRequest.getUserId());
        }
        if (!StringUtils.isEmpty(logApiRequest.getRequestMethod())) {
            logApiLambdaQueryWrapper.eq(LogApi::getRequestMethod, logApiRequest.getRequestMethod());
        }
        if (!StringUtils.isEmpty(logApiRequest.getContent())) {
            logApiLambdaQueryWrapper.and(wrapper -> wrapper.like(LogApi::getServiceHost, logApiRequest.getContent())
                    .or()
                    .like(LogApi::getSystemName, logApiRequest.getContent())
                    .or()
                    .like(LogApi::getBrowserName, logApiRequest.getContent())
                    .or()
                    .like(LogApi::getRequestUrl, logApiRequest.getContent())
                    .or()
                    .like(LogApi::getClassName, logApiRequest.getContent())
                    .or()
                    .like(LogApi::getMethodName, logApiRequest.getContent())
                    .or()
                    .like(LogApi::getRequestBody, logApiRequest.getContent()));
        }
        if (!StringUtils.isEmpty(logApiRequest.getTimeBegin())) {
            logApiLambdaQueryWrapper.ge(LogApi::getTime, logApiRequest.getTimeBegin());
        }
        if (!StringUtils.isEmpty(logApiRequest.getTimeEnd())) {
            logApiLambdaQueryWrapper.le(LogApi::getTime, logApiRequest.getTimeEnd());
        }
        if (!StringUtils.isEmpty(logApiRequest.getCreateBegin())) {
            logApiLambdaQueryWrapper.ge(LogApi::getCreateAt, logApiRequest.getCreateBegin());
        }
        if (!StringUtils.isEmpty(logApiRequest.getCreateEnd())) {
            logApiLambdaQueryWrapper.le(LogApi::getCreateAt, logApiRequest.getCreateEnd());
        }

        logApiLambdaQueryWrapper.orderByDesc(LogApi::getCreateAt);
        IPage<LogApi> logApiPage = logApiMapper.selectPage(new Page<>(logApiRequest.getPageNum(), logApiRequest.getPageSize()), logApiLambdaQueryWrapper);

        for (LogApi logApi : logApiPage.getRecords()) {

            if (!StringUtils.isEmpty(logApi.getApiId())) {
                OauthApi oauthApi = oauthApiService.getOauthApiBaseNullable(logApi.getApiId());
                if (!StringUtils.isEmpty(oauthApi)) {
                    logApi.setApiName(oauthApi.getName());
                    logApi.setApiPath(oauthApi.getPath());
                }
            }
        }
        return logApiPage;
    }
}
