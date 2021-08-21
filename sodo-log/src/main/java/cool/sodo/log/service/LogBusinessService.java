package cool.sodo.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.LogBusiness;
import cool.sodo.log.entity.LogBusinessDTO;

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
     * @param logBusinessDTO LogBusiness 多条件查询实体
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cool.sodo.common.domain.LogBusiness>
     */
    IPage<LogBusiness> pageLogBusinessBase(LogBusinessDTO logBusinessDTO);
}
