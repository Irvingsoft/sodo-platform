package cool.sodo.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.LogBusiness;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.StringUtil;
import cool.sodo.log.entity.LogBusinessDTO;
import cool.sodo.log.mapper.LogBusinessMapper;
import cool.sodo.log.service.LogBusinessService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/3 0:27
 */
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
    @Async
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
    public IPage<LogBusiness> pageLogBusinessBase(LogBusinessDTO logBusinessDTO) {

        LambdaQueryWrapper<LogBusiness> logBusinessLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);

        if (!StringUtil.isEmpty(logBusinessDTO.getServiceId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getServiceId, logBusinessDTO.getServiceId());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getClientId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getClientId, logBusinessDTO.getClientId());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getUserId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getUserId, logBusinessDTO.getUserId());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getRequestId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getRequestId, logBusinessDTO.getRequestId());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getRequestMethod())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getRequestMethod, logBusinessDTO.getRequestMethod());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getBusinessId())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getBusinessId, logBusinessDTO.getBusinessId());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getBusinessType())) {
            logBusinessLambdaQueryWrapper.eq(LogBusiness::getBusinessType, logBusinessDTO.getBusinessType());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getContent())) {
            logBusinessLambdaQueryWrapper.and(wrapper -> wrapper.like(LogBusiness::getMessage, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getBusinessData, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getServiceHost, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getSystemName, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getBrowserName, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getRequestUrl, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getClassName, logBusinessDTO.getContent())
                    .or()
                    .like(LogBusiness::getMethodName, logBusinessDTO.getContent()));
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getCreateBegin())) {
            logBusinessLambdaQueryWrapper.ge(LogBusiness::getCreateAt, logBusinessDTO.getCreateBegin());
        }
        if (!StringUtil.isEmpty(logBusinessDTO.getCreateEnd())) {
            logBusinessLambdaQueryWrapper.le(LogBusiness::getCreateAt, logBusinessDTO.getCreateEnd());
        }

        logBusinessLambdaQueryWrapper.orderByDesc(LogBusiness::getCreateAt);
        return logBusinessMapper.selectPage(new Page<>(logBusinessDTO.getPageNum(),
                logBusinessDTO.getPageSize()), logBusinessLambdaQueryWrapper);
    }
}
