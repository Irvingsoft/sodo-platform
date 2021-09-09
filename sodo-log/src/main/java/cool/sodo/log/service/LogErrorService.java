package cool.sodo.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.log.domain.LogError;
import cool.sodo.log.entity.LogErrorDTO;

public interface LogErrorService {

    void insertLogErrorByAsync(LogError logError);

    /**
     * 查询 LogError 详情信息
     *
     * @param id LogError.id
     * @return cool.sodo.log.domain.LogError
     */
    LogError getLogErrorInfo(String id);

    /**
     * 分页查询 LogError
     *
     * @param logErrorDTO 错误日志多条件查询实体
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cool.sodo.log.domain.LogError>
     */
    IPage<LogError> pageLogErrorBase(LogErrorDTO logErrorDTO);
}
