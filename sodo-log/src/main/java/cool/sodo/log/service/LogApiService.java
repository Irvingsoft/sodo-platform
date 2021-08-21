package cool.sodo.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.LogApi;
import cool.sodo.log.entity.LogApiDTO;

/**
 * LogApi Service 层
 *
 * @author TimeChaser
 * @date 2021/6/23 11:42
 */
public interface LogApiService {

    void insertLogApiByAsync(LogApi logApi);

    /**
     * 根据 ID 查询 LogApi 详情信息，并填充外部属性
     *
     * @param id LogApi.id
     * @return cool.sodo.common.domain.LogApi
     */
    LogApi getLogApiInfoDetail(String id);

    /**
     * 多条件分页查询 LogApi 基本信息，并填充外部属性
     *
     * @param logApiDTO LogApi 查询多条件实体
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cool.sodo.common.domain.LogApi>
     */
    IPage<LogApi> pageLogApiBaseDetail(LogApiDTO logApiDTO);
}
