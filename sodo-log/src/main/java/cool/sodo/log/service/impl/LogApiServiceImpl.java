package cool.sodo.log.service.impl;

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
import cool.sodo.common.util.StringUtil;
import cool.sodo.log.entity.LogApiDTO;
import cool.sodo.log.mapper.LogApiMapper;
import cool.sodo.log.service.LogApiService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/3 0:27
 */
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
                        LogApi::getUserId, LogApi::getUserIp, LogApi::getRequestMethod,
                        LogApi::getTime, LogApi::getResponseStatus, LogApi::getCreateAt);
                break;
            case SELECT_INFO:
                logApiLambdaQueryWrapper.select(LogApi::getId, LogApi::getServiceId, LogApi::getServiceIp,
                        LogApi::getServiceHost, LogApi::getEnv, LogApi::getClientId, LogApi::getApiId,
                        LogApi::getRequestId, LogApi::getUserId, LogApi::getUserIp, LogApi::getSystemName,
                        LogApi::getBrowserName, LogApi::getRequestUrl, LogApi::getRequestMethod, LogApi::getClassName,
                        LogApi::getMethodName, LogApi::getRequestBody, LogApi::getTime, LogApi::getResponseStatus,
                        LogApi::getResponseBody, LogApi::getCreateAt);
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
        if (StringUtil.isEmpty(logApi)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, id);
        }
        if (!StringUtil.isEmpty(logApi.getApiId())) {
            OauthApi oauthApi = oauthApiService.getOauthApiBaseNullable(logApi.getApiId());
            if (!StringUtil.isEmpty(oauthApi)) {
                logApi.setApiName(oauthApi.getName());
                logApi.setApiPath(oauthApi.getPath());
            }
        }
        return logApi;
    }

    @Override
    public IPage<LogApi> pageLogApiBaseDetail(LogApiDTO logApiDTO) {

        LambdaQueryWrapper<LogApi> logApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);

        if (!StringUtil.isEmpty(logApiDTO.getApiId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getApiId, logApiDTO.getApiId());
        }
        if (!StringUtil.isEmpty(logApiDTO.getServiceId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getServiceId, logApiDTO.getServiceId());
        }
        if (!StringUtil.isEmpty(logApiDTO.getClientId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getClientId, logApiDTO.getClientId());
        }
        if (!StringUtil.isEmpty(logApiDTO.getUserId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getUserId, logApiDTO.getUserId());
        }
        if (!StringUtil.isEmpty(logApiDTO.getRequestId())) {
            logApiLambdaQueryWrapper.eq(LogApi::getRequestId, logApiDTO.getRequestId());
        }
        if (!StringUtil.isEmpty(logApiDTO.getRequestMethod())) {
            logApiLambdaQueryWrapper.eq(LogApi::getRequestMethod, logApiDTO.getRequestMethod());
        }
        if (!StringUtil.isEmpty(logApiDTO.getResponseStatus())) {
            logApiLambdaQueryWrapper.eq(LogApi::getResponseStatus, logApiDTO.getResponseStatus());
        }
        if (!StringUtil.isEmpty(logApiDTO.getContent())) {
            logApiLambdaQueryWrapper.and(wrapper -> wrapper.like(LogApi::getServiceHost, logApiDTO.getContent())
                    .or()
                    .like(LogApi::getSystemName, logApiDTO.getContent())
                    .or()
                    .like(LogApi::getBrowserName, logApiDTO.getContent())
                    .or()
                    .like(LogApi::getRequestUrl, logApiDTO.getContent())
                    .or()
                    .like(LogApi::getClassName, logApiDTO.getContent())
                    .or()
                    .like(LogApi::getMethodName, logApiDTO.getContent())
                    .or()
                    .like(LogApi::getRequestBody, logApiDTO.getContent()));
        }
        if (!StringUtil.isEmpty(logApiDTO.getTimeBegin())) {
            logApiLambdaQueryWrapper.ge(LogApi::getTime, logApiDTO.getTimeBegin());
        }
        if (!StringUtil.isEmpty(logApiDTO.getTimeEnd())) {
            logApiLambdaQueryWrapper.le(LogApi::getTime, logApiDTO.getTimeEnd());
        }
        if (!StringUtil.isEmpty(logApiDTO.getCreateBegin())) {
            logApiLambdaQueryWrapper.ge(LogApi::getCreateAt, logApiDTO.getCreateBegin());
        }
        if (!StringUtil.isEmpty(logApiDTO.getCreateEnd())) {
            logApiLambdaQueryWrapper.le(LogApi::getCreateAt, logApiDTO.getCreateEnd());
        }

        logApiLambdaQueryWrapper.orderByDesc(LogApi::getCreateAt);
        IPage<LogApi> logApiPage = logApiMapper.selectPage(new Page<>(logApiDTO.getPageNum(), logApiDTO.getPageSize()), logApiLambdaQueryWrapper);

        for (LogApi logApi : logApiPage.getRecords()) {

            if (!StringUtil.isEmpty(logApi.getApiId())) {
                OauthApi oauthApi = oauthApiService.getOauthApiBaseNullable(logApi.getApiId());
                if (!StringUtil.isEmpty(oauthApi)) {
                    logApi.setApiName(oauthApi.getName());
                    logApi.setApiPath(oauthApi.getPath());
                }
            }
        }
        return logApiPage;
    }
}
