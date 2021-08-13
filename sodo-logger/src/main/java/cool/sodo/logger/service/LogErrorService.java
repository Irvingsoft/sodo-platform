package cool.sodo.logger.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.LogError;
import cool.sodo.logger.entity.LogErrorRequest;

public interface LogErrorService {

    void insertLogErrorByAsync(LogError logError);

    /**
     * 查询 LogError 详情信息
     *
     * @param id LogError.id
     * @return cool.sodo.common.domain.LogError
     */
    LogError getLogErrorInfo(String id);

    /**
     * 分页查询 LogError
     *
     * @param logErrorRequest 错误日志多条件查询实体
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cool.sodo.common.domain.LogError>
     */
    IPage<LogError> pageLogErrorBase(LogErrorRequest logErrorRequest);
}
