package cool.sodo.logger.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.LogBusiness;
import cool.sodo.logger.entity.LogBusinessRequest;

public interface LogBusinessService {

    void insertLogBusinessByAsync(LogBusiness logBusiness);

    /**
     * 查询 LogBusiness 详情信息
     *
     * @param id LogBusiness.id
     * @return cool.sodo.common.domain.LogBusiness
     */
    LogBusiness getBusinessInfo(String id);

    /**
     * 分页查询 LogBusiness
     *
     * @param logBusinessRequest LogBusiness 多条件查询实体
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cool.sodo.common.domain.LogBusiness>
     */
    IPage<LogBusiness> pageLogBusinessBase(LogBusinessRequest logBusinessRequest);
}
