package cool.sodo.catkin.server.service;

import cool.sodo.catkin.server.domain.CatkinInfo;

public interface CatkinInfoService {

    int updateMaxId(Long id, Long newMaxId, Long oldMaxId, Long version, String bizType);

    CatkinInfo getOneByBizType(String bizType);
}
