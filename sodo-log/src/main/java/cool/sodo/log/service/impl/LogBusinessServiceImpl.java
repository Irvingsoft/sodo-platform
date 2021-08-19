package cool.sodo.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.LogApi;
import cool.sodo.common.domain.LogBusiness;
import cool.sodo.common.domain.LogError;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.StringUtil;
import cool.sodo.log.entity.LogBusinessRequest;
import cool.sodo.log.mapper.LogBusinessMapper;
import cool.sodo.log.service.LogBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogBusinessServiceImpl implements LogBusinessService {

    public static final String ERROR_SELECT = "LogBusiness 不存在！";
    public static final String ERROR_INSERT_ASYNC = "异步任务插入 LogBusiness 失败！";

    public static final int SELECT_BASE = 0;
    public static final int SELECT_INFO = 1;

    @Resource
    private LogBusinessMapper logBusinessMapper;

    private LambdaQueryWrapper<LogBusiness> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<LogBusiness> logBusinessLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_BASE:
                logBusinessLambdaQueryWrapper.select(LogBusiness::getId, LogBusiness::getServiceId, LogBusiness::getServiceIp,
                        LogBusiness::getServiceHost, LogBusiness::getEnv, LogBusiness::getClientId, LogBusiness::getUserId,
                        LogBusiness::getUserIp, LogBusiness::getRequestMethod, LogBusiness::getBusinessType,
                        LogBusiness::getMessage, LogBusiness::getCreateAt);
                break;
            case SELECT_INFO:
                logBusinessLambdaQueryWrapper.select(LogBusiness::getId, LogBusiness::getServiceId, LogBusiness::getServiceIp,
                        LogBusiness::getServiceHost, LogBusiness::getEnv, LogBusiness::getClientId, LogBusiness::getRequestId,
                        LogBusiness::getUserId, LogBusiness::getUserIp, LogBusiness::getSystemName, LogBusiness::getBrowserName,
                        LogBusiness::getRequestUrl, LogBusiness::getRequestMethod, LogBusiness::getClassName, LogBusiness::getMethodName,
                        LogBusiness::getBusinessType, LogBusiness::getBusinessId, LogBusiness::getBusinessData, LogBusiness::getMessage,
                        LogBusiness::getCreateAt);
                break;
            default:
                break;
        }
        return logBusinessLambdaQueryWrapper;
    }

    @Override
    public void insertLogBusinessByAsync(LogBusiness logBusiness) {

        if (logBusinessMapper.insert(logBusiness) <= 0) {
            throw new AsyncException(ERROR_INSERT_ASYNC);
        }
    }

    @Override
    public LogBusiness getBusinessInfo(String id) {

        LambdaQueryWrapper<LogBusiness> logBusinessLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        logBusinessLambdaQueryWrapper.eq(LogBusiness::getId, id);
        LogBusiness logBusiness = logBusinessMapper.selectOne(logBusinessLambdaQueryWrapper);
        if (StringUtil.isEmpty(logBusiness)) {
            throw new SoDoException(ERROR_SELECT);
        }
        return logBusiness;
    }

    @Override
    public IPage<LogBusiness> pageLogBusinessBase(LogBusinessRequest logBusinessRequest) {

        LambdaQueryWrapper<LogBusiness> logBusinessLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);

        if (!StringUtil.isEmpty(logBusinessRequest.getServiceId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getServiceId, logBusinessRequest.getServiceId());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getClientId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getClientId, logBusinessRequest.getClientId());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getUserId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getUserId, logBusinessRequest.getUserId());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getRequestId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getRequestId, logBusinessRequest.getRequestId());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getRequestMethod())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getRequestMethod, logBusinessRequest.getRequestMethod());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getBusinessId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getBusinessId, logBusinessRequest.getBusinessId());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getBusinessType())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getBusinessType, logBusinessRequest.getBusinessType());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getContent())) {
            logBusinessLambdaQueryWrapper.and(wrapper -> wrapper.like(LogBusiness::getMessage, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getBusinessData, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getServiceHost, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getSystemName, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getBrowserName, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getRequestUrl, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getClassName, logBusinessRequest.getContent())
                    .or()
                    .like(LogBusiness::getMethodName, logBusinessRequest.getContent()));
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getCreateBegin())) {
            logBusinessLambdaQueryWrapper.ge(LogBusiness::getCreateAt, logBusinessRequest.getCreateBegin());
        }
        if (!StringUtil.isEmpty(logBusinessRequest.getCreateEnd())) {
            logBusinessLambdaQueryWrapper.le(LogBusiness::getCreateAt, logBusinessRequest.getCreateEnd());
        }

        logBusinessLambdaQueryWrapper.orderByDesc(LogBusiness::getCreateAt);
        return logBusinessMapper.selectPage(new Page<>(logBusinessRequest.getPageNum(),
                logBusinessRequest.getPageSize()), logBusinessLambdaQueryWrapper);
    }
}