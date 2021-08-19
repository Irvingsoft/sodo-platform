package cool.sodo.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.LogError;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.StringUtil;
import cool.sodo.log.entity.LogErrorRequest;
import cool.sodo.log.mapper.LogErrorMapper;
import cool.sodo.log.service.LogErrorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogErrorServiceImpl implements LogErrorService {

    public static final String ERROR_SELECT = "LogError 不存在！";
    public static final String ERROR_INSERT_ASYNC = "异步任务插入 LogError 失败！";

    public static final int SELECT_BASE = 0;
    public static final int SELECT_INFO = 1;

    @Resource
    private LogErrorMapper logErrorMapper;

    private LambdaQueryWrapper<LogError> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<LogError> logErrorLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_BASE:
                logErrorLambdaQueryWrapper.select(LogError::getId, LogError::getServiceId, LogError::getServiceIp,
                        LogError::getServiceHost, LogError::getEnv, LogError::getClientId, LogError::getUserId,
                        LogError::getUserIp, LogError::getRequestMethod, LogError::getExceptionName,
                        LogError::getMessage, LogError::getCreateAt);
                break;
            case SELECT_INFO:
                logErrorLambdaQueryWrapper.select(LogError::getId, LogError::getServiceId, LogError::getServiceIp,
                        LogError::getServiceHost, LogError::getEnv, LogError::getClientId, LogError::getRequestId,
                        LogError::getUserId, LogError::getUserIp, LogError::getSystemName, LogError::getBrowserName,
                        LogError::getRequestUrl, LogError::getRequestMethod, LogError::getClassName, LogError::getMethodName,
                        LogError::getStackTrace, LogError::getExceptionName, LogError::getMessage,
                        LogError::getFileName, LogError::getLineNum, LogError::getParams, LogError::getCreateAt);
                break;
            default:
                break;
        }
        return logErrorLambdaQueryWrapper;
    }

    @Override
    public void insertLogErrorByAsync(LogError logError) {

        if (logErrorMapper.insert(logError) <= 0) {
            throw new AsyncException(ERROR_INSERT_ASYNC);
        }
    }

    @Override
    public LogError getLogErrorInfo(String id) {

        LambdaQueryWrapper<LogError> logErrorLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        logErrorLambdaQueryWrapper.eq(LogError::getId, id);
        LogError logError = logErrorMapper.selectOne(logErrorLambdaQueryWrapper);
        if (StringUtil.isEmpty(logError)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, id);
        }
        return logError;
    }

    @Override
    public IPage<LogError> pageLogErrorBase(LogErrorRequest logErrorRequest) {

        LambdaQueryWrapper<LogError> logErrorLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);

        if (!StringUtil.isEmpty(logErrorRequest.getServiceId())) {
            logErrorLambdaQueryWrapper.eq(LogError::getServiceId, logErrorRequest.getServiceId());
        }
        if (!StringUtil.isEmpty(logErrorRequest.getClientId())) {
            logErrorLambdaQueryWrapper.eq(LogError::getClientId, logErrorRequest.getClientId());
        }
        if (!StringUtil.isEmpty(logErrorRequest.getUserId())) {
            logErrorLambdaQueryWrapper.eq(LogError::getUserId, logErrorRequest.getUserId());
        }
        if (!StringUtil.isEmpty(logErrorRequest.getRequestId())) {
            logErrorLambdaQueryWrapper.eq(LogError::getRequestId, logErrorRequest.getRequestId());
        }
        if (!StringUtil.isEmpty(logErrorRequest.getRequestMethod())) {
            logErrorLambdaQueryWrapper.eq(LogError::getRequestMethod, logErrorRequest.getRequestMethod());
        }
        if (!StringUtil.isEmpty(logErrorRequest.getContent())) {
            logErrorLambdaQueryWrapper.and(wrapper -> wrapper.like(LogError::getStackTrace, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getExceptionName, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getMessage, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getFileName, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getLineNum, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getParams, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getServiceHost, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getSystemName, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getBrowserName, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getRequestUrl, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getClassName, logErrorRequest.getContent())
                    .or()
                    .like(LogError::getMethodName, logErrorRequest.getContent()));
        }
        if (!StringUtil.isEmpty(logErrorRequest.getCreateBegin())) {
            logErrorLambdaQueryWrapper.ge(LogError::getCreateAt, logErrorRequest.getCreateBegin());
        }
        if (!StringUtil.isEmpty(logErrorRequest.getCreateEnd())) {
            logErrorLambdaQueryWrapper.le(LogError::getCreateAt, logErrorRequest.getCreateEnd());
        }

        logErrorLambdaQueryWrapper.orderByDesc(LogError::getCreateAt);
        return logErrorMapper.selectPage(new Page<>(logErrorRequest.getPageNum(), logErrorRequest.getPageSize()),
                logErrorLambdaQueryWrapper);
    }
}
